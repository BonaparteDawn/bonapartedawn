package github.bonapartedawn.service;

import github.bonapartedawn.common.utils.EnvironmentUtil;
import github.bonapartedawn.common.utils.FileUtil;
import java.util.Date;
import java.util.List;

/**
 * Created by Fuzhong.Yan on 17/3/27.
 */
class Test{

    public static void main(String[] args) {
        String home = EnvironmentUtil.getSystemEnv().get("STOCK_DATA");
        String filePath = FileUtil.path(home);
        System.out.println(filePath);
        List<Date> d = ExcelStockService.queryData(filePath,0,Date.class,0,12);
        for (Date date : d){
//            System.out.println(date);
        }
    }
}
