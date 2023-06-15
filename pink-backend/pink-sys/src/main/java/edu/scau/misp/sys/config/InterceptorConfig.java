package edu.scau.misp.sys.config;

import edu.scau.misp.sys.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author
 * @date
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    private final TokenInterceptor tokenInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //拦截所有目录，除了白名单
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/login/save/**","/register/**"
//                        , "/list/**", "/list/queryTagName/**","/list/queryTagName1/**"
                )
                .excludePathPatterns("/**/*.html", "/**/*.js", "/**/*.css")
                .excludePathPatterns("/lab/**", "/sys/**","/controller/**");
    }
}

