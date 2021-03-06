package com.vaadin.tutorial.crm.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.tutorial.crm.ui.views.dasboard.DashboardView;
import com.vaadin.tutorial.crm.ui.views.list.ListView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
        this.getElement().getStyle().set("background-image", "url('images/3.jpg')");
    }

    private void createHeader() {
        H1 logo = new H1("PATIENTENBESTAND");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        header.expand(logo);

        addToNavbar(header);

    }

    private void createDrawer() {

        RouterLink listLink = new RouterLink("List", ListView.class);
        addToDrawer(new VerticalLayout(listLink, new RouterLink("Dashboard", DashboardView.class)));
        listLink.setHighlightCondition(HighlightConditions.sameLocation());


    }
}
