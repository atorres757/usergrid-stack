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

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for uploading and updating files in ZooKeeper.
 *
 */
public class ZooPut implements Watcher {

  private static final Logger logger = LoggerFactory.getLogger(ZooPut.class);

	private ZooKeeper keeper;

	private boolean connected = false;

	public ZooPut(String host) throws IOException {
		keeper = new ZooKeeper(host, 10000, this);
	}

  /**
   * Wait until the client can successfully connect to the server
   */
  public void waitForInit(){
    synchronized (this) {
        try {
          this.wait(20000);
        } catch (InterruptedException e) {
          logger.error("Unable to wait for zookeeper to start", e);
        }
      if(! connected){
        throw new RuntimeException("Failed to connect to zookeeper. The server doesn't appear to be running correctly");
      }
    }

    logger.info("Zookeeper client successfully connected");
  }

	public void close() throws InterruptedException {
			keeper.close();
	}


	@Override
	public void process(WatchedEvent event) {
		// nocommit: consider how we want to accomplish this
		if (event.getState() == KeeperState.SyncConnected) {
			synchronized (this) {
				connected = true;
				this.notify();
			}
		}

	}

}
