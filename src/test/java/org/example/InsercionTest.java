package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsercionTest {
    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/";
    private static final String YOUR_API_URL = "http://localhost:8080";
    @Test
    public void testInserirUsuario() {
        try {
            String randomUserData = getApiResponse(RANDOM_USER_API_URL);
            JsonNode userData = new ObjectMapper().readTree(randomUserData);

            HttpURLConnection connection = (HttpURLConnection) new URL(YOUR_API_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            connection.getOutputStream().write(userData.toString().getBytes());

            int responseCode = connection.getResponseCode();
            assertEquals(201, responseCode, "Código de resposta esperado: 201 (Created)");

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getApiResponse(String apiUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode, "Código de resposta esperado: 200");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}
