package telegony.view.page;

import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.control.Table;
import org.apache.click.extras.control.LinkDecorator;

/**
 * Страница списка устройств
 * @author Ivashin Alexey
 */
public class DeviceList extends FramePage {

    private Table table = new Table("table");
//    private PageLink editLink = new PageLink("Edit", EditCustomer.class);
    private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");

    public DeviceList() {
        super("Список устройств");
        addControl(table);
//        addControl(editLink);
        addControl(deleteLink);

        table.setClass(Table.CLASS_ITS);
        table.setPageSize(10);
        table.setShowBanner(true);
        table.setSortable(true);

        table.addColumn(new Column("id"));

        table.addColumn(new Column("name"));

        Column column = new Column("email");
        column.setAutolink(true);
        column.setTitleProperty("name");
        table.addColumn(column);

        table.addColumn(new Column("investments"));

//        editLink.setImageSrc("/images/table-edit.png");
//        editLink.setTitle("Edit customer details");
//        editLink.setParameter("referrer", "/introduction/advanced-table.htm");

        deleteLink.setImageSrc("/images/table-delete.png");
        deleteLink.setTitle("Delete customer record");
        deleteLink.setAttribute("onclick",
                "return window.confirm('Are you sure you want to delete this record?');");

        column = new Column("Action");
        column.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[]{/*editLink,*/ deleteLink};
        column.setDecorator(new LinkDecorator(table, links, "id"));
        column.setSortable(false);
        table.addColumn(column);

        // Table rowList will be populated through a DataProvider which loads
        // data on demand.
        table.setDataProvider(new DataProvider() {

            public List getData() {
                return getCustomerService().getCustomers();
            }
        });

        // Below we setup the table to preserve it's state (sorting and paging)
        // while editing customers

        table.getControlLink().setActionListener(new ActionListener() {

            public boolean onAction(Control source) {
                // Save Table sort and paging state between requests.
                // NOTE: we set the listener on the table's Link control which is invoked
                // when the Link is clicked, such as when paging or sorting.
                // This ensures the table state is only saved when the state changes, and
                // cuts down on unnecessary session replication in a cluster environment.
                table.saveState(getContext());
                return true;
            }
        });

        // Restore the table sort and paging state from the session between requests
        table.restoreState(getContext());

    }

    public boolean onDeleteClick() {
        Integer id = deleteLink.getValueInteger();
        getCustomerService().deleteCustomer(id);
        return true;
    }
}
