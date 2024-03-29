package com.billion_dollor_company.npciServer.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Base64;

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
