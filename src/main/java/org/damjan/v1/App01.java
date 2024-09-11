package org.damjan.v1;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public class App01 {
    public static void main(String[] args) throws Exception {
        String wsdlUrl = "https://fa-euth-dev52-saasfademo1.ds-fa.oraclepdemos.com/xmlpserver/services/ExternalReportWSSService?wsdl";
        Document wsdlDocument = loadWsdl(wsdlUrl);

        // Extract service name
        NodeList serviceNodes = wsdlDocument.getElementsByTagName("wsdl:service");
        String serviceName = serviceNodes.item(0).getAttributes().getNamedItem("name").getTextContent();

        // Extract endpoint
        NodeList portNodes = wsdlDocument.getElementsByTagName("soap12:address");
        //NodeList portNodes = wsdlDocument.getElementsByTagNameNS("*","address");
        System.out.println(portNodes.getLength());
        if (portNodes.item(0) != null) {
            String endpoint = portNodes.item(0).getAttributes().getNamedItem("location").getTextContent();
            System.out.println(endpoint);
        }

        // Extract operations
        NodeList operationNodes = wsdlDocument.getElementsByTagName("wsdl:operation");
        for (int i = 0; i < operationNodes.getLength(); i++) {
            String operationName = operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            System.out.println(operationName);
        }

        System.out.println("Service name: " + serviceName);
    }

    private static Document loadWsdl(String wsdlUrl) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL url = new URL(wsdlUrl);
        return builder.parse(url.openStream());
    }
}
