package model;

public class VectorAcc {

    private float aX;
    private float aY;
    private float aZ;
    private long timeStamp;

    public VectorAcc(float aX, float aY, float aZ, long timeStamp) {
        this.aX = aX;
        this.aY = aY;
        this.aZ = aZ;
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
