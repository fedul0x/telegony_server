package telegony.hardware;

import telegony.general.TransientEnum;

/**
 * Состояние устройства
 * @author Ivashin Alexey
 */
public class ActivityState extends TransientEnum {

    public final static ActivityState WORKING_STATE = new ActivityState(1L, "Рабочее состояние");
    public final static ActivityState DISABLED_STATE = new ActivityState(2L, "Отключено");
    public final static ActivityState OFFLINE_STATE = new ActivityState(3L, "Нет отклика");

    public ActivityState(Long id, String description) {
        super(id, description);
    }
}
