package test;

import static org.junit.Assert.assertEquals;

import org.ivran.customjoin.MessageFormatter;
import org.junit.Test;

public class MessageFormatterTest {

  @Test
  public void testFormatter() {
    MessageFormatter formatter = new MessageFormatter();
    assertEquals("Steve has joined", formatter.format("%player has joined", "Steve"));
  }

}
