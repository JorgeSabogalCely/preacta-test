package org.jsab.abnamro.txreporter.transactions;

import org.jsab.abnamro.txreporter.transactions.reports.ClientDailySummaryReport;
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
public class ProcessedFutureTxDAOTest
{
  
  public ProcessedFutureTxDAOTest()
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
   * Test of getInstance method, of class ProcessedFutureTxDAO.
   */
  @Test
  public void testTxSample1()
  {
    System.out.println("testTxSample1");
    ClientDailySummaryReport myReport =
            ProcessedFutureTxDAO.getInstance().generateDailySummaryReport("src/test/resources/data/tx-sample1.txt");
    
    // Four different client-products
    assertEquals(4, myReport.getSize());
  }
}

