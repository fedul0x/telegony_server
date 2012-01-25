package telegony.hardware;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import telegony.general.TransientEnum;

/**
 * Тип сенсорного устройства
 * @author Ivashin Alexey
 */
public class SensorDeviceType extends TransientEnum {

    public final static SensorDeviceType TEMPERATUTE_SENSOR_DEVICE_ON_THE_TUBE = new SensorDeviceType(1L, "Датчик температуры на трубе");
    public final static SensorDeviceType TEMPERATUTE_SENSOR_DEVICE_EXTERNAL = new SensorDeviceType(2L, "Датчик температуры внешний");
    public final static SensorDeviceType TEMPERATUTE_SENSOR_DEVICE_INTERNAL = new SensorDeviceType(3L, "Датчик температуры в помещении");
    public final static SensorDeviceType FLOW_SENSOR_DEVICE_ON_THE_TUBE = new SensorDeviceType(4L, "Датчик расхода на трубе");
    public static final List<SensorDeviceType> ALL_TYPES = new LinkedList<SensorDeviceType>();

    static {
        ALL_TYPES.addAll(Arrays.asList(TEMPERATUTE_SENSOR_DEVICE_ON_THE_TUBE, TEMPERATUTE_SENSOR_DEVICE_EXTERNAL, TEMPERATUTE_SENSOR_DEVICE_INTERNAL, FLOW_SENSOR_DEVICE_ON_THE_TUBE));
    }

    public SensorDeviceType() {
    }

    private SensorDeviceType(Long id, String description) {
        super(id, description);
    }

    public static SensorDeviceType getById(Long id) {
        for (SensorDeviceType type : ALL_TYPES) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
