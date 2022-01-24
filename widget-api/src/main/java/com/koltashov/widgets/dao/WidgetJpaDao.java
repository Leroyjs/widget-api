package com.koltashov.widgets.dao;

import com.koltashov.widgets.domain.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface WidgetJpaDao extends PagingAndSortingRepository<Widget, BigInteger> {

    @Query(value = "select w from Widget w where (w.x - TRUNC(w.width/2)) >= :lowerX and (w.x + TRUNC(w.width/2)) <= :upperX and (w.y - TRUNC(w.height/2)) >= :lowerY and (w.y + TRUNC(w.height/2)) <= :upperY")
    Page<Widget> findByFilterCriteria(Pageable pageable, int lowerX, int lowerY, int upperX, int upperY);

}
