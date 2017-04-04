//package framework.util;
//
//import github.bonapartedawn.common.utils.JsonUtil;
//import github.bonapartedawn.common.utils.TreeUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TreeNodeTest {
//
//    public static void main(String[] args) {
//        toTree();
//        print();
//        runNode();
//        toList();
//    }
//    public static void toTree(){
//        System.out.println("toTree======================");
//        MenuVo root = new MenuVo(1, 0, "root") ;
//        List<MenuVo> list = newMenuItems();
//        root = TreeUtil.toTree(root, list);
//        System.out.println(JsonUtil.toJson(root));
//    }
//    public static void print(){
//        System.out.println("print======================");
//        MenuVo root = new MenuVo(1, 0, "root") ;
//        List<MenuVo> list = newMenuItems();
//        root = TreeUtil.toTree(root, list);
//        TreeUtil.printTree(root);
//    }
//    public static void runNode(){
//        System.out.println("runNode======================");
//        MenuVo root = new MenuVo(1, 0, "root");
//        List<MenuVo> list = newMenuItems();
//        root =  TreeUtil.toTree(root, list);
//        final List<String> list2 = new ArrayList<String>();
//        TreeUtil.nodeRun(root, new TreeNodeRun<Integer>() {
//            public void run(TreeNode<Integer> treeNode, int level) {
//                list2.add(treeNode.toString());
//            }
//        }, TreeUtil.traceLevel());
//        for (String string : list2) {
//            System.out.println(string);
//        }
//    }
//    public static void toList(){
//        System.out.println("toList======================");
//        MenuVo root = new MenuVo(1, 0, "root");
//        List<MenuVo> list = newMenuItems();
//        root = TreeUtil.toTree(root, list);
//        List<MenuVo> res = TreeUtil.toList(root);
//        for (MenuVo menuVo : res) {
//            System.out.println(menuVo);
//        }
//    }
//    public static List<MenuVo> newMenuItems(){
//        List<MenuVo> rs = new ArrayList<MenuVo>();
//        rs.add(new MenuVo(1, 0, "root"));
//        rs.add(new MenuVo(20, 1, "主页"));
//        rs.add(new MenuVo(3, 1, "理财产品"));
//        rs.add(new MenuVo(4, 1, "商铺"));
//        rs.add(new MenuVo(5, 1, "我"));
//        rs.add(new MenuVo(6, 20, "Logo"));
//        rs.add(new MenuVo(7, 3, "推荐标签"));
//        rs.add(new MenuVo(8, 20, "推荐产品"));
//        rs.add(new MenuVo(9, 20, "精选产品"));
//        rs.add(new MenuVo(10, 5, "资料"));
//        rs.add(new MenuVo(11, 10, "关于我们"));
//        return rs;
//    }
//}
