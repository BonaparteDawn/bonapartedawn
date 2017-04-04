package github.bonapartedawn.mapper;

import github.bonapartedawn.entity.KeyStore;
import github.bonapartedawn.entity.KeyStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KeyStoreMapper {
    /**
     *
     * @mbg.generated 2017-04-02
     */
    long countByExample(KeyStoreExample example);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int deleteByExample(KeyStoreExample example);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int insert(KeyStore record);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int insertSelective(KeyStore record);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    List<KeyStore> selectByExample(KeyStoreExample example);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    KeyStore selectByPrimaryKey(Long id);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int updateByExampleSelective(@Param("record") KeyStore record, @Param("example") KeyStoreExample example);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int updateByExample(@Param("record") KeyStore record, @Param("example") KeyStoreExample example);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int updateByPrimaryKeySelective(KeyStore record);

    /**
     *
     * @mbg.generated 2017-04-02
     */
    int updateByPrimaryKey(KeyStore record);
}