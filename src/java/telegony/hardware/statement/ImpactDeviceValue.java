package telegony.hardware.statement;

import telegony.general.TransientObject;
import telegony.hardware.ImpactDevice;

/**
 * Сущность хранений упраляющего воздействия
 * @author Ivashin Alexey
 */
public class ImpactDeviceValue extends TransientObject {

    private ImpactDevice impactDevice;
    private Double value;

    public ImpactDevice getImpactDevice() {
        return impactDevice;
    }

    public void setImpactDevice(ImpactDevice impactDevice) {
        this.impactDevice = impactDevice;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
