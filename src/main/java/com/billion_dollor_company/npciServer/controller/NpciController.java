package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.fetchKeys.FetchKeysReqDTO;
import com.billion_dollor_company.npciServer.payloads.fetchKeys.FetchKeysResDTO;
import com.billion_dollor_company.npciServer.payloads.registration.RegistrationReqDTO;
import com.billion_dollor_company.npciServer.payloads.registration.RegistrationResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.util.Constants;
import com.billion_dollor_company.npciServer.util.MessagePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// NPCI only send the response back in XML. So make the produces as XML and create the ResponseEntity in this class only.
// If you directly return ResponseEntity from Service, it somehow sends it in JSON. IDK why.
@RestController
@RequestMapping(value = "/npci", produces = {"application/xml"})
public class NpciController {

    private final NpciService npciService;

    @Autowired
    public NpciController(NpciService npciService) {
        this.npciService = npciService;
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<BalanceResDTO> getAccountBalance(@RequestBody BalanceReqDTO request) {

        MessagePrinter.printMessage(Constants.MessagePrinter.Server.NPCI, Constants.MessagePrinter.MethodType.CheckBalance, request);

        // getAccountBalance forwards the req to bank by decrypting and re-encrypting the password.
        BalanceResDTO responseDTO = npciService.getAccountBalance(request);

        // If the response status code was BAD_REQUEST then send Failed, 400 otherwise Success 200.
        if (responseDTO.getStatus().equals(Constants.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping("/transaction")
    public ResponseEntity<TransactionResDTO> initiateTransaction(@RequestBody TransactionReqDTO request) {

        MessagePrinter.printMessage(Constants.MessagePrinter.Server.NPCI, Constants.MessagePrinter.MethodType.InitiateTransaction, request);

        // initiateTransaction forwards the req to bank by decrypting and re-encrypting the password.
        TransactionResDTO responseDTO = npciService.initiateTransaction(request);

        // If the response status code was BAD_REQUEST then send Failed, 400 otherwise Success 200.
        if (responseDTO.getStatus().equals(Constants.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResDTO> register(@RequestBody RegistrationReqDTO request) {

        MessagePrinter.printMessage(Constants.MessagePrinter.Server.NPCI, Constants.MessagePrinter.MethodType.Register, request);

        RegistrationResDTO responseDTO = npciService.registration(request);

        // If the response status code was BAD_REQUEST then send Failed, 400 otherwise Success 200.
        if (responseDTO.getStatus().equals(Constants.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/fetchKeys")
    public ResponseEntity<FetchKeysResDTO> fetchKeys() {

        MessagePrinter.printMessage(Constants.MessagePrinter.Server.NPCI, Constants.MessagePrinter.MethodType.FetchKeys, new Object());

        FetchKeysResDTO responseDTO = npciService.fetchKeys();

        // If the response status code was BAD_REQUEST then send Failed, 400 otherwise Success 200.
        if (responseDTO.getStatus().equals(Constants.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


}
