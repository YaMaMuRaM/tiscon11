package com.tiscon11;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import com.tiscon11.viewhelper.SpringMVCHelper;
import com.tiscon11.viewhelper.SpringMVCHelper.NumberFormatHelper;

/**
 * Spring Bootのエントリーポイント。
 * 
 * @author TIS Taro
 * 
 */
@SpringBootApplication
public class Tiscon11Application {

    public static void main(String[] args) {
        SpringApplication.run(Tiscon11Application.class, args);
    }

    @Bean
    public ViewResolver viewResolver() {
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".hbs");
        viewResolver.setCache(false);
        Map<String, Helper<?>> helpers = Map.of(
            // "eq", ConditionalHelpers.eq,
            "eq", new SpringMVCHelper.EQHelper(),
            "not", ConditionalHelpers.not,
            "numberFormat", new NumberFormatHelper(),
            "fieldErrors", new SpringMVCHelper.FieldErrorsHelper(),
            "hasFieldErrors", new SpringMVCHelper.HasFieldErrorsHelper()
        );
        viewResolver.setHelpers(helpers);
        return viewResolver;
    }
}
