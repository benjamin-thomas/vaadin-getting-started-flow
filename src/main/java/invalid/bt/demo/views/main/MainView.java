package invalid.bt.demo.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import invalid.bt.demo.Customer;
import invalid.bt.demo.CustomerForm;
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
    private CustomerForm form = new CustomerForm(this);

    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();

    public MainView() {
        // Setup
        setColumns();
        populateRows();
        configureSearch();

        Button addBtn = new Button("Ajouter un client");
        addBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        // Render
        setSizeFull();
        add(grid);
//        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        VerticalLayout mainContent = new VerticalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBtn);
        add(toolbar, mainContent);

        form.setCustomer(null);
//        grid.addItemClickListener(event -> form.setCustomer(event.getItem())); // my idea
        // Given by the docs
        grid.asSingleSelect().addValueChangeListener(evt -> {
            form.setCustomer(grid.asSingleSelect().getValue());
        });

    }

    private void configureSearch() {
        filterText.setPlaceholder("Rechercher par nom...");
        filterText.setClearButtonVisible(true);

        // ValueChangeMode.EAGER ensures that change events are fired immediately when the user types.
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> populateRows());
    }

    private void setColumns() {
        grid.addColumn(Customer::getFirstName);
        grid.addColumn(Customer::getLastName);
        grid.addColumn(Customer::getStatus);
    }

    public void populateRows() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
