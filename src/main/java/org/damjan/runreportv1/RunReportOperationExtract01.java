package org.damjan.runreportv1;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RunReportOperationExtract01 {
    public static void main(String[] args) throws Exception {
        String wsdlUrl = "https://fa-euth-dev52-saasfademo1.ds-fa.oraclepdemos.com/xmlpserver/services/ExternalReportWSSService?wsdl";
        Document wsdlDocument = loadWsdl(wsdlUrl);

        // Extract operation: runReport
        NodeList operationNodes = wsdlDocument.getElementsByTagName("wsdl:operation");
        String operationName = null;
        for (int i = 0; i < operationNodes.getLength(); i++) {
            if (operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent().equals("runReport")) {
                operationName = operationNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
                break;
            }
        }

        // Extract all URIs from wsp:PolicyReference elements
        NodeList policyNodes = wsdlDocument.getElementsByTagName("wsp:PolicyReference");
        List<String> uris = new ArrayList<>();
        for (int i = 0; i < policyNodes.getLength(); i++) {
            String uri = policyNodes.item(i).getAttributes().getNamedItem("URI").getTextContent();
            uris.add(uri);
        }

        // Output extracted values
        System.out.println("Operation name: " + operationName);
        System.out.println("Extracted URIs:");
        for (String uri : uris) {
            System.out.println(uri);
        }
    }

    private static Document loadWsdl(String wsdlUrl) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL url = new URL(wsdlUrl);
        return builder.parse(url.openStream());
    }
}
