package com.maddyhome.idea.vim.lang.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.maddyhome.idea.vim.lang.lexer.VimScriptElementType;

import static com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes.*;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public interface VimScriptElementTypes {
  public static final IElementType EMPTY_INPUT = new VimScriptElementType("empty input");

  /* function */
  public static final IElementType FUNCTION_DEFINITION = new VimScriptElementType("function definition");
  public static final IElementType FUNCTION_BODY = new VimScriptElementType("function body");


  public static final IElementType KEYWORD = new VimScriptElementType("keyword");
  public static final IElementType VARIABLE = new VimScriptElementType("variable");
  public static final IElementType VALUE = new VimScriptElementType("value");
  public static final IElementType EXPRESSION = new VimScriptElementType("expression");
  public static final IElementType ASSIGNMENT_STMT = new VimScriptElementType("assignment statement");

  /* let stmt's staff */
  public static final IElementType LET_STMT = new VimScriptElementType("let statement");

  /* set stmt's staff */
  public static final IElementType SET_STMT = new VimScriptElementType("set statement");
  public static final IElementType SET_OPTION = new VimScriptElementType("option");
  public static final TokenSet set_stmt_operators = TokenSet.create(
      OP_ASSIGN, COLON, OP_PLUS_ASSIGN, OP_CIRCUMFLEX_ASSIGN, OP_MINUS_ASSIGN
  );
}
