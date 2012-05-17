package com.maddyhome.idea.vim.lang.parser;

import com.intellij.testFramework.ParsingTestCase;
import com.maddyhome.idea.vim.file.VimScriptFileType;

import java.io.IOException;


/**
 * <p>Date: 06.04.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public abstract class VimScriptParsingTestCase extends ParsingTestCase {
  public VimScriptParsingTestCase(@org.jetbrains.annotations.NonNls @org.jetbrains.annotations.NotNull String dataPath) {
    super("psi/" + dataPath, VimScriptFileType.DEFAULT_EXTENSION, new VimScriptParserDefinition());
  }

  protected void doParserTest(final String text) {
    final String name = getTestName(false);
    myFile = createPsiFile(name, text);
    try {
      checkResult(name, myFile);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  protected String getTestDataPath() {
    return System.getProperty("user.dir") + "/tests/testData";
  }
}
