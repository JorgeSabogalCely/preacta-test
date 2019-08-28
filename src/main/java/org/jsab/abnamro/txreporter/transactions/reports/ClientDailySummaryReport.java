package org.jsab.abnamro.txreporter.transactions.reports;

import org.jsab.abnamro.txreporter.transactions.ProcessedFutureTransacion;
import org.jsab.abnamro.txreporter.transactions.TransactionClientInfo;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jsab.abnamro.txreporter.engine.FormattableReport;
import org.jsab.abnamro.txreporter.engine.ReportFormatter;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class ClientDailySummaryReport implements FormattableReport, Serializable
{
  private static final long serialVersionUID = -1897284797190536944L;
  
  private final Map<TransactionClientInfo, ClientDailySummaryTxAmounts> clients = new HashMap<>(100);
  
  @Override
  public String getName()
  {
    return "client-daily-summary-report";
  }
  
  public Map<TransactionClientInfo, ClientDailySummaryTxAmounts> getTransactionClients()
  {
    return Collections.unmodifiableMap(clients);
  }
  
  public int getSize()
  {
    return clients.size();
  }

  public void addTransactionAmount(ProcessedFutureTransacion transaction)
  {
    if (null == transaction) {
      throw new TxReporterArgumentException("Invalid processed future transaction");
    }
    
    TransactionClientInfo myClientInfo = transaction.getClientInfo();
    
    // Looks for transactions amount per product for current client
    ClientDailySummaryTxAmounts myProductTxAmounts = clients.get(myClientInfo);
    if (null == myProductTxAmounts) {
      myProductTxAmounts = new ClientDailySummaryTxAmounts();
      clients.put(myClientInfo, myProductTxAmounts);
    }
    
    // Adds transaction amount for product in transaction
    myProductTxAmounts.addTransactionAmount(transaction);
  }

  @Override
  public String toString()
  {
    return clients.toString();
  }

  @Override
  public void toFormattedData(ReportFormatter formatter)
  {
    // appends header names
    formatter.headerStart()
             .value("Client_Information")
             .fieldSeparator()
             .value("Product_Information")
             .fieldSeparator()
             .value("Total_Transaction_Amount")
             .headerEnd();
    
    // Appends report contents
    clients.forEach((clientInfo, productAmounts) -> {
      appendEntry(formatter, clientInfo, productAmounts);
    });
  }
  
  private void appendEntry(ReportFormatter formatter, TransactionClientInfo clientInfo,
                           ClientDailySummaryTxAmounts productTxAmounts)
  {
    productTxAmounts.getProductsMap().forEach((txProductInfo, summaryTxAmount) -> {
      // starts entry
      formatter.entryStart();
      
      // Adds value transaction client information field
      formatter.value(clientInfo.getClient().getType())
               .fieldSectionSeparator()
               .value(clientInfo.getClient().getNumber())
               .fieldSectionSeparator()
               .value(clientInfo.getAccount().getMainAccountNumber())
               .fieldSectionSeparator()
               .value(clientInfo.getAccount().getSubaccountNumber());
      
      formatter.fieldSeparator();
      
      //Adds value for transaction product information field
      formatter.value(txProductInfo.getProduct().getGroupCode())
               .fieldSectionSeparator()
               .value(txProductInfo.getProduct().getExchangeCode())
               .fieldSectionSeparator()
               .value(txProductInfo.getProduct().getSymbol())
               .fieldSectionSeparator()
               .value(txProductInfo.getExpirationDate());
      
      formatter.fieldSeparator();

      //Adds value for total  transaction amount
      formatter.value(summaryTxAmount.getTransactionsAmount());
      
      // ends entry
      formatter.entryEnd();
    });
  }
}