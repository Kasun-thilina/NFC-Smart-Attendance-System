package com.kasuncreations.loginmgmt;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kasun Thilina on 11/26/2017.
 */

public class RegisterRequest extends StringRequest
{
    private static final String Reg_Req_URL="http://nfcappnibm.mywebcommunity.org/register.php";
    private Map<String, String>params;

    public RegisterRequest(String name,String username,String password,String course, Response.Listener<String>listener)
    {
        super(Method.POST,Reg_Req_URL,listener,null);
        params=new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("course",course);
    }



    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
