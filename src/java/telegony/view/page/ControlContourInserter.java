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
import telegony.dataaccess.common.ControlContour;
import telegony.hardware.ImpactDevice;
import telegony.hardware.ImpactDeviceType;
import telegony.hardware.SensorDevice;
import telegony.hardware.SensorDeviceType;
import telegony.view.component.DeviceObjectSelectField;

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
    @Bindable
    FieldSet fs = new FieldSet("editFieldset", "Добавление нового контура управления");
    private TextField name = new TextField("name", "Имя контура управления", false);
    private DeviceObjectSelectField flowSensor;
    private DeviceObjectSelectField temperatureInputSensor;
    private DeviceObjectSelectField temperatureOutputSensor;
    private DeviceObjectSelectField temperatureInsideSensor;
    private DeviceObjectSelectField temperatureOutsideSensor;
    private DeviceObjectSelectField flowRegulator;
    private TextArea desc = new TextArea("description", "Описание контура управления");
    private HiddenField idField = new HiddenField("id", Long.class);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Добавить");

    public ControlContourInserter() {
//        TODO Не работают "обязательные поля"
        super("Добавление нового контура управления");
        SensorDevice sdf = new SensorDevice();
        sdf.setDeviceType(SensorDeviceType.FLOW_SENSOR_DEVICE_ON_THE_TUBE);
        flowSensor = new DeviceObjectSelectField(sdf, "flowSensor", "Датчик расхода", false);
        SensorDevice sdt = new SensorDevice();
        sdt.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_ON_THE_TUBE);
        temperatureInputSensor = new DeviceObjectSelectField(sdt, "temperatureInputSensor", "Датчик температуры на трубе на входе", false);
        temperatureOutputSensor = new DeviceObjectSelectField(sdt, "temperatureOutputSensor", "Датчик температуры на трубе на выходе", false);
        SensorDevice sdte = new SensorDevice();
        sdte.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_INTERNAL);
        temperatureInsideSensor = new DeviceObjectSelectField(sdte, "temperatureInsideSensor", "Датчик температуры внутри помещения", false);
        SensorDevice sdti = new SensorDevice();
        sdti.setDeviceType(SensorDeviceType.TEMPERATUTE_SENSOR_DEVICE_EXTERNAL);
        temperatureOutsideSensor = new DeviceObjectSelectField(sdti, "temperatureOutsideSensor", "Датчик температуры снаружи помещения", false);
        ImpactDevice idev = new ImpactDevice();
        idev.setDeviceType(ImpactDeviceType.FLOW_IMPACT_DEVICE_ON_THE_TUBE);
        flowRegulator = new DeviceObjectSelectField(idev, "flowRegulator", "Исполнительный механизм регулирования расхода", false);
        
        fs.add(name);
        fs.add(flowSensor);
        fs.add(temperatureInputSensor);
        fs.add(temperatureOutputSensor);
        fs.add(temperatureInsideSensor);
        fs.add(temperatureOutsideSensor);
        fs.add(flowRegulator);
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
        form.add(fs);
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
