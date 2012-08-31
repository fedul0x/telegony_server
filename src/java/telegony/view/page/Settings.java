package telegony.view.page;

import org.apache.click.control.Form;
import org.apache.click.extras.control.IntegerField;
import org.apache.click.util.Bindable;
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
import telegony.hardware.SensorDevice;
import telegony.view.component.MultiObjectSelectField;

/**
 * Страница настроек
 * @author Ivashin Alexey
 */
public class Settings extends FramePage {
    //    TODO Добавить обязательные поля

    @Bindable
    private Form form = new Form("settingsForm");
    @Bindable
    FieldSet fs = new FieldSet("editFieldset", "Редактирование зоны");
    private IntegerField surveyPeriod = new IntegerField("period", "Период опроса", false);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Изменить");

    public Settings() {
        super("Настройки");

        fs.add(surveyPeriod);

        backButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
//                setForward(ZoneTable.class);
                return false;
            }
        });
        sendButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                if (form.isValid()) {
                    form.copyTo(id);
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
//        TODO Рассмотреть ситуацию, когда id не указан, так же во всех редаторах
        form.copyFrom(id);
        controlContours.setSelectedValues(id.getControlContours());
        sensorDevices.setSelectedValues(id.getSensorDevices());
        idField.setValueObject(id.getId());
    }
}
