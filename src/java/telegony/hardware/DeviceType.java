package telegony.hardware;

import telegony.general.TransientEnum;

/**
 * Тип устройства
 * @author Ivashin Alexey
 */
class DeviceType extends TransientEnum {

    public final static DeviceType SENSOR_DEVICE = new DeviceType(1L, "Сенсорный механизм");
    public final static DeviceType IMPACT_DEVICE = new DeviceType(2L, "Исполнительный механизм");
    public final static DeviceType SENSOR_IMPACT_DEVICE = new DeviceType(3L, "Сенсорный и исполнительный механизм");

    private DeviceType(Long id, String description) {
        super(id, description);
    }
}
