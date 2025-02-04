package com.sh.trippy.domain.tripinvitaion.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvitationStatus {
    ACCEPT("ACCEPT", "승낙"),
    WAIT("WAIT", "보류"),
    REJECT("REJECT", "거절");

    private final String status;
    private final String value;
}
