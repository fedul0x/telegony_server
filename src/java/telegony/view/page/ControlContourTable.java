package telegony.view.page;

import java.util.List;
import org.apache.click.control.Column;
import java.util.LinkedList;
import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.PagingDataProvider;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.ControlContour;

/**
 * Страница отображения доступных контуров управления
 * @author Ivashin Alexey
 */
public class ControlContourTable extends EditableTablePage{

    public ControlContourTable() {
//        TODO Не работает ссылка удаления записи
        super("Список контуров управления", "Таблица доступных контуров управления");
        PageLink addLink = new PageLink("addLink", "Добавление", ControlContourInserter.class);
        addLink.setImageSrc("/img/table-add.png");
        addLink.setTitle("Добавить запись подобную данной");
//        addLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");

        PageLink editLink = new PageLink("editLink", "Редактирование", ControlContourEditor.class);
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
            PageLink addLink = new PageLink("addLink", "Добавление", ControlContourInserter.class);
            addLink.setImageSrc("/img/table-add.png");
            addLink.setLabel("Добавить новый контур управления");
            addLink.setTitle("Добавить новый контур управления");
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
        columns.add(new Column("name", "Имя контура"));
        columns.add(new Column("flowSensor.name", "Датчик расхода"));
        columns.add(new Column("temperatureInputSensor.name", "Датчик температуры на трубе на входе"));
        columns.add(new Column("temperatureOutputSensor.name", "Датчик температуры на трубе на выходе"));
        columns.add(new Column("temperatureInsideSensor.name", "Датчик температуры внутри помещения"));
        columns.add(new Column("temperatureOutsideSensor.name", "Датчик температуры снаружи помещения"));
        columns.add(new Column("flowRegulator.name", "Исполнительный механизм регулирования расхода"));
        columns.add(new Column("description", "Описание"));
        return columns;
    }

    @Override
    protected Class getDataType() {
        return ControlContour.class;
    }
    
    
    
}
