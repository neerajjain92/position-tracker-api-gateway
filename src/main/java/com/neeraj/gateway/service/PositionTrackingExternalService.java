package com.neeraj.gateway.service;

import com.neeraj.gateway.domain.VehiclePosition;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author neeraj on 27/09/19
 * Copyright (c) 2019, PositionTrackerAPIGateway.
 * All rights reserved.
 */
@Service
public class PositionTrackingExternalService {

    @Autowired
    private RemoteService remoteService;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    @HystrixCommand(fallbackMethod = "handleExternalServiceDown")
    public Collection<VehiclePosition> getAllUpdatedPositionsSince(Date since) {
        String date = formatter.format(since);
        Collection<VehiclePosition> positions = remoteService.getAllLatestPositionsSince(date);
        return positions;
    }

    public Collection<VehiclePosition> handleExternalServiceDown(Date since) {
        System.out.println("hystrix triggered for getAllLatestPositionSince..... at " + new Date());
        // as the external service is down, simply return "no updates"
        return new HashSet<>();
    }

    @HystrixCommand(fallbackMethod = "getHistoryForDown")
    public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
        return remoteService.getHistoryFor(vehicleName);
    }

    public Collection<VehiclePosition> getHistoryForDown(String vehicleName) {
        System.out.println("Hystrix Triggered for getHistoryFor....... at " + new Date());
        return new HashSet<>();
    }
}
