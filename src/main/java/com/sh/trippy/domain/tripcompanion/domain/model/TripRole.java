package com.sh.trippy.domain.tripcompanion.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum TripRole {

    GUEST("HOST", "호스트"),
    USER("GUEST", "게스트");

    private final String key;
    private final String value;

}
