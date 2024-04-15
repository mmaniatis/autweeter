package com.michael.autweeter.service.spring.config;

import com.michael.autweeter.service.persist.FilePersister;
import com.michael.autweeter.service.web.openai.AiService;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfiguration {

    @Bean
    public AiService aiService() {
        System.out.println("USING API_KEY=" + System.getenv("OPENAI_API_KEY"));
        return new AiService(new OpenAiService(System.getenv("OPENAI_API_KEY")), new FilePersister());
    }
}
