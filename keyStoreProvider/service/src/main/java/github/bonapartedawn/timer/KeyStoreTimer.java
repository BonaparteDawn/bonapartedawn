package github.bonapartedawn.timer;

import github.bonapartedawn.api.KeyStoreApi;
import github.bonapartedawn.common.utils.ObjectUtils;
import github.bonapartedawn.common.utils.TimeUtil;
import github.bonapartedawn.vo.KeyStoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 密钥定时器
 */
@Service
public class KeyStoreTimer implements  Runnable{

    @Autowired
    private KeyStoreApi keyStoreService;

    private boolean closable(KeyStoreVo temp){
        Assert.notNull(temp,"KeyStoreTimer_closable_temp_null");
        boolean res = false;
        if (temp.getExpire() != null && !temp.getExpire().equals(Long.valueOf(0)) ){
            Date time = temp.getTimestamp();
            Date endTime = TimeUtil.addMilliSeconds(time, Integer.valueOf(temp.getExpire() + ""));
            res = TimeUtil.currentTime().after(endTime);
        }
        return  res;
    }

    /**
     * 检查密钥是否过期
     */
    @Override
    public void run() {
        int index = 1;
        int pageSize = 10;
        while (true) {
            try {
                List<KeyStoreVo> temp = keyStoreService.queryKeyStores(index, pageSize);
                if (ObjectUtils.isEmpty(temp)){
                    break;
                }
                index++;
                for (KeyStoreVo t:temp){
                    if (closable(t) && !t.getClose().equals(1)){
                        t.setClose(1);
                        t.setRemark("expire");
                        keyStoreService.updateKeyStoreById(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
