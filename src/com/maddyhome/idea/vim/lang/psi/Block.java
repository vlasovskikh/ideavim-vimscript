package com.maddyhome.idea.vim.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.maddyhome.idea.vim.file.VimScriptFileType;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Date: 01.12.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class Block extends ASTWrapperPsiElement implements PsiElement {
  public Block(@org.jetbrains.annotations.NotNull ASTNode node) {
    super(node);
  }

  public @NotNull
  Language getLanguage() {
    return VimScriptFileType.VIM_SCRIPT_LANGUAGE;
  }
}
