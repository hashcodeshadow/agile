package agile;

import agile.engine.JsoupCssSelectSnippet;
import agile.engine.JsoupFindByIdSnippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude={SpringDataWebAutoConfiguration.class, DataSourceAutoConfiguration.class})
@Slf4j
public class Application {

    public static void main(String[] args) {

        /*new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);
        */

        log.info("----Starting---");
        JsoupFindByIdSnippet.main(args);

    }


}