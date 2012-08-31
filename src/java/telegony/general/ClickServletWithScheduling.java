package telegony.general;

import java.io.Serializable;
import org.apache.click.ClickServlet;
import telegony.dataaccess.RepositoryProvider;
import telegony.dataaccess.common.SystemSettings;

/**
 *
 * @author Ivashin Alexey
 */
public class ClickServletWithScheduling extends ClickServlet {

    public ClickServletWithScheduling() {

        super();

        if (RepositoryProvider.getRepository(SystemSettings.class).findAll().isEmpty()) {
            SystemSettings ss = new SystemSettings();
            ss.setId(new Long(0));
            ss.setSurveyPeriod(60);
            ss.setImpactPeriod(60);
            RepositoryProvider.getRepository(SystemSettings.class).save(ss);
        }

    }
}
