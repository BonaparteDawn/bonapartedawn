package framework.annotation;

import github.bonapartedawn.common.service.EmailHelper;
import github.bonapartedawn.common.service.security.MD5Util;
import github.bonapartedawn.common.utils.EmailUtil;
import github.bonapartedawn.common.utils.InternetUtil;
import github.bonapartedawn.common.utils.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Fuzhong.Yan on 16/11/21.
 */
public class MemberService_Test {
    @Before
    public void init(){
        ClassPathXmlApplicationContext act = new ClassPathXmlApplicationContext("classpath*:spring.xml");
    }


    public void testRegisterMember() throws Exception {
        File file = new File("/Users/Fuzhong.Yan/Downloads/1.mkv");
        FileInputStream inputStream = new FileInputStream(file);
        System.out.println(new MD5Util().productSummary(inputStream));
    }
    @Test
    public void rand() throws Exception {
        for (int i = 0 ; i < 10 ; i++){
            System.out.println(RandomUtil.generate32UUID());
        }
        System.out.println(RandomUtil.randChinese(12).length());
        System.out.println(RandomUtil.randUpperCaseEnglish(1200).length());
        System.out.println(RandomUtil.randLowerCaseEnglish(1200).length());
        System.out.println(RandomUtil.randEnglish(1200).length());
        System.out.println(RandomUtil.rangNumber(1000).length());
    }
    @Test
    public void internet(){
        try {
            System.out.println(InternetUtil.ping("127.0.0.1"));
            System.out.println(InternetUtil.doGet("http://www.baidu.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSendEmail() throws Exception {
//        Scanner scanner = new Scanner(System.in);
//        while (true){
//            emailPush();
//            emailPush1();
//            String line = scanner.next();
//            if (line.equals("0")){
//                break;
//            }
//            for (int i= 0;i<line.length();i++){
//                emailPush();
//                emailPush1();
//            }
//        }
//        System.exit(0);
    }
    public void emailPush(){
        SimpleMailMessage simpleMailMessage = EmailHelper.createSimpleMailMessage();
        simpleMailMessage.setTo("yanfuzhongchina@126.com");
        simpleMailMessage.setSubject("测试");
        simpleMailMessage.setText("test1");
        try {
            EmailUtil.push(simpleMailMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void emailPush1(){
        MimeMessage mimeMessage = EmailHelper.createMimeMessage();
        try {
            MimeMessageHelper helper = EmailHelper.createMimeMessageHelper(mimeMessage);
            helper.setTo("yanfuzhongchina@126.com");
            helper.setSubject("测试");
            helper.setText(InternetUtil.doGet("http://www.baidu.com"),true);
            EmailUtil.push(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}