package telegony.general;

/**
 * Транзитный список
 * @author Ivashin Alexey
 */
public abstract class TransientEnum extends TransientObject {

    /*
     * Идентификатор
     */
    private Long id;
    /*
     * Описание элемента списка
     */
    private String description;

    public TransientEnum() {
    }

    public TransientEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        final TransientEnum other = (TransientEnum) obj;
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
        hash = 79 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }
}
