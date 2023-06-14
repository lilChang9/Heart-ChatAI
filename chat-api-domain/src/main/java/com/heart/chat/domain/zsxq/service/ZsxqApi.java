package com.heart.chat.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.heart.chat.domain.zsxq.IZsxqApi;
import com.heart.chat.domain.zsxq.model.aggregates.UnCommentedCommentsAggregates;
import com.heart.chat.domain.zsxq.model.req.AnswerReq;
import com.heart.chat.domain.zsxq.model.req.ReqData;
import com.heart.chat.domain.zsxq.model.res.AnswerResp;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnCommentedCommentsAggregates queryUncommentedComments(String groupId, String cookie) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=all&count=20");

        get.addHeader("Content-Type", "application/json;charset=utf8");
        get.addHeader("cookie", cookie);

        CloseableHttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String json = EntityUtils.toString(response.getEntity());
            return JSON.parseObject(json, UnCommentedCommentsAggregates.class);
        } else {
            throw new RuntimeException("query Error, the error code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answerComment(String groupId, String cookie, long topicId, String ansContent, boolean silenced) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");
        logger.info("{}", post.getURI());
        post.addHeader("Content-Type", "application/json;charset=utf8");
        post.addHeader("cookie", cookie);
        post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        // 设置响应体
        /*String jsonParam = " {\n" +
                "            \"req_data\": {\n" +
                "            \"text\": \"" + "test" + "\\n\",\n" +
                "                    \"image_ids\": [],\n" +
                "            \"mentioned_user_ids\": []\n" +
                "        }\n" +
                "        }";*/
        AnswerReq answerReq = new AnswerReq(new ReqData(ansContent, null, null));
        String jsonParam = JSON.toJSONString(answerReq, SerializerFeature.WriteNullListAsEmpty);
        logger.info("{}", jsonParam);

        StringEntity entity = new StringEntity(jsonParam, ContentType.create("text/json").toString(), "UTF-8");
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            logger.info("回复评论结果: groupId:{},topicId:{},jsonStr:{}", groupId, topicId, res);
            AnswerResp answerRes = JSON.parseObject(res, AnswerResp.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err code is " + response.getStatusLine().getStatusCode());
        }
    }
}
