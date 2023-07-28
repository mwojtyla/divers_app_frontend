package com.diver.form;

import com.diver.api.client.DiverEquipmentClient;
import com.diver.api.config.UserContext;
import com.diver.dto.EquipmentDto;
import com.diver.view.EquipmentView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class EquipmentUpdateForm extends FormLayout {

    private final DiverEquipmentClient diverEquipmentClient;
    private final EquipmentView equipmentView;
    private final UserContext userContext;
    private TextField name = new TextField("Current name");

    private TextField newName = new TextField("New name");
    private Button update = new Button("Update");
    private Button cancel = new Button("Cancel");


    public EquipmentUpdateForm(EquipmentView equipmentView, DiverEquipmentClient diverEquipmentClient, UserContext userContext) {
        this.equipmentView = equipmentView;
        this.diverEquipmentClient = diverEquipmentClient;
        this.userContext = userContext;

        add(
             name,
             newName,
             createButtonLayout()
        );

        update.addClickListener(event -> update());
        cancel.addClickListener(event -> cancel());
    }

    private Component createButtonLayout() {
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        update.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(update, cancel);
    }

    private void cancel() {
        setVisible(false);
    }

    private void update() {
        if(diverEquipmentClient.getEquipments(this.userContext.getUserId()).stream()
                .map(EquipmentDto::getName)
                .toList()
                .contains(newName.getValue())){
            Notification.show("The new name you want to use already exist!");
        } else {
            diverEquipmentClient.updateEquipment(EquipmentDto.builder()
                    .id(diverEquipmentClient.getEquipmentIdByNameAndUserId(name.getValue(),this.userContext.getUserId()))
                    .name(newName.getValue())
                    .userId(this.userContext.getUserId())
                    .build());
            equipmentView.refresh();
            setVisible(false);
        }
    }
}
