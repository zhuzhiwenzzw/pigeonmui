package top.zhuzhiwen.pigeonmui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import top.zhuzhiwen.pigeonmui.config.ShiroConfig;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepositoryFactoryBean;

@SpringBootApplication
//指定自己的工厂类
@EnableJpaRepositories(basePackages = {"top.zhuzhiwen.pigeonmui"}, repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@Import(ShiroConfig.class)
@EnableCaching
public class PigeonmuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PigeonmuiApplication.class, args);
    }

}
