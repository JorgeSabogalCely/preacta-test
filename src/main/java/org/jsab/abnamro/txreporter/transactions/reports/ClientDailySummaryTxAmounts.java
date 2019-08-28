package org.jsab.abnamro.txreporter.transactions.reports;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jsab.abnamro.txreporter.transactions.ProcessedFutureTransacion;
import org.jsab.abnamro.txreporter.transactions.TransactionProductInfo;

/**
 *
 * @author JorgeLuis
 */
public class ClientDailySummaryTxAmounts implements Serializable
{
  private static final long serialVersionUID = -4897328010801650547L;

  private final Map<TransactionProductInfo, SummaryTransactionsAmount> products = new HashMap<>();

  public Map<TransactionProductInfo, SummaryTransactionsAmount> getTransactionProducts()
  {
    return Collections.unmodifiableMap(products);
  }

  Map<TransactionProductInfo, SummaryTransactionsAmount> getProductsMap()
  {
    return products;
  }

  void addTransactionAmount(ProcessedFutureTransacion transaction)
  {
    if (null != transaction) {
      TransactionProductInfo myTxProductInfo = transaction.getProductInfo();

      SummaryTransactionsAmount myProductTxAmount = products.get(myTxProductInfo);
      if (null == myProductTxAmount) {
        myProductTxAmount = new SummaryTransactionsAmount();
        products.put(myTxProductInfo, myProductTxAmount);
      }

      myProductTxAmount.addTransactionAmount(transaction.getQuantityLong() - transaction.getQuantityShort());
    }
  }

  @Override
  public String toString()
  {
    return products.toString();
  }

  public static class SummaryTransactionsAmount implements Serializable
  {

    private static final long serialVersionUID = -4897328010801650547L;

    private long amount;

    void addTransactionAmount(long newTxAmount)
    {
      amount += newTxAmount;
    }

    public long getTransactionsAmount()
    {
      return amount;
    }

    @Override
    public String toString()
    {
      return String.valueOf(amount);
    }
  }
}