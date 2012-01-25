package telegony.hardware;

/**
 * Класс "Сенсорный механизм"
 * @author Ivashin Alexey
 */
public class SensorDevice extends Device {

    /*
     * Единицы измерения
     */
    private String units;
    /*
     * Нижний и верхний пределы измерения
     */
    private Double lowLimit, highLimit;
    /*
     * Тип устройства в терминологии зоны управления
     */
    private SensorDeviceType deviceType;
    /*
     * Коэффициент веса
     */
    private Double width;

    public SensorDevice() {
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

    public SensorDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(SensorDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }
}
