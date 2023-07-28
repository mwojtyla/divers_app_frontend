package com.diver.view;

import com.diver.api.client.DiverDiversLogClient;
import com.diver.api.client.DiverDivingBaseClient;
import com.diver.api.config.UserContext;
import com.diver.dto.DiversLogDto;
import com.diver.form.DiversLogAddForm;
import com.diver.form.DiversLogUpdateAndDeleteForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route(value = "/divers-log", layout = MainLayout.class)
public class DiversLogView extends VerticalLayout {
    private final DiverDiversLogClient diverDiversLogClient;
    private final DiverDivingBaseClient diverDivingBaseClient;

    private UserContext userContext;
    private final Grid<DiversLogDto> grid = new Grid<>(DiversLogDto.class);

    private TextField filterByLocalizationField = new TextField();
    private NumberField filterByDepthField = new NumberField();

    private Button addDive = new Button("Add new dive");

    public DiversLogView(DiverDiversLogClient diverDiversLogClient, DiverDivingBaseClient diverDivingBaseClient, UserContext userContext) {
        this.diverDiversLogClient = diverDiversLogClient;
        this.diverDivingBaseClient = diverDivingBaseClient;
        this.userContext = userContext;

        setAlignItems(FlexComponent.Alignment.CENTER);

        gridConfig();
        filterConfig();

        DiversLogAddForm diversLogAddForm = new DiversLogAddForm(this, diverDiversLogClient, userContext, diverDivingBaseClient);
        DiversLogUpdateAndDeleteForm diversLogUpdateAndDeleteForm = new DiversLogUpdateAndDeleteForm(this, diverDiversLogClient, userContext, diverDivingBaseClient);

        addDive.addClickListener(event -> {
            grid.asSingleSelect().clear();
            diversLogAddForm.setDiversLogDto(new DiversLogDto());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterByLocalizationField, filterByDepthField, addDive);

        HorizontalLayout mainContent = new HorizontalLayout(grid, diversLogAddForm, diversLogUpdateAndDeleteForm);
        mainContent.setSizeFull();

        add(
                new H3("DIVER'S LOG"),
                toolbar,
                mainContent
        );

        diversLogAddForm.setDiversLogDto(null);
        diversLogUpdateAndDeleteForm.setDiversLogDto(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> diversLogUpdateAndDeleteForm.setDiversLogDto(grid.asSingleSelect().getValue()));
    }

    public void filterConfig() {
        filterByLocalizationField.setPlaceholder("Filter by localization...");
        filterByLocalizationField.setClearButtonVisible(true);
        filterByLocalizationField.setValueChangeMode(ValueChangeMode.EAGER);
        filterByLocalizationField.addValueChangeListener(e -> filterByLocalization());

        filterByDepthField.setPlaceholder("Filter by depth...");
        filterByDepthField.setClearButtonVisible(true);
        filterByDepthField.setValueChangeMode(ValueChangeMode.EAGER);
        filterByDepthField.addValueChangeListener(e -> filterByDepth());
    }

    public void gridConfig() {
        grid.setColumns("dateOfDiving", "localization", "visibility", "airTemperature", "surfaceTemperature", "bottomTemperature", "weight", "depth", "timeOfDiving", "conditions", "timeIn", "timeOut", "airUsed");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void filterByDepth() {
        if (filterByDepthField == null || filterByDepthField.isEmpty()) {
            refresh();
        } else {
            grid.setItems(diverDiversLogClient.getDiversLogByDepthAndUserId(filterByDepthField.getValue(), this.userContext.getUserId()));
        }
    }

    private void filterByLocalization() {
        if (filterByLocalizationField == null || filterByLocalizationField.isEmpty()) {
            refresh();
        } else {
            grid.setItems(diverDiversLogClient.getDiversLogByLocalizationAndUserId(filterByLocalizationField.getValue(), this.userContext.getUserId()));
        }
    }

    public void refresh() {
        grid.setItems(diverDiversLogClient.getDiversLogsByUserId(this.userContext.getUserId()));
    }
}
