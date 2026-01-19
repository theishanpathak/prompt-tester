package com.example.prompt_tester.dto;

public record PromptResult(
        String aiResponse,
        Integer tokenCount,
        Double cost,
        Long responseTimeMs
){}
