package database;


import model.VectorMerged;
import model.VectorAcc;
import model.VectorGyro;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.util.ArrayList;
import java.util.List;

public class InfluxClient {

    private final InfluxDBClient influxDBClient;
    private List<Point> pointList;

    /**
     * InfluxClient constructor for InfluxDB Version 2.0.
     * @param url url to the dashboard of the influxDB Website.
     * @param org your organisation name, retrivable from the dashboard on the InfluxDB Website.
     * @param bucket bucket name which represents your database container, also retrievable from the InfluxDB Website.
     * @param token your token which lets you access a specific bucket, also retrievable from the InfluxDB Website.
     *              Make sure that you have either a read/write token or an all access token.
     */
    public InfluxClient(String url, String org, String bucket, char[] token){
        influxDBClient = InfluxDBClientFactory.create(url, token, bucket, org);
        pointList = new ArrayList<>();
    }

    /**
     * InfluxClient constructor for older Versions of InfluxDB e.g Version 1.8
     * @param user username of the chronograf dashboard
     * @param passWord password for the chronograf dashboard
     * @param retentionPolicy your retention policy for the data you want to insert(put "" for default policy (30 days))
     * @param db the name of the database(make sure that you have write access)
     */
    public InfluxClient(String url, String user, char[] passWord, String retentionPolicy, String db){
        influxDBClient = InfluxDBClientFactory.createV1(url, user, passWord, db, retentionPolicy);
        pointList = new ArrayList<>();
    }


    /**
     * Writes data from the MPU9250 Gyroscope to the influx database.
     * @param vectorGyro the vector which contains gyro x, y, z and timestamp
     */
    public void writeDataGyro(VectorGyro vectorGyro){
        WriteApi writeApi = influxDBClient.getWriteApi();
        while(pointList.size() <= 2){
            Point point = Point.measurement("gyro-data")
                    .addTag("Position", "Value")
                    .addField("x", vectorGyro.getX())
                    .addField("y", vectorGyro.getY())
                    .addField("z", vectorGyro.getZ())
                    .time(vectorGyro.getTimeStamp(), WritePrecision.MS);

            pointList.add(point);
            System.out.println("Point added!");
        }
        writeApi.writePoints(pointList);
        System.out.println("Added Point List!");
        pointList.clear();
    }

    /**
     * Writes data from the MPU9250 Accelerometer to the InfluxDB.
     * @param vectorAcc the vector object which contains the x, y, z acceleration values
     */
    public void writeDataAcc(VectorAcc vectorAcc){
        WriteApi writeApi = influxDBClient.getWriteApi();
        while(pointList.size() <= 2){
            Point point = Point.measurement("acc-data")
                    .addTag("Position", "Value")
                    .addField("x", vectorAcc.getaX())
                    .addField("y", vectorAcc.getaY())
                    .addField("z", vectorAcc.getaZ())
                    .time(vectorAcc.getTimeStamp(), WritePrecision.MS);

            pointList.add(point);
            System.out.println("Point added!");
        }
        writeApi.writePoints(pointList);
        System.out.println("Added Point List!");
        pointList.clear();
    }

    /**
     * This function allows you to write both gyroscope and accelerometer at the same time.
     * @param gyroVector vector object with the gyroscope values
     * @param accVector vector object with the accelerometer values
     */
    public void writeDataGyroAndAcc(VectorGyro gyroVector, VectorAcc accVector){
        WriteApi writeApi = influxDBClient.getWriteApi();
        while(pointList.size() <= 2){
            Point point = Point.measurement("gyro-acc-data")
                    .addTag("Position", "Value")
                    .addField("gX", gyroVector.getX())
                    .addField("gY", gyroVector.getY())
                    .addField("gZ", gyroVector.getZ())
                    .addField("aX", accVector.getaX())
                    .addField("aY", accVector.getaY())
                    .addField("aZ", accVector.getaZ())
                    .time(gyroVector.getTimeStamp(), WritePrecision.MS);

            pointList.add(point);
            System.out.println("Point added!");
        }
        writeApi.writePoints(pointList);
        System.out.println("Added Point List!");
        pointList.clear();
    }

    public void writeDataVectorMerged(VectorMerged vectorMerged){
        WriteApi writeApi = influxDBClient.getWriteApi();
        while(pointList.size() <= 1){
            Point point = Point.measurement("gyro-acc-data")
                    .addTag("Position", "Value")
                    .addField("gX", vectorMerged.getgX())
                    .addField("gY", vectorMerged.getgY())
                    .addField("gZ", vectorMerged.getgZ())
                    .addField("aX", vectorMerged.getaX())
                    .addField("aY", vectorMerged.getaY())
                    .addField("aZ", vectorMerged.getaZ())
                    .addField("camX", vectorMerged.getCamX())
                    .addField("camY", vectorMerged.getCamY())
                    .addField("camZ", vectorMerged.getCamZ())
                    .time(vectorMerged.getSensorTime(), WritePrecision.MS);

            pointList.add(point);
//            System.out.println("Point added!");
        }
        writeApi.writePoints(pointList);
//        System.out.println("Added Point List!");
        pointList.clear();
    }

    /**
     * Method to close the InfluxDB Connection.
     */
    public void closeConnection(){
        influxDBClient.close();
    }

    /**
     * Method to retrive the data from the bucket, this works on InfluxDB
     * Version 2.0 and retrieves all Data.
     */
    public void retrieveData(){
        String flux = "from(bucket:\"test3\") |> range(start: 0)";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }
    }

}
