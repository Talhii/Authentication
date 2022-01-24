package com.example.authentication;

public class VerificattionData {

    private String user;
    private int code;
    private String host;


    public VerificattionData() {

    }



    public VerificattionData(String user, int code) {
        this.user = user;
        this.code = code;
    }



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}