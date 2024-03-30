package com.billion_dollor_company.npciServer.payloads;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "TransactionResponse")
public class TransactionResponseDTO {
    private String status;
    private String message;
}

