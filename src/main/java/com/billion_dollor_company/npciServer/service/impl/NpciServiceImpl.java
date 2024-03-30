package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;
import com.billion_dollor_company.npciServer.service.interfaces.BankApiService;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NpciServiceImpl implements NpciService {

    private final BankApiService bankApiService;

    @Autowired
    public NpciServiceImpl(BankApiService bankApiService) {
        this.bankApiService = bankApiService;
    }

    @Override
    public TransactionResponseDTO initiateTransaction(TransactionRequestDTO requestInfo) {

        // Decrypt the password with NPCI private key.
        String encryptedPassword = requestInfo.getEncryptedPassword();
        DecryptionManager decryptionManager = new DecryptionManager(Constants.Keys.NPCI_PRIVATE_KEY, "NPCI private key");
        String originalPassword = decryptionManager.getDecryptedMessage(encryptedPassword);
        System.out.println("At npci, the decrypted password is: " + originalPassword);
        // now encrypt the password with Bank's public key.
        EncryptionManager encryptionManager = new EncryptionManager(Constants.Keys.BANK_PUBLIC_KEY, "Bank's private key");
        encryptedPassword = encryptionManager.getEncryptedMessage(originalPassword);

        requestInfo.setEncryptedPassword(encryptedPassword);

        TransactionResponseDTO responseFromBank = bankApiService.initiateTransaction(requestInfo);
        System.out.println("The response from bank is: " + responseFromBank);
        return responseFromBank;
    }
}
