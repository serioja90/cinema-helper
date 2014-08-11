package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.Singleton;

@Singleton
public class Logger {
  public static final int FATAL = 0;
  public static final int ERROR = 1;
  public static final int WARN = 2;
  public static final int INFO = 3;
  public static final int DEBUG = 4;
  protected static final String WEBINF = "WEB-INF";
  protected static final String[] logLevelNames = {"FATAL", "ERROR", "WARN", "INFO", "DEBUG"};
  protected static String logFilename;
  protected static PrintWriter logFile;
  protected static int logLevel = INFO;
  
  static {
    logFilename = getWebInfPath() + "/log/application.log";
    init();
  }
  
  public static synchronized void setLogFilename(String filename) throws FileNotFoundException, IOException {
    File file = new File(filename);
    Logger.close();
    if(!file.exists()){
      file.getParentFile().mkdirs();
      file.createNewFile();
    }
    logFilename = filename;
    logFile = new PrintWriter(new FileOutputStream(filename,true));
  }
  
  public static String getLogFilename(){
    return logFilename;
  }
  
  public static void setLogLevel(int level){
    if(level < FATAL){
      throw new IllegalArgumentException();
    }
    logLevel = level;
  }
  
  public static int getLogLevel(){
    return logLevel;
  }
  
  public static void fatal(String msg){
    if(logLevel >= FATAL) print(FATAL, msg);
  }
  
  public static void error(String msg){
    if(logLevel >= ERROR) print(ERROR, msg);
  }
  
  public static void warn(String msg){
    if(logLevel >= WARN) print(WARN, msg);
  }
  
  public static void info(String msg){
    if(logLevel >= INFO) print(INFO, msg);
  }
  
  public static void debug(String msg){
    if(logLevel >= DEBUG) print(DEBUG, msg);
  }
  
  public static synchronized void close(){
    if(logFile != null){
      logFile.flush();
      logFile.close();
    }
  }
  
  protected static synchronized void print(int level, String msg){
    logFile.append('[' + getTimestamp() + "][" + logLevelNames[level] + "] " + msg + "\n");
    logFile.flush();
  }
  
  protected static void init(){
    if(logFile == null){
      try{
        setLogFilename(logFilename);
      }catch(FileNotFoundException e){
        System.out.println(e.getMessage());
      }catch(IOException e){
        System.out.println(e.getMessage());
      }
    }
  }
  
  protected static String getTimestamp(){
    Date now = new Date();
    String timestamp = (new Timestamp(now.getTime())).toString();
    String nano = timestamp.split("\\.")[1];
    if(nano.length() < 3) timestamp += "0";
    return timestamp;
  }
  
  protected static String getWebInfPath(){
    URL url = Logger.class.getResource("Logger.class");
    String className = url.getFile();
    String filePath = className.substring(0,className.indexOf(WEBINF) + WEBINF.length());
    return filePath;
  }
}
