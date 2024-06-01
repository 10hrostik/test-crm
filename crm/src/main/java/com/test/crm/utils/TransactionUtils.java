package com.test.crm.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class TransactionUtils {

  public static void afterCommit(Runnable task) {
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
      @Override
      public void afterCommit() {
        task.run();
      }
    });
  }
}
