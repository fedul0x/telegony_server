package telegony.hardware;

import telegony.general.TransientEnum;

/**
 * Тип показаний прибора
 * @author Ivashin Alexey
 */
public class ReadingsType extends TransientEnum {

    public final static ReadingsType TEMPERATURE = new ReadingsType(1L, "Температура");
    public final static ReadingsType FLOWRATE = new ReadingsType(2L, "Расход");

    public ReadingsType(Long id, String description) {
        super(id, description);
    }
}
