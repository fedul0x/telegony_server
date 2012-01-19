package telegony.hardware;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import telegony.general.TransientEnum;

/**
 * Тип исполнительного механизма
 * @author Ivashin Alexey
 */
class ImpactDeviceType extends TransientEnum {

    public final static ImpactDeviceType FLOW_IMPACT_DEVICE_ON_THE_TUBE = new ImpactDeviceType(1L, "Исполнительный механизм регулирования расхода на трубе");
    public static final List<ImpactDeviceType> ALL_TYPES = new LinkedList<ImpactDeviceType>();

    static {
        ALL_TYPES.addAll(Arrays.asList(FLOW_IMPACT_DEVICE_ON_THE_TUBE));
    }

    public ImpactDeviceType() {
    }

    private ImpactDeviceType(Long id, String description) {
        super(id, description);
    }

    public static ImpactDeviceType getById(Long id) {
        for (ImpactDeviceType type : ALL_TYPES) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
