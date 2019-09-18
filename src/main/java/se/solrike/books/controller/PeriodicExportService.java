package se.solrike.books.controller;

import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import se.solrike.books.BooksApplication;

@Component
public class PeriodicExportService {

  private LockRegistry mLockRegistry;

  @Autowired
  public PeriodicExportService(LockRegistry lockRegistry) {
    mLockRegistry = lockRegistry;
  }

  // export "job" shall only run from one node at the time in a cluster
  // hence a global lock needs to be taken before the job can start
  @Scheduled(fixedRate = 5000) // 5000 msec
  public void export() {
    Lock lock = mLockRegistry.obtain("myGlobalLockId");
    if (lock.tryLock()) {
      try {
        // manipulate protected state
        System.out.println(BooksApplication.CLIENT_ID + " doing heavy export of data to some external system");

//      } catch (InterruptedException e) {
//        // give up
//        Thread.currentThread().interrupt();
      } finally {
        lock.unlock();
      }
    } else {
      // someone else has the lock so just skip
      System.err.println("someone else has the lock so just skip");
    }
  }

}
