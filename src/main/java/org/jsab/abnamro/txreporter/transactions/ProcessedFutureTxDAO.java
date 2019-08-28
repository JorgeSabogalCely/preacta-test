package org.jsab.abnamro.txreporter.transactions;

import org.jsab.abnamro.txreporter.transactions.reports.ClientDailySummaryReport;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jsab.abnamro.txreporter.model.Account;
import org.jsab.abnamro.txreporter.model.Client;
import org.jsab.abnamro.txreporter.model.ClientType;
import org.jsab.abnamro.txreporter.exceptions.TxReporterAppException;
import org.jsab.abnamro.txreporter.model.ExchangeProduct;

import static org.jsab.abnamro.txreporter.TxReportsConstants.*;
import static org.jsab.abnamro.txreporter.transactions.TransactionsConstants.*;

/**
 *
 * @author JorgeLuis
 */
public class ProcessedFutureTxDAO
{
  private static final Logger LOGGER = Logger.getLogger(ProcessedFutureTxDAO.class.getName());
  
  private static volatile ProcessedFutureTxDAO instance;

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

  private ProcessedFutureTxDAO()
  {
  }

  /**
   * Returns unique instance of this DAO.
   *
   * @return
   */
  public static ProcessedFutureTxDAO getInstance()
  {
    ProcessedFutureTxDAO myDAOInstance = instance;
    if (myDAOInstance == null) {
      synchronized (ProcessedFutureTxDAO.class) {
        myDAOInstance = instance;
        if (null == myDAOInstance) {
          instance = myDAOInstance = new ProcessedFutureTxDAO();
        }
      }
    }
    return myDAOInstance;
  }

  public ClientDailySummaryReport generateDailySummaryReport()
  {
    return buildDailySummaryReport(null);
  }

  public ClientDailySummaryReport generateDailySummaryReport(String sourceDataFilePath)
  {
    return buildDailySummaryReport(sourceDataFilePath);
  }

  private ClientDailySummaryReport buildDailySummaryReport(String sourceDataFilePath)
  {
    ClientDailySummaryReport mySummaryReport = null;
    
    try {
      ResourceBundle myDataFileDefinitions = getDataFileProps();
      Path myFinalDataFilePath = getFinalSourceDataFilePath(myDataFileDefinitions, sourceDataFilePath);
      mySummaryReport = buildDailySummaryReport(myDataFileDefinitions, myFinalDataFilePath);
    }
    catch (TxReporterAppException e) {
      LOGGER.log(Level.SEVERE, "Error while processing entries in specified data-file", e);
      throw new TxReporterAppException("Client daily summary report could not be generated");
    }
    
    return mySummaryReport;
  }
  
  public List<ProcessedFutureTransacion> getProcessedFutureTransactions()
  {
    return buildProcessedFutureTransactions(null);
  }
          
  public List<ProcessedFutureTransacion> getProcessedFutureTransactions(String sourceDataFilePath)
  {
    return buildProcessedFutureTransactions(sourceDataFilePath);
  }

  private List<ProcessedFutureTransacion> buildProcessedFutureTransactions(String sourceDataFilePath)
  {
    List<ProcessedFutureTransacion> myTransactions = null;
    
    try {
      ResourceBundle myDataFileDefinitions = getDataFileProps();
      Path myFinalDataFilePath = getFinalSourceDataFilePath(myDataFileDefinitions, sourceDataFilePath);
      myTransactions = loadTxFromDataFile(myDataFileDefinitions, myFinalDataFilePath);
    }
    catch (TxReporterAppException e) {
      LOGGER.log(Level.SEVERE, "Error while processing entries in default data-file", e);
    }
    catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error while reading default data-file", e);
    }
    
