package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.models.TransactionRequestInfo;
import com.billion_dollor_company.npciServer.models.TransactionResponseInfo;
import com.billion_dollor_company.npciServer.service.interfaces.NpciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/npci", produces = {"application/xml"})
public class NpciController {

    @Autowired
    private NpciService npciService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponseInfo> initiateTransaction(@RequestBody TransactionRequestInfo request) {
        return ResponseEntity.ok(npciService.initiateTransaction(request));
    }

}
