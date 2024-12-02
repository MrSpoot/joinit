package com.weaw.joinit.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.weaw.joinit.configurations.component.SecurityComponent;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    private final SecurityComponent securityComponent;

    @Autowired
    public WebConfig(SecurityComponent securityComponent) {
        this.securityComponent = securityComponent;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityComponent)
                .addPathPatterns("/api/**");
    }
}
