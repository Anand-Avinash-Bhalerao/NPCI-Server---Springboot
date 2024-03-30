package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        try {
            return restTemplate.postForEntity(bankServerURL, requestInfo, TransactionResponseDTO.class).getBody();
        } catch (HttpClientErrorException exception) {
            return exception.getResponseBodyAs(TransactionResponseDTO.class);
        }
    }
}
