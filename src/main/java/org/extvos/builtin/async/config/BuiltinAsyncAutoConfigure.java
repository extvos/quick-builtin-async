package org.extvos.builtin.async.config;

import org.extvos.builtin.async.service.AsyncTaskContainer;
import org.extvos.builtin.async.service.impl.DefaultAsyncTaskContainerImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author shenmc
 */
@Configuration
@EntityScan("org.extvos.builtin.async.entity")
@MapperScan("org.extvos.builtin.async.mapper")
@ComponentScan(basePackages = "org.extvos.builtin.async")
public class BuiltinAsyncAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(AsyncTaskContainer.class)
    public AsyncTaskContainer asyncTaskContainer() {
        return new DefaultAsyncTaskContainerImpl();
    }


    @Bean
    public Docket createAsyncDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("异步任务服务")
                .apiInfo(new ApiInfoBuilder()
                        .title("异步任务服务")
                        .description("Builtin Quartz services for generic use.")
                        .contact(new Contact("Mingcai SHEN", "https://gitlab.inodes.cn/", "archsh@gmail.com"))
                        .termsOfServiceUrl("https://gitlab.inodes.cn/quickstart/java-scaffolds/quick-builtin-async.git")
                        .version(getClass().getPackage().getImplementationVersion())
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.extvos.builtin.async"))
                .build();
    }
}
