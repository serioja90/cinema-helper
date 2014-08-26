/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import workers.FilmsParsingWorker;

/**
 *
 * @author sergiu
 */
public class ApplicationContextListener implements ServletContextListener {
  private ScheduledExecutorService scheduler;
  private static ServletContext context;
  
  public static ServletContext getServletContext(){ return context; }
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Logger.setLogLevel(Logger.DEBUG);
    context = sce.getServletContext();
    String filmsParsingInterval = sce.getServletContext().getInitParameter("films-parsing-worker-interval");
    Logger.info("Application started!");
    scheduler = Executors.newSingleThreadScheduledExecutor();
    Logger.info("Films will be parsed once every " + filmsParsingInterval + " seconds");
    scheduler.scheduleAtFixedRate(
      new FilmsParsingWorker(sce.getServletContext()), 
      0,
      filmsParsingInterval == null ? 300 : Integer.parseInt(filmsParsingInterval),
      TimeUnit.SECONDS
    );
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    scheduler.shutdown();
    Logger.info("Application stopped!");
    Logger.close();
  }
}
