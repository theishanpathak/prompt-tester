package com.example.prompt_tester.dto;

public record PromptRequest(
        String systemPrompt,
        String userMessage,
        Double temperature
) {}
