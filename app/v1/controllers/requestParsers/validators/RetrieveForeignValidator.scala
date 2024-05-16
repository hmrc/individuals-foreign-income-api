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

package v1.controllers.requestParsers.validators

import config.ForeignIncomeConfig
import shared.controllers.requestParsers.validators.Validator
import shared.controllers.requestParsers.validators.validations._
import shared.models.errors.MtdError
import v1.models.request.retrieve.RetrieveForeignRawData

import javax.inject.{Inject, Singleton}

@Singleton
class RetrieveForeignValidator @Inject() (implicit appConfig: ForeignIncomeConfig) extends Validator[RetrieveForeignRawData] {

  private val validationSet = List(parameterFormatValidation, parameterRuleValidation)

  override def validate(data: RetrieveForeignRawData): Seq[MtdError] = run(validationSet, data).distinct

  private def parameterFormatValidation: RetrieveForeignRawData => Seq[Seq[MtdError]] =
    (data: RetrieveForeignRawData) => List(NinoValidation.validate(data.nino), TaxYearValidation.validate(data.taxYear))

  private def parameterRuleValidation: RetrieveForeignRawData => Seq[Seq[MtdError]] =
    (data: RetrieveForeignRawData) => List(TaxYearNotSupportedValidation.validate(data.taxYear, appConfig.minimumPermittedTaxYear()))

}
