package github.bonapartedawn.mybatis;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Util {
    /**
     * 清空该文件下的所有的数据
     * @param workspaceRootPath
     */
    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        if(file.exists()){
            clearFiles(file);
        }
    }
    /**
     * 清空该文件下的所有的数据
     * @param file
     */
    public static void clearFiles(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                clearFiles(files[i]);
                files[i].delete();
            }
        }
    }
    /**
     * 判断对象为空
     * @param temp
     * @return
     */
    public static boolean isEmpty(List temp){
        boolean res = false;
        if (temp == null || temp.size() ==0){
            res = true;
        }
        return res;
    }

    /**
     * 判断对象不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(List temp){
        return !isEmpty(temp);
    }
    /**
     * 判断字符串是空
     * @param temp
     * @return
     */
    public static boolean isEmpty(String temp){
        boolean res = false;
        if (temp == null || "".equals(temp)){
            res = true;
        }
        return res;
    }

    /**
     * 判断字符串不为空
     * @param temp
     * @return
     */
    public static boolean isNotEmpty(String temp){
        return !isEmpty(temp);
    }
    /**
     * 获得用户当前工作目录
     * @return
     */
    public static String getUserDir(){
        return System.getProperty("user.dir");
    }
    /**
     * 判断对象为空
     * @param temp
     * @return
     */
    public static boolean isEmpty(Object temp){
        boolean res = false;
        if (temp == null){
            res = true;
        }
        if (temp instanceof String){
            if ("".equals(temp)){
                return true;
            }
        }else if (temp instanceof List){
            List list = (List) temp;
            if (list.size()==0){
                return true;
            }
        }else if (temp instanceof Map){
            Map map  = (Map) temp;
            if (map.size()==0){
                return true;
            }
        }
        return res;
    }
    public static String replacePointAsFileSeparator(String temp){
        String res = null;
        if (File.separator.equals("\\")) {
            res = temp.replaceAll("\\.","\\"+File.separator);
        }else {
            res = temp.replaceAll("\\.",File.separator);
        }
        return res;
    }
}
