package telegony.dataaccess.common;

import java.io.Serializable;
import telegony.hardware.ImpactDevice;
import telegony.hardware.SensorDevice;

/**
 * Контур управления
 * @author Ivashin ALexey
 */
public class ControlContour extends TransientObject {

    /*
     * Имя контура управления
     */
    private String name;
    /*
     * Датчик расхода
     */
    private SensorDevice flowSensor;
    /*
     * Датчик температуры на трубе на входе (перед радиатором)
     */
    private SensorDevice temperatureInputSensor;
    /*
     * Датчик температуры на трубе на выходе (после радиатора)
     */
    private SensorDevice temperatureOutputSensor;
    /*
     * Датчик температуры внутри помещения
     */
    private SensorDevice temperatureInsideSensor;
    /*
     * Датчик температуры снаружи помещения
     */
    private SensorDevice temperatureOutsideSensor;
    /*
     * Исполнительный механизм регулирования расхода через радиатор
     */
    private ImpactDevice flowRegulator;

    public ControlContour() {
    }

    public ControlContour(Serializable id, String description) {
        super(id, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDevice getFlowSensor() {
        return flowSensor;
    }

    public void setFlowSensor(SensorDevice flowSensor) {
        this.flowSensor = flowSensor;
    }

    public SensorDevice getTemperatureInputSensor() {
        return temperatureInputSensor;
    }

    public void setTemperatureInputSensor(SensorDevice temperatureInputSensor) {
        this.temperatureInputSensor = temperatureInputSensor;
    }

    public SensorDevice getTemperatureInsideSensor() {
        return temperatureInsideSensor;
    }

    public void setTemperatureInsideSensor(SensorDevice temperatureInsideSensor) {
        this.temperatureInsideSensor = temperatureInsideSensor;
    }

    public SensorDevice getTemperatureOutputSensor() {
        return temperatureOutputSensor;
    }

    public void setTemperatureOutputSensor(SensorDevice temperatureOutputSensor) {
        this.temperatureOutputSensor = temperatureOutputSensor;
    }

    public SensorDevice getTemperatureOutsideSensor() {
        return temperatureOutsideSensor;
    }

    public void setTemperatureOutsideSensor(SensorDevice temperatureOutsideSensor) {
        this.temperatureOutsideSensor = temperatureOutsideSensor;
    }

    public ImpactDevice getFlowRegulator() {
        return flowRegulator;
    }

    public void setFlowRegulator(ImpactDevice flowRegulator) {
        this.flowRegulator = flowRegulator;
    }
}
