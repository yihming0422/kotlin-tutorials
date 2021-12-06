package com.bennyhuo.kotlin.types.eg;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GitHuber {
    public static void main(String... args) throws IOException {
        GitHubApi gitHubApi = new Retrofit.Builder().baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GitHubApi.class);

        Response<Repository> response = gitHubApi.getRepository("JetBrains", "Kotlin").execute();

        Repository repository = response.body();

        if(repository == null){
            System.out.println("Error! " + response.code() + " " + response.message());
        } else {
            System.out.println(repository.getName());
            System.out.println(repository.getOwner().getLogin());
            System.out.println(repository.getStargazers_count());
            System.out.println(repository.getForks_count());
            System.out.println(repository.getHtml_url());

            File htmlFile = new File("Kotlin.html");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(htmlFile));
            bufferedWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>" + repository.getOwner().getLogin() + " - " + repository.getName() + "</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1><a href='" + repository.getHtml_url() + "'>" + repository.getOwner().getLogin() + " - " + repository.getName() + "</a></h1>\n" +
                    "<p>" + repository.getDescription() + "</p>\n" +
                    "<p>Stars: "+ repository.getStargazers_count() +"</p>\n" +
                    "<p>Forks: " + repository.getForks_count() + "</p>\n" +
                    "</body>\n" +
                    "</html>");

            bufferedWriter.close();
        }
    }
}
