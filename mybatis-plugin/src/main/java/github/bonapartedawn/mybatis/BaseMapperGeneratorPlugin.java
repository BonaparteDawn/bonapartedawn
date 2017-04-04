package github.bonapartedawn.mybatis;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * Created by Fuzhong.Yan on 16/12/4.
 */
public class BaseMapperGeneratorPlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return  true;
    }
}
