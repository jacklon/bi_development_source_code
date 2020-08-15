package org.java.bi.admin.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonFind {
    /**
     parameters:
     * "Object o" is JSONObject or JSONArray that you converted
     *  from some javabean or string.....by net.sf.json
     *
     * "Map<String,Object> m" is a empty object, key is the column's
     *  name that you want to find it out from json object,
     *  value just set to null first, program will help to set
     *  the value in.
     **/
    public static ArrayList jsonFindRecursive(String strJson,String strAttrName)
    {
        JSONObject job= JSONObject.parseObject(strJson);
        ArrayList al=new ArrayList();
        jsonFindRecursive(job,strAttrName,al);
        return al;

    }

    private static void jsonFindRecursive(Object o,String strAttrName,ArrayList al)
    {
        if(o.getClass().equals(JSONObject.class))
        {
            JSONObject temp= (JSONObject)o;
            if(temp.containsKey(strAttrName))
            {
                Map<String,Object> m=new HashMap<String,Object>();
                m.put(strAttrName, temp.get(strAttrName));
                al.add(m);
            }
            Iterator it=temp.keySet().iterator();
            while(it.hasNext()){
                String key;
                key=it.next().toString();
                Object v=temp.get(key);
                if(v.getClass().equals(JSONObject.class)||v.getClass().equals(JSONArray.class))
                {
                    jsonFindRecursive(v,strAttrName,al);
                }
            }
        }
        else if(o.getClass().equals(JSONArray.class))
        {
            JSONArray tempArray=(JSONArray) o;
            for(Object ob:tempArray)
            {
                jsonFindRecursive(ob,strAttrName,al);
            }
        }
    }

}
