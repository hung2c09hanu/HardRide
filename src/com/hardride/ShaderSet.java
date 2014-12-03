package com.hardride;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderSet {
	
	public final int ID;
	
    private String mShaderName;
	
	public ShaderSet(Context context, String shaderName) {
		mShaderName = shaderName;
		String vShaderCode = new String();
		String fShaderCode = new String();
		
		try {
        	AssetManager assetManager = context.getAssets();
        	InputStream ims = assetManager.open("shaders/" + mShaderName + ".vs");
        	vShaderCode = convertStreamToString(ims);
        	ims = assetManager.open("shaders/" + mShaderName + ".fs");
        	fShaderCode = convertStreamToString(ims);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        // prepare shaders and OpenGL program
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fShaderCode);

        ID = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(ID, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(ID, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(ID);                  // create OpenGL program executables
	}

	public void SetVec4Unfiorm(String uniformName, float[] value) {
        int uniformHandle = GLES20.glGetUniformLocation(ID, uniformName);
        verifyUniformHandle(uniformHandle, uniformName);
        
        GLES20.glUniform4fv(uniformHandle, 1, value, 0);
        HardRideRenderer.checkGlError("glUniform4fv");
	}
	
	public void SetMat4Unfiorm(String uniformName, float[] value) {
        int uniformHandle = GLES20.glGetUniformLocation(ID, uniformName);
        verifyUniformHandle(uniformHandle, uniformName);
        
        GLES20.glUniformMatrix4fv(uniformHandle, 1, false, value, 0);
        HardRideRenderer.checkGlError("glUniform4fv");
	}
	
	public void verifyUniformHandle(int uniformHandler, String uniformName) {
		HardRideRenderer.checkGlError("glGetUniformLocation");
		if (uniformHandler == -1) {
			throw new RuntimeException("Set uniform failed!\n" +
					"Cannot find uniform '" + uniformName + 
					"' in '" + mShaderName + "' shader!");
		}
	}
	
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        
        if (compileStatus[0] == 0) {
        	// glGetShaderInfoLog() doesn't work :( For possible solution look here: 
        	// http://stackoverflow.com/questions/4588800/glgetshaderinfolog-returns-empty-string-android
        	// and here:
        	// http://stackoverflow.com/questions/24122075/the-import-com-badlogic-cannot-be-resolved-in-java-project-libgdx-setup
        	Log.e("ShaderCompiler", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
        	GLES20.glDeleteShader(shader);
        	shader = 0;
        	throw new RuntimeException("Error during compiling shader");
        }
        
        return shader;
    }
	
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
