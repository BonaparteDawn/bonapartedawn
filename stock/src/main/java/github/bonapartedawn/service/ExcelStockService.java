package github.bonapartedawn.service;

import github.bonapartedawn.common.bean.FileTree;
import github.bonapartedawn.common.interfaces.TreeNode;
import github.bonapartedawn.common.interfaces.TreeNodeRun;
import github.bonapartedawn.common.utils.FileUtil;
import github.bonapartedawn.common.utils.TreeUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.Assert;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExcelStockService {
    /**
     * 从网易财经下载下来的excel里面获取数据
     * @author Fuzhong.Yan
     * 2017年3月27日
     * @param filePath 数据文件地址
     * @param column 数据文件的列
     * @param classed 返回的数据类型
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    public static <E> List<E> queryData(final String filePath,final int column,final Class<E> classed,final int start,final int end){
        Assert.hasLength(filePath, "ExcelStockService_queryData_filePath_len0");
        Assert.notNull(classed, "ExcelStockService_classed_classed_null");
        final List<E> res = new ArrayList<E>();
        FileTree root = new FileTree(filePath);
        TreeUtil.nodeRun(root, new TreeNodeRun<String>() {
            @Override
            public void run(TreeNode<String> treeNode, int level) {
                Collections.sort(treeNode.children());
            }
        }, TreeUtil.traceLevel());
        TreeUtil.printTree(root);
        final int s = start < 0 ? 0:start;
        final Index index = new ExcelStockService().new Index(0);
        TreeUtil.nodeRun(root, new TreeNodeRun<String>() {
            @Override
            public void run(TreeNode<String> treeNode, int level) {
                FileTree temp = (FileTree) treeNode;
                if (temp.children().size()==0) {
                    try {
                        BufferedInputStream io = FileUtil.convert2BufferdInputStream(temp.queryFile().getAbsolutePath());
                        if (io != null) {
                            Workbook workbook = WorkbookFactory.create(io);
                            Sheet sheet = workbook.getSheetAt(0);
                            if (res.size() < end -s +1 || end < 0) {
                                for (int i = sheet.getLastRowNum(); i > 0; i--) {
                                    Row row = sheet.getRow(i);
                                    Cell cell = row.getCell(column);
                                    if ((res.size() < end -s +1 || end < 0) && index.index >= s) {
                                        res.add(convert(cell, classed,treeNode.id().replace(".xls", "")));
                                    }else {
                                        index.setIndex(index.getIndex()+1);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, TreeUtil.traceLevel());
        return res;
    }
    @SuppressWarnings("unchecked")
    private static <E> E convert(Cell cell,Class<E> classed,String date) throws Exception{
        E res = null;
        if (Integer.class.equals(classed)) {
            res = (E) new Integer((int) cell.getNumericCellValue());
        }else if (Long.class.equals(classed)) {
            res = (E) new Long((long) cell.getNumericCellValue());
        }else if (Short.class.equals(classed)) {
            res = (E) new Short((short) cell.getNumericCellValue());
        }else if (Double.class.equals(classed)) {
            res = (E) new Double((double) cell.getNumericCellValue());
        }else if (Float.class.equals(classed)) {
            res = (E) new Float((float) cell.getNumericCellValue());
        }else if (Byte.class.equals(classed)) {
            res = (E) new Byte((byte) cell.getNumericCellValue());
        }else if (String.class.equals(classed)) {
            res = (E) new String(cell.getStringCellValue());
        }else if (Boolean.class.equals(classed)) {
            res = (E) new Boolean(cell.getBooleanCellValue());;
        }else if (Date.class.equals(classed)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
            simpleDateFormat.parse(date+" "+cell.getStringCellValue());
            res = (E) simpleDateFormat.parse(date+" "+cell.getStringCellValue());
        }
        return res;
    }
    class Index{
        int index;
        public Index(int index) {
            super();
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }
}
