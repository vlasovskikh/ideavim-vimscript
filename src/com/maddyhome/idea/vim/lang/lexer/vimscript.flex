/**
* VimScript lexer.
*/

package com.maddyhome.idea.vim.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.maddyhome.idea.vim.lang.lexer.VimScriptTokenTypes.*;

%%

%class _VimScriptLexer
%implements FlexLexer
%final
%public

%unicode

%function advance
%type IElementType


Char = .
InputChar = [^\r\n]
SpaceChar = [\ \t]
Space = {SpaceChar}{SpaceChar}*
NewLineChar = \r|\n|\r\n
EscapedChar = \\{Char}

Digit = [0-9]

//Integers
Decimal = 0 | [-+]?[1-9]{Digit}*
Hexadecimal = 0[xX]{Digit}+
Octal = 0[0-7]+
Integer = {Decimal} | {Hexadecimal} | {Octal}

//Floating point number
Float = [-+]?(0 | [1-9]{Digit}*)\.({Digit}+)([eE][-+]?{Digit}*)?

//Comment
Comment = {InputChar}*

//Identifier
Name = [A-Za-z_][_A-Za-z0-9]*
VariableWithPrefix = [abglstvw]:{Name}
EnvironmentVariable = \${Name}
Option = &{Name}
Register = @{Name}
Identifier = {Name}
KeyString = [_A-Za-z0-9]+

%state STRING_DQ
%state STRING_SQ

%%

<YYINITIAL> {
  {EscapedChar}               { return ESCAPED_CHAR; }
  {NewLineChar}               { return NEW_LINE; }

  // operators
  //logic operators
  "=="                        { return OP_EQUAL_TO; }
  "!="                        { return OP_NOT_EQUAL_TO; }
  ">="                        { return OP_GT_EQ; }
  ">"                         { return OP_GT; }
  "<="                        { return OP_LT_EQ; }
  "<"                         { return OP_LT; }
  "=~"                        { return OP_MATCHES; }
  "!~"                        { return OP_NOT_MATCHES; }
  "||"                        { return OP_LOGICAL_OR; }
  "&&"                        { return OP_LOGICAL_AND; }

  //assign operators
  "+="                        { return OP_PLUS_ASSIGN; }
  "-="                        { return OP_MINUS_ASSIGN; }
  "*="                        { return OP_MULT_ASSIGN; }
  "/="                        { return OP_DIV_ASSIGN; }
  "^="                        { return OP_CIRCUMFLEX_ASSIGN; }
  ".="                        { return OP_DOT_ASSIGN; }
  "="                         { return OP_ASSIGN; }

  //unary operators
  "+"                         { return OP_PLUS; }
  "-"                         { return OP_MINUS; }
  "*"                         { return OP_MULT; }
  "/"                         { return OP_DIV; }
  "%"                         { return OP_MOD; }

  {Float}                     { return FLOAT; }
  {Integer}                   { return INTEGER; }
  {VariableWithPrefix}        { return VARIABLE_WITH_PREFIX; }
  {EnvironmentVariable}       { return ENVIRONMENT_VARIABLE; }
  {Option}                    { return OPTION; }
  {Register}                  { return REGISTER; }
  {Identifier}                { return IDENTIFIER; }
  {KeyString}                 { return DICT_KEY_STRING; }

  \'                          { yybegin(STRING_SQ); return SINGLE_QUOTE; }
  \"                          { yybegin(STRING_DQ); return DOUBLE_QUOTE; }

  // braces
  "("                         { return LEFT_ROUND_BRACKET; }
  ")"                         { return RIGHT_ROUND_BRACKET; }
  "["                         { return LEFT_SQUARE_BRACKET; }
  "]"                         { return RIGHT_SQUARE_BRACKET; }
  "{"                         { return LEFT_CURLY_BRACKET; }
  "}"                         { return RIGHT_CURLY_BRACKET; }

  // separators
  "&"                         { return AMPERSAND; }
  ":"                         { return COLON; }
  ";"                         { return SEMICOLON; }
  "."                         { return DOT; }
  ","                         { return COMMA; }
  "?"                         { return QUESTION_MARK; }
  "!"                         { return EXCLAMATION_MARK; }
  "#"                         { return NUMBER_SIGN; }

  {Space}                     { return WHITESPACE; }
}

<STRING_DQ> {
  [^\n\r\"]+"\n"              { return COMMENT; }
  [^\n\r\"]+                  { return STRING; }
  \"                          { yybegin(YYINITIAL); return DOUBLE_QUOTE; }
}

<STRING_SQ> {
  [^\n\r\']+                  { return STRING; }
  \'                          { yybegin(YYINITIAL); return SINGLE_QUOTE; }
}

<<EOF>>                       { return null; }
.                             { return BAD_CHARACTER; }
