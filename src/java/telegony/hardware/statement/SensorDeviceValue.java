package telegony.hardware.statement;

import telegony.dataaccess.common.TransientObject;
import telegony.hardware.SensorDevice;

/**
 * Сущность хранения показаний датчика
 * @author Ivashin Alexey
 */
public class SensorDeviceValue extends TransientObject {

    private SensorDevice sensorDevice;
    private Double value;

    public SensorDevice getSensorDevice() {
        return sensorDevice;
    }

    public void setSensorDevice(SensorDevice sensorDevice) {
        this.sensorDevice = sensorDevice;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
