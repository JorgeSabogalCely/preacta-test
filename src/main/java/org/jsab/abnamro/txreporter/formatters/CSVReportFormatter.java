package org.jsab.abnamro.txreporter.formatters;

import org.jsab.abnamro.txreporter.engine.ReportFormatter;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;
import org.jsab.abnamro.txreporter.engine.FormattedDataDestination;

/**
 * Implementation of ReportFormatter for CSV format
 * 
 * @author JorgeLuis
 */
public class CSVReportFormatter implements ReportFormatter
{
  private FormattedDataDestination dataOutput;
  
  public CSVReportFormatter(FormattedDataDestination reportDataOutput)
  {
    if (null == reportDataOutput) {
      throw new TxReporterArgumentException("Invalid destination file container in new Report Formatter");
    }
    
    dataOutput = reportDataOutput;
  }

  @Override
  public ReportFormatter headerStart()
  { 
    return this;
  }

  @Override
  public ReportFormatter headerEnd()
  {
    return this;
  }

  @Override
  public ReportFormatter entryStart()
  {
    try {
      dataOutput.append(System.lineSeparator());
    }
    catch (Exception e) {
    }
    return this;
  }

  @Override
  public ReportFormatter entryEnd()
  {
    return this;
  }

  @Override
  public ReportFormatter value(Object value)
  {
    dataOutput.append(value);
    return this;
  }

  @Override
  public ReportFormatter value(int value)
  {
    dataOutput.append(value);
    return this;
  }

  @Override
  public ReportFormatter value(long value)
  {
    dataOutput.append(value);
    return this;
  }

  @Override
  public ReportFormatter fieldSectionSeparator()
  {
    dataOutput.append(':');
    return this;
  }

  @Override
  public ReportFormatter fieldSeparator()
  {
    dataOutput.append(',');
    return this;
  }
}