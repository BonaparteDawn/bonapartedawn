package framework.util;

import github.bonapartedawn.common.testSample.DemoPeople;
import github.bonapartedawn.common.utils.JsonUtil;
import github.bonapartedawn.common.utils.XmlUtil;

/**
 * Created by Fuzhong.Yan on 17/4/1.
 */
public class JsonUtilTest {
    public static void main(String[] args) throws Exception {
        DemoPeople demoPeople = new DemoPeople();
        demoPeople.setName("nihao");
        System.out.println(JsonUtil.toJson(demoPeople));
        System.out.println(XmlUtil.toNormalXml(demoPeople));
    }
}
