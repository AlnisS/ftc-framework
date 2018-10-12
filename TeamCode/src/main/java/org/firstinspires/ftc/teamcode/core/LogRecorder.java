package org.firstinspires.ftc.teamcode.core;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public class LogRecorder {
    public static void writeLog() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec("logcat -d").getInputStream()));
            StringBuilder log = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("team-code-")) {
                    log.append(line);
                    log.append("\n");
                }
            }

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "team-code-logs");

            if(!dir.exists()) {
                dir.mkdirs();
            }

            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(
                    new File(dir, "logcat.txt")));

            osw.write(log.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            Log.e("team-code-log-error", getStackTrace(e));
        }
    }
}
