package telegony.dataaccess.common;

import java.util.Set;
import telegony.hardware.SensorDevice;

/**
 * Зоны включают в себя контуры управления и устройства мониторига
 * @author Ivashin Alexey
 */
public class Zone extends TransientObject {

//    TODO Ввести стаут активности для зоны?
    /*
     * Имя зоны управления
     */
    private String name;
    /*
     * Зоны управления
     */
    private Set<ControlContour> controlContours;
    /*
     * Устройства мониторинга
     */
    private Set<SensorDevice> sensorDevices;

    public Zone() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ControlContour> getControlContours() {
        return controlContours;
    }

    public void setControlContours(Set<ControlContour> controlContours) {
        this.controlContours = controlContours;
    }

    public Set<SensorDevice> getSensorDevices() {
        return sensorDevices;
    }

    public void setSensorDevices(Set<SensorDevice> sensorDevices) {
        this.sensorDevices = sensorDevices;
    }
}
