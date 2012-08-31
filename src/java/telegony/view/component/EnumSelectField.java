package telegony.view.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.TransientEnum;
import telegony.dataaccess.common.TransientObject;


/*
 * Контрол для выбора объектов из списка
 * @author Ivashin Alexey
 */
public class EnumSelectField extends Select {

    private Class<TransientEnum> enumType;

    public EnumSelectField(Class type, String name) {
        super(name);
        enumType = type;
        DataProvider dp = new DataProvider() {

            @Override
            public List<Option> getData() {
                //TODO ЗАменить на addAll
                List<TransientEnum> allItems = (List<TransientEnum>) RepositoryProvider.getRepository(enumType).findAll();
                List<Option> result = new ArrayList<Option>();
                for (TransientEnum item : allItems) {
                    result.add(new Option(item.getId(), item.getDescription()));
                }
                return result;

            }
        };
        setDataProvider(dp);
    }

    public EnumSelectField(Class type, String name, String label) {
        this(type, name);
        setLabel(label);
    }

    public EnumSelectField(Class type, String name, boolean required) {
        this(type, name);
        setRequired(required);
    }

    public EnumSelectField(Class type, String name, String label, boolean required) {
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
