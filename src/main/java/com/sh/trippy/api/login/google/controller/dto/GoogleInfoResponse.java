package com.sh.trippy.api.login.google.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleInfoResponse {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private String email_verified;
    private String at_hash;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;

    public GoogleInfoResponse(String iss, String azp, String aud, String sub, String email, String email_verified, String at_hash, String name, String picture, String given_name, String family_name, String locale, String iat, String exp, String alg, String kid, String typ) {
        this.iss = iss;
        this.azp = azp;
        this.aud = aud;
        this.sub = sub;
        this.email = email;
        this.email_verified = email_verified;
        this.at_hash = at_hash;
        this.name = name;
        this.picture = picture;
        this.given_name = given_name;
        this.family_name = family_name;
        this.locale = locale;
        this.iat = iat;
        this.exp = exp;
        this.alg = alg;
        this.kid = kid;
        this.typ = typ;
    }
}
