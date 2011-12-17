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
    } else {
      while (!builder.eof()) {
        parseBlock();
      }
    }

    rootMark.done(VimScriptParserDefinition.VIM_SCRIPT_FILE);
    return builder.getTreeBuilt();
  }

  private void parseBlock() {
    PsiBuilder.Marker block = builder.mark();
    skipWhitespaces();
    if (atToken(IDENTIFIER, "set") || atToken(IDENTIFIER, "se")) {
      final PsiBuilder.Marker mark = builder.mark();
      if (!parseSetStmt(mark)) {
        mark.drop();
        builder.error("Could not parse set stmt");
      }

    } else if (atToken(WHITESPACE)) {
      skipWhitespaces();

    }
    else  if (atToken(NEW_LINE)) {
      advanceLexer();

    } else {
      builder.error("Set expression expected");
      advanceToNewLine();
    }
    block.done(BLOCK);
  }

  /**
   * Parses 'set' statements.
   * @param startMark before 'set' keyword.
   * @return true if parsed successful, false otherwise.
   */
  private boolean parseSetStmt(PsiBuilder.Marker startMark) {
    PsiBuilder.Marker keyword = builder.mark();
    advanceLexer();
    keyword.done(KEYWORD);
    advanceLexerSkippingWhitespaces();

    if (endl()) {
      startMark.done(SET_STMT);
      return true;

    } else {
      if (parseOptions()) {
        startMark.done(SET_STMT);
        advanceLexer();
        return true;
      } else {
        startMark.drop();
        builder.error("Options list expected");
        advanceUntilNewLine();
      }
    }
    return false;
  }

  private boolean parseOptions() {
    do {
      if (builder.getTokenType() != null) {
        System.out.println(builder.getTokenType() + ": " + builder.getTokenText());
      } else {
        System.out.print("!");
      }

      if (atToken(IDENTIFIER, "all")) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();

        } else if (atToken(AMPERSAND)) {
          advanceLexer();
          if (whitespace()) {
            mark.done(SET_OPTION);
            skipWhitespaces();

          } else {
            mark.drop();
            builder.error("'all&' option expected");
            advanceUntilNewLine();
          }
        } else {
          mark.drop();
          builder.error("'all' or 'all&' option expected");
          advanceUntilNewLine();
        }
      }
      else
      if (atToken(IDENTIFIER, "termcap")) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();

        } else {
          mark.drop();
          builder.error("'termcap' option expected");
          advanceUntilNewLine();
        }
      }
      else
      if (atToken(IDENTIFIER) && (startsWith("no") || startsWith("inv"))) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();
        if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();
        } else {
          mark.drop();
          builder.error("'no{option}' or 'inv{option}' expected");
          advanceUntilNewLine();
        }
      }
      else
      if (atToken(IDENTIFIER)) {
        PsiBuilder.Marker mark = builder.mark();
        advanceLexer();

        if (atToken(QUESTION_MARK) || atToken(EXCLAMATION_MARK) || atToken(AMPERSAND)) {
          advanceLexer();
          mark.done(SET_OPTION);
          advanceLexer();

        } else if (atToken(OP_ASSIGN) || atToken(COLON) || atToken(OP_PLUS_ASSIGN) ||
            atToken(OP_CIRCUMFLEX_ASSIGN) || atToken(OP_MINUS_ASSIGN)) {
          advanceLexer();
          if (atToken(identifiers) || atToken(number)) {
            advanceLexer();
            if (whitespace()) {
              mark.done(SET_OPTION);
              skipWhitespaces();
            } else {
              mark.drop();
              builder.error("Identifier expected");
              advanceUntilNewLine();
            }
          } else {
            mark.drop();
            builder.error("Value or variable expected");
            advanceUntilNewLine();
          }

        } else if (whitespace()) {
          mark.done(SET_OPTION);
          skipWhitespaces();

        } else {
          mark.drop();
          builder.error("Smth different expected");
        }
      }
      else
      if (!atToken(IDENTIFIER) && !atToken(WHITESPACE) && !endl()) {
        builder.error("Identifier expected");
        advanceUntilNewLine();
      }
    } while (!endl());
    return true;
  }

  private boolean atToken(@NotNull IElementType elementType) {
    return elementType.equals(builder.getTokenType());
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
      advanceLexerSkippingWhitespaces();
    }
    marker.rollbackTo();
    return NEW_LINE.equals(builder.getTokenType()) || builder.eof();
  }

  private void advanceLexer() {
    //System.out.print(builder.getTokenType() + ": " + builder.getTokenText() + " advance to ");
    builder.advanceLexer();
    //System.out.println(builder.getTokenType() + ": " + builder.getTokenText());
  }

  private void advanceLexerSkippingWhitespaces() {
    do {
      advanceLexer();
    } while (atToken(WHITESPACE) && !builder.eof());
  }

  private void advanceUntilNewLine() {
    do {
      advanceLexer();
    } while (!atToken(NEW_LINE) && !builder.eof());
  }

  private void advanceToNewLine() {
    while (!atToken(NEW_LINE) && !builder.eof()) {
      advanceLexer();
    }
    advanceLexer();
  }

  private void skipWhitespaces() {
    while (atToken(WHITESPACE) && !builder.eof()) {
      advanceLexer();
    }
  }
}