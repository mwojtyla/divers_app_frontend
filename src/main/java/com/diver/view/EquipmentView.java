package com.diver.view;

import com.diver.api.client.DiverEquipmentClient;
import com.diver.api.config.UserContext;
import com.diver.dto.EquipmentDto;
import com.diver.form.EquipmentAddForm;
import com.diver.form.EquipmentDeleteForm;
import com.diver.form.EquipmentUpdateForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.stream.Collectors;

@Route(value = "/equipment", layout = MainLayout.class)
public class EquipmentView extends VerticalLayout {

    private final DiverEquipmentClient diverEquipmentClient;

    private UserContext userContext;
    private CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
    private Button addEquipment = new Button("Add new equipment");
    private Button updateEquipment = new Button("Update equipment");
    private Button deleteEquipment = new Button("Delete equipment");

    public EquipmentView(DiverEquipmentClient diverEquipmentClient, UserContext userContext) {
        this.diverEquipmentClient = diverEquipmentClient;
        this.userContext = userContext;

        setAlignItems(FlexComponent.Alignment.CENTER);

        EquipmentAddForm equipmentAddForm = new EquipmentAddForm(this, diverEquipmentClient, userContext);
        EquipmentUpdateForm equipmentUpdateForm = new EquipmentUpdateForm(this, diverEquipmentClient, userContext);
        EquipmentDeleteForm equipmentDeleteForm = new EquipmentDeleteForm(this, diverEquipmentClient, userContext);

        addEquipment.addClickListener(event -> {
            equipmentAddForm.setVisible(true);
        });

        updateEquipment.addClickListener(event -> {
            equipmentUpdateForm.setVisible(true);
        });

        deleteEquipment.addClickListener(event -> {
            equipmentDeleteForm.setVisible(true);
        });

        HorizontalLayout toolbar = new HorizontalLayout(addEquipment, updateEquipment, deleteEquipment);
        HorizontalLayout forms = new HorizontalLayout(equipmentAddForm, equipmentUpdateForm, equipmentDeleteForm);


        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        checkboxGroup.setItems(diverEquipmentClient.getEquipments(this.userContext.getUserId()).stream()
                .map(EquipmentDto::getName)
                .collect(Collectors.toList()));

        add(
                new H3("EQUIPMENT CHECKLIST"),
                toolbar,
                forms,
                checkboxGroup

        );
        equipmentAddForm.setVisible(false);
        equipmentUpdateForm.setVisible(false);
        equipmentDeleteForm.setVisible(false);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        checkboxGroup.setItems(diverEquipmentClient.getEquipments(this.userContext.getUserId()).stream()
                .map(EquipmentDto::getName)
                .collect(Collectors.toList()));
    }
}
