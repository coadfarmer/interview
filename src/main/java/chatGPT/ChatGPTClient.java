package chatGPT;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTClient {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
    private static final String OPENAI_API_KEY = "***";

    public static String generateText(String prompt) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(OPENAI_API_URL);

        // Set the request parameters
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("prompt", prompt));
        params.add(new BasicNameValuePair("temperature", "0.5"));
        params.add(new BasicNameValuePair("max_tokens", "128"));
        params.add(new BasicNameValuePair("n", "1"));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Set the API key in the headers
        httppost.setHeader("Authorization", "Bearer " + OPENAI_API_KEY);

        // Send the request and get the response
        HttpResponse response;
        String text = "";
        try {
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                text = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public static void main(String[] args) {
        String prompt = "Hello, how are you?";
        String text = generateText(prompt);
        System.out.println(text);
    }
}
