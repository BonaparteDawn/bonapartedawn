package github.bonapartedawn.common.bean.regex;

import github.bonapartedawn.common.abstructs.AbstrucRegexSize;
import org.springframework.util.Assert;

/**
 * Created by Fuzhong.Yan on 17/1/28.
 */
public class RegexBean extends AbstrucRegexSize {
    /**正则表达式*/
    private String regex = null;

    public RegexBean() {
        regex = "";
    }
    public RegexBean(String regex) {
        Assert.hasLength("regex_len0");
        this.regex = regex;
    }

    @Override
    public String toString() {
        return getRegex();
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
    @Override
    public String getCurrentRegex() {
        return getRegex();
    }
}
