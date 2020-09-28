package org.java.bi.admin;
import org.java.bi.admin.config.ApplicationConfiguration;
import org.java.bi.admin.config.ShiroConfig;
import org.java.bi.admin.servlet.AppDispatcherServletConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;


@Import({
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class,
        ShiroConfig.class
})
@MapperScan("org.java.bi.db.dao")
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"org.java.bi.db",
        "org.java.bi.core", "org.java.bi.ck","org.java.bi.admin"})
//"org.java.bi.core", "org.java.bi.admin"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
