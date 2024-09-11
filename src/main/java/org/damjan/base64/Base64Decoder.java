package org.damjan.base64;

import java.util.Base64;

public class Base64Decoder {
    public static void main(String[] args) {
        String encodedString = "77u/UFBFUlNPTl9OVU1CRVIsUEVSU09OX05VTUJFUixGVUxMX05BTUUsRU1BSUxfQUREUkVTUwo3LDcsIkNvb2ssIEFsYW4iLEFMQU4uQ09PS19ldXRoLWRldjMxQG9yYWNsZXBkZW1vcy5jb20K";

        // Decode encodedString
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println(decodedString);
    }
}
