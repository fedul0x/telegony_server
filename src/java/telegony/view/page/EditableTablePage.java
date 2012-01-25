package telegony.view.page;

import java.util.LinkedList;
import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.dataprovider.PagingDataProvider;
import org.apache.click.extras.control.LinkDecorator;
import telegony.dataaccess.Repository.SORT_ORDER;
import telegony.dataaccess.RepositoryProvider;
import telegony.general.TransientObject;

/**
 * Абстрактный класс всех страниц с таблицами, имеющих ссылки для редактирования:
 * <ul>
 * <li>добавления записи;</li>
 * <li>изменения записи;</li>
 * <li>удаления записи.</li>
 * </ul>
 * @author Ivashin Alexey
 */
public abstract class EditableTablePage extends TablePage {

    /*
     * Ссылки для удаления, редактирования и вставки
     */
    private AbstractLink insertingLink = null;
    private AbstractLink editingLink = null;
    private AbstractLink deletingLink = null;

    public EditableTablePage(String title) {
        super(title);
        
    }

    public EditableTablePage(String title, String caption) {
        this(title);
        setTableCaption(caption);
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

    @Override
    protected DataProvider getDataProvider() {
        DataProvider da = new PagingDataProvider() {

            @Override
            public int size() {
                return RepositoryProvider.getRepository(getDataType()).getTotalCount().intValue();
            }

            @Override
            public List<TransientObject> getData() {
                int start = getDataTable().getFirstRow();
                int count = getDataTable().getPageSize();
                String sortColumn = getDataTable().getSortedColumn();
                SORT_ORDER order = getDataTable().isSortedAscending() ? SORT_ORDER.ASC : SORT_ORDER.DESC;
                return RepositoryProvider.getRepository(getDataType()).findRange(start, count, sortColumn, order);
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
}