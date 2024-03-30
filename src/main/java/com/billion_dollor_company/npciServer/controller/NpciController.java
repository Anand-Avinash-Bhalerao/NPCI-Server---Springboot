package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceReqDTO;
import com.billion_dollor_company.npciServer.payloads.checkBalance.BalanceResDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionReqDTO;
import com.billion_dollor_company.npciServer.payloads.transaction.TransactionResDTO;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import com.billion_dollor_company.npciServer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("The request is "+request);
        BalanceResDTO responseDTO = npciService.getAccountBalance(request);
        if (responseDTO.getStatus().equals(Constants.Transaction.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping("/transaction")
    public ResponseEntity<TransactionResDTO> initiateTransaction(@RequestBody TransactionReqDTO request) {
        TransactionResDTO responseDTO = npciService.initiateTransaction(request);

        if (responseDTO.getStatus().equals(Constants.Transaction.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
