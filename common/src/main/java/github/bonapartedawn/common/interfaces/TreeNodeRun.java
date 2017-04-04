package github.bonapartedawn.common.interfaces;

public interface TreeNodeRun <ID> {
    /**
     * 对某个节点执行操作
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @param treeNode
     * @param level 树层级
     * @return
     */
    public void run(TreeNode<ID> treeNode, int level);
}
