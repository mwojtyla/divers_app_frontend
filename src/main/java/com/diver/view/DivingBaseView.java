package com.diver.view;

import com.diver.api.client.DiverDivingBaseClient;
import com.diver.api.config.UserContext;
import com.diver.dto.DivingBaseDto;
import com.diver.form.DivingBaseAddForm;
import com.diver.form.DivingBaseUpdateAndDeleteForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/diving-base", layout = MainLayout.class)
public class DivingBaseView extends VerticalLayout {
    private final DiverDivingBaseClient diverDivingBaseClient;
    private final Grid<DivingBaseDto> grid = new Grid<>(DivingBaseDto.class);

    private UserContext userContext;

    private Button addDivingBase = new Button("Add new diving base");

    public DivingBaseView(DiverDivingBaseClient diverDivingBaseClient, UserContext userContext) {
        this.diverDivingBaseClient = diverDivingBaseClient;
        this.userContext = userContext;
        grid.setColumns("name", "localization", "currencyName", "currencyRate", "temperature");

        setAlignItems(FlexComponent.Alignment.CENTER);

        DivingBaseAddForm divingBaseAddForm = new DivingBaseAddForm(this, diverDivingBaseClient, userContext);
        DivingBaseUpdateAndDeleteForm divingBaseUpdateAndDeleteForm = new DivingBaseUpdateAndDeleteForm(this, diverDivingBaseClient, userContext);

        addDivingBase.addClickListener(event -> {
                grid.asSingleSelect().clear();
                divingBaseAddForm.setDivingBaseDto(new DivingBaseDto());
                });

        HorizontalLayout toolbar = new HorizontalLayout(addDivingBase);

        HorizontalLayout mainContent = new HorizontalLayout(grid, divingBaseAddForm, divingBaseUpdateAndDeleteForm);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(
                new H3("DIVING BASES"),
                toolbar,
                mainContent
        );

        divingBaseAddForm.setDivingBaseDto(null);
        divingBaseUpdateAndDeleteForm.setDivingBaseDto(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> divingBaseUpdateAndDeleteForm.setDivingBaseDto(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(diverDivingBaseClient.getDivingBasesByUserId(this.userContext.getUserId()));
    }
}