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

  public void testLetError0() { doParserTest("let var="); }
  public void testLetError1() { doParserTest("let var.="); }

  public void testLetVarSequence() { doParserTest("let var1 @var2 var3"); }

  public void testLetVarNumber() { doParserTest("let var=12"); }
  public void testLetVarString0() { doParserTest("let var = \"Hello\""); }
  public void testLetVarString1() { doParserTest("let var = 'Hello'"); }
  public void testLetVarVariable() { doParserTest("let var = another_variable"); }
  public void testLetVarNested() { doParserTest("let var = (15)"); }
  public void testLetVarFuncCall0() { doParserTest("let a = get_a()"); }
  public void testLetVarFuncCall1() { doParserTest("let a = get_a(d, e)"); }
}
