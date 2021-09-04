/* Parser.java */
/* Generated By:JavaCC: Do not edit this line. Parser.java */
public class Parser implements ParserConstants {

// Parser

// CU -> (decl | stmnt)*
  final public void cu() throws ParseException {
    trace_call("cu");
    try {

      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 11:
        case 16:
        case 21:
        case 23:
        case 25:
        case 27:
        case 28:
        case 29:
        case 31:
        case 32:
        case Type:
        case BoolLiteral:
        case CharLiteral:
        case StringLiteral:
        case IntegerLiteral:
        case Identifier:{
          ;
          break;
          }
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          decl();
          break;
          }
        case 11:
        case 16:
        case 21:
        case 23:
        case 25:
        case 27:
        case 28:
        case 29:
        case 31:
        case 32:
        case BoolLiteral:
        case CharLiteral:
        case StringLiteral:
        case IntegerLiteral:
        case Identifier:{
          stmnt();
          break;
          }
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } finally {
      trace_return("cu");
    }
}

// decl -> <Type> <Identifier> (varDecl | methDecl | ";")
  final public void decl() throws ParseException {
    trace_call("decl");
    try {

      jj_consume_token(Type);
      jj_consume_token(Identifier);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 13:{
        varDecl();
        break;
        }
      case 21:{
        methDecl();
        break;
        }
      case 27:{
        jj_consume_token(27);
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("decl");
    }
}

// varDecl -> "=" atom ";"
  final public void varDecl() throws ParseException {
    trace_call("varDecl");
    try {

      jj_consume_token(13);
      expr();
      jj_consume_token(27);
    } finally {
      trace_return("varDecl");
    }
}

// methDecl -> "(" (<Type> <Identifier> ("," <Type> <Identifier>)*)? ")" stmnt
  final public void methDecl() throws ParseException {
    trace_call("methDecl");
    try {

      jj_consume_token(21);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case Type:{
        jj_consume_token(Type);
        jj_consume_token(Identifier);
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 14:{
            ;
            break;
            }
          default:
            jj_la1[3] = jj_gen;
            break label_2;
          }
          jj_consume_token(14);
          jj_consume_token(Type);
          jj_consume_token(Identifier);
        }
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        ;
      }
      jj_consume_token(22);
      stmnt();
    } finally {
      trace_return("methDecl");
    }
}

// stmnt -> returnStmnt | ifStmnt | whileStmnt | blockStmnt | expr | ";"
  final public void stmnt() throws ParseException {
    trace_call("stmnt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 32:{
        returnStmnt();
        break;
        }
      case 29:{
        ifStmnt();
        break;
        }
      case 31:{
        whileStmnt();
        break;
        }
      case 25:{
        blockStmnt();
        break;
        }
      case 11:
      case 16:
      case 21:
      case 23:
      case 28:
      case BoolLiteral:
      case CharLiteral:
      case StringLiteral:
      case IntegerLiteral:
      case Identifier:{
        expr();
        break;
        }
      case 27:{
        jj_consume_token(27);
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("stmnt");
    }
}

// returnStmnt -> "return" atom ";"
  final public void returnStmnt() throws ParseException {
    trace_call("returnStmnt");
    try {

      jj_consume_token(32);
      expr();
      jj_consume_token(27);
    } finally {
      trace_return("returnStmnt");
    }
}

