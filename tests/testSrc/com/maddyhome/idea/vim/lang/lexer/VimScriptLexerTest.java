package com.maddyhome.idea.vim.lang.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;

/**
 * <p>Date: 05.04.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptLexerTest extends LexerTestCase {
  public void testIntegerNumbers() {
    doTest(
      "1 -2 017 0x12",
      "int ('1')\nWHITE_SPACE (' ')\n" +
        "int ('-2')\nWHITE_SPACE (' ')\n" +
        "int ('017')\nWHITE_SPACE (' ')\n" +
        "int ('0x12')"
    );
  }

  public void testFloatingPointNumbers() {
    doTest(
      "0.1 -0.3 0.2e12 -3.3e2 4.5e-10 -5.6e-4",
      "float ('0.1')\nWHITE_SPACE (' ')\n" +
        "float ('-0.3')\nWHITE_SPACE (' ')\n" +
        "float ('0.2e12')\nWHITE_SPACE (' ')\n" +
        "float ('-3.3e2')\nWHITE_SPACE (' ')\n" +
        "float ('4.5e-10')\nWHITE_SPACE (' ')\n" +
        "float ('-5.6e-4')"
    );
  }

  public void testStrings() {
    doTest(
      "\'Hello!\'\n\"Double-quoted string\"",
      "string (''Hello!'')\n" +
        "NEW_LINE_INDENT ('\\n')\n" +
        "string ('\"Double-quoted string\"')"
    );
  }

  public void testOperators() {
    doTest(
      "== != >= > <= < =~ !~ || && + - * / % =",
      "== ('==')\nWHITE_SPACE (' ')\n" +
        "!= ('!=')\nWHITE_SPACE (' ')\n" +
        ">= ('>=')\nWHITE_SPACE (' ')\n" +
        "> ('>')\nWHITE_SPACE (' ')\n" +
        "<= ('<=')\nWHITE_SPACE (' ')\n" +
        "< ('<')\nWHITE_SPACE (' ')\n" +
        "=~ ('=~')\nWHITE_SPACE (' ')\n" +
        "!~ ('!~')\nWHITE_SPACE (' ')\n" +
        "|| ('||')\nWHITE_SPACE (' ')\n" +
        "&& ('&&')\nWHITE_SPACE (' ')\n" +
        "+ ('+')\nWHITE_SPACE (' ')\n" +
        "- ('-')\nWHITE_SPACE (' ')\n" +
        "* ('*')\nWHITE_SPACE (' ')\n" +
        "/ ('/')\nWHITE_SPACE (' ')\n" +
        "% ('%')\nWHITE_SPACE (' ')\n" +
        "= ('=')"
    );
  }

  public void testVariablesAndIdentifiers() {
    doTest(
      "let a:v1 $env &opt @reg",
      "identifier ('let')\nWHITE_SPACE (' ')\n" +
        "prefix:varname ('a:v1')\nWHITE_SPACE (' ')\n" +
        "envvar ('$env')\nWHITE_SPACE (' ')\n" +
        "option ('&opt')\nWHITE_SPACE (' ')\n" +
        "register ('@reg')"
    );
  }

  public void testBrackets() {
    doTest(
      "()[]{}",
      "( ('(')\n) (')')\n" +
        "[ ('[')\n] (']')\n" +
        "{ ('{')\n} ('}')"
    );
  }

  public void testSpecialCharacters() {
    doTest(
      "\\r\\n",
      "ESCAPED ('\\r')\n" +
        "ESCAPED ('\\n')"
    );
  }

  public void testSymbols() {
    doTest(
      ": . ; ? ! &",
      ": (':')\nWHITE_SPACE (' ')\n" +
        ". ('.')\nWHITE_SPACE (' ')\n" +
        "; (';')\nWHITE_SPACE (' ')\n" +
        "? ('?')\nWHITE_SPACE (' ')\n" +
        "! ('!')\nWHITE_SPACE (' ')\n" +
        "& ('&')"
    );
  }

  @Override
  protected Lexer createLexer() {
    return new VimScriptFlexLexer();
  }

  @Override
  protected String getDirPath() {
    return "";
  }
}
