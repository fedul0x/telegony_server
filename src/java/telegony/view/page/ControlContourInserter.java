package telegony.view.page;

import java.io.Serializable;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Reset;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.DoubleField;
import org.apache.click.util.Bindable;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.ControlContour;
import telegony.hardware.ActivityState;
import telegony.hardware.ImpactDevice;
import telegony.hardware.ImpactDeviceType;
import telegony.hardware.SensorDevice;
import telegony.hardware.SensorDeviceType;
import telegony.view.component.EnumSelectField;
import telegony.view.component.ObjectSelectField;

/**
 * Страница добавления контура управления
 * @author Ivashin Alexey
 */
public class ControlContourInserter extends FramePage {

//    TODO Добавить обязательные поля 
    @Bindable
    private ControlContour id;
    @Bindable
    private Form form = new Form("editForm");
    private TextField name = new TextField("name", "Имя контура управления");
    private ObjectSelectField flowSensor;
    private ObjectSelectField temperatureInputSensor;
    private ObjectSelectField temperatureOutputSensor;
    private ObjectSelectField temperatureInsideSensor;
    private ObjectSelectField temperatureOutsideSensor;
    private ObjectSelectField flowRegulator;
    private TextArea desc = new TextArea("description", "Описание контура управления");
    private HiddenField idField = new HiddenField("id", Long.class);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Добавить");

    public ControlContourInserter() {
        super("Добавление нового контура управления");
        SensorDevice sd = new SensorDevice();
        sd.setDeviceType(SensorDeviceType.FLOW_SENSOR_DEVICE_ON_THE_TUBE);
        flowSensor = new ObjectSelectField(sd, "flowSensor", "Датчик расхода");
        sd.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_ON_THE_TUBE);
        temperatureInputSensor = new ObjectSelectField(sd, "temperatureInputSensor", "Датчик температуры на трубе на входе");
        temperatureOutputSensor = new ObjectSelectField(sd, "temperatureOutputSensor", "Датчик температуры на трубе на выходе");
        sd.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_INTERNAL);
        temperatureInsideSensor = new ObjectSelectField(sd, "temperatureInsideSensor", "Датчик температуры внутри помещения");
        sd.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_EXTERNAL);
        temperatureOutsideSensor = new ObjectSelectField(sd, "temperatureOutsideSensor", "Датчик температуры снаружи помещения");
        ImpactDevice idev = new ImpactDevice();
        idev.setDeviceType(ImpactDeviceType.FLOW_IMPACT_DEVICE_ON_THE_TUBE);
        flowRegulator = new ObjectSelectField(idev, "flowRegulator", "Исполнительный механизм регулирования расхода");
        form.add(name);
        form.add(flowSensor);
        form.add(temperatureInputSensor);
        form.add(temperatureOutputSensor);
        form.add(temperatureInsideSensor);
        form.add(temperatureOutsideSensor);
        form.add(flowRegulator);
        form.add(desc);
        form.add(idField);

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
                    id = new ControlContour();
                    form.copyTo(id);
                    //TODO Проверять есть ли такая сущность с таким id, если есть - наращивать id в цикле
                    id.setId(RepositoryProvider.getRepository(ControlContour.class).getTotalCount() + 1);
                    RepositoryProvider.getRepository(ControlContour.class).save(id);
                }
                setRedirect(ControlContourTable.class);
                return false;
            }
        });
        form.add(backButton);
        form.add(resetButton);
        form.add(sendButton);
    }

    @Override
    public void onInit() {
        super.onInit();
        if (id != null) {
            form.copyFrom(id);
        }
    }
}
