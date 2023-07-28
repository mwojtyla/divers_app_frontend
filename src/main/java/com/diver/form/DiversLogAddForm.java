package com.diver.form;

import com.diver.api.client.DiverDiversLogClient;
import com.diver.api.client.DiverDivingBaseClient;
import com.diver.api.config.UserContext;
import com.diver.dto.DiversLogDto;
import com.diver.view.DiversLogView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;

public class DiversLogAddForm extends FormLayout {
    private final DiverDiversLogClient diverDiversLogClient;
    private final DiverDivingBaseClient diverDivingBaseClient;
    private final DiversLogView diversLogView;
    private final UserContext userContext;
    private Binder<DiversLogDto> binder = new Binder<>(DiversLogDto.class);

    DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
    DatePicker singleFormatDatePicker = new DatePicker("Date of diving");

    private TextField localization = new TextField("Localization");
    private IntegerField visibility = new IntegerField("Visibility [m]");
    private NumberField airTemperature = new NumberField("Air temperature [deg.C]");
    private NumberField surfaceTemperature = new NumberField("Surface temperature [deg.C]");
    private NumberField bottomTemperature = new NumberField("Bottom temperature [deg.C]");
    private NumberField weight = new NumberField("Weight [kg]");
    private NumberField depth = new NumberField("Depth [m]");
    private IntegerField timeOfDiving = new IntegerField("Time of diving [min.]");
    private TextField conditions = new TextField("Conditions");
    private TimePicker timeIn = new TimePicker();
    private TimePicker timeOut = new TimePicker();
    private IntegerField airUsed = new IntegerField("Air used [bar]");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");


    public DiversLogAddForm(DiversLogView diversLogView, DiverDiversLogClient diverDiversLogClient, UserContext userContext, DiverDivingBaseClient diverDivingBaseClient) {
        this.diversLogView = diversLogView;
        this.diverDiversLogClient = diverDiversLogClient;
        this.userContext = userContext;
        this.diverDivingBaseClient = diverDivingBaseClient;

        binder.bindInstanceFields(this);

        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        singleFormatDatePicker.setI18n(singleFormatI18n);
        timeIn.setLabel("Time in");
        timeOut.setLabel("Time out");

        add(
                singleFormatDatePicker,
                localization,
                visibility,
                airTemperature,
                surfaceTemperature,
                bottomTemperature,
                weight,
                depth,
                timeOfDiving,
                conditions,
                timeIn,
                timeOut,
                airUsed,
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
        diverDiversLogClient.createNewDiversLog(DiversLogDto.builder()
                .dateOfDiving(singleFormatDatePicker.getValue())
                .localization(localization.getValue())
                .visibility(visibility.getValue())
                .airTemperature(airTemperature.getValue())
                .surfaceTemperature(surfaceTemperature.getValue())
                .bottomTemperature(bottomTemperature.getValue())
                .weight(weight.getValue())
                .depth(depth.getValue())
                .timeOfDiving(timeOfDiving.getValue())
                .conditions(conditions.getValue())
                .timeIn(timeIn.getValue())
                .timeOut(timeOut.getValue())
                .airUsed(airUsed.getValue())
                .userId(this.userContext.getUserId())
                .build());
        diversLogView.refresh();
        setDiversLogDto(null);
    }

    private void cancel() {
        setDiversLogDto(null);
    }

    public void setDiversLogDto(DiversLogDto diversLogDto) {
        binder.setBean(diversLogDto);

        if (diversLogDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }
}

