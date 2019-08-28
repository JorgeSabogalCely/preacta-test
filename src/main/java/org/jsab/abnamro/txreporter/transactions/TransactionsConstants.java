package org.jsab.abnamro.txreporter.transactions;

/**
 *
 * @author JorgeLuis
 */
class TransactionsConstants
{
  // Keys for properties in ProcessedFutureTXRecordStruct file
  
  static final String KEY_DEFAULT_INPUT_DATA_FILENAME = "defaultInpuFilename.path";
  
  private static final String KEY_DATA_FIELD = "field";
  
  private static final String KEY_CLIENT = KEY_DATA_FIELD + ".client";
  static final String KEY_CLIENT_TYPE = KEY_CLIENT + ".type";
  static final String KEY_CLIENT_NUMBER = KEY_CLIENT + ".number";
  
  private static final String KEY_ACCOUNT = KEY_DATA_FIELD + ".account";
  static final String KEY_ACCT_NUMBER = KEY_ACCOUNT + ".mainAccountNumber";
  static final String KEY_SUBACCT_NUMBER = KEY_ACCOUNT + ".subaccountNumber";

  static final String KEY_PROD_GROUP_CODE = KEY_DATA_FIELD + ".productGroupCode";
  static final String KEY_EXCHANGE_CODE = KEY_DATA_FIELD + ".exchangeCode";
  static final String KEY_SYMBOL = KEY_DATA_FIELD + ".symbol";
    
  static final String KEY_EXPIRE_DATE = KEY_DATA_FIELD + ".expirationDate";
  
  private static final String KEY_QUANTITY = KEY_DATA_FIELD + ".quantity";
  static final String KEY_QUANTITY_LONG_SIGN = KEY_QUANTITY + ".longSign";
  static final String KEY_QUANTITY_LONG = KEY_QUANTITY + ".long";
  static final String KEY_QUANTITY_SHORT_SIGN = KEY_QUANTITY + ".shortSign";
  static final String KEY_QUANTITY_SHORT = KEY_QUANTITY + ".short";
 
  static final String KEY_FIELD_START = ".start";
  static final String KEY_FIELD_END = ".end";
 
  private TransactionsConstants()
  {
  }
}