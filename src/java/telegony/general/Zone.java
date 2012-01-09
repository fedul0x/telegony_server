package telegony.general;

/**
 * Класс, описывающий зоны управления и мониторига, позволяющий
 * <ul>
 * <li></li>
 * </ul>
 * @author Ivashin Alexey
 */
public class Zone extends TransientObject{
    private ZoneType zoneType;
    
    /*
     * Признак произведения в зоне управления
     */

    private Boolean isManaged;
    /*
     * Признак произведения в зоне мониторинга
     */
    private Boolean isMonitored;
    /*
     * Описание зоны
     */
    private String description;

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
