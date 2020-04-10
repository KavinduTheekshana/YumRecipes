package com.example.yum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUserData {

    public static boolean login_validate(String user_email,String user_password)
    {
        if (user_email.isEmpty() || user_password.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static boolean signup_validate(String user_name,String user_email,String user_password,String user_repassword)
    {
        if (user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_repassword.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static boolean add_recipes_validate(String recipes_name,String recipes_ingrediants,String recipes_description)
    {
        if (recipes_name.isEmpty() || recipes_ingrediants.isEmpty() || recipes_description.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static boolean update_profile_validate(String user_name,String user_email)
    {
        if (user_name.isEmpty() || user_email.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static boolean update_password_validate(String password,String new_password)
    {
        if (password.isEmpty() || new_password.isEmpty())
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static boolean check_password_validate(String password1,String password2)
    {
        if (password1.equals(password2))
        {
            return true;
        }else
        {
            return false;
        }
    }

    public static boolean isValidmail(final String email)
    {
        String StringTosearch = email;

        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(StringTosearch);


        if (m.find())
            return true;
        else
            return false;
    }
}
