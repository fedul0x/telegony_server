package telegony.view.component;

import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.TransientEnum;
import telegony.general.TransientObject;

/*
 * Контрол для выбора персистентных объектов
 * @author Ivashin Alexey
 */
public class PersistentSelectField extends Select {

    private Class<TransientEnum> enumType;

    public PersistentSelectField(Class type, String name) {
        super(name);
        enumType = type;
    }

    public PersistentSelectField(Class type, String name, String label) {
        this(type, name);
        setLabel(label);
    }

    public PersistentSelectField(Class type, String name, boolean required) {
        this(type, name);
        setRequired(required);
    }

    public PersistentSelectField(Class type, String name, String label, boolean required) {
        this(type, name);
        setLabel(label);
        setRequired(required);
    }

    @Override
    public TransientObject getValueObject() {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            return RepositoryProvider.getRepository(enumType).findById(Long.valueOf(value));
        }
    }

//    @Override
//    public void setValueObject(TransientEnum object) {
//        value = String.valueOf(object.getId());
//    }

    public void setDefaultZone(TransientEnum object) {
        String id = String.valueOf((Long) object.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
                break;
            }
        }
    }
}
