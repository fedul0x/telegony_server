package telegony.dataaccess;

import java.io.Serializable;
import java.util.List;
import telegony.dataaccess.common.TransientObject;

/**
 * Объект доступа к данным.
 * Позволяет абстрагироваться от источника данных.
 * @author Kurbatov Oleg
 */
public abstract interface Repository<T extends TransientObject>{

    /**
     * Порядок сортировки
     */
    public static enum SORT_ORDER{
        ASC,
        DESC
    }
    
    /**
     * Получить количество сущностей в хранилище
     * @return 
     */
    public Long getTotalCount();
    
    /**
     * Найти объект по идентификатору
     * @param id Идентификатор
     * @return Объект из БД с заданным идентификатором
     */
    public T findById(Serializable id);

    /**
     * Выбрать все объекты класса, сохранённые в БД
     * @return Список всех сохранённых объектов
     */
    public List<T> findAll();

    /**
     * Выборка множества упорядоченных объектов
     * @param from Индекс первого элемента выборки
     * @param fetchSize Размер выборки
     * @param orderCondition Название поля, по которому производится сортировка
     * @return Список объектов из БД
     */
    public List<T> findRange(int from, int fetchSize, String orderCondition, SORT_ORDER howToOrder);

    /**
     * Найти по образцу
     * @param exampleInstance Образцовый экземпляр
     * @return Список похожих на образец экземпляров
     */
    public List<T> findByExample(T exampleInstance, String ... excludeProperty);

    /**
     * Сохранить текущее состояние объекта в хранилище
     */
    public void save(T dataObject);

    /**
     * Удалить текущий объект из хранилища, если он там присутствует
     */
    public T remove(T dataObject);

    /**
     * Удалить объект из БД по идентификатору
     * @param id Идентификатор
     * @return Удалённая сущность
     */
    public T removeById(Long id);

    /**
     * Актуализировать состояние объекта данных
     */
    public void refresh(T dataObject);

}
