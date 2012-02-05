package com.maddyhome.idea.vim.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.maddyhome.idea.vim.file.VimScriptFileType;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Date: 01.02.12</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptPsiElementImpl extends ASTWrapperPsiElement implements VimScriptPsiElement {
  public VimScriptPsiElementImpl(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }

  public @NotNull Language getLanguage() {
    return VimScriptFileType.VIM_SCRIPT_LANGUAGE;
  }
}