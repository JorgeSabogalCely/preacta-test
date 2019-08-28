package org.jsab.abnamro.txreporter.model;

import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author JorgeLuis
 */
public class ExchangeProductTest
{
  
  public ExchangeProductTest()
  {
  }
  
  @BeforeClass
  public static void setUpClass()
  {
  }
  
  @AfterClass
  public static void tearDownClass()
  {
  }
  
  @Before
  public void setUp()
  {
  }
  
  @After
  public void tearDown()
  {
  }

  /**
   * Test of class Client.
   */
  @Test
  public void testInit()
  {
    System.out.println("testInit");
    ExchangeProduct myExchangeProduct = new ExchangeProduct("FU", "SGX", "K1");
    
    assertEquals("FU", myExchangeProduct.getGroupCode());
    assertEquals("SGX", myExchangeProduct.getExchangeCode());
    assertEquals("K1", myExchangeProduct.getSymbol());
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testGroupCodeError()
  {
    System.out.println("testGroupCodeError");
    ExchangeProduct myExchangeProduct = new ExchangeProduct(null, "SGX", "K1");
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testExchangeCodeError()
  {
    System.out.println("testExchangeCodeError");
    ExchangeProduct myExchangeProduct = new ExchangeProduct("FU", null, "K1");
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testSymbolError()
  {
    System.out.println("testSymbolError");
    ExchangeProduct myExchangeProduct = new ExchangeProduct("FU", "SGX", null);
  }
}
