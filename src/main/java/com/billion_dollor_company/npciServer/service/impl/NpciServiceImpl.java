package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.service.interfaces.CryptographyService;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NpciServiceImpl implements NpciService {

    private final BankApiService bankApiService;

    private final CryptographyService cryptographyService;

    @Autowired
    public NpciServiceImpl(BankApiService bankApiService, CryptographyService cryptographyService) {
        this.bankApiService = bankApiService;
        this.cryptographyService = cryptographyService;
    }

    @Override
    public BalanceResDTO getAccountBalance(BalanceReqDTO requestInfo) {
        // Decrypt the password with NPCI private key.
        String encryptedPassword = requestInfo.getEncryptedPassword();

        // Decrypt and re encrypt the password with bank public key.
        encryptedPassword = cryptographyService.decryptAndReEncryptPW(encryptedPassword);

        // reuse the same object but now with changed encryptedPassword.
        requestInfo.setEncryptedPassword(encryptedPassword);

        // Forward the req to bank and send back the res received
        return bankApiService.getAccountBalance(requestInfo);
    }

    @Override
    public TransactionResDTO initiateTransaction(TransactionReqDTO requestInfo) {
        // Get the encrypted password from the request.
        String encryptedPassword = requestInfo.getEncryptedPassword();

        // Decrypt and re encrypt the password with bank public key.
        encryptedPassword = cryptographyService.decryptAndReEncryptPW(encryptedPassword);

        // reuse the same object. because why not.
        requestInfo.setEncryptedPassword(encryptedPassword);

        // Forward the req to bank and send back the res received
        return bankApiService.initiateTransaction(requestInfo);
    }


}
