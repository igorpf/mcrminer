package com.mcrminer.service.mining;

public class DefaultAuthenticationData implements AuthenticationData {

    private String username;
    private String password;
    private String host;

    public DefaultAuthenticationData() {
    }

    public DefaultAuthenticationData(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getHost() {
        return host;
    }
}
