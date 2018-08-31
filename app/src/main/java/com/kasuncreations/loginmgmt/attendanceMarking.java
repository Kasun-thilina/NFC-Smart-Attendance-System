package com.kasuncreations.loginmgmt;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kasun Thilina on 1/9/2018.
 */

public class attendanceMarking extends StringRequest {

    private static final String URL="http://nfcappnibm.mywebcommunity.org/attendance.php";
    private Map<String, String>params;

    public attendanceMarking(String name,String username,String password,String course, Response.Listener<String>listener)
    {
        super(Method.POST,URL,listener,null);
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
