package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptPsiBuilder implements PsiBuilder {
  private PsiBuilder psiBuilder;

  public VimScriptPsiBuilder(@NotNull PsiBuilder builder) {
    psiBuilder = builder;
  }

  @Override
  public Project getProject() {
    return psiBuilder.getProject();
  }

  @Override
  public CharSequence getOriginalText() {
    return psiBuilder.getOriginalText();
  }

  @Override
  public void advanceLexer() {
    psiBuilder.advanceLexer();
  }

  @Override
  public IElementType getTokenType() {
    return psiBuilder.getTokenType();
  }

  @Override
  public void setTokenTypeRemapper(ITokenTypeRemapper iTokenTypeRemapper) {
    psiBuilder.setTokenTypeRemapper(iTokenTypeRemapper);
  }

  @Override
  public void remapCurrentToken(IElementType iElementType) {
    psiBuilder.remapCurrentToken(iElementType);
  }

  @Override
  public void setWhitespaceSkippedCallback(WhitespaceSkippedCallback whitespaceSkippedCallback) {
    psiBuilder.setWhitespaceSkippedCallback(whitespaceSkippedCallback);
  }

  @Override
  public IElementType lookAhead(int i) {
    return psiBuilder.lookAhead(i);
  }

  @Override
  public IElementType rawLookup(int i) {
    return psiBuilder.rawLookup(i);
  }

  @Override
  public int rawTokenTypeStart(int i) {
    return psiBuilder.rawTokenTypeStart(i);
  }

  @Override
  public String getTokenText() {
    return psiBuilder.getTokenText();
  }

  @Override
  public int getCurrentOffset() {
    return psiBuilder.getCurrentOffset();
  }

  @Override
  public Marker mark() {
    return psiBuilder.mark();
  }

  @Override
  public void error(String s) {
    System.err.println(s);
    psiBuilder.error(s);
  }

  @Override
  public boolean eof() {
    return psiBuilder.eof();
  }

  @Override
  public ASTNode getTreeBuilt() {
    return psiBuilder.getTreeBuilt();
  }

  @Override
  public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
    return psiBuilder.getLightTree();
  }

  @Override
  public void setDebugMode(boolean b) {
    psiBuilder.setDebugMode(b);
  }

  @Override
  public void enforceCommentTokens(TokenSet tokenSet) {
    psiBuilder.enforceCommentTokens(tokenSet);
  }

  @Override
  public LighterASTNode getLatestDoneMarker() {
    return psiBuilder.getLatestDoneMarker();
  }

  @Override
  public <T> T getUserData(@NotNull Key<T> tKey) {
    return psiBuilder.getUserData(tKey);
  }

  @Override
  public <T> void putUserData(@NotNull Key<T> tKey, @Nullable T t) {
    psiBuilder.putUserData(tKey, t);
  }

  @Override
  public <T> T getUserDataUnprotected(@NotNull Key<T> tKey) {
    return psiBuilder.getUserDataUnprotected(tKey);
  }

  @Override
  public <T> void putUserDataUnprotected(@NotNull Key<T> tKey, @Nullable T t) {
    psiBuilder.putUserDataUnprotected(tKey, t);
  }
}