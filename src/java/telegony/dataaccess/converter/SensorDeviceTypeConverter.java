package telegony.dataaccess.converter;

import org.apache.click.util.RequestTypeConverter;
import org.apache.velocity.exception.ParseErrorException;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.SensorDevice;

/**
 * Конвертер запроса для сущности SensorDevice
 * @author Ivashin Alexey
 */
public class SensorDeviceTypeConverter extends RequestTypeConverter {

//    private SensorDevice sensorDevice = new SensorDevice();
    
    @Override
    protected Object convertValue(Object value, Class<?> toType) {
        if (toType.equals(SensorDevice.class)) {
            Long id;
             try {
                id = Long.valueOf((String)value);
            } catch (NumberFormatException ex) {
                throw new ParseErrorException("Ошибка в запросе для сущности " + toType.toString());
            }
            return (SensorDevice) RepositoryProvider.getRepository(SensorDevice.class).findById(id);
        }
        return super.convertValue(value, toType);
    }
}
