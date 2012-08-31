package telegony.view.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.TransientObject;

/*
 * Контрол для выбора объектов из списка
 * @author Ivashin Alexey1
 */
public class MultiObjectSelectField extends Select {

    private final TransientObject example;

    public MultiObjectSelectField(final TransientObject example, String name) {
        super(name);
        this.example = example;
        DataProvider dp = new DataProvider() {

            @Override
            public List<Option> getData() {
                List<TransientObject> allItems = (List<TransientObject>) RepositoryProvider.getRepository(example.getClass()).findByExample(example);
                List<Option> result = new ArrayList<Option>();
                for (TransientObject item : allItems) {
                    result.add(new Option(item.getId(), item.getDescription()));
                }
                return result;
            }
        };
        setDataProvider(dp);
        setMultiple(true);
    }

    public MultiObjectSelectField(final TransientObject example, String name, String label) {
        this(example, name);
        setLabel(label);
    }

    public MultiObjectSelectField(final TransientObject example, String name, boolean required) {
        this(example, name);
        setRequired(required);
    }

    public MultiObjectSelectField(final TransientObject example, String name, String label, boolean required) {
        this(example, name);
        setLabel(label);
        setRequired(required);
    }

    public void setSelectedValues(Set<? extends TransientObject> multipleValues) {
        List<String> mv = new LinkedList<String>();
        for (TransientObject value : multipleValues) {
            mv.add(String.valueOf(value.getId()));
        }
        super.setSelectedValues(mv);
    }

    @Override
    public Object getValueObject() {
        if (!multiple) {
            if (value == null || value.length() == 0) {
                return null;
            } else {
                return RepositoryProvider.getRepository(example.getClass()).findById(Long.valueOf(value));
            }
        } else {
            Set<TransientObject> set = new HashSet<TransientObject>();
            set.addAll(getSelectedValues());
            return set;
        }
    }

    @Override
    public List getSelectedValues() {
        if (selectedValues != null) {
            List<TransientObject> list = new LinkedList<TransientObject>();
            for (String value : selectedValues) {
                list.add(RepositoryProvider.getRepository(example.getClass()).findById(Long.valueOf(value)));
            }
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    public void setDefaultOption(TransientObject object) {
        String id = String.valueOf((Long) object.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
                break;
            }
        }
    }
}
