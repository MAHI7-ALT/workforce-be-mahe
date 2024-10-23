package org.imaginnovate.entity.common;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CurrentUser {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId; 
    }

    public boolean isLoggedIn() {
        return userId != null;
    }
}
