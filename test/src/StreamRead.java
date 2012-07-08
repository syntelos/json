
import json.ArrayJson;
import json.Json;
import json.ObjectJson;
import json.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 */
public class StreamRead
    extends Object
{
    /**
     * 
     */
    public static void Write(Json json, File file)
        throws IOException
    {
        byte[] data = json.toString().getBytes();

        FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(data,0,data.length);
        }
        finally {
            out.close();
        }
    }
    public static ArrayJson Read(File file)
        throws IOException
    {

        FileInputStream in = new FileInputStream(file);
        try {
            return new Reader().read(in);
        }
        finally {
            in.close();
        }
    }

    /**
     * 
     */
    public static void main(String[] argv){

        File file = new File("StreamRead.json");

        ArrayJson source = new ArrayJson();

        for (int cc = 0; cc < 3; cc++){

            ObjectJson object = new ObjectJson();

            object.setValue("index",cc);

            source.add(object);
        }

        try {
            Write(source,file);
        }
        catch (IOException exc){
            System.err.println(file.getAbsolutePath());
            exc.printStackTrace();
            System.exit(1);
        }


        ArrayJson target = null;
        try {
            target = Read(file);
        }
        catch (IOException exc){
            System.err.println(file.getAbsolutePath());
            exc.printStackTrace();
            System.exit(1);
        }

        if (target.equals(source)){

            System.exit(0);
        }
        else {
            System.err.println(target.toString());

            System.exit(1);
        }
    }
}
