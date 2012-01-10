package telegony.view.page;

import java.util.List;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.control.Table;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.dataprovider.PagingDataProvider;
import org.apache.click.extras.control.LinkDecorator;
import telegony.dataaccess.Repository.SORT_ORDER;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.SensorDevice;

/**
 * Страница списка устройств
 * @author Ivashin Alexey
 */
public class DeviceList extends FramePage {

    private Table deviceTable = new Table("deviceTable");
//    private PageLink editLink = new PageLink("Edit", EditCustomer.class);
    private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");
    private static final int ROW_COUNT_PER_PAGE = 30;

    public DeviceList() {
        super("Список устройств");
        addControl(deviceTable);
//        addControl(editLink);
        addControl(deleteLink);
        deviceTable.setCaption("Список сенсорных механизмов");
        deviceTable.setPageSize(ROW_COUNT_PER_PAGE);
        deviceTable.setShowBanner(true);
        deviceTable.setHoverRows(true);
        deviceTable.setSortable(true);
        deviceTable.setClass("blue1");
//        deviceTable.setClass(Table.CLASS_ITS);
        deviceTable.addColumn(new Column("sensor_device_id", "Номер"));
        deviceTable.addColumn(new Column("name", "Имя устройства"));
        deviceTable.addColumn(new Column("readingsType", "Тип показаний"));
        deviceTable.addColumn(new Column("lowLimit", "Нижний предел"));
        deviceTable.addColumn(new Column("highLimit", "Верхний предел"));
        deviceTable.addColumn(new Column("units", "Единицы измерения"));
        deviceTable.addColumn(new Column("width", "Вес показаний"));
        deviceTable.addColumn(new Column("description", "Описание"));
        deviceTable.setSortedColumn("sensor_device_id");
//        editLink.setImageSrc("/images/deviceTable-edit.png");
//        editLink.setTitle("Edit customer details");
//        editLink.setParameter("referrer", "/introduction/advanced-deviceTable.htm");
        deleteLink.setImageSrc("/images/deviceTable-delete.png");
        deleteLink.setTitle("Delete customer record");
        deleteLink.setAttribute("onclick",
                "return window.confirm('Are you sure you want to delete this record?');");

        Column column = new Column("Action");
        column.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[]{/*editLink,*/deleteLink};
        column.setDecorator(new LinkDecorator(deviceTable, links, "id"));
        column.setSortable(false);
        deviceTable.addColumn(column);

        deviceTable.setDataProvider(new PagingDataProvider() {

            @Override
            public int size() {
                return RepositoryProvider.getRepository(SensorDevice.class).getTotalCount().intValue();
            }

            public List<SensorDevice> getData() {
                int start = deviceTable.getFirstRow();
                int count = deviceTable.getPageSize();
                String sortColumn = deviceTable.getSortedColumn();
                SORT_ORDER order = deviceTable.isSortedAscending() ? SORT_ORDER.ASC : SORT_ORDER.DESC;
                return RepositoryProvider.getRepository(SensorDevice.class).findRange(start, count, sortColumn, order);
            }
        });

        // Below we setup the deviceTable to preserve it's state (sorting and paging)
        // while editing customers

        deviceTable.getControlLink().setActionListener(new ActionListener() {

            public boolean onAction(Control source) {
                // Save Table sort and paging state between requests.
                // NOTE: we set the listener on the deviceTable's Link control which is invoked
                // when the Link is clicked, such as when paging or sorting.
                // This ensures the deviceTable state is only saved when the state changes, and
                // cuts down on unnecessary session replication in a cluster environment.
                deviceTable.saveState(getContext());
                return true;
            }
        });

        // Restore the deviceTable sort and paging state from the session between requests
        deviceTable.restoreState(getContext());

    }

    public boolean onDeleteClick() {
        Long id = deleteLink.getValueLong();
        RepositoryProvider.getRepository(SensorDevice.class).removeById(id);
        return true;
    }
}
