package com.example.yum.Common;

import android.app.ProgressDialog;
import android.content.Context;

import java.net.URLEncoder;

public class Stables {
    public static String baseUrl="http://192.168.8.102:8000/";


    public String getLoginController(String username,String password){
        return baseUrl+"api/mobilelogin?username="+username+"&password="+password;
    }

    public String CurrentPassword(String uid,String password){
        return baseUrl+"api/currentpassword?uid="+uid+"&password="+password;
    }

    public String UpdatePasswordController(String uid,String password,String confirm_password){
        return baseUrl+"api/updatePasswordMobile"+"?uid="+uid+"&password="+password+"&confirm_password="+confirm_password;
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

    public String EmailVerification(String email){
        String url="";
        try {
            url=baseUrl+"api/verificationcode?"+"email="+ URLEncoder.encode(email,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String CodeVerification(String email,String code){
        String url="";
        try {
            url=baseUrl+"api/checkverificationcode?"+"email="+ URLEncoder.encode(email,"utf-8")+"&code="+URLEncoder.encode(code,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public String ResetPassword(String email,String password){
        String url="";
        try {
            url=baseUrl+"api/resetpasswordmobile?"+"email="+ URLEncoder.encode(email,"utf-8")
                    +"&password="+ URLEncoder.encode(password,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }


    public String getCategoriesToList(){
        return baseUrl+"api/getCategoryItem";
    }

    public String getCookBooksToList(String userid){
        return baseUrl+"api/getcookbookitem?userid="+userid;
    }

























    public ProgressDialog showLoading(Context context){
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait until the process complete");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
