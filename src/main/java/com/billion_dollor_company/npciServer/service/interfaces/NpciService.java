package com.billion_dollor_company.npciServer.service.interfaces;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.fetchKeys.FetchKeysReqDTO;
import com.billion_dollor_company.npciServer.payloads.fetchKeys.FetchKeysResDTO;
import com.billion_dollor_company.npciServer.payloads.registration.RegistrationReqDTO;
import com.billion_dollor_company.npciServer.payloads.registration.RegistrationResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;

public interface NpciService {
    TransactionResDTO initiateTransaction(TransactionReqDTO requestInfo);

    BalanceResDTO getAccountBalance(BalanceReqDTO requestInfo);

    RegistrationResDTO registration(RegistrationReqDTO requestInfo);

    FetchKeysResDTO fetchKeys();
}
