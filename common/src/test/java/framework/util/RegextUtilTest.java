package framework.util;

import github.bonapartedawn.common.bean.regex.DoubleRegexBean;
import github.bonapartedawn.common.utils.RegextUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Fuzhong.Yan on 17/1/28.
 */
public class RegextUtilTest {
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void test(){
        String test = "+0.123 10 +0 0  12.123 <img a='Aa1234' a='' b> <img>     333a你 fadas dfs好 000 $1.234B嘿#9528@12.com123 @123.com嘿+2.345嗯--1.23A-我 2012-01-01 12:00 2012-01-01:00知道123爱你123 -123 <img a>123</img> <img a='123'>123</img> http://www.baidu.com http://a 255.255.255.255....";
        System.out.println(new DoubleRegexBean());
        List<String> res = RegextUtil.findGroup(new DoubleRegexBean(), test);
        for (String string : res) {
            System.out.println(string);
            System.out.println(RegextUtil.match(new DoubleRegexBean(), string));
        }
    }
}