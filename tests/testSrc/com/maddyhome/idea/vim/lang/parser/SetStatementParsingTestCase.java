package com.maddyhome.idea.vim.lang.parser;

/**
 * <p>Date: 06.04.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class SetStatementParsingTestCase extends VimScriptParsingTestCase {
  public SetStatementParsingTestCase() {
    super("parser/set");
  }

  public void testSet0() { doParserTest("set"); }
  public void testSet1() { doParserTest("se"); }
  public void testSet2() { doParserTest("set\n"); }
  public void testSet3() { doParserTest("se\n"); }

  public void testSetAll0() { doParserTest("set all"); }
  public void testSetAll1() { doParserTest("set all&"); }

  public void testSetTermcap() { doParserTest("set termcap"); }


}
