package Context.aop;

import org.springframework.stereotype.Component;

@Component
public class Annimal {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @log
    public int sale(){
        System.out.println("annimal销售");
        return 1000;
    }


}
