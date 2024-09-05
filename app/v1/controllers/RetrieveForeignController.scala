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
import shared.controllers.{AuthorisedController, EndpointLogContext, RequestContext, RequestHandler}
import shared.services.{EnrolmentsAuthService, MtdIdLookupService}
import shared.utils.IdGenerator
import v1.controllers.validators.RetrieveForeignValidatorFactory
import v1.services.RetrieveForeignService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class RetrieveForeignController @Inject() (val authService: EnrolmentsAuthService,
                                           val lookupService: MtdIdLookupService,
                                           validatorFactory: RetrieveForeignValidatorFactory,
                                           service: RetrieveForeignService,
                                           cc: ControllerComponents,
                                           val idGenerator: IdGenerator,
                                           foreignIncomeConfig: ForeignIncomeConfig)(implicit ec: ExecutionContext, appConfig: AppConfig)
    extends AuthorisedController(cc) {

  override val endpointName: String = "retrieve-foreign"


  implicit val endpointLogContext: EndpointLogContext =
    EndpointLogContext(
      controllerName = "RetrieveForeignController",
      endpointName = "retrieveForeign"
    )

  def retrieveForeign(nino: String, taxYear: String): Action[AnyContent] =
    authorisedAction(nino).async { implicit request =>
      implicit val ctx: RequestContext = RequestContext.from(idGenerator, endpointLogContext)

      val validator = validatorFactory.validator(nino, taxYear)
      val requestHandler =
        RequestHandler
          .withValidator(validator)
          .withService(service.retrieve)
          .withPlainJsonResult()

      requestHandler.handleRequest()
    }

}
