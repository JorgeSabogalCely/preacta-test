package org.jsab.abnamro.txreporter.engine;

import java.io.Closeable;

/**
 *
 * @author JorgeLuis
 */
public interface FormattedDataDestination extends Closeable
{
  void append(String value);
  void append(Object value);
  void append(char value);
  void append(int value);
  void append(long value);
}
