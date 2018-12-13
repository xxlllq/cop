package com.xxlllq.web.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @项目名称：evget.casst
 * @类名称：WebMvcConfiguration
 * @类描述：
 * @创建人：xiangxl
 * @创建时间：2018/10/21 16:19
 * @version：
 */

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

//    /**
//     * 配置是否允许跨域请求，同时需要将ShiroConfiguration中的filterChainDefinitionMap.put("/**", "session")相关注释掉
//     *
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")//设置允许跨域的路径
////                .allowedOrigins("*")//设置允许跨域请求的域名
////                .allowedHeaders("*")
////                .allowCredentials(true)// 非必须的,表示是否允许发送Cookie,注意,当设置为true的时候,客户端的ajax请求,也需要将withCredentials属性设置为true
////                .allowedMethods("GET", "POST")//设置允许的方法
////                .maxAge(3600);//跨域允许时间
//
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST")
//                .maxAge(3600);
////        registry.addMapping("/**")
////                .allowedOrigins("*")
////                .allowCredentials(true)
////                .allowedMethods("GET", "POST", "DELETE", "PUT");
//
//    }

    /**
     * 错误页面配置
     */
    @Configuration
    public class ErrorPageConfig {
        @Bean
        public EmbeddedServletContainerCustomizer containerCustomizer() {
            return (container -> {
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/unauthorized");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/notFound");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/serverError");

                container.addErrorPages(error401Page, error404Page, error500Page);
            });
        }
    }
}