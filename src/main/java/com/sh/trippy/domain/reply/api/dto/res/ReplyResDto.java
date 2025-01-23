package com.sh.trippy.domain.reply.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResDto {

    private List<ReplyInfoDto> replyInfoDtoList;


    public ReplyResDto(List<ReplyInfoDto> replyInfoDtoList) {
        this.replyInfoDtoList = replyInfoDtoList;
    }

}
