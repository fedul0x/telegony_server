package telegony.hardware;

import telegony.general.TransientEnum;

/**
 * Тип показаний прибора
 * @author Ivashin Alexey
 */
public class SensorReadingsType extends TransientEnum {

    public final static SensorReadingsType TEMPERATURE = new SensorReadingsType(1L, "Температура");
    public final static SensorReadingsType FLOWRATE = new SensorReadingsType(2L, "Расход");

    public SensorReadingsType() {
    }

    public SensorReadingsType(Long id, String description) {
        super(id, description);
    }
}
