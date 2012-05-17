package com.maddyhome.idea.vim.lang.psi;

import com.intellij.lang.ASTNode;

/**
 * <p>Date: 03.05.12</p>
 * Represents function definition.
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class FunctionDefinition extends PsiVimScriptElementImpl {
  public FunctionDefinition(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }
}
