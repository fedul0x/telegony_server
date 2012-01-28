package telegony.view.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.TransientObject;


/*
 * Контрол для выбора объектов из списка
 * @author Ivashin Alexey
 */
public class ObjectSelectField extends Select {

    private final TransientObject example;

    public ObjectSelectField(final TransientObject example, String name) {
        super(name);
        this.example = example;
        DataProvider dp = new DataProvider() {

            @Override
            public List<Option> getData() {
                //TODO ЗАменить на addAll
                List<TransientObject> allItems = (List<TransientObject>) RepositoryProvider.getRepository(example.getClass()).findByExample(example);
                List<Option> result = new ArrayList<Option>();
                for (TransientObject item : allItems) {
                    result.add(new Option(item.getId(), item.getDescription()));
                }
                return result;

            }
        };
        setDataProvider(dp);
    }

    public ObjectSelectField(final TransientObject example, String name, String label) {
        this(example, name);
        setLabel(label);
    }

    public ObjectSelectField(final TransientObject example, String name, boolean required) {
        this(example, name);
        setRequired(required);
    }

    public ObjectSelectField(final TransientObject example, String name, String label, boolean required) {
        this(example, name);
        setLabel(label);
        setRequired(required);
    }

    @Override
    public TransientObject getValueObject() {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            return RepositoryProvider.getRepository(example.getClass()).findById(Long.valueOf(value));
        }
    }

    public void setDefaultObject(TransientObject object) {
        String id = String.valueOf((Long) object.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
                break;
            }
        }
    }
}
