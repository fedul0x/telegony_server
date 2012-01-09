package telegony.security;

import java.util.Map;
import telegony.general.Zone;

/**
 * Пользователь системы
 * @author Ivashin Alexey
 */
public class User {
    /*
     * Имя пользователя
     */
    private String login;
    /*
     * Хэш пароля
     */
    private String passwordHash;
    /*
     * Права доступа для каждой из зон
     */
    private Map<Zone, PermissionType> permissions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Map<Zone, PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<Zone, PermissionType> permissions) {
        this.permissions = permissions;
    }
    

}
