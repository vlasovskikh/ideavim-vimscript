package com.maddyhome.idea.vim.lang.parser;

/**
 * <p>Date: 08.04.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class CommonParsingTestCase extends VimScriptParsingTestCase {
  public CommonParsingTestCase() {
    super("parser/common");
  }

  public void testEmpty() { doParserTest(""); }
  public void testNewline() { doParserTest("\n"); }
}
