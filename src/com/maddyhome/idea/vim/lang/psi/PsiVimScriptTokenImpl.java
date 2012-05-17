package com.maddyhome.idea.vim.lang.psi;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;

/**
 * <p>Date: 04.02.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class PsiVimScriptTokenImpl  extends LeafPsiElement implements PsiVimScriptToken {
  public PsiVimScriptTokenImpl(IElementType type, CharSequence text) {
    super(type, text);
  }

  /**
   * Returns the type of the token.
   *
   * @return the token type.
   */
  @Override
  public IElementType getTokenType() {
    return getElementType();
  }

  public String toString(){
    return "PsiVimScriptToken:" + getElementType().toString();
  }
}
