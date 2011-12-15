package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.lexer.Lexer;
import com.maddyhome.idea.vim.lang.lexer.VimScriptFlexLexer;
import com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes;
import org.junit.Test;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptParserTest {
  @Test
  public void test() {
    String text = "set d=\n";
    final ParserDefinition parserDefinition = new VimScriptParserDefinition();
    final Lexer lexer = new VimScriptFlexLexer();
    final PsiBuilder builder = new PsiBuilderImpl(
        lexer, VimScriptTokenTypes.whitespaces, VimScriptTokenTypes.comments, text
    );
    final ASTNode node = parserDefinition.createParser(null).parse(builder.getTokenType(), builder);

    System.out.println(node.getElementType());
  }
}
