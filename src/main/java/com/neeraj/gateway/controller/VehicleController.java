package com.neeraj.gateway.controller;

import com.neeraj.gateway.domain.LatLong;
import com.neeraj.gateway.domain.VehiclePosition;
import com.neeraj.gateway.service.PositionTrackingExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author neeraj on 27/09/19
 * Copyright (c) 2019, PositionTrackerAPIGateway.
 * All rights reserved.
 */
@RestController
public class VehicleController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private PositionTrackingExternalService positionTrackingExternalService;

    private Date lastUpdateTime;

    @GetMapping("/")
    public String healthCheck() {
        return "Hey API Gateway is healthy at " + new Date() + " ...!";
    }

    @GetMapping("/vehicles/history/{vehicleName}")
    @CrossOrigin(origins = "*")
    public Collection<LatLong> getHistoryFor(@PathVariable("vehicleName") String vehicleName) {
        Collection<LatLong> results;
        Collection<VehiclePosition> vehicles = positionTrackingExternalService.getHistoryFor(vehicleName);
        results = vehicles.stream()
                .map(vehicle -> new LatLong(vehicle.getLat(), vehicle.getLng()))
                .collect(Collectors.toList());
        Collections.reverse((List<?>) results);
        return results;
    }


    /**
     * Scheduled polling of coordinates for all Vehicles from Tracker Micro-Service and publish to WebSockets,
     * whoever has subscribed to this websocket
     */
    @Scheduled(fixedRate = 2000)
    public void updatePositions() {
        Collection<VehiclePosition> positions = positionTrackingExternalService.getAllUpdatedPositionsSince(lastUpdateTime);
        this.lastUpdateTime = new Date();
        positions.forEach((position) -> this.messagingTemplate.convertAndSend("/chat", position));
    }
}
