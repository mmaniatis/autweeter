package com.michael.autweeter.service.web.twitter;

import com.michael.autweeter.service.web.SocialService;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class TwitterService implements SocialService {
    HttpClient httpClient;
    String token;


    public HttpResponse post(String text) throws Exception {
        //Build URI
//        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
//        ArrayList<NameValuePair> queryParameters;
//        queryParameters = new ArrayList<>();
//
        //Add query Parameters
//        queryParameters.add(new BasicNameValuePair("ids", ids));
//        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
//        uriBuilder.addParameters(queryParameters);

        //Set up the request w/ below headers.
//        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
//        httpGet.setHeader("Content-Type", "application/json");

        //Execute..
//        HttpResponse response = httpClient.execute(httpGet);


        throw new Exception("Not implemented yet!");
    }
}
