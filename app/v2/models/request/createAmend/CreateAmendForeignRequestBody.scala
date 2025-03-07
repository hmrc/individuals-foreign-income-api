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

package v2.models.request.createAmend

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, OWrites, Reads}
import v2.utils.JsonUtils

case class CreateAmendForeignRequestBody(foreignEarnings: Option[ForeignEarnings],
                                         unremittableForeignIncome: Option[Seq[UnremittableForeignIncomeItem]])

object CreateAmendForeignRequestBody extends JsonUtils {
  val empty: CreateAmendForeignRequestBody = CreateAmendForeignRequestBody(None, None)

  implicit val reads: Reads[CreateAmendForeignRequestBody] = (
    (JsPath \ "foreignEarnings").readNullable[ForeignEarnings] and
      (JsPath \ "unremittableForeignIncome").readNullable[Seq[UnremittableForeignIncomeItem]].mapEmptySeqToNone
  )(CreateAmendForeignRequestBody.apply _)

  implicit val writes: OWrites[CreateAmendForeignRequestBody] = (
    (JsPath \ "foreignEarnings").writeNullable[ForeignEarnings] and
      (JsPath \ "unremittableForeignIncome").writeNullable[Seq[UnremittableForeignIncomeItem]]
  )(unlift(CreateAmendForeignRequestBody.unapply))

}
