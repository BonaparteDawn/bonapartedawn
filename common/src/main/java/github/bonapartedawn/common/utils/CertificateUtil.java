package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.bean.MyCertificateInfo;
import github.bonapartedawn.common.bean.MyX500Name;
import org.springframework.util.Assert;
import sun.security.x509.*;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

/**
 * 证书工具类
 */
public class CertificateUtil {
    private static SecureRandom secureRandom;
    static{
        secureRandom = RandomUtil.newInstance();
    }
    /**
     * 颁布证书
     * @param certificateInfo
     * @param appendChain
     * @param subjectPfxPath
     * @param subjectPassword
     * @param subjectCrtPath
     * @throws Exception
     */
    public static void createX509SubjectCertFile(MyCertificateInfo certificateInfo, X509Certificate[] appendChain, String subjectPfxPath, String subjectPassword, String subjectCrtPath)throws Exception {
        Assert.notNull(certificateInfo,"certificateUtil_certificateInfo_null");
        Assert.notNull(certificateInfo.getSubjectAlias(),"certificateUtil_subjectAlias_null");
        Assert.notNull(certificateInfo.getSubjectPrivateKey(),"certificateUtil_subjectPrivateKey_null");
        X509Certificate x509CertImpl = newX509SubjectCert(certificateInfo);
        X509Certificate[] chain = (X509Certificate[]) ObjectUtils.append(x509CertImpl,appendChain);
        writeKeyStore(certificateInfo.getSubjectAlias(), certificateInfo.getSubjectPrivateKey(),subjectPassword, chain, subjectPfxPath);
        saveCertificate(subjectCrtPath, x509CertImpl);
    }

    /**
     * 生成一个证书
     * @param certificateInfo
     * @return
     * @throws Exception
     */
    public static X509Certificate newX509SubjectCert(MyCertificateInfo certificateInfo)throws Exception {
        Assert.notNull(certificateInfo,"certificateUtil_certificateInfo_null");
        Assert.notNull(certificateInfo.getIssuerPrivateKey(),"certificateUtil_issuerPrivateKey_null");
        Assert.notNull(certificateInfo.getSigAlg(),"certificateUtil_sigAlg_null");
        X509CertInfo info = convert2X509CertInfo(certificateInfo);
        X509CertImpl x509CertImpl = new X509CertImpl(info);
        x509CertImpl.sign(certificateInfo.getIssuerPrivateKey(), certificateInfo.getSigAlg());
        return x509CertImpl;
    }

    /**
     * 创建根证书（证书有效期10年）
     * @param alias
     * @param rootX500Name
     * @param rootPfxPath
     * @param rootCrtPath
     * @param rootPassword
     * @throws Exception
     */
    public static void createX509RootCert(String alias, MyX500Name rootX500Name, String rootPfxPath, String rootCrtPath, String rootPassword) throws Exception{
        Assert.notNull(rootX500Name,"certificateUtil_rootX500Name_null");
        Assert.hasLength(alias,"certificateUtil_alias_len0");
        Assert.hasLength(rootPfxPath,"certificateUtil_rootPfxPath_len0");
        Assert.hasLength(rootCrtPath,"certificateUtil_rootCrtPath_len0");
        Assert.hasLength(rootPassword,"certificateUtil_rootPassword_len0");
        CertAndKeyGen rootCertAndKeyGen = newCertAndKeyGen(null, null);
        X509Certificate rootCertificate = rootCertAndKeyGen.getSelfCertificate( new X500Name(rootX500Name.getName()), 10*365 * 24L * 60L * 60L);
        writeKeyStore(alias, rootCertAndKeyGen.getPrivateKey(), rootPassword, new X509Certificate[]{rootCertificate}, rootPfxPath);
        saveCertificate(rootCrtPath, rootCertificate);
    }

