package com.maddyhome.idea.vim.lang.psi;

import com.intellij.lang.ASTNode;

/**
 * <p>Date: 01.12.11</p>
 * Represents function body.
 * Contains allowed in function body statements.
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class FunctionBody extends PsiVimScriptElementImpl {
  public FunctionBody(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }
}
