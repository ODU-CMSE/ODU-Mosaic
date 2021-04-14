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
package org.eclipse.mosaic.fed.output.generator.carlacosim;
import org.eclipse.mosaic.fed.output.ambassador.AbstractOutputGenerator;
import org.eclipse.mosaic.fed.output.ambassador.Handle;

import org.eclipse.mosaic.interactions.communication.V2xMessageReception;
import org.eclipse.mosaic.interactions.communication.V2xMessageTransmission;
import org.eclipse.mosaic.interactions.electricity.ChargingStationUpdates;
import org.eclipse.mosaic.interactions.mapping.ChargingStationRegistration;
import org.eclipse.mosaic.interactions.mapping.RsuRegistration;
import org.eclipse.mosaic.interactions.mapping.TrafficLightRegistration;
import org.eclipse.mosaic.interactions.mapping.VehicleRegistration;
import org.eclipse.mosaic.interactions.traffic.VehicleUpdates;

import java.net.InetSocketAddress;

public class CarlaCoSim extends AbstractOutputGenerator {

    private final SocketRelay socketRelay;

    public CarlaCoSim(String sumoHostName, int sumoPort, int carlaPort) {
        socketRelay = new SocketRelay(sumoHostName,sumoPort,carlaPort);
        socketRelay.run();
    }

   /*  @Handle
    public void visualizeInteraction(VehicleUpdates interaction) throws Exception {
        socketRelay.updateVehicleUpdates(interaction);
    }

    @Handle
    public void visualizeInteraction(V2xMessageTransmission interaction) throws Exception {
        socketRelay.sendV2xMessage(interaction);
    }

    @Handle
    public void visualizeInteraction(V2xMessageReception interaction) throws Exception {
       socketRelay.receiveV2xMessage(interaction);
    }

    @Handle
    public void visualizeInteraction(VehicleRegistration interaction) throws Exception {
        websocketVisualizerServer.addVehicle(interaction);
    }

    @Handle
    public void visualizeInteraction(RsuRegistration interaction) throws Exception {
        websocketVisualizerServer.addRoadsideUnit(interaction);
    }

    @Handle
    public void visualizeInteraction(TrafficLightRegistration interaction) throws Exception {
        socketRelay.addTrafficLight(interaction);
    }

    @Handle
    public void visualizeInteraction(ChargingStationRegistration interaction) throws Exception {
        socketRelay.addChargingStation(interaction);
    }

    @Handle
    public void visualizeInteraction(ChargingStationUpdates interaction) throws Exception {
        socketRelay.updateChargingStation(interaction);
    } */

}
