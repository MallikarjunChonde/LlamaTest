//package com.arjun.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.arjun.model.VIPRequest;
//import com.arjun.model.Volunteer;
//import com.arjun.repository.VolunteerRepository;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class LlamaService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private VolunteerRepository volunteerRepository;
//
//    public List<Volunteer> getLlamaPredictionAndFindVolunteers(VIPRequest request) {
//        String url = "https://azureaitestworksapce-xjstk.eastus2.inference.ml.azure.com/score";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer 5zeGuPOXNskuiF7pWPBQAYVEJPSOM9SL");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("text", request.toString());
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        // Call the AI service
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        String aiResponse = response.getBody();
//
//        // Assuming AI provides a postal code and date in the response
//        String postalCode = extractPostalCodeFromResponse(aiResponse);
//        LocalDate availableDate = extractAvailableDateFromResponse(aiResponse);
//
//        // Find volunteers by postcode and available date
//        List<Volunteer> volunteersByPostcode = volunteerRepository.findByPostcode(postalCode);
//        List<Volunteer> matchingVolunteers = volunteersByPostcode.stream()
//            .filter(volunteer -> volunteer.getAvailabilityDate().equals(availableDate))
//            .collect(Collectors.toList());
//
//        return matchingVolunteers;
//    }
//
//    private String extractPostalCodeFromResponse(String aiResponse) {
//
//        return "extractedPostalCode";
//    }
//
//    private LocalDate extractAvailableDateFromResponse(String aiResponse) {
//        return LocalDate.now();
//    }
//}
//
//


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.arjun.model.VIPRequest;
//import com.arjun.model.Volunteer;
//import com.arjun.repository.VolunteerRepository;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.stream.Collectors;
//
//@Service
//public class LlamaService {
//
//    @Autowired
//    private VolunteerRepository volunteerRepository;
//
//    public List<Volunteer> getLlamaPredictionAndFindVolunteers(VIPRequest request) throws Exception {
//        String accessToken = "";
//        String endpointUrl = "";
//
//        URL url = new URL(endpointUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setDoOutput(true);
//
//        // Creating JSON request body
//        String jsonInputString = "{ \"text\": \"" + request.toString() + "\" }";
//
//        // Sending the request
//        try (OutputStream os = connection.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            // Handle the response
//            String aiResponse = new String(connection.getInputStream().readAllBytes(), "utf-8");
//
//            // Assuming AI provides a postal code and date in the response
//            String postalCode = extractPostalCodeFromResponse(aiResponse);
//            LocalDate availableDate = extractAvailableDateFromResponse(aiResponse);
//
//            // Find volunteers by postcode and available date
//            List<Volunteer> volunteersByPostcode = volunteerRepository.findByPostcode(postalCode);
//            return volunteersByPostcode.stream()
//                    .filter(volunteer -> volunteer.getAvailabilityDate().equals(availableDate))
//                    .collect(Collectors.toList());
//        } else {
//            System.out.println("Failed to get a response from AI service. Response Code: " + responseCode);
//            return List.of(); 
//        }
//    }
//
//    private String extractPostalCodeFromResponse(String aiResponse) {
//        try {
//           
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(aiResponse);
//
//            String postalCode = rootNode.path("postalCode").asText();
//            return postalCode;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null; 
//        }
//    }
//
//    private LocalDate extractAvailableDateFromResponse(String aiResponse) {
//        try {
//           
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(aiResponse);
//
//            String dateStr = rootNode.path("availableDate").asText();
//            return LocalDate.parse(dateStr);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null; 
//        }
//    }
//}
package com.arjun.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LlamaService {

    public String getLlamaResponse(String message) throws Exception {
        String accessToken = "xZ3GmYna1AD6Qcts2wnlu0WkrI9hrCKC"; 
        String endpointUrl = "https://arjun-5024-vizwp.eastus2.inference.ml.azure.com/score"; 

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



