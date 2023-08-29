package org.example.api;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiRequester {
    private final String apiUrl;

    public ApiRequester(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void makeRequest(int characterID) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/" + characterID))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonNode jsonNode = objectMapper.readTree(responseBody);

            processCharacter(jsonNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCharacter(JsonNode characterNode) {
        int id = characterNode.get("id").asInt();
        String name = characterNode.get("name").asText();
        String status = characterNode.get("status").asText();
        String species = characterNode.get("species").asText();

        JsonNode locationNode = characterNode.get("location");
        String locationName = locationNode.get("name").asText();

        System.out.println("ID: " + id);
        System.out.println("Character Name: " + name);
        System.out.println("Character Status: " + status);
        System.out.println("Character Species: " + species);
        System.out.println("Location Name: " + locationName);

        JsonNode episodeNode = characterNode.get("episode");
        List<Integer> episodeNumbers = new ArrayList<>();

        for (JsonNode episode : episodeNode) {
            String episodeUrl = episode.asText();
            int episodeNumber = extractEpisodeNumber(episodeUrl);
            episodeNumbers.add(episodeNumber);
        }

        System.out.println("Episodes List: ");
        for (int episodeNumber : episodeNumbers) {
            System.out.println("Ep - " + episodeNumber);
        }
    }

    private int extractEpisodeNumber(String episodeUrl) {
        String[] parts = episodeUrl.split("/");
        String episodeNumberStr = parts[parts.length - 1];
        return Integer.parseInt(episodeNumberStr);
    }
}

