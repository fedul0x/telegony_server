package telegony.dataaccess.converter;

import org.apache.click.util.RequestTypeConverter;
import org.apache.velocity.exception.ParseErrorException;
import telegony.dataaccess.RepositoryProvider;
import telegony.hardware.ImpactDevice;

/**
 * Конвертер запроса для сущности ImpactDevice
 * @author Ivashin Alexey
 */
public class ImpactDeviceTypeConverter extends RequestTypeConverter {
//    TODO Конвертеры подобные этому можно свернуть в один большой

    @Override
    protected Object convertValue(Object value, Class<?> toType) {
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
