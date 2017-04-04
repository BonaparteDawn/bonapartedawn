package github.bonapartedawn.common.bean.regex;


import github.bonapartedawn.common.abstructs.AbtructSelfDefineAZRegexBean;
import github.bonapartedawn.common.service.RegexFactory;
import github.bonapartedawn.common.utils.RegextUtil;

/**
 * 浮点数正则表达式（包括整数）
 * @author Fuzhong.Yan
 *
 */
public class DoubleRegexBean extends AbtructSelfDefineAZRegexBean {
    @Override
    public RegexBean productRegexBean() {
        //符号
        RegexBean symbol = RegexFactory.selfdefine("[+-]").LEOne();
        //小于1的浮点数
        RegexBean lessThan1 = RegextUtil.operation().append("0").append("[.]").append(RegexFactory.NUMBER.GEOne()).toBean();
        //大于1的浮点数
        RegexBean greatThan1 = RegextUtil.operation().appendAnyChar("1-9").append(RegexFactory.NUMBER.GEZero()).appendAnyChar(".").append(RegexFactory.NUMBER.GEOne()).toBean();
        //整数非0
        RegexBean inteter = RegextUtil.operation().appendAnyChar("1-9").append(RegexFactory.NUMBER.GEZero()).toBean();
        //整数0
        RegexBean zero = RegexFactory.selfdefine("0");
        //组合生成
        return RegextUtil.operation().append(symbol).appendOr(greatThan1,lessThan1,inteter,zero).toBean();
    }
}
