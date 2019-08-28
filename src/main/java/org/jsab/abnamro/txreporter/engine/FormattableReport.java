package org.jsab.abnamro.txreporter.engine;

/**
 *
 * @author JorgeLuis
 */
public interface FormattableReport
{
  String getName();
  void toFormattedData(ReportFormatter reportFormatter);
}
