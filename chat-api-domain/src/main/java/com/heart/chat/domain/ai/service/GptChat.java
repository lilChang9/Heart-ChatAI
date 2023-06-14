package com.heart.chat.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.heart.chat.domain.ai.IGptChat;
import com.heart.chat.domain.ai.model.aggregates.AIAnswer;
import com.heart.chat.domain.ai.model.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GptChat implements IGptChat {
    @Override
    public String answer(String question) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.aiproxy.xyz/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String jsonParam = "{\n" +
                "     \"model\": \"text-similarity-davinci-001\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
        StringEntity entity = new StringEntity(jsonParam, ContentType.create("text/json").toString(), "UTF-8");
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getText());
            }
            return answers.toString();
        } else {
            return "Too Many Requests";
        }
    }
}
