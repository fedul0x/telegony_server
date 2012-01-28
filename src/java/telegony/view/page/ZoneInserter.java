package telegony.view.page;

import java.io.Serializable;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Reset;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.ControlContour;
import telegony.general.Zone;
import telegony.hardware.ImpactDevice;
import telegony.hardware.ImpactDeviceType;
import telegony.hardware.SensorDevice;
import telegony.hardware.SensorDeviceType;
import telegony.view.component.DeviceObjectSelectField;
import telegony.view.component.MultiObjectSelectField;

/**
 * Страница добавления зоны
 * @author Ivashin Alexey
 */
public class ZoneInserter extends FramePage {
//    TODO Сильно переписать!

//    TODO Добавить обязательные поля 
    @Bindable
    private Zone id;
    @Bindable
    private Form form = new Form("editForm");
    @Bindable
    FieldSet fs = new FieldSet("editFieldset", "Редактирование зоны");
    private TextField name = new TextField("name", "Имя зоны", false);
    private MultiObjectSelectField controlContours;
    private MultiObjectSelectField sensorDevices;
    private TextArea desc = new TextArea("description", "Описание зоны");
    private HiddenField idField = new HiddenField("id", Long.class);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Добавить");

    public ZoneInserter() {
//        TODO Не работают "обязательные поля"
        super("Добавление нового контура управления");
        ControlContour cc = new ControlContour();
        controlContours = new MultiObjectSelectField(cc, "controlContours", "Контуры управления", false);
        SensorDevice sd = new SensorDevice();
        sensorDevices = new MultiObjectSelectField(sd, "sensorDevices", "Устройства мониторинга", false);

        fs.add(name);
        fs.add(controlContours);
        fs.add(sensorDevices);
        fs.add(desc);
        fs.add(idField);

        backButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                setRedirect(ControlContourTable.class);
                return false;
            }
        });
        sendButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                if (form.isValid()) {
                    id = new Zone();
                    form.copyTo(id);
                    //TODO Проверять есть ли такая сущность с таким id, если есть - наращивать id в цикле
                    id.setId(RepositoryProvider.getRepository(Zone.class).getTotalCount() + 1);
                    RepositoryProvider.getRepository(Zone.class).save(id);
                }
                setRedirect(ZoneTable.class);
                return false;
            }
        });
        form.add(fs);
        form.add(backButton);
        form.add(resetButton);
        form.add(sendButton);
    }

    @Override
    public void onInit() {
        super.onInit();
        if (id != null) {
            controlContours.setSelectedValues(id.getControlContours());
            sensorDevices.setSelectedValues(id.getSensorDevices());
            form.copyFrom(id);
        }
    }
}
