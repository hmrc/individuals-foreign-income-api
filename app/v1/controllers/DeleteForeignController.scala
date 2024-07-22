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

import config.ForeignIncomeConfig
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import shared.config.AppConfig
import shared.controllers._
import shared.routing.Version1
import shared.services.{AuditService, EnrolmentsAuthService, MtdIdLookupService}
import shared.utils.IdGenerator
import v1.controllers.validators.DeleteForeignValidatorFactory
import v1.services.DeleteForeignService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class DeleteForeignController @Inject() (val authService: EnrolmentsAuthService,
                                         val lookupService: MtdIdLookupService,
                                         validatorFactory: DeleteForeignValidatorFactory,
                                         service: DeleteForeignService,
                                         auditService: AuditService,
                                         cc: ControllerComponents,
                                         val idGenerator: IdGenerator,
                                         foreignIncomeConfig: ForeignIncomeConfig)(implicit ec: ExecutionContext, appConfig: AppConfig)
    extends AuthorisedController(cc) {

  implicit val endpointLogContext: EndpointLogContext =
    EndpointLogContext(
      controllerName = "DeleteForeignController",
      endpointName = "deleteForeign"
    )

  def deleteForeign(nino: String, taxYear: String): Action[AnyContent] =
    authorisedAction(nino).async { implicit request =>
      implicit val ctx: RequestContext = RequestContext.from(idGenerator, endpointLogContext)
      val validator                    = validatorFactory.validator(nino, taxYear)

      val requestHandler = RequestHandler
        .withValidator(validator)
        .withService(service.deleteForeign)
        .withAuditing(AuditHandler(
          auditService = auditService,
          auditType = "DeleteForeignIncome",
          transactionName = "delete-foreign-income",
          apiVersion = Version1,
          params = Map("nino" -> nino, "taxYear" -> taxYear),
          requestBody = None
        ))

      requestHandler.handleRequest()
    }

}
