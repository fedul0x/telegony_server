package telegony.view.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.hardware.ActivityState;

/**
 * Контрол для выбора статуса активности устройства
 * @author Ivashin Alexey
 */
@Deprecated
public class ActivityStateField extends Select {

    private DataProvider dp = new DataProvider() {

        @Override
        public List<Option> getData() {
            List<Option> result = new ArrayList<Option>();
            for (ActivityState state : ActivityState.ALL_STAUSES) {
                result.add(new Option(state.getId(), state.getDescription()));
            }
            return result;
        }
    };
//TODO Убрать setDataProvider(dp); из конструкторов

    public ActivityStateField(String name) {
        super(name);
        setDataProvider(dp);
    }

    public ActivityStateField(String name, String label) {
        super(name, label);
        setDataProvider(dp);
    }

    public ActivityStateField(String name, String label, boolean required) {
        super(name, label, required);
        setDataProvider(dp);
    }

    public ActivityStateField(String name, boolean required) {
        super(name, required);
        setDataProvider(dp);
    }

    @Override
    public ActivityState getValueObject() {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            return (ActivityState.getById(Long.valueOf(value)));
        }
    }

    @Override
    public void setValueObject(Object object) {
        if (object instanceof ActivityState) {
            ActivityState state = (ActivityState) object;
            value = String.valueOf((state).getId());
        } else {
            throw new IllegalArgumentException("Объект не является экземляром ActivityState");
        }
    }

    public void setDefaultActivityState(ActivityState state) {
        String id = String.valueOf((Long) state.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
            }
        }
    }
}
