/* Generated By:JavaCC: Do not edit this line. PolicyDefParserConstants.java */
package org.mxupdate.update.datamodel.policy;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface PolicyDefParserConstants_mxJPO {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int DESCRIPTION = 5;
  /** RegularExpression Id. */
  int TYPE = 6;
  /** RegularExpression Id. */
  int FORMAT = 7;
  /** RegularExpression Id. */
  int STORE = 8;
  /** RegularExpression Id. */
  int DEFAULTFORMAT = 9;
  /** RegularExpression Id. */
  int SEQUENCE = 10;
  /** RegularExpression Id. */
  int HIDDEN = 11;
  /** RegularExpression Id. */
  int STATE = 12;
  /** RegularExpression Id. */
  int REGISTEREDNAME = 13;
  /** RegularExpression Id. */
  int REVISION = 14;
  /** RegularExpression Id. */
  int VERSION = 15;
  /** RegularExpression Id. */
  int PROMOTE = 16;
  /** RegularExpression Id. */
  int CHECKOUTHISTORY = 17;
  /** RegularExpression Id. */
  int OWNER = 18;
  /** RegularExpression Id. */
  int PUBLIC = 19;
  /** RegularExpression Id. */
  int USER = 20;
  /** RegularExpression Id. */
  int FILTER = 21;
  /** RegularExpression Id. */
  int ACTION = 22;
  /** RegularExpression Id. */
  int CHECK = 23;
  /** RegularExpression Id. */
  int OVERRIDE = 24;
  /** RegularExpression Id. */
  int INPUT = 25;
  /** RegularExpression Id. */
  int TRIGGER = 26;
  /** RegularExpression Id. */
  int APPROVE = 27;
  /** RegularExpression Id. */
  int IGNORE = 28;
  /** RegularExpression Id. */
  int REJECT = 29;
  /** RegularExpression Id. */
  int BRANCH = 30;
  /** RegularExpression Id. */
  int SIGNATURE = 31;
  /** RegularExpression Id. */
  int ROUTE = 32;
  /** RegularExpression Id. */
  int BOOLEAN_TRUE = 37;
  /** RegularExpression Id. */
  int BOOLEAN_FALSE = 38;
  /** RegularExpression Id. */
  int BOOLEAN_TRUESTR = 39;
  /** RegularExpression Id. */
  int BOOLEAN_FALSESTR = 40;
  /** RegularExpression Id. */
  int LS_CURLY_BRACKET_OPEN = 45;
  /** RegularExpression Id. */
  int LS_CURLY_BRACKET_CLOSE = 46;
  /** RegularExpression Id. */
  int LS_STRING = 47;
  /** RegularExpression Id. */
  int LS_SINGLE_STRING = 48;
  /** RegularExpression Id. */
  int LS_APOSTROPHE = 49;
  /** RegularExpression Id. */
  int LS_CHAR = 50;
  /** RegularExpression Id. */
  int SL_STRING = 55;
  /** RegularExpression Id. */
  int SL_SINGLE_STRING = 56;
  /** RegularExpression Id. */
  int SL_APOSTROPHE = 57;
  /** RegularExpression Id. */
  int SL_CHAR = 58;
  /** RegularExpression Id. */
  int S_STRING = 63;
  /** RegularExpression Id. */
  int S_SINGLE_STRING = 64;
  /** RegularExpression Id. */
  int S_APOSTROPHE = 65;
  /** RegularExpression Id. */
  int S_CHAR = 66;
  /** RegularExpression Id. */
  int L_CURLY_BRACKET_OPEN = 71;
  /** RegularExpression Id. */
  int L_CURLY_BRACKET_CLOSE = 76;
  /** RegularExpression Id. */
  int L_STRING = 77;
  /** RegularExpression Id. */
  int L_SINGLE_STRING = 78;
  /** RegularExpression Id. */
  int L_APOSTROPHE = 79;
  /** RegularExpression Id. */
  int L_CHAR = 80;
  /** RegularExpression Id. */
  int L2_CURLY_BRACKET_OPEN = 81;
  /** RegularExpression Id. */
  int L3_BRACE_STRING = 82;
  /** RegularExpression Id. */
  int L3_CHAR = 83;
  /** RegularExpression Id. */
  int L4_CURLY_BRACKET_CLOSE = 84;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int B_EXPECTED = 1;
  /** Lexical state. */
  int LS_EXPECTED = 2;
  /** Lexical state. */
  int SL_EXPECTED = 3;
  /** Lexical state. */
  int S_EXPECTED = 4;
  /** Lexical state. */
  int L_EXPECTED = 5;
  /** Lexical state. */
  int L2_EXPECTED = 6;
  /** Lexical state. */
  int L3_EXPECTED = 7;
  /** Lexical state. */
  int L4_EXPECTED = 8;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"description\"",
    "\"type\"",
    "\"format\"",
    "\"store\"",
    "\"defaultformat\"",
    "\"sequence\"",
    "\"hidden\"",
    "\"state\"",
    "\"registeredName\"",
    "\"revision\"",
    "\"version\"",
    "\"promote\"",
    "\"checkouthistory\"",
    "\"owner\"",
    "\"public\"",
    "\"user\"",
    "\"filter\"",
    "\"action\"",
    "\"check\"",
    "\"override\"",
    "\"input\"",
    "\"trigger\"",
    "\"approve\"",
    "\"ignore\"",
    "\"reject\"",
    "\"branch\"",
    "\"signature\"",
    "\"route\"",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<BOOLEAN_TRUE>",
    "<BOOLEAN_FALSE>",
    "<BOOLEAN_TRUESTR>",
    "<BOOLEAN_FALSESTR>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"{\"",
    "\"}\"",
    "<LS_STRING>",
    "<LS_SINGLE_STRING>",
    "\"\\\"\"",
    "<LS_CHAR>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<SL_STRING>",
    "<SL_SINGLE_STRING>",
    "\"\\\"\"",
    "<SL_CHAR>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<S_STRING>",
    "<S_SINGLE_STRING>",
    "\"\\\"\"",
    "<S_CHAR>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"{\"",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"}\"",
    "<L_STRING>",
    "<L_SINGLE_STRING>",
    "\"\\\"\"",
    "<L_CHAR>",
    "\"{\"",
    "<L3_BRACE_STRING>",
    "<L3_CHAR>",
    "\"}\"",
    "\"{\"",
    "\"}\"",
  };

}
