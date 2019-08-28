package org.jsab.abnamro.txreporter.model;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jsab.abnamro.txreporter.exceptions.TxReporterArgumentException;

/**
 *
 * @author JorgeLuis
 */
public class Client implements Serializable
{
  private static final long serialVersionUID = -5837957308710609389L;

  private final ClientType type;
  private final int number;
  
  private transient int hash = -1;
  
  public Client(ClientType clientType, int clientNumber)
  {
    if (null == clientType) {
      throw new TxReporterArgumentException("Invalid client type in new Client information");
    }
    if (clientNumber <= 0) {
      throw new TxReporterArgumentException("Invalid client number in new Client information");
    }

    type = clientType;
    number = clientNumber;
  }
  
  public final ClientType getType()
  {
    return type;
  }

  public final int getNumber()
  {
    return number;
  }

  @Override
  public int hashCode()
  {
    if (hash == -1) {
      int myHash = 7;
      myHash = 97 * myHash + type.hashCode();
      myHash = 97 * myHash + number;
      hash = myHash;
    }

    return hash;
  }

  @Override
  public boolean equals(Object otherObject)
  {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    
    Client myOtherClient = (Client) otherObject;
    return type == myOtherClient.getType() && number == myOtherClient.getNumber();
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("Client[type:{0},number:{1,number,#}]", type, number);
  }
}