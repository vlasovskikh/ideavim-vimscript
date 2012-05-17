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
  public static final IElementType EMPTY = new VimScriptElementType("empty");

  /* function */
  public static final IElementType FUNCTION_DEFINITION = new VimScriptElementType("function definition");
  public static final IElementType FUNCTION_BODY = new VimScriptElementType("function body");


  public static final IElementType KEYWORD = new VimScriptElementType("keyword");
  public static final IElementType VARIABLE = new VimScriptElementType("variable");
  public static final IElementType VALUE = new VimScriptElementType("value");

  /* expressions */
  public static final IElementType EXPRESSION = new VimScriptElementType("expression");
  public static final IElementType ASSIGNMENT_STMT = new VimScriptElementType("assignment statement");
  public static final IElementType TERNARY_EXPRESSION = new VimScriptElementType("ternary expression");
  public static final IElementType CONDITION = new VimScriptElementType("condition");
  public static final IElementType TERNARY_THEN = new VimScriptElementType("ternary then");
  public static final IElementType TERNARY_ELSE = new VimScriptElementType("ternary else");
  public static final IElementType OR_EXPRESSION = new VimScriptElementType("or expression");
  public static final IElementType AND_EXPRESSION = new VimScriptElementType("and expression");
  public static final IElementType COMPARISON_EXPRESSION = new VimScriptElementType("comparison expression");
  public static final IElementType PLUS_MINUS_DOT_EXPRESSION = new VimScriptElementType("plus-minus-dot expression");
  public static final IElementType MULT_DIV_MOD_EXPRESSION = new VimScriptElementType("mult-div-mod expression");
  public static final IElementType UNARY_EXPRESSION = new VimScriptElementType("unary expression");
  public static final IElementType COLLECTION_ITEM_EXPRESSION = new VimScriptElementType("collection item expression");
  public static final IElementType COLLECTION_ITEM_ID_EXPRESSION = new VimScriptElementType("collection item id expression");
  public static final IElementType SUBCOLLECTION_EXPRESSION = new VimScriptElementType("subcollection expression");
  public static final IElementType DICT_ITEM_EXPRESSION = new VimScriptElementType("dict item expression");
  public static final IElementType DICT_NAME = new VimScriptElementType("dict name");
  public static final IElementType DICT_KEY = new VimScriptElementType("dict key");
  public static final IElementType NESTED_EXPRESSION = new VimScriptElementType("nested expression");
  public static final IElementType FUNCTION_CALL = new VimScriptElementType("function call");


  /* let stmt's staff */
  public static final IElementType LET_STMT = new VimScriptElementType("let statement");

  /* set stmt's staff */
  public static final IElementType SET_STMT = new VimScriptElementType("set statement");
  public static final IElementType SET_OPTION = new VimScriptElementType("option");
  public static final TokenSet set_stmt_operators = TokenSet.create(
      OP_ASSIGN, COLON, OP_PLUS_ASSIGN, OP_CIRCUMFLEX_ASSIGN, OP_MINUS_ASSIGN
  );
}
