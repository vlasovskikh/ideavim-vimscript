package com.maddyhome.idea.vim.lang.parser;

/**
 * <p>Date: 08.04.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class LetStatementParsingTestCase extends VimScriptParsingTestCase {
  public LetStatementParsingTestCase() {
    super("parser/let");
  }

  public void testLet0() { doParserTest("let"); }
  public void testLet1() { doParserTest("let\n"); }

  public void testLetVar0() { doParserTest("let var"); }
  public void testLetVar1() { doParserTest("let var\n"); }

  //public void testLetError0() { doParserTest("let var="); }
}
