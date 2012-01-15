package telegony.view.page;

import java.io.Serializable;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Reset;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.DoubleField;
import org.apache.click.util.Bindable;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.SensorDevice;
import telegony.viw.component.ActivityStateField;
import telegony.viw.component.SensorReadingsTypeField;
import telegony.viw.component.ZoneField;

/**
 * Страница добавления сенсорного механизма
 * @author Ivashin Alexey
 */
public class SensorDeviceInserter extends FramePage {

//    TODO Добавить обязательные поля 
    @Bindable private SensorDevice sensorDevice;
    @Bindable private Form form = new Form("editForm");
    private TextField name = new TextField("name", "Имя устройства");
    private ZoneField zone = new ZoneField("zone", "Зона");
    private SensorReadingsTypeField readingsType = new SensorReadingsTypeField("readingsType", "Тип показаний");
    private TextField unit = new TextField("units", "Единицы измерения");
    private DoubleField lowLimit = new DoubleField("lowLimit", "Нижний предел");
    private DoubleField highLimit = new DoubleField("highLimit", "Верхний предел");
    private DoubleField width = new DoubleField("width", "Вес показаний");
    private ActivityStateField state = new ActivityStateField("state", "Активность");
    private TextArea desc = new TextArea("description", "Описание");
    private HiddenField idField = new HiddenField("id", Long.class);

    public SensorDeviceInserter() {
        super("Добавление нового сенсорного устройства");

        form.add(name);
        form.add(zone);
        form.add(readingsType);
        form.add(unit);
        form.add(lowLimit);
        form.add(highLimit);
        form.add(width);
        form.add(state);
        form.add(desc);
        form.add(idField);

        form.add(new Submit("back", "Вернуться", this, "onBackPress"));
        form.add(new Reset("reset", "Сбросить"));
        form.add(new Submit("submit", "Добавить", this, "onSubmitPress"));
    }

    @Override
    public void onInit() {
        super.onInit();

        Long id;
        if (getContext().hasRequestParameter("id")) {
            try {
                id = Long.valueOf(getContext().getRequestParameter("id"));
            } catch (NumberFormatException ex) {
                return;
            }
        } else {
            return;
        }

        sensorDevice = (SensorDevice) RepositoryProvider.getRepository(SensorDevice.class).findById(id);
        form.copyFrom(sensorDevice);
    }

    public boolean onBackPress() {
        setRedirect(SensorDeviceTable.class);
        return false;
    }

    public boolean onSubmitPress() {
        if (form.isValid()) {
            sensorDevice = new SensorDevice();
            form.copyTo(sensorDevice);
            //TODO Проверять есть ли такая сущность с таким id, если есть - наращивать id в цикле
            sensorDevice.setId(RepositoryProvider.getRepository(SensorDevice.class).getTotalCount() + 1);
            RepositoryProvider.getRepository(SensorDevice.class).save(sensorDevice);
        }
        setRedirect(SensorDeviceTable.class);
        return false;
    }
}
