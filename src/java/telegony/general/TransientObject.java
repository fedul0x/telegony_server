package telegony.general;

import java.io.Serializable;

/**
 * Транзитный объект
 * @author Ivashin Alexey
 */
public abstract class TransientObject {
    /*
     * Идентификатор
     */
    private Serializable id;
    /*
     * Описание элемента списка
     */
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }
    
}
