package com.maddyhome.idea.vim.lang.parser;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.junit.Test;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptParserTest  {
  //java/java-tests/testData/psi/parser-full/expressionParsing
  @Test
  public void test() {
    String text = "set d\n4";
    //String rcText = FileUtil.loadFile(filename);
    PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(
      ProjectManager.getInstance().getDefaultProject()
    );
    PsiFile testPsiFile = psiFileFactory.createFileFromText("test.vim", text);
  }
}
