package github.bonapartedawn.common.interfaces;

/**
 * 对象拷贝接口
 * Created by Fuzhong.Yan on 16/11/13.
 */
public interface BeanCopy<Source,Target> {

    /**
     * 将source拷贝到输出结果里面
     * @param source
     * @return
     */
    public Target copy(Source source);
}