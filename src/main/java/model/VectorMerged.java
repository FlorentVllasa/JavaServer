package model;

import java.util.ArrayList;
import java.util.List;

public class VectorMerged {
    private float gX;
    private float gY;
    private float gZ;
    private float aX;
    private float aY;
    private float aZ;
    private float camX;
    private float camY;
    private float camZ;
    private long sensorTime;
    private double camStamp;


    private List<VectorMerged> vectorMergedList;

    public VectorMerged(float gX, float gY, float z1, float x2, float y2, float z2, long sensorTime) {
        this.gX = gX;
        this.gY = gY;
        this.gZ = gZ;
        this.aX = aX;
        this.aY = aY;
        this.aZ = aZ;
        this.sensorTime = sensorTime;
        vectorMergedList = new ArrayList<>();
    }


    public VectorMerged(VectorGyro vectorGyro, VectorAcc vectorAcc) {
        this.gX = vectorGyro.getX();
        this.gY = vectorGyro.getY();
        this.gZ = vectorGyro.getZ();
        this.sensorTime = vectorGyro.getTimeStamp();
        this.aX = vectorAcc.getaX();
        this.aY = vectorAcc.getaY();
        this.aZ = vectorAcc.getaZ();
        vectorMergedList = new ArrayList<>();
    }

    public VectorMerged(VectorGyro vectorGyro, VectorAcc vectorAcc, VectorCamera vectorCamera){
        this.gX = vectorGyro.getX();
        this.gY = vectorGyro.getY();
        this.gZ = vectorGyro.getZ();
        this.sensorTime = vectorGyro.getTimeStamp();
        this.aX = vectorAcc.getaX();
        this.aY = vectorAcc.getaY();
        this.aZ = vectorAcc.getaZ();
        this.camX = vectorCamera.getCamX();
        this.camY = vectorCamera.getCamY();
        this.camZ = vectorCamera.getCamZ();
        this.camStamp = vectorCamera.getTimeStamp();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("x1: ").append(this.gX).append(" ");
        sb.append("y1: ").append(this.gY).append(" ");
        sb.append("z1: ").append(this.gZ).append(" ");
        sb.append("Sensor-Stamp: ").append(this.sensorTime).append(" | ");
        sb.append("x2: ").append(this.aX).append(" ");
        sb.append("y2: ").append(this.aY).append(" ");
        sb.append("z2: ").append(this.aZ).append(" ");
        sb.append("Cam-Stamp: ").append(this.camStamp).append("\n");
        return sb.toString();
    }

    public float getgX() {
        return gX;
    }

    public void setgX(float gX) {
        this.gX = gX;
    }

    public float getgY() {
        return gY;
    }

    public void setgY(float gY) {
        this.gY = gY;
    }

    public float getgZ() {
        return gZ;
    }

    public void setgZ(float gZ) {
        this.gZ = gZ;
    }

    public float getaX() {
        return aX;
    }

    public void setaX(float aX) {
        this.aX = aX;
    }

    public float getaY() {
        return aY;
    }

    public void setaY(float aY) {
        this.aY = aY;
    }

    public float getaZ() {
        return aZ;
    }

    public void setaZ(float aZ) {
        this.aZ = aZ;
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

    public long getSensorTime() {
        return sensorTime;
    }

    public void setSensorTime(long sensorTime) {
        this.sensorTime = sensorTime;
    }

    public double getCamStamp() {
        return camStamp;
    }

    public void setCamStamp(long camStamp) {
        this.camStamp = camStamp;
    }

    public List<VectorMerged> getVectorMergedList() {
        return vectorMergedList;
    }

    public void setVectorMergedList(List<VectorMerged> vectorMergedList) {
        this.vectorMergedList = vectorMergedList;
    }
}
