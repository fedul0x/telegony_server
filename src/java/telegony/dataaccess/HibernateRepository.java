package telegony.dataaccess;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import telegony.general.TransientObject;

/**
 * Объект доступа к долгоживущим (персистентным) объектам, которые предоставляются посредством Hibernate.
 * @author Kurbatov Oleg
 */
public class HibernateRepository<T extends TransientObject> implements Repository<T>{

    /**
     * Сессия Hibernate
     */
    private Session session;
    
    /**
     * Транзакция Hibernate
     */
    private Transaction transaction;
    
    /**
     * Флаг режима транзакции
     */
    private boolean transactionMode;

    /**
     * Класс пресистентного объекта данных
     */
    private Class<T> persistClass;

    /**
     * Создать новый объект доступа к персистентным объектам указанного класса.
     * @param c Класс персистентного объекта
     * @throws IllegalArgumentException если переданный в параметре класс не отображён в структурах БД
     */
    @SuppressWarnings("unchecked")
    public HibernateRepository(Class<T> c){
        persistClass = c;
    }

    /**
     * Немедленно сохранить все изменения, произведённые в сессии, в БД.
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * Отменить несохранённые изменения из сессии
     */
    public void clear() {
        getSession().clear();
    }

    /**
     * Открыта ли сессия подключения к БД
     * @return
     */
    public boolean isSessionOpen(){
        return session.isOpen();
    }
    
    @Override
    public Long getTotalCount(){
        beginTransaction();
        Long result=(Long) session.createQuery("select count(*) from "+persistClass.getName()).iterate().next() ;
        commitTransaction();
        return result;
    }

    /**
     * Получить персистентный объект по идентификатору.
     * @param id Идентификатор
     * @param lock Блокировка
     * @return Объект
     * @throws HibernateException Если объекта с заданным идентификатором не существует
     */
    @SuppressWarnings("unchecked")
    public T findById(Serializable id, boolean lock) throws HibernateException {
        beginTransaction();
        T entity;
        if (lock) {
            entity = (T) session.load(persistClass, id, LockMode.UPGRADE);
        } else {
            entity = (T) session.load(persistClass, id);
        }
        commitTransaction();
        return entity;
    }

    /**
     * Получить персистентный объект по идентификатору.
     * @param id Идентификатор
     * @return Объект
     * @throws HibernateException Если объекта с заданным идентификатором не существует
     */
    @Override
    public T findById(Serializable id)throws HibernateException{
        return findById(id,false);
    }

    /**
     * Выборка всех существующих персистентных объектов класса.
     * @return Список всех существующих персистентных объектов класса.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    /**
     * Выборка диапазона данных
     * @param from Первый элемент (смещение) выборки
     * @param fetchSize Размер выборки (количество записей)
     * @param orderCondition Имя поля, по которому сортировать
     * @param howToOrder Способ сортировки
     * @param join
     * @param joinCondition
     * @return Диапазон данных
     */
    @SuppressWarnings("unchecked")
    public List<T> findRange(int from, int fetchSize, String orderCondition, SORT_ORDER howToOrder, int join, String... joinCondition) {
        String classAlias = persistClass.getSimpleName().toLowerCase();
        Session s=getSession();
        beginTransaction();
        Criteria rangeCriteria = s.createCriteria(persistClass, classAlias);

        rangeCriteria.setFirstResult(from);
        rangeCriteria.setMaxResults(fetchSize);
        if (orderCondition != null) {
            if (howToOrder.equals(SORT_ORDER.ASC)) {
                rangeCriteria.addOrder(Order.asc(orderCondition));
            } else {
                rangeCriteria.addOrder(Order.desc(orderCondition));
            }
        }
        for (String condition : joinCondition){
            rangeCriteria.createAlias(classAlias + "." + condition, condition, join);
        }
        List<T> result=rangeCriteria.list();
        commitTransaction();
        return result;
    }

    /**
     * Выборка диапазона данных
     * @param from Первый элемент (смещение) выборки
     * @param fetchSize Размер выборки (количество записей)
     * @param orderCondition Имя поля, по которому сортировать
     * @param howToOrder Порядок сортировки
     * @return
     */
    @Override
    public List<T> findRange(int from, int fetchSize, String orderCondition,SORT_ORDER howToOrder){
        return findRange(from,fetchSize,orderCondition,howToOrder,0);
    }

    /**
     * Выборка диапазона данных
     * @param from Первый элемент (смещение) выборки
     * @param fetchSize Размер выборки (количество записей)
     * @return
     */
    public List<T> findRange(int from, int fetchSize){
        return findRange(from,fetchSize,null,SORT_ORDER.DESC,0);
    }

