package com.pkp;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.pkp.gameengine.IScreen;
import com.pkp.gameengine.i.IGame;
import com.pkp.gameengine.io.impl.Audio;
import com.pkp.gameengine.io.impl.FileIO;
import com.pkp.gameengine.io.impl.GLGraphics;
import com.pkp.gameengine.io.impl.Input;
import com.pkp.screen.LoadingScreen;

public class GLGame extends Activity implements IGame, Renderer {
	enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }
    
    GLSurfaceView glView;    
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    IScreen screen;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setEGLConfigChooser(8, 8, 8, 8, 0, 0); 
        glView.getHolder().setFormat(PixelFormat.RGBA_8888); 
        glView.setRenderer(this);
        setContentView(glView);
        
        glGraphics = new GLGraphics(glView);
        fileIO = new FileIO(this);
        audio = new Audio(this);
        input = new Input(this, glView, 1, 1);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");        
    }
    
    public void onResume() {
        super.onResume();
        glView.onResume();
        wakeLock.acquire();
    }
    
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {        
        glGraphics.setGL(gl);
        synchronized(stateChanged) {
            if(state == GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }        
    }
    
    public void onSurfaceChanged(GL10 gl, int width, int height) {        
    }

    public void onDrawFrame(GL10 gl) {                
        GLGameState state = null;
        
        synchronized(stateChanged) {
            state = this.state;
        }
        
        if(state == GLGameState.Running) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen.update(deltaTime);
            screen.present(deltaTime);
        }
        
        if(state == GLGameState.Paused) {
            screen.pause();            
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        
        if(state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }            
        }
    }   
    
    @Override 
    public void onPause() {        
        synchronized(stateChanged) {
            if(isFinishing())            
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true) {
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException e) {         
                }
            }
        }
        wakeLock.release();
        glView.onPause();  
        super.onPause();
    }    
    
    public GLGraphics getGLGraphics() {
        return glGraphics;
    }  
    
    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(IScreen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public IScreen getCurrentScreen() {
        return screen;
    }

    public IScreen getStartScreen() {
    	return new LoadingScreen(this);
    }
    
}
