package com.example.yum.Common;

import java.net.URLEncoder;

public class Stables {
    public static String baseUrl="http://192.168.8.101:8000/";


    public String getLoginController(String username,String password){
        return baseUrl+"api/mobilelogin?username="+username+"&password="+password;
    }

    public String getCheckLoginController(String id){
        return baseUrl+"api/checkLogin"+"?id="+id;
    }

    public String SignUp(String username, String email, String password){
        String url="";
        try {
            url=baseUrl+"api/signup?"+"username="+ URLEncoder.encode(username,"utf-8")+"&email="+URLEncoder.encode(email,"utf-8")+"&password="+URLEncoder.encode(password,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
