package henu.soft.epidemicsituationdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yiqing")
@Configuration
@Data
public class URLConfig {
    // 国内疫情
    private String gurl;

    // 海外疫情
    private String hurl;

}

