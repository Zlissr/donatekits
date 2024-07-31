package ru.raytrace;

import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscordHelper {
    private URL url;

    public DiscordHelper(String webhookUrl) {
        try {
            this.url = new URL(webhookUrl);
        } catch (MalformedURLException e) {
            System.out.println("[DonateKits] Error with Webhook URL!");
        }
    }

    public void sendMessage(String message) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", "Log");
            jsonObject.put("content", message);

            HttpsURLConnection httpsURLConnection = getHttpsURLConnection();

            try (OutputStream outputStream = httpsURLConnection.getOutputStream()) {
                outputStream.write(jsonObject.toJSONString().getBytes());
            } finally {
                httpsURLConnection.getInputStream().close();
                httpsURLConnection.disconnect();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private HttpsURLConnection getHttpsURLConnection() throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.addRequestProperty("Content-Type", "application/json");
        httpsURLConnection.addRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 5.0.2; LG-V410) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.74 Safari/537.36");
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setRequestMethod("POST");

        return httpsURLConnection;
    }

}
