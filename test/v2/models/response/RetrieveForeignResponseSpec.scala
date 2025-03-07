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

package v2.models.response

import play.api.libs.json.{JsError, Json}
import shared.models.domain.Timestamp
import shared.utils.UnitSpec
import v2.models.response.retrieve.{ForeignEarnings, RetrieveForeignResponse, UnremittableForeignIncome}

class RetrieveForeignResponseSpec extends UnitSpec {

  private val fullRetrieveForeignResponseJson = Json.parse(
    """
      |{
      |   "submittedOn": "2019-04-04T01:01:01.000Z",
      |   "foreignEarnings": {
      |     "customerReference": "FOREIGNINCME123A",
      |     "earningsNotTaxableUK": 1999.99
      |   },
      |   "unremittableForeignIncome": [
      |     {
      |       "countryCode": "FRA",
      |       "amountInForeignCurrency": 1999.99,
      |       "amountTaxPaid": 1999.99
      |     },
      |     {
      |       "countryCode": "IND",
      |       "amountInForeignCurrency": 2999.99,
      |       "amountTaxPaid": 2999.99
      |     }
      |   ]
      |}
    """.stripMargin
  )

  val fullForeignEarningsModel: ForeignEarnings = ForeignEarnings(
    customerReference = Some("FOREIGNINCME123A"),
    earningsNotTaxableUK = 1999.99
  )

  val fullUnremittableForeignIncomeModel1: UnremittableForeignIncome = UnremittableForeignIncome(
    countryCode = "FRA",
    amountInForeignCurrency = 1999.99,
    amountTaxPaid = Some(1999.99)
  )

  val fullUnremittableForeignIncomeModel2: UnremittableForeignIncome = UnremittableForeignIncome(
    countryCode = "IND",
    amountInForeignCurrency = 2999.99,
    amountTaxPaid = Some(2999.99)
  )

  private val fullRetrieveResponseBodyModel = RetrieveForeignResponse(
    submittedOn = Timestamp("2019-04-04T01:01:01.000Z"),
    foreignEarnings = Some(fullForeignEarningsModel),
    unremittableForeignIncome = Some(
      List(
        fullUnremittableForeignIncomeModel1,
        fullUnremittableForeignIncomeModel2
      ))
  )

  private val minRetrieveResponseBodyModel = RetrieveForeignResponse(Timestamp("2019-04-04T01:01:01.000Z"), None, None)

  "RetrieveForeignResponse" when {
    "read from valid JSON" should {
      "produce the expected RetrieveForeignResponseBody model" in {
        fullRetrieveForeignResponseJson.as[RetrieveForeignResponse] shouldBe fullRetrieveResponseBodyModel
      }
    }

    "read from empty JSON" should {
      "produce a model with 'foreignEarnings' and 'unremittableForeignIncome' as None" in {
        val emptyJson = Json.parse("""{"submittedOn": "2019-04-04T01:01:01.000Z"}""")

        emptyJson.as[RetrieveForeignResponse] shouldBe minRetrieveResponseBodyModel
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson = Json.parse(
          """
            |{
            |   "foreignEarnings": {
            |     "customerReference": false,
            |     "earningsNotTaxableUK": 1999.99
            |   }
            |}
          """.stripMargin
        )

        invalidJson.validate[RetrieveForeignResponse] shouldBe a[JsError]
      }
    }

    "written to JSON" should {
      "produce the expected JSON object" in {
        Json.toJson(fullRetrieveResponseBodyModel) shouldBe fullRetrieveForeignResponseJson
      }
    }
  }

}
