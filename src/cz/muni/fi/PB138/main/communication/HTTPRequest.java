package cz.muni.fi.PB138.main.communication;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public class HTTPRequest {

    private final String BASE_URL = "http://barutoma.azurewebsites.net";
    private final String TOKEN = "/Token";

    private final String CONTENT_TYPE_TOKEN = "application/x-www-form-urlencoded";

    /**
     *
     * @param url
     * @return JSON
     */
    public String getRequest(String url) {
        HttpGet httpGet = new HttpGet(BASE_URL + url);

        HttpResponse response = null;
        HttpClient httpClient = HttpClientBuilder.create().build(); //new DefaultHttpClient();
        try {
            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            return body;
        } catch (ClientProtocolException cpe) {
            System.err.println("Protocol error " + cpe);
        } catch (IOException ioe) {
            System.err.println("Communication error " + ioe);
        }
        return null;
    }

    /**
     * Get data with authorization
     * @param token
     * @param url
     * @return JSON
     */
    public String getRequestWithToken(String token, String url) {
        HttpGet httpGet = new HttpGet(BASE_URL + url);
        httpGet.setHeader("Authorization", "Bearer " + token);

        HttpResponse response = null;
        HttpClient httpClient = HttpClientBuilder.create().build(); //new DefaultHttpClient();
        try {
            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            return body;
        } catch (ClientProtocolException cpe) {
            System.err.println("Protocol error " + cpe);
        } catch (IOException ioe) {
            System.err.println("Communication error " + ioe);
        }
        return null;
    }

    /**
     *
     * @param username
     * @param password
     * @return Access Token
     */
    public String postRequestToken(String username, String password) {
        HttpPost httpPost = new HttpPost(BASE_URL + TOKEN);
        httpPost.setHeader("Content-Type", CONTENT_TYPE_TOKEN);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("grant_type", "password"));

        HttpResponse response = null;
        HttpClient client = HttpClientBuilder.create().build(); //new DefaultHttpClient();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            JSONObject jsonObject = new JSONObject(body);
            String token = jsonObject.getString("access_token");

            return token;
        } catch (UnsupportedEncodingException uee) {
            System.err.println("Entity Exception: " + uee);
        } catch (ClientProtocolException cpe) {
            System.err.println("Protocol error: " + cpe);
        } catch (IOException ioe) {
            System.err.println("Communication error: " + ioe);
        }
        return null;
    }
}
