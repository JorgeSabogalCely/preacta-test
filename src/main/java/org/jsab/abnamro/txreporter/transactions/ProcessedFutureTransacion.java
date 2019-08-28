package org.jsab.abnamro.txreporter.transactions;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class ProcessedFutureTransacion implements Serializable
{
  private static final long serialVersionUID = -1433871953250196751L;

  private final TransactionClientInfo clientInfo;
  private final TransactionProductInfo productInfo;
  private final long quantityLong;
  private final long quantityShort;
  
  private transient int hash = -1;

  public ProcessedFutureTransacion(TransactionClientInfo txClientInfo, TransactionProductInfo txProductInfo,
                                   long txQuantityLong, long txQuantityShort)
  {
    if (null == txClientInfo) {
      throw new TxReporterArgumentException("Invalid client information in new Processed Future Transaction information");
    }
    if (null == txProductInfo) {
      throw new TxReporterArgumentException("Invalid product information in new Processed Future Transaction information");
    }

    clientInfo = txClientInfo;
    productInfo = txProductInfo;
    quantityLong = txQuantityLong;
    quantityShort = txQuantityShort;
  }

  public final TransactionClientInfo getClientInfo()
  {
    return clientInfo;
  }

  public final TransactionProductInfo getProductInfo()
  {
    return productInfo;
  }

  public final long getQuantityLong()
  {
    return quantityLong;
  }

  public final long getQuantityShort()
  {
    return quantityShort;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 7;
      myHash = 17 * myHash + clientInfo.hashCode();
      myHash = 17 * myHash + productInfo.hashCode();
      myHash = 17 * myHash + Long.hashCode(quantityLong);
      myHash = 17 * myHash + Long.hashCode(quantityShort);
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
    
    ProcessedFutureTransacion myOtherTx = (ProcessedFutureTransacion) otherObject;
    return clientInfo.equals(myOtherTx.getClientInfo()) && productInfo.equals(myOtherTx.getProductInfo())
           && quantityLong == myOtherTx.getQuantityLong() && quantityShort == myOtherTx.getQuantityLong();
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("ProcessedFutureTransacion[clientInfo:{0},productInfo:{1},quantityShort:{2,number,#},quantityShort:{3,number,#}]",
               clientInfo, productInfo, quantityLong, quantityShort);
  }
}