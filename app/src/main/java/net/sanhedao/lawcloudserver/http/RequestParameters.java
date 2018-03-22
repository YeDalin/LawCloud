package net.sanhedao.lawcloudserver.http;


import net.sanhedao.lawcloudserver.log.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/1/4.
 *
 * 网络访问上传数据
 *
 */
public class RequestParameters {
    private Map<String, String> requestMap = new HashMap<>();

    public void put(String key, String value) {
        requestMap.put(key, value);
    }

    public void putAll(Map map){
        requestMap=map;
    }

    public String buildRequestString(String use) {
        Set<Map.Entry<String, String>> entries = requestMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();

            stringBuffer.append(entry.getKey() + "=" + entry.getValue()+"&");
        }

        Logger.e("xxxx",use+" 网络请求post参数str= "+stringBuffer.toString());

        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }
}
