package org.imaginnovate.dto;


public class LogoutRequestBody {
    private String userName;
    private String jwtToken;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }   

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

   
}
