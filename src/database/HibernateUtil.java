package database;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static ServiceRegistry service;
    private static Configuration cfg;
    private static StandardServiceRegistryBuilder ssrb;
    private static SessionFactory factory;
 
    static {
    	//File file = new File("hibernate.cfg.xml");
        cfg=new Configuration().configure();
        ssrb=new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
  
        service=ssrb.build();
        factory=cfg.buildSessionFactory(service);
    }

    public static Session getSession(){
        return  factory.openSession();
    }
}
