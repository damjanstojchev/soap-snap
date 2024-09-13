package org.damjan.runreportv2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;

public class RunReportV2 {
    public static void main(String[] args) {
        String url = "https://fa-euth-dev31-saasfademo1.ds-fa.oraclepdemos.com:443/xmlpserver/services/ExternalReportWSSService";
        String username = "SAAS.IMP1";
        String password = "welcome123";

        try {
            // Create the credentials provider
            BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(username, password)
            );

            // Create HTTP client with credentials provider
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();

            HttpPost httpPost = new HttpPost(url);

            // SOAP request body
            String soapEnvelope = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\""
                    + " xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\">"
                    + "    <soap:Header/>"
                    + "    <soap:Body>"
                    + "        <pub:runReport>"
                    + "            <pub:reportRequest>"
                    + "                <pub:attributeFormat>csv</pub:attributeFormat>"
                    + "                <pub:parameterNameValues>"
                    + "                    <pub:item>"
                    + "                        <pub:name>pPERSON_NUMBER</pub:name>"
                    + "                        <pub:values>"
                    + "                            <pub:item>7</pub:item>"
                    + "                        </pub:values>"
                    + "                    </pub:item>"
                    + "                </pub:parameterNameValues>"
                    + "                <pub:reportAbsolutePath>/Custom/Person Details/Person Detail Report.xdo</pub:reportAbsolutePath>"
                    + "                <pub:sizeOfDataChunkDownload>-1</pub:sizeOfDataChunkDownload>"
                    + "            </pub:reportRequest>"
                    + "            <pub:appParams>?</pub:appParams>"
                    + "        </pub:runReport>"
                    + "    </soap:Body>"
                    + "</soap:Envelope>";
            // Headers
            httpPost.setHeader("Content-Type", "application/soap+xml");

            StringEntity entity = new StringEntity(soapEnvelope, "UTF-8");
            httpPost.setEntity(entity);

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Response status code
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);

            // Get the response entity (the SOAP envelope)
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                //System.out.println(responseString); if the xml needs to be printed

                // Convert the SOAP XML response to JSON
                JSONObject jsonResponse = XML.toJSONObject(responseString);
                System.out.println(jsonResponse.toString(4));

                // Access specific fields in the JSON
                JSONObject envelope = jsonResponse.getJSONObject("env:Envelope");
                JSONObject body = envelope.getJSONObject("env:Body");
                // Access the response data from the body
                if (body.has("runReportResponse")) {
                    JSONObject reportResponse = body.getJSONObject("runReportResponse");
                    System.out.println("Report Response Data: " + reportResponse.toString());
                }
            }
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
