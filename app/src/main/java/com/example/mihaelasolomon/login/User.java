package com.example.mihaelasolomon.login;

/**
 * Created by mihaela.solomon on 2/28/2018.
 */

public class User {
    public String user;
    public String pass;
    public String email;

    public User(){

    }

    public User (String user,String pass,String email){
        this.email=email;
        this.user=user;
        this.pass=pass;
    }

}
