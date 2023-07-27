package com.diver;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("main-view")
public class MainView extends VerticalLayout {
    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                new H1("DIVER'S APPLICATION"),
                new H2("Start your diving adventure!"),
                new TextField("Login"),
                new PasswordField("Password"),
                new Button("Log in", event ->
                        UI.getCurrent().navigate("home")),
                new H4("Don't have an account?"),
                new Button("Sign up", event ->
                        UI.getCurrent().navigate("registration"))
                );


        //VerticalLayout layout = new VerticalLayout();

/*

            layout.add(new Button("LogIn"));
            layout.add(new Button("SignIn"));

            this.setClassName("basic-layouts-example");

            this.add(layout);*/
    }

}
