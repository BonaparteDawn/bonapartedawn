package github.bonapartedawn.mybatis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @goal MyBatisClean
 * @requiresProject false
 * Created by Fuzhong.Yan on 17/2/27.
 */

public class MyBatisCleanPlugin extends AbstractMojo{
    /**
     * mybatis-generator.xml的原始的配置文件的位置
     * @parameter expression="${path}"
     */
    private String path;
    
    public MyBatisCleanPlugin(){
    }
    public static void main(String[] args) throws Exception {
        MyBatisCleanPlugin myBatisCleanPlugin = new MyBatisCleanPlugin();
        myBatisCleanPlugin.execute();
    }
    /**
     * 读取配置文件的根元素
     */
    public Element readRoot(String path){
        notEmpty(path, "MyBatisCleanPlugin_readRoot_path_empty");
        Element rootElement = null;
        SAXReader reader = new SAXReader();
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/resources/"+path));
            Document document = reader.read(inputStream);
            rootElement = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootElement;
    }
    /**
     * 清空指定文件下的所有文件
     * @author Fuzhong.Yan
     * 2017年3月14日
     * @param paths
     */
    public void clearPath(List<String > paths){
        notEmpty(paths, "MyBatisCleanPlugin_clearPath_paths_empty");
        for (String path:paths){
            getLog().info("清空文件内容："+path);
            Util.clearFiles(path);
        }
    }
    /**
     * 根据元素分析需要删除的文件
     * @author Fuzhong.Yan
     * 2017年3月14日
     * @param elements
     * @return
     */
    public List<String> findNeedDeletePath(List<Element> elements){
        notEmpty(elements, "MyBatisCleanPlugin_findNeedDeletePath_elements_empty");
        List<String> res = new ArrayList<String>();
        for (Element element :elements){
            @SuppressWarnings("unchecked")
            List<Element> p = element.elements();
            if (Util.isNotEmpty(p)){
                for (Element e : p){
                    Attribute targetProject = e.attribute("targetProject");
                    Attribute targetPackage = e.attribute("targetPackage");
                    if (targetPackage!=null &&  targetProject!=null){
                        String targetProject_v = targetProject.getValue();
                        String targetPackage_v = targetPackage.getValue();
                        if (Util.isNotEmpty(targetProject_v) && Util.isNotEmpty(targetPackage_v)){
                            targetPackage_v = Util.replacePointAsFileSeparator(targetPackage_v);
                            String relativePath = targetProject.getValue()+"/"+targetPackage_v;
                            File file = new File(Util.getUserDir()+"/"+relativePath);
                            if (file.exists()){
                                res.add(Util.getUserDir()+"/"+relativePath);
                            }else {
                                getLog().info("文件"+file.getAbsolutePath()+"不存在");
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
    /**
     * 通过根文件寻找上下文元素
     * @author Fuzhong.Yan
     * 2017年3月14日
     * @param rootElement
     * @return
     */
    public List<Element> findContextElements(Element rootElement){
        notEmpty(path, "MyBatisCleanPlugin_findContextElements_rootElement_empty");
        @SuppressWarnings("unchecked")
        List<Element> els = rootElement.elements();
        List<Element> res = new ArrayList<Element>();
        for (Element temp : els){
            if (temp.getName().equals("context")){
                res.add(temp);
            }
        }
        return res;
    }
    /**
     * 执行插件
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        notEmpty(path, "MyBatisCleanPlugin_execute_path_empty");
        Element root = readRoot(path);
        List<Element> context = findContextElements(root);
        if (Util.isNotEmpty(context)) {
            List<String> needDelete = findNeedDeletePath(context);
            if (Util.isNotEmpty(needDelete)) {
                clearPath(needDelete);
            }
        }
    }
    public void notEmpty(Object object,String message){
        if (Util.isEmpty(object)) {
            getLog().error(message);
        }
    }
}
