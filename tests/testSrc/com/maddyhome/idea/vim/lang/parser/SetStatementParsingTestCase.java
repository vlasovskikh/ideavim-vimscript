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

  public void testSetAll() { doParserTest("set all"); }
  public void testSetAllAmp() { doParserTest("set all&"); }

  public void testSetTermcap() { doParserTest("set termcap"); }

  public void testOption() { doParserTest("set opt"); }
  public void testOptionQuestion() { doParserTest("set opt?"); }
  public void testOptionAmp() { doParserTest("set opt&"); }

  /*
  public void testOptionAmpVi() { doParserTest("set opt&vi"); }
  public void testOptionAmpVim() { doParserTest("set opt&vim"); }
  */

  public void testOptionReset() { doParserTest("set noopt"); }

  public void testOptionInv0() { doParserTest("set invopt"); }
  public void testOptionInv1() { doParserTest("set opt!"); }

  public void testOptionAssignment0() { doParserTest("set opt=val"); }
  public void testOptionAssignment1() { doParserTest("set opt =val"); }
  public void testOptionAssignment2() { doParserTest("set opt=3"); }
  public void testOptionAssignment3() { doParserTest("set opt =3"); }
  public void testOptionAssignment4() { doParserTest("set opt:0x13"); }
  public void testOptionAssignment5() { doParserTest("set opt :0x13"); }

  public void testOptionAssignment6() { doParserTest("se opt +=val"); }
  public void testOptionAssignment7() { doParserTest("se opt -=val"); }
  public void testOptionAssignment8() { doParserTest("se opt ^=val"); }

}
