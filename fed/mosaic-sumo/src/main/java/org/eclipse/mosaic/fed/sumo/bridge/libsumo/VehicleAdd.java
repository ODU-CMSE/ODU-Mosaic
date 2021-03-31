/*
 * Copyright (c) 2020 Fraunhofer FOKUS and others. All rights reserved.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contact: mosaic@fokus.fraunhofer.de
 */
package org.eclipse.mosaic.fed.sumo.bridge.libsumo;

import org.eclipse.mosaic.fed.sumo.bridge.Bridge;
import org.eclipse.mosaic.fed.sumo.bridge.CommandException;
import org.eclipse.mosaic.rti.api.InternalFederateException;

import org.eclipse.sumo.libsumo.Vehicle;

import java.util.Locale;

public class VehicleAdd implements org.eclipse.mosaic.fed.sumo.bridge.api.VehicleAdd {

    public void execute(Bridge con, String vehicleId, String vehicleType, String routeId, double departPosition, int departLane, double departSpeed) {
        //FIXME departSpeed, departPos, departLane magic numbers
        Vehicle.add(
                vehicleId,
                routeId,
                vehicleType,
                "now",
                Integer.toString(departLane),
                String.format(Locale.ENGLISH, "%.2f", departPosition),
                "max" //FIXME
        );
    }

    @Override
    public void execute(Bridge con, String vehicleId, String routeId, String vehicleType, String departLane, String departPosition, String departSpeed, String line, int personCapacity, int personNumber) throws CommandException, InternalFederateException {
        //FIXME line, personCapacity, personNumber
        Vehicle.add(
                vehicleId,
                routeId,
                vehicleType,
                "now",
                departLane,
                departPosition,
                departSpeed
        );
    }

    @Override
    public void execute(Bridge con, String vehicleId, String routeId, String vehicleType, String departLane, String departPosition, String departSpeed) throws CommandException, InternalFederateException {
        Vehicle.add(
                vehicleId,
                routeId,
                vehicleType,
                "now",
                departLane,
                departPosition,
                departSpeed
        );
    }
}