// ifStmnt -> "if" "(" atom ")" stmnt ("else" stmnt)?
  final public void ifStmnt() throws ParseException {
    trace_call("ifStmnt");
    try {

      jj_consume_token(29);
      jj_consume_token(21);
      expr();
      jj_consume_token(22);
      stmnt();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 30:{
        jj_consume_token(30);
        stmnt();
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
    } finally {
      trace_return("ifStmnt");
    }
}

// whileStmnt -> "while" "(" atom ")" stmnt
  final public void whileStmnt() throws ParseException {
    trace_call("whileStmnt");
    try {

      jj_consume_token(31);
      jj_consume_token(21);
      expr();
      jj_consume_token(22);
      stmnt();
    } finally {
      trace_return("whileStmnt");
    }
}

// blockStmnt -> "{" (decl | stmnt)* "}"
  final public void blockStmnt() throws ParseException {
    trace_call("blockStmnt");
    try {

      jj_consume_token(25);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 11:
        case 16:
        case 21:
        case 23:
        case 25:
        case 27:
        case 28:
        case 29:
        case 31:
        case 32:
        case Type:
        case BoolLiteral:
        case CharLiteral:
        case StringLiteral:
        case IntegerLiteral:
        case Identifier:{
          ;
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          break label_3;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          decl();
          break;
          }
        case 11:
        case 16:
        case 21:
        case 23:
        case 25:
        case 27:
        case 28:
        case 29:
        case 31:
        case 32:
        case BoolLiteral:
        case CharLiteral:
        case StringLiteral:
        case IntegerLiteral:
        case Identifier:{
          stmnt();
          break;
          }
        default:
          jj_la1[8] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(26);
    } finally {
      trace_return("blockStmnt");
    }
}

// atom -> "!" atom | ("+" | "-") atom | <Character> | <String> | <Integer> | <Boolean> | <Identifier>
//         | expr | range | state | transition | fa | methCall
  final public void atom() throws ParseException {
    trace_call("atom");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 11:{
        jj_consume_token(11);
        atom();
        break;
        }
      case CharLiteral:{
        jj_consume_token(CharLiteral);
        break;
        }
      case StringLiteral:{
        jj_consume_token(StringLiteral);
        break;
        }
      case IntegerLiteral:{
        jj_consume_token(IntegerLiteral);
        break;
        }
      case BoolLiteral:{
        jj_consume_token(BoolLiteral);
        break;
        }
      case 16:{
        range();
        break;
        }
      case 23:{
        fa();
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        if (jj_2_2(2)) {
          methCall();
        } else {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 28:
          case Identifier:{
            switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
            case 28:{
              state();
              break;
              }
            case Identifier:{
              jj_consume_token(Identifier);
              label_4:
              while (true) {
                switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
                case 15:{
                  ;
                  break;
                  }
                default:
                  jj_la1[9] = jj_gen;
                  break label_4;
                }
                jj_consume_token(15);
                jj_consume_token(Identifier);
              }
              break;
              }
            default:
              jj_la1[10] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            if (jj_2_1(3)) {
              transition();
            } else {
              ;
            }
            break;
            }
          default:
            jj_la1[12] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
      }
    } finally {
      trace_return("atom");
    }
}

// methCall -> (<Idenfier> ".")? <Identifier> "(" (atom ("," atom)*)? ")"
  final public void methCall() throws ParseException {
    trace_call("methCall");
    try {

      jj_consume_token(Identifier);
      jj_consume_token(21);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 11:
      case 16:
      case 23:
      case 28:
      case BoolLiteral:
      case CharLiteral:
      case StringLiteral:
      case IntegerLiteral:
      case Identifier:{
        atom();
        label_5:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 14:{
            ;
            break;
            }
          default:
            jj_la1[13] = jj_gen;
            break label_5;
          }
          jj_consume_token(14);
          atom();
        }
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      jj_consume_token(22);
    } finally {
      trace_return("methCall");
    }
}

// range -> "[" <Char> ("-" <Char)? ("," <Char> ("-" <Char)?)* "]"
  final public void range() throws ParseException {
    trace_call("range");
    try {

      jj_consume_token(16);
      jj_consume_token(CharLiteral);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 2:{
        jj_consume_token(2);
        jj_consume_token(CharLiteral);
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        ;
      }
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 14:{
          ;
          break;
          }
        default:
          jj_la1[16] = jj_gen;
          break label_6;
        }
        jj_consume_token(14);
        jj_consume_token(CharLiteral);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 2:{
          jj_consume_token(2);
          jj_consume_token(CharLiteral);
          break;
          }
        default:
          jj_la1[17] = jj_gen;
          ;
        }
      }
      jj_consume_token(17);
      label_7:
      while (true) {
        if (jj_2_3(2)) {
          ;
        } else {
          break label_7;
        }
        jj_consume_token(1);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 16:{
          range();
          break;
          }
        case Identifier:{
          jj_consume_token(Identifier);
          break;
          }
        default:
          jj_la1[18] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } finally {
      trace_return("range");
    }
}

