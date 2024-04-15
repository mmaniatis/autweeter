package com.michael.autweeter.service.web;

import java.net.http.HttpResponse;

public interface SocialService {
    HttpResponse post(String text) throws Exception;
}
