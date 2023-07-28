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

public class EquipmentAddForm extends FormLayout {

    private final DiverEquipmentClient diverEquipmentClient;
    private final EquipmentView equipmentView;
    private final UserContext userContext;
    private TextField name = new TextField("Name");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    public EquipmentAddForm(EquipmentView equipmentView, DiverEquipmentClient diverEquipmentClient, UserContext userContext) {
        this.equipmentView = equipmentView;
        this.diverEquipmentClient = diverEquipmentClient;
        this.userContext = userContext;

        add(
                name,
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
        if (diverEquipmentClient.getEquipments(this.userContext.getUserId()).stream()
                .map(EquipmentDto::getName)
                .toList()
                .contains(name.getValue())) {
            Notification.show("The name you want to use already exist!");
        } else {
            diverEquipmentClient.createNewEquipment(EquipmentDto.builder()
                    .name(name.getValue())
                    .userId(this.userContext.getUserId())
                    .build());
            equipmentView.refresh();
            setVisible(false);
        }
    }

    private void cancel() {
        setVisible(false);
    }

}
