package com.billion_dollor_company.npciServer.payloads;

import lombok.Data;

@Data
public class TransactionRequestDTO {
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