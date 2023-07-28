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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class DivingBaseUpdateAndDeleteForm extends FormLayout {
    private final DiverDivingBaseClient diverDivingBaseClient;
    private final DivingBaseView divingBaseView;
    private Binder<DivingBaseDto> binder = new Binder<>(DivingBaseDto.class);

    private UserContext userContext;
    private TextField name = new TextField("Name");
    private TextField localization = new TextField("Localization");
    private TextField currencyName = new TextField("Currency name");

    private Button update = new Button("Update");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    public DivingBaseUpdateAndDeleteForm(DivingBaseView divingBaseView, DiverDivingBaseClient diverDivingBaseClient, UserContext userContext) {
        this.divingBaseView = divingBaseView;
        this.diverDivingBaseClient = diverDivingBaseClient;
        this.userContext = userContext;
        binder.bindInstanceFields(this);

        add(
                name,
                localization,
                currencyName,
                createButtonLayout()
        );
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
        cancel.addClickListener(event -> cancel());
    }


    private Component createButtonLayout() {
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        update.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(update, delete, cancel);
    }

    private void update() {
        DivingBaseDto divingBaseDto = binder.getBean();
        diverDivingBaseClient.updateDivingBaseDto(DivingBaseDto.builder()
                .id(divingBaseDto.getId())
                .name(name.getValue())
                .localization(localization.getValue())
                .currencyName(currencyName.getValue())
                .userId(this.userContext.getUserId())
                .build());
        divingBaseView.refresh();
        setDivingBaseDto(null);
    }

    private void delete() {
        DivingBaseDto divingBaseDto = binder.getBean();
        diverDivingBaseClient.deleteDivingBase(divingBaseDto.getId());
        divingBaseView.refresh();
        setDivingBaseDto(null);
    }

    private void cancel() {
        setDivingBaseDto(null);
    }

    public void setDivingBaseDto(DivingBaseDto divingBaseDto) {
        binder.setBean(divingBaseDto);

        if (divingBaseDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}
