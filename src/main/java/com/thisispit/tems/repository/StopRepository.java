package com.thisispit.tems.repository;

import com.thisispit.tems.entity.Stop;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {

    List<Stop> findByRouteIdOrderByStopOrderAsc(Long routeId);

    Optional<Stop> findByRouteIdAndStopOrder(Long routeId, int stopOrder);

    Optional<Stop> findByIdAndRouteId(Long id, Long routeId);

    void deleteByRouteId(Long routeId);
}