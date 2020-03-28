package stream;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.LinkedList;

public class Testlambda {


    LinkedList<String> ss;
    public  void wirte(String s){
        System.out.println(s);
    }

    public  void addFirst(String s) {
        this.ss.addFirst(s);
    }
}

