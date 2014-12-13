/*
 * Hard Ride
 * 
 * Copyright (C) 2014
 *
 */

package com.hardride;

import android.content.Context;

public class DebugCubeRed extends DebugCube {
	
	public DebugCubeRed(Context context) {
		super(new UnlitShaderSet(context), context, new float[]{1.0f, 0.0f, 0.0f, 1.0f});
	}
}
