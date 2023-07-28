package com.diver.form;

import com.diver.api.client.DiverEquipmentClient;
import com.diver.api.config.UserContext;
import com.diver.view.EquipmentView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class EquipmentDeleteForm extends FormLayout {

    private final DiverEquipmentClient diverEquipmentClient;
    private final EquipmentView equipmentView;
    private final UserContext userContext;
    private TextField name = new TextField("Name of equipment to delete");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    public EquipmentDeleteForm(EquipmentView equipmentView, DiverEquipmentClient diverEquipmentClient, UserContext userContext) {
        this.equipmentView = equipmentView;
        this.diverEquipmentClient = diverEquipmentClient;
        this.userContext = userContext;

        add(
                name,
                createButtonLayout()
        );

        delete.addClickListener(event -> delete());
        cancel.addClickListener(event -> cancel());
    }

    private Component createButtonLayout() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        delete.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(delete, cancel);
    }

    private void cancel() {
        setVisible(false);
    }

    private void delete() {
        diverEquipmentClient.deleteEquipment(diverEquipmentClient.getEquipmentIdByNameAndUserId(name.getValue(), this.userContext.getUserId()));
        equipmentView.refresh();
        setVisible(false);
    }
}
