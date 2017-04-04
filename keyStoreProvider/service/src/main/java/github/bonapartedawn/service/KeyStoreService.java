package github.bonapartedawn.service;

import com.github.pagehelper.PageHelper;
import github.bonapartedawn.api.KeyStoreApi;
import github.bonapartedawn.common.annotations.LogInfo;
import github.bonapartedawn.common.bean.MyObject;
import github.bonapartedawn.common.enums.LogType;
import github.bonapartedawn.common.service.LogAdvice;
import github.bonapartedawn.common.utils.ObjectUtils;
import github.bonapartedawn.entity.KeyStore;
import github.bonapartedawn.entity.KeyStoreExample;
import github.bonapartedawn.mapper.KeyStoreMapper;
import github.bonapartedawn.vo.KeyStoreVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 密码库服务
 */
@Service
public class KeyStoreService extends MyObject implements KeyStoreApi{

    @Autowired
    private KeyStoreMapper keyStoreMapper;

    /**
     * 添加一个密码存储实体
     * @param keyStore 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "添加一个密码存储实体",type = LogType.Default)
    public boolean addKeyStore(KeyStoreVo keyStore) throws Exception {
        Assert.notNull(keyStore,"KeyStoreService_addKeyStore_keyStore_null");
        Assert.notNull(keyStore.getBusinessID(),"KeyStoreService_addKeyStore_keyStore_getBusinessID_null");
        Assert.hasLength(keyStore.getPassword(),"KeyStoreService_addKeyStore_keyStore_getPassword_len0");
        Assert.hasLength(keyStore.getBusinessType(),"KeyStoreService_addKeyStore_keyStore_getBusinessType_len0");
        keyStore.setTimestamp(new Date());
        if (keyStore.getRemark() == null){
            keyStore.setRemark("add");
        }
        if (this.queryKeyStore(keyStore.getBusinessType(),keyStore.getBusinessID()) != null){
            throw new Exception("KeyStoreService_addKeyStore_keyStore_exist_e");
        }
        return keyStoreMapper.insertSelective(keyStore) > 0? true:false;
    }

    /**
     * 添加一组密码存储实体
     * @param list 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "添加一组密码存储实体",type = LogType.Default)
    @Transactional(rollbackFor = Exception.class)
    public boolean addKeyStores(List<KeyStoreVo> list) throws Exception {
        Assert.notEmpty(list,"KeyStoreService_addKeyStores_list_empty");
        boolean res = true;
        for (KeyStoreVo keyStore :list){
            res = res && addKeyStore(keyStore);
        }
        return res;
    }

    /**
     * 根据ID查询密码存储实体
     * @param l 密码ID
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据ID查询密码存储实体",type = LogType.Default)
    public KeyStoreVo queryKeyStoreById(long l) throws Exception {
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andCloseNotEqualTo(1);
        c.andIdEqualTo(l);
        List<KeyStore> list = keyStoreMapper.selectByExample(e);
        KeyStore vo = null;
        if (ObjectUtils.isNotEmpty(list)){
            vo = list.get(0);
        }
        return convert2Vo(vo);
    }

    /**
     * 根据业务类型查询密码
     * @param s 业务类型
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型查询密码",type = LogType.Default)
    public List<KeyStoreVo> queryKeyStoresByBusinessType(String s) throws Exception {
        Assert.hasLength(s,"KeyStoreService_queryKeyStoresByBusinessType_s_len0");
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(s);
        return convert2Vos(keyStoreMapper.selectByExample(e));
    }

    /**
     * 根据业务类型查询密码(分页)
     * @param s 业务类型
     * @param i 指定页码(开始页为1)
     * @param i1 单页数据量
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型查询密码(分页)",type = LogType.Default)
    public List<KeyStoreVo> queryKeyStoresByBusinessType(String s, int i, int i1) throws Exception {
        Assert.hasLength(s,"KeyStoreService_queryKeyStoresByBusinessType_s_len0");
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(s);
        PageHelper.startPage(i,i1);
        return convert2Vos(keyStoreMapper.selectByExample(e));
    }

    /**
     * 查询指定页的数据
     * @param i 指定页码(开始页为1)
     * @param i1 单页数据量
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "查询指定页的数据",type = LogType.Default)
    public List<KeyStoreVo> queryKeyStores(int i, int i1) throws Exception {
        KeyStoreExample e = new KeyStoreExample();
        PageHelper.startPage(i,i1);
        List<KeyStore> lists = keyStoreMapper.selectByExample(e);
        return convert2Vos(lists);
    }

    /**
     * 根据业务类型和业务ID查询密码存储实体
     * @param s 业务类型
     * @param aLong 业务ID
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型和业务ID查询密码存储实体",type = LogType.Default)
    public KeyStoreVo queryKeyStore(String s, Long aLong) throws Exception {
        Assert.hasLength(s,"KeyStoreService_queryKeyStore_s_len0");
        Assert.notNull(s,"KeyStoreService_queryKeyStore_aLong_null");
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(s);
        c.andCloseNotEqualTo(1);
        c.andBusinessIDEqualTo(aLong);
        List<KeyStore> lists = keyStoreMapper.selectByExample(e);
        KeyStore res = null;
        if (lists.size() > 1){
            throw new Exception("keyStoreDatabase_businessType_and_businessID_unique_exception");
        }else if (lists.size() == 1){
            res = lists.get(0);
        }
        return convert2Vo(res);
    }

    /**
     * 根据ID删除密码存储实体
     * @param l 密码ID
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据ID删除密码存储实体",type = LogType.Default)
    public boolean deleteKeyStoreById(long l) throws Exception {
        return keyStoreMapper.deleteByPrimaryKey(l) > 0 ? true:false;
    }

    /**
     * 根据业务类型删除密码实体
     * @param s 业务类型
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型删除密码实体",type = LogType.Default)
    public boolean deleteKeyStoresByBusinessType(String s) throws Exception {
        Assert.hasLength(s,"KeyStoreService_deleteKeyStoresByBusinessType_s_len0");
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(s);
        return keyStoreMapper.deleteByExample(e) > 0 ? true:false;
    }

    /**
     * 根据业务类型和业务ID删除密码存储实体
     * @param s 业务类型
     * @param aLong 业务ID
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型和业务ID删除密码存储实体",type = LogType.Default)
    public boolean deleteKeyStore(String s, Long aLong) throws Exception {
        Assert.hasLength(s,"KeyStoreService_deleteKeyStore_s_len0");
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(s);
        c.andBusinessIDEqualTo(aLong);
        return keyStoreMapper.deleteByExample(e) > 0 ? true:false;
    }

    /**
     * 根据ID更新密码存储实体
     * @param keyStore 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据ID更新密码存储实体",type = LogType.Default)
    public boolean updateKeyStoreById(KeyStoreVo keyStore) throws Exception {
        Assert.notNull(keyStore,"KeyStoreService_updateKeyStoreById_keyStore_null");
        Assert.notNull(keyStore.getId(),"KeyStoreService_updateKeyStoreById_keyStore_getId_null");
        keyStore.setTimestamp(new Date());
        if (keyStore.getRemark() == null){
            keyStore.setRemark("update");
        }
        return keyStoreMapper.updateByPrimaryKeySelective(keyStore) > 0 ? true:false;
    }

    /**
     * 根据ID更新密码存储实体
     * @param list 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据ID更新密码存储实体",type = LogType.Default)
    @Transactional(rollbackFor = Exception.class)
    public boolean updateKeyStoreById(List<KeyStoreVo> list) throws Exception {
        Assert.notEmpty(list,"KeyStoreService_updateKeyStoreById_list_empty");
        boolean res = true;
        for (KeyStoreVo keyStore:list){
            res = res && updateKeyStoreById(keyStore);
        }
        return res;
    }

    /**
     * 根据业务类型更新密码实体
     * @param keyStore 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型更新密码实体",type = LogType.Default)
    public boolean updateKeyStoresByBusinessType(KeyStoreVo keyStore) throws Exception {
        Assert.notNull(keyStore,"KeyStoreService_updateKeyStoresByBusinessType_keyStore_null");
        Assert.hasLength(keyStore.getBusinessType(),"KeyStoreService_updateKeyStoresByBusinessType_keyStore_getBusinessType_len0");
        keyStore.setTimestamp(new Date());
        if (keyStore.getRemark() == null){
            keyStore.setRemark("update");
        }
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(keyStore.getBusinessType());
        return keyStoreMapper.updateByExampleSelective(keyStore,e) > 0 ? true:false;
    }

    /**
     * 根据业务类型和业务ID更新密码存储实体
     * @param keyStore 密码
     * @return
     * @throws Exception
     */
    @Override
    @LogInfo(name = "根据业务类型和业务ID更新密码存储实体",type = LogType.Default)
    public boolean updateKeyStore(KeyStoreVo keyStore) throws Exception {
        Assert.notNull(keyStore,"KeyStoreService_updateKeyStore_keyStore");
        Assert.hasLength(keyStore.getBusinessType(),"KeyStoreService_updateKeyStore_keyStore_getBusinessType_len0");
        keyStore.setTimestamp(new Date());
        if (keyStore.getRemark() == null){
            keyStore.setRemark("update");
        }
        KeyStoreExample e = new KeyStoreExample();
        KeyStoreExample.Criteria c = e.createCriteria();
        c.andBusinessTypeEqualTo(keyStore.getBusinessType());
        c.andBusinessIDEqualTo(keyStore.getBusinessID());
        return keyStoreMapper.updateByExampleSelective(keyStore,e) > 0 ? true:false;
    }

    /**
     * 当前对象销毁执行方法
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {

    }

    /**
     * 初始化当前对象
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        addAdvice(new LogAdvice<KeyStoreService>(this));
    }
    private KeyStoreVo convert2Vo(KeyStore keyStore){
        if (keyStore == null){
            return null;
        }
        KeyStoreVo vo = new KeyStoreVo();
        BeanUtils.copyProperties(keyStore,vo);
        return vo;
    }
    private List<KeyStoreVo> convert2Vos(List<KeyStore> beans){
        List<KeyStoreVo> res = new ArrayList<KeyStoreVo>();
        for (KeyStore keyStore:beans){
            res.add(convert2Vo(keyStore));
        }
        return  res;
    }
}