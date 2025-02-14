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
import shared.models.domain.Timestamp
import v2.utils.JsonUtils

case class RetrieveForeignResponse(submittedOn: Timestamp,
                                   foreignEarnings: Option[ForeignEarnings],
                                   unremittableForeignIncome: Option[Seq[UnremittableForeignIncome]])

object RetrieveForeignResponse extends JsonUtils {

  implicit val reads: Reads[RetrieveForeignResponse] = (
    (JsPath \ "submittedOn").read[Timestamp] and
      (JsPath \ "foreignEarnings").readNullable[ForeignEarnings] and
      (JsPath \ "unremittableForeignIncome").readNullable[Seq[UnremittableForeignIncome]].mapEmptySeqToNone
  )(RetrieveForeignResponse.apply _)

  implicit val writes: OWrites[RetrieveForeignResponse] = Json.writes[RetrieveForeignResponse]
}
