package telegony.hardware;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import telegony.general.TransientEnum;

/**
 * Тип показаний прибора
 * @author Ivashin Alexey
 */
public class SensorReadingsType extends TransientEnum {

    public final static SensorReadingsType TEMPERATURE = new SensorReadingsType(1L, "Температура");
    public final static SensorReadingsType FLOWRATE = new SensorReadingsType(2L, "Расход");
    public static final List<SensorReadingsType> ALL_STAUSES = new LinkedList<SensorReadingsType>();
    static {
        ALL_STAUSES.addAll(Arrays.asList(TEMPERATURE, FLOWRATE));
    }

    public SensorReadingsType() {
    }

    public SensorReadingsType(Long id, String description) {
        super(id, description);
    }
    public static SensorReadingsType getById(Long id) {
        for (SensorReadingsType state : ALL_STAUSES) {
            if (state.getId().equals(id)) {
                return state;
            }
        }
        return null;
    }
}
