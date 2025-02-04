package com.sh.trippy.domain.tripcompanion.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum TripRole {

    HOST("HOST", "호스트"),
    GUEST("GUEST", "게스트");

    private final String status;
    private final String value;

}
