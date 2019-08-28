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
public class ClientTest
{
  
  public ClientTest()
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
    Client myClient = new Client(ClientType.CL, 23);
    
    assertEquals(ClientType.CL, myClient.getType());
    assertEquals(23, myClient.getNumber());
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testClientTypeError()
  {
    System.out.println("testClientTypeError");
    Client myClient = new Client(null, 23);
  }

  @Test(expected = TxReporterArgumentException.class)
  public void testClientNumberError()
  {
    System.out.println("testClientNumberError");
    Client myClient = new Client(ClientType.CL, -1);
  }
}