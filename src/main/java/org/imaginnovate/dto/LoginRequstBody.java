package org.imaginnovate.dto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginRequstBody {
    private String userName;
    private String password;

    public LoginRequstBody() {
    }
    
    public LoginRequstBody(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    

}
