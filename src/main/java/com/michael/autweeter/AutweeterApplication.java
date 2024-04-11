package com.michael.autweeter;

import com.michael.autweeter.service.web.WebCrawler;
import com.michael.autweeter.service.web.golf.PgaCrawler;
import com.michael.autweeter.service.web.openai.AiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
public class AutweeterApplication {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(AutweeterApplication.class, args);
		WebCrawler pgaCrawler = context.getBean(PgaCrawler.class);
		AiService aiService = context.getBean(AiService.class);

		List<String> articles = pgaCrawler.fetch();
		aiService.summarizeAndPersistStrings(articles);

		exit(0);
	}

}
