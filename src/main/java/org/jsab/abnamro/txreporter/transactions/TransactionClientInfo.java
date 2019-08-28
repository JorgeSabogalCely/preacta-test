package org.jsab.abnamro.txreporter.transactions;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;
import org.jsab.abnamro.txreporter.model.Account;
import org.jsab.abnamro.txreporter.model.Client;

/**
 *
 * @author JorgeLuis
 */
public class TransactionClientInfo implements Serializable
{
  private static final long serialVersionUID = -289759686265276458L;
  
  private Client client;
  private Account account;

  private transient int hash = -1;
  
  public TransactionClientInfo(Client txClient, Account txAccount)
  {
    if (null == txClient) {
      throw new TxReporterArgumentException("Invalid client in new Trasaction Client information");
    }
    if (null == txAccount) {
      throw new TxReporterArgumentException("Invalid account in new Trasaction Client information");
    }

    client = txClient;
    account = txAccount;
  }
  
  public final Client getClient()
  {
    return client;
  }

  public final Account getAccount()
  {
    return account;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 3;
      myHash = 37 * myHash + client.hashCode();
      myHash = 37 * myHash + account.hashCode();
      hash = myHash;
    }

    return hash;
  }

  @Override
  public boolean equals(Object otherObject)
  {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    
    TransactionClientInfo myOtherTxClient = (TransactionClientInfo) otherObject;
    return client.equals(myOtherTxClient.getClient()) && account.equals(myOtherTxClient.getAccount());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("TransactionClientInfo[client:{0},account:{1}]", client, account);
  }
}