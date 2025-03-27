package redlib.backend.config;

import java.io.File;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebConfig implements WebMvcConfigurer, SchedulingConfigurer {
    @Value("${react.debug:false}")
    private Boolean reactDebug;

    @Value("${image-work-path:}")
    private String imageWorkPath;

    @PostConstruct
    public void init() {
        if (imageWorkPath.isEmpty()) {
            imageWorkPath = System.getProperty("user.dir") + File.separator + "imageFiles";
        }
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                .allowedHeaders("*")
                //设置允许的方法
                .allowedMethods("*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registerReactRoute(registry, "");
    }

    private void registerReactRoute(ViewControllerRegistry registry, String directoryName) {
        String regex = "/**/{spring:\\w+}";
        String forwardUrl = "forward:/" + directoryName + "index.html";

        registry.addViewController("/" + directoryName + "/")
                .setViewName(forwardUrl);

        registry.addViewController("/" + directoryName + regex)
                .setViewName(forwardUrl);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
