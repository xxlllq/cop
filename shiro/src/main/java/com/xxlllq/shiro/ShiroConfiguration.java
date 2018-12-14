package com.xxlllq.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxlllq.dataprovider.sys.mapper.PermissionMapper;
import com.xxlllq.dataprovider.sys.pojo.Permission;
import com.xxlllq.dataprovider.sys.service.IPermissionService;
import com.xxlllq.shiro.auth.AuthRealm;
import com.xxlllq.shiro.auth.CredentialsMatcher;
import com.xxlllq.shiro.auth.SessionAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名称： ShiroConfiguration
 * @类描述：shiro的配置类
 * @创建人：xiangxl
 * @创建时间：2018/12/13 11:47
 * @version：
 */
@Configuration
public class ShiroConfiguration {
//    @Autowired
//    private IPermissionService permissionService;

    //不直接注入PermissionService，是因为这里@Autowired会导致事务失败，所以用Mapper
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件过滤器
     * </br>1,配置shiro安全管理器接口securityManage;
     * </br>2,shiro 连接约束配置filterChainDefinitions;
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        //shiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 配置shiro安全管理器 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 指定要求登录时的链接
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login/");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");
        // 未授权时跳转的链接;
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauthorized");
        // filterChainDefinitions拦截器=map必须用：LinkedHashMap，因为它必须保证有序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置退出过滤器,具体的退出代码Shiro已经实现
        // 执行SecurityUtils.getSubject().logout()，会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
        filterChainDefinitionMap.put("/login/logout", "logout");

        //自定义session拦截器
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("sessionAuth", new SessionAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

//        //加载系统中的权限规则，需要权限验证的URL，可以通过如下加载配置和注解（启用注解方法在本页最后）。
        // permissionService.setDefaultFilterChain(filterChainDefinitionMap);

        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<Permission>()
                .select("id, code, url")
                .isNotNull("url")
                .eq("enable", 1)
                .orderByDesc("zindex, code"));
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                //anon权限验证，不需要sessionAuth过滤器，其他的需要
                if (!StringUtils.isBlank(permission.getCode()))
                    filterChainDefinitionMap.put(permission.getUrl(), "anon".equals(permission.getCode()) ? permission.getCode() : ("sessionAuth," + permission.getCode()));
            }
        }
        filterChainDefinitionMap.put("/** ", "user");

        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 功能描述: 身份认证realm; (账号密码校验；权限等)
     *
     * @param:
     * @return:
     * @auther: xiangxl
     * @date: 2018/10/21 15:16
     */
    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        //加入缓存管理器
        authRealm.setCacheManager(ehCacheManager());
        //使用自定义的CredentialsMatcher进行密码校验
        authRealm.setCredentialsMatcher(credentialsMatcher());
        return authRealm;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码,更改密码生成规则和校验的逻辑一致即可; ）
     *
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(authRealm());
        // 自定义缓存实现
        securityManager.setCacheManager(ehCacheManager());
        // 自定义session管理
        securityManager.setSessionManager(sessionManager());
        // 注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 缓存管理器
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * session管理器
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //开启扫描session线程，清理超时会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //禁用了url重写 去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);//默认true
        return sessionManager;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


    /**
     * 开启在thymeleaf和使用shiro标签
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    //region 开启Shiro通过注解权限

    /**
     * 功能描述: 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * * </br>Enable Shiro Annotations for Spring-configured beans. Only run after the lifecycleBeanProcessor(保证实现了Shiro内部lifecycle函数的bean执行) has run
     * * </br>不使用注解的话，可以注释掉这两个配置
     *
     * @auther: xiangxl
     * @date: 2018/10/21 16:49
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
    //endregion

}
