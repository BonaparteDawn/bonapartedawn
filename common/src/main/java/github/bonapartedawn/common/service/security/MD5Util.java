package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.interfaces.MessageSummary;
import github.bonapartedawn.common.service.MyLogFactory;
import github.bonapartedawn.common.utils.Base64Util;
import github.bonapartedawn.common.utils.FileUtil;
import github.bonapartedawn.common.utils.TimeUtil;
import org.apache.commons.logging.Log;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Fuzhong.Yan on 16/11/12.
 */
public final class MD5Util extends SecurityCoder implements MessageSummary, Serializable{

    private Log log = MyLogFactory.productLog(LogType.Default,MD5Util.class);

    /**
     * 加密
     *
     * @param data 待加密的数据
     * @param keys 密钥
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (data == null){
            return null;
        }
        MessageDigest md = getMessageDigest();
        md.update(data);
        return md.digest();
    }

    /**
     * 解密
     *
     * @param data 加了密的数据
     * @param keys 密钥
     * @return
     * @throws Exception
     */
    @Override
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        log.error("DECRYPT_UNSUPPORTED");
        throw new Exception("DECRYPT_UNSUPPORTED");//不支持解密
    }

    /**
     * 获得输入流的MD5值
     *
     * @param inputStream
     * @return
     */
    public String productSummary(InputStream inputStream) throws IOException {
        Assert.notNull(inputStream,"INPUT_STREAM_IS_NULL");
        String res = null;
        long inputSize = inputStream.available()/ FileUtil.M;
        long startTime = System.currentTimeMillis();
        try {
            MessageDigest messageDigest = getMessageDigest();
            DigestInputStream digestInputStream = new DigestInputStream(inputStream,messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer =new byte[1*FileUtil.M];
            while (digestInputStream.read(buffer) > 0);
            // 获取最终的MessageDigest
            messageDigest= digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            try {
                res = Base64Util.encode(resultByteArray);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        log.info("数据流的大小:"+inputSize+"M");
        log.info("MD5值为:"+res);
        log.info("MD5耗时:"+(endTime-startTime)/ TimeUtil.SECOND+" s");
        return res;
    }

    private MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }
}
