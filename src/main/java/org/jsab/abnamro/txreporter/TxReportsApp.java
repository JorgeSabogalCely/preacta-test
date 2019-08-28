package org.jsab.abnamro.txreporter;

import java.nio.file.Path;
import org.jsab.abnamro.txreporter.engine.ReportOutputFormat;
import org.jsab.abnamro.txreporter.engine.ReportsEngine;

/**
 *
 * @author JorgeLuis
 */
public class TxReportsApp
{
  public static void main(String[] args)
  {
    // first argument is input file
    String myInputFile = null;
    
    // second argument is the output directory
    String myOuputDir = null;
    
    if (args.length > 0) {
      myInputFile = args[0].trim();
      if (args.length > 1) {
        myOuputDir = args[1].trim();
      }
    }
    
    // CSV is the only output format implemented so far
    try {
      Path myOutpuFile = ReportsEngine.getInstance().generateDailySummaryReport(myInputFile, ReportOutputFormat.CSV,
                                                                                myOuputDir);
      System.out.println("Report file created: " + myOutpuFile.toAbsolutePath());
    }
    catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }
}