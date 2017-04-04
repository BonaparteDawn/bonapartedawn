package github.bonapartedawn.common.utils;

import github.bonapartedawn.common.interfaces.TreeNode;
import github.bonapartedawn.common.interfaces.TreeNodeRun;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 树形数据结构工具类
 * @author Fuzhong.Yan
 * 2017年3月17日
 */
public final class TreeUtil {
    /**
     * 转换成树结构
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param root
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public  static <E,ID> E toTree(E root,List<? extends TreeNode<ID>> list){
        E e = null;
        if (root instanceof TreeNode) {
            TreeNode<ID> temp = (TreeNode<ID>) root;
            e = (E) toTree(temp, list, null,traceLevel());
        }
        return e;
    }
    /**
     * 转换成树结构
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param root
     * @param list
     * @param run
     * @param rootLevel 根节点的绝对位置
     * @return
     */
    public  static <E,ID> TreeNode<ID> toTree(E root,List<? extends TreeNode<ID>> list,TreeNodeRun<ID> run,int rootLevel){
        Assert.isTrue(root instanceof TreeNode, "TreeUtil_toTree_root_instanceof_false");
        Assert.notEmpty(list,"TreeUtil_toTree_list_empty");
        Assert.notNull(root, "TreeUtil_toTree_root_null");
        @SuppressWarnings("unchecked")
        TreeNode<ID> rootTemp = (TreeNode<ID>) root;
        Assert.notNull(rootTemp.id(), "TreeUtil_toTree_root_id_null");
        int level = traceLevel();
        for (int i=0; i < list.size(); i++) {
            TreeNode<ID> treeNode = list.get(i);
            if (root.equals(treeNode) || treeNode.id() == null || treeNode.pid() == null) {
                continue;
            }
            if (rootTemp.id().equals(treeNode.pid())) {
                TreeNode<ID> temp = toTree(treeNode, list,run,rootLevel);
                if (run != null) {
                    run.run(temp,level-rootLevel);
                }
                rootTemp.children().add(temp);
                list.remove(treeNode);
                i--;
            }
        }
        if (root != null) {
            Collections.sort(rootTemp.children());
        }
        return rootTemp;
    }
    /**
     * 依次遍历所有节点，并且各个节点执行run方法
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param root 根节点
     * @param run 待执行的接口
     * @param rootLevel
     * @return
     */
    public static <ID> TreeNode<ID> nodeRun(TreeNode<ID> root,TreeNodeRun<ID> run,int rootLevel){
        Assert.notNull(root,"TreeUtil_printTree_root_null");
        Assert.notNull(root.id(), "TreeUtil_printTree_root_id_null");
        int level = traceLevel();
        for (TreeNode<ID> temp : root.children()) {
            if (temp.id() == null || temp.pid() == null) {
                continue;
            }
            nodeRun(temp,run,rootLevel);
        }
        if (ObjectUtils.isNotEmpty(root)){
            run.run(root,level-rootLevel);
        }
        return root;
    }
    /**
     * 打印该树形结构
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param root
     */
    public static <ID> void printTree(TreeNode<ID> root){
        TreeUtil.nodeRun(root, new TreeNodeRun<ID>() {
            public void run(TreeNode<ID> treeNode,int level) {
                String spaces ="";
                for (int i = 1; i < level; i++) {
                    spaces += "\t";
                }
                System.out.println(spaces+treeNode);
            }
        },traceLevel());
    }
    /**
     * 位置追踪
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @return
     */
    public static int traceLevel(){
        return Thread.currentThread().getStackTrace().length;
    }
    /**
     * 将根节点及其孩子节点转化为List结构
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param e
     * @return
     */
    public static <E,ID> List<E> toList(E e){
        final List<E> list = new ArrayList<E>();
        if (e instanceof TreeNode) {
            @SuppressWarnings("unchecked")
            TreeNode<ID> root = (TreeNode<ID>) e;
            TreeUtil.nodeRun(root, new TreeNodeRun<ID>() {
                @SuppressWarnings("unchecked")
                public void run(TreeNode<ID> treeNode, int level) {
                    list.add((E) treeNode);
                }
            }, traceLevel());
        }
        return list;
    }
}