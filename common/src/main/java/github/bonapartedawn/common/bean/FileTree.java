package github.bonapartedawn.common.bean;

import github.bonapartedawn.common.interfaces.TreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 以树形结构读取某个目录文件
 * @author Fuzhong.Yan
 * 2017年3月27日
 */
public class FileTree implements TreeNode<String>{
    private String id;
    private String pid;
    private List<TreeNode<String>> children = new ArrayList<TreeNode<String>>();
    private File file;
    public FileTree(){
    }
    public FileTree(String path){
        file = new File(path);
        FileTree fullFileTree = equitpFile(file);
        children.addAll(fullFileTree.children());
        id = fullFileTree.id();
        pid = fullFileTree.pid();
    }
    
    private FileTree  equitpFile(File root){
        FileTree res = new FileTree();
        res.setId(root.getName());
        res.setPid(root.getParentFile().getName());
        File[] files = root.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                FileTree tempFile = equitpFile(file);
                tempFile.file = file;
                res.children().add(tempFile);
            }
        }
        return res;
    }
    
    public int compareTo(TreeNode<String> arg0) {
        FileTree FileTree = (FileTree) arg0;
        return -this.id().compareTo(FileTree.id());
    }

    public String id() {
        return id;
    }

    public String pid() {
        return pid;
    }

    public List<TreeNode<String>> children() {
        return children;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public File queryFile() {
        return file;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Override
    public String toString() {
        return "FileTree [id=" + id + ", pid=" + pid + "]";
    }
    
}
