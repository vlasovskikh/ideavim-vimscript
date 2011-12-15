package com.maddyhome.idea.vim.lang.parser;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

import com.maddyhome.idea.vim.lang.psi.Block;
import com.maddyhome.idea.vim.lang.psi.SetStatement;

/**
 * <p>Date: 09.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptPsiCreator {
  public static PsiElement createElement(ASTNode node) {

    //return node.getPsi();

    if (VimScriptElementTypes.SET_STMT.equals(node.getElementType())) {
      return new SetStatement(node);
    }

    if (VimScriptElementTypes.BLOCK.equals(node.getElementType())) {
      return new Block(node);
    }

    return new ASTWrapperPsiElement(node);
  }
}
