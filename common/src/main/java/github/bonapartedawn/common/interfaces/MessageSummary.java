package github.bonapartedawn.common.interfaces;

import java.io.IOException;
import java.io.InputStream;

/**
 * 消息摘要
 * Created by Fuzhong.Yan on 17/1/10.
 */
public interface MessageSummary {
    /**
     * 获得输入流的摘要
     * @param inputStream
     * @return
     */
    public String productSummary(InputStream inputStream) throws IOException;
}
