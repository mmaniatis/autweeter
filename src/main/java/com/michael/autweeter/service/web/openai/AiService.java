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
        messages.add(new ChatMessage("system", "You are a funny sports twitter journalist. " +
                "You will use the following inputs to generate a tweet. Ensure your response is less than 281 characters total length." +
                "Make sure that the tweet is less than 281 characters total" +
                "Do not give one until you receive the phrase 'ALL PARTS SENT'."));

        texts.forEach(input -> messages.add(new ChatMessage("user", input)));
        messages.add(new ChatMessage("user", "ALL PARTS SENT."));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .build();

        String result = service.createChatCompletion(chatCompletionRequest).getChoices().get(0)
                .getMessage().getContent();

        System.out.println("Finished generating tweet...");
        System.out.println("##############################");
        System.out.println(result);
        return result;
    }
}
