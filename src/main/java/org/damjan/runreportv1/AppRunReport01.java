package org.damjan.runreportv1;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class AppRunReport01 {
    public static void main(String[] args) {
        String url = "https://fa-euth-dev31-saasfademo1.ds-fa.oraclepdemos.com:443/xmlpserver/services/ExternalReportWSSService";
        String username = "SAAS.IMP2";
        String password = "welcome123";

        // Create the HTTP client with credentials
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username, password)
        );

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            // Create the HTTP POST request
            HttpPost httpPost = new HttpPost(url);

            // Set the SOAP request body
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


            httpPost.setHeader("Content-Type", "application/soap+xml");
            httpPost.setEntity(new org.apache.http.entity.StringEntity(soapEnvelope, "UTF-8"));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            System.out.println(response.getStatusLine().getStatusCode());

            // Print the response
            if (responseEntity != null) {
                String responseBody = EntityUtils.toString(responseEntity);
                System.out.println(responseBody.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
