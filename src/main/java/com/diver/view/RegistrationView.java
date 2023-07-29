package com.diver.view;

import com.diver.api.client.DiverUserClient;
import com.diver.dto.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "/registration")
public class RegistrationView extends VerticalLayout {
    private final DiverUserClient diverUserClient;
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField localization = new TextField("Localization");

    private EmailField email = new EmailField("Email");
    private TextField login = new TextField("Login");
    private PasswordField password = new PasswordField("Password");
    private Button register = new Button("Register");
    DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
    DatePicker singleFormatDatePicker = new DatePicker("Date of birth:");


    public RegistrationView(DiverUserClient diverUserClient) {
        this.diverUserClient = diverUserClient;
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        singleFormatDatePicker.setI18n(singleFormatI18n);
        setPadding(false);

        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(
                new H2("REGISTRATION"),
                name,
                surname,
                singleFormatDatePicker,
                localization,
                email,
                login,
                password,
                register
        );

        register.addClickListener(event -> register());
    }

    private void register() {
        if (!diverUserClient.isLoginAvailable(login.getValue())) {
            Notification.show("Invalid login! The given login already exist, choose another one!");
        } else {
            createUser(UserDto.builder()
                    .name(name.getValue())
                    .surname(surname.getValue())
                    .dateOfBirth(singleFormatDatePicker.getValue())
                    .localization(localization.getValue())
                    .email(email.getValue())
                    .login(login.getValue())
                    .password(password.getValue())
                    .build());
            Notification.show("Registration completed successfully! Log in to your account.");
            UI.getCurrent().navigate("main-view");
        }
    }

    public void createUser(UserDto userDto) {
        diverUserClient.createNewUser(userDto);
    }

}
