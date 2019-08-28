package org.jsab.abnamro.txreporter.exceptions;

/**
 * Unchecked exception thrown by transactions reporter components during execution when immediate action
 * is not required from callers or clients.
 */
public class TxReporterArgumentException extends RuntimeException
{
  private static final long serialVersionUID = 5721308101838698728L;

  /**
   * 
   * @param errorMessage 
   */
  public TxReporterArgumentException(String errorMessage)
  {
    super(errorMessage);
  }
}