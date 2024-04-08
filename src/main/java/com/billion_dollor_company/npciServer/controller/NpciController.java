package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.models.ListKeysInfo;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.listKeys.ListKeysReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.service.interfaces.PublicKeysService;
import com.billion_dollor_company.npciServer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// NPCI only send the response back in XML. So make the produces as XML and create the ResponseEntity in this class only.
// If you directly return ResponseEntity from Service, it somehow sends it in JSON. IDK why.
@RestController
@RequestMapping(value = "/npci", produces = {"application/xml"})
public class NpciController {

    private final NpciService npciService;

    private final PublicKeysService publicKeysService;

    @Autowired
    public NpciController(NpciService npciService, PublicKeysService publicKeysService) {
        this.npciService = npciService;
        this.publicKeysService = publicKeysService;
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<BalanceResDTO> getAccountBalance(@RequestBody BalanceReqDTO request) {

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
        // initiateTransaction forwards the req to bank by decrypting and re-encrypting the password.
        TransactionResDTO responseDTO = npciService.initiateTransaction(request);

        // If the response status code was BAD_REQUEST then send Failed, 400 otherwise Success 200.
        if (responseDTO.getStatus().equals(Constants.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getListKeys")
    public List<ListKeysReqDTO> getListKeys() {

        System.out.println("In NPCI Contoller");
        List<ListKeysReqDTO> responseDTO= publicKeysService.getListKeys();
        return responseDTO;


    }

}
