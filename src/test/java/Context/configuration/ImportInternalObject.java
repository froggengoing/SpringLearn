package Context.configuration;

public class ImportInternalObject {

    private String factoryName;
    private String product;

    public ImportInternalObject() {
    }

    public ImportInternalObject(String factoryName, String product) {
        this.factoryName = factoryName;
        this.product = product;
    }

    @Override
    public String toString() {
        return "ImportInternalObject{" +
                "factoryName='" + factoryName + '\'' +
                ", product='" + product + '\'' +
                '}';
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
