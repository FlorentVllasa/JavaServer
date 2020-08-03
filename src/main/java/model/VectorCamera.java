package model;

public class VectorCamera {
    private float camX;
    private float camY;
    private float camZ;
    private double timeStamp;

    public VectorCamera(float camX, float camY, float camZ, double timeStamp){
        this.camX = camX;
        this.camY = camY;
        this.camZ = camZ;
        this.timeStamp = timeStamp;
    }

    public void determinePlatformStart(VectorCamera vectorCamera){
        double camY = vectorCamera.getCamY();
        while(camY >= 1.33){
            System.out.println("Platform not moving!");
        }
        System.out.println("Platform started!");
    }

    public void determinePlatformEnd(VectorCamera vectorCamera){

    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }

    public float getCamZ() {
        return camZ;
    }

    public void setCamZ(float camZ) {
        this.camZ = camZ;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }
}
