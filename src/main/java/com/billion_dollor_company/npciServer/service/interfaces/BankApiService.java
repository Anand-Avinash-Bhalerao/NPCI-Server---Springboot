package com.billion_dollor_company.npciServer.service.interfaces;

import com.billion_dollor_company.npciServer.models.TransactionRequestInfo;
import com.billion_dollor_company.npciServer.models.TransactionResponseInfo;
import org.springframework.http.ResponseEntity;

public interface BankApiService {
    public TransactionResponseInfo initiateTransaction(TransactionRequestInfo requestInfo);
}
