package org.jsab.abnamro.txreporter.engine;

/**
 *
 * @author JorgeLuis
 */
public interface ReportFormatter
{
  ReportFormatter headerStart();
  ReportFormatter headerEnd();

  ReportFormatter entryStart();
  ReportFormatter entryEnd();

  ReportFormatter value(Object value);
  ReportFormatter value(int value);
  ReportFormatter value(long value);
  
  ReportFormatter fieldSectionSeparator();
  ReportFormatter fieldSeparator();
}
