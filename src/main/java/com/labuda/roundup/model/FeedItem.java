package com.labuda.roundup.model;

public class FeedItem {

    private String feedItemUid;
    private String categoryUid;
    private CurrencyAndAmount amount;
    private CurrencyAndAmount sourceAmount;
    private String direction;
    private String updatedAt;
    private String transactionTime;
    private String settlementTime;
    private String retryAllocationUntilTime;
    private String source;
    private String sourceSubType;
    private String status;
    private String counterPartyType;
    private String counterPartyUid;
    private String counterPartyName;
    private String counterPartySubEntityUid;
    private String counterPartySubEntityIdentifier;
    private String counterPartySubEntitySubIdentifier;
    private String exchangeRate;
    private String totalFees;
    private String reference;
    private String country;
    private String spendingCategory;
    private String userNote;
    private String roundUp;

    @Override
    public String toString() {
        return "FeedItem{" +
                "feedItemUid='" + feedItemUid + '\'' +
                ", categoryUid='" + categoryUid + '\'' +
                ", amount=" + amount +
                ", sourceAmount=" + sourceAmount +
                ", direction='" + direction + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", settlementTime='" + settlementTime + '\'' +
                ", retryAllocationUntilTime='" + retryAllocationUntilTime + '\'' +
                ", source='" + source + '\'' +
                ", sourceSubType='" + sourceSubType + '\'' +
                ", status='" + status + '\'' +
                ", counterPartyType='" + counterPartyType + '\'' +
                ", counterPartyUid='" + counterPartyUid + '\'' +
                ", counterPartyName='" + counterPartyName + '\'' +
                ", counterPartySubEntityUid='" + counterPartySubEntityUid + '\'' +
                ", counterPartySubEntityIdentifier='" + counterPartySubEntityIdentifier + '\'' +
                ", counterPartySubEntitySubIdentifier='" + counterPartySubEntitySubIdentifier + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", totalFees='" + totalFees + '\'' +
                ", reference='" + reference + '\'' +
                ", country='" + country + '\'' +
                ", spendingCategory='" + spendingCategory + '\'' +
                ", userNote='" + userNote + '\'' +
                ", roundUp='" + roundUp + '\'' +
                '}';
    }

    public String getFeedItemUid() {
        return feedItemUid;
    }

    public void setFeedItemUid(String feedItemUid) {
        this.feedItemUid = feedItemUid;
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(String categoryUid) {
        this.categoryUid = categoryUid;
    }

    public CurrencyAndAmount getAmount() {
        return amount;
    }

    public void setAmount(CurrencyAndAmount amount) {
        this.amount = amount;
    }

    public CurrencyAndAmount getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(CurrencyAndAmount sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getRetryAllocationUntilTime() {
        return retryAllocationUntilTime;
    }

    public void setRetryAllocationUntilTime(String retryAllocationUntilTime) {
        this.retryAllocationUntilTime = retryAllocationUntilTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceSubType() {
        return sourceSubType;
    }

    public void setSourceSubType(String sourceSubType) {
        this.sourceSubType = sourceSubType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCounterPartyType() {
        return counterPartyType;
    }

    public void setCounterPartyType(String counterPartyType) {
        this.counterPartyType = counterPartyType;
    }

    public String getCounterPartyUid() {
        return counterPartyUid;
    }

    public void setCounterPartyUid(String counterPartyUid) {
        this.counterPartyUid = counterPartyUid;
    }

    public String getCounterPartyName() {
        return counterPartyName;
    }

    public void setCounterPartyName(String counterPartyName) {
        this.counterPartyName = counterPartyName;
    }

    public String getCounterPartySubEntityUid() {
        return counterPartySubEntityUid;
    }

    public void setCounterPartySubEntityUid(String counterPartySubEntityUid) {
        this.counterPartySubEntityUid = counterPartySubEntityUid;
    }

    public String getCounterPartySubEntityIdentifier() {
        return counterPartySubEntityIdentifier;
    }

    public void setCounterPartySubEntityIdentifier(String counterPartySubEntityIdentifier) {
        this.counterPartySubEntityIdentifier = counterPartySubEntityIdentifier;
    }

    public String getCounterPartySubEntitySubIdentifier() {
        return counterPartySubEntitySubIdentifier;
    }

    public void setCounterPartySubEntitySubIdentifier(String counterPartySubEntitySubIdentifier) {
        this.counterPartySubEntitySubIdentifier = counterPartySubEntitySubIdentifier;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(String totalFees) {
        this.totalFees = totalFees;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSpendingCategory() {
        return spendingCategory;
    }

    public void setSpendingCategory(String spendingCategory) {
        this.spendingCategory = spendingCategory;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getRoundUp() {
        return roundUp;
    }

    public void setRoundUp(String roundUp) {
        this.roundUp = roundUp;
    }

    public enum Source {
        CASH_DEPOSIT, CASH_DEPOSIT_CHARGE, CASH_WITHDRAWAL, CASH_WITHDRAWAL_CHARGE,
        CHAPS, CHEQUE, CICS_CHEQUE, CURRENCY_CLOUD, DIRECT_CREDIT,
        DIRECT_DEBIT, DIRECT_DEBIT_DISPUTE, INTERNAL_TRANSFER, MASTER_CARD, MASTERCARD_MONEYSEND,
        MASTERCARD_CHARGEBACK, FASTER_PAYMENTS_IN, FASTER_PAYMENTS_OUT, FASTER_PAYMENTS_REVERSAL,
        STRIPE_FUNDING, INTEREST_PAYMENT, NOSTRO_DEPOSIT, OVERDRAFT, OVERDRAFT_INTEREST_WAIVED,
        FASTER_PAYMENTS_REFUND, STARLING_PAY_STRIPE, ON_US_PAY_ME, LOAN_PRINCIPAL_PAYMENT, LOAN_REPAYMENT,
        LOAN_OVERPAYMENT, LOAN_LATE_PAYMENT, SEPA_CREDIT_TRANSFER, SEPA_DIRECT_DEBIT, TARGET2_CUSTOMER_PAYMENT,
        FX_TRANSFER, ISS_PAYMENT, STARLING_PAYMENT, SUBSCRIPTION_CHARGE, OVERDRAFT_FEE
    }

    public enum Status {
        UPCOMING, PENDING, REVERSED, SETTLED, DECLINED, REFUNDED, RETRYING, ACCOUNT_CHECK
    }

    public enum Direction {IN, OUT}
}