    /**
     * Поиск (выборка) по образцу.
     * @param exampleInstance Образец для выборки
     * @param excludeProperty Исключаемые поля (не учитываются при поиске)
     * @return Список найденных в БД объектов
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(T exampleInstance, String ... excludeProperty) {
        HashSet<String> excludes=new HashSet<String>();
        excludes.add("changed");
        excludes.addAll(Arrays.asList(excludeProperty));
        
        Session s=getSession();
        beginTransaction();
        
        Criteria crit = s.createCriteria(persistClass);
        Class cl=exampleInstance.getClass();
        do{
            Field[] fields = cl.getDeclaredFields();
            for (Field f : fields) {
                if (excludes.contains(f.getName())) {
                    continue;
                }
                f.setAccessible(true);
                try {
                    Object field = f.get(exampleInstance);
                    if (field != null) {
                        if (field instanceof TransientObject) {
                            //Hibernate.initialize(field);
                            crit.add(Restrictions.eq(f.getName(), field));
                        }else if(field instanceof Collection){
                            Collection c=(Collection)field;
                            if(c.isEmpty()||!(c.iterator().next() instanceof TransientObject))continue;
                            Disjunction d=Restrictions.disjunction();
                            for(Object value:c){
                                d.add(Restrictions.idEq(((TransientObject)value).getId()));
                            }
                            crit=crit.createCriteria(f.getName()).add(d);
                        }else if (field.toString().indexOf("%") != -1) {//включает like, если в тексте поля содержится %
                            crit.add(Restrictions.like(f.getName(), field));
                        } else {
                            crit.add(Restrictions.eq(f.getName(), field));
                        }
                    }
                } catch (Exception e) {
                    //условие выполнения не наступит никогда (я надеюсь)
                    Logger.getLogger(HibernateRepository.class.getName()).log(Level.SEVERE, "Ошибка анализа полей образца при попытке поиска по образцу.", e);
                }
            }
        }while((cl=cl.getSuperclass())!=Object.class);
        List<T> result=crit.list();
        commitTransaction();
        return result;
    }

    /**
     * Поиск (выборка) по образцу.
     * @param exampleInstance Образец для выборки
     * @return
     */
    public List<T> findByExample(T exampleInstance) {
        return findByExample(exampleInstance);
    }

    /**
     * Сохранить текущее состояние объекта в БД.
     */
    @Override
    public void save(T dataObject){
//        if(dataObject.isPersistent())return;
        if(false)return;
        Session s=getSession();
        beginTransaction();
        s.saveOrUpdate(dataObject);
        commitTransaction();
//        dataObject.setPersistent(true);
    }

    /**
     * Удалить персистентный объект из БД
     * @param dataObject Объект для удаления
     * @return Удалённый из БД объект
     */
    @Override
    public T remove(T dataObject){
//        if(dataObject.isPersistent()){
        if(true){
            beginTransaction();
            getSession().delete(dataObject);
            commitTransaction();
        }else{
            throw new IllegalStateException("Попытка удаления объекта, текущее состояние которого не сохранено в БД");
        }
//        dataObject.setPersistent(false);
        return dataObject;
    }

    /**
     * Удалить объект данного класса из БД по идентификатору.
     * @param id Идентификатор
     * @return Удалённый из БД объект
     */
    @Override
    public T removeById(Long id){
        return remove(findById(id, true));
    }

    @Override
    public void refresh(T dataObject) {
        Session s = getSession();
        beginTransaction();
        s.refresh(dataObject);
        commitTransaction();
    }

//-----------------------Protected methods------------------------------------//

    /**
     * Устанавливает репозиторию сессию, в рамках транзакции которой будет
     * выполняться дальнейшая работа с БД до закрытия этой сессии.
     * @param s 
     */
    void setTransactionSession(Session s){
        session=s;
        setTransactionMode(true);
    }
    
    /**
     * Установить транзакционный режим
     * @param transactionMode 
     */
    void setTransactionMode(boolean transactionMode){
        this.transactionMode=transactionMode;
    }
    
    /**
     * Получить сессию репозитория
     * @return Сессия репозитория
     */
    private Session getSession(){
        if(!transactionMode){
            session=HibernateUtil.currentSession();
        }
        return session;
    }
    
    /**
     * Начинает транзакцию в целях изменения объектов БД.
     * @return Сессия, в которой начата транзакция.
     */
    private void beginTransaction(){
        if(!transactionMode){
            transaction=getSession().beginTransaction();
        }
    }
    
    /**
     * Подтверждает сохранение транзакционных изменений в БД.
     */
    private void commitTransaction(){
        if(!transactionMode)transaction.commit();
    }

    /**
     * Использовать этот метод внутри подклассов для поиска через Criteria.
     */
    @SuppressWarnings("unchecked")
    private List<T> findByCriteria(Criterion... criterion) {
        beginTransaction();
        Criteria crit = getSession().createCriteria(persistClass);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        List<T> result=crit.list();
        commitTransaction();
        return result;
    }

}
