import com.froggengo.springlearn.project.domain.User;
import org.assertj.core.util.Lists;

import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        User user = new User();
        user.setName("fly");
        user.setAge(20);
        User user1 = new User();
        user1.setName("fly1");
        user1.setAge(21);
/*        list.add(user1);
        list.add(user);*/

/*        list.add("测试2");
        list.add("测试3");*/
        String join = String.join(",", list);//只适用于string
        System.out.println("join{}"+join);
    }
}
