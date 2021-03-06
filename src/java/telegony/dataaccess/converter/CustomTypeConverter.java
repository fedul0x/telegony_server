package telegony.dataaccess.converter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.click.util.RequestTypeConverter;
import org.apache.velocity.exception.ParseErrorException;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.ControlContour;
import telegony.dataaccess.common.TransientObject;
import telegony.dataaccess.common.Zone;
import telegony.hardware.ImpactDevice;
import telegony.hardware.SensorDevice;

/**
 * Конвертер запроса для сущности SensorDevice
 * @author Ivashin Alexey
 */
public class CustomTypeConverter extends RequestTypeConverter {

    private static List<Class> avalibleClasses = new LinkedList<Class>();

    static {
        avalibleClasses.addAll(Arrays.asList(SensorDevice.class, ImpactDevice.class, ControlContour.class, Zone.class));
    }

    @Override
    protected Object convertValue(Object value, Class<?> toType) {
        for (Class type : avalibleClasses) {
            Long id;
            if (toType.equals(type)) {
                try {
                    id = Long.valueOf((String) value);
                } catch (NumberFormatException ex) {
                    throw new ParseErrorException("Ошибка в запросе для сущности " + toType.toString());
                }
                return RepositoryProvider.getRepository(type).findById(id);
            }
        }
        return super.convertValue(value, toType);
    }
}
