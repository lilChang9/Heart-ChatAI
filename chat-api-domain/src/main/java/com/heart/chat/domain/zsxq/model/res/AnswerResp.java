package com.heart.chat.domain.zsxq.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResp {

    private boolean succeeded;

    private RespData resp_data;
}
