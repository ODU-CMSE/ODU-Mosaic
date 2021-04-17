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
/**
 * The Socket Relay implements first connect to a server as a client then
 * generate a socket server
 *
 * @author Defu Cui
 * @version 1.0
 * @since 04-09-2021
 */
package org.eclipse.mosaic.fed.output.generator.carlacosim;
import org.eclipse.mosaic.interactions.communication.V2xMessageReception;
import org.eclipse.mosaic.interactions.communication.V2xMessageTransmission;
import org.eclipse.mosaic.interactions.electricity.ChargingStationUpdates;
import org.eclipse.mosaic.interactions.mapping.ChargingStationRegistration;
import org.eclipse.mosaic.interactions.mapping.RsuRegistration;
import org.eclipse.mosaic.interactions.mapping.TrafficLightRegistration;
import org.eclipse.mosaic.interactions.mapping.VehicleRegistration;
import org.eclipse.mosaic.interactions.traffic.VehicleUpdates;
import org.eclipse.mosaic.rti.api.Interaction;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.TimeUnit;

import java.net.*;
import java.io.*;
/**
 * The socket relay class.
 */
public class SocketRelay implements Runnable {

	// Relay Server port
	int carlaPort = 8913;
    // Relay client port and host
	int sumoPort = 8813;
	String sumoHostName = "localhost";
	
	/**
	 * This is the customized class constructor
	 * 
	 * @param sumoHostName  This is the sumo server host name
	 * @param sumPort   This is the sumo server port
	 * @param carlaPort This is the carla client port
	 */
	public SocketRelay(String sumoHostName, int sumoPort, int carlaPort)
	{
		this.sumoHostName = sumoHostName;
		this.sumoPort = sumoPort;
		this.carlaPort = carlaPort;
	}
	
	public SocketRelay()
	{
		this("localhost", 8813, 8913);
	}

	/**
	 * This method is used to run the relay. 
	 * First connect to the SUMO server as a client and generate a socket server for carla
	 * After Carla connected, the message begins to transfer between too ends.
	 */
	@Override
	public void run() 
	{
        System.out.println("Socket Relay Start");
		System.out.println("sumo port is " + sumoPort);
		System.out.println("sumo host is "+ sumoHostName);
		System.out.println("carla port is " + carlaPort);
		//wait for the sumo server starts
		try
		{
			TimeUnit.SECONDS.sleep(30); //Socket relay thread sleeps 30 seconds.
		}
		catch (InterruptedException e) {
            System.out.println("Interrupted "
                               + "while Sleeping");
        }
			//set up the client order as 2
			//final byte[] setOrderBytes = new byte[]{0x00,0x00,0x00,0x0a,0x06,0x03,0x00, 0x00,0x00,0x02};
           // toSumoDataOutputStream.write(setOrderBytes);
	
		try (Socket sumoSocket = new Socket(sumoHostName, sumoPort)) {
			// Connect to SUMO first.

			InputStream fromSumoinputStream = sumoSocket.getInputStream();
			DataInputStream fromSumoDataInputStream = new DataInputStream(fromSumoinputStream);
			OutputStream toSumoOutputStream = sumoSocket.getOutputStream();
			DataOutputStream toSumoDataOutputStream = new DataOutputStream(toSumoOutputStream);
            System.out.println("Sumo Server connected");			
			//final byte[] setOrderBytes = new byte[]{0x00,0x00,0x00,0x0a,0x06,0x03,0x00, 0x00,0x00,0x02};
            //toSumoDataOutputStream.write(setOrderBytes);
			// Listen to CARLA request.
			try (ServerSocket carlaServerSocket = new ServerSocket(carlaPort)) {
				// Blocking call until CARLA is connected.
				System.out.println("Wait for Carla client connected");
				Socket carlaSocket = carlaServerSocket.accept();
                System.out.println("Carla connected");
				try {
					InputStream fromCarlaInputStream = carlaSocket.getInputStream();
					DataInputStream fromCarlaDataInputStream= new DataInputStream(fromCarlaInputStream);
					OutputStream toCarlaOutputStream = carlaSocket.getOutputStream();
					DataOutputStream toCarlaDataOutputStream = new DataOutputStream(toCarlaOutputStream);
                    System.out.println("Begin Data Streaming");
					byte[] buffer = new byte[65535];

					String closeMessage = "";
					while (closeMessage != "Close") {
						// From Carla to Sumo
						int length = fromCarlaDataInputStream.read(buffer);
						toSumoDataOutputStream.write(buffer, 0, length);

						// From Sumo to Carla
						length = fromSumoDataInputStream.read(buffer);
						toCarlaDataOutputStream.write(buffer, 0, length);

						// Check the close message.
					}
                    System.out.println("End Data Streaming");
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					carlaSocket.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				sumoSocket.close();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
}

