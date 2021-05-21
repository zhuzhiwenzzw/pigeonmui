package top.zhuzhiwen.pigeonmui.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;
import top.zhuzhiwen.pigeonmui.filter.ResourceCheckFilter;
import top.zhuzhiwen.pigeonmui.permission.UrlPermissionResovler;
import top.zhuzhiwen.pigeonmui.realm.MyShiroRealm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author CoolWen
 * @version 2018-11-01 7:44
 */
@Configuration
@EnableTransactionManagement
public class ShiroConfig {

    protected static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public ResourceCheckFilter resourceCheckFilter() {
        ResourceCheckFilter resourceCheckFilter = new ResourceCheckFilter();
        resourceCheckFilter.setErrorUrl("405");
        return resourceCheckFilter;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明  authc
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        logger.debug("Shiro拦截器工厂类注入开始");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/user/list");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/405");
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        Map<String, String> chains = new HashMap();
        //配置不会被拦截的链接，顺序判断
        filterChainDefinitionMap.put("/index/index", "anon");
        filterChainDefinitionMap.put("/index/main", "anon");
//        filterChainDefinitionMap.put("/index/shouye", "anon");
//        filterChainDefinitionMap.put("/index/new", "anon");
//        filterChainDefinitionMap.put("/index/news", "anon");
//        filterChainDefinitionMap.put("/index/userpg", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/js/*/*/*", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/user/**", "authc,resourceCheckFilter");
        filterChainDefinitionMap.put("/admin/**", "authc,resourceCheckFilter");
        filterChainDefinitionMap.put("/index/renzhengList", "authc,resourceCheckFilter");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/405", "anon");
        filterChainDefinitionMap.put("/upload", "anon");
        filterChainDefinitionMap.put("/barcode", "anon");
        filterChainDefinitionMap.put("/common", "anon");
        filterChainDefinitionMap.put("/download", "anon");
        filterChainDefinitionMap.put("/fonts", "anon");
//        filterChainDefinitionMap.put("/download", "anon");
//        chains.put("/admin/*","roles[Admin]");
        //配置退出  过滤器，其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/logout", "anon");
        logger.debug("filterChainDefinitionMap" + filterChainDefinitionMap);
        logger.debug("Shiro拦截器工厂类注入成功");
//        filterChainDefinitionMap.put("/**", "authc");//需要登录访问的资源 , 一般将/**放在最下边
        filterChainDefinitionMap.put("/index/**", "authc");//需要登录访问的资源 , 一般将/**放在最下边
        filterChainDefinitionMap.put("/news/**", "authc");//需要登录访问的资源 , 一般将/**放在最下边
        filterChainDefinitionMap.put("/userInfo/**", "authc");//需要登录访问的资源 , 一般将/**放在最下边
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现
        securityManager.setCacheManager(ehCacheManager());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public UrlPermissionResovler urlPermissionResovler() {
        UrlPermissionResovler urlPermissionResovler = new UrlPermissionResovler();
        return urlPermissionResovler;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        myShiroRealm.setPermissionResolver(urlPermissionResovler());
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthenticationCacheName("shiro");
        myShiroRealm.setAuthorizationCacheName("shiro");
        myShiroRealm.setCacheManager(ehCacheManager());
/*        <property name="authenticationCachingEnabled" value="true"/>
	 	<property name="authenticationCacheName" value="shiro-authenticationCache"/>
	 	<property name="authorizationCachingEnabled" value="true"/>
	 	<property name="authorizationCacheName" value="shiro-authorizationCache"/>*/
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;加密方式
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
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


//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
//        return lifecycleBeanPostProcessor;
//    }

    /**
     * ehcache缓存管理器；shiro整合ehcache：
     * 通过安全管理器：securityManager
     * 单例的cache防止热部署重启失败
     *
     * @return EhCacheManager
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        logger.debug("=====shiro整合ehcache缓存：ShiroConfiguration.getEhCacheManager()");
        EhCacheManager ehcache = new EhCacheManager();
//        ehcache.setCacheManager(cacheManager);
        ehcache.setCacheManagerConfigFile("classpath:config/ehcache.xml");
     /*   CacheManager cacheManager = CacheManager.getCacheManager("es");
        if (cacheManager == null) {
            try {

                cacheManager = CacheManager.create(ResourceUtils
                        .getInputStreamForPath("classpath:config/ehcache.xml"));

            } catch (CacheException | IOException e) {
                e.printStackTrace();
            }
        }*/
//        ehcache.setCacheManager(cacheManager);
        return ehcache;
    }
}
