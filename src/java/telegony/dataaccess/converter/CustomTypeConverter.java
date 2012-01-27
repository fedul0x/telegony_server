package telegony.dataaccess.converter;

import org.apache.click.util.RequestTypeConverter;
import org.apache.velocity.exception.ParseErrorException;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.ImpactDevice;
import telegony.hardware.SensorDevice;

/**
 * Конвертер запроса для сущности SensorDevice
 * @author Ivashin Alexey
 */
public class CustomTypeConverter extends RequestTypeConverter {

    @Override
    protected Object convertValue(Object value, Class<?> toType) {
        if (toType.equals(SensorDevice.class)) {
            Long id;
            try {
                id = Long.valueOf((String) value);
            } catch (NumberFormatException ex) {
                throw new ParseErrorException("Ошибка в запросе для сущности " + toType.toString());
            }
            return (SensorDevice) RepositoryProvider.getRepository(SensorDevice.class).findById(id);
        }
        if (toType.equals(ImpactDevice.class)) {
            Long id;
            try {
                id = Long.valueOf((String) value);
            } catch (NumberFormatException ex) {
                throw new ParseErrorException("Ошибка в запросе для сущности " + toType.toString());
            }
            return (ImpactDevice) RepositoryProvider.getRepository(ImpactDevice.class).findById(id);
        }
        return super.convertValue(value, toType);
    }
}
