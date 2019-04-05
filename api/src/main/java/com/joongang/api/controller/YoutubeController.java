package com.joongang.api.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
public class YoutubeController {

    private static HttpTransport HTTP_TRANSPORT;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String APPLICATION_NAME = "youtube-data";

    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    @GetMapping("/open-api/youtube/videos")
    public String searchChannels(@RequestParam(value = "name", required = false) String name) throws Exception {

        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        ClassPathResource clientSecret = new ClassPathResource("client_secret.json");
        File DATA_STORE_DIR = new File(String.format("%s/youtube-data", clientSecret.getFile().getParentFile().getPath()));
        DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

        YouTube youTube = getYouTubeService();
        YouTube.Channels.List channelsListByUsernameRequest = youTube.channels().list("snippet,contentDetails,statistics");
        channelsListByUsernameRequest.setForUsername("GoogleDevelopers");

        ChannelListResponse response = channelsListByUsernameRequest.execute();
        Channel channel = response.getItems().get(0);

        return channel.toPrettyString();
    }

    private YouTube getYouTubeService() throws IOException {
        Credential credential = authorize();
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                this.getClass().getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("200133197728-lfp8993j3idk0fd0tkak4f7cv8u7gi9b.apps.googleusercontent.com");
        return credential;
    }

}
