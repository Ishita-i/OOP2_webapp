
package com.vaadin.tutorial.crm.ui.views.list;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();


    public LoginView() {
        addClassName("login-view");
        // setSizeFull();
        //setAlignItems(Alignment.AUTO);
        //setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");


        Image img = new Image("images/medical.jpg", "Medical");
        img.setHeight("180px");
        img.setWidthFull();
        add(img);

        add(new H1("HUISARTSKLINIEK - Drs. Harpal R."));
        add(new Paragraph("SPREEKUREN: MA. - ZA. OCHTEND 9.00-11.00 UUR"));
        add(new Paragraph(" MA. - ZA. MIDDAG 17.00-19.00 UUR"));
        add(new Paragraph("ZONDAG OP AFSPRAAK"));

        add("CONTACT: +597 8760998");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");


        this.getElement().getStyle().set("background-image", "url('images/3.jpg')");

        //add(new H1(" HUISARTSKLINIEK - Drs......"));

        add(new H1(login));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}

