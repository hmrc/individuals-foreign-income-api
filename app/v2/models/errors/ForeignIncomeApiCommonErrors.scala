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

package v2.models.errors

import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR}
import shared.models.errors.MtdError

object AccountNameFormatError extends MtdError("FORMAT_ACCOUNT_NAME", "The provided account name is invalid", BAD_REQUEST)

object CustomerRefFormatError extends MtdError("FORMAT_CUSTOMER_REF", "The provided customer reference is invalid", BAD_REQUEST)
object EventFormatError       extends MtdError("FORMAT_EVENT", "The provided policy event is invalid", BAD_REQUEST)
object QOPSRefFormatError     extends MtdError("FORMAT_QOPS_REF", "The provided QOPS reference number is invalid", BAD_REQUEST)

object ClassOfSharesAwardedFormatError
    extends MtdError("FORMAT_CLASS_OF_SHARES_AWARDED", "The provided class of shares awarded is invalid", BAD_REQUEST)

object ClassOfSharesAcquiredFormatError
    extends MtdError("FORMAT_CLASS_OF_SHARES_ACQUIRED", "The provided class of shares acquired is invalid", BAD_REQUEST)

object SchemePlanTypeFormatError   extends MtdError("FORMAT_SCHEME_PLAN_TYPE", "The provided scheme plan type is invalid", BAD_REQUEST)
object PayrollIdFormatError        extends MtdError("FORMAT_PAYROLL_ID", "The provided payroll ID is invalid", BAD_REQUEST)
object StartDateFormatError        extends MtdError("FORMAT_START_DATE", "The provided start date is invalid", BAD_REQUEST)
object CessationDateFormatError    extends MtdError("FORMAT_CESSATION_DATE", "The provided cessation date is invalid", BAD_REQUEST)
object SourceFormatError           extends MtdError("FORMAT_SOURCE", "The provided source is invalid", BAD_REQUEST)
object AssetDescriptionFormatError extends MtdError("FORMAT_ASSET_DESCRIPTION", "The provided asset description is invalid", BAD_REQUEST)
object AssetTypeFormatError        extends MtdError("FORMAT_ASSET_TYPE", "The format of the assetType value is invalid", BAD_REQUEST)

object ClaimOrElectionCodesFormatError
    extends MtdError("FORMAT_CLAIM_OR_ELECTION_CODES", "The format of the claimOrElectionCodes value is invalid", BAD_REQUEST)

object SavingsAccountIdFormatError
    extends MtdError("FORMAT_SAVINGS_ACCOUNT_ID", "The format of the supplied savings account ID is not valid", BAD_REQUEST)

object DoubleTaxationArticleFormatError
    extends MtdError("FORMAT_DOUBLE_TAXATION_ARTICLE", "The provided double taxation article is invalid", BAD_REQUEST)

object DoubleTaxationTreatyFormatError
    extends MtdError("FORMAT_DOUBLE_TAXATION_TREATY", "The provided double taxation treaty is invalid", BAD_REQUEST)

object SF74RefFormatError      extends MtdError("FORMAT_SF74_REF", "The provided SF74 reference is invalid", BAD_REQUEST)
object EmployerNameFormatError extends MtdError("FORMAT_EMPLOYER_NAME", "The provided employer name is invalid", BAD_REQUEST)
object EmployerRefFormatError  extends MtdError("FORMAT_EMPLOYER_REF", "The provided employer ref is invalid", BAD_REQUEST)

object EmploymentIdFormatError    extends MtdError("FORMAT_EMPLOYMENT_ID", "The provided employment ID is invalid", BAD_REQUEST)
object PpdSubmissionIdFormatError extends MtdError("FORMAT_PPD_SUBMISSION_ID", "The provided ppdSubmissionId is invalid", BAD_REQUEST)

object RuleCustomEmploymentError extends MtdError("RULE_CUSTOM_EMPLOYMENT", "A custom employment cannot be ignored", BAD_REQUEST)

object RuleCustomEmploymentUnignoreError extends MtdError("RULE_CUSTOM_EMPLOYMENT", "A custom employment cannot be unignored", BAD_REQUEST)

object RuleLumpSumsError extends MtdError("RULE_LUMP_SUMS", "At least one child object is required when lumpSums are provided", BAD_REQUEST)

object RuleAmountGainLossError
    extends MtdError("RULE_AMOUNT_GAIN_LOSS", "Either amountOfGain or amountOfLoss, must be provided but not both", BAD_REQUEST)

object RuleLossesGreaterThanGainError
    extends MtdError(
      "RULE_LOSSES_GREATER_THAN_GAIN",
      "The total losses from this year or the previous year must not be greater than the gain",
      BAD_REQUEST)

