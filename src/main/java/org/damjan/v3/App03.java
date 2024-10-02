package org.damjan.v3;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public class App03 {
    public static void main(String[] args) throws Exception {
        String wsdlUrl = "https://fa-euth-dev52-saasfademo1.ds-fa.oraclepdemos.com/xmlpserver/services/ExternalReportWSSService?wsdl";
        Document wsdlDocument = loadWsdl(wsdlUrl);

        // Extract service name
        String serviceName = extractServiceName(wsdlDocument);

        // Extract endpoints
        extractEndpoints(wsdlDocument);

        // Extract specific operation
        extractSpecificOperation(wsdlDocument, serviceName);
    }

    private static void extractSpecificOperation(Document wsdlDocument, String serviceName) {
        String targetOperation = null;
        NodeList operationNodes = wsdlDocument.getElementsByTagName("wsdl:operation");

        boolean operationFound = false;
        for (int i = 0; i < operationNodes.getLength(); i++) {
            String operationName = operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            System.out.println(operationName);

            // Check if it's the specific operation you're looking for
            if (operationName.equals(targetOperation)) {
                System.out.println(operationName);
                operationFound = true;
                // Perform any additional actions you want here
                break; // Stop after finding the specific operation
            }
        }

        if (!operationFound) {
            System.out.println("Specific operation not found.");
        }

        System.out.println(serviceName);
    }

    private static void extractEndpoints(Document wsdlDocument) {
        NodeList portNodes = wsdlDocument.getElementsByTagName("soap12:address");
        System.out.println(portNodes.getLength());

        for (int i = 0; i < portNodes.getLength(); i++) {
            String endpoint = portNodes.item(i).getAttributes().getNamedItem("location").getTextContent();
            System.out.println(endpoint);
        }
    }

    private static String extractServiceName(Document wsdlDocument) {
        NodeList serviceNodes = wsdlDocument.getElementsByTagName("wsdl:service");
        String serviceName = serviceNodes.item(0).getAttributes().getNamedItem("name").getTextContent();
        return serviceName;
    }

    private static Document loadWsdl(String wsdlUrl) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL url = new URL(wsdlUrl);
        return builder.parse(url.openStream());
    }
}
