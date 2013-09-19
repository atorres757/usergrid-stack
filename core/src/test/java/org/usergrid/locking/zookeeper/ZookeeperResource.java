package org.usergrid.locking.zookeeper;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usergrid.cassandra.AvailablePortFinder;
import org.usergrid.utils.UUIDUtils;

import java.io.File;
import java.net.InetSocketAddress;

/**
 *
 * Wrapper of zookeeper startup into a helper class
 * @author: tnine
 *
 */
public class ZookeeperResource extends ExternalResource {


  private static Logger logger = LoggerFactory.getLogger(ZookeeperResource.class);

//  /**
//   * We need a singleton pointer.  Otherwise we have issues with multiple instances of
//   * ZK starting and failing to bind to ports etc
//   *
//   */
  private static final ZookeeperResource instance = new ZookeeperResource();

  protected ZKServerMain zkServer;

  protected static File tmpDir = new File(String.format("./tmp/zookeeper/instance-%s", UUIDUtils.newTimeUUID()));

  private static final int  DEFAULT_CLIENT_PORT = 20181;

  public static final int  RUNNING_PORT = AvailablePortFinder.getNextAvailable(DEFAULT_CLIENT_PORT
      + RandomUtils.nextInt(1000));

  public static final String ZOO_KEEPER_HOST_URL = String.format("localhost:%d/", RUNNING_PORT);


  private ZookeeperResource(){
  }

  public static ZookeeperResource getInstance(){

//    ZookeeperResource instance = new ZookeeperResource();

    try {
      instance.maybestart();
    } catch (Exception e) {
      throw new RuntimeException("Unable to start zk");
    }

    return instance;
  }


  public void maybestart() throws Exception {
    if(zkServer != null){
      return;
    }

    synchronized(this){
      if(zkServer != null){
        return;
      }

      start();
    }
  }

  private void start() throws Exception {

    if(zkServer != null){
      logger.info("A zookeeper server is already running.  Stop this instance before starting a new one");
    }

    zkServer = new ZKServerMain();

    // we don't call super.setUp
    System.setProperty("zookeeper.url", ZOO_KEEPER_HOST_URL);

    ZooStartThread zooThread = new ZooStartThread(zkServer);
    zooThread.setDaemon(true);
    zooThread.start();

    waitForClientConnect();

    if(zooThread.isShutdown){
      //TODO T.N. we can't blow up here with a runtime exception, even though we should.  It kills
      //parallel JVM tests
      logger.error("Unable to start zookeeper.  Check the log for details");
    }

    logger.info("Zookeeper initialized.");
  }

  @Override
  protected void before() throws Throwable {
    maybestart();
  }

//  @Override
//  protected void after() {
//    shutdown();
//  }

  /**
   * Creates a zk client and waits for successful connection to the server
   * @throws Exception
   */
  public void waitForClientConnect() throws Exception{
    ZooPut zooPut = new ZooPut(ZOO_KEEPER_HOST_URL.substring(0,
        ZOO_KEEPER_HOST_URL.indexOf('/')));

    zooPut.waitForInit();

    zooPut.close();
  }

  public void shutdown() {

    if(zkServer == null){
      logger.info("Zookeeper is already shut down");
      return;
    }

    zkServer.shutdown();

    // Remove test data.
    boolean deletedData = recurseDelete(tmpDir);

    if (!deletedData) {
      logger.warn("Zk testing data was not removed properly. You need to"
          + "manually remove: {}" ,  tmpDir.getAbsolutePath());
    }
  }

  private static boolean recurseDelete(File f) {
    if (f.isDirectory()) {
      for (File sub : f.listFiles()) {
        if (!recurseDelete(sub)) {
          return false;
        }
      }
    }
    return f.delete();
  }


  private static class ZKServerMain extends ZooKeeperServerMain {
    @Override
    public void shutdown() {
      super.shutdown();
    }
  }

  private static class ZooStartThread extends Thread{

    public boolean isShutdown = false;

    private ZKServerMain zkServer;

    private ZooStartThread(ZKServerMain zkServer){
      this.zkServer = zkServer;
    }

    @Override
    public void run() {

      ServerConfig config = new ServerConfig() {
        {
          clientPortAddress = new InetSocketAddress("localhost",
              RUNNING_PORT);
          dataDir = tmpDir.getAbsolutePath();
          dataLogDir = dataDir;

        }
      };

      try {
        zkServer.runFromConfig(config);
        //occasionally during port conflicts ZK will fail to start correctly and just exit.  Unfortunately
        //we can't tell the difference b/t a clean exit and a failed start.  If we start correctly, the above
        //call will block, and we wont' reach this until shutdown.  In which case we won't care anyway.
        isShutdown = true;
        logger.info("ZOOKEEPER EXIT");
      } catch (Throwable e) {
        logger.error("Unable to start zookeeper", e);
        isShutdown = true;
      }


    }
  }

}
