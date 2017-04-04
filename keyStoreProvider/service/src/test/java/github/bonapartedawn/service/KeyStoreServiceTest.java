package github.bonapartedawn.service;

import github.bonapartedawn.api.KeyStoreApi;
import github.bonapartedawn.vo.KeyStoreVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fuzhong.Yan on 17/3/30.
 */
public  class KeyStoreServiceTest {

    private static KeyStoreApi keyStoreService;
    static {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/spring.xml");
        keyStoreService = applicationContext.getBean("keyStoreService", KeyStoreApi.class);
        String[] s = applicationContext.getBeanDefinitionNames();
    }
    public static void main(String[] args) throws Exception {
//        testAddKeyStore();
//        testAddKeyStores();
        testQueryKeyStore();
//        testQueryKeyStoreById();
//        testQueryKeyStoresByBusinessType();
//        testDeleteKeyStoreById();
//        testDeleteKeyStore();
//        testDeleteKeyStoresByBusinessType();
//        testUpdateKeyStoreById();
//        testUpdateKeyStoreById1();
//        testUpdateKeyStore();
//        testUpdateKeyStoresByBusinessType();
//        testPage();
        Thread.sleep(100000);
    }
    static void testPage() throws Exception {
        System.out.println(keyStoreService.queryKeyStores(1,2).size());
    }

    static void testAddKeyStore() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(122L);
        keyStore.setPassword("123");
        System.out.println(keyStoreService.addKeyStore(keyStore));
    }

    static void testAddKeyStores() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessID(1L);
        keyStore.setBusinessType("MEMBER");
        keyStore.setPassword("123");
        List<KeyStoreVo> temp = new ArrayList<KeyStoreVo>();
        temp.add(keyStore);
        keyStoreService.addKeyStores(temp);

    }

    static void testQueryKeyStoreById() throws Exception {
        KeyStoreVo s = keyStoreService.queryKeyStoreById(53);
        System.out.println(s.getPassword());
    }

    static void testQueryKeyStoresByBusinessType() throws Exception {
        System.out.println(keyStoreService.queryKeyStoresByBusinessType("MEMBER").size());
    }

    static void testQueryKeyStore() throws Exception {
        System.out.println(keyStoreService.queryKeyStores(1,10).size());;
    }

    static void testDeleteKeyStoreById() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(1222L);
        keyStore.setPassword("123");
        keyStoreService.addKeyStore(keyStore);
        System.out.println(keyStore.getId()+":"+keyStoreService.deleteKeyStoreById(keyStore.getId()));
    }

    static void testDeleteKeyStoresByBusinessType() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(122222L);
        keyStore.setPassword("123");
        keyStoreService.addKeyStore(keyStore);
        System.out.println(keyStore.getId()+":"+keyStoreService.deleteKeyStoresByBusinessType(keyStore.getBusinessType()));
    }

    static void testDeleteKeyStore() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(12222L);
        keyStore.setPassword("123");
        keyStoreService.addKeyStore(keyStore);
        System.out.println(keyStore.getId()+":"+keyStoreService.deleteKeyStore(keyStore.getBusinessType(),keyStore.getBusinessID()));

    }

    static void testUpdateKeyStoreById() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setId(58L);
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(11L);
        keyStore.setPassword("11");
        System.out.println(keyStoreService.updateKeyStoreById(keyStore));;
    }

    static void testUpdateKeyStoreById1() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(2L);
        keyStore.setPassword("2");
        keyStore.setId(58L);
        List<KeyStoreVo> list = new ArrayList<KeyStoreVo>();
        list.add(keyStore);
        System.out.println(keyStoreService.updateKeyStoreById(list));
    }

    static void testUpdateKeyStoresByBusinessType() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setPassword("3");
        keyStore.setId(58L);
        System.out.println(keyStoreService.updateKeyStoresByBusinessType(keyStore));
    }

    static void testUpdateKeyStore() throws Exception {
        KeyStoreVo keyStore = new KeyStoreVo();
        keyStore.setBusinessType("MEMBER");
        keyStore.setBusinessID(3L);
        keyStore.setPassword("44");
        System.out.println(keyStoreService.updateKeyStore(keyStore));
    }
}
