package com.maddyhome.idea.vim.lang.parser;

import com.intellij.debugger.engine.DebuggerUtils;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.lexer.Lexer;
import com.intellij.mock.MockProject;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.maddyhome.idea.vim.lang.lexer.VimScriptFlexLexer;
import com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes;
import com.maddyhome.idea.vim.lang.psi.PsiVimScriptFile;
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
