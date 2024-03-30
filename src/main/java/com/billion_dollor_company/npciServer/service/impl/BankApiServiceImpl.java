package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankApiServiceImpl implements BankApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public BankApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TransactionResponseDTO initiateTransaction(TransactionRequestDTO requestInfo) {
        String bankServerURL = Constants.Servers.BankServer.getTransactionURL();
        // make the API call to Bank.
        return restTemplate.postForEntity(bankServerURL, requestInfo, TransactionResponseDTO.class).getBody();
    }
}
