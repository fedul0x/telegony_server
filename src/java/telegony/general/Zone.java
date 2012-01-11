package telegony.general;

/**
 * Класс, описывающий зоны управления и мониторига, позволяющий
 * <ul>
 * <li></li>
 * </ul>
 * @author Ivashin Alexey
 */
public class Zone extends TransientObject {

    private ZoneType zoneType;
    /*
     * Признак проведения управления в зоне
     */
    private Boolean isManaged;
    /*
     * Признак проведения мониторинга в зоне
     */
    private Boolean isMonitored;

    public Zone() {
    }

    public Boolean getIsManaged() {
        return isManaged;
    }

    public void setIsManaged(Boolean isManaged) {
        this.isManaged = isManaged;
    }

    public Boolean getIsMonitored() {
        return isMonitored;
    }

    public void setIsMonitored(Boolean isMonitored) {
        this.isMonitored = isMonitored;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }
}
