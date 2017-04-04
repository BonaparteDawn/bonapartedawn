package framework.util;

import github.bonapartedawn.common.testSample.DemoPeople;
import github.bonapartedawn.common.utils.ObjectUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Fuzhong.Yan on 17/2/16.
 */
public class ObjectUtilsTest {
    @Test
    public void queryModelProperties() throws Exception {
        DemoPeople object = new DemoPeople();
        List<Map.Entry<String, Object>> a = ObjectUtils.queryModelProperties(object);
        System.out.println(a.size());
    }

    @Test
    public void queryObjectProperties() throws Exception {
        List<String> temp = new ArrayList<String>();
        temp.add("nihao");
        temp.add("I");
        DemoPeople demoPeople = new DemoPeople();
        demoPeople.setLists(temp);
        demoPeople.setName("yfz");
        JSONObject a = JSONObject.fromObject(demoPeople);
        if (a!=null){
            Iterator keys = a.keys();
            while (keys.hasNext()){
                String key = String.valueOf(keys.next());
                Object value = a.get(key);
                if (value instanceof JSONArray){
                    a.getJSONArray(key);
                }else {

                }
            }
        }
    }

}