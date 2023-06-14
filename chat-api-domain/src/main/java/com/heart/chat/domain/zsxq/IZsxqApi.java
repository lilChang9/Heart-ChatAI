package com.heart.chat.domain.zsxq;

import com.heart.chat.domain.zsxq.model.aggregates.UnCommentedCommentsAggregates;

import java.io.IOException;

public interface IZsxqApi {

    /**
     * 获取未被评论的所有评论信息
     *
     * @param groupId
     * @param cookie
     */
    UnCommentedCommentsAggregates queryUncommentedComments(String groupId, String cookie) throws IOException;

    /**
     * 评论回复
     *
     * @param groupId    分组id
     * @param cookie     cookie值
     * @param topicId    commentId
     * @param ansContent 回答的文本内容
     * @param silenced   是否让所有人可见
     * @return 是否回答成功
     * @throws IOException
     */
    boolean answerComment(String groupId, String cookie, long topicId, String ansContent, boolean silenced) throws IOException;
}
