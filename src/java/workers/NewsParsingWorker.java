package workers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utilities.Logger;

public class NewsParsingWorker implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Logger.info("Servlet initialized:");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Logger.info("Servlet destroyed:");
    Logger.close();
  }
}
