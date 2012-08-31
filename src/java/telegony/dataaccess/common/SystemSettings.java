package telegony.dataaccess.common;

/**
 * Хранение настроек системы управления
 * @author Ivashin Alexey
 */
public class SystemSettings extends TransientObject {
    /*
     * Длина периода опроса датчиков, с
     */

    Integer surveyPeriod = new Integer(1);
    /*
     * Период принудительной выдачи управляющего воздействия, с
     */
    Integer impactPeriod = new Integer(1);

    public Integer getImpactPeriod() {
        return impactPeriod;
    }

    public void setImpactPeriod(Integer impactPeriod) {
        this.impactPeriod = impactPeriod;
    }

    public Integer getSurveyPeriod() {
        return surveyPeriod;
    }

    public void setSurveyPeriod(Integer surveyPeriod) {
        this.surveyPeriod = surveyPeriod;
    }
}
