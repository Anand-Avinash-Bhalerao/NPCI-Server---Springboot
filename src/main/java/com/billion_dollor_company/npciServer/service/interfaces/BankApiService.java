package com.billion_dollor_company.npciServer.service.interfaces;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;

public interface BankApiService {
    public TransactionResponseDTO initiateTransaction(TransactionRequestDTO requestInfo);
}
