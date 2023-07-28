package com.diver.view;

import com.diver.api.client.DiverUserClient;
import com.diver.api.config.UserContext;;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private Image gif = new Image("https://www.gify.net/data/media/179/nurkowanie-ruchomy-obrazek-0057.gif", "Not found");

    public HomeView(DiverUserClient diverUserClient, UserContext userContext) {
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H2 welcomeHeading = new H2("Welcome " + diverUserClient.getUser(userContext.getUserId()).getName() + "!");
        H3 welcomeInfo = new H3("Let's enjoy the diving with Diver's Application :-)");

        add(
                welcomeHeading,
                welcomeInfo,
                gif
        );
    }
}
