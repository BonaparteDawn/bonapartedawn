package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.bean.EmailBox;
import github.bonapartedawn.common.runnable.MimeMessageConsumerRunnable;
import github.bonapartedawn.common.runnable.SimpleEmailConsumerRunnable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Fuzhong.Yan on 17/1/16.
 */
public class EmailUtil {
    /**
     * 简单邮件处理线程是否创建
     */
    private static boolean SIMPLEMAILMESSAGEQUEUE_THREAD = false;
    /**
     * 复杂邮件处理线程是否创建
     */
    private static boolean MIMEMESSAGEQUEUE_THREAD = false;
    /**
     * 简单邮件处理线程的名字
     */
    private static String SIMPLEMAILMESSAGEQUEUE_THREAD_NAME = "SIMPLEMAILMESSAGEQUEUE_THREAD_NAME";
    /**
     * 复杂邮件处理线程的名字
     */
    private static String MIMEMESSAGEQUEUE_THREAD_NAME = "MIMEMESSAGEQUEUE_THREAD_NAME";
    /**
     * 投递邮件
     * @param simpleMailMessage
     * @throws InterruptedException
     */
    public static void push(SimpleMailMessage simpleMailMessage) throws InterruptedException{
        Assert.notNull(simpleMailMessage, "simpleMailMessage_null");
        Assert.hasLength(simpleMailMessage.getText(),"text_null");
        Assert.noNullElements(simpleMailMessage.getTo(),"to_noNullElements");
        Assert.notNull(simpleMailMessage.getFrom(), "from_null");
        if (SIMPLEMAILMESSAGEQUEUE_THREAD == false){
            if (!EnvironmentUtil.existThread(SIMPLEMAILMESSAGEQUEUE_THREAD_NAME)){
                Thread thread = ThreadUtil.createThread(new SimpleEmailConsumerRunnable(ThreadUtil.getThreadPool()),SIMPLEMAILMESSAGEQUEUE_THREAD_NAME);
                if (ObjectUtils.isNotEmpty(thread)){
                    thread.start();
                }
            }
        }
        EmailBox.push(simpleMailMessage);
    }
    /**
     * 取邮件
     * @param mimeMessage
     * @throws InterruptedException
     */
    public static void push(MimeMessage mimeMessage) throws InterruptedException{
        Assert.notNull(mimeMessage, "mimeMessage_null");
        try {
            Assert.notNull(mimeMessage.getAllRecipients(),"allRecipients_null");
            Assert.noNullElements(mimeMessage.getFrom(),"from_null");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (MIMEMESSAGEQUEUE_THREAD == false){
            if (!EnvironmentUtil.existThread(MIMEMESSAGEQUEUE_THREAD_NAME)){
                Thread thread = ThreadUtil.createThread(new MimeMessageConsumerRunnable(ThreadUtil.getThreadPool()),SIMPLEMAILMESSAGEQUEUE_THREAD_NAME);
                if (ObjectUtils.isNotEmpty(thread)){
                    thread.start();
                }
            }
        }
        EmailBox.push(mimeMessage);
    }
}