package com.sh.trippy.api.home.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeTripStatusResDto {
    private int domestic;
    private int abroad;


    public HomeTripStatusResDto(int domestic, int abroad) {
        this.domestic = domestic;
        this.abroad = abroad;
    }

    public void addDomestic(){
        this.domestic++;
    }

    public void addAbroad(){
        this.abroad++;
    }



}
