package ru.kuznetsov.service.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/** Класс, отвечающий за конфигурацию SOAP взаимодействия
 * @author Kuznetsov Vladislav
 * @version 1.8.6
 */
@EnableWs
@Configuration
public class SoapServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
    }

    @Bean(name = "CreditInfoCBRService")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema creditInfoCBRServiceSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CreditInfoCBRServicePort");
        definition.setTargetNamespace("http://localhost:3000/WEBService");
        definition.setLocationUri("/ws");
        definition.setSchema(creditInfoCBRServiceSchema);
        return definition;
    }

    @Bean
    public XsdSchema creditInfoCBRServiceSchema() {
        return new SimpleXsdSchema(new ClassPathResource("META-INF/WSDL/Service/CreditInfoCBRService.xsd"));
    }
}