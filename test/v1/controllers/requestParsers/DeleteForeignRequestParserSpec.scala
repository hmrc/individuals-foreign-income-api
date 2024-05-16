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

package v1.controllers.requestParsers

import shared.models.domain.{Nino, TaxYear}
import shared.models.errors._
import shared.UnitSpec
import v1.controllers.requestParsers.validators.MockDeleteForeignValidator
import v1.models.request.delete.{DeleteForeignRawData, DeleteForeignRequest}

class DeleteForeignRequestParserSpec extends UnitSpec {

  val nino: String                   = "AA123456B"
  val taxYear: String                = "2019-20"
  implicit val correlationId: String = "a1e8057e-fbbc-47a8-a8b4-78d9f015c253"

  val deleteForeignSavingsRawData: DeleteForeignRawData = DeleteForeignRawData(
    nino = nino,
    taxYear = taxYear
  )

  trait Test extends MockDeleteForeignValidator {

    lazy val parser: DeleteForeignRequestParser = new DeleteForeignRequestParser(
      validator = mockDeleteForeignValidator
    )

  }

  "parse" should {
    "return a request object" when {
      "valid request data is supplied" in new Test {
        MockedDeleteForeignValidator.validate(deleteForeignSavingsRawData).returns(Nil)

        parser.parseRequest(deleteForeignSavingsRawData) shouldBe
          Right(DeleteForeignRequest(Nino(nino), TaxYear.fromMtd(taxYear)))
      }
    }

    "return an ErrorWrapper" when {
      "a single validation error occurs" in new Test {
        MockedDeleteForeignValidator
          .validate(deleteForeignSavingsRawData)
          .returns(List(NinoFormatError))

        parser.parseRequest(deleteForeignSavingsRawData) shouldBe
          Left(ErrorWrapper(correlationId, NinoFormatError, None))
      }

      "multiple validation errors occur" in new Test {
        MockedDeleteForeignValidator
          .validate(deleteForeignSavingsRawData)
          .returns(List(NinoFormatError, TaxYearFormatError))

        parser.parseRequest(deleteForeignSavingsRawData) shouldBe
          Left(ErrorWrapper(correlationId, BadRequestError, Some(List(NinoFormatError, TaxYearFormatError))))
      }
    }
  }

}
