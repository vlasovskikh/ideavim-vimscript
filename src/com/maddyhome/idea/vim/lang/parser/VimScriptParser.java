package com.maddyhome.idea.vim.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

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

    if (endl()) {
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
      else if (!atToken(IDENTIFIER) && !atToken(WHITESPACE) && !endl()) {
        PsiBuilder.Marker mark = builder.mark();
        advanceToNewLineCharacter();
        mark.error("Identifier expected");
      }
    } while (!endl());
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

    if (endl()) {
      startMark.done(LET_STMT);
      advanceToNewLineCharacter();

    }
    else if (atToken(identifier)) {
      IElementType varType = builder.getTokenType();
      PsiBuilder.Marker mark = builder.mark();
      advanceLexer();
      mark.done(VARIABLE);
      skipWhitespaces();

      if (endl()) {
        startMark.done(LET_STMT);
        advanceToNewLineCharacter();

      }
      else {
        if (atToken(identifier)) {
          while (!endl()) {
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
    parseTernaryExpression(startMarker);
    return true;
  }

  private boolean parseTernaryExpression(PsiBuilder.Marker startMarker) {
    PsiBuilder.Marker condition = builder.mark();
    parseOrExpression(condition);
    skipWhitespaces();
    if (endl()) {
      // parsed lower-level expression
      // so there is no need in TERNARY_EXPRESSION element
      condition.drop();
      startMarker.drop();
      advanceToNewLineCharacter();

    }
    else if (atToken(QUESTION_MARK)) {
      // continue parsing
      condition.done(CONDITION);
      skipWhitespaces();
      
      PsiBuilder.Marker thenValue = builder.mark();
      parseTernaryExpression(thenValue);
      skipWhitespaces();
      
      if (atToken(COLON)) {
        // right, continue parsing
        thenValue.done(TERNARY_THEN);
        skipWhitespaces();
        
        PsiBuilder.Marker elseValue = builder.mark();
        parseTernaryExpression(elseValue);
        elseValue.done(TERNARY_ELSE);
        startMarker.done(TERNARY_EXPRESSION);

      }
      else {
        thenValue.drop();
        advanceToNewLineCharacter();
        startMarker.error("Colon and else value expected but got only {cond}?expr");
      }
    }
    else {
      condition.drop();
      advanceToNewLineCharacter();
      startMarker.error("Valid expression expected.");
    }
    return true;
  }

  private boolean parseOrExpression(PsiBuilder.Marker startMark) {
    return true;
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
    return atToken(WHITESPACE) || endl();
  }

  private boolean endl() {
    PsiBuilder.Marker marker = builder.mark();
    if (atToken(WHITESPACE)) {
      skipWhitespaces();
    }
    boolean isEndl = NEW_LINE.equals(builder.getTokenType()) || builder.eof();
    marker.rollbackTo();
    return isEndl;
  }

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