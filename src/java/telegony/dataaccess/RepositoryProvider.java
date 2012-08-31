package telegony.dataaccess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import telegony.dataaccess.common.TransientObject;

/**
 * Класс, предоставляющий репозитории для различных объектов
 * @author Kurbatov Oleg
 */
public class RepositoryProvider {

    private static final RepositoryProvider SINGLETONE = new RepositoryProvider();
    private Map<Class, Repository> repositories;
    private Set attached;
    private boolean transactionMode;
    /**
     * Сессия для внесение транзакционных изменений в БД через репозитории разных классов.
     */
    private Session transactionSession;
    /**
     * Сессия для ленивой инициализации подключенных к ней объектов
     */
    private Session attachetObjectSession;

    private RepositoryProvider() {
        repositories = new HashMap<Class, Repository>();
        attached = new HashSet();
    }

    /**
     * Получить репозиторий заданного класса
     * @param c Класс персистентного объекта
     * @return Репозиторий для указанного класса
     */
    public static Repository getRepository(Class<? extends TransientObject> c) {
        HibernateRepository result;
        if (!SINGLETONE.repositories.containsKey(c)) {
            result = new HibernateRepository(c);
            SINGLETONE.repositories.put(c, result);
        } else {
            result = (HibernateRepository) SINGLETONE.repositories.get(c);
        }
        if (SINGLETONE.transactionMode) {
            result.setTransactionSession(SINGLETONE.transactionSession);
        } else {
            result.setTransactionMode(false);
        }
        return result;
    }

    /**
     * Начать транзакцию, общую для всех репозиториев
     */
    public static void startTransaction() {
        if (SINGLETONE.transactionMode) {
            throw new IllegalStateException("Предыдущая транзакция не была завершена.");
        }
        SINGLETONE.transactionSession = HibernateUtil.currentSession();
        SINGLETONE.transactionSession.beginTransaction();
        SINGLETONE.transactionMode = true;
    }

    /**
     * Фиксировать изменения, произошедшие в рамках транзакции
     */
    public static void commitTransaction() {
        if (!SINGLETONE.transactionMode) {
            throw new IllegalStateException("Транзакция не была начата.");
        }
        SINGLETONE.transactionMode = false;
        SINGLETONE.transactionSession.getTransaction().commit();
        SINGLETONE.transactionSession.close();
    }

    /**
     * Откатить изменения, произошедшие в рамках транзакции
     */
    public static void rollbackTransaction() {
        if (!SINGLETONE.transactionMode) {
            throw new IllegalStateException("Транзакция не была начата.");
        }
        SINGLETONE.transactionMode = false;
        SINGLETONE.transactionSession.getTransaction().rollback();
        SINGLETONE.transactionSession.close();
    }

    /**
     * Подключить транзитный объект к сессии для получения дополнительных данных
     * @param instance 
     */
    public static synchronized void attach(Object instance) {
        if (instance == null) {
            return;
        }
        boolean contains = true;
        try {
            Hibernate.initialize(instance);
            contains = SINGLETONE.attached.contains(instance);
        } catch (org.hibernate.LazyInitializationException e) {
            System.out.println("Это исключение обработано");
            contains = false;
        }
        if (!contains) {
            if (SINGLETONE.attachetObjectSession == null || !SINGLETONE.attachetObjectSession.isOpen()) {
                SINGLETONE.attachetObjectSession = HibernateUtil.currentSession();
            }
            SINGLETONE.attachetObjectSession.lock(instance, LockMode.NONE);
            SINGLETONE.attached.add(instance);
        }
    }

    /**
     * Отключить все транзитные объекты от сессии
     */
    public static void detachAll() {
        if (SINGLETONE.attachetObjectSession != null && SINGLETONE.attachetObjectSession.isOpen()) {
            SINGLETONE.attachetObjectSession.close();
            SINGLETONE.attached.clear();
        }
    }
}
