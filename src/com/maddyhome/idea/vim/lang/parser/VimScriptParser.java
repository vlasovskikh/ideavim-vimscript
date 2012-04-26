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
    this.builder = psiBuilder;//new VimScriptPsiBuilder(psiBuilder);
    this.builder.setDebugMode(true);
    final PsiBuilder.Marker rootMark = builder.mark();

    if (builder.eof()) {
      final PsiBuilder.Marker marker = builder.mark();
      marker.done(EMPTY_INPUT);
    }
    else {
      while (!builder.eof()) {
        if (atToken(NEW_LINE)) {
          advanceToFirstTokenOfNewLine();
        }
        else {
          parseBlock();
        }
      }
    }

    rootMark.done(VimScriptParserDefinition.VIM_SCRIPT_FILE);
    return builder.getTreeBuilt();
  }

  private void parseBlock() {
    PsiBuilder.Marker block = builder.mark();
    skipWhitespaces();
    final PsiBuilder.Marker mark = builder.mark();
    if (atToken(IDENTIFIER, "set") || atToken(IDENTIFIER, "se")) {
      if (!parseSetStmt(mark)) {
        advanceToNewLineCharacter();
        mark.error("Could not parse set stmt");
      }

    }
    else if (atToken(IDENTIFIER, "let")) {
      if (!parseLetStatement(mark)) {
        advanceToNewLineCharacter();
        mark.error("Could not parse let statement");
      }
    }
    else {
      advanceToNewLineCharacter();
      mark.error("Set or let expression expected");
    }
    if (atToken(WHITESPACE)) {
      skipWhitespaces();
    }
    block.done(BLOCK);
    if (atToken(NEW_LINE)) {
      advanceLexer();
    }
  }

  /**
   * Parses 'set' statements.
   * @param startMark marks the position before 'set' keyword.
   * @return true if parsed successful, false otherwise.
   */
  private boolean parseSetStmt(PsiBuilder.Marker startMark) {
    PsiBuilder.Marker keyword = builder.mark();
    advanceLexer();
    keyword.done(KEYWORD);
    skipWhitespaces();

    if (endl()) {
      startMark.done(SET_STMT);
      advanceToNewLineCharacter();
      return true;

    }
    else {
      if (parseOptions()) {
        startMark.done(SET_STMT);
        advanceLexer();
        return true;
      }
      else {
        advanceToNewLineCharacter();
        startMark.error("Options list expected");
      }
    }
    return false;
  }

  private boolean parseOptions() {
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
    return true;
  }

  /**
   * Parses 'let' statement.
   * @param startMark marks the position before 'let' keyword.
   * @return true if parsed successful, false otherwise.
   */
  private boolean parseLetStatement(PsiBuilder.Marker startMark) {
    PsiBuilder.Marker keyword = builder.mark();
    advanceLexer();
    keyword.done(KEYWORD);
    skipWhitespaces();

    if (endl()) {
      startMark.done(LET_STMT);
      advanceToNewLineCharacter();
      return true;

    }
    else if (atToken(identifier)) {
      PsiBuilder.Marker mark = builder.mark();
      advanceLexer();

      if (endl()) {
        mark.done(VARIABLE);
        skipWhitespaces();
        startMark.done(LET_STMT);
        advanceToNewLineCharacter();
        return true;

      }
      else if (atToken(assignmentOperator)) {
        advanceLexerSkippingWhitespaces();
        if (!parseAssignmentStatement(mark)) {
          advanceToNewLineCharacter();
          startMark.error("Error parsing assignment statement.");
        }
      }
      else {
        advanceToNewLineCharacter();
        startMark.error("End of line or assignment stmt expected.");
      }
    }
    else {
      advanceToNewLineCharacter();
      startMark.error("Variable expected.");
    }
    return false;
  }

  private boolean parseAssignmentStatement(PsiBuilder.Marker startMarker) {
    if (atToken(ENVIRONMENT_VARIABLE, REGISTER)) {
      PsiBuilder.Marker var = builder.mark();
      advanceLexer();
      var.done(VARIABLE);
      skipWhitespaces();

      if (atToken(OP_ASSIGN, OP_DOT_ASSIGN)) {
        advanceLexerSkippingWhitespaces();

        // Here should be expression
      }
      else {
        advanceToNewLineCharacter();
        startMarker.error("Assign operation for $env or @reg expected.");
      }
    }
    else if (atToken(IDENTIFIER, OPTION)) {
      if (atToken(OP_ASSIGN, OP_DOT_ASSIGN, OP_MINUS_ASSIGN, OP_PLUS_ASSIGN)) {
        advanceLexerSkippingWhitespaces();

        // Here should be expression
      }
      else {
        advanceToNewLineCharacter();
        startMarker.error("Assign operator for variable or &option expected.");
      }
    }
    // Here should be array assignments
    else {
      advanceToNewLineCharacter();
      startMarker.error("Could not parse assignment statement.");
    }
    return false;
  }

  private boolean parseExpression(PsiBuilder.Marker startMarker) {
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