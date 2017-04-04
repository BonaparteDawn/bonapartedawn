package github.bonapartedawn.common.interfaces;

import java.util.List;
/**
 * 树节点
 * @author Fuzhong.Yan
 * 2017年3月17日
 */
public interface TreeNode<ID> extends Comparable<TreeNode<ID>>{
    /**
     * 当前节点的ID
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @return
     */
    public abstract ID id();
    /**
     * 当前节点的父ID
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @return
     */
    public abstract ID pid();
    /**
     * 当前节点的孩子
     * @author Fuzhong.Yan
     * 2017年3月17日
     * @return
     */
    public List<TreeNode<ID>> children();
}