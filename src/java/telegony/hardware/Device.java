package telegony.hardware;

import telegony.general.TransientObject;

/**
 * Абстрактрое устройство системы
 * @author Ivashin Alexey
 */
public abstract class Device extends TransientObject {
    /*
     * Имя устройства (ip-адрес:порт)
     */

    private String name;
    /*
     * Зона действия устройства
     */
//    private Zone zone;
    /*
     * Статус устройства
     */
    private ActivityState state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }

//    public Zone getZone() {
//        return zone;
//    }
//
//    public void setZone(Zone zone) {
//        this.zone = zone;
//    }
}
