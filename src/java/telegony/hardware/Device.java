package telegony.hardware;

import org.apache.jasper.tagplugins.jstl.core.Url;
import telegony.general.TransientObject;
import telegony.general.Zone;

/**
 * Абстрактрое устройство системы
 * @author Ivashin Alexey
 */
public abstract class Device extends TransientObject {
    /*
     * Имя устройства
     */

    private String name;
    /*
     * Зона действия устройства
     */
    private Zone zone;
    /*
     * Статус устройства
     */
    private ActivityState state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
