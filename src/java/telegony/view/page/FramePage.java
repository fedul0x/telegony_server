package telegony.view.page;

import org.apache.click.Page;

/**
 * Основной шаблон страниц
 * @author Ivashin Alexey
 */
public class FramePage extends Page {

    @Override
    public String getTemplate() {
        return "/frame.htm";
    }
}
