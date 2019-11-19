package lk.ijse.dep.pos.db;

import lk.ijse.deppo.crypto.DEPCrypt;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {

    private static EntityManagerFactory entityManager=buildEntityManager();
    private static String username;
    private static String password;
    private static String db;
    private static String port;
    private static String host;

    private  static EntityManagerFactory buildEntityManager(){

        File file = new File("resources/application.properties");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);

            username=properties.getProperty("hibernate.connection.username");
            password= properties.getProperty("hibernate.connection.password");
            db=properties.getProperty("ijse.dep.db");
            port=properties.getProperty("ijse.dep.port");
            host=properties.getProperty("ijse.dep.ip");

            return  Persistence.createEntityManagerFactory("dep4",properties);

        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep.pos.db.JPAUtil").log(Level.SEVERE,null,e);
            System.exit(1);
            return null;
        }

    }

    public static EntityManagerFactory getEntityManager(){
        return entityManager;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        JPAUtil.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        JPAUtil.password = password;
    }

    public static String getDb() {
        return db;
    }

    public static void setDb(String db) {
        JPAUtil.db = db;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        JPAUtil.port = port;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        JPAUtil.host = host;
    }
}
