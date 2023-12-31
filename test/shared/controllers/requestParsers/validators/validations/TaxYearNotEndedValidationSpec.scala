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

import org.scalamock.handlers.CallHandler
import shared.models.errors._
import shared.utils.{CurrentDate, MockCurrentDate}
import support.UnitSpec

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaxYearNotEndedValidationSpec extends UnitSpec {

  class Test extends MockCurrentDate {
    implicit val dateProvider: CurrentDate = mockCurrentDate
    val dateFormatter: DateTimeFormatter       = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    def setupTimeProvider(date: String): CallHandler[LocalDate] =
      MockCurrentDate.getLocalDate
        .returns(LocalDate.parse(date, dateFormatter))

  }

  "validate" should {
    "return no errors" when {
      "the supplied tax year has ended" in new Test {

        setupTimeProvider("2022-04-06")

        private val validTaxYear     = "2021-22"
        private val validationResult = TaxYearNotEndedValidation.validate(validTaxYear)

        validationResult.isEmpty shouldBe true
      }
    }

    "return the given error" when {
      "the supplied tax year has not yet ended" in new Test {

        setupTimeProvider("2022-04-04")

        private val invalidTaxYear   = "2021-22"
        private val validationResult = TaxYearNotEndedValidation.validate(invalidTaxYear)

        validationResult.isEmpty shouldBe false
        validationResult.length shouldBe 1
        validationResult.head shouldBe RuleTaxYearNotEndedError
      }
    }
  }

}