// state -> "$" <String> ("^" (<Integer> | <Identifier>))?
  final public void state() throws ParseException {
    trace_call("state");
    try {

      jj_consume_token(28);
      jj_consume_token(StringLiteral);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 12:{
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case IntegerLiteral:{
          jj_consume_token(IntegerLiteral);
          break;
          }
        case Identifier:{
          jj_consume_token(Identifier);
          break;
          }
        default:
          jj_la1[19] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        ;
      }
    } finally {
      trace_return("state");
    }
}

// transition -> (state | <Identifier>) (("--" (range | <Identifier)) | "-") "-->" (state | <Identifier>)
  final public void transition() throws ParseException {
    trace_call("transition");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 7:{
        jj_consume_token(7);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 16:{
          range();
          break;
          }
        case Identifier:{
          jj_consume_token(Identifier);
          break;
          }
        default:
          jj_la1[21] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
        }
      case 2:{
        jj_consume_token(2);
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(48);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 28:{
        state();
        break;
        }
      case Identifier:{
        jj_consume_token(Identifier);
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("transition");
    }
}

// fa -> "<" (state | <Identifier>) ",{" (transition ("," transition)*)? "}>"
  final public void fa() throws ParseException {
    trace_call("fa");
    try {

      jj_consume_token(23);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 28:{
        state();
        break;
        }
      case Identifier:{
        jj_consume_token(Identifier);
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(14);
      jj_consume_token(25);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case Identifier:{
        jj_consume_token(Identifier);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 2:
        case 7:{
          transition();
          break;
          }
        default:
          jj_la1[25] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 14:{
          jj_consume_token(14);
          jj_consume_token(Identifier);
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 2:
          case 7:{
            transition();
            break;
            }
          default:
            jj_la1[26] = jj_gen;
            ;
          }
          break;
          }
        default:
          jj_la1[27] = jj_gen;
          ;
        }
        break;
        }
      default:
        jj_la1[28] = jj_gen;
        ;
      }
      jj_consume_token(26);
      jj_consume_token(24);
      label_8:
      while (true) {
        if (jj_2_4(2)) {
          ;
        } else {
          break label_8;
        }
        jj_consume_token(1);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 23:{
          fa();
          break;
          }
        case Identifier:{
          jj_consume_token(Identifier);
          break;
          }
        default:
          jj_la1[29] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } finally {
      trace_return("fa");
    }
}

// expr -> "(" expr ")" | compExpr (("&&" | "||") compExpr)*
  final public void expr() throws ParseException {
    trace_call("expr");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 21:{
        jj_consume_token(21);
        expr();
        jj_consume_token(22);
        break;
        }
      case 11:
      case 16:
      case 23:
      case 28:
      case BoolLiteral:
      case CharLiteral:
      case StringLiteral:
      case IntegerLiteral:
      case Identifier:{
        compExpr();
        label_9:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 8:
          case 9:{
            ;
            break;
            }
          default:
            jj_la1[30] = jj_gen;
            break label_9;
          }
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 8:{
            jj_consume_token(8);
            break;
            }
          case 9:{
            jj_consume_token(9);
            break;
            }
          default:
            jj_la1[31] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          compExpr();
        }
        break;
        }
      default:
        jj_la1[32] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("expr");
    }
}

