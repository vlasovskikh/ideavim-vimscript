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
  public void testLetVarVariable0() { doParserTest("let var = another_variable"); }
  public void testLetVarVariable1() { doParserTest("let var = another_variable  "); }
  public void testLetVarNested() { doParserTest("let var = (15)"); }
  public void testLetVarFuncCall0() { doParserTest("let a = get_a()"); }
  public void testLetVarFuncCall1() { doParserTest("let a = get_a(d, e)"); }

  public void testLetVarListItem() { doParserTest("let list_item = list[ id ]"); }
  public void testLetVarSublist() { doParserTest("let sublist = list[ 1 : 10 ]"); }
  public void testLetVarDictEntry() { doParserTest("let entry = eng2ru.hello"); }

  public void testLetVarUnary0() { doParserTest("let var = +r "); }
  public void testLetVarUnary1() { doParserTest("let rev = -ver "); }
  public void testLetVarUnary2() { doParserTest("let inv = !v "); }
}
