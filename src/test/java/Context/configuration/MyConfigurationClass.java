package Context.configuration;

import Context.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ImportBook.class)
public class MyConfigurationClass {
    @Bean
    public Book MyBook(){
        return new Book("王小波","白银时代");
    }
}
