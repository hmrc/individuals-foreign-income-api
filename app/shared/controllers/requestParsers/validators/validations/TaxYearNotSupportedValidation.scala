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

package shared.controllers.requestParsers.validators.validations

import shared.models.domain.TaxYear
import shared.models.errors.{MtdError, RuleTaxYearNotSupportedError}

object TaxYearNotSupportedValidation {

  // @param taxYear In format YYYY-YY
  def validate(taxYear: String, minimumTaxYear: Int): List[MtdError] = {
    val year = TaxYear.fromMtd(taxYear).year

    if (year < minimumTaxYear) List(RuleTaxYearNotSupportedError)
    else NoValidationErrors
  }

}
