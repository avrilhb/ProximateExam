package com.example.avril.proximateexam.models;

import io.realm.RealmObject;

/**
 * Created by avrilhb on 13/12/17.
 */

public class TokenEntity extends RealmObject {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
