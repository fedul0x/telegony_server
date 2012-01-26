package telegony.view.page;

import java.util.LinkedList;
import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.PagingDataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.ImpactDevice;

/**
 * Страница списка исполнительных устройств
 * @author Ivashin Alexey
 */
public class ImpactDeviceTable extends EditableTablePage {

    public ImpactDeviceTable() {
        super("Список устройств", "Таблица исполнительных механизмов");
        PageLink addLink = new PageLink("addLink", "Добавление", ImpactDeviceInserter.class);
        addLink.setImageSrc("/img/table-add.png");
        addLink.setTitle("Добавить запись подобную данной");
//        addLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");

        PageLink editLink = new PageLink("editLink", "Редактирование", ImpactDeviceEditor.class);
        editLink.setImageSrc("/img/table-edit.png");
        editLink.setTitle("Редактировать данную запись");
//        editLink.setParameter("referrer", "/edit-sensor-device.htm");


        ActionLink deleteLink = new ActionLink("deleteLink", "Удаление");
        deleteLink.setActionListener(new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                ActionLink delitingLink = (ActionLink) getDeletingLink();
                if (delitingLink != null) {
                    Long id = delitingLink.getValueLong();
                    RepositoryProvider.getRepository(getDataType()).removeById(id);
                    return true;
                }
                return false;
            }
        });
        deleteLink.setImageSrc("/img/table-delete.png");
        deleteLink.setTitle("Удалить данную запись");
        deleteLink.setAttribute("onclick",
                "return window.confirm('Вы действительно желаете удалить данную запись?');");
        setDeletingLink(deleteLink);
        setEditingLink(editLink);
        setInsertingLink(addLink);
    }

    @Override
    public void onInit() {
        super.onInit();
        if (((PagingDataProvider)getDataProvider()).size() == 0) {
            PageLink addLink = new PageLink("addLink", "Добавление", ImpactDeviceInserter.class);
            addLink.setImageSrc("/img/table-add.png");
            addLink.setLabel("Добавить новый исполнительный механизм");
            addLink.setTitle("Добавить новый исполнительный механизм");
            addLink.setRenderLabelAndImage(true);
            addControl(addLink);
        }
    }

    @Override
    protected List<Column> getDataColumns() {
        List<Column> columns = new LinkedList<Column>();
        Column column = new Column("id", "Номер");
//        Скрываем ячейку от пользователя, но она нужна для декорирования ссылок
        column.setAttribute("hidden", "hidden");
        columns.add(column);
        columns.add(new Column("name", "Имя устройства"));
        columns.add(new Column("deviceType.description", "Тип устройства"));
        columns.add(new Column("units", "Единицы измерения"));
        columns.add(new Column("lowLimit", "Нижний предел"));
        columns.add(new Column("highLimit", "Верхний предел"));
        columns.add(new Column("state.description", "Активность"));
        columns.add(new Column("description", "Описание"));
        return columns;
    }

    @Override
    protected Class getDataType() {
        return ImpactDevice.class;
    }
}
