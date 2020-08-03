package utils;


import model.VectorAcc;
import model.VectorGyro;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFilter {

    private final String xGyroRegex = "(?<=gx:)(.*?)(?=G)";
    private final String yGyroRegex = "(?<=gy:)(.*?)(?=G)";
    private final String zGyroRegex = "(?<=gz:)(.*?)(?=A)";

    private final String xAccRegex = "(?<=ax:)(.*?)(?=A)";
    private final String yAccRegex = "(?<=ay:)(.*?)(?=A)";
    private final String zAccRegex = "(?<=az:).*";

    public VectorGyro vectorCompileGyro(String data){

        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;
        long timeStamp = Instant.now().toEpochMilli();

        Pattern xPattern = Pattern.compile(xGyroRegex);
        Pattern yPattern = Pattern.compile(yGyroRegex);
        Pattern zPattern = Pattern.compile(zGyroRegex);

        Matcher matcherX = xPattern.matcher(data);
        Matcher matcherY = yPattern.matcher(data);
        Matcher matcherZ = zPattern.matcher(data);


        if(matcherX.find()){
            x = new Float(matcherX.group(0));
        }

        if(matcherY.find()){
            y = new Float(matcherY.group(0));
        }

        if(matcherZ.find()){
            z = new Float(matcherZ.group(0));
        }

        VectorGyro vector = new VectorGyro(x, y, z, timeStamp);
        return vector;
    }

    public VectorAcc vectorCompileAcc(String data){
        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;


        Pattern xPattern = Pattern.compile(xAccRegex);
        Pattern yPattern = Pattern.compile(yAccRegex);
        Pattern zPattern = Pattern.compile(zAccRegex);

        Matcher matcherX = xPattern.matcher(data);
        Matcher matcherY = yPattern.matcher(data);
        Matcher matcherZ = zPattern.matcher(data);


        if(matcherX.find()){
            x = new Float(matcherX.group(0));
        }

        if(matcherY.find()){
            y = new Float(matcherY.group(0));
        }

        if(matcherZ.find()){
            z = new Float(matcherZ.group(0));
        }
        VectorAcc vector = new VectorAcc(x, y, z, 0);
        return vector;
    }

}
