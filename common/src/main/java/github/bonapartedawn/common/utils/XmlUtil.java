package github.bonapartedawn.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;

/**
 * XML工具类
 * @author Fuzhong.Yan
 * 2017年3月31日
 */
public class XmlUtil {
    /**
     * 完整解析MAPPER
     */
    private static  XmlMapper xmlMapper;
    /**
     * 部分解析MAPPER
     */
    private static XmlMapper igoreXmlMapper;
    
    static{
        xmlMapper = new XmlMapper();
        igoreXmlMapper = new XmlMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        xmlMapper.setDateFormat(simpleDateFormat);
        xmlMapper.setSerializationInclusion(Include.ALWAYS);
        xmlMapper.configure(Feature.ALLOW_COMMENTS, true);
        xmlMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        xmlMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        xmlMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        
        igoreXmlMapper.setDateFormat(simpleDateFormat);
        igoreXmlMapper.setSerializationInclusion(Include.NON_ABSENT);
        igoreXmlMapper.configure(Feature.ALLOW_COMMENTS, true);
        igoreXmlMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        igoreXmlMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        igoreXmlMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        igoreXmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }
  
    /**
     * 将XML内容转换为对象
     * @author Fuzhong.Yan
     * 2017年3月31日
     * @param xmlContent XML内容
     * @param clazz 转换类型
     * @return
     * @throws Exception
     */
    public static <T> T toNormalObject(String xmlContent, Class<T> clazz){
        return toNormalObject(xmlContent,clazz,false);
    }
    /**
     *  
     * @author Fuzhong.Yan
     * 2017年3月31日
     * @param object
     * @return
     * @throws Exception 
     */
    public static String toNormalXml(Object object){
        return toNormalXml(object,false);  
    }
    
    /**
     * 将XML内容转换为对象
     * @author Fuzhong.Yan
     * 2017年3月31日
     * @param xmlContent XML内容
     * @param clazz 转换类型
     * @param ignore 未知属性忽略
     * @return
     * @throws Exception 
     */
    public static <T> T toNormalObject(String xmlContent, Class<T> clazz,boolean ignore){
        Assert.notNull(clazz, "XmlUtil_toNormalObject_clazz_null");
        Assert.hasLength(xmlContent,"XmlUtil_toNormalObject_xmlContent_len0");
        T res = null;
        try {
            if (ignore) {
                res = igoreXmlMapper.readValue(xmlContent, clazz);
            }else {
                res = xmlMapper.readValue(xmlContent, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     *  
     * @author Fuzhong.Yan
     * 2017年3月31日
     * @param object
     * @param ignore 数据为空是否忽略
     * @return
     * @throws Exception 
     */
    public static String toNormalXml(Object object,boolean ignore){
        Assert.notNull(object, "XmlUtil_toNormalXml_object_null");
        byte[] res = null;
        try {
            if (ignore == true) {
                res = igoreXmlMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(object);
            }else {
                res = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res == null ? null:new String(res);  
    }
}
