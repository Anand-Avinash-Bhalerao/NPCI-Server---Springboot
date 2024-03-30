package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.service.interfaces.CryptographyService;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
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
    public TransactionResponseDTO initiateTransaction(TransactionRequestDTO requestInfo) {

        // Decrypt the password with NPCI private key.
        String encryptedPassword = requestInfo.getEncryptedPassword();

        // This is the encrypted password.
        encryptedPassword = cryptographyService.decryptAndReEncryptPW(encryptedPassword);

        requestInfo.setEncryptedPassword(encryptedPassword);
        return bankApiService.initiateTransaction(requestInfo);
    }
}
