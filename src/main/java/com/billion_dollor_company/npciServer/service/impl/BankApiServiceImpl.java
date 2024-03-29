package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.models.TransactionRequestInfo;
import com.billion_dollor_company.npciServer.models.TransactionResponseInfo;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankApiServiceImpl implements BankApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TransactionResponseInfo initiateTransaction(TransactionRequestInfo requestInfo) {

        String bankServerURL = Constants.Servers.BankServer.getTransactionURL();

        // make the API call to Bank.
        return restTemplate.postForEntity(bankServerURL, requestInfo, TransactionResponseInfo.class).getBody();
    }
}
