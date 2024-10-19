package org.example.bsproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域调用配置类
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 允许跨域调用的过滤器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许跨域路径
       registry.addMapping("/**")
               //允许请求跨域的域名
               .allowedOriginPatterns("*")
               //是否允许cookie
               .allowCredentials(true)
               //允许请求方式
               .allowedMethods("GET","POST","DELETE","PUT")
               //允许暴露的header
               .allowedHeaders("*")
               //允许跨域时间
               .maxAge(3600);
    }
}
