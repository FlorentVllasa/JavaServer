package datacollection;

import interfaces.SensorDataCollector;
import model.VectorCamera;
import model.VectorMerged;
import model.VectorAcc;
import model.VectorGyro;
import org.eclipse.paho.client.mqttv3.MqttException;
import utils.DataFilter;
import utils.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ESP32Collector implements SensorDataCollector {
    private JsonParser jsonParser = new JsonParser();
    private DataFilter dataFilter = new DataFilter();

    private int port;
    private int bufferSize;
    private InetAddress inetAddress;

    private static String mqttMessage;

    private boolean isInStartZone = false;
    private boolean hasMoved = false;


    public ESP32Collector(int port, int bufferSize) throws IOException, MqttException {
        this.port = port;
        this.bufferSize = bufferSize;

    }

    public ESP32Collector(InetAddress inetAddress, int port) throws IOException, MqttException {
        this.inetAddress = inetAddress;
        this.port = port;

    }

    /**
     * This Method annotates positiondata from the camerasystem with sensordata. This happens
     * only if the platform moves outside the starting zone.
     * @return List of VectorMerged Objects that were collected during the datacollection phase.
     * @throws IOException
     */
    @Override
    public List<VectorMerged> collectData() throws IOException {
        List<VectorMerged> vectorMergedList = new ArrayList<>();
        while(mqttMessage == null){
            System.out.println("No MQTT-Message there!");
        }
        System.out.println("Mqtt-Message arrived! Waiting for Server Connection...");
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println("Connection established! Listening for sensor Client...");
        boolean hasMoved = false;
        while(true){
            Socket connectionSocket = null;
            VectorCamera vectorCamera = jsonParser.receiveDataAsCamVector(mqttMessage);
            boolean isInStartZone = vectorCamera.getCamY() >= 1.3 && vectorCamera.getCamX() >= 4;
            if(isInStartZone && hasMoved){
                System.out.println("End reached!" + vectorCamera.getCamY());
                break;
            }else if(isInStartZone){
                System.out.println("Startzone! " + vectorCamera.getCamY());
            }else{
                hasMoved = true;
                connectionSocket = welcomeSocket.accept();
                ByteBuffer bf = ByteBuffer.allocate(bufferSize);
                BufferedInputStream inFromClient = new BufferedInputStream(connectionSocket.getInputStream());
                fillBuffer(bf, inFromClient);
                String str = new String(bf.array());
                VectorGyro vectorGyro = dataFilter.vectorCompileGyro(str);
                VectorAcc vectorAcc = dataFilter.vectorCompileAcc(str);
                VectorMerged vectorMerged = new VectorMerged(vectorGyro, vectorAcc, vectorCamera);
                vectorMergedList.add(vectorMerged);

                connectionSocket.close();
            }
        }
        return vectorMergedList;
    }

    @Override
    public List<VectorMerged> processData(List<VectorMerged> collectedData) {
        return null;
    }


    /**
     * Fills the Buffer with the filtered content of the Inputstream
     * @param byteBuffer
     * @param bufferedInputStream
     * @throws IOException
     */
    public void fillBuffer(ByteBuffer byteBuffer, BufferedInputStream bufferedInputStream) throws IOException {
        while (true) {
            int b = bufferedInputStream.read();
            if (b == -1) {
                break;
            }
            byteBuffer.put( (byte) b);
        }
    }

    /**
     * This Method receives the MQTT-Message arriving in the MQTT-Collector class.
     * @param mqttMessage MQTT-Message that arrived in the MQTT-Collector class
     */
    public static void setMQTTMessage(String mqttMessage){
        ESP32Collector.mqttMessage = mqttMessage;
    }

    public static String getMqttMessage() {
        return mqttMessage;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

}

//System.out.println("gX: " + vectorMerged.getgX() + " gY: " + vectorMerged.getgY()
//        + " gZ: " + vectorMerged.getgZ() + "Stamp: " + vectorMerged.getSensorTime() +
//        " | " + "aX: " + vectorMerged.getaX() + " aY: " + vectorMerged.getaY()
//        + " aZ: " + vectorMerged.getaZ() + " | " + " camX: " + vectorCamera.getCamX() +
//        " camY: " + vectorCamera.getCamY() + " camZ: " + vectorCamera.getCamZ());
