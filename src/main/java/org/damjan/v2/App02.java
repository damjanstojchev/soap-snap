package org.damjan.v2;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public class App02 {
    public static void main(String[] args) throws Exception {
        String wsdlUrl = "https://fa-euth-dev52-saasfademo1.ds-fa.oraclepdemos.com/xmlpserver/services/ExternalReportWSSService?wsdl";
        Document wsdlDocument = loadWsdl(wsdlUrl);

        // Extract service name
        NodeList serviceNodes = wsdlDocument.getElementsByTagName("wsdl:service");
        String serviceName = serviceNodes.item(0).getAttributes().getNamedItem("name").getTextContent();

        // Extract endpoints
        NodeList portNodes = wsdlDocument.getElementsByTagName("soap12:address");
        System.out.println("Number of endpoints: " + portNodes.getLength());

        for (int i = 0; i < portNodes.getLength(); i++) {
            String endpoint = portNodes.item(i).getAttributes().getNamedItem("location").getTextContent();
            System.out.println("Endpoint: " + endpoint);
        }

        // Extract specific operation
        String targetOperation = "runReport"; // Replace with the operation you're looking for
        NodeList operationNodes = wsdlDocument.getElementsByTagName("wsdl:operation");

        boolean operationFound = false;
        for (int i = 0; i < operationNodes.getLength(); i++) {
            String operationName = operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            System.out.println("Operation: " + operationName);

            // Check if it's the specific operation you're looking for
            if (operationName.equals(targetOperation)) {
                System.out.println("Specific operation found: " + operationName);
                operationFound = true;
                // Perform any additional actions you want here
                break; // Stop after finding the specific operation
            }
        }

        if (!operationFound) {
            System.out.println("Specific operation not found.");
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
