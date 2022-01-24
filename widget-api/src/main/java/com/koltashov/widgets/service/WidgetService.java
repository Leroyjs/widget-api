package com.koltashov.widgets.service;

import com.koltashov.widgets.domain.Widget;
import com.koltashov.widgets.domain.WidgetFilterCriteria;
import com.koltashov.widgets.domain.WidgetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface WidgetService {

    Widget createWidget(WidgetRequest widget);

    Page<Widget> getWidgetsByFilterCriteria(Pageable pageable, WidgetFilterCriteria criteria);

    Optional<Widget> getWidget(BigInteger id);

    Optional<Widget> updateWidget(BigInteger id, WidgetRequest widget);

    void deleteWidget(BigInteger id);
}
