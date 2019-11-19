package lk.ijse.dep.pos.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {

    private static EntityManagerFactory entityManager=buildEntityManager();

    private  static EntityManagerFactory buildEntityManager(){

        File file = new File("resources/application.properties");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);

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
}
