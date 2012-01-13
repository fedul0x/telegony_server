package telegony.viw.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.hardware.SensorReadingsType;

/**
 * Контрол для выбора типа показаний устройства
 * @author Ivashin Alexey
 */
public class SensorReadingsTypeField extends Select {

    private DataProvider dp = new DataProvider() {

        @Override
        public List<Option> getData() {
            List<Option> result = new ArrayList<Option>();
            for (SensorReadingsType type : SensorReadingsType.ALL_STAUSES) {
                result.add(new Option(type.getId(), type.getDescription()));
            }
            return result;
        }
    };
//TODO Убрать setDataProvider(dp); из конструкторов

    public SensorReadingsTypeField(String name) {
        super(name);
        setDataProvider(dp);
    }

    public SensorReadingsTypeField(String name, String label) {
        super(name, label);
        setDataProvider(dp);
    }

    public SensorReadingsTypeField(String name, String label, boolean required) {
        super(name, label, required);
        setDataProvider(dp);
    }

    public SensorReadingsTypeField(String name, boolean required) {
        super(name, required);
        setDataProvider(dp);
    }

    @Override
    public SensorReadingsType getValueObject() {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            return (SensorReadingsType.getById(Long.valueOf(value)));
        }
    }

    @Override
    public void setValueObject(Object object) {
        if (object instanceof SensorReadingsType) {
            SensorReadingsType type = (SensorReadingsType) object;
            value = String.valueOf((type).getId());
        } else {
            throw new IllegalArgumentException("Объект не является экземляром SensorReadingsType");
        }
    }

    public void setDefaultReadingsType(SensorReadingsType type) {
        String id = String.valueOf((Long) type.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
            }
        }
    }
}
