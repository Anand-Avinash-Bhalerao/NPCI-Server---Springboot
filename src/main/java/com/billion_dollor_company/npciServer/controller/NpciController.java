package com.billion_dollor_company.npciServer.controller;

import com.billion_dollor_company.npciServer.payloads.TransactionRequestDTO;
import com.billion_dollor_company.npciServer.payloads.TransactionResponseDTO;
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

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponseDTO> initiateTransaction(@RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO responseDTO = npciService.initiateTransaction(request);

        if (responseDTO.getStatus().equals(Constants.Transaction.Status.FAILED)) {
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

}
