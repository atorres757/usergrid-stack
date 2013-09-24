/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.locking.zookeeper;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usergrid.locking.Lock;
import org.usergrid.locking.LockManager;
import org.usergrid.locking.LockPathBuilder;

import com.netflix.curator.RetryPolicy;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.recipes.locks.InterProcessMutex;
import com.netflix.curator.retry.ExponentialBackoffRetry;

/** Implementation for Zookeeper service that handles global locks. */
public final class ZooKeeperLockManagerImpl implements LockManager {

  private String hostUrl;

  private int sessionTimeout = 10000;

  private int maxAttempts = 5;

  private CuratorFramework client;

  public ZooKeeperLockManagerImpl(String hostUrl, int sessionTimeout, int maxAttemps) {
    this.hostUrl = hostUrl;
    this.sessionTimeout = sessionTimeout;
    this.maxAttempts = maxAttemps;
    init();
  }

  public ZooKeeperLockManagerImpl() {
  }

  @PostConstruct
  public void init() {
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(sessionTimeout, maxAttempts);
    client = CuratorFrameworkFactory.newClient(hostUrl, retryPolicy);
    client.start();


    try {
      setupPrefix();
    } catch (Exception e) {
      logger.error("Unable to validate zookeeper namespace", e);
      throw new RuntimeException("Unable to validate zookeeper namespace", e);
    }

    //add the shutdown hook to close the client
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        client.close();
      }
    }
    );


  }

  private void setupPrefix() throws Exception {

    int index = hostUrl.indexOf("/");

    /**
     * We dont' have a prefix
     */
    if (index == -1 || index == hostUrl.length() - 1) {
      return;
    }


    //we have a prefix, check if it exists.  If this is null, we need to create the prefix
    if (client.checkExists().forPath("/") != null) {
         return;
    }


    RetryPolicy retryPolicy = new ExponentialBackoffRetry(sessionTimeout, maxAttempts);
    CuratorFramework rootClient =  CuratorFrameworkFactory.newClient(hostUrl.substring(0, index), retryPolicy);
    rootClient.start();

    //now use the root client to create the namespace
    String namepsace = hostUrl.substring(index, hostUrl.length());

    rootClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(namepsace);

    rootClient.close();


  }

  protected static final Logger logger = LoggerFactory.getLogger(ZooKeeperLockManagerImpl.class);

  /*
   * (non-Javadoc)
   * 
   * @see org.usergrid.locking.LockManager#createLock(java.util.UUID,
   * java.lang.String[])
   */
  @Override
  public Lock createLock(UUID applicationId, String... path) {
    String lockPath = LockPathBuilder.buildPath(applicationId, path);


    return new ZookeeperLockImpl(new InterProcessMutex(client, lockPath));

  }

  @PreDestroy
  public void shutdown() {
    if (client == null) {
      return;
    }

    client.close();
  }

  public String getHostUrl() {
    return hostUrl;
  }

  public void setHostUrl(String hostUrl) {
    this.hostUrl = hostUrl;
  }

  public int getSessionTimeout() {
    return sessionTimeout;
  }

  public void setSessionTimeout(int sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  public int getMaxAttempts() {
    return maxAttempts;
  }

  public void setMaxAttempts(int maxAttemps) {
    this.maxAttempts = maxAttemps;
  }


}
