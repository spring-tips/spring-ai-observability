package bootiful.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@SpringBootApplication
public class AiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}

@Controller
@ResponseBody
class JokeController {

    private final ChatClient chatClient;

    JokeController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/joke")
    Map<String, String> joke() {
        var reply = chatClient
                .prompt()
                .user("""
                        tell me a joke. be concise. don't send anything except the joke.
                        """)
                .call()
                .content();
        return Map.of("joke", reply);
    }
}
