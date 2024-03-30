package com.billion_dollor_company.npciServer.service.interfaces;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;

public interface NpciService {
    TransactionResDTO initiateTransaction(TransactionReqDTO requestInfo);

    BalanceResDTO getAccountBalance(BalanceReqDTO requestInfo);
}
