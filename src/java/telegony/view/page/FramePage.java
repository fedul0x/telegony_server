package telegony.view.page;

import org.apache.click.Page;
import org.apache.click.extras.control.Menu;
import org.apache.click.extras.control.MenuFactory;

/**
 * Основной шаблон страниц
 * @author Ivashin Alexey
 */
public abstract class FramePage extends Page {

    private String title;

    public FramePage(String title) {
        this.title = title;
        addModel("title", title);
        MenuFactory mf = new MenuFactory();
        Menu rootMenu = mf.getRootMenu();
        rootMenu.setTitle("Меню");
        addControl(rootMenu);
    }

    @Override
    public String getTemplate() {
        return "/frame.htm";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
