package github.bonapartedawn.common.service;

import github.bonapartedawn.common.utils.ObjectUtils;
import github.bonapartedawn.common.utils.PropertiesUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件帮助器
 * @author Fuzhong.Yan
 *
 */
public class EmailHelper {
    /**
     * 邮件服务器
     */
    public static String EMAILHOST = null;
    /**
     * 邮件发送者
     */
    public static String FROMEMAIL = null;
    /**
     * 邮件发送者昵称
     */
    public static String FROMNICKNAME = null;
    /**
     * 邮件发送者的密码
     */
    public static String PASSEWORD = null;
    /**
     * 邮件发送超时
     */
    public static String TIMEOUT = "10000";
    static{
        try {
            Properties p = PropertiesUtil.getPropertiesByPathMatching("classpath:email.properties");
            if (p != null){
                EMAILHOST = ObjectUtils.setValue(p.getProperty("mail.host"),"smtp.126.com");
                FROMEMAIL = ObjectUtils.setValue(p.getProperty("mail.fromEmail"),"mail_server_hoster@126.com");
                PASSEWORD = ObjectUtils.setValue(p.getProperty("mail.password"),"xigiapuemcqrgwjt");
                FROMNICKNAME = ObjectUtils.setValue(p.getProperty("mail.fromNickName"),"test");
                TIMEOUT = ObjectUtils.setValue(p.getProperty("mail.timeOut"),"10000");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建邮件发送器
     * @return
     */
    public static JavaMailSenderImpl createMailSender(){
        Assert.hasLength(EMAILHOST,"EMAILHOST_null");
        Assert.hasLength(FROMEMAIL,"FROMEMAIL_null");
        Assert.hasLength(PASSEWORD,"PASSEWORD_null");
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(EMAILHOST);
        javaMailSenderImpl.setUsername(FROMEMAIL);
        javaMailSenderImpl.setPassword(PASSEWORD);
        Properties prop = new Properties() ;
        prop.put("mail.smtp.auth", "true") ; // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout",TIMEOUT) ;
        javaMailSenderImpl.setJavaMailProperties(prop);
        return javaMailSenderImpl;
    }
    /**
     * 创建简单的邮件消息对象（发送者信息自动生成）
     * @return
     */
    public static SimpleMailMessage createSimpleMailMessage(){
        Assert.hasLength(FROMEMAIL,"FROMEMAIL_null");
        if (FROMNICKNAME == null || "".equals(FROMNICKNAME)) {
            FROMNICKNAME = FROMEMAIL;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROMNICKNAME+"<"+FROMEMAIL+">");
        simpleMailMessage.setSentDate(new Date());
        return simpleMailMessage;
    }
    /**
     * 创建复杂的邮件消息对象
     * @return
     */
    public static MimeMessage createMimeMessage(){
        return createMailSender().createMimeMessage();
    }
    /**
     * 创建复杂的邮件消息对象的帮助器（发送者自动生成）
     * @return
     */
    public static MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage) throws MessagingException{
        Assert.notNull(mimeMessage, "mimeMessage_null");
        Assert.hasLength(FROMEMAIL,"FROMEMAIL_null");
        if (FROMNICKNAME == null || "".equals(FROMNICKNAME)) {
            FROMNICKNAME = FROMEMAIL;
        }
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true,"utf-8");
        try {
            mimeMessageHelper.setFrom(FROMEMAIL,FROMNICKNAME);
            mimeMessageHelper.setSentDate(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mimeMessageHelper;
    }
}
