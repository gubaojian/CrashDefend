package com.efurture.wireless.defend;

import com.alibaba.fastjson.JSONObject;
import com.efurture.wireless.defend.plugin.config.DefendConfigReader;
import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;

public class DefendConfigReaderTest {


    public static void main(String[] args) {
        com.efurture.wireless.defend.plugin.config.domain.DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/defend.xml", config);

        System.out.println(JSONObject.toJSONString(config));
    }
}
