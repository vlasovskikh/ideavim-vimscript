package com.maddyhome.idea.vim.lang.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

/**
 * <p>Date: 04.02.12</p>
 * Represents a single token in a file (leaf of PSI tree).
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public interface PsiVimScriptToken  extends PsiElement {
  /**
   * Returns the type of the token.
   *
   * @return the token type.
   */
  IElementType getTokenType();
}
