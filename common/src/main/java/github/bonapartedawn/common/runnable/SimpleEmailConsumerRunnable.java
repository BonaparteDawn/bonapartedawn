package github.bonapartedawn.common.runnable;

import github.bonapartedawn.common.bean.EmailBox;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.service.EmailHelper;
import github.bonapartedawn.common.service.MyLogFactory;
import github.bonapartedawn.common.service.ThreadPoolService;
import org.apache.commons.logging.Log;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;

/**
 * 简单邮件消费者
 * @author Fuzhong.Yan
 */
public class SimpleEmailConsumerRunnable implements Runnable {
    private Log log = MyLogFactory.productLog(LogType.Default,SimpleEmailConsumerRunnable.class);
    private ThreadPoolService threadPoolService = null;
    public SimpleEmailConsumerRunnable() {
    }
    public SimpleEmailConsumerRunnable(ThreadPoolService threadPoolService) {
        this.threadPoolService = threadPoolService;
    }
    public void run() {
        while (true) {
            try {
                JavaMailSenderImpl javaMailSenderImpl = EmailHelper.createMailSender();
                SimpleMailMessage simpleMailMessage = EmailBox.takeSimpleMailMessage();
                if (threadPoolService == null) {
                    log.info("取得邮件开始邮件服务");
                    new EmailService(javaMailSenderImpl,simpleMailMessage).run();
                    log.info("已经完成邮件服务");
                }else {
                    log.info("取得邮件准备投入线程池");
                    threadPoolService.execute(new EmailService(javaMailSenderImpl,simpleMailMessage));
                    log.info("已经投入邮件线程池");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public ThreadPoolService getThreadPoolService() {
        return threadPoolService;
    }
    public void setThreadPoolService(ThreadPoolService threadPoolService) {
        this.threadPoolService = threadPoolService;
    }
    /**
     * 电子邮件服务
     * @author Fuzhong.Yan
     */
    class EmailService implements Runnable {
        private MailSender mailSender = null;
        private SimpleMailMessage simpleMailMessage = null;
        public EmailService(MailSender mailSender,SimpleMailMessage simpleMailMessage) {
            Assert.notNull(mailSender,"mailSender_null");
            Assert.notNull(simpleMailMessage,"simpleMailMessage_null");
            this.mailSender = mailSender;
            this.simpleMailMessage = simpleMailMessage;
        }
        public void run() {
            Assert.notNull(mailSender,"mailSender_null");
            Assert.notNull(simpleMailMessage,"simpleMailMessage_null");
            try {
                mailSender.send(simpleMailMessage);
                log.info("发送成功");
            } catch (MailException e) {
                log.info("发送失败:"+e.getMessage());
            }
        }
    }
}
