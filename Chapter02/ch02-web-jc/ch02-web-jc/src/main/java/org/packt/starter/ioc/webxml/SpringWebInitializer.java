package org.packt.starter.ioc.webxml;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.packt.starter.ioc.context.SpringContextConfig;
import org.packt.starter.ioc.dispatcher.SpringDispatcherConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@ComponentScan(basePackages = "org.packt.starter.ioc")
@Configuration
public class SpringWebInitializer implements WebApplicationInitializer {
	  
	  @Override
	  public void onStartup(ServletContext container) throws ServletException {
	    addRootContext(container);
	    addDispatcherContext(container);
	   
	  }

	private void addRootContext(ServletContext container) {
	    // Create the application context
	    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	    rootContext.register(SpringContextConfig.class); 
	 
	    // Register application context with ContextLoaderListener
	    container.addListener(new ContextLoaderListener(rootContext));
	  }
	   
	  private void addDispatcherContext(ServletContext container) {
	    // Create the dispatcher servlet's Spring application context
	    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	    dispatcherContext.register(SpringDispatcherConfig.class); 
	 
	    // Declare  <servlet> and <servlet-mapping> for the DispatcherServlet
	    ServletRegistration.Dynamic dispatcher = container.addServlet("ch02-servlet", 
	    		new DispatcherServlet(dispatcherContext));
	    dispatcher.addMapping("*.html");
	    dispatcher.setLoadOnStartup(1);
	  }

}
