package com.example.prompt_tester.controller;

import com.example.prompt_tester.dto.PromptRequest;
import com.example.prompt_tester.dto.PromptResult;
import com.example.prompt_tester.service.OpenAIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PageController {

    private final OpenAIService openAIService;

    public PageController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping
    public String index(Model model) {
        //adding empty request object for form binding
        model.addAttribute("promptRequest", new PromptRequest("You are a helpful assistant.", "", 0.7));
        return "index";
    }

    //handles the form submission request
    @PostMapping("/submit")
    public String testPrompt(@ModelAttribute PromptRequest request, Model model) {
        try {
            PromptResult response = openAIService.sendPrompt(request);

            //add data to model so thymeleaf can display it
            model.addAttribute("promptRequest", request);
            model.addAttribute("response", response);
        } catch (Exception e) {
            //handle errors
//            model.addAttribute("error", "Oops! " + e.getMessage());

            String errorMsg = e.getMessage().toLowerCase();
            System.out.println(errorMsg);

            if (errorMsg.contains("quota") ||
                    errorMsg.contains("insufficient") ||
                    errorMsg.contains("billing") ||
                    errorMsg.contains("429")) {

                // 😂 BROKE MODE: Random funny message
                model.addAttribute("error", getRandomBrokeMessage());
            }
           else if(errorMsg.contains("i/o") || errorMsg.contains("post") ){
                model.addAttribute("error", "Oops! Connection to the OpenAI failed");
            }
            else {
                // Other errors: show normal message
                model.addAttribute("error", "Oops! " + e.getMessage());
            }
            model.addAttribute("promptRequest", request);
        }
        return "index";
    }


    private String getRandomBrokeMessage() {
        String[] messages = {
                "😭 Aaand my $5 OpenAI credit is gone.\n\n" +
                        "I'm officially broke. Like, actually broke.\n\n" +
                        "But hey! The code is free. Clone it and use YOUR key:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>View on GitHub</a>\n\n" +
                        "(OpenAI gives you free $5 credit too)",

                "💸 Breaking News: Local College Student Runs Out of Money\n\n" +
                        "In other news: Water is wet.\n\n" +
                        "Good news though — you can run this yourself!\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>GitHub Repo</a>\n" +
                        "(Literally 3 commands to set up)",

                "🎓 Budget Status Report\n" +
                        "━━━━━━━━━━━━━━━━━━━━\n" +
                        "Initial: $5.00\n" +
                        "Current: $0.00\n" +
                        "Broke-ness: MAXIMUM\n\n" +
                        "Want to keep using this? Clone it!\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>Get the Code</a>",

                "⚰️ RIP My OpenAI Credits\n" +
                        "Born: When I got free $5\n" +
                        "Died: Just now\n" +
                        "Last Words: \"sk-proj-...\"\n\n" +
                        "To resurrect this with YOUR credits:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>Clone on GitHub</a>\n\n" +
                        "Or just star the repo. That's free and makes me feel better.",

                "📊 Financial Report:\n\n" +
                        "Revenue: $0\n" +
                        "Expenses: $5\n" +
                        "Profit: -$5\n" +
                        "Business Model: Failed Successfully ✅\n\n" +
                        "Clone the code and be your own boss:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>GitHub Repository</a>",

                "🚨 EMERGENCY ALERT 🚨\n\n" +
                        "This broke college student has run out of OpenAI credits.\n\n" +
                        "If found, please direct them to ramen.\n" +
                        "Or better yet, clone this repo and run your own:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>Get Started Here</a>\n\n" +
                        "Free $5 OpenAI credit for new users btw 👀",

                "💀 Service Status: DECEASED\n\n" +
                        "Cause of Death: Insufficient Funds\n" +
                        "Survivors: This GitHub repo\n\n" +
                        "Memorial service (aka clone instructions):\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>Pay Respects on GitHub</a>\n\n" +
                        "Flowers (aka GitHub stars) appreciated 🌹",

                "🎪 Ladies and gentlemen...\n\n" +
                        "The moment you've all been waiting for...\n\n" +
                        "I'M BROKE! 🎉\n\n" +
                        "Thank you for coming to my TED talk.\n\n" +
                        "Encore performance available at:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>GitHub</a>\n" +
                        "(Bring your own OpenAI key)",

                "🤷‍♂️ Well, that was fun while it lasted.\n\n" +
                        "$5 of OpenAI credits = gone\n" +
                        "Lessons learned = many\n" +
                        "Regrets = zero\n\n" +
                        "Your turn to play! Clone it here:\n" +
                        "👉 <a href='https://github.com/theishanpathak/prompt-tester' target='_blank'>Get the Code</a>\n\n" +
                        "Setup time: ~5 minutes\n" +
                        "Your free credit: $5\n" +
                        "The friends we made along the way: Priceless"
        };

        int randomIndex = (int) (Math.random() * messages.length);
        return messages[randomIndex];
    }
}
