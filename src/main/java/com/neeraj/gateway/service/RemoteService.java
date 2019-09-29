package com.neeraj.gateway.service;

import com.neeraj.gateway.domain.VehiclePosition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @author neeraj on 27/09/19
 * Copyright (c) 2019, PositionTrackerAPIGateway.
 * All rights reserved.
 */
@FeignClient(url = "${position-tracker-url}", name = "position-tracker")
public interface RemoteService {

    @GetMapping("/vehicles")
    Collection<VehiclePosition> getAllLatestPositionsSince(@RequestParam(value = "since", required = false) String date);

    @GetMapping("/vehicles")
    Collection<VehiclePosition> getAllLatestPositionsSince();

    @GetMapping("/vehicles/history/{vehicleName}")
    Collection<VehiclePosition> getHistoryFor(@PathVariable("vehicleName") String vehicleName);
}
