package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.maddyhome.idea.vim.file.VimScriptFileType;
import com.maddyhome.idea.vim.lang.lexer.VimScriptFlexLexer;
import com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes;
import com.maddyhome.idea.vim.lang.psi.PsiVimScriptFile;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Date: 02.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptParserDefinition implements ParserDefinition {
 public static final IFileElementType VIM_SCRIPT_FILE =
      new IFileElementType("VimScriptFile", VimScriptFileType.VIM_SCRIPT_LANGUAGE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new VimScriptFlexLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new VimScriptParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return VIM_SCRIPT_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return VimScriptTokenTypes.whitespaces;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return VimScriptTokenTypes.comments;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return VimScriptTokenTypes.strings;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return VimScriptPsiCreator.createElement(node);
  }

  @Override
  public PsiFile createFile(FileViewProvider provider) {
    return new PsiVimScriptFile(provider);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    Lexer lexer = new VimScriptFlexLexer();
    return LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer);
  }
}