object RuleGainLossError extends MtdError("RULE_GAIN_LOSS", "Only one of gain or loss values can be provided", BAD_REQUEST)

object RuleDisposalDateError
    extends MtdError("RULE_DISPOSAL_DATE", "The disposalDate must be in the specified tax year and no later than today's date", BAD_REQUEST)

object RuleDisposalDateErrorV2 extends MtdError("RULE_DISPOSAL_DATE", "The disposalDate must be within the specified tax year", BAD_REQUEST)

object RuleDisposalDateNotFutureError
    extends MtdError(
      "RULE_DISPOSAL_DATE_NOT_FUTURE",
      "The disposalDate must be in the specified tax year and no later than today's date",
      BAD_REQUEST)

object RuleAcquisitionDateError extends MtdError("RULE_ACQUISITION_DATE", "The acquisitionDate must not be later than disposalDate", BAD_REQUEST)

object RuleGainAfterReliefLossAfterReliefError
    extends MtdError("RULE_GAIN_AFTER_RELIEF_LOSS_AFTER_RELIEF", "Only one of gainAfterRelief or lossAfterRelief values can be provided", BAD_REQUEST)

object RuleCompletionDateBeforeDisposalDateError
    extends MtdError("RULE_COMPLETION_DATE_BEFORE_DISPOSAL_DATE", "The completionDate must not be earlier than the disposalDate", BAD_REQUEST)

object RuleAcquisitionDateAfterDisposalDateError
    extends MtdError("RULE_ACQUISITION_DATE_AFTER_DISPOSAL_DATE", "The acquisitionDate must not be later than disposalDate", BAD_REQUEST)

object RuleCompletionDateError
    extends MtdError(
      "RULE_COMPLETION_DATE",
      "The completionDate must be within the specific tax year and not in the future. If the specified tax year has not ended, the completionDate must be between 7th March and 5th April",
      BAD_REQUEST)

object RuleDuplicatedPpdSubmissionIdError
    extends MtdError("RULE_DUPLICATED_PPD_SUBMISSION_ID", "A provided ppdSubmissionId is duplicated", BAD_REQUEST) {

  def forDuplicatedIdAndPaths(id: String, paths: Seq[String]): MtdError =
    RuleDuplicatedPpdSubmissionIdError.copy(message = s"The ppdSubmissionId '$id' is duplicated", paths = Some(paths))

}

object RuleIncorrectDisposalTypeError
    extends MtdError("RULE_INCORRECT_DISPOSAL_TYPE", "A provided ppdSubmissionId is being used for the incorrect disposal type", BAD_REQUEST)

object RuleDeleteForbiddenError extends MtdError("RULE_DELETE_FORBIDDEN", "A deletion of an HMRC held employment is not permitted", BAD_REQUEST)

object RuleMaximumSavingsAccountsLimitError
    extends MtdError("RULE_MAXIMUM_SAVINGS_ACCOUNTS_LIMIT", "The 1000 savings account limit exceeded", BAD_REQUEST)

object RuleDuplicateAccountNameError extends MtdError("RULE_DUPLICATE_ACCOUNT_NAME", "Duplicate account name given for supplied NINO", BAD_REQUEST)

object RuleNotAllowedOffPayrollWorker
    extends MtdError("NOT_ALLOWED_OFF_PAYROLL_WORKER", "offPayrollWorker field is not allowed for tax years prior to 2023-24", BAD_REQUEST)

object RuleMissingOffPayrollWorker extends MtdError("MISSING_OFF_PAYROLL_WORKER", "The offPayrollWorker field was not provided", BAD_REQUEST)

object RuleUnalignedCessationTaxYear
    extends MtdError("RULE_UNALIGNED_CESSATION_TAX_YEAR", "The tax year provided must be the same as the tax year of income to be taxed", BAD_REQUEST)

object CountryCodeRuleError extends MtdError("RULE_COUNTRY_CODE", "The country code is not a valid ISO 3166-1 alpha-3 country code", BAD_REQUEST)

object RuleRequestCannotBeFulfilled
    extends MtdError("RULE_REQUEST_CANNOT_BE_FULFILLED", "Custom (will vary in production depending on the actual error)", 422)

object InternalError extends MtdError("INTERNAL_SERVER_ERROR", "An internal server error occurred", INTERNAL_SERVER_ERROR)

object RuleOutsideAmendmentWindowError extends MtdError("RULE_OUTSIDE_AMENDMENT_WINDOW", "You are outside the amendment window", BAD_REQUEST)
