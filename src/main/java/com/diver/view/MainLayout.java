package com.diver.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("main-layout")
public class MainLayout extends AppLayout {
    public MainLayout() {
        H1 title = new H1("DIVER'S APPLICATION");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();

        addToNavbar(title, tabs);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Home"), createTab("Your account"),
                createTab("Diver's log"), createTab("Equipment"), createTab("Diving bases"), createTab("Log out"));
        return tabs;
    }

    private Tab createTab(String viewName) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        for (int i = 0; i < 5; i++) {
            if (viewName.equals("Home")) {
                link.setRoute(HomeView.class);
            } else if (viewName.equals("Your account")) {
                link.setRoute(AccountView.class);
            } else if (viewName.equals("Equipment")) {
                link.setRoute(EquipmentView.class);
            } else if (viewName.equals("Diver's log")) {
                link.setRoute(DiversLogView.class);
            } else if (viewName.equals("Diving bases")) {
                link.setRoute(DivingBaseView.class);
            } else if (viewName.equals("Log out")) {
                link.setRoute(MainView.class);
            }
        }
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
