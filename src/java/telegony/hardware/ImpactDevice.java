package telegony.hardware;

/**
 * Класс "Исполнительный механизм"
 * @author Ivashin Alexey
 */
public class ImpactDevice  extends Device{
    
    /*
     * Единицы измерения регулируемой величины
     */
    private String units;
    /*
     * Нижний и верхний пределы регулирования
     */
    private Double lowLimit, highLimit;
    /*
     * Тип устройства в терминологии зоны управления
     */
    private ImpactDeviceType deviceType;
    
    public ImpactDevice() {
    }

    public ImpactDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(ImpactDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Double getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(Double highLimit) {
        this.highLimit = highLimit;
    }

    public Double getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(Double lowLimit) {
        this.lowLimit = lowLimit;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
    
    
}
