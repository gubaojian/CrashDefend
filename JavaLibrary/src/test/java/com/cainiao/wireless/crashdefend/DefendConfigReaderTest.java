package com.cainiao.wireless.crashdefend;

import com.alibaba.fastjson.JSONObject;
import com.cainiao.wireless.crashdefend.plugin.config.DefendConfigReader;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendConfig;

public class DefendConfigReaderTest {


    public static void main(String[] args) {
        DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/defend.xml", config);

        System.out.println(JSONObject.toJSONString(config));
    }
}
