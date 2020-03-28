package Context.configuration;

import org.springframework.context.annotation.Bean;

public class ImportBook {

    private String city;
    private String country;

    public ImportBook() {
    }

    public ImportBook(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "ImportBook{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Bean
    public ImportInternalObject internal(){
        ImportInternalObject internalObject = new ImportInternalObject("娃哈哈", "牛奶");
        return internalObject;
    }
}
