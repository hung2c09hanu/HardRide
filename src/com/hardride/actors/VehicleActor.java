/*
 * Hard Ride
 * 
 * Copyright (C) 2014
 *
 */

package com.hardride.actors;

import com.hardride.actors.base.Actor;
import com.hardride.models.VehicleFlatModel;
import com.hardride.models.base.Model;

import android.content.Context;

public class VehicleActor extends Actor {
	
	protected static Model msModel;
	
	public VehicleActor(Context context) {
		super(context);
		
		if (msModel == null) {
			msModel = new VehicleFlatModel(context);
		}
		
		setModel(msModel);
	}
	
	public VehicleActor(Context context, float x, float y, float z, float yaw, float pitch, float roll) {
		super(context, x, y, z, yaw, pitch, roll);
			
		if (msModel == null) {
			msModel = new VehicleFlatModel(context);
		}
			
		setModel(msModel);		
	}
}
