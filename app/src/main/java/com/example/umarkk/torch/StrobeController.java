package com.example.umarkk.torch;

import android.hardware.Camera;


public class StrobeController implements Runnable {

    public static StrobeController getInstance() {
        return instance == null ? instance = new StrobeController() : instance;
    }

    private static StrobeController instance;

    Camera camera;
    Camera.Parameters paramsOn, paramsOff;

    public volatile boolean requestStop = false;
    public volatile boolean isRunning = false;
    public volatile LED_Light controller;
    public int freq;
    public volatile String errorMessage = "";


    @Override
    public void run() {
        if (isRunning)
            return;

        requestStop = false;
        isRunning = true;

        camera = Camera.open();

        paramsOn = camera.getParameters();
        paramsOff = camera.getParameters();

        paramsOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        paramsOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        while (!requestStop) {
            if (freq == 0) {
                camera.setParameters(paramsOn);
                camera.startPreview();
                continue;
            }
            try {

                camera.setParameters(paramsOn);
                camera.startPreview();
                Thread.sleep(900 - freq * 100);

                camera.setParameters(paramsOff);
                camera.stopPreview();
                Thread.sleep(900 - freq * 100);

            } catch (InterruptedException ex) {
            } catch (RuntimeException ex) {
                requestStop = true;
                errorMessage = "Error setting camera flash status. Your device may be unsupported.";
            }
        }
        camera.setParameters(paramsOff);

        camera.release();

        isRunning = false;
        requestStop = false;


    }
}
