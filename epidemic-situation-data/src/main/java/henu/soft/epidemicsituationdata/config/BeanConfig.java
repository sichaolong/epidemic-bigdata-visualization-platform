package henu.soft.epidemicsituationdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

      /*
        配置爬虫的类
        RestTemplate
     */

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
