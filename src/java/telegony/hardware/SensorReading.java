package telegony.hardware;

import java.util.Date;
import telegony.general.TransientObject;

/**
 * Показание прибора в некоторый момент времени
 * @author Ivashin Alexey
 */
public class SensorReading extends TransientObject {
    
    /*
     * Момент времени, в который данные были получены
     */
    private Date timeTick;
    
    /*
     * Устройство, с которого получены данные показания
     */
    private SensorDevice device;
    
    /*
     * Значение, полученное от сенсора
     */
    private Double value;

    public SensorDevice getDevice() {
        return device;
    }

    public void setDevice(SensorDevice device) {
        this.device = device;
    }

    public Date getTimeTick() {
        return timeTick;
    }

    public void setTimeTick(Date timeTick) {
        this.timeTick = timeTick;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
