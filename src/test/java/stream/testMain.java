package stream;

public class testMain {


    public  void mains() {
        /*方法引用：实例方法时，
         如下：实例方法addFirst只有一个参数，且该方法里面包含this，
                这是但MyBiConsumer接口参数有两个。其中一个作为this指针
        */
        MyBiConsumer<Testlambda, String> addMethod= Testlambda::addFirst;
        Testlambda testlambda = new Testlambda();
        addMethod.accept(testlambda,"你好");
        //Myconsumer<> cs=testlambda::wirte;

    }
}

