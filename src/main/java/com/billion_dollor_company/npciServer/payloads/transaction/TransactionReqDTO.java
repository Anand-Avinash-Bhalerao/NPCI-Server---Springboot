package com.billion_dollor_company.npciServer.payloads.transaction;

import lombok.Data;

@Data
public class TransactionReqDTO {
    private String payeeFullName;
    private String amountToTransfer;
    private String payeeAccountNo;
    private String payeeBankName;
    private String payerFullName;
    private String payerAccountNo;
    private String payerBankName;
    private String payerUpiID;
    private String encryptedPassword;
    private String payeeUpiID;
}