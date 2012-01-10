package telegony.view.page;

import org.apache.click.Page;

/**
 * Основной шаблон страниц
 * @author Ivashin Alexey
 */
public class FramePage extends Page {
    private String title;

    public FramePage(String title) {
        this.title = title;
        addModel("title", title);
    }

    
    @Override
    public String getTemplate() {
        return "/frame.htm";
    }
}
