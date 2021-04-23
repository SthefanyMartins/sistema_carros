/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.util;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author victor
 */
public class JPAHelper implements ServletContextListener {

    public static EntityManagerFactory factory;
    
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando EntityManagerFactory");

        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("sistema_carros");
        }

        /*
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("loggi");
        sce.getServletContext().setAttribute("EMFactory", emFactory);
         *
         */
        System.out.println("EntityManagerFactory inicializada");
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        if (factory != null) {
            factory.close();
        }
        /*
        EntityManagerFactory emFactory = (EntityManagerFactory)sce.getServletContext().getAttribute("EMFactory");
        if (emFactory != null) {
            System.out.println("Concluindo EntityManagerFactory");
            emFactory.close();
        }
         *
         */
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("sistema_carros");
        }
        return factory;
        /*
        if (FacesContext.getCurrentInstance() != null) {
            Object attribute = JSFHelper.getApplicationAttribute("EMFactory");
            return (EntityManagerFactory)attribute;
        } else {
             return Persistence.createEntityManagerFactory("loggi");
        }
         *
         */
    }

    public static EntityManager createEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
