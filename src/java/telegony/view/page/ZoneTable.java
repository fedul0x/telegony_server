package telegony.view.page;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.PagingDataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.ControlContour;
import telegony.general.Zone;
import telegony.hardware.SensorDevice;

/**
 * Страница списка сенсорных устройств
 * @author Ivashin Alexey
 */
public class ZoneTable extends EditableTablePage {
//    TODO Не работает сортировка по TransientEnum

    public ZoneTable() {
//        TODO Не работает ссылка удаления записи
        super("Список зон", "Таблица устанволенных зон");
        
        
//        Zone z = new Zone();
//        Set<ControlContour> cc = new HashSet<ControlContour>();
//        cc.add((ControlContour)RepositoryProvider.getRepository(ControlContour.class).findById(1L));
//        cc.add((ControlContour)RepositoryProvider.getRepository(ControlContour.class).findById(2L));
//        z.setControlContours(cc);
//        Set<SensorDevice> sd = new HashSet<SensorDevice>();
//        sd.add((SensorDevice)RepositoryProvider.getRepository(SensorDevice.class).findById(1L));
//        sd.add((SensorDevice)RepositoryProvider.getRepository(SensorDevice.class).findById(2L));
//        z.setSensorDevices(sd);
//        z.setId(1L);
//        RepositoryProvider.getRepository(Zone.class).save(z);
//        Zone z = new Zone();
//        Set<ControlContour> cc = new HashSet<ControlContour>();
//        cc.add((ControlContour)RepositoryProvider.getRepository(ControlContour.class).findById(1L));
//        cc.add((ControlContour)RepositoryProvider.getRepository(ControlContour.class).findById(2L));
//        z.setControlContours(cc);
//        Set<SensorDevice> sd = new HashSet<SensorDevice>();
//        sd.add((SensorDevice)RepositoryProvider.getRepository(SensorDevice.class).findById(1L));
//        sd.add((SensorDevice)RepositoryProvider.getRepository(SensorDevice.class).findById(2L));
//        z.setSensorDevices(sd);
//        z.setId(2L);
//        RepositoryProvider.getRepository(Zone.class).save(z);
        
        PageLink addLink = new PageLink("addLink", "Добавление", ZoneInserter.class);
        addLink.setImageSrc("/img/table-add.png");
        addLink.setTitle("Добавить запись подобную данной");
//        addLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");

        PageLink editLink = new PageLink("editLink", "Редактирование", ZoneEditor.class);
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
        if (((PagingDataProvider) getDataProvider()).size() == 0) {
            PageLink addLink = new PageLink("addLink", "Добавление", ZoneInserter.class);
            addLink.setImageSrc("/img/table-add.png");
            addLink.setLabel("Добавить новую зону");
            addLink.setTitle("Добавить новую зону");
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
        columns.add(new Column("name", "Имя зоны"));
        columns.add(new Column("controlContours", "Контуры управления"));
        columns.add(new Column("sensorDevices", "Устройства мониторинга"));
        columns.add(new Column("description", "Описание"));
        return columns;
    }

    @Override
    protected Class getDataType() {
        return Zone.class;
    }
}
