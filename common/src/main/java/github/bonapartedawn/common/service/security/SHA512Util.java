package github.bonapartedawn.common.service.security;

import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.enums.LogType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA512Util extends SecurityCoder{

    @Override
    @LogInfo(name="SHA512Util解密",type=LogType.Default)
    public byte[] encrypt(byte[] data, byte[]... keys) throws Exception {
        if (data == null){
            return null;
        }
        MessageDigest md = getMessageDigest();
        md.update(data);
        return md.digest();
    }
    @Override
    @LogInfo(name="SHA512Util解密",description="SHA512Util不支持解密",type=LogType.Default)
    public byte[] decrypt(byte[] data, byte[]... keys) throws Exception {
        throw new Exception("DECRYPT_UNSUPPORTED");//不支持解密
    }
    private MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-512");
    }
}
