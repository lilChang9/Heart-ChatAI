package com.heart.chat.test;

import com.alibaba.fastjson.JSON;
import com.heart.chat.domain.ai.IGptChat;
import com.heart.chat.domain.zsxq.IZsxqApi;
import com.heart.chat.domain.zsxq.model.aggregates.UnCommentedCommentsAggregates;
import com.heart.chat.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.heart.chat.domain")
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatapi.groupId}")
    private String groupId;

    @Value("${chatapi.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IGptChat gptChat;

    @Test
    public void queryCommentTest() throws Exception {
        UnCommentedCommentsAggregates unCommentedCommentsAggregates = zsxqApi.queryUncommentedComments(groupId, cookie);
        logger.info(JSON.toJSONString(unCommentedCommentsAggregates));
        for (Topics topic : unCommentedCommentsAggregates.getRespData().getTopics()) {
            // 获取评论内容
            String text = topic.getTalk().getText();
            logger.info("topicId:{},text:{}", topic.getTopic_id(), text);
            String answer = gptChat.answer(text);
            zsxqApi.answerComment(groupId, cookie, topic.getTopic_id(), answer, false);
        }
    }

    @Test
    public void testChatgpt() throws Exception {
        String str = gptChat.answer("hello");
        System.out.println(str);
    }
}