// compExpr -> sumExpr (("=="|"!="|"<="|">="|"<"|">") sumExpr)*
  final public void compExpr() throws ParseException {
    trace_call("compExpr");
    try {

      sumExpr();
      label_10:
      while (true) {
        if (jj_2_5(2)) {
          ;
        } else {
          break label_10;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 49:{
          jj_consume_token(49);
          break;
          }
        case 50:{
          jj_consume_token(50);
          break;
          }
        case 51:{
          jj_consume_token(51);
          break;
          }
        case 52:{
          jj_consume_token(52);
          break;
          }
        case 23:{
          jj_consume_token(23);
          break;
          }
        case 24:{
          jj_consume_token(24);
          break;
          }
        default:
          jj_la1[33] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        sumExpr();
      }
    } finally {
      trace_return("compExpr");
    }
}

// sumExpr -> prodExpr (("+" | "-") prodExpr)*
  final public void sumExpr() throws ParseException {
    trace_call("sumExpr");
    try {

      prodExpr();
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 1:
        case 2:{
          ;
          break;
          }
        default:
          jj_la1[34] = jj_gen;
          break label_11;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 1:{
          jj_consume_token(1);
          break;
          }
        case 2:{
          jj_consume_token(2);
          break;
          }
        default:
          jj_la1[35] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        prodExpr();
      }
    } finally {
      trace_return("sumExpr");
    }
}

