package telegony.security;

import telegony.dataaccess.common.TransientEnum;

/**
 * Список прав польователя
 * @author Ivashin Alexey
 */
public class PermissionType extends TransientEnum {

    public final static PermissionType READ = new PermissionType(1L, "Чтение");
    public final static PermissionType MODIFY = new PermissionType(2L, "Изменение");

    private PermissionType(Long id, String description) {
        super(id, description);
    }
}
