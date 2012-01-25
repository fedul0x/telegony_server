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
import telegony.hardware.ImpactDevice;
import telegony.hardware.ImpactDeviceType;
import telegony.hardware.SensorDevice;
import telegony.view.component.ActivityStateField;
import telegony.view.component.PersistentSelectField;

/**
 * Страница добавления исполнительного механизма
 * @author Ivashin Alexey
 */
public class ImpactDeviceInserter extends FramePage {

//    TODO Добавить обязательные поля 
    @Bindable
    private ImpactDevice id;
    @Bindable
    private Form form = new Form("editForm");
    private TextField name = new TextField("name", "Имя устройства");
    private PersistentSelectField deviceType = new PersistentSelectField(ImpactDeviceType.class, "deviceType", "Тип устройства");
    private TextField unit = new TextField("units", "Единицы измерения");
    private DoubleField lowLimit = new DoubleField("lowLimit", "Нижний предел");
    private DoubleField highLimit = new DoubleField("highLimit", "Верхний предел");
    private TextArea desc = new TextArea("description", "Описание");
    private ActivityStateField state = new ActivityStateField("state", "Активность");
    private HiddenField idField = new HiddenField("id", Long.class);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Добавить");

    public ImpactDeviceInserter() {
        super("Добавление нового исполнительного устройства");

        form.add(name);
        form.add(deviceType);
        form.add(unit);
        form.add(lowLimit);
        form.add(highLimit);
        form.add(state);
        form.add(desc);
        form.add(idField);

        backButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                setRedirect(SensorDeviceTable.class);
                return false;
            }
        });
        sendButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                if (form.isValid()) {
                    id = new ImpactDevice();
                    form.copyTo(id);
                    //TODO Проверять есть ли такая сущность с таким id, если есть - наращивать id в цикле
                    id.setId(RepositoryProvider.getRepository(SensorDevice.class).getTotalCount() + 1);
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
    }
}
