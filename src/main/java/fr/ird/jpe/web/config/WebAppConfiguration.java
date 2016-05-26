/*
 * Copyright (C) 2014 IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.jpe.web.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import fr.ird.aas.exception.UserAlreadyExistsException;
import fr.ird.aas.realm.AASManager;
import fr.ird.common.log.LogService;
import fr.ird.jpe.web.formatter.AuthorizationFormatter;
import fr.ird.jpe.web.formatter.RoleFormatter;
import fr.ird.jpe.web.aas.Privileges;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import uk.co.gcwilliams.jodatime.thymeleaf.JodaTimeDialect;

/**
 * Defines the Java-based configuration of this App.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0.0
 * @date 3 oct. 2014
 *
 */
@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan({"fr.ird.jpe.web"})
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private WebSecurityManager securityManager;

    @Bean
    @Description("Thymeleaf view resolver")
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());

        return viewResolver;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();

        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.setTemplateResolver(templateResolver());

        Set additionalDialects = new HashSet<>();

        additionalDialects.add(new LayoutDialect());
        additionalDialects.add(new JodaTimeDialect());
        additionalDialects.add(new ShiroDialect());
        templateEngine.setAdditionalDialects(additionalDialects);

        return templateEngine;
    }

    @Bean(name = "messageSource")
    @Description("Spring message resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        String[] basenames = new String[]{"i18n/messages", "i18n/authentication"};
        messageSource.setBasenames(basenames);
        messageSource.setDefaultEncoding("utf-8");

        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

        localeChangeInterceptor.setParamName("language");

        return localeChangeInterceptor;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        Locale defaultLocale = new Locale("fr");

        localeResolver.setDefaultLocale(defaultLocale);

        return localeResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();

        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(-1);

        return resolver;
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();

        lvfb.setValidationMessageSource(messageSource());

        return lvfb;
    }

    @Override
    public Validator getValidator() {
        return localValidatorFactoryBean();
    }

//  @Bean
//    public MultipartConfigElement multipartConfigElement() {
//         MultipartConfigFactory factory = new MultipartConfigFactory();
//   factory.setMaxFileSize("128KB");
//   factory.setMaxRequestSize("128KB");
//   return factory.createMultipartConfig();
//    }
    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addFormatter(new RoleFormatter());
        formatterRegistry.addFormatter(new AuthorizationFormatter());
    }

    /**
     * Setup a simple strategy: use all the defaults and return JSON by default
     * when not sure.
     *
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.ignoreAcceptHeader(true);
        configurer.favorParameter(false);
        configurer.favorPathExtension(true);

        Map mediaTypes = new HashMap<>();

        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        configurer.mediaTypes(mediaTypes);
        super.configureContentNegotiation(configurer);
    }

    private ObjectMapper objectMapper() {
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setIndentOutput(true);
        bean.setSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        bean.afterPropertiesSet();
        ObjectMapper objectMapper = bean.getObject();
        objectMapper.registerModule(new JodaModule());
        return objectMapper;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = objectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        Map<String, String> definitionsMap = new HashMap<>();

        definitionsMap.put("/login", "authc");
        definitionsMap.put("/admin/**", "authc, roles[administrator]");
        definitionsMap.put("/**", "authc");
        definitionsMap.put("/assets/**", "anon");
        definitionsMap.put("/exit", "logout");
        shiroFilter.setFilterChainDefinitionMap(definitionsMap);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSecurityManager(securityManager);

        return shiroFilter;
    }

//  @Autowired
//  private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    @Bean(name = "AASManager")
    public AASManager getAASManager() {
        AASManager manager = AASManager.getInstance();
        String persitanceUnit = "jpe-aas";
        if ("".equals(JPEProperties.JDBC_DRIVER_CLASS)) {
            manager.init(persitanceUnit, true);
        } else {
            Properties properties = new Properties();
            properties.put("javax.persistence.jdbc.url", JPEProperties.JDBC_URL);
            properties.put("javax.persistence.jdbc.password", JPEProperties.JDBC_PASSWORD);
            properties.put("javax.persistence.jdbc.driver", JPEProperties.JDBC_DRIVER_CLASS);
            properties.put("javax.persistence.jdbc.user", JPEProperties.JDBC_USERNAME);
            manager.init(persitanceUnit, properties);
        }
        try {
            Privileges.insertDefaultPrivileges(manager);
        } catch (UserAlreadyExistsException ex) {
            LogService.getService(WebAppConfiguration.class).logApplicationError(ex.getMessage());
        }

        return manager;
    }

//    @Bean(name = "TransferService")
//    public TransferService getTransferService() {
//        TransferService service = TransferService.getService();
//        try {
//            service.init();
//        } catch (EvaException ex) {
//           LogService.getService(WebAppConfiguration.class).logApplicationError(ex.getMessage());
//        }
//        return service;
//    }
    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error
     * is returned.
     */
    @Controller
    static class FaviconController {

        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/WEB-INF/assets/img/favicon.ico";
        }
    }
}
