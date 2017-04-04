package github.bonapartedawn.common.bean;


import github.bonapartedawn.common.enums.ServerResponseCode;
import github.bonapartedawn.common.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 服务器响应第三方平台抽象对象
 * Created by Fuzhong.Yan on 16/10/30.
 */
public class ResultEntity {
    /**
     * 服务器唯一ID
     */
    private  String server_uuid = "none";
    /**
     * 服务器称呼
     */
    private  String server_name ="none";
    /**
     * 服务器响应码
     */
    private ServerResponseCode status = ServerResponseCode.FAILED;
    /**
     * 客服端错误参数
     */
    private String[] error_args;
    /**
     * 服务器响应消息
     */
    private  String message = "FAILED!";
    public ResultEntity(){

    }

    public ResultEntity(ServerResponseCode status,String message){
        this.status = status;
        this.message = message;
    }

    public ResultEntity(String server_uuid,String server_name,ServerResponseCode status,String message){
        this.status = status;
        this.message = message;
        this.server_uuid = server_uuid;
        this.server_name = server_name;
    }

    /**
     * 服务器返回数据
     */
    private Map<String ,Object> data;

    public String getServer_uuid() {
        return server_uuid;
    }

    public void setServer_uuid(String server_uuid) {
        this.server_uuid = server_uuid;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public ServerResponseCode getStatus() {
        return status;
    }

    public void setStatus(ServerResponseCode status) {
        this.status = status;
    }

    public String[] getError_args() {
        return error_args;
    }

    public void setError_args(String[] error_args) {
        this.error_args = error_args;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        if (data == null){
            data = new HashMap<String, Object>();
        }
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
