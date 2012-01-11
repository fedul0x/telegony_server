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

    private ActionLink deleteLink;

    public SensorDeviceTable() {
        super("Список устройств");

        //TODO последний параметр - класс страницы редактирования
        PageLink addLink = new PageLink("addLink", "Добавление", SensorDeviceEditer.class);
        addLink.setImageSrc("/img/table-add.png");
        addLink.setTitle("Добавить запись подобную данной");
//        addLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");

        //TODO последний параметр - класс страницы редактирования
        PageLink editLink = new PageLink("editLink", "Редактирование", SensorDeviceEditer.class);
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
        setAddingLink(addLink);
    }

    @Override
    protected List<Column> getDataColumns() {
        List<Column> columns = new LinkedList<Column>();
        Column column = new Column("id", "Номер");
//        column.setDecorator(new Decorator() {
//            
//            @Override
//            public String render(Object object, Context context) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//        });
        column.setWidth("0px");
        columns.add(column);
        columns.add(new Column("name", "Имя устройства"));
        //        TODO Надо поменять на zone.description, но "Error getting property 'description' from class telegony.general.Zone"
        columns.add(new Column("zone.id", "Зона"));
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

    @Override
    protected boolean onDeleteClick() {
        Long id = deleteLink.getValueLong();
        RepositoryProvider.getRepository(getDataType()).removeById(id);
        return true;
    }
}
