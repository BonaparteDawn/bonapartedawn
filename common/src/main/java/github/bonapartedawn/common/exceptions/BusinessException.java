package github.bonapartedawn.common.exceptions;

/**
 * Created by Fuzhong.Yan on 16/12/13.
 */
public class BusinessException extends Exception {
    /**
     * 异常备注
     */
    private String remark;
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, String remark) {
        super(message);
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "remark='" + remark + '\'' +
                '}';
    }
}