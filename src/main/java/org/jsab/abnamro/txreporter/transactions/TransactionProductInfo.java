package org.jsab.abnamro.txreporter.transactions;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;
import org.jsab.abnamro.txreporter.model.ExchangeProduct;

/**
 *
 * @author JorgeLuis
 */
public class TransactionProductInfo implements Serializable
{
  private static final long serialVersionUID = -6807542859808881823L;
  
  private ExchangeProduct product;
  private LocalDate expirationDate;

  private transient int hash = -1;
  
  public TransactionProductInfo(ExchangeProduct txProduct, LocalDate txExpirationDate)
  {
    if (null == txProduct) {
      throw new TxReporterArgumentException("Invalid exchange product in new Trasaction Product information");
    }
    if (null == txExpirationDate) {
      throw new TxReporterArgumentException("Invalid expiration date in new Trasaction Product information");
    }

    product = txProduct;
    expirationDate = txExpirationDate;
  }

  public final ExchangeProduct getProduct()
  {
    return product;
  }

  public final LocalDate getExpirationDate()
  {
    return expirationDate;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 7;
      myHash = 13 * myHash + product.hashCode();
      myHash = 13 * myHash + expirationDate.hashCode();
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
    
    TransactionProductInfo myOtherTxClient = (TransactionProductInfo) otherObject;
    return product.equals(myOtherTxClient.getProduct())
           && expirationDate.equals(myOtherTxClient.getExpirationDate());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("TransactionProductInfo[product:{0},expirationDate:{1}]", product, expirationDate);
  }
}