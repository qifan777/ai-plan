package io.github.qifan777.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ChatTest {
    @Autowired
    ChatModel chatModel;
    @Test
    public void test(){
        String hello = chatModel.call("你好");
        log.info("hello:{}",hello);
    }
}
