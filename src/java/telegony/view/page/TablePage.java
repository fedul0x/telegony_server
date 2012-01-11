package telegony.view.page;

import java.util.HashMap;
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
import telegony.dataaccess.RepositoryProvider;

/**
 * Абстрактный класс всех страниц с таблицами
 * @author Ivashin Alexey
 */
public abstract class TablePage extends FramePage {

    private Table dataTable = new Table("dataTable");
    private Map<String, AbstractLink> actionLinks = new HashMap<String, AbstractLink>();
    private Integer rowCountPerPage = 30;
    private String tableCaption;
    {
        actionLinks.put("deleting", null);
        actionLinks.put("editing", null);
        actionLinks.put("adding", null);

    }

    public TablePage(String title) {
        super(title);
        addControl(dataTable);
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

    //TODO Вынести за пределы класса
    //<editor-fold defaultstate="collapsed" desc="comment">
    protected boolean onDeleteClick() {
        Long id = ((ActionLink) actionLinks.get("deleting")).getValueLong();
        RepositoryProvider.getRepository(getDataType()).removeById(id);
        return true;
    }
    //</editor-fold>

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

    public void setAddingLink(AbstractLink addingLink) {
        String key = "adding";
        if (actionLinks.get(key) != null) {
            removeControl(actionLinks.get(key));
        }
        if (addingLink != null) {
            addControl(addingLink);
        }
        actionLinks.put(key, (PageLink) addingLink);
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

    protected abstract List<Column> getDataColumns();

    protected abstract DataProvider getDataProvider();

    protected abstract ActionListener getControlLinkListner();

    protected abstract Class getDataType();

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
