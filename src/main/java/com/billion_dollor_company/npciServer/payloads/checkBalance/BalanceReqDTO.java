package com.billion_dollor_company.npciServer.payloads.checkBalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceReqDTO {
    private String upiID;
    private String encryptedPassword;
}
