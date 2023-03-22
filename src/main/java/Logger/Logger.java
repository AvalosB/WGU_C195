package Logger;

import java.io.*;

public class Logger {
    public static void LogInLogger(String log) throws IOException{
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        File file = new File("login_activity.txt");
        try{
            fw = new FileWriter(file, true);
            fw.write(log + "\n");
            fw.close();
        } catch (Exception e){
            e.getMessage();
        }

    }
}
