package com.billion_dollor_company.npciServer.service.impl;

import com.billion_dollor_company.npciServer.service.interfaces.CryptographyService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
import org.springframework.stereotype.Service;

@Service
public class CryptographyServiceImpl implements CryptographyService {

    @Override
    public String decryptAndReEncryptPW(String encryptedPassword) {

        DecryptionManager decryptionManager = new DecryptionManager(Constants.Keys.NPCI_PRIVATE_KEY, "NPCI private key");

        // This is the password entered by the user. example -123456
        String originalPassword = decryptionManager.getDecryptedMessage(encryptedPassword);
        System.out.println("The orignal password is " + originalPassword);
        // now encrypt the password with Bank's public key.
        EncryptionManager encryptionManager = new EncryptionManager(Constants.Keys.BANK_PUBLIC_KEY, "Bank's private key");

        // This is the password encrypted with the bank's public key.
        return encryptionManager.getEncryptedMessage(originalPassword);
    }
}
