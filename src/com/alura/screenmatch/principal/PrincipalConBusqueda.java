package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloODMb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el nombre de una película:");
        String busqueda = lectura.nextLine();
        String direccion = "http://www.omdbapi.com/?t=" + busqueda + "&apikey=8448b322";
        direccion = direccion.replace(" ", "+");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build(); //Request es abstracto, para instanciarlo uso el patròn builder

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        //tiene dos mètodos principales: toJson() y fromJson() para convertir una clase a Json o viceversa
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        String json = response.body();
        //Titulo miTitulo = gson.fromJson(json, Titulo.class);
        //System.out.println(miTitulo.toString());

        TituloODMb miTituloODMb = gson.fromJson(json, TituloODMb.class);
        try {
            Titulo miTitulo = new Titulo(miTituloODMb); //el formateo podria fallar
            System.out.println(miTitulo.toString());
        } catch (Exception e) {
            System.out.println("Ocurrió un error: ");
            System.out.println(e.getMessage());
        }
    }

}
