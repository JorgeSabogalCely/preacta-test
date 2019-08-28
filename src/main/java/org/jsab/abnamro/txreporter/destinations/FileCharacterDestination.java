package org.jsab.abnamro.txreporter.destinations;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import org.jsab.abnamro.txreporter.engine.FormattedDataDestination;
import org.jsab.abnamro.txreporter.exceptions.TxReporterAppException;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class FileCharacterDestination implements FormattedDataDestination
{
  private Writer writer;
  
  public FileCharacterDestination (Path reportOutputFilePath)
  {
    if (null == reportOutputFilePath) { 
      throw new TxReporterArgumentException("Invalid file path in new Report File Destianion");
      
    }
    try {
      writer = new FileWriter(reportOutputFilePath.toAbsolutePath().toString());
    }
    catch (IOException e) {
      throw new TxReporterAppException("Report output file is invalid", e);
    }
  }

  private void appendString(String value)
  {
    try {
      writer.append(value);
    }
    catch (IOException e) {
      throw new TxReporterAppException("Write operation failed for string value", e);
    }
  }

  private void appendChar(char value)
  {
    try {
      writer.append(value);
    }
    catch (IOException e) {
      throw new TxReporterAppException("Write operation failed for character value", e);
    }
  }

  @Override
  public void append(String value)
  {
    if (null != value) {
      appendString(value);
    }
  }

  @Override
  public void append(Object value)
  {
    if (null != value) {
      appendString(value.toString());
    }
  }

  @Override
  public void append(char value)
  {
    appendChar(value);
  }

  @Override
  public void append(int value)
  {
    append(String.valueOf(value));
  }

  @Override
  public void append(long value)
  {
    append(String.valueOf(value));
  }

  @Override
  public void close() throws IOException
  {
    writer.close();
  }
}
