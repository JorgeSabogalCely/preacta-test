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
public class AccountTest
{
  
  public AccountTest()
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
    Account myAccount = new Account(10, 3);
    
    assertEquals(10, myAccount.getMainAccountNumber());
    assertEquals(3, myAccount.getSubaccountNumber());
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testMainNumberError()
  {
    System.out.println("testMainNumberError");
    Account myAccount = new Account(-1, 3);
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testSubaccountNumberError()
  {
    System.out.println("testSubaccountNumberError");
    Account myAccount = new Account(1, -3);
  }
}
