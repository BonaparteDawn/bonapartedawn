package github.bonapartedawn.common.bean;

import github.bonapartedawn.common.utils.EnvironmentUtil;
import github.bonapartedawn.common.utils.RandomUtil;
import github.bonapartedawn.common.utils.TimeUtil;
import sun.security.x509.CertificateVersion;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * 证书信息
 * @author Fuzhong.Yan
 */
public class MyCertificateInfo {
    private String subjectAlias;
    private String issuerAlias;
    /**
     * 证书有效期开始时间
     */
    private Date startTime = EnvironmentUtil.getOperatingSystemCurrentTime();
    /**
     * 证书有效期结束时间(默认30年)
     */
    private Date endTime = TimeUtil.addYear(startTime,30);
    /**
     * 证书算法
     */
    private String sigAlg = "MD5WithRSA";
    /**
     * 证书版本
     */
    private int version = CertificateVersion.V3;
    /**
     * 序列号
     */
    private int serialNumber = RandomUtil.rand() & 0x7fffffff;

    private MyX500Name issuerName = new MyX500Name("");
    private PublicKey issuerPublicKey;
    private PrivateKey issuerPrivateKey;

    private MyX500Name subjectName = new MyX500Name("");
    private PublicKey subjectPublicKey;
    private PrivateKey subjectPrivateKey;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSigAlg() {
        return sigAlg;
    }

    public void setSigAlg(String sigAlg) {
        this.sigAlg = sigAlg;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public MyX500Name getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(MyX500Name issuerName) {
        this.issuerName = issuerName;
    }

    public MyX500Name getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(MyX500Name subjectName) {
        this.subjectName = subjectName;
    }

    public PublicKey getIssuerPublicKey() {
        return issuerPublicKey;
    }

    public void setIssuerPublicKey(PublicKey issuerPublicKey) {
        this.issuerPublicKey = issuerPublicKey;
    }

    public PrivateKey getIssuerPrivateKey() {
        return issuerPrivateKey;
    }

    public void setIssuerPrivateKey(PrivateKey issuerPrivateKey) {
        this.issuerPrivateKey = issuerPrivateKey;
    }

    public PublicKey getSubjectPublicKey() {
        return subjectPublicKey;
    }

    public void setSubjectPublicKey(PublicKey subjectPublicKey) {
        this.subjectPublicKey = subjectPublicKey;
    }

    public PrivateKey getSubjectPrivateKey() {
        return subjectPrivateKey;
    }

    public void setSubjectPrivateKey(PrivateKey subjectPrivateKey) {
        this.subjectPrivateKey = subjectPrivateKey;
    }

    public String getSubjectAlias() {
        return subjectAlias;
    }

    public void setSubjectAlias(String subjectAlias) {
        this.subjectAlias = subjectAlias;
    }

    public String getIssuerAlias() {
        return issuerAlias;
    }

    public void setIssuerAlias(String issuerAlias) {
        this.issuerAlias = issuerAlias;
    }
}

