package com.diver.form;

import com.diver.api.client.DiverDivingBaseClient;
import com.diver.api.config.UserContext;
import com.diver.dto.DivingBaseDto;
import com.diver.view.DivingBaseView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class DivingBaseAddForm extends FormLayout {

    private final DiverDivingBaseClient diverDivingBaseClient;
    private final DivingBaseView divingBaseView;
    private Binder<DivingBaseDto> binder = new Binder<>(DivingBaseDto.class);

    private UserContext userContext;
    private TextField name = new TextField("Name");
    private TextField localization = new TextField("Localization");
    private TextField currencyName = new TextField("Currency name");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");


    public DivingBaseAddForm(DivingBaseView divingBaseView, DiverDivingBaseClient diverDivingBaseClient, UserContext userContext) {
        this.divingBaseView = divingBaseView;
        this.diverDivingBaseClient = diverDivingBaseClient;
        this.userContext = userContext;
        binder.bindInstanceFields(this);

        currencyName.setPlaceholder("Enter e.g.: EUR...");

        add(
                name,
                localization,
                currencyName,
                createButtonLayout()
        );
        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
    }
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, cancel);
    }

    private void save() {
        if (name.getValue().length() == 0 && localization.getValue().length() == 0 && currencyName.getValue().length() == 0){
            Notification.show("\n" +
                    "Not all fields have been completed!");
        } else {
            diverDivingBaseClient.createNewDivingBase(DivingBaseDto.builder()
                    .name(name.getValue())
                    .localization(localization.getValue())
                    .currencyName(currencyName.getValue())
                    .userId(this.userContext.getUserId())
                    .build());
            divingBaseView.refresh();
            setDivingBaseDto(null);
        }
    }

    private void cancel() {
        setDivingBaseDto(null);
    }
    public void setDivingBaseDto(DivingBaseDto divingBaseDto) {
        binder.setBean(divingBaseDto);

        if(divingBaseDto == null){
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
