package com.kasuncreations.loginmgmt;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kasun Thilina on 12/1/2017.
 */

public class LoginRequest extends StringRequest{
    private static final String Login_Req_URL="http://nfcappnibm.mywebcommunity.org/login.php";
    private Map<String, String> params;

    public LoginRequest(String username,String password, Response.Listener<String>listener)
    {
        super(Request.Method.POST,Login_Req_URL,listener,null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }



    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
