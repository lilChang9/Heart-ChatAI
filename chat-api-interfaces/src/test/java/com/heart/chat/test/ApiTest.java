package com.heart.chat.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ApiTest {


    @Test
    public void query_comment() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15552458522282/topics?scope=all&count=20");

        get.addHeader("Content-Type","application/json;charset=utf8");
        get.addHeader("cookie","zsxq_access_token=35EBB6A1-D530-1E09-72D2-9BB35A62BBF9_552C54BCCABB53FD; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184242554145582%22%2C%22first_id%22%3A%2218789f7ec81cab-0fcdb6893c548f8-26031b51-1821369-18789f7ec821474%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3ODlmN2VjODFjYWItMGZjZGI2ODkzYzU0OGY4LTI2MDMxYjUxLTE4MjEzNjktMTg3ODlmN2VjODIxNDc0IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiMTg0MjQyNTU0MTQ1NTgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184242554145582%22%7D%2C%22%24device_id%22%3A%2218789f7ec81cab-0fcdb6893c548f8-26031b51-1821369-18789f7ec821474%22%7D; zsxqsessionid=42119e57cd296bf26b3faff4495a21f3");

        CloseableHttpResponse response = client.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void reply_to_comment() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/214481112114441/comments");
        post.addHeader("Content-Type","application/json;charset=utf8");
        post.addHeader("cookie","zsxq_access_token=35EBB6A1-D530-1E09-72D2-9BB35A62BBF9_552C54BCCABB53FD; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22184242554145582%22%2C%22first_id%22%3A%2218789f7ec81cab-0fcdb6893c548f8-26031b51-1821369-18789f7ec821474%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg3ODlmN2VjODFjYWItMGZjZGI2ODkzYzU0OGY4LTI2MDMxYjUxLTE4MjEzNjktMTg3ODlmN2VjODIxNDc0IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiMTg0MjQyNTU0MTQ1NTgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22184242554145582%22%7D%2C%22%24device_id%22%3A%2218789f7ec81cab-0fcdb6893c548f8-26031b51-1821369-18789f7ec821474%22%7D; zsxqsessionid=42119e57cd296bf26b3faff4495a21f3");

        // 设置响应体
        String jsonParam = " {\n" +
                "            \"req_data\": {\n" +
                "            \"text\": \"我真不知道\\n\",\n" +
                "                    \"image_ids\": [],\n" +
                "            \"mentioned_user_ids\": []\n" +
                "        }\n" +
                "        }";
        StringEntity entity = new StringEntity(jsonParam, ContentType.create("text/json").toString(), "UTF-8");
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testChatgpt() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.aiproxy.xyz/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String jsonParam = "{\n" +
                "     \"model\": \"text-similarity-davinci-001\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + "帮我写一个归并排序" + "\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
        StringEntity entity = new StringEntity(jsonParam, ContentType.create("text/json").toString(), "UTF-8");
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
