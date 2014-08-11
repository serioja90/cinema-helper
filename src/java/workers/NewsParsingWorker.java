package workers;

import utilities.Logger;

public class NewsParsingWorker implements Runnable {

  @Override
  public void run() {
    Logger.info("Going to parse news ...");
    new Thread(new GoogleNewsParser()).start();
  }
}
