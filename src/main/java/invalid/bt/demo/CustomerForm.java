package invalid.bt.demo;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import invalid.bt.demo.views.main.MainView;

@SuppressWarnings("unused")
public class CustomerForm extends FormLayout {
    private final MainView mainView;
    private CustomerService service = CustomerService.getInstance();
    private Binder<Customer> binder = new Binder<>(Customer.class);

    private TextField firstName = new TextField("Prénom");
    private TextField lastName = new TextField("Nom");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Statut");
    private DatePicker birthDate = new DatePicker("Date de naissance");
    private Button save = new Button("Enregistrer");
    private Button delete = new Button("Supprimer");

    public CustomerForm(MainView mainView) {
        this.mainView = mainView;
        status.setItems(CustomerStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(
                firstName,
                lastName,
                status,
                birthDate,
                buttons
        );

        /*
         Processes all the instance variables that are input fields
         For example, Customer#firstName is mapped to the CustomerForm#firstName input field.
         You can override automatic mapping using the @PropertyId annotation in the CustomerForm
         input fields to explicitly declare the corresponding Customer instance variables.
        */
        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());

        UI.getCurrent().addShortcutListener(event -> setCustomer(null), Key.ESCAPE);
    }

    private void delete() {
        Customer customer = binder.getBean();
        service.delete(customer);
        mainView.populateRows();
        setCustomer(null);
    }

    private void save() {
        Customer customer = binder.getBean();
        service.save(customer);
        mainView.populateRows();
        setCustomer(null);
    }

    public void setCustomer(Customer customer) {
        binder.setBean(customer); // connects the values in the customer object to the corresponding input fields of the form

        if (customer == null) {
            setVisible(false);
            return;
        }

        setVisible(true);
        firstName.focus();
    }
}
