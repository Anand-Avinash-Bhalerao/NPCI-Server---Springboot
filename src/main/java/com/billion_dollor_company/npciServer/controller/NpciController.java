package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.models.ResponseStatusInfo;
import com.billion_dollor_company.npciServer.models.TransactionRequest;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.Helper;
import com.billion_dollor_company.npciServer.util.cryptography.DecryptionManager;
import com.billion_dollor_company.npciServer.util.cryptography.EncryptionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/npci")
public class NpciController {

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping("/transaction")
    public ResponseEntity<String> startTransaction(@RequestBody String reqFromPSP) {
        System.out.println("\n______________________________________AT NPCI SERVER______________________________________\n");
        System.out.println("The XML request sent from PSP to NPCI Server is:\n" + Helper.getPrettyXML(reqFromPSP, TransactionRequest.class) + "\n");

        XmlMapper xmlMapper = new XmlMapper();
        TransactionRequest transactionInfo = null;
        HttpStatus responseStatusCode = HttpStatus.OK;
        ResponseStatusInfo responseForPSPObj = new ResponseStatusInfo();

        try {
            transactionInfo = xmlMapper.readValue(reqFromPSP, TransactionRequest.class);
        } catch (Exception e) {

            System.out.println("An error occurred while converting request from XML");
            e.printStackTrace();

            responseForPSPObj.setStatus(Constants.Values.ERROR_WHILE_CONVERSION);

            String errorResponseForPSPBody = "";
            try {
                errorResponseForPSPBody = xmlMapper.writeValueAsString(responseForPSPObj);
            } catch (Exception ignored) {
            }

            ResponseEntity<String> errorResponse = new ResponseEntity<>(errorResponseForPSPBody, HttpStatus.OK);
            return errorResponse;
        }

        String responseForPSPStr;
        HttpStatus responseStatus = HttpStatus.OK;
        try {
            // it was encrypted using NPCI public key.
            // Decrypt the text received from the app which was encrypted by the CL using NPCI's public key.
            DecryptionManager decryptionManager = new DecryptionManager(Constants.Keys.NPCI_PRIVATE_KEY, "NPCI Private key");
            String decryptedWithNPCI = decryptionManager.getDecryptedMessage(transactionInfo.getEncryptedPassword());

            System.out.println("Decrypted password at NPCI server is: " + decryptedWithNPCI + "\n");

            // Encrypt with the banks public key. Needs to be dynamic in future tho.
            EncryptionManager encryptionManager = new EncryptionManager(Constants.Keys.BANK_PUBLIC_KEY, "Bank Public key");

            // encrypted with bank public key.
            String encryptedWithBank = encryptionManager.getEncryptedMessage(decryptedWithNPCI);
            transactionInfo.setEncryptedPassword(encryptedWithBank);

            String reqToSendToBank = xmlMapper.writer().withRootName("request").writeValueAsString(transactionInfo);
            System.out.println("The request sent from NPCI to bank in XML is:\n" + Helper.getPrettyXML(reqToSendToBank, TransactionRequest.class) + "\n");

            String bankServerUrl = Constants.Servers.BankServer.getTransactionURL();
            ResponseEntity<String> responseFromBank = restTemplate.postForEntity(bankServerUrl, reqToSendToBank, String.class);
//            System.out.println("The response received at NPCI from bank is: \n"+ Helper.getPrettyXML(responseFromBank.getBody(), ResponseStatusInfo.class));

            responseStatus = (HttpStatus) responseFromBank.getStatusCode();

            if (responseFromBank.getStatusCode().is4xxClientError()) {

            }

            System.out.println("The response received at NPCI from bank is: \n"+ responseFromBank);

            responseForPSPObj = xmlMapper.readValue(responseFromBank.getBody(), ResponseStatusInfo.class);
            responseForPSPStr = xmlMapper.writer().withRootName("response").writeValueAsString(responseForPSPObj);

        } catch (Exception e) {
            System.out.println("Some error occurec");
            responseForPSPStr = "";
            responseStatus = HttpStatus.OK;
            responseForPSPObj.setStatus(Constants.Values.SOME_ERROR_OCCURRED);
            try {
                responseForPSPStr = xmlMapper.writer().withRootName("response").writeValueAsString(responseForPSPObj);
            } catch (Exception ignored) {
            }
        }

        System.out.println("The response sent to PSP from NPCI in XML is \n" + Helper.getPrettyXML(responseForPSPStr, ResponseStatusInfo.class) + "\n\n");

        return new ResponseEntity<>(responseForPSPStr, responseStatus);
    }
}