// prodExpr -> atom (("*" | "/") atom)*
  final public void prodExpr() throws ParseException {
    trace_call("prodExpr");
    try {

      atom();
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 3:
        case 4:{
          ;
          break;
          }
        default:
          jj_la1[36] = jj_gen;
          break label_12;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 3:{
          jj_consume_token(3);
          break;
          }
        case 4:{
          jj_consume_token(4);
          break;
          }
        default:
          jj_la1[37] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        atom();
      }
    } finally {
      trace_return("prodExpr");
    }
}

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_5()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  private boolean jj_3R_sumExpr_135_5_17()
 {
    if (jj_3R_prodExpr_140_5_22()) return true;
    return false;
  }

  private boolean jj_3R_range_104_24_15()
 {
    if (jj_3R_range_103_5_20()) return true;
    return false;
  }

  private boolean jj_3R_transition_114_13_23()
 {
    if (jj_3R_range_103_5_20()) return true;
    return false;
  }

  private boolean jj_3_4()
 {
    if (jj_scan_token(1)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_fa_120_24_16()) {
    jj_scanpos = xsp;
    if (jj_scan_token(41)) return true;
    }
    return false;
  }

  private boolean jj_3R_fa_119_5_21()
 {
    if (jj_scan_token(23)) return true;
    return false;
  }

  private boolean jj_3R_transition_114_6_18()
 {
    if (jj_scan_token(7)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_transition_114_13_23()) {
    jj_scanpos = xsp;
    if (jj_scan_token(41)) return true;
    }
    return false;
  }

  private boolean jj_3R_transition_114_5_13()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_transition_114_6_18()) {
    jj_scanpos = xsp;
    if (jj_scan_token(2)) return true;
    }
    if (jj_scan_token(48)) return true;
    xsp = jj_scanpos;
    if (jj_3R_transition_114_52_19()) {
    jj_scanpos = xsp;
    if (jj_scan_token(41)) return true;
    }
    return false;
  }

  private boolean jj_3R_transition_114_52_19()
 {
    if (jj_3R_state_109_5_24()) return true;
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_3R_methCall_98_5_14()) return true;
    return false;
  }

  private boolean jj_3R_state_109_5_24()
 {
    if (jj_scan_token(28)) return true;
    return false;
  }

  private boolean jj_3R_atom_93_18_31()
 {
    if (jj_scan_token(Identifier)) return true;
    return false;
  }

  private boolean jj_3_3()
 {
    if (jj_scan_token(1)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_range_104_24_15()) {
    jj_scanpos = xsp;
    if (jj_scan_token(41)) return true;
    }
    return false;
  }

  private boolean jj_3R_atom_92_17_28()
 {
    if (jj_3R_fa_119_5_21()) return true;
    return false;
  }

  private boolean jj_3R_range_103_5_20()
 {
    if (jj_scan_token(16)) return true;
    if (jj_scan_token(CharLiteral)) return true;
    return false;
  }

  private boolean jj_3R_methCall_98_5_14()
 {
    if (jj_scan_token(Identifier)) return true;
    if (jj_scan_token(21)) return true;
    return false;
  }

  private boolean jj_3R_atom_93_8_30()
 {
    if (jj_3R_state_109_5_24()) return true;
    return false;
  }

  private boolean jj_3R_atom_93_7_29()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_atom_93_8_30()) {
    jj_scanpos = xsp;
    if (jj_3R_atom_93_18_31()) return true;
    }
    return false;
  }

  private boolean jj_3R_atom_92_7_27()
 {
    if (jj_3R_range_103_5_20()) return true;
    return false;
  }

  private boolean jj_3_5()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(49)) {
    jj_scanpos = xsp;
    if (jj_scan_token(50)) {
    jj_scanpos = xsp;
    if (jj_scan_token(51)) {
    jj_scanpos = xsp;
    if (jj_scan_token(52)) {
    jj_scanpos = xsp;
    if (jj_scan_token(23)) {
    jj_scanpos = xsp;
    if (jj_scan_token(24)) return true;
    }
    }
    }
    }
    }
    if (jj_3R_sumExpr_135_5_17()) return true;
    return false;
  }

  private boolean jj_3_1()
 {
    if (jj_3R_transition_114_5_13()) return true;
    return false;
  }

  private boolean jj_3R_prodExpr_140_5_22()
 {
    if (jj_3R_atom_91_5_25()) return true;
    return false;
  }

  private boolean jj_3R_fa_120_24_16()
 {
    if (jj_3R_fa_119_5_21()) return true;
    return false;
  }

  private boolean jj_3R_atom_91_5_26()
 {
    if (jj_scan_token(11)) return true;
    return false;
  }

  private boolean jj_3R_atom_91_5_25()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_atom_91_5_26()) {
    jj_scanpos = xsp;
    if (jj_scan_token(37)) {
    jj_scanpos = xsp;
    if (jj_scan_token(38)) {
    jj_scanpos = xsp;
    if (jj_scan_token(39)) {
    jj_scanpos = xsp;
    if (jj_scan_token(36)) {
    jj_scanpos = xsp;
    if (jj_3R_atom_92_7_27()) {
    jj_scanpos = xsp;
    if (jj_3R_atom_92_17_28()) {
    jj_scanpos = xsp;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3R_atom_93_7_29()) return true;
    }
    }
    }
    }
    }
    }
    }
    }
    return false;
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[38];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0xbaa10800,0xbaa10800,0x8202000,0x4000,0x0,0xbaa10800,0x40000000,0xbaa10800,0xbaa10800,0x8000,0x10000000,0x810800,0x10000000,0x4000,0x10810800,0x4,0x4000,0x4,0x10000,0x0,0x1000,0x10000,0x84,0x10000000,0x10000000,0x84,0x84,0x4000,0x0,0x800000,0x300,0x300,0x10a10800,0x1800000,0x6,0x6,0x18,0x18,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x2f3,0x2f3,0x0,0x0,0x2,0x2f1,0x0,0x2f3,0x2f3,0x0,0x200,0xf0,0x200,0x0,0x2f0,0x0,0x0,0x0,0x200,0x280,0x0,0x200,0x0,0x200,0x200,0x0,0x0,0x0,0x200,0x200,0x0,0x0,0x2f0,0x1e0000,0x0,0x0,0x0,0x0,};
	}
  final private JJCalls[] jj_2_rtns = new JJCalls[5];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  {
      enable_tracing();
  }
  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 38; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	   trace_token(token, " (in getNextToken)");
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[53];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 38; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 53; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  private int trace_indent = 0;
/** Enable tracing. */
  final public void enable_tracing() {
	 trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
	 trace_enabled = false;
  }

  protected void trace_call(String s) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Call:	" + s);
	 }
	 trace_indent = trace_indent + 2;
  }

  protected void trace_return(String s) {
	 trace_indent = trace_indent - 2;
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Return: " + s);
	 }
  }

  protected void trace_token(Token t, String where) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Consumed token: <" + tokenImage[t.kind]);
	   if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
	   }
	   System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
	 }
  }

  protected void trace_scan(Token t1, int t2) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Visited token: <" + tokenImage[t1.kind]);
	   if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
	   }
	   System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
	 }
  }

  private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 5; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			   case 4: jj_3_5(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
