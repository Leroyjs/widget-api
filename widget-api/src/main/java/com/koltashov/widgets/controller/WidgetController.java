package com.koltashov.widgets.controller;


import com.koltashov.widgets.domain.Widget;
import com.koltashov.widgets.domain.WidgetFilterCriteria;
import com.koltashov.widgets.domain.WidgetRequest;
import com.koltashov.widgets.domain.WidgetResourceAssembler;
import com.koltashov.widgets.service.WidgetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Optional;

@RestController
public class WidgetController {
    private final WidgetService widgetService;
    private final WidgetResourceAssembler resourceAssembler;

    public WidgetController(WidgetService widgetService, WidgetResourceAssembler resourceAssembler) {
        this.widgetService = widgetService;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping("/widget")
    public ResponseEntity<EntityModel<Widget>> createWidget(@RequestBody @Valid WidgetRequest widget) {
        Widget response = widgetService.createWidget(widget);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resourceAssembler.toModel(response));
    }

    @GetMapping("/widgets")
    public ResponseEntity<PagedModel<EntityModel<Widget>>> getAllWidgets(@SortDefault.SortDefaults({
            @SortDefault(sort = "zindex", direction = Sort.Direction.ASC)}) Pageable pageable
            , PagedResourcesAssembler<Widget> assembler
            , WidgetFilterCriteria criteria) {
        Page<Widget> page = widgetService.getWidgetsByFilterCriteria(pageable, criteria);
        return ResponseEntity.ok(assembler.toModel(page, resourceAssembler));
    }

    @GetMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> getWidget(@PathVariable BigInteger id) {
        Optional<Widget> foundWidget = widgetService.getWidget(id);
        return foundWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PatchMapping("/widget/{id}")
    public ResponseEntity<EntityModel<Widget>> updateWidget(@PathVariable("id") BigInteger id, @RequestBody WidgetRequest widgetRequest) {
        Optional<Widget> updateWidget = widgetService.updateWidget(id, widgetRequest);
        return updateWidget
                .map(resourceAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/widget/{id}")
    public ResponseEntity deleteWidget(@PathVariable BigInteger id) {
        widgetService.deleteWidget(id);
        return ResponseEntity.noContent()
                .build();
    }

}
