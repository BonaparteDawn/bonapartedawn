package github.bonapartedawn.api;

import github.bonapartedawn.vo.KeyStoreVo;

import java.util.List;

/**
 * 密码服务接口
 * Created by Fuzhong.Yan on 17/3/30.
 */
public interface KeyStoreApi {

    /**
     * 添加一个密码存储实体
     * @param keyStore
     * @return
     * @throws Exception
     */
    public boolean addKeyStore(KeyStoreVo keyStore) throws Exception;

    /**
     * 添加一组密码存储实体
     * @param keyStores
     * @return
     * @throws Exception
     */
    public boolean addKeyStores(List<KeyStoreVo> keyStores) throws Exception;

    /**
     * 根据ID查询密码存储实体
     * @param id
     * @return
     */
    public KeyStoreVo queryKeyStoreById(long id) throws Exception;

    /**
     * 根据业务类型查询密码
     * @param businessType
     * @return
     * @throws Exception
     */
    public List<KeyStoreVo> queryKeyStoresByBusinessType(String businessType) throws Exception;

    /**
     * 根据业务类型查询密码
     * @param businessType
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<KeyStoreVo> queryKeyStoresByBusinessType(String businessType,int pageIndex,int pageSize) throws Exception;

    /**
     * 查询指定页的数据
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<KeyStoreVo> queryKeyStores(int pageIndex,int pageSize) throws Exception;


    /**
     * 根据业务类型和业务ID查询密码存储实体
     * @param businessType
     * @param businessID
     * @return
     * @throws Exception
     */
    public KeyStoreVo queryKeyStore(String businessType,Long businessID) throws Exception;

    /**
     * 根据ID删除密码存储实体
     * @param id
     * @return
     */
    public boolean deleteKeyStoreById(long id) throws Exception;

    /**
     * 根据业务类型删除密码实体
     * @param businessType
     * @return
     * @throws Exception
     */
    public boolean deleteKeyStoresByBusinessType(String businessType) throws Exception;

    /**
     * 根据业务类型和业务ID删除密码存储实体
     * @param businessType
     * @return
     * @throws Exception
     */
    public boolean deleteKeyStore(String businessType,Long businessID) throws Exception;

    /**
     * 根据ID更新密码存储实体
     * @param keyStore
     * @return
     */
    public boolean updateKeyStoreById(KeyStoreVo keyStore) throws Exception;

    /**
     * 根据ID更新密码存储实体
     * @param keyStores
     * @return
     */
    public boolean updateKeyStoreById(List<KeyStoreVo> keyStores) throws Exception;

    /**
     * 根据业务类型更新密码实体
     * @param keyStore
     * @return
     * @throws Exception
     */
    public boolean updateKeyStoresByBusinessType(KeyStoreVo keyStore) throws Exception;

    /**
     * 根据业务类型和业务ID更新密码存储实体
     * @param keyStore
     * @return
     * @throws Exception
     */
    public boolean updateKeyStore(KeyStoreVo keyStore) throws Exception;

}
