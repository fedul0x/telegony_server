package telegony.view.page;

import java.util.List;
import org.apache.click.control.Button;
import org.apache.click.control.Form;
import org.apache.click.control.Option;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.DoubleField;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.Zone;
import telegony.hardware.ActivityState;
import telegony.hardware.SensorDevice;
import telegony.hardware.SensorReadingsType;

/**
 * Страница редактирования сенсорного механизма
 * @author Ivashin Alexey
 */
public class SensorDeviceEditer extends FramePage {

    public SensorDeviceEditer() {
        super("Редактирование настроек сенсорного устройства");
    }

    public SensorDeviceEditer(String title) {
        super(title);
    }

    @Override
    public void onInit() {
        super.onInit();
        Long id = Long.valueOf(getContext().getRequestParameterValues("id")[0]);

        SensorDevice sd = (SensorDevice) RepositoryProvider.getRepository(SensorDevice.class).findById(id);

        Form form = new Form("editForm");
        TextField name = new TextField("deviceName", "Имя устройства");
        name.setValue(sd.getName());
        form.add(name);

        Select zones = new Select("zone", "Зона");
        List<Zone> allZones = (List<Zone>) RepositoryProvider.getRepository(Zone.class).findAll();
        addModel("id", allZones.size());
        for (Zone zone : allZones) {
            Option op = new Option(zone, zone.getDescription());
            zones.add(op);
        }
        zones.setValue(String.valueOf((Long) sd.getZone().getId()));
        form.add(zones);


        Select readingsType = new Select("readingsType", "Тип показаний");
        for (SensorReadingsType type : SensorReadingsType.ALL_STAUSES) {
            Option op = new Option(type, type.getDescription());
            readingsType.add(op);
        }
        readingsType.setValue(sd.getReadingsType().getDescription());
        form.add(readingsType);

        TextField unit = new TextField("units", "Единицы измерения");
        unit.setValue(sd.getUnits());
        form.add(unit);

        DoubleField lowLimit = new DoubleField("lowLimit", "Нижний предел");
        lowLimit.setDouble(sd.getLowLimit());
        lowLimit.setValidate(true);
        form.add(lowLimit);

        DoubleField highLimit = new DoubleField("hignLimit", "Верхний предел");
        highLimit.setDouble(sd.getHighLimit());
        highLimit.setValidate(true);
        form.add(highLimit);

        DoubleField width = new DoubleField("width", "Вес показаний");
        width.setDouble(sd.getWidth());
        width.setValidate(true);
        form.add(width);

        Select states = new Select("state", "Активность");
        for (ActivityState state : ActivityState.ALL_STAUSES) {
            Option op = new Option(state, state.getDescription());
            states.add(op);
        }
        states.setValue(sd.getState().getDescription());
        form.add(states);

        TextArea desc = new TextArea("description", "Описание");
        desc.setValue(sd.getDescription());
        form.add(desc);

        form.add(new Button("back", "Вернуться"));
        form.add(new Submit("reset", "Сбросить"));
        form.add(new Submit("submit", "Изменить"));

        addControl(form);
    }
}
