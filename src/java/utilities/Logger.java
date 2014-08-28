package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.ejb.Singleton;
import sun.reflect.Reflection;

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
    logFilename = Tools.getWebInfPath() + "/log/application.log";
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
    String className = Reflection.getCallerClass().getSimpleName();
    if(logLevel >= FATAL) print(FATAL, className, msg);
  }
  
  public static void error(String msg){
    String className = Reflection.getCallerClass().getSimpleName();
    if(logLevel >= ERROR) print(ERROR, className, msg);
  }
  
  public static void warn(String msg){
    String className = Reflection.getCallerClass().getSimpleName();
    if(logLevel >= WARN) print(WARN, " " + className, msg);
  }
  
  public static void info(String msg){
    String className = Reflection.getCallerClass().getSimpleName();
    if(logLevel >= INFO) print(INFO, " " + className, msg);
  }
  
  public static void debug(String msg){
    String className = Reflection.getCallerClass().getSimpleName();
    if(logLevel >= DEBUG) print(DEBUG, className, msg);
  }
  
  public static synchronized void close(){
    if(logFile != null){
      logFile.flush();
      logFile.close();
    }
  }
  
  public static void reportException(Exception e){
    String className = Reflection.getCallerClass().getSimpleName();
    print(ERROR, className, e.toString());
    for (StackTraceElement item : e.getStackTrace()) {
      print(ERROR, null, "\t" + item.toString());
    }
  }
  
  protected static synchronized void print(int level, String className, String msg){
    logFile.append('[' + Tools.getTimestamp() + "][" + logLevelNames[level] + "] " + 
      (className == null ? "" : className + ": ") + msg + "\n"
    );
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
}
