/*
 * Copyright 2023 HM Revenue & Customs
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

package v1.controllers.requestParsers

import shared.controllers.requestParsers.RequestParser
import shared.models.domain.{Nino, TaxYear}
import v1.controllers.requestParsers.validators.CreateAmendForeignValidator
import v1.models.request.createAmend
import v1.models.request.createAmend.{CreateAmendForeignRawData, CreateAmendForeignRequest, CreateAmendForeignRequestBody}

import javax.inject.{Inject, Singleton}

@Singleton
class CreateAmendForeignRequestParser @Inject() (val validator: CreateAmendForeignValidator) extends RequestParser[CreateAmendForeignRawData, CreateAmendForeignRequest] {

  override protected def requestFor(data: CreateAmendForeignRawData): CreateAmendForeignRequest =
    createAmend.CreateAmendForeignRequest(Nino(data.nino), TaxYear.fromMtd(data.taxYear), data.body.json.as[CreateAmendForeignRequestBody])

}
