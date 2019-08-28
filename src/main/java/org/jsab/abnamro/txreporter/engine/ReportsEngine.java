package org.jsab.abnamro.txreporter.engine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsab.abnamro.txreporter.destinations.FileCharacterDestination;
import org.jsab.abnamro.txreporter.exceptions.TxReporterAppException;
import org.jsab.abnamro.txreporter.formatters.CSVReportFormatter;
import org.jsab.abnamro.txreporter.transactions.reports.ClientDailySummaryReport;
import org.jsab.abnamro.txreporter.transactions.ProcessedFutureTxDAO;

import static org.jsab.abnamro.txreporter.TxReportsConstants.DATA_DEFINITIONS_FILE;
import static org.jsab.abnamro.txreporter.TxReportsConstants.KEY_DEFAULT_DATA_DIR;

/**
 *
 * @author JorgeLuis
 */
public class ReportsEngine
{
  private static final Logger LOGGER = Logger.getLogger(ReportsEngine.class.getName());
  
  private static volatile ReportsEngine instance;

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
  
  private ReportsEngine()
  {
  }
  
  /**
   * Returns unique instance of this engine.
   *
   * @return
   */
  public static ReportsEngine getInstance()
  {
    ReportsEngine myEngineInstance = instance;
    if (myEngineInstance == null) {
      synchronized (ReportsEngine.class) {
        myEngineInstance = instance;
        if (null == myEngineInstance) {
          instance = myEngineInstance = new ReportsEngine();
        }
      }
    }

    return myEngineInstance;
  }

  public Path generateDailySummaryReport(String sourceDataFilePath, ReportOutputFormat outputFormat,
                                         String destinationDirectoryPath)
  {
    ClientDailySummaryReport mySummaryReport = 
            ProcessedFutureTxDAO.getInstance().generateDailySummaryReport(sourceDataFilePath);
    
    return formatReport(mySummaryReport, outputFormat, destinationDirectoryPath);
  }
  
  public Path generateDailySummaryReport(ReportOutputFormat outputFormat)
  {
    ClientDailySummaryReport mySummaryReport = ProcessedFutureTxDAO.getInstance().generateDailySummaryReport();
    
    return formatReport(mySummaryReport, outputFormat, null);
  }

  private Path formatReport(FormattableReport report, ReportOutputFormat outputFormat, String destinationDirectoryPath)
  {
    Path myDestinationFilePath = getDestinationFilePath(report, outputFormat, destinationDirectoryPath);
    try (FormattedDataDestination myDataOutput = getDataDestination(myDestinationFilePath)) {
      ReportFormatter myFormatter = getReportFormatter(outputFormat, myDataOutput);
      report.toFormattedData(myFormatter);
    }
    catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error while processing entries in specified data-file", e);
      throw new TxReporterAppException("Error while processing entries in specified data-file");
    }
    
    return myDestinationFilePath;
  }

  private Path getDestinationFilePath(FormattableReport report, ReportOutputFormat outputFormat,
                                      String destinationDirectoryPath)
  {
    String myFileName = report.getName() + "-" + LocalDateTime.now().format(DATE_FORMATTER)
                        + "." + outputFormat.name().toLowerCase();
    
    Path myDestFilePath = destinationDirectoryPath != null ? Paths.get(destinationDirectoryPath, myFileName)
                                                           : Paths.get(getDefaultDataDirPath(), myFileName);
    
    return myDestFilePath;
  }

  private String getDefaultDataDirPath()
  {
    try {
      ResourceBundle myDataDefinitionProps = ResourceBundle.getBundle(DATA_DEFINITIONS_FILE);
      return myDataDefinitionProps.getString(KEY_DEFAULT_DATA_DIR);
    }
    catch (Exception e) {
      throw new TxReporterAppException("Invalid data file definition properties in Reports Engine", e);
    }
  }

  private FormattedDataDestination getDataDestination(Path destinationFilePath)
  {
    return new FileCharacterDestination(destinationFilePath);
  }
 
  private ReportFormatter getReportFormatter(ReportOutputFormat outputFormat, FormattedDataDestination dataOutput)
  {
    switch (outputFormat) {
      case CSV : {
        return new CSVReportFormatter(dataOutput);
      }
      default : {
        return new CSVReportFormatter(dataOutput);
      }
    }
  }
}