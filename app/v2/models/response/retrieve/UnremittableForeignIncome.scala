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

package v2.models.response.retrieve

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, OWrites, Reads}

case class UnremittableForeignIncome(countryCode: String, amountInForeignCurrency: BigDecimal, amountTaxPaid: Option[BigDecimal])

object UnremittableForeignIncome {

  implicit val reads: Reads[UnremittableForeignIncome] = (
    (JsPath \ "countryCode").read[String] and
      (JsPath \ "amountInForeignCurrency").read[BigDecimal] and
      (JsPath \ "amountTaxPaid").readNullable[BigDecimal]
  )(UnremittableForeignIncome.apply _)

  implicit val writes: OWrites[UnremittableForeignIncome] = Json.writes[UnremittableForeignIncome]
}