    /**
     * 创建根证书（证书有效期10年）
     * @param certificateInfo
     * @param rootPfxPath
     * @param rootCrtPath
     * @param rootPassword
     * @throws Exception
     */
    public static void createX509RootCertFile(MyCertificateInfo certificateInfo,String rootPfxPath,String rootPassword, String rootCrtPath) throws Exception{
        Assert.notNull(certificateInfo,"certificateUtil_certificateInfo_null");
        Assert.notNull(certificateInfo.getSubjectName(),"certificateUtil_subjectName_null");
        Assert.notNull(certificateInfo.getSubjectAlias(),"certificateUtil_subjectAlias_null");
        Assert.notNull(certificateInfo.getStartTime(),"certificateUtil_startTime_null");
        Assert.notNull(certificateInfo.getEndTime(),"certificateUtil_endTime_null");
        X509Certificate rootCertificate = newX509RootCert(certificateInfo);
        writeKeyStore(certificateInfo.getSubjectAlias(), certificateInfo.getSubjectPrivateKey(), rootPassword, new X509Certificate[]{rootCertificate}, rootPfxPath);
        saveCertificate(rootCrtPath, rootCertificate);
    }
    public static X509Certificate newX509RootCert(MyCertificateInfo certificateInfo) throws Exception{
        Assert.notNull(certificateInfo,"certificateUtil_certificateInfo_null");
        Assert.notNull(certificateInfo.getSubjectName(),"certificateUtil_subjectName_null");
        Assert.notNull(certificateInfo.getStartTime(),"certificateUtil_startTime_null");
        Assert.notNull(certificateInfo.getEndTime(),"certificateUtil_endTime_null");
        CertAndKeyGen rootCertAndKeyGen = newCertAndKeyGen(null, null);
        certificateInfo.setSubjectPrivateKey(rootCertAndKeyGen.getPrivateKey());
        certificateInfo.setSubjectPublicKey(rootCertAndKeyGen.getPublicKey());
        long difference = TimeUtil.difference2Long(certificateInfo.getEndTime(),certificateInfo.getStartTime())/TimeUtil.SECOND;
        return rootCertAndKeyGen.getSelfCertificate( new X500Name(certificateInfo.getSubjectName().getName()),difference);
    }
    /**
     * 私钥存储设施
     * @param alias
     * @param privateKey
     * @param password
     * @param chain
     * @param filePath
     * @throws Exception
     */
    public static void writeKeyStore(String alias, PrivateKey privateKey, String password,X509Certificate[] chain, String filePath) throws Exception {
        Assert.hasLength(alias,"certificateUtil_alias_len0");
        Assert.notNull(privateKey,"certificateUtil_privateKey_null");
        Assert.hasLength(password,"certificateUtil_password_len0");
        Assert.notNull(chain,"certificateUtil_chain_null");
        Assert.hasLength(filePath,"certificateUtil_filePath_len0");
        OutputStream outputStream = FileUtil.convert2BufferedOutputStream(filePath);
        writeKeyStore(alias, privateKey, password, chain, outputStream);
        outputStream.close();
    }
    /**
     * 读取证书
     * @param crtPath
     * @return
     * @throws CertificateException
     * @throws IOException
     */
    public static Certificate readCertificate(String crtPath,String certificateType) throws CertificateException, IOException {
        Assert.hasLength(crtPath,"certificateUtil_crtPath_len0");
        certificateType = ObjectUtils.setValue(certificateType,"X.509");
        InputStream inStream = new FileInputStream(crtPath);
        CertificateFactory cf = CertificateFactory.getInstance(certificateType);
        Certificate cert = cf.generateCertificate(inStream);
        inStream.close();
        return cert;
    }

