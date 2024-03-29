package com.billion_dollor_company.npciServer.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "transactionResponse")
public class TransactionResponseInfo {
    private String status;
    private String message;
}

