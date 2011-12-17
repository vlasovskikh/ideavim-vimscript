package com.maddyhome.idea.vim.lang.lexer;

import com.intellij.lang.ParserDefinition;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * <p>Date: 25.10.11</p>
 * <p>Tokens of VimScript.</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public interface VimScriptTokenTypes {
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  public static final IElementType WHITESPACE = TokenType.WHITE_SPACE;
  public static final IElementType NEW_LINE = TokenType.NEW_LINE_INDENT;

  public static final TokenSet whitespaces = TokenSet.create(
      //WHITESPACE
  );

  /* brackets */
  public static final IElementType LEFT_ROUND_BRACKET = new VimScriptElementType("(");
  public static final IElementType RIGHT_ROUND_BRACKET = new VimScriptElementType(")");
  public static final IElementType LEFT_SQUARE_BRACKET = new VimScriptElementType("[");
  public static final IElementType RIGHT_SQUARE_BRACKET = new VimScriptElementType("]");
  public static final IElementType LEFT_CURLY_BRACKET = new VimScriptElementType("{");
  public static final IElementType RIGHT_CURLY_BRACKET = new VimScriptElementType("}");

  /* quotes */
  public static final IElementType SINGLE_QUOTE = new VimScriptElementType("\'");
  public static final IElementType DOUBLE_QUOTE = new VimScriptElementType("\"");

  /* operators */
  public static final IElementType OP_ASSIGN = new VimScriptElementType("=");
  public static final IElementType OP_PLUS_ASSIGN = new VimScriptElementType("+=");
  public static final IElementType OP_MINUS_ASSIGN = new VimScriptElementType("-=");
  public static final IElementType OP_MULT_ASSIGN = new VimScriptElementType("*=");
  public static final IElementType OP_DIV_ASSIGN = new VimScriptElementType("/=");
  public static final IElementType OP_CIRCUMFLEX_ASSIGN = new VimScriptElementType("^=");
  public static final TokenSet assignmentOperators = TokenSet.create(
      OP_ASSIGN, OP_PLUS_ASSIGN, OP_MINUS_ASSIGN, OP_MULT_ASSIGN, OP_DIV_ASSIGN, OP_CIRCUMFLEX_ASSIGN
  );

  public static final IElementType OP_PLUS = new VimScriptElementType("+");
  public static final IElementType OP_MINUS = new VimScriptElementType("-");
  public static final IElementType OP_MULT = new VimScriptElementType("*");
  public static final IElementType OP_DIV = new VimScriptElementType("/");
  public static final IElementType OP_MOD = new VimScriptElementType("%");
  public static final TokenSet operators = TokenSet.create(
      OP_PLUS, OP_MINUS, OP_MULT, OP_DIV, OP_MOD
  );

  //logic
  public static final IElementType OP_EQUAL_TO = new VimScriptElementType("==");
  public static final IElementType OP_NOT_EQUAL_TO = new VimScriptElementType("!=");
  public static final IElementType OP_GT = new VimScriptElementType(">");
  public static final IElementType OP_GT_EQ = new VimScriptElementType(">=");
  public static final IElementType OP_LT = new VimScriptElementType("<");
  public static final IElementType OP_LT_EQ = new VimScriptElementType("<=");
  public static final IElementType OP_MATCHES = new VimScriptElementType("=~");
  public static final IElementType OP_NOT_MATCHES = new VimScriptElementType("!~");

  public static final IElementType OP_LOGICAL_OR = new VimScriptElementType("||");
  public static final IElementType OP_LOGICAL_AND = new VimScriptElementType("&&");

  public static final IElementType AMPERSAND = new VimScriptElementType("&");

  /* separators */
  public static final IElementType COLON = new VimScriptElementType(":");
  public static final IElementType DOT = new VimScriptElementType(".");
  public static final IElementType QUESTION_MARK = new VimScriptElementType("?");
  public static final IElementType EXCLAMATION_MARK = new VimScriptElementType("!");

  /* identifiers */
  public static final IElementType ENVIRONMENT_VARIABLE = new VimScriptElementType("envvar");
  public static final IElementType OPTION = new VimScriptElementType("option");
  public static final IElementType REGISTER = new VimScriptElementType("register");
  public static final IElementType VARIABLE_WITH_PREFIX = new VimScriptElementType("prefix:varname");
  public static final IElementType IDENTIFIER = new VimScriptElementType("identifier");
  public static final TokenSet identifiers = TokenSet.create(
      ENVIRONMENT_VARIABLE, OPTION, REGISTER, VARIABLE_WITH_PREFIX, IDENTIFIER
  );

  /* numbers */
  public static final IElementType FLOAT = new VimScriptElementType("float");
  public static final IElementType INTEGER = new VimScriptElementType("int");
  public static final TokenSet number = TokenSet.create(
      FLOAT, INTEGER
  );

  /* string */
  public static final IElementType STRING = new VimScriptElementType("string");
  public static final TokenSet strings = TokenSet.create(
      STRING
  );

  /* comment */
  public static final IElementType COMMENT = new VimScriptElementType("comment");
  public static final TokenSet comments = TokenSet.create(
      COMMENT
  );
}
