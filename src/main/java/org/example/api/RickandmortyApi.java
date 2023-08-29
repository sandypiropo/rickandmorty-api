package org.example.api;

import org.example.api.ApiRequester;

import java.util.Scanner;

public class RickandmortyApi {
    public static void main(String[] args) {
        String urlBase = "https://rickandmortyapi.com/api/character";

        ApiRequester charactersApi = new ApiRequester(urlBase);

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter character ID: ");
            int characterID = scanner.nextInt();
            charactersApi.makeRequest(characterID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

