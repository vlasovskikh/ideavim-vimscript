package com.maddyhome.idea.vim.lang.psi;

import com.intellij.lang.ASTNode;

/**
 * <p>Date: 01.02.12</p>
 * Contains options of 'set' statements.
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class SetOption extends VimScriptPsiElementImpl {
  public SetOption(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }
}
