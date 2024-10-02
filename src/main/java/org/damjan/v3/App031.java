package org.damjan.v3;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App031 {
        // Main method is no longer needed in SnapLogic context, but keeping for testing purposes
        public static void main(String[] args) throws Exception {
            String wsdlUrl = "https://fa-euth-dev52-saasfademo1.ds-fa.oraclepdemos.com/xmlpserver/services/ExternalReportWSSService?wsdl";
            Document wsdlDocument = loadWsdl(wsdlUrl);

            // Extract service name
            String serviceName = extractServiceName(wsdlDocument).toString();

            // Extract endpoints
            List<String> endpoints = extractEndpoints(wsdlDocument);
            System.out.println("Endpoints: " + endpoints);

            // Extract specific operation
            List<String> operations = extractSpecificOperations(wsdlDocument, serviceName);
            System.out.println("Operations: " + operations);
        }

        // Refactor to return a List of specific operations
        public static List<String> extractSpecificOperations(Document wsdlDocument, String serviceName) {
            List<String> operationsList = new ArrayList<>();
            NodeList operationNodes = wsdlDocument.getElementsByTagName("wsdl:operation");

            for (int i = 0; i < operationNodes.getLength(); i++) {
                String operationName = operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
                operationsList.add(operationName);
            }

            return operationsList;
        }

        // Refactor to return a List of endpoints
        public static List<String> extractEndpoints(Document wsdlDocument) {
            List<String> endpointsList = new ArrayList<>();
            NodeList portNodes = wsdlDocument.getElementsByTagName("soap12:address");

            for (int i = 0; i < portNodes.getLength(); i++) {
                String endpoint = portNodes.item(i).getAttributes().getNamedItem("location").getTextContent();
                endpointsList.add(endpoint);
            }

            return endpointsList;
        }

        // Refactor to return a List containing the service name
        public static List<String> extractServiceName(Document wsdlDocument) {
            List<String> serviceNamesList = new ArrayList<>();
            NodeList serviceNodes = wsdlDocument.getElementsByTagName("wsdl:service");
            String serviceName = serviceNodes.item(0).getAttributes().getNamedItem("name").getTextContent();
            serviceNamesList.add(serviceName);
            return serviceNamesList;
        }

        // Method to load WSDL as before
        public static Document loadWsdl(String wsdlUrl) throws Exception {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL url = new URL(wsdlUrl);
            return builder.parse(url.openStream());
        }
    }

