package telegony.view.page;

import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.control.Column;
import org.apache.click.control.Table;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.util.Bindable;

/**
 * Абстрактный класс всех страниц с таблицами
 * @author Ivashin Alexey
 */
public abstract class TablePage extends FramePage {

    /*
     * Таблица с данными
     */
    @Bindable
    protected Table dataTable = new Table("dataTable");
    
    /*
     * Количество строк в таблице на одной странице
     */
    private Integer rowCountPerPage = 30;
    /*
     * Заголовок таблицы
     */
    private String tableCaption;

    public TablePage(String title) {
        super(title);
        //        addControl(dataTable);
        dataTable.setPageSize(rowCountPerPage);
        dataTable.setDataProvider(getDataProvider());
        for (Column column : getDataColumns()) {
            dataTable.addColumn(column);
        }
        dataTable.setShowBanner(true);
        dataTable.setHoverRows(true);
        dataTable.setSortable(true);
        dataTable.getControlLink().setActionListener(getControlLinkListner());
        dataTable.restoreState(getContext());
    }

    public TablePage(String title, String caption) {
        this(title);
        setTableCaption(caption);
    }

    public String getTableCaption() {
        return tableCaption;
    }

    public void setTableCaption(String tableCaption) {
        if (!tableCaption.equals(this.tableCaption)) {
            this.tableCaption = tableCaption;
            dataTable.setCaption(tableCaption);
        }
    }

    public Integer getRowCountPerPage() {
        return rowCountPerPage;
    }

    public void setRowCountPerPage(Integer rowCountPerPage) {
        this.rowCountPerPage = rowCountPerPage;
        dataTable.setPageSize(rowCountPerPage);
    }

    protected Table getDataTable() {
        return dataTable;
    }

    /*
     * Возвращает список столбцов таблицы
     */

    protected abstract List<Column> getDataColumns();

    /*
     * Возвращает провайдер данных таблицы
     */
    protected abstract DataProvider getDataProvider();

    protected abstract ActionListener getControlLinkListner();

    /*
     * Возвращает класс сущностей, которые отображаются в таблице
     */
    protected abstract Class getDataType();

    
}
