package github.bonapartedawn.common.bean;

public class MyX500Name{
    /**
     * 一般名字
     */
    private String commonName = "az";
    /**
     * 地方名
     */
    private String localityName = "";
    /**
     * 州省名
     */
    private String stateOrProvinceName = "四川省";
    /**
     * 组织名
     */
    private String organizationName;
    /**
     *  组织单位名
     */
    private String organizationalUnitName;
    /**
     * 国家
     */
    private String countryName ="中国";
    /**
     * 街道地址
     */
    private String streetAddress;
    /**
     * 领域
     */
    private String domainComponent;
    /**
     * 用户ID
     */
    private String userID;

    private String name;

    public MyX500Name(String arg0){
        name = arg0;
    }
    public String getCommonName() {
        return commonName;
    }
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
    public String getLocalityName() {
        return localityName;
    }
    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }
    public String getStateOrProvinceName() {
        return stateOrProvinceName;
    }
    public void setStateOrProvinceName(String stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }
    public String getOrganizationName() {
        return organizationName;
    }
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }
    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getDomainComponent() {
        return domainComponent;
    }
    public void setDomainComponent(String domainComponent) {
        this.domainComponent = domainComponent;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getName() {
        String res =  name;
        if (name == null || "".equals(name)) {
            res =
                    "CN="+getCommonName()+","+
                            "L="+getLocalityName()+","+
                            "ST="+getStateOrProvinceName()+","+
                            "O="+getOrganizationName()+","+
                            "OU="+getOrganizationalUnitName()+","+
                            "C="+getCountryName()+","+
                            "STREET="+getStreetAddress()+","+
                            "DC="+getDomainComponent()+","+
                            "UID="+getUserID();
        }
        return res;
    }
    @Override
    public String toString() {
        return getName();
    }
}