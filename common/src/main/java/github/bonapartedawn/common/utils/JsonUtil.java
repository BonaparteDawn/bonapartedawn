package github.bonapartedawn.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by Fuzhong.Yan on 16/12/3.
 */
public class JsonUtil {
    /**
     * 完整解析MAPPER
     */
    private static  ObjectMapper objectMapper;
    /**
     * 部分解析MAPPER
     */
    private static ObjectMapper ignoreObjectMapper;

    static {
        objectMapper = new ObjectMapper();
        ignoreObjectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(simpleDateFormat);
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        objectMapper.configure(Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        ignoreObjectMapper.setDateFormat(simpleDateFormat);
        ignoreObjectMapper.setSerializationInclusion(Include.NON_ABSENT);
        ignoreObjectMapper.configure(Feature.ALLOW_COMMENTS, true);
        ignoreObjectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        ignoreObjectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        ignoreObjectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        ignoreObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }
    public static String toJson(Object o){
        return  toJson(o,false);
    }

    public static <T> T toObject(String json,Class<T> type){
        return toObject(json,type,false);
    }

    /**
     *
     * @param o
     * @param ignore 是否忽略为空的属性
     * @return
     */
    public static String toJson(Object o,boolean ignore){
        String res = null;
        try {
            if (ignore){
                res = ignoreObjectMapper.writeValueAsString(o);
            }else {
                res = objectMapper.writeValueAsString(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  res;
    }

    /**
     * 由对象转对象
     * @param json
     * @param type
     * @param ignore 是否忽略不匹配的属性
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json,Class<T> type,boolean ignore){

        T t = null;
        try {
            if (ignore){
                t = ignoreObjectMapper.readValue(json, type);
            }else {
                t = objectMapper.readValue(json, type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

}
