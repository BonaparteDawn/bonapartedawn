package github.bonapartedawn.entity;

import java.util.Date;

public class KeyStore {
    /**
     * 密码库id
     */
    private Long id;

    /**
     * 业务ID
     */
    private Long businessID;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 盐
     */
    private String salt;

    /**
     * 密码
     */
    private String password;

    /**
     * 时间戳
     */
    private Date timestamp;

    /**
     * 超时（单位：毫秒，默认为0即永远不超时）
     */
    private Long expire;

    /**
     * 关闭服务(0、开启；1、关闭)
     */
    private Integer close;

    /**
     * 备注
     */
    private String remark;

    /**
     * 密码库id
     * @return id 密码库id
     */
    public Long getId() {
        return id;
    }

    /**
     * 密码库id
     * @param id 密码库id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 业务ID
     * @return businessID 业务ID
     */
    public Long getBusinessID() {
        return businessID;
    }

    /**
     * 业务ID
     * @param businessID 业务ID
     */
    public void setBusinessID(Long businessID) {
        this.businessID = businessID;
    }

    /**
     * 业务类型
     * @return businessType 业务类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 业务类型
     * @param businessType 业务类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    /**
     * 盐
     * @return salt 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 盐
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 时间戳
     * @return timestamp 时间戳
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 时间戳
     * @param timestamp 时间戳
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 超时（单位：毫秒，默认为0即永远不超时）
     * @return expire 超时（单位：毫秒，默认为0即永远不超时）
     */
    public Long getExpire() {
        return expire;
    }

    /**
     * 超时（单位：毫秒，默认为0即永远不超时）
     * @param expire 超时（单位：毫秒，默认为0即永远不超时）
     */
    public void setExpire(Long expire) {
        this.expire = expire;
    }

    /**
     * 关闭服务(0、开启；1、关闭)
     * @return close 关闭服务(0、开启；1、关闭)
     */
    public Integer getClose() {
        return close;
    }

    /**
     * 关闭服务(0、开启；1、关闭)
     * @param close 关闭服务(0、开启；1、关闭)
     */
    public void setClose(Integer close) {
        this.close = close;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}