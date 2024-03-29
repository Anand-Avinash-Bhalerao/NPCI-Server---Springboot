package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.models.TransactionRequestInfo;
import com.billion_dollor_company.npciServer.models.TransactionResponseInfo;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NpciServiceImpl implements NpciService {

    @Autowired
    private BankApiService bankApiService;

    @Override
    public TransactionResponseInfo initiateTransaction(TransactionRequestInfo requestInfo) {

        // Decrypt the password with NPCI private key.
        String encryptedPassword = requestInfo.getEncryptedPassword();
        DecryptionManager decryptionManager = new DecryptionManager(Constants.Keys.NPCI_PRIVATE_KEY, "NPCI private key");
        String originalPassword = decryptionManager.getDecryptedMessage(encryptedPassword);
        System.out.println("At npci, the decrypted password is: " + originalPassword);
        // now encrypt the password with Bank's public key.
        EncryptionManager encryptionManager = new EncryptionManager(Constants.Keys.BANK_PUBLIC_KEY, "Bank's private key");
        encryptedPassword = encryptionManager.getEncryptedMessage(originalPassword);

        requestInfo.setEncryptedPassword(encryptedPassword);

        TransactionResponseInfo responseFromBank = bankApiService.initiateTransaction(requestInfo);
        System.out.println("The response from bank is: " + responseFromBank);
        return responseFromBank;
    }
}
