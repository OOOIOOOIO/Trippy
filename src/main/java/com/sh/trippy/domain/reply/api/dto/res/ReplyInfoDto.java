package com.sh.trippy.domain.reply.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // Querydls Projections.bean 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyInfoDto {
    private Long replyId;
    private String content;
    private String profileImg;
    private String nickname;

}
