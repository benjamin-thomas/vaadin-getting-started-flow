package invalid.bt.demo.views.main;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import invalid.bt.demo.Customer;
import invalid.bt.demo.CustomerService;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route("")
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "Demo", shortName = "Demo", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
public class MainView extends VerticalLayout {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);

    public MainView() {
    }

}
