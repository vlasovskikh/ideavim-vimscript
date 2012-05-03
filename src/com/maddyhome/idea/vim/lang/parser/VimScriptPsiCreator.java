package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

import com.intellij.psi.tree.IElementType;
import com.maddyhome.idea.vim.lang.psi.*;

/**
 * <p>Date: 09.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptPsiCreator {
  public static PsiElement createElement(ASTNode node) {

    //return node.getPsi();

    IElementType elementType = node.getElementType();

    if (VimScriptElementTypes.SET_OPTION.equals(elementType)) {
      return new SetOption(node);
    }

    if (VimScriptElementTypes.KEYWORD.equals(elementType)) {
      return new Keyword(node);
    }

    if (VimScriptElementTypes.VARIABLE.equals(elementType)) {
      return new Variable(node);
    }

    if (VimScriptElementTypes.EXPRESSION.equals(elementType)) {
      return new Expression(node);
    }

    if (VimScriptElementTypes.LET_STMT.equals(elementType)) {
      return new LetStatement(node);
    }

    if (VimScriptElementTypes.SET_STMT.equals(elementType)) {
      return new SetStatement(node);
    }

    if (VimScriptElementTypes.FUNCTION_DEFINITION.equals(elementType)) {
      return new FunctionDefinition(node);
    }

    if (VimScriptElementTypes.FUNCTION_BODY.equals(elementType)) {
      return new FunctionBody(node);
    }

    return new PsiVimScriptTokenImpl(elementType, node.getText());
  }
}
