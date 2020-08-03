package model;

import java.util.ArrayList;
import java.util.List;

public class VectorGyro {
    private float gX;
    private float gY;
    private float gZ;
    private long timeStamp;
    private List<VectorGyro> vector3List;

    public VectorGyro(float gX, float gY, float gZ, long timeStamp) {
        this.gX = gX;
        this.gY = gY;
        this.gZ = gZ;
        this.timeStamp = timeStamp;
        vector3List = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("x: ").append(this.gX).append(" ");
        sb.append("y: ").append(this.gY).append(" ");
        sb.append("z: ").append(this.gZ).append("\n");
        return sb.toString();
    }

    public float getX() {
        return gX;
    }

    public void setX(float x) {
        this.gX = x;
    }

    public float getY() {
        return gY;
    }

    public void setY(float y) {
        this.gY = y;
    }

    public float getZ() {
        return gZ;
    }

    public void setZ(float z) {
        this.gZ = z;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
