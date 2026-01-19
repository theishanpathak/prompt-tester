# 🧪 AI Prompt Tester

Test OpenAI prompts in real-time and see what they actually cost.

Made by a broke college student learning AI integration with $5 of free OpenAI credit.

![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![OpenAI](https://img.shields.io/badge/OpenAI-GPT--4o--mini-blue)

## 🚀 Try It Live

**[Launch App](comning-soon)**

*Free while my credit lasts. When it runs out, you'll see a funny message and a link to clone this.*

## 💀 What Happens When I'm Broke?

You'll get one of several random messages like:

> 😭 Aaand my $5 OpenAI credit is gone.
>
> I'm officially broke. Like, actually broke.
>
> But hey! The code is free. Clone it and use YOUR key:
> 👉 [View on GitHub](https://github.com/theishanpathak/prompt-tester)
>
> (OpenAI gives you free $5 credit too)


All errors gracefully handled with humor instead of scary red text. Check `PageController.java` for all 9 variations.

## ✨ Features

- **Test System Prompts**: Shape AI behavior and personality
- **Adjust Temperature**: Control creativity with a slider (0-2)
- **Real-time Metrics**: See exact token count, cost, and response time
- **Cost Tracking**: Know exactly how much each request costs
- **Error Messages**: Get roasted when my credits run out (or any error occurs)
- **Dark Mode UI**: Clean, comfortable interface for testing

## 🎯 What This Does

Ever wondered how different prompts affect AI responses? This tool lets you experiment with:
- Different system prompts ("You are a helpful assistant" vs "You are a pirate")
- Temperature settings (0.2 = focused, 1.5 = creative chaos)
- Real cost tracking (so you don't accidentally spend $50 on AI haikus)

Perfect for developers learning prompt engineering or building AI-powered applications.

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3, Java 17
- **Frontend**: Thymeleaf, CSS (dark mode)
- **AI API**: OpenAI GPT-4o-mini
- **HTTP Client**: RestTemplate
- **Vibes**: Broke college student energy ☕

## 🏃 Run Your Own Version

### Quick Setup (3 commands)

```bash
# 1. Clone
git clone https://github.com/theishanpathak/prompt-tester.git
cd prompt-tester

# 2. Get OpenAI key (free $5 credit for new users)
# https://platform.openai.com/api-keys

# 3. Add your key to application.properties
echo "openai.api.key=sk-proj-YOUR_KEY_HERE" > src/main/resources/application.properties

# 4. Run
mvn spring-boot:run

# 5. Open browser
# http://localhost:8080
```

### Alternative: Using Environment Variable

```bash
export OPENAI_API_KEY=sk-your-key
mvn spring-boot:run
```

## 📊 How It Works

1. **User fills form** → System prompt + User message + Temperature
2. **Spring Controller** receives submission (`PageController.java`)
3. **OpenAI Service** builds API request and sends to OpenAI (`OpenAIService.java`)
4. **Response parsing** extracts AI response, tokens, and cost
5. **Display results** with metrics OR show funny broke message if quota exceeded

## 💰 Cost Breakdown

Using OpenAI's GPT-4o-mini:
- **Input**: $0.15 per 1M tokens
- **Output**: $0.60 per 1M tokens
- **Response cap**: 500 tokens (keeps costs low)
- **New users**: Get $5 free credit

Example: A typical prompt + response costs ~$0.0001 (yes, fraction of a cent)

## 📚 What I Learned Building This

This project taught me:
- ✅ Integrating third-party AI APIs into Spring Boot
- ✅ Handling HTTP requests/responses with RestTemplate
- ✅ Parsing nested JSON data from API responses
- ✅ Calculating real-time costs based on token usage
- ✅ Building responsive forms with Thymeleaf
- ✅ Error handling for rate limits and API failures
- ✅ Managing sensitive API keys in Spring applications
- ✅ Temperature actually matters (0.2 vs 1.5 = wildly different results)
- ✅ Users prefer funny error messages over scary red text

## 🎓 Part of My AI Learning Journey

This is one of several mini-projects I'm building to learn AI integration:

✅ **This project**: Prompt testing and OpenAI API basics


Building in public and documenting everything on [LinkedIn](https://www.linkedin.com/in/ishan-pathak333/).

## 🤝 Contributing

This is a learning project, but ideas are welcome! Feel free to:
- ⭐ Star the repo if you found it helpful
- 🐛 Open an issue for bugs or suggestions
- 🍴 Fork and experiment with your own modifications
- 📢 Share what you learned if you use this

## 📝 License

MIT — do whatever you want with this code

## 🔗 Connect With Me

Building in public and sharing my AI learning journey:
- **Portfolio**: [@theishanpathak](https://theishanpathak.com)
- **GitHub**: [@theishanpathak](https://github.com/theishanpathak)
- **LinkedIn**: [Connect with me](https://linkedin.com/in/yourprofile)

---

**Note**: The live demo runs on my personal OpenAI credits. If you see the broke message, it means I actually ran out 😅 Clone it and use your own key to keep experimenting!

*Made with ☕ and a $5 budget*

⭐ **If this helped you learn something new, star the repo!**