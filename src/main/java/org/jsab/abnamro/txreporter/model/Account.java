package org.jsab.abnamro.txreporter.model;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class Account implements Serializable
{
  private static final long serialVersionUID = -7503625791316350350L;

  private int mainAccountNumber;
  private int subaccountNumber;

  private transient int hash = -1;
  
  public Account(int mainAccountNumber, int subaccountNumber)
  {
    if (mainAccountNumber <= 0 || subaccountNumber <= 0) {
      throw new TxReporterArgumentException("Invalid main account number in new Account information");
    }
    if (mainAccountNumber <= 0 || subaccountNumber <= 0) {
      throw new TxReporterArgumentException("Invalid subaccount number in new Account information");
    }

    this.mainAccountNumber = mainAccountNumber;
    this.subaccountNumber = subaccountNumber;
  }

  public final int getMainAccountNumber()
  {
    return mainAccountNumber;
  }

  public final int getSubaccountNumber()
  {
    return subaccountNumber;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 5;
      myHash = 67 * myHash + mainAccountNumber;
      myHash = 67 * myHash + subaccountNumber;
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
    
    Account myOtherAccount = (Account) otherObject;
    return mainAccountNumber == myOtherAccount.getMainAccountNumber()
           && subaccountNumber == myOtherAccount.getSubaccountNumber();
  }
  
  @Override
  public String toString()
  {
    return MessageFormat.format("Account[mainAccountNumber:{0,number,#},subAccountNumber:{1,number,#}]", mainAccountNumber, subaccountNumber);
  }
}