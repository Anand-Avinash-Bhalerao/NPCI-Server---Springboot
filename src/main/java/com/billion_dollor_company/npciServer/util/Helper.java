package com.billion_dollor_company.npciServer.util;

import com.billion_dollor_company.npciServer.models.TransactionRequest;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String encodedStr) {
        return Base64.getDecoder().decode(encodedStr);
    }

    public static <T> String getPrettyXML(String uglyXML, Class<T> valueType) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            T obj = xmlMapper.readValue(uglyXML, valueType);
            String pretty = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            return pretty;
        } catch (Exception ignored) {
        }
        return "";
    }
}
