package com.tokenvalidator.app.domain.user;

public enum UserRole {
    ADMIN("ADMIN"),
    MEMBER("member"),
    EXTERNAL("external"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

   public String getRole(){
    return role;
   } 

}
