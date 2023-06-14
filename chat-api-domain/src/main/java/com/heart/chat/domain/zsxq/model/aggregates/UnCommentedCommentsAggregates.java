package com.heart.chat.domain.zsxq.model.aggregates;

import com.heart.chat.domain.zsxq.model.res.RespData;
import lombok.Data;

@Data
public class UnCommentedCommentsAggregates {

    private boolean isSucceed;

    private RespData respData;
}
