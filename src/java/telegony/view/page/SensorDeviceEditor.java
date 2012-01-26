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
import telegony.hardware.SensorDevice;
import telegony.view.component.ActivityStateField;
import telegony.view.component.SensorReadingsTypeField;
import telegony.view.component.ZoneField;

/**
 * Страница редактирования сенсорного механизма
 * @author Ivashin Alexey
 */
public class SensorDeviceEditor extends FramePage {
//    TODO Добавить обязательные поля

    @Bindable
    private SensorDevice id;
    @Bindable
    private Form form = new Form("editForm");
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
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Изменить");

    public SensorDeviceEditor() {
        super("Редактирование настроек сенсорного устройства");

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

        backButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                setForward(SensorDeviceTable.class);
                return false;
            }
        });
        sendButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                if (form.isValid()) {
                    form.copyTo(id);
                    RepositoryProvider.getRepository(SensorDevice.class).save(id);
                }
                setRedirect(SensorDeviceTable.class);
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

        form.copyFrom(id);
        idField.setValueObject(id.getId());
    }
}