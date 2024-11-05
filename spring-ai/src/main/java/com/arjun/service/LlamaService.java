\package com.arjun.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LlamaService {

    public String getLlamaResponse(String message) throws Exception {
        String accessToken = "API_KEY"; 
        String endpointUrl = "END_POINT"; 

        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{ \"text\": \"" + message + "\" }";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            System.out.println("Failed to get a response from AI service. Response Code: " + responseCode);
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line.trim());
        }
        reader.close();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "{\"response\": \"" + response.toString() + "\"}";
        } else {
            return "{\"error\": \"" + response.toString() + "\"}";
        }
    }
}



