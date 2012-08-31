package telegony.view.page;

import org.apache.click.extras.control.IntegerField;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Reset;
import org.apache.click.control.Submit;
import org.apache.click.control.TextArea;
import org.apache.click.util.Bindable;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.SystemSettings;

/**
 * Страница настроек
 * @author Ivashin Alexey
 */
public class SettingsPage extends FramePage {
    //    TODO Добавить обязательные поля

    private SystemSettings id = (SystemSettings) RepositoryProvider.getRepository(SystemSettings.class).findById(new Long(0));
    @Bindable
    private Form form = new Form("settingsForm");
    @Bindable
    FieldSet fs = new FieldSet("editFieldset", "Редактирование зоны");
    private IntegerField surveyPeriod = new IntegerField("surveyPeriod", "Период опроса датчиков, с", true);
    private IntegerField impactPeriod = new IntegerField("impactPeriod", "Период принудительной выдачи управляющего воздействия, с", true);
    private TextArea desc = new TextArea("description", "Описание зоны");
    private HiddenField idField = new HiddenField("id", Long.class);
    private Submit backButton = new Submit("back", "Вернуться");
    private Reset resetButton = new Reset("reset", "Сбросить");
    private Submit sendButton = new Submit("submit", "Изменить");

    public SettingsPage() {
        super("Настройки");

        fs.add(surveyPeriod);
        fs.add(impactPeriod);
        fs.add(desc);
        fs.add(idField);

        backButton.setActionListener(new ActionListener() {


            @Override
            public boolean onAction(Control source) {
//TODO Куда бекать???            
//                setForward(ZoneTable.class);
                return false;
            }
        });
        sendButton.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                if (form.isValid()) {
                    form.copyTo(id);
                    RepositoryProvider.getRepository(SystemSettings.class).save(id);
                }
//TODO Куда редиректить???
//                setRedirect(ZoneTable.class);
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
        idField.setValueObject(id.getId());
    }
}
