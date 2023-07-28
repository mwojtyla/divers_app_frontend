package com.diver.view;

import com.diver.api.client.DiverUserClient;
import com.diver.api.config.UserContext;
import com.diver.dto.UserDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "/account", layout = MainLayout.class)
public class AccountView extends VerticalLayout {

    private final DiverUserClient diverUserClient;
    private final UserContext userContext;
    private Binder<UserDto> binderUser = new Binder<>(UserDto.class);

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField localization = new TextField("Localization");
    private EmailField email = new EmailField("Email");
    private TextField login = new TextField("Login");
    private PasswordField password = new PasswordField("Password");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");


    public AccountView(DiverUserClient diverUserClient, UserContext userContext) {
        this.diverUserClient = diverUserClient;
        this.userContext = userContext;

        binderUser.bindInstanceFields(this);

        setAlignItems(FlexComponent.Alignment.CENTER);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        DatePicker singleFormatDatePicker = new DatePicker("Date of birth:");
        singleFormatDatePicker.setI18n(singleFormatI18n);
        setPadding(false);


        add(
                new H3("YOUR ACCOUNT"),
                name,
                surname,
                singleFormatDatePicker,
                localization,
                email,
                login,
                password,
                createButtonLayout()

        );

        UserDto userDto = diverUserClient.getUser(this.userContext.getUserId());
        binderUser.setBean(userDto);
        singleFormatDatePicker.setValue(userDto.getDateOfBirth());


        update.addClickListener(clickEvent -> {
            diverUserClient.updateUser(UserDto.builder()
                    .id(this.userContext.getUserId())
                    .name(name.getValue())
                    .surname(surname.getValue())
                    .dateOfBirth(singleFormatDatePicker.getValue())
                    .localization(localization.getValue())
                    .email(email.getValue())
                    .login(login.getValue())
                    .password(password.getValue())
                    .build());
            Notification.show("Your account has been updated!");
        });

        delete.addClickListener(clickEvent -> {
            diverUserClient.deleteUser(this.userContext.getUserId());
            Notification.show("Your account has been deleted!");
            UI.getCurrent().navigate("main-view");
        });
    }

    private Component createButtonLayout() {
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        update.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(update, delete);
    }
}
