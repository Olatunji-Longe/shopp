package com.shopp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class LocalizationConfig {

    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.messages.cache-duration}")
    private Integer cacheDuration;

    @Value("${spring.messages.encoding}")
    private String defaultEncoding;

    @Bean(name="messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(baseName, "classpath:application", "classpath:bootstrap");
        messageSource.setCacheSeconds(cacheDuration);
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }

}
