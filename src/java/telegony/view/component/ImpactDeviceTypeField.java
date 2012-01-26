package telegony.view.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.dataprovider.DataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.Zone;

/**
 * Контрол для выбора типа устройства устройства
 * @author Ivashin Alexey
 */
@Deprecated
public class ImpactDeviceTypeField extends Select {

    private DataProvider dp = new DataProvider() {

        @Override
        public List<Option> getData() {
            //TODO ЗАменить на addAll
            List<Zone> allZones = (List<Zone>) RepositoryProvider.getRepository(Zone.class).findAll();
            List<Option> result = new ArrayList<Option>();
            for (Zone zone : allZones) {
                result.add(new Option(zone.getId(), zone.getDescription()));
            }
            return result;

        }
    };
//TODO Убрать setDataProvider(dp); из конструкторов

    public ImpactDeviceTypeField(String name) {
        super(name);
        setDataProvider(dp);
    }

    public ImpactDeviceTypeField(String name, String label) {
        super(name, label);
        setDataProvider(dp);
    }

    public ImpactDeviceTypeField(String name, String label, boolean required) {
        super(name, label, required);
        setDataProvider(dp);
    }

    public ImpactDeviceTypeField(String name, boolean required) {
        super(name, required);
        setDataProvider(dp);
    }

    @Override
    public Zone getValueObject() {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            return (Zone) RepositoryProvider.getRepository(Zone.class).findById(Long.valueOf(value));
        }
    }
    
    @Override
    public void setValueObject(Object object) {
        if (object instanceof Zone) {
            Zone zone = (Zone) object;
            value = String.valueOf((zone).getId());
        }
        else throw new IllegalArgumentException("Объект не является экземляром Zone");
    }

    public void setDefaultZone(Zone zone) {
        String id = String.valueOf((Long) zone.getId());
        for (Option option : (List<Option>) getOptionList()) {
            if (option.getValue().equals(id)) {
                setValue(option.getValue());
                break;
            }
        }
    }
}