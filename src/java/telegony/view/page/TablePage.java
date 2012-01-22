package telegony.view.page;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.click.ActionListener;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.control.Table;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.extras.control.LinkDecorator;
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
     * Ссылки для удаления, редактирования и вставки
     */
    private AbstractLink insertingLink = null;
    private AbstractLink editingLink = null;
    private AbstractLink deletingLink = null;
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
//        TODO Что за класс такой?
        dataTable.setClass("blue1");
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

    public void setInsertingLink(AbstractLink insertingLink) {
        if (insertingLink != null) {
            this.insertingLink = (PageLink) insertingLink;
        } else {
            this.insertingLink = null;
        }
        refreshActionItems();
    }

    public void setDeletingLink(AbstractLink deletingLink) {
        if (deletingLink != null) {
            this.deletingLink = (ActionLink) deletingLink;
        } else {
            this.deletingLink = null;
        }
        refreshActionItems();
    }

    public void setEditingLink(AbstractLink editingLink) {
        if (editingLink != null) {
            this.editingLink = (PageLink) editingLink;
        } else {
            this.editingLink = null;
        }
        refreshActionItems();
    }

    public AbstractLink getInsertingLink() {
        return insertingLink;

    }

    public AbstractLink getDeletingLink() {
        return deletingLink;
    }

    public AbstractLink getEditingLink() {
        return editingLink;

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
    /*
     * Добавляет ссылки в столбец "Действия" таблицы
     */

    private void refreshActionItems() {
        String tableName = "Действия";
        if (dataTable.getColumns().containsKey(tableName)) {
            dataTable.removeColumn(tableName);
        }
        Column column = new Column(tableName);
        column.setTextAlign("center");
        List<AbstractLink> links = new LinkedList<AbstractLink>();
        if (editingLink != null) {
            links.add(editingLink);
        }
        if (insertingLink != null) {
            links.add(insertingLink);
        }
        if (deletingLink != null) {
            links.add(deletingLink);
        }
        //TODO Можно изменить имя параметра id
        column.setDecorator(new LinkDecorator(dataTable, links, "id"));
        column.setSortable(false);
        dataTable.addColumn(column);
    }
}
