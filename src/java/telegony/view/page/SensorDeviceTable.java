package telegony.view.page;

import java.util.LinkedList;
import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.Context;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.Decorator;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.dataprovider.PagingDataProvider;
import telegony.dataaccess.Repository.SORT_ORDER;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.SensorDevice;

/**
 * Страница списка устройств
 * @author Ivashin Alexey
 */
public class SensorDeviceTable extends TablePage {

    public SensorDeviceTable() {
        super("Список устройств");

        PageLink addLink = new PageLink("addLink", "Добавление", SensorDeviceInserter.class);
        addLink.setImageSrc("/img/table-add.png");
        addLink.setTitle("Добавить запись подобную данной");
//        addLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");

        PageLink editLink = new PageLink("editLink", "Редактирование", SensorDeviceEditor.class);
        editLink.setImageSrc("/img/table-edit.png");
        editLink.setTitle("Редактировать данную запись");
//        editLink.setParameter("referrer", "/edit-sensor-device.htm");

        setTableCaption("Таблица сенсорных механизмов");
        ActionLink deleteLink = new ActionLink("delteLink", "Удаление", this, "onDeleteClick");
        deleteLink.setImageSrc("/img/table-delete.png");
        deleteLink.setTitle("Удалить данную запись");
        deleteLink.setAttribute("onclick",
                "return window.confirm('Вы действительно желаете удалить данную запись?');");
        setDeletingLink(deleteLink);
        setEditingLink(editLink);
        setInsertingLink(addLink);
    }

    @Override
    protected List<Column> getDataColumns() {
        List<Column> columns = new LinkedList<Column>();
        Column column = new Column("id", "Номер");
//        Скрываем ячейку от пользователя, но она нужна для формирования ссылок
        column.setAttribute("hidden", "hidden");
        columns.add(column);
        columns.add(new Column("name", "Имя устройства"));
        columns.add(new Column("zone.description", "Зона"));
        columns.add(new Column("readingsType.description", "Тип показаний"));
        columns.add(new Column("units", "Единицы измерения"));
        columns.add(new Column("lowLimit", "Нижний предел"));
        columns.add(new Column("highLimit", "Верхний предел"));
        columns.add(new Column("width", "Вес показаний"));
        columns.add(new Column("state.description", "Активность"));
        columns.add(new Column("description", "Описание"));
        return columns;
    }

    @Override
    protected DataProvider getDataProvider() {
        DataProvider da = new PagingDataProvider() {

            @Override
            public int size() {
                return RepositoryProvider.getRepository(SensorDevice.class).getTotalCount().intValue();
            }

            @Override
            public List<SensorDevice> getData() {
                int start = getDataTable().getFirstRow();
                int count = getDataTable().getPageSize();
                String sortColumn = getDataTable().getSortedColumn();
                SORT_ORDER order = getDataTable().isSortedAscending() ? SORT_ORDER.ASC : SORT_ORDER.DESC;
                return RepositoryProvider.getRepository(SensorDevice.class).findRange(start, count, sortColumn, order);
            }
        };
        return da;
    }

    @Override
    protected ActionListener getControlLinkListner() {
        ActionListener al = new ActionListener() {

            @Override
            public boolean onAction(Control source) {
                getDataTable().saveState(getContext());
                return true;
            }
        };
        return al;
    }

    @Override
    protected Class getDataType() {
        return SensorDevice.class;
    }

    protected boolean onDeleteClick() {
        ActionLink delitingLink = (ActionLink) getDeletingLink();
        if (delitingLink != null) {
            Long id = delitingLink.getValueLong();
            RepositoryProvider.getRepository(getDataType()).removeById(id);
            return true;
        }
        return false;
    }
}
