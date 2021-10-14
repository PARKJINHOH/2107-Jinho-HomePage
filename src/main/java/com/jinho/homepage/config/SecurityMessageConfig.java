package com.jinho.homepage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class SecurityMessageConfig {

    /*
    * security_message.properties 관련 설정
    * TODO : 적용 안됨.
   * */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();

        /* message properties 위치 설정 */
        source.setBasenames("classpath:/messages/security_message");

        /* encoding 설정 */
        source.setDefaultEncoding("UTF-8");

        /* 5초간 캐싱*/
        source.setCacheSeconds(5);

        return source;
    }
}
