package plus.extvos.builtin.async.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import plus.extvos.builtin.async.service.AsyncTaskContainer;
import plus.extvos.builtin.async.service.impl.DefaultAsyncTaskContainerImpl;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Mingcai SHEN
 */
@Configuration
@EntityScan("plus.extvos.builtin.async.entity")
@ComponentScan(basePackages = "plus.extvos.builtin.async")
public class BuiltinAsyncAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(AsyncTaskContainer.class)
    public AsyncTaskContainer asyncTaskContainer() {
        return new DefaultAsyncTaskContainerImpl();
    }


    @Bean
    @ConditionalOnProperty(prefix = "spring.swagger", name = "disabled", havingValue = "false", matchIfMissing = true)
    public Docket createAsyncDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("异步任务服务")
            .apiInfo(new ApiInfoBuilder()
                .title("异步任务服务")
                .description("Builtin Quartz services for generic use.")
                .contact(new Contact("Mingcai SHEN", "https://github.com/", "archsh@gmail.com"))
                .termsOfServiceUrl("https://github.com/extvos/quick-builtin-async.git")
                .version(getClass().getPackage().getImplementationVersion())
                .build())
            .select()
            .apis(RequestHandlerSelectors.basePackage("plus.extvos.builtin.async"))
            .build();
    }
}
