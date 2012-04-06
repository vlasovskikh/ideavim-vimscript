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

  public void testSetAll0() { doParserTest("set all"); }
  public void testSetAll1() { doParserTest("set all&"); }

}
