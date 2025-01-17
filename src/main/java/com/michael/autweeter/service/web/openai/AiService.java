package com.michael.autweeter.service.web.openai;

import com.michael.autweeter.service.persist.FilePersister;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
public class AiService {
    OpenAiService service;
    FilePersister filePersister;
    final String SUMMARIZE_TEXT_STATEMENT = "Summarize the following text in less than 280 characters, ";

    public String summarizeAndPersistStrings(List<String> texts) {
        String filePath = "dummy_path_" + Date.from(Instant.now());
        List<String> summaries = new ArrayList<>();
        IntStream.range(0, texts.size()).forEach(i -> {
            System.out.println("Currently Generating summary...");

            System.out.printf("%.2f percent complete..\n", (((double) i + 1) / (double) texts.size()) * 100.00);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(
                            new ChatMessage("system", "You are a gambling based sports journalist."),
                            new ChatMessage("user", SUMMARIZE_TEXT_STATEMENT + texts.get(i))
                    ))
                    .build();

            String summarizedText = service.createChatCompletion(chatCompletionRequest).getChoices()
                    .get(0).getMessage().getContent();

            summaries.add(summarizedText);
            System.out.println("Completed Generating summary...");

        });
//            filePersister.saveToFile(summarizedText, filePath);
        generateTweet(summaries);
        return filePath;
    }

    public String generateTweet(List<String> texts) {
        System.out.println("Starting to generate the tweet based on all the previous summaries...");

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", "You are a twitter golf journalist keeping people informed on critical golf news." +
                "You will use the following inputs to generate a tweet that is 281 characters or less. Generate 5 tweets that encapsulate all the article inputs you have been given." +
                "Separate the tweets with the delimiter ';;'." +
                "Do not give the tweets until you receive the phrase 'ALL PARTS SENT'."));

        texts.forEach(input -> messages.add(new ChatMessage("user", input)));
        messages.add(new ChatMessage("user", "ALL PARTS SENT."));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .build();

        String[] result = service.createChatCompletion(chatCompletionRequest).getChoices().get(0)
                .getMessage().getContent().split(";;");

        System.out.println("Finished generating tweet...");
        System.out.println("##############################");
        System.out.println(String.join("\n", result));
        return String.join("\n", result);
    }
}
