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

    @Bindable protected Table dataTable = new Table("dataTable");
    /*
     * Хэш-карта ссылок для удаления, редактирования и вставки
     */
    private Map<String, AbstractLink> actionLinks = new LinkedHashMap<String, AbstractLink>();
    /*
     * Количество строк в таблице на одной странице
     */
    private Integer rowCountPerPage = 30;
    /*
     * Заголовок таблицы
     */
    private String tableCaption;

    {
        actionLinks.put("editing", null);
        actionLinks.put("inserting", null);
        actionLinks.put("deleting", null);

    }

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
        String key = "inserting";
        if (actionLinks.get(key) != null) {
            removeControl(actionLinks.get(key));
        }
        if (insertingLink != null) {
            addControl(insertingLink);
        }
        actionLinks.put(key, (PageLink) insertingLink);
        refreshActionItems();
    }

    public void setDeletingLink(AbstractLink deletingLink) {
        String key = "deleting";
        if (actionLinks.get(key) != null) {
            removeControl(actionLinks.get(key));
        }
        if (deletingLink != null) {
            addControl(deletingLink);
        }
        actionLinks.put(key, (ActionLink) deletingLink);
        refreshActionItems();
    }

    public void setEditingLink(AbstractLink editingLink) {
        String key = "editing";
        if (actionLinks.get(key) != null) {
            removeControl(actionLinks.get(key));
        }
        if (editingLink != null) {
            addControl(editingLink);
        }
        actionLinks.put(key, (PageLink) editingLink);
        refreshActionItems();
    }

    public AbstractLink getInsertingLink() {
        return actionLinks.get("inserting");

    }

    public AbstractLink getDeletingLink() {
        return actionLinks.get("deleting");
    }

    public AbstractLink getEditingLink() {
        return actionLinks.get("editing");

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
        for (String key : actionLinks.keySet()) {
            if (actionLinks.get(key) != null) {
                links.add(actionLinks.get(key));
            }
        }
        column.setDecorator(new LinkDecorator(dataTable, links, "id"));
        column.setSortable(false);
        dataTable.addColumn(column);
    }
}
