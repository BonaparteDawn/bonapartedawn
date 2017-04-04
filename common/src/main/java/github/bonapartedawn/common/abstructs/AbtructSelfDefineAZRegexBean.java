package github.bonapartedawn.common.abstructs;

import github.bonapartedawn.common.bean.regex.RegexBean;
import org.springframework.util.Assert;

/**
 * 自定义正则表达式bean父类
 * @author Fuzhong.Yan
 */
public abstract class AbtructSelfDefineAZRegexBean extends RegexBean implements Runnable {
    /**
     * 生成正则表达式
     * @return
     */
    public abstract RegexBean productRegexBean();

    public void run() {
        RegexBean regexBean = productRegexBean();
        Assert.notNull(regexBean, "regexBean_null");
        setRegex(regexBean.getRegex());
    }
    @Override
    public String getRegex() {
        if (super.getRegex() == null || "".equals(super.getRegex())) {
            run();
        }
        return super.getRegex();
    }
}
