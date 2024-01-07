package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.Helper;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/npci")
public class NpciController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/transaction")
    public String startTransaction(@RequestBody String xmlRequest) {

        // the request is in xml. we need to extract the required things.
        String[] toExtractArr = {Constants.TransactionRequest.AMOUNT, Constants.TransactionRequest.ENCRYPTED_STRING, Constants.TransactionRequest.BANK_NAME, Constants.TransactionRequest.PAYER_ID, Constants.TransactionRequest.PAYEE_ID};

        // in this requestMap, we extract all the tags from the request xml.
        HashMap<String, String> requestMap = Helper.xmlToMap(xmlRequest, toExtractArr);

        // in this responseMap, we store all the tags needed in for the response xml. Used in the very end.
        HashMap<String, String> responseMap = new HashMap<>();


        String bankName = requestMap.get(Constants.TransactionRequest.BANK_NAME);
        String payerID = requestMap.get(Constants.TransactionRequest.PAYER_ID);
        String payeeID = requestMap.get(Constants.TransactionRequest.PAYEE_ID);
        String amount = requestMap.get(Constants.TransactionRequest.AMOUNT);

        // This is the message sent from CL. Encrypted part is the password.
        String encryptedMessage = requestMap.get(Constants.TransactionRequest.ENCRYPTED_STRING);

        try {
            // TESTING: the encrypted text was : "My name is Anand Bhalerao"
            // it was encrypted using NPCI public key.
            // Decrypt the text received from the app which was encrypted by the CL using NPCI's public key.
            DecryptionManager decryptionManager = new DecryptionManager(Constants.Keys.NPCI_PRIVATE_KEY_STRING, "NPCI Private key");
            String decryptedWithNPCI = decryptionManager.getDecryptedMessage(encryptedMessage);

            System.out.println("Decrypted message at NPCI server is: " + decryptedWithNPCI);

            // Encrypt with the banks public key. Needs to be dynamic in future tho.
            EncryptionManager encryptionManager = new EncryptionManager(Constants.Keys.BANK_PUBLIC_KEY, "Bank Public key");

            // encrypted with bank public key.
            String encryptedWithBank = encryptionManager.getEncryptedMessage(decryptedWithNPCI);

            // replace the encrypted entry in the map only, don't need to create another map.
            requestMap.put(Constants.TransactionRequest.ENCRYPTED_STRING, encryptedWithBank);

            String toSend = Helper.mapToXML(requestMap, "request");
            System.out.println("XML to send is :\n" + toSend);

            String bankServerUrl = Constants.Servers.BankServer.getTransactionURL();
            ResponseEntity<String> response = restTemplate.postForEntity(bankServerUrl, toSend, String.class);


            if (response.getStatusCode().is2xxSuccessful()) {
                //we need to extract status from the response.
                String status = Helper.extractFromXML(response.getBody(), "status");
                if (status == null) status = "failed";
                responseMap.put("status", status);
            } else {
                responseMap.put("status", "failed");
            }
        } catch (Exception e) {
            responseMap.put("status", "failed");
            System.out.println(e.getMessage());
        }
        return Helper.mapToXML(responseMap, "response");
    }
}
