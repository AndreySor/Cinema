package edu.school21.cinema.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.TaskExecutorRegistration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.MultiServerUserRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.simp.user.UserRegistryMessageHandler;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.messaging.support.ImmutableMessageChannelInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.PathMatcher;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "edu.school21.cinema")
@PropertySource("classpath:application.properties")
public class SpringConfiguration {

    @Value("${datasource.driver-class-name}")
    private String driverClassName;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String userName;
    @Value("${datasource.password}")
    private String password;
    @Value("${images.folder.path}")
    private String pathToImagesFolder;

    @Nullable
    private ChannelRegistration clientInboundChannelRegistration;

    @Nullable
    private ChannelRegistration clientOutboundChannelRegistration;

    @Nullable
    private MessageBrokerRegistry brokerRegistry;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/jsp/");

        return freeMarkerConfigurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver internalResourceViewResolver = new FreeMarkerViewResolver();
        internalResourceViewResolver.setCache(true);
        internalResourceViewResolver.setPrefix("");
        internalResourceViewResolver.setSuffix(".ftl");

        return internalResourceViewResolver;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClassName);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(userName);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("edu.school21.cinema.model");
        entityManagerFactory.setJpaProperties(hibernateProperties());
        entityManagerFactory.setJpaVendorAdapter(createJpaVendorAdapter());
        entityManagerFactory.afterPropertiesSet();

        return entityManagerFactory;
    }

    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        return hibernateProperties;
    }

    private JpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);   // 20MB
        multipartResolver.setMaxInMemorySize(1048576);  // 1MB
        return multipartResolver;
    }

//    protected final ChannelRegistration getClientInboundChannelRegistration() {
//        if (this.clientInboundChannelRegistration == null) {
//            ChannelRegistration registration = new ChannelRegistration();
//            configureClientInboundChannel(registration);
//            registration.interceptors(new ImmutableMessageChannelInterceptor());
//            this.clientInboundChannelRegistration = registration;
//        }
//        return this.clientInboundChannelRegistration;
//    }
//
//    protected void configureClientInboundChannel(ChannelRegistration registration) {
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor clientInboundChannelExecutor() {
//        TaskExecutorRegistration reg = getClientInboundChannelRegistration().taskExecutor();
//        ThreadPoolTaskExecutor executor = reg.getTaskExecutor();
//        executor.setThreadNamePrefix("clientInboundChannel-");
//        return executor;
//    }
//
//    protected void configureClientOutboundChannel(ChannelRegistration registration) {
//    }
//
//    @Bean
//    public AbstractSubscribableChannel clientInboundChannel() {
//        ExecutorSubscribableChannel channel = new ExecutorSubscribableChannel(clientInboundChannelExecutor());
//        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
//        ChannelRegistration reg = getClientInboundChannelRegistration();
//        if (reg.hasInterceptors()) {
//            channel.setInterceptors(reg.getInterceptors());
//        }
//        return channel;
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor clientOutboundChannelExecutor() {
//        TaskExecutorRegistration reg = getClientOutboundChannelRegistration().taskExecutor();
//        ThreadPoolTaskExecutor executor = reg.getTaskExecutor();
//        executor.setThreadNamePrefix("clientOutboundChannel-");
//        return executor;
//    }
//
//    protected final ChannelRegistration getClientOutboundChannelRegistration() {
//        if (this.clientOutboundChannelRegistration == null) {
//            ChannelRegistration registration = new ChannelRegistration();
//            configureClientOutboundChannel(registration);
//            registration.interceptors(new ImmutableMessageChannelInterceptor());
//            this.clientOutboundChannelRegistration = registration;
//        }
//        return this.clientOutboundChannelRegistration;
//    }
//
//    @Bean
//    public AbstractSubscribableChannel clientOutboundChannel() {
//        ExecutorSubscribableChannel channel = new ExecutorSubscribableChannel(clientOutboundChannelExecutor());
//        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
//        ChannelRegistration reg = getClientOutboundChannelRegistration();
//        if (reg.hasInterceptors()) {
//            channel.setInterceptors(reg.getInterceptors());
//        }
//        return channel;
//    }
//
//    @Bean
//    public SimpMessagingTemplate brokerMessagingTemplate() {
//        SimpMessagingTemplate template = new SimpMessagingTemplate(brokerChannel());
//        String prefix = getBrokerRegistry().getUserDestinationPrefix();
//        if (prefix != null) {
//            template.setUserDestinationPrefix(prefix);
//        }
//        template.setMessageConverter(brokerMessageConverter());
//        return template;
//    }
//
//    @Bean
//    public AbstractSubscribableChannel brokerChannel() {
//        ChannelRegistration reg = getBrokerRegistry().getBrokerChannelRegistration();
//        ExecutorSubscribableChannel channel = (reg.hasTaskExecutor() ?
//                new ExecutorSubscribableChannel(brokerChannelExecutor()) : new ExecutorSubscribableChannel());
//        reg.interceptors(new ImmutableMessageChannelInterceptor());
//        channel.setLogger(SimpLogging.forLog(channel.getLogger()));
//        channel.setInterceptors(reg.getInterceptors());
//        return channel;
//    }
//
//    protected final MessageBrokerRegistry getBrokerRegistry() {
//        if (this.brokerRegistry == null) {
//            MessageBrokerRegistry registry = new MessageBrokerRegistry(clientInboundChannel(), clientOutboundChannel());
//            configureMessageBroker(registry);
//            this.brokerRegistry = registry;
//        }
//        return this.brokerRegistry;
//    }
//    protected void configureMessageBroker(MessageBrokerRegistry registry) {
//    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean("pathToImagesFolder")
    public String pathToImagesFolder() {
        return pathToImagesFolder;
    }
}
