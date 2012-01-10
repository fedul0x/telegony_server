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
    Double lowLimit, highLimit;
    /*
     * Тип снимаемых показаний
     */
    SensorReadingsType readingsType;
    /*
     * Коэффициент веса
     */
    Double width;

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

    public SensorReadingsType getReadingsType() {
        return readingsType;
    }

    public void setReadingsType(SensorReadingsType readingsType) {
        this.readingsType = readingsType;
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
