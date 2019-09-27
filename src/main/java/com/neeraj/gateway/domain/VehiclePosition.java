package com.neeraj.gateway.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author neeraj on 27/09/19
 * Copyright (c) 2019, PositionTrackerAPIGateway.
 * All rights reserved.
 */
@Data
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class VehiclePosition implements Comparable<VehiclePosition> {

    private String name;
    private BigDecimal lat;
    private BigDecimal lng;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
    private Date timestamp;
    private BigDecimal speed;


    @Override
    public int compareTo(VehiclePosition o) {
        return o.timestamp.compareTo(this.timestamp);
    }
}
