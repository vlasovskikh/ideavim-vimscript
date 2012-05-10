package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.maddyhome.idea.vim.lang.parser.VimScriptElementTypes.*;
import static com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes.*;

/**
 * <p>Date: 08.11.11</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptParser implements PsiParser {
  private PsiBuilder builder;
  
  private static final int TERNARY_EXPRESSION_LEVEL = 0;
  private static final int OR_EXPRESSION_LEVEL = 1;
  private static final int AND_EXPRESSION_LEVEL = 2;
  private static final int COMPARISON_EXPRESSION_LEVEL = 3;
  private static final int PMD_EXPRESSION_LEVEL = 4;
  private static final int MDM_EXPRESSION_LEVEL = 5;
  private static ArrayList<ArrayList<IElementType>> operatorsByLevel = new ArrayList<ArrayList<IElementType>>(9);

  static {
    // It's ternary expression.
    operatorsByLevel.get(TERNARY_EXPRESSION_LEVEL).add(QUESTION_MARK);
    operatorsByLevel.get(TERNARY_EXPRESSION_LEVEL).add(COLON);
    
    operatorsByLevel.get(OR_EXPRESSION_LEVEL).add(OP_LOGICAL_OR);
    
    operatorsByLevel.get(AND_EXPRESSION_LEVEL).add(OP_LOGICAL_AND);
    
    for (IElementType type : comparisonOperators.getTypes()) {
      operatorsByLevel.get(COMPARISON_EXPRESSION_LEVEL).add(type);
    }
    
    operatorsByLevel.get(PMD_EXPRESSION_LEVEL).add(OP_PLUS);
    operatorsByLevel.get(PMD_EXPRESSION_LEVEL).add(OP_MINUS);
    operatorsByLevel.get(PMD_EXPRESSION_LEVEL).add(DOT);
    
    operatorsByLevel.get(MDM_EXPRESSION_LEVEL).add(OP_MULT);
    operatorsByLevel.get(MDM_EXPRESSION_LEVEL).add(OP_DIV);
    operatorsByLevel.get(MDM_EXPRESSION_LEVEL).add(OP_MOD);

    // It's unary expression, may be there is no need in this element.
    // operatorsByLevel.get(6);
    
    // It's: list item, sublist, dictionary item. May not be needed, too. 
    // operatorsByLevel.get(7);
    
    // It's: value, variable, nested expression, function call. May not be needed.
    // operatorsByLevel.get(8);
  }

  @NotNull
  @Override
  public ASTNode parse(IElementType root, PsiBuilder psiBuilder) {
    this.builder = psiBuilder; //new VimScriptPsiBuilder(psiBuilder);
    this.builder.setDebugMode(true);
    final PsiBuilder.Marker rootMark = builder.mark();

    if (builder.eof()) {
      final PsiBuilder.Marker marker = builder.mark();
      marker.done(EMPTY);
    }
    else {
      while (!builder.eof()) {
        if (atToken(NEW_LINE)) {
          advanceToFirstTokenOfNewLine();
        }
        else {
          parseStatement();
        }
      }
    }

    rootMark.done(VimScriptParserDefinition.VIM_SCRIPT_FILE);
    return builder.getTreeBuilt();
  }

  private void parseStatement() {
    skipWhitespaces();
    final PsiBuilder.Marker mark = builder.mark();
    if (atToken(IDENTIFIER, "set") || atToken(IDENTIFIER, "se")) {
      parseSetStatement(mark);

    }
    else if (atToken(IDENTIFIER, "let")) {
      parseLetStatement(mark);
    }
    else {
      advanceToNewLineCharacter();
      mark.error("Set or let expression expected");
    }
    if (atToken(WHITESPACE)) {
      skipWhitespaces();
    }
    if (atToken(NEW_LINE)) {
      advanceLexer();
    }
  }

  /**
   * Parses 'set' statements.
   * @param startMark marks the position before 'set' keyword.
   */
  private void parseSetStatement(PsiBuilder.Marker startMark) {
    PsiBuilder.Marker keyword = builder.mark();
    advanceLexer();
    keyword.done(KEYWORD);
    skipWhitespaces();

    if (eol()) {
      startMark.done(SET_STMT);
      advanceToNewLineCharacter();
    }
    else {
      parseSetOptions();
      startMark.done(SET_STMT);
      advanceLexer();
    }
  }

  private void parseSetOptions() {
    do {
      if (atToken(IDENTIFIER, "all")) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();
        }
        else if (atToken(AMPERSAND)) {
          advanceLexer();
          if (whitespace()) {
            mark.done(SET_OPTION);
            skipWhitespaces();
          }
          else {
            advanceToNewLineCharacter();
            mark.error("'all&' option expected");
          }
        }
        else {
          advanceToNewLineCharacter();
          mark.error("'all' option expected");
        }
      }
      else if (atToken(IDENTIFIER, "termcap")) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();
        }
        else {
          advanceToNewLineCharacter();
          mark.error("'termcap' option expected");
        }
      }
      else if (atToken(IDENTIFIER) && (startsWith("no") || startsWith("inv"))) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();
        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();
        }
        else {
          advanceToNewLineCharacter();
          mark.error("'no{option}' or 'inv{option}' expected");
        }
      }
      else if (atToken(IDENTIFIER)) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (atToken(QUESTION_MARK) || atToken(EXCLAMATION_MARK) || atToken(AMPERSAND)) {
          advanceLexer();
          if (whitespace()) {
            mark.done(SET_OPTION);
            skipWhitespaces();
          }
          else {
            advanceToNewLineCharacter();
            mark.error("{option}[? || ! || &] expected.");
          }

        }
        else if (atToken(OP_ASSIGN) || atToken(COLON) || atToken(OP_PLUS_ASSIGN) ||
            atToken(OP_CIRCUMFLEX_ASSIGN) || atToken(OP_MINUS_ASSIGN)) {
          advanceLexer();
          if (atToken(identifier) || atToken(number)) {
            advanceLexer();
            if (whitespace()) {
              mark.done(SET_OPTION);
              skipWhitespaces();
            }
            else if (atToken(COMMA)) {
              while (atToken(IDENTIFIER, COMMA)) {
                advanceLexer();
              }
              if (whitespace()) {
                mark.done(SET_OPTION);
                skipWhitespaces();
              } else {
                advanceToNewLineCharacter();
                mark.error("String expected");
              }
            }
            else {
              advanceToNewLineCharacter();
              mark.error("Identifier expected");
            }
          }
          else {
            advanceToNewLineCharacter();
            mark.error("Value or variable expected");
          }
        }
        else if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();
        }
        else {
          mark.error("Smth different expected");
        }
      }
      else if (!atToken(IDENTIFIER) && !atToken(WHITESPACE) && !eol()) {
        PsiBuilder.Marker mark = builder.mark();
        advanceToNewLineCharacter();
        mark.error("Identifier expected");
      }
    } while (!eol());
  }

  /**
   * Parses 'let' statement.
   * @param startMark marks the position before 'let' keyword.
   * @return true if parsed successful, false otherwise.
   */
  private void parseLetStatement(PsiBuilder.Marker startMark) {
    PsiBuilder.Marker keyword = builder.mark();
    advanceLexer();
    keyword.done(KEYWORD);
    skipWhitespaces();

    if (eol()) {
      startMark.done(LET_STMT);
      advanceToNewLineCharacter();

    }
    else if (atToken(identifier)) {
      IElementType varType = builder.getTokenType();
      PsiBuilder.Marker mark = builder.mark();
      advanceLexer();
      mark.done(VARIABLE);
      skipWhitespaces();

      if (eol()) {
        startMark.done(LET_STMT);
        advanceToNewLineCharacter();

      }
      else {
        if (atToken(identifier)) {
          while (!eol()) {
            mark = builder.mark();
            advanceLexer();
            mark.done(VARIABLE);
            skipWhitespaces();
          }
        }
        else {
          PsiBuilder.Marker assignmentStart = mark.precede();
          if (ENVIRONMENT_VARIABLE.equals(varType) || REGISTER.equals(varType)) {
            if (atToken(OP_ASSIGN, OP_DOT_ASSIGN)) {
              advanceLexerSkippingWhitespaces();
              PsiBuilder.Marker expression = builder.mark();
              parseExpression(expression);
              assignmentStart.done(ASSIGNMENT_STMT);
              advanceToNewLineCharacter();

            }
            else {
              advanceToNewLineCharacter();
              assignmentStart.error("Assign operation for $env or @reg expected.");
            }
          }
          else if (IDENTIFIER.equals(varType) || OPTION.equals(varType)) {
            if (atToken(OP_ASSIGN, OP_DOT_ASSIGN, OP_MINUS_ASSIGN, OP_PLUS_ASSIGN)) {
              advanceLexerSkippingWhitespaces();
              PsiBuilder.Marker expression = builder.mark();
              parseExpression(expression);
              assignmentStart.done(ASSIGNMENT_STMT);
              advanceToNewLineCharacter();

            }
            else {
              advanceToNewLineCharacter();
              assignmentStart.error("Assign operator for variable or &option expected.");
            }
          }
          // ListItem | Sublist | DictEntry
          // [var0, [var1, ...] [: varn] ]
          else {
            advanceToNewLineCharacter();
            assignmentStart.error("Could not parse assignment statement.");
          }
          
        }
        startMark.done(LET_STMT);
        advanceToNewLineCharacter();

      }
    }
    else {
      advanceToNewLineCharacter();
      startMark.error("Variable expected.");
    }
  }

  private boolean parseExpression(PsiBuilder.Marker startMarker) {
    parseTernaryExpression(startMarker, false);
    return true;
  }

  private boolean parseExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    parseTernaryExpression(startMarker, isNested);
    return true;
  }

  private void parseTernaryExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker condition = builder.mark();
    parseOrExpression(condition, false);
    if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET))) {
      // lower-level expression
      startMarker.drop();

    }
    else if (atToken(QUESTION_MARK)) {
      // continue parsing
      condition = condition.precede();
      condition.done(CONDITION);
      advanceLexerSkippingWhitespaces();
      
      PsiBuilder.Marker thenValue = builder.mark();
      parseTernaryExpression(thenValue, false);

      if (atToken(COLON)) {
        // right, continue parsing
        thenValue.done(TERNARY_THEN);
        advanceLexerSkippingWhitespaces();
        
        PsiBuilder.Marker elseValue = builder.mark();
        parseTernaryExpression(elseValue, isNested);
        elseValue.done(TERNARY_ELSE);
        startMarker.done(TERNARY_EXPRESSION);

      }
      else {
        thenValue.drop();
        advanceToNewLineCharacter();
        startMarker.error("':' expected.");
      }
    }
    else {
      condition.drop();
      advanceToNewLineCharacter();
      startMarker.error("Newline or '?' expected.");
    }
    skipWhitespaces();
  }

  private void parseOrExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker elem = builder.mark();
    parseAndExpression(elem, isNested);
    if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET)) || isHigherLevelToken(OR_EXPRESSION_LEVEL)) {
      // lower-level expression
      startMarker.drop();

    }
    else if (atToken(OP_LOGICAL_OR)) {
      // this-level expression
      while (true) {
        elem = builder.mark();
        parseAndExpression(elem, isNested);
        if (atToken(OP_LOGICAL_OR)) {
          advanceLexerSkippingWhitespaces();
        }
        else if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET)) || isHigherLevelToken(OR_EXPRESSION_LEVEL)) {
          startMarker.done(OR_EXPRESSION);
          break;
        }
        else {
          advanceToNewLineCharacter();
          startMarker.error("Something wrong with '||'.");
          break;
        }
      }
    }
    else {
      advanceToNewLineCharacter();
      startMarker.error("Newline or '||' expected.");
    }
    skipWhitespaces();
  }

  private void parseAndExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker elem = builder.mark();
    parseComparisonExpression(elem, isNested);

    if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET)) || isHigherLevelToken(AND_EXPRESSION_LEVEL)) {
      // lower-level expression
      startMarker.drop();

    }
    else if (atToken(OP_LOGICAL_AND)) {
      // this-level expression
      while (true) {
        elem = builder.mark();
        parseComparisonExpression(elem, isNested);
        if (atToken(OP_LOGICAL_AND)) {
          advanceLexerSkippingWhitespaces();
        }
        else if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET)) || isHigherLevelToken(AND_EXPRESSION_LEVEL)) {
          startMarker.done(AND_EXPRESSION);
          break;
        }
        else {
          advanceToNewLineCharacter();
          startMarker.error("Something wrong with '&&'.");
          break;
        }
      }
    }
    else {
      advanceToNewLineCharacter();
      startMarker.error("&& smth expected.");
    }
    skipWhitespaces();
  }

  private void parseComparisonExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker left = builder.mark();
    parsePlusMinusDotExpression(left, isNested);
    if (eol()) {
      // lower-level expression
      startMarker.drop();

    }
    else if (atToken(comparisonOperators)) {
      advanceLexer();
      if (atToken(QUESTION_MARK, NUMBER_SIGN)) {
        advanceLexer();
      }
      skipWhitespaces();
      PsiBuilder.Marker right = builder.mark();
      parsePlusMinusDotExpression(right, isNested);
      startMarker.done(COMPARISON_EXPRESSION);
      skipWhitespaces();

    }
    else {
      advanceToNewLineCharacter();
      startMarker.error("Newline or one of comparison operators expected.");
    }
  }

  private void parsePlusMinusDotExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker elem = builder.mark();
    parseMultDivModExpression(elem, isNested);
    if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET) || isHigherLevelToken(PMD_EXPRESSION_LEVEL))) {
      // lower-level expression
      startMarker.drop();

    }
    else if (atToken(OP_PLUS, OP_MINUS, DOT)) {
      skipWhitespaces();
      while (true) {
        elem = builder.mark();
        parseMultDivModExpression(elem, isNested);
        if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET) || isHigherLevelToken(PMD_EXPRESSION_LEVEL))) {
          startMarker.done(PLUS_MINUS_DOT_EXPRESSION);
          break;
        }
        else if (atToken(OP_PLUS, OP_MINUS, DOT)) {
          advanceLexerSkippingWhitespaces();
        }
        else {
          advanceToNewLineCharacter();
          startMarker.error("Something wrong with '+', '-' or '.'.");
        }
      }
    }
    else {
      advanceToNewLineCharacter();
      startMarker.error("Newline or op. with higher priority or one of '+', '-', '.', ')' expected.");
    }
    skipWhitespaces();
  }

  private void parseMultDivModExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    PsiBuilder.Marker elem = builder.mark();
    parseUnaryExpression(elem, isNested);
    if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET)) || isHigherLevelToken(MDM_EXPRESSION_LEVEL)) {
      startMarker.drop();

    }
    else if (atToken(OP_MULT, OP_DIV, OP_MOD)) {
      skipWhitespaces();
      while (true) {
        elem = builder.mark();
        parseUnaryExpression(elem, isNested);
        if (eol() || (isNested && atToken(RIGHT_ROUND_BRACKET) || isHigherLevelToken(MDM_EXPRESSION_LEVEL))) {
          startMarker.done(MULT_DIV_MOD_EXPRESSION);
          break;
        }
        else if (atToken(OP_MULT, OP_DIV, OP_MOD)) {
          advanceLexerSkippingWhitespaces();
        }
        else {
          advanceToNewLineCharacter();
          startMarker.error("Something wrong with '*', '/' or '%'.");
        }
      }
    }
    else {
      advanceToNewLineCharacter();
      startMarker.error("Newline or op. with higher priority or one of '*', '/', '%', ')' expected.");
    }
    skipWhitespaces();
  }

  private void parseUnaryExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    if (atToken(OP_PLUS, OP_MINUS, EXCLAMATION_MARK)) {
      advanceLexerSkippingWhitespaces();
    }
    PsiBuilder.Marker expr = builder.mark();
    parseCollectionElemExpression(expr, isNested);
    startMarker.done(UNARY_EXPRESSION);
  }

  private void parseCollectionElemExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    if (atToken(identifier)) {
      PsiBuilder.Marker var = builder.mark();
      advanceLexer();
      if (atToken(DOT)) {
        // dictionary entry
        var.done(DICT_NAME);
        advanceLexer();
        PsiBuilder.Marker key = builder.mark();
        if (atToken(IDENTIFIER, DICT_KEY_STRING)) {
          advanceLexer();
          key.done(DICT_KEY);
          startMarker.done(COLLECTION_ITEM_EXPRESSION);
        }
        else {
          advanceToNewLineCharacter();
          startMarker.error("Wrong dictionary key");
        }
      }
      else if (atToken(LEFT_SQUARE_BRACKET)) {
        // list item or sublist
        advanceLexer();

      }
      else {
        parseLowestLevelExpression(startMarker, isNested);
      }
    }
    else {
      parseLowestLevelExpression(startMarker, isNested);
    }
    skipWhitespaces();
  }

  private void parseLowestLevelExpression(PsiBuilder.Marker startMarker, boolean isNested) {
    startMarker.done(VALUE);
  }
  
  private boolean isHigherLevelToken(int level) {
    for (int i = 0; i < level; ++i) {
      if (operatorsByLevel.get(i).contains(builder.getTokenType())) {
        return true;
      }
    }
    return false;
  }
  
  private boolean atToken(@NotNull TokenSet tokenSet, TokenSet ... tokenSets) {
    if (tokenSet.contains(builder.getTokenType())) {
      return true;
    }
    for (TokenSet ts : tokenSets) {
      if (ts.contains(builder.getTokenType())) {
        return true;
      }
    }
    return false;
  }

  private boolean atToken(@NotNull IElementType elementType, IElementType ... elementTypes) {
    if (elementType.equals(builder.getTokenType())) {
      return true;
    }
    for (IElementType et : elementTypes) {
      if (et.equals(builder.getTokenType())) {
        return true;
      }
    }
    return false;
  }

  private boolean atToken(@NotNull TokenSet tokenSet) {
    return tokenSet.contains(builder.getTokenType());
  }

  private boolean atToken(@NotNull IElementType elementType, @NotNull String text) {
    return elementType.equals(builder.getTokenType()) &&
           text.equals(builder.getTokenText());
  }

  private boolean startsWith(@NotNull String text) {
    return builder.getTokenText() != null && builder.getTokenText().startsWith(text);
  }

  private boolean whitespace() {
    return atToken(WHITESPACE) || eol();
  }

  private boolean eol() {
    PsiBuilder.Marker marker = builder.mark();
    if (atToken(WHITESPACE)) {
      skipWhitespaces();
    }
    boolean isEOL = NEW_LINE.equals(builder.getTokenType()) || builder.eof();
    marker.rollbackTo();
    return isEOL;
  }


  /**
   * Various advance functions.
   */

  private void advanceLexer() {
    builder.advanceLexer();
  }

  private void advanceLexerSkippingWhitespaces() {
    do {
      advanceLexer();
    } while (atToken(WHITESPACE) && !builder.eof());
  }

  private void advanceToNewLineCharacter() {
    while (!atToken(NEW_LINE) && !builder.eof()) {
      advanceLexer();
    }
  }

  private void advanceToFirstTokenOfNewLine() {
    advanceToNewLineCharacter();
    advanceLexer();
  }

  private void skipWhitespaces() {
    while (atToken(WHITESPACE) && !builder.eof()) {
      advanceLexer();
    }
  }
}