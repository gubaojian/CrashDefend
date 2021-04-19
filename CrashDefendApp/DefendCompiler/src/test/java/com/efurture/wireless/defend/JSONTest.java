package com.efurture.wireless.defend;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JSONTest  {


    @Test
    public void testToJson(){
        RequestConstants requestConstants = new RequestConstants();
        String json = JSONObject.toJSONString(requestConstants);
        System.out.println(json);

        System.out.println(json.getBytes().length);
    }
}
