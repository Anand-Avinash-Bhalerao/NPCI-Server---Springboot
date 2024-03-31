package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BalanceResDTO getAccountBalance(BalanceReqDTO requestInfo) {
        String checkBalanceURL = Constants.Servers.BankServer.getCheckBalanceURL();

        try {

            // make the API call to Bank.
            return restTemplate.postForEntity(checkBalanceURL, requestInfo, BalanceResDTO.class).getBody();

        } catch (HttpClientErrorException exception) {

            // even if the status code was anything other 200, the body contains an instance of BalanceResDTO
            return exception.getResponseBodyAs(BalanceResDTO.class);
        }
    }

    @Override
    public TransactionResDTO initiateTransaction(TransactionReqDTO requestInfo) {
        String transactionURL = Constants.Servers.BankServer.getTransactionURL();

        try {
            // make the API call to Bank.
            return restTemplate.postForEntity(transactionURL, requestInfo, TransactionResDTO.class).getBody();

        } catch (HttpClientErrorException exception) {

            // even if the status code was anything other 200, the body contains an instance of BalanceResDTO
            return exception.getResponseBodyAs(TransactionResDTO.class);
        }
    }


}
