package github.bonapartedawn.common.bean;

import github.bonapartedawn.common.utils.ProxyUtil;
import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义根对象
 * @author Fuzhong.Yan
 * 2017年3月13日
 */
public abstract class MyObject extends Object implements FactoryBean<Object>,InitializingBean,DisposableBean{
    private List<Advice> advices = new ArrayList<Advice>();
    @Override
    public Object getObject() throws Exception {
        List<Advice> list = new ArrayList<Advice>();
        Advice[] advices = new Advice[list.size()];
        advices = getAdvices().toArray(advices);
        return ProxyUtil.newProxyInstance2(this,advices);
    }
    
    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    protected void addAdvice(Advice ...advice){
        if (advice != null) {
            for (Advice temp : advice) {
                if (temp != null) {
                    advices.add(temp);
                }
            }
        }
    }
    public List<Advice> getAdvices() {
        return advices;
    }

    public void setAdvices(List<Advice> advices) {
        this.advices = advices;
    }
}
