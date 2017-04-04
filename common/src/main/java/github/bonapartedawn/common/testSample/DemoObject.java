package github.bonapartedawn.common.testSample;

import java.util.List;

/**
 * Created by Fuzhong.Yan on 17/2/16.
 */
public class DemoObject {
    private String a;
    private String name;
    private int age;
    private List<String> lists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
