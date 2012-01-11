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
     * Описание объекта
     */
    private String description;

    public TransientObject() {
    }
    
    public TransientObject(Serializable id, String description) {
        this.id = id;
        this.description = description;
    }
    

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

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransientObject other = (TransientObject) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }
    
}
