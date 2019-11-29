package com.ks.projectbasictools.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 78652 on 2017/11/10.
 */
public class JSONUtil {
    public static boolean isPrintException = true;

    /**
     * @Desc JSON字符串格式化
     */
    public static String formatJSONString(String JSONString) {
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int index = 0; index < JSONString.length(); index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = JSONString.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return String.valueOf(jsonForMatStr);
    }
    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    private JSONUtil() {
        throw new AssertionError();
    }

    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static Long getLong(String jsonData, String key, Long defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
        return getLong(jsonObject, key, (Long)defaultValue);
    }

    public static long getLong(String jsonData, String key, long defaultValue) {
        return getLong(jsonData, key, (Long)defaultValue);
    }

    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static Integer getInt(String jsonData, String key, Integer defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        return getInt(jsonObject, key, (Integer)defaultValue);
    }

    public static int getInt(String jsonData, String key, int defaultValue) {
        return getInt(jsonData, key, (Integer)defaultValue);
    }


    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static Double getDouble(String jsonData, String key, Double defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
        return getDouble(jsonObject, key, (Double)defaultValue);
    }


    public static double getDouble(String jsonData, String key, double defaultValue) {
        return getDouble(jsonData, key, (Double)defaultValue);
    }


    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static String getString(String jsonData, String key, String defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static String getStringCascade(JSONObject jsonObject, String defaultValue, String... keyArray) {
        if (jsonObject == null || keyArray.length<1) {
            return defaultValue;
        }

        String data = jsonObject.toString();
        for (String key : keyArray) {
            data = getStringCascade(data, key, defaultValue);
            if (data == null) {
                return defaultValue;
            }
        }
        return data;
    }


    public static String getStringCascade(String jsonData, String defaultValue, String... keyArray) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        String data = jsonData;
        for (String key : keyArray) {
            data = getString(data, key, defaultValue);
            if (data == null) {
                return defaultValue;
            }
        }
        return data;
    }


    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                String[] value = new String[statusArray.length()];
                for (int i = 0; i < statusArray.length(); i++) {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }


    public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
        if (key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static List<String> getStringList(JSONObject jsonObject, String key, List<String> defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < statusArray.length(); i++) {
                    list.add(statusArray.getString(i));
                }
                return list;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }


    public static List<String> getStringList(String jsonData, String key, List<String> defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringList(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static JSONObject getJSONObjectCascade(JSONObject jsonObject, JSONObject defaultValue, String... keyArray) {
        if (jsonObject == null || keyArray.length<1) {
            return defaultValue;
        }

        JSONObject js = jsonObject;
        for (String key : keyArray) {
            js = getJSONObject(js, key, defaultValue);
            if (js == null) {
                return defaultValue;
            }
        }
        return js;
    }


    public static JSONObject getJSONObjectCascade(String jsonData, JSONObject defaultValue, String... keyArray) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {
        if (jsonObject == null || key == null || key.length() == 0) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
        if (jsonData == null || jsonData.length() == 0) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }


    public static Map<String, String> getMap(JSONObject jsonObject, String key) {
        return JSONUtil.parseKeyAndValueToMap(JSONUtil.getString(jsonObject, key, null));
    }


    public static Map<String, String> getMap(String jsonData, String key) {

        if (jsonData == null) {
            return null;
        }
        if (jsonData.length() == 0) {
            return new HashMap<String, String>();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getMap(jsonObject, key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
        if (sourceObj == null) {
            return null;
        }

        Map<String, String> keyAndValueMap = new HashMap<String, String>();
        for (Iterator iter = sourceObj.keys(); iter.hasNext();) {
            String key = (String)iter.next();
            putMapNotEmptyKey(keyAndValueMap, key, getString(sourceObj, key, ""));

        }
        return keyAndValueMap;
    }

    public static boolean putMapNotEmptyKey(Map<String, String> map, String key, String value) {
        if (map == null || key == null || key.length() == 0) {
            return false;
        }
        map.put(key, value);
        return true;
    }


    public static Map<String, String> parseKeyAndValueToMap(String source) {
        if (source == null || source.length() == 0) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(source);
            return parseKeyAndValueToMap(jsonObject);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String getString(String json, String item, String name, int type)
    {
        String value = "";
        switch (type)
        {
            case 1:
                try
                {
                    JSONObject jsonObj = new JSONObject(json);
                    value = jsonObj.getString(name);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return "";
                }
            case 2:
                try
                {
                    JSONObject jsonObj = new JSONObject(json).getJSONObject(item);
                    value = jsonObj.getString(name);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return "";
                }
            case 3:
                try
                {
                    JSONObject demoJson = new JSONObject(json);
                    JSONArray numberList = demoJson.getJSONArray(item);
                    for (int i = 0; i < numberList.length(); i++) {
                        if (i == 0) {
                            value = numberList.getJSONObject(i).getString(name);
                        } else {
                            value = value + "\n" + numberList.getJSONObject(i).getString(name);
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return "";
                }
        }
        return value;
    }
}
