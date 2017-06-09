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

import fr.ird.common.log.LogService;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initialisation de la servelet de l'application.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 22 oct. 2014
 *
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        //Initialisation du JPEProperties
        // Je ne sais pas si c'est le meilleur endroit
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        rootContext.register(WebAppConfiguration.class, SecurityConfiguration.class);

        // Manage the lifecycle of the root application context
        container.addListener(new JPEContextLoaderListerner(rootContext));

        // Create the dispatcher servlet's Spring application context
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup create dispatcherContext");
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcherContext.setServletContext()");
        dispatcherContext.setServletContext(container);
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcherContext.setParent()");
        dispatcherContext.setParent(rootContext);
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcherContext.register()");
        dispatcherContext.register(WebAppConfiguration.class);

        // Register and map the dispatcher servlet
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup create dispatcher");
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
                new DispatcherServlet(dispatcherContext));
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcher.setLoadOnStartup()");
        dispatcher.setLoadOnStartup(1);
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcher.addMapping()");
        dispatcher.addMapping("/");
        LogService.getService(WebAppInitializer.class).logApplicationDebug("onStartup dispatcher.addFilter()");
        container.addFilter("shiroFilter",
                new DelegatingFilterProxy("shiroFilterBean",
                        dispatcherContext)).addMappingForUrlPatterns(null, false, "/*");
    }

    public class JPEContextLoaderListerner extends ContextLoaderListener {

        public JPEContextLoaderListerner(AnnotationConfigWebApplicationContext rootContext) {
            super(rootContext);
        }

        @Override
        public void contextDestroyed(ServletContextEvent event) {
            super.contextDestroyed(event);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            LogService.getService(WebAppInitializer.class).logApplicationDebug("ServletContextListener destroyed");
            Enumeration<Driver> drivers = DriverManager.getDrivers();

            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                if (driver.getClass().getClassLoader() == cl) {
                    try {
                        DriverManager.deregisterDriver(driver);

                    } catch (SQLException e) {
                        LogService.getService(WebAppInitializer.class).logApplicationError(String.format("Error deregistering driver %s", driver));
                        LogService.getService(WebAppInitializer.class).logApplicationError(e.getMessage());
                    }
                }
            }
        }

        @Override
        public void contextInitialized(ServletContextEvent event) {
            super.contextInitialized(event);
            LogService.getService(WebAppInitializer.class).logApplicationDebug("ServletContextListener started");

        }
    }
}
