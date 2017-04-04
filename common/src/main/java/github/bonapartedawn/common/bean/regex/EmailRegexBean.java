package github.bonapartedawn.common.bean.regex;


import github.bonapartedawn.common.abstructs.AbtructSelfDefineAZRegexBean;

/**
 * 电子邮件正则表达式
 * @author Fuzhong.Yan
 *
 */
public class EmailRegexBean extends AbtructSelfDefineAZRegexBean {
    @Override
    public RegexBean productRegexBean() {
        RegexBean bean = new RegexBean("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        return bean;
    }
}
