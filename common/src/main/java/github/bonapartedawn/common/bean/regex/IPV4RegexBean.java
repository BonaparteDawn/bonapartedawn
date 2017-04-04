package github.bonapartedawn.common.bean.regex;


import github.bonapartedawn.common.abstructs.AbtructSelfDefineAZRegexBean;

/**
 * IPV4正则表达式
 * @author Fuzhong.Yan
 *
 */
public class IPV4RegexBean extends AbtructSelfDefineAZRegexBean {
    @Override
    public RegexBean productRegexBean() {
        return new RegexBean("((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)");
    }
}