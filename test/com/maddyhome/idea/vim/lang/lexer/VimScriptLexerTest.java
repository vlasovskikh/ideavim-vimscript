package com.maddyhome.idea.vim.lang.lexer;

import com.intellij.psi.tree.IElementType;
import org.junit.*;

import java.io.*;
import java.util.ArrayList;

/**
 * <p>Date: 02.11.11</p>
 * <p>Test class for VimScriptLexer.</p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptLexerTest {
  String dir = "test/com/maddyhome/idea/vim/lang/lexer/files/";

  /* Tests only separate tokens */
  String testTokensFile = "testTokens";

  /* Pattern for more advanced tests */
  String test = "test";

  @Test
  public void testTokenFile() {
    final IElementType [] expected = {
        VimScriptTokenTypes.OP_EQUAL_TO,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_NOT_EQUAL_TO,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_GT_EQ,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_GT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_LT_EQ,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_LT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_MATCHES,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_NOT_MATCHES,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_LOGICAL_OR,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_LOGICAL_AND,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_PLUS,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_MINUS,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_MULT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_DIV,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_MOD,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OP_ASSIGN,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.INTEGER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.INTEGER,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.INTEGER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.FLOAT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.INTEGER,
        VimScriptTokenTypes.DOT,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.INTEGER,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.VARIABLE_WITH_PREFIX,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.VARIABLE_WITH_PREFIX,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.VARIABLE_WITH_PREFIX,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.COLON,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.ENVIRONMENT_VARIABLE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.OPTION,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.REGISTER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.STRING,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.STRING,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.LEFT_ROUND_BRACKET,
        VimScriptTokenTypes.RIGHT_ROUND_BRACKET,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.LEFT_SQUARE_BRACKET,
        VimScriptTokenTypes.RIGHT_SQUARE_BRACKET,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.LEFT_CURLY_BRACKET,
        VimScriptTokenTypes.RIGHT_CURLY_BRACKET,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.COLON,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.DOT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.BAD_CHARACTER,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.QUESTION_MARK,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.EXCLAMATION_MARK,
        null
    };

    try {
      File file = new File(dir + testTokensFile + ".vim");
      ArrayList<IElementType> actual = new ArrayList<IElementType>();

      VimScriptFlexLexer lexer = new VimScriptFlexLexer();
      final String data = readFile(file);
      lexer.start(data);
      while (true) {
        IElementType token = lexer.getTokenType();
        actual.add(token);
        if (token == null) {
          break;
        }
        lexer.advance();
      }

      for (int i = 0; i != expected.length; ++i) {
        System.out.print(expected[i] + " ");
      }
      System.out.println();
      for (int i = 0; i != actual.size(); ++i) {
        System.out.print(actual.get(i) + " ");
      }

      Assert.assertArrayEquals(expected, actual.toArray());

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  @Test
  public void testExpression() {
    IElementType [] expected = {
        VimScriptTokenTypes.COMMENT,
        VimScriptTokenTypes.NEW_LINE,
        VimScriptTokenTypes.IDENTIFIER,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.VARIABLE_WITH_PREFIX,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.OP_ASSIGN,
        VimScriptTokenTypes.WHITESPACE,
        VimScriptTokenTypes.INTEGER,
        null
    };
    ArrayList<IElementType> actual = new ArrayList<IElementType>();
    try {
      File file = new File(dir + test + "1.vim");

      VimScriptFlexLexer lexer = new VimScriptFlexLexer();
      final String data = readFile(file);

      lexer.start(data);

      while (true) {
        IElementType token = lexer.getTokenType();
        actual.add(token);
        if (token == null) {
          break;
        }
        lexer.advance();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    Assert.assertArrayEquals(expected, actual.toArray());
  }

  private static String readFile(File file) throws IOException {
    StringBuilder b = new StringBuilder();
    FileReader fr = new FileReader(file);
    char[] buf = new char[4096];
    while (true) {
      int n = fr.read(buf);
      if (n < 0) {
        break;
      }
      b.append(buf, 0, n);
    }
    return b.toString();
  }
}
