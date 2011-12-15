package com.maddyhome.idea.vim.lang.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.maddyhome.idea.vim.lang.lexer.VimScriptElementType;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public interface VimScriptElementTypes {
  public static final IElementType EMPTY_INPUT = new VimScriptElementType("empty input");
  public static final IElementType BLOCK = new VimScriptElementType("code block");

  public static final IElementType EXPRESSION = new VimScriptElementType("expression");
  public static final IElementType ASSIGNMENT_STMT = new VimScriptElementType("assign_stmt");

  public static final IElementType LET_VAR_EXPR = new VimScriptElementType("let {var} = {expr}");

  /* set stmts */
  public static final IElementType KEYWORD = new VimScriptElementType("keyword");

  public static final IElementType SET_STMT = new VimScriptElementType("set stmt");

  /* options */
  public static final IElementType SET_OPTION = new VimScriptElementType("option");
}