    return myTransactions != null ? myTransactions : Collections.emptyList();
  }

  private Path getFinalSourceDataFilePath(ResourceBundle dataFileDefinitions, String sourceDataFilePath)
  {
    Path myFinalDataFilePath;
    
    if (null == sourceDataFilePath || sourceDataFilePath.isEmpty()) {
      String myDefaultDataDir = dataFileDefinitions.getString(KEY_DEFAULT_DATA_DIR);
      String myDefaultFilename = dataFileDefinitions.getString(KEY_DEFAULT_INPUT_DATA_FILENAME);
      myFinalDataFilePath = Paths.get(myDefaultDataDir, myDefaultFilename);
    }
    else {
      myFinalDataFilePath = Paths.get(sourceDataFilePath);
    }

    return myFinalDataFilePath;
  }

  private ClientDailySummaryReport buildDailySummaryReport(ResourceBundle dataFileDefinitions, Path dataFilePath)
  {
    ClientDailySummaryReport mySummaryReport = new ClientDailySummaryReport();

    try (BufferedReader myReader = Files.newBufferedReader(dataFilePath)) {
      myReader.lines().forEach(it -> {
        mySummaryReport.addTransactionAmount(buildProcessedFutureTx(dataFileDefinitions, it));
      });
    }
    catch (IOException e) {
      throw new TxReporterAppException("Error while reading data-file definitions", e);
    }
    
    return mySummaryReport;
  }
  
  private List<ProcessedFutureTransacion> loadTxFromDataFile(ResourceBundle dataFileDefinitions, Path dataFilePath)
  {
    List<ProcessedFutureTransacion> myTransactions = null;
    
    try (BufferedReader myReader = Files.newBufferedReader(dataFilePath)) {
       myTransactions = 
           myReader.lines().map(it -> buildProcessedFutureTx(dataFileDefinitions, it)).collect(Collectors.toList());
    }
    catch (IOException e) {
      throw new TxReporterAppException("Error while reading data-file definitions", e);
    }
    
    return myTransactions;
  }

  private ResourceBundle getDataFileProps()
  {
    try {
      return ResourceBundle.getBundle(DATA_DEFINITIONS_FILE);
    }
    catch (Exception e) {
      throw new TxReporterAppException("Invalid data file definition properties", e);
    }
  }

  private ProcessedFutureTransacion buildProcessedFutureTx(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    TransactionClientInfo myClientInfo = buildTxClientInfo(dataFileDefinitions, dataEntry);
    TransactionProductInfo myProductInfo = buildTxProductInfo(dataFileDefinitions, dataEntry);

    long myQuantityLong = getFieldLongValue(dataFileDefinitions, KEY_QUANTITY_LONG, dataEntry);
    String myQuantityLongSign = getFieldStringValue(dataFileDefinitions, KEY_QUANTITY_LONG_SIGN, dataEntry);
    if ("-".equals(myQuantityLongSign)) {
      myQuantityLong *= (-1);
    }

    long myQuantityShort = getFieldLongValue(dataFileDefinitions, KEY_QUANTITY_SHORT, dataEntry);
    String myQuantityShortSign = getFieldStringValue(dataFileDefinitions, KEY_QUANTITY_SHORT_SIGN, dataEntry);
    if ("-".equals(myQuantityShortSign)) {
      myQuantityShort *= (-1);
    }

    return new ProcessedFutureTransacion(myClientInfo, myProductInfo, myQuantityLong, myQuantityShort);
  }

  private TransactionClientInfo buildTxClientInfo(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    Client myClient = buildClient(dataFileDefinitions, dataEntry);
    Account myAccount = buildAccount(dataFileDefinitions, dataEntry);

    return new TransactionClientInfo(myClient, myAccount);
  }

  private Client buildClient(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    String myClientTypeValue = getFieldStringValue(dataFileDefinitions, KEY_CLIENT_TYPE, dataEntry);
    ClientType myClientType = myClientTypeValue != null && myClientTypeValue.equalsIgnoreCase(ClientType.CL.name())
                                ? ClientType.CL : ClientType.OTHER;
    int myClientNumber = getFieldIntValue(dataFileDefinitions, KEY_CLIENT_NUMBER, dataEntry);

    return new Client(myClientType, myClientNumber);
  }

  private Account buildAccount(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    int myMainAccountNumber = getFieldIntValue(dataFileDefinitions, KEY_ACCT_NUMBER, dataEntry);
    int mySubaccountNumber = getFieldIntValue(dataFileDefinitions, KEY_SUBACCT_NUMBER, dataEntry);

    return new Account(myMainAccountNumber, mySubaccountNumber);
  }

  private TransactionProductInfo buildTxProductInfo(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    ExchangeProduct myExchangeProduct = buildExchangeProduct(dataFileDefinitions, dataEntry);
    String myExpirationDateValue = getFieldStringValue(dataFileDefinitions, KEY_EXPIRE_DATE, dataEntry);

    LocalDate myExpirationDate = null;
    try {
      myExpirationDate = LocalDate.parse(myExpirationDateValue, DATE_FORMATTER);
    }
    catch (Exception e) {
      throw new TxReporterAppException("Invalid expiration date", e);
    }

    return new TransactionProductInfo(myExchangeProduct, myExpirationDate);
  }

  private ExchangeProduct buildExchangeProduct(ResourceBundle dataFileDefinitions, String dataEntry)
  {
    String myGroupCode = getFieldStringValue(dataFileDefinitions, KEY_PROD_GROUP_CODE, dataEntry);
    String myExchangeCode = getFieldStringValue(dataFileDefinitions, KEY_EXCHANGE_CODE, dataEntry);
    String mySymbol = getFieldStringValue(dataFileDefinitions, KEY_SYMBOL, dataEntry);

    return new ExchangeProduct(myGroupCode, myExchangeCode, mySymbol);
  }

  private String getFieldStringValue(ResourceBundle dataFileDefinitions, String fieldName, String dataEntry)
  {
    int myStartIndex = -1;
    int myEndIndex = -1;

    try {
      myStartIndex = Integer.parseInt(dataFileDefinitions.getString(fieldName + KEY_FIELD_START));
      myEndIndex = Integer.parseInt(dataFileDefinitions.getString(fieldName + KEY_FIELD_END));
    }
    catch (Exception e) {
      throw new TxReporterAppException("Unable to retrieve metadata for field: " + fieldName, e);
    }

    if (myStartIndex > myEndIndex || myEndIndex > dataEntry.length()) {
      throw new TxReporterAppException("Incorrect metadata for field: " + fieldName);
    }

    return dataEntry.substring(myStartIndex - 1, myEndIndex).trim();
  }

  private int getFieldIntValue(ResourceBundle dataFileDefinitions, String fieldName, String dataEntry)
  {
    String myFieldValue = getFieldStringValue(dataFileDefinitions, fieldName, dataEntry);

    int myIntValue = -1;
    try {
      myIntValue = Integer.parseInt(myFieldValue);
    }
    catch (Exception e) {
      throw new TxReporterAppException("Incorrect integer value in field: " + fieldName, e);
    }

    return myIntValue;
  }

  private int getFieldLongValue(ResourceBundle dataFileDefinitions, String fieldName, String dataEntry)
  {
    String myFieldValue = getFieldStringValue(dataFileDefinitions, fieldName, dataEntry);

    int myIntValue = -1;
    try {
      myIntValue = Integer.parseInt(myFieldValue);
    }
    catch (Exception e) {
      throw new TxReporterAppException("Incorrect long integer value in field: " + fieldName, e);
    }

    return myIntValue;
  }
}