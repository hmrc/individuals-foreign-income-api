/*
 * Copyright 2025 HM Revenue & Customs
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

package v2.models.domain

import play.api.libs.json.*
import shared.utils.enums.Enums

enum MtdSourceEnum(val toDesViewString: String) {
  case hmrcHeld extends MtdSourceEnum("HMRC-HELD")
  case user     extends MtdSourceEnum("CUSTOMER")
  case latest   extends MtdSourceEnum("LATEST")
}

object MtdSourceEnum {
  given Format[MtdSourceEnum] = Enums.format(values)

  val parser: PartialFunction[String, MtdSourceEnum] = Enums.parser(values)
}
