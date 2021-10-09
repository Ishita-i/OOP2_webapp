package com.vaadin.tutorial.crm.ui.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Patient;
import com.vaadin.tutorial.crm.backend.service.CompanyService;
import com.vaadin.tutorial.crm.backend.service.PatientService;
import com.vaadin.tutorial.crm.ui.MainLayout;


/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route(value = "", layout = MainLayout.class)
@PageTitle("Patients | Patientenbestand")
//@CssImport("./styles/shared-styles.css")

public class ListView extends VerticalLayout {

    private final PatientForm form;
    Grid<Patient> grid = new Grid<>(Patient.class);
    com.vaadin.flow.component.textfield.TextField filterText = new com.vaadin.flow.component.textfield.TextField();

    private PatientService patientService;

    public ListView(PatientService patientService,
                    CompanyService companyService) {
        this.patientService = patientService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        getToolBar();

        form = new PatientForm(companyService.findAll());
        form.addListener(PatientForm.SaveEvent.class, this::savePatient);
        form.addListener(PatientForm.DeleteEvent.class, this::deletePatient);
        form.addListener(PatientForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();

    }

    private void deletePatient(PatientForm.DeleteEvent event) {
        patientService.delete(event.getPatient());
        updateList();
        closeEditor();
    }

    private void savePatient(PatientForm.SaveEvent event) {
        patientService.save(event.getPatient());
        updateList();
        closeEditor();
    }

    private Component getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add new person", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Patient());
    }


    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "phonenumber", "address", "lastVisited", "issues", "dateOfBirth", "assurancenr");//, "email", "status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company Assurance");

        grid.getColumns().forEach(col -> col.setWidth("30px"));

        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private void editContact(Patient patient) {
        if (patient == null) {
            closeEditor();
        } else {
            form.setPatient(patient);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPatient(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(patientService.findAll(filterText.getValue()));
    }
}
