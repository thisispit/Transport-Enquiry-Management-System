package com.thisispit.tems.repository;

import com.thisispit.tems.entity.Route;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Page<Route> findByActiveTrue(Pageable pageable);

    Page<Route> findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCaseAndActiveTrue(
            String source,
            String destination,
            Pageable pageable);

    List<Route> findByActiveTrueOrderByRouteNameAsc();

    long countByActiveTrue();
}