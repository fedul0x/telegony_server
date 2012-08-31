package telegony.view.component;

import java.util.ArrayList;
import java.util.List;
import org.apache.click.control.Option;
import org.apache.click.dataprovider.DataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.TransientObject;
import telegony.hardware.Device;

/**
 * Контрол для выбора объектов-устройств из списка
 * @author Ivashin Alexey
 */
public class DeviceObjectSelectField extends ObjectSelectField {

    public DeviceObjectSelectField(final Device example, String name) {
        super(example, name);
        DataProvider dp = new DataProvider() {

            @Override
            public List<Option> getData() {
                List<TransientObject> allItems = (List<TransientObject>) RepositoryProvider.getRepository(example.getClass()).findByExample(example);
                List<Option> result = new ArrayList<Option>();
                for (TransientObject item : allItems) {
                    String label = ((Device) item).getName() + ((!item.getDescription().isEmpty()) ? " \n(" + item.getDescription() + ")" : "");
                    result.add(new Option(item.getId(), label));
                }
                return result;
            }
        };
        setDataProvider(dp);
    }

    public DeviceObjectSelectField(final Device example, String name, boolean required) {
        this(example, name);
        setRequired(required);
    }

    public DeviceObjectSelectField(final Device example, String name, String label) {
        this(example, name);
        setLabel(label);
    }

    public DeviceObjectSelectField(final Device example, String name, String label, boolean required) {
        this(example, name);
        setLabel(label);
        setRequired(required);
    }
}
