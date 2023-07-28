package com.diver.view;

import com.diver.api.client.DiverUserClient;
import com.diver.api.config.UserContext;
import com.diver.dto.UserDtoToAuthorised;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("main-view")
public class MainView extends VerticalLayout {

    private final DiverUserClient diverUserClient;

    private UserContext userContext;
    private TextField login = new TextField("Login");
    private PasswordField password = new PasswordField("Password");
    private Button logIn = new Button("Log in");
    private Button signUp = new Button("Sign up");


    public MainView(DiverUserClient diverUserClient, UserContext userContext) {
        this.diverUserClient = diverUserClient;
        this.userContext = userContext;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                new H1("DIVER'S APPLICATION"),
                new H2("Start your diving adventure!"),
                login,
                password,
                logIn,
                new H4("Don't have an account?"),
                signUp
        );

        logIn.addClickListener(clickEvent -> {
            if (diverUserClient.isAuthorised(UserDtoToAuthorised.builder()
                    .login(login.getValue())
                    .password(password.getValue())
                    .build())) {

                this.userContext.setUserId(diverUserClient.getUserIdByLoginAndPassword(UserDtoToAuthorised.builder()
                        .login(login.getValue())
                        .password(password.getValue())
                        .build()));

                UI.getCurrent().navigate("home");
            } else {
                Notification.show("Wrong login or password!");
            }
        });

        signUp.addClickListener(clickEvent ->
                UI.getCurrent().navigate("registration"));
    }
}
