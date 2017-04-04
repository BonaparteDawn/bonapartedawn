package github.bonapartedawn.bddao;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 数据库访问抽象类
 * Created by Fuzhong.Yan on 16/11/4.
 */
public abstract class DBDao {
    public String namespace;
    public DBDao(){

    }
    /**
     * 查询一组对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract <T> List<T> selectObjects(String statement) throws  Exception;

    /**
     * 查询指定页的一组对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract <T> Page<T> selectObjects(String statement, Integer pageNum, Integer pageSize ) throws  Exception;

    /**
     * 查询一组对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract <T> List<T> selectObjects(String statement,Object object) throws  Exception;

    /**
     * 查询指定页的一组对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract <T> Page<T> selectObjects(String statement,Object object,Integer pageNum,Integer pageSize ) throws  Exception;


    /**
     * 查询一个对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract  <T> T selectObject(String statement) throws  Exception;

    /**
     * 查询一个对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract <T> T selectObject(String statement,Object object)  throws  Exception;

    /**
     * 批量更新一组对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract boolean updateObjects(String  statement) throws  Exception;

    /**
     * 批量更新一组对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract boolean updateObjects(String statement,Object object) throws  Exception;

    /**
     * 更新一个对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract boolean updateObject(String statement) throws  Exception;

    /**
     * 更新一个对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract boolean updateObject(String statement,Object object) throws  Exception;

    /**
     * 删除一个对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract boolean deleteObject(String statement) throws Exception;


    /**
     * 删除一个对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract boolean deleteObject(String statement ,Object object)  throws Exception;

    /**
     * 删除一组对象
     * @param statement
     * @return
     * @throws Exception
     */
    public abstract boolean deleteObjects(String statement) throws Exception;


    /**
     * 删除一组对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract boolean deleteObjects(String statement ,Object object)  throws Exception;

    /**
     * 添加一个对象
     * @param statement
     * @return 添加到数据库里面的对象
     * @throws Exception
     */
    public abstract  boolean addObject(String  statement) throws Exception;

    /**
     * 添加一个对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract  boolean addObject(String  statement ,Object object)  throws Exception;

    /**
     * 添加一组对象
     * @param statement
     * @return 添加到数据库里面的对象
     * @throws Exception
     */
    public abstract  boolean addObjects(String  statement) throws Exception;

    /**
     * 添加一组对象
     * @param statement
     * @param object
     * @return
     * @throws Exception
     */
    public abstract  boolean addObjects(String  statement ,Object object)  throws Exception;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