    /**
     * 保存证书
     * @param crtPath
     * @param certificate
     * @throws CertificateException
     * @throws IOException
     */
    public static void saveCertificate(String crtPath,Certificate certificate) throws CertificateException, IOException {
        Assert.hasLength(crtPath,"certificateUtil_crtPath_len0");
        Assert.notNull(certificate,"certificateUtil_startDate_null");
        FileOutputStream fos = new FileOutputStream(new File(crtPath));
        fos.write(certificate.getEncoded());
        fos.close();
    }
    public static X509CertInfo produceX509CertInfo(Date startDate,Date endDate,MyX500Name rootX500Name,MyX500Name subjectX500Name,String sigAlg,PublicKey publicKey) throws Exception{
        Assert.notNull(startDate,"certificateUtil_startDate_null");
        Assert.notNull(endDate,"certificateUtil_endDate_null");
        Assert.notNull(rootX500Name,"certificateUtil_rootX500Name_null");
        Assert.notNull(subjectX500Name,"certificateUtil_subjectX500Name_null");
        Assert.notNull(sigAlg,"certificateUtil_sigAlg_null");
        Assert.notNull(publicKey,"certificateUtil_publicKey_null");
        X509CertInfo info = new X509CertInfo();
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new Random().nextInt() & 0x7fffffff));
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(AlgorithmId.get(sigAlg)));
        info.set(X509CertInfo.ISSUER, new CertificateIssuerName(new X500Name(rootX500Name.getName())));
        info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(new X500Name(subjectX500Name.getName())));
        info.set(X509CertInfo.KEY, new CertificateX509Key(publicKey));
        info.set(X509CertInfo.VALIDITY, new CertificateValidity(startDate,endDate));
        return info;
    }
    public static X509CertInfo convert2X509CertInfo(MyCertificateInfo certificateInfo) throws Exception{
        Assert.notNull(certificateInfo,"certificateUtil_certificateInfo_null");
        Assert.notNull(certificateInfo.getSubjectPublicKey(),"certificateUtil_subjectPublicKey_null");
        Assert.notNull(certificateInfo.getIssuerName(),"certificateUtil_issuerName_null");
        Assert.notNull(certificateInfo.getSubjectName(),"certificateUtil_subjectName_null");
        Assert.notNull(certificateInfo.getVersion(),"certificateUtil_version_null");
        Assert.notNull(certificateInfo.getSerialNumber(),"certificateUtil_serialNumber_null");
        Assert.notNull(certificateInfo.getSigAlg(),"certificateUtil_sigAlg_null");
        Assert.notNull(certificateInfo.getStartTime(),"certificateUtil_startTime_null");
        Assert.notNull(certificateInfo.getEndTime(),"certificateUtil_endTime_null");
        X509CertInfo info = new X509CertInfo();
        info.set(X509CertInfo.VERSION, new CertificateVersion(certificateInfo.getVersion()));
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(certificateInfo.getSerialNumber()));
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(AlgorithmId.get(certificateInfo.getSigAlg())));
        info.set(X509CertInfo.ISSUER, new CertificateIssuerName(new X500Name(certificateInfo.getIssuerName().getName())));
        info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(new X500Name(certificateInfo.getSubjectName().getName())));
        info.set(X509CertInfo.KEY, new CertificateX509Key(certificateInfo.getSubjectPublicKey()));
        info.set(X509CertInfo.VALIDITY, new CertificateValidity(certificateInfo.getStartTime() ,certificateInfo.getEndTime()));
        return info;
    }
    /**
     * 验证证书链
     * @param rootX509certificate 根证书
     * @param chainX509Certificate 证书链
     * @param x509crl 作废证书列表
     * @param communicationTarget 现在的通信对象
     * @return
     */
    public static boolean verifyCertificateChain(X509Certificate rootX509certificate,Collection<X509Certificate> chainX509Certificate, X509CRL x509crl,MyX500Name communicationTarget) throws Exception {
        Assert.notNull(rootX509certificate,"certificateUtil_rootX509certificate_null");
        Assert.notNull(chainX509Certificate,"certificateUtil_chainX509Certificate_null");
        Assert.notNull(communicationTarget,"certificateUtil_communicationTarget_null");
        // 获取证书链长度
        int nSize = chainX509Certificate.size();
        // 将证书链转化为数组
        X509Certificate[] x509certificate = new X509Certificate[nSize];
        chainX509Certificate.toArray(x509certificate);
        // 存储证书链中证书主体信息
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();
        // 1、验证证书的所有者是下一个证书的颁布者（沿证书链自上而下）
        Principal principalLast = null;
        for (int i = 0; i < nSize; i++) {
            X509Certificate x509Certificate = x509certificate[i];
            // 获取发布者标识
            Principal principalIssuer = x509Certificate.getIssuerDN();
            // 获取证书的主体标识
            Principal principalSubject = x509Certificate.getSubjectDN();
            // 保存证书的序列号
            list.add(x509Certificate.getSerialNumber());
            if (principalLast != null) {
                // 验证证书的颁布者是上一个证书的所有者
                if (principalIssuer.equals(principalLast)) {
                    // 获取上个证书的公钥
                    PublicKey publickey = x509certificate[i - 1].getPublicKey();
                    // 2、验证是否已使用与指定公钥相应的私钥签署了此证书
                    x509certificate[i].verify(publickey);
                } else {
                    return false;
                }
            }
            principalLast = principalSubject;

        }
        if (x509crl != null) {
            // 3、验证根证书是否在撤销列表中
            if (!x509crl.getIssuerDN().equals(rootX509certificate.getSubjectDN())){
                return false;
            }
            x509crl.verify(rootX509certificate.getPublicKey());
            // 4、验证证书链中每个证书是否存在撤销列表中
            Set<? extends X509CRLEntry> setEntries = x509crl.getRevokedCertificates();// 获取被撤销的证书的列表
            if (setEntries == null && setEntries.isEmpty() == false) {
                Iterator<? extends X509CRLEntry> iterator = setEntries.iterator();
                while (iterator.hasNext()) {
                    X509CRLEntry X509crlentry = iterator.next();
                    if (list.contains(X509crlentry.getSerialNumber()))
                        return false;
                }
            }
        }
        // 5、证明证书链中的第一个证书由用户所信任的CA颁布
        PublicKey publickey = rootX509certificate.getPublicKey();
        x509certificate[0].verify(publickey);
        // 6、证明证书链中的最后一个证书的所有者正是现在通信对象
        Principal principal = x509certificate[nSize - 1].getSubjectDN();
        X500Name x500Name = new X500Name(communicationTarget.getName());
        if (!x500Name.getName().equals(principal.getName())){
            return false;
        }
        // 7、验证证书链里每个证书是否在有效期里
        Date date = new Date();
        for (int i = 0; i < nSize; i++) {
            x509certificate[i].checkValidity(date);
        }
        return true;
    }
    /**
     * 验证签名
     * @param x509certificateCA 证书
     * @param sign 加密的签名数据
     * @param original 未加密的原始数据
     * @return
     */
    public static boolean verifySign(X509Certificate x509certificateCA,byte[] sign, byte[] original) {
        Assert.notNull(x509certificateCA,"certificateUtil_x509certificateCA_null");
        Assert.notNull(sign,"certificateUtil_sign_null");
        Assert.notNull(original,"certificateUtil_original_null");
        try {
            // 获得签名实例
            Signature signature = CertificateUtil.newSignature(x509certificateCA.getSigAlgName());
            // 用证书公钥进行初始化
            signature.initVerify(x509certificateCA.getPublicKey());
            // 更新源数据
            signature.update(original);
            // 验证数字签名
            return signature.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 获得作废证书列表
     * @param inputStream
     * @return
     * @throws IOException
     * @throws CertificateException
     * @throws CRLException
     */
    public static X509CRL convert2X509CRL(InputStream inputStream) throws IOException,CertificateException, CRLException {
        Assert.notNull(inputStream,"certificateUtil_inputStream_null");
        // 指定证书类型
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // 根据CRL文件流，获得X509CRL实例
        X509CRL crl = (X509CRL) cf.generateCRL(inputStream);
        return crl;
    }

    /**
     * 写入存储设施
     * @param alias
     * @param key
     * @param password
     * @param chain
     * @param outputStream
     * @throws Exception
     */
    public static void writeKeyStore(String alias, Key key, String password,Certificate[] chain, OutputStream outputStream) throws Exception{
        Assert.hasLength(alias,"certificateUtil_alias_len0");
        Assert.notNull(outputStream,"certificateUtil_outputStream_null");
        Assert.hasLength(password,"certificateUtil_password_len0");
        Assert.notNull(chain,"certificateUtil_chain_null");
        KeyStore keyStore = CertificateUtil.newKeyStore(null);
        keyStore.load(null, password.toCharArray());
        keyStore.setKeyEntry(alias, key, password.toCharArray(), chain);
        keyStore.store(outputStream, password.toCharArray());
    }

    /**
     * 读取存储设施
     * @param alias
     * @param password
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static KeyStore readKeyStore(String alias,String password,InputStream inputStream) throws Exception {
        Assert.hasLength(alias,"certificateUtil_alias_len0");
        Assert.notNull(inputStream,"certificateUtil_inputStream_null");
        Assert.hasLength(password,"certificateUtil_password_len0");
        KeyStore keyStore = CertificateUtil.newKeyStore(null);
        keyStore.load(inputStream, password.toCharArray());
        return keyStore;
    }
    /**
     * 读取PFX文件中的私钥
     * @param alias
     * @param pfxPath
     * @param password
     * @return
     * @throws Exception
     */
    public static KeyStore readKeyStore(String alias, String pfxPath,String password) throws Exception {
        Assert.hasLength(alias,"certificateUtil_alias_len0");
        Assert.hasLength(pfxPath,"certificateUtil_pfxPath_len0");
        Assert.hasLength(password,"certificateUtil_password_len0");
        KeyStore keyStore = CertificateUtil.newKeyStore(null);
        InputStream inputStream = FileUtil.convert2BufferdInputStream(pfxPath);
        keyStore = readKeyStore(alias, password, inputStream);
        inputStream.close();
        return keyStore;
    }
    /**
     * 生成一个存储设施对象
     * @param keyStoreType
     * @return
     * @throws KeyStoreException
     */
    public static KeyStore newKeyStore(String keyStoreType) throws KeyStoreException{
        keyStoreType = ObjectUtils.setValue(keyStoreType,"pkcs12");
        return KeyStore.getInstance(keyStoreType);
    }
    /**
     * 使用指定算法生成一个签名对象
     * @param sigAlg
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Signature newSignature(String sigAlg) throws NoSuchAlgorithmException{
        Assert.hasLength(sigAlg,"certificateUtil_sigAlg_len0");
        return Signature.getInstance(sigAlg);
    }
    /**
     * 获得一个证书生成器和密钥生成器
     * @param publicSigAlg 公钥算法 默认 RSA
     * @param signatureSigAlg 签名算法 默认  MD5WithRSA
     * @return
     * @throws Exception
     */
    public static CertAndKeyGen newCertAndKeyGen(String publicSigAlg,String signatureSigAlg) throws Exception{
        publicSigAlg = ObjectUtils.setValue(publicSigAlg,"RSA");
        signatureSigAlg = ObjectUtils.setValue(signatureSigAlg,"MD5WithRSA");
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen(publicSigAlg, signatureSigAlg, null);
        certAndKeyGen.setRandom(secureRandom);
        certAndKeyGen.generate(1024);
        return certAndKeyGen;
    }
}