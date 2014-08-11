/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import workers.NewsParsingWorker;

/**
 *
 * @author sergiu
 */
public class ApplicationContextListener implements ServletContextListener {
  private ScheduledExecutorService scheduler;
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Logger.info("Application started!");
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(new NewsParsingWorker(), 0, 300, TimeUnit.SECONDS);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    scheduler.shutdown();
    Logger.info("Application stopped!");
    Logger.close();
  }
}
