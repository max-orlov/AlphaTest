package dao;

import org.hibernate.Session;

import java.util.List;

public class DAO {
    private static Session openedSession;

    static {
        openedSession = HibernateUtil.getSession();
    }

    public static void addObject(Object o){
        openedSession.beginTransaction();
        openedSession.save(o);
        openedSession.getTransaction().commit();
    }

    public static void addObjects(List<?> objects) {
        for (Object object : objects) {
            addObject(object);
        }
    }

    public static Object executeSQLQuery(String SQLQuery){
        return openedSession.createSQLQuery(SQLQuery).list();
    }

}
