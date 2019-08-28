package org.jsab.abnamro.txreporter.exceptions;

/**
 * Checked exception thrown by transactions reporter components when action is required from
 * callers or clients.
 */
public class TxReporterAppException extends RuntimeException
{
  private static final long serialVersionUID = -6395896259915747601L;

  /**
   * 
   * @param errorMessage 
   */
  public TxReporterAppException(String errorMessage)
  {
    super(errorMessage);
  }

  /**
   * 
   * @param errorMessage
   * @param rootCause 
   */
  public TxReporterAppException(String errorMessage, Throwable rootCause)
  {
    super(errorMessage, rootCause);
  }
}