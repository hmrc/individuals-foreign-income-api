/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.controllers

import config.MockForeignIncomeConfig
import play.api.Configuration
import play.api.libs.json.JsValue
import play.api.mvc.Result
import shared.controllers.{ControllerBaseSpec, ControllerTestRunner}
import shared.models.audit.{AuditEvent, AuditResponse, GenericAuditDetail}
import shared.models.domain.{Nino, TaxYear}
import shared.models.errors.*
import shared.models.outcomes.ResponseWrapper
import shared.services.MockAuditService
import shared.utils.MockIdGenerator
import v1.controllers.validators.MockDeleteForeignValidatorFactory
import v1.models.request.delete
import v1.models.request.delete.DeleteForeignRequest
import v1.services.MockDeleteForeignService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DeleteForeignControllerSpec
    extends ControllerBaseSpec
    with ControllerTestRunner
    with MockAuditService
    with MockDeleteForeignService
    with MockDeleteForeignValidatorFactory
    with MockIdGenerator
    with MockForeignIncomeConfig {

  val minTaxYear: Int = 2019
  val taxYear: String = "2019-20"

  val requestData: DeleteForeignRequest = delete.DeleteForeignRequest(
    nino = Nino(validNino),
    taxYear = TaxYear.fromMtd(taxYear)
  )

  "DeleteForeignController" should {
    "return a successful response with status 204 (No Content)" when {
      "happy path" in new Test {

        willUseValidator(returningSuccess(requestData))

        MockedDeleteForeignService
          .deleteForeign(requestData)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, ()))))

        runOkTestWithAudit(expectedStatus = NO_CONTENT)
      }
    }

    "return the error as per spec" when {
      "the parser validation fails" in new Test {
        willUseValidator(returning(NinoFormatError))

        runErrorTestWithAudit(NinoFormatError)

      }

      "service returns an error" in new Test {
        willUseValidator(returningSuccess(requestData))

        MockedDeleteForeignService
          .deleteForeign(requestData)
          .returns(Future.successful(Left(ErrorWrapper(correlationId, RuleTaxYearNotSupportedError))))

        runErrorTestWithAudit(RuleTaxYearNotSupportedError)
      }
    }
  }

  trait Test extends ControllerTest with AuditEventChecking[GenericAuditDetail] {

    val controller = new DeleteForeignController(
      authService = mockEnrolmentsAuthService,
      lookupService = mockMtdIdLookupService,
      validatorFactory = mockDeleteForeignValidatorFactory,
      service = mockDeleteForeignService,
      auditService = mockAuditService,
      cc = cc,
      idGenerator = mockIdGenerator,
      mockForeignIncomeConfig
    )

    MockedAppConfig.featureSwitchConfig.anyNumberOfTimes() returns Configuration(
      "supporting-agents-access-control.enabled" -> true
    )

    MockedAppConfig.endpointAllowsSupportingAgents(controller.endpointName).anyNumberOfTimes() returns false

    protected def callController(): Future[Result] =
      controller.deleteForeign(validNino, taxYear)(fakeRequest)

    def event(auditResponse: AuditResponse, requestBody: Option[JsValue]): AuditEvent[GenericAuditDetail] =
      AuditEvent(
        auditType = "DeleteForeignIncome",
        transactionName = "delete-foreign-income",
        detail = GenericAuditDetail(
          userType = "Individual",
          agentReferenceNumber = None,
          versionNumber = "1.0",
          params = Map("nino" -> validNino, "taxYear" -> taxYear),
          requestBody = None,
          `X-CorrelationId` = correlationId,
          auditResponse = auditResponse
        )
      )

  }

}
