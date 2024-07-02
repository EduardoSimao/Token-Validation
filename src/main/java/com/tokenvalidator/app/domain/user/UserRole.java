package com.tokenvalidator.app.domain.user;

public enum UserRole {
    ADMIN("admin"),
    MEMBER("member"),
    EXTERNAL("external");

    private String role;

    UserRole(String role){
        this.role = role;
    }

   public String getRole(){
    return role;
   } 

}
