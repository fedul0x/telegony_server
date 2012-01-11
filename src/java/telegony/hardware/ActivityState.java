package telegony.hardware;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import telegony.general.TransientEnum;

/**
 * Состояние устройства
 * @author Ivashin Alexey
 */
public class ActivityState extends TransientEnum {

    public final static ActivityState WORKING_STATE = new ActivityState(1L, "Рабочее состояние");
    public final static ActivityState DISABLED_STATE = new ActivityState(2L, "Отключено");
    public final static ActivityState OFFLINE_STATE = new ActivityState(3L, "Нет отклика");
    public static final List<ActivityState> ALL_STAUSES = new LinkedList<ActivityState>();

    static {
        ALL_STAUSES.addAll(Arrays.asList(WORKING_STATE, DISABLED_STATE, OFFLINE_STATE));
    }

    @Deprecated
    public ActivityState() {
    }

    public ActivityState(Long id, String description) {
        super(id, description);
    }

    public static ActivityState getById(Long id) {
        for (ActivityState state : ALL_STAUSES) {
            if (state.getId().equals(id)) {
                return state;
            }
        }
        return null;
    }
}
