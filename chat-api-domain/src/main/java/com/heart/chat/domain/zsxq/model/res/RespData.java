package com.heart.chat.domain.zsxq.model.res;

import com.heart.chat.domain.zsxq.model.vo.Topics;

import java.util.List;

public class RespData {
    private List<Topics> topics;

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

    public List<Topics> getTopics() {
        return this.topics;
    }

}