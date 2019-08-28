package org.jsab.abnamro.txreporter.model;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class ExchangeProduct implements Serializable
{
  private static final long serialVersionUID = -4321775782520845360L;

  private final String groupCode;
  private final String exchangeCode;
  private final String symbol;

  private transient int hash = -1;

  public ExchangeProduct(String productGroupCode, String productExchangeCode, String productSymbol)
  {
    if (null == productGroupCode || productGroupCode.isEmpty()) {
      throw new TxReporterArgumentException("Invalid group code in new Product information");
    }
    if (null == productExchangeCode || productExchangeCode.isEmpty()) {
      throw new TxReporterArgumentException("Invalid exchange code in new Product information");
    }
    if (null == productSymbol || productSymbol.isEmpty()) {
      throw new TxReporterArgumentException("Invalid simbol in new Product information");
    }

    groupCode = productGroupCode;
    exchangeCode = productExchangeCode;
    symbol = productSymbol;
  }

  public final String getGroupCode()
  {
    return groupCode;
  }

  public final String getExchangeCode()
  {
    return exchangeCode;
  }

  public final String getSymbol()
  {
    return symbol;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 5;
      myHash = 11 * myHash + groupCode.hashCode();
      myHash = 11 * myHash + exchangeCode.hashCode();
      myHash = 11 * myHash + symbol.hashCode();
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
    
    ExchangeProduct myOtherClient = (ExchangeProduct) otherObject;
    return groupCode.equals(myOtherClient.getGroupCode()) && exchangeCode.equals(myOtherClient.getExchangeCode())
           && symbol.equals(myOtherClient.getSymbol());
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("ExchangeProduct[groupCode:{0},exchangeCode:{1},symbol:{2}]",
               groupCode, exchangeCode, symbol);
  }
}