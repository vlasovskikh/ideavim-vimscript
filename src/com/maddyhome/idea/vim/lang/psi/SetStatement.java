package com.maddyhome.idea.vim.lang.psi;

import com.intellij.lang.ASTNode;

/**
 * <p>Date: 09.11.11</p>
 * Contains 'set' statements.
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class SetStatement extends VimScriptPsiElementImpl {
  public SetStatement(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }
}
