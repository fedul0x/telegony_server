package telegony.view.page;

import java.util.LinkedList;
import java.util.List;
import org.apache.click.control.Column;
import telegony.hardware.ImpactDevice;

/**
 * Страница списка исполнительных устройств
 * @author Ivashin Alexey
 */
public class ImpactDeviceTable extends EditableTablePage {

    public ImpactDeviceTable() {
        super("Список устройств", "Таблица исполнительных механизмов");
    }

    @Override
    public void onInit() {
        super.onInit();

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
