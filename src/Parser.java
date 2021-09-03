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
        case Type:
        case Identifier:
        case 49:
        case 50:{
          ;
          break;
          }
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case 49:
        case 50:{
          decl();
          break;
          }
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

// decl -> (Type "[]"? | set | map | array) Identifier (varDecl | methDecl) ";"
  final public void decl() throws ParseException {
    trace_call("decl");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case Type:{
        jj_consume_token(Type);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 18:{
          jj_consume_token(18);
          break;
          }
        default:
          jj_la1[2] = jj_gen;
          ;
        }
        break;
        }
      case 49:{
        set();
        break;
        }
      case 50:{
        map();
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
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
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(27);
    } finally {
      trace_return("decl");
    }
}

// varDecl ->  "=" expr
  final public void varDecl() throws ParseException {
    trace_call("varDecl");
    try {

      jj_consume_token(13);
      expr();
    } finally {
      trace_return("varDecl");
    }
}

// set -> "Set<" (Type | set | map)+ ">"
  final public void set() throws ParseException {
    trace_call("set");
    try {

      jj_consume_token(49);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          jj_consume_token(Type);
          break;
          }
        case 49:{
          set();
          break;
          }
        case 50:{
          map();
          break;
          }
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case 49:
        case 50:{
          ;
          break;
          }
        default:
          jj_la1[6] = jj_gen;
          break label_2;
        }
      }
      jj_consume_token(24);
    } finally {
      trace_return("set");
    }
}

// map -> "Map<" (Type | set | map)+ , (Type | set | map)+ ">"
  final public void map() throws ParseException {
    trace_call("map");
    try {

      jj_consume_token(50);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          jj_consume_token(Type);
          break;
          }
        case 49:{
          set();
          break;
          }
        case 50:{
          map();
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case 49:
        case 50:{
          ;
          break;
          }
        default:
          jj_la1[8] = jj_gen;
          break label_3;
        }
      }
      jj_consume_token(14);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          jj_consume_token(Type);
          break;
          }
        case 49:{
          set();
          break;
          }
        case 50:{
          map();
          break;
          }
        default:
          jj_la1[9] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case 49:
        case 50:{
          ;
          break;
          }
        default:
          jj_la1[10] = jj_gen;
          break label_4;
        }
      }
      jj_consume_token(24);
    } finally {
      trace_return("map");
    }
}

// expr -> TODO | range | (state transition?) | fa | atom
  final public void expr() throws ParseException {
    trace_call("expr");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 16:{
        range();
        break;
        }
      case 28:{
        state();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 2:
        case 7:{
          transition();
          break;
          }
        default:
          jj_la1[11] = jj_gen;
          ;
        }
        break;
        }
      case 23:{
        fa();
        break;
        }
      case Identifier:
      case IntegerLiteral:
      case DoubleLiteral:
      case RegexLiteral:{
        atom();
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("expr");
    }
}

// range -> "[" ( CharLiteral ("-" CharLiteral)? ) ("," ( CharLiteral ("-" CharLiteral)? )* "]"
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
        jj_la1[13] = jj_gen;
        ;
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 14:{
          ;
          break;
          }
        default:
          jj_la1[14] = jj_gen;
          break label_5;
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
          jj_la1[15] = jj_gen;
          ;
        }
      }
      jj_consume_token(17);
    } finally {
      trace_return("range");
    }
}

// state -> "$" StringLiteral ("^" IntegerLiteral)?
  final public void state() throws ParseException {
    trace_call("state");
    try {

      jj_consume_token(28);
      jj_consume_token(StringLiteral);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 12:{
        jj_consume_token(12);
        jj_consume_token(IntegerLiteral);
        break;
        }
      default:
        jj_la1[16] = jj_gen;
        ;
      }
    } finally {
      trace_return("state");
    }
}

// transition -> ("--" range | "-") "-->" state
  final public void transition() throws ParseException {
    trace_call("transition");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 7:{
        jj_consume_token(7);
        range();
        break;
        }
      case 2:{
        jj_consume_token(2);
        break;
        }
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(51);
      state();
    } finally {
      trace_return("transition");
    }
}

// fa -> "<" state ",{" transition* "}>"
  final public void fa() throws ParseException {
    trace_call("fa");
    try {

      jj_consume_token(23);
      state();
      jj_consume_token(52);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 2:
        case 7:{
          ;
          break;
          }
        default:
          jj_la1[18] = jj_gen;
          break label_6;
        }
        transition();
      }
      jj_consume_token(53);
    } finally {
      trace_return("fa");
    }
}

// methDecl -> "(" (Type Identifier)* ")" block
  final public void methDecl() throws ParseException {
    trace_call("methDecl");
    try {

      jj_consume_token(21);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          ;
          break;
          }
        default:
          jj_la1[19] = jj_gen;
          break label_7;
        }
        jj_consume_token(Type);
        jj_consume_token(Identifier);
      }
      jj_consume_token(22);
      block();
    } finally {
      trace_return("methDecl");
    }
}

// methCall -> Identifier ("." Identifier)? "(" ( atom ("," atom)* )? ")"
  final public void methCall() throws ParseException {
    trace_call("methCall");
    try {

      jj_consume_token(Identifier);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 15:{
        jj_consume_token(15);
        jj_consume_token(Identifier);
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        ;
      }
      jj_consume_token(21);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case Identifier:
      case IntegerLiteral:
      case DoubleLiteral:
      case RegexLiteral:{
        atom();
        label_8:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 14:{
            ;
            break;
            }
          default:
            jj_la1[21] = jj_gen;
            break label_8;
          }
          jj_consume_token(14);
          atom();
        }
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        ;
      }
      jj_consume_token(22);
    } finally {
      trace_return("methCall");
    }
}

// block -> "{" (decl | stmnt)* "}"
  final public void block() throws ParseException {
    trace_call("block");
    try {

      jj_consume_token(25);
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case Identifier:
        case 49:
        case 50:{
          ;
          break;
          }
        default:
          jj_la1[23] = jj_gen;
          break label_9;
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:
        case 49:
        case 50:{
          decl();
          break;
          }
        case Identifier:{
          stmnt();
          break;
          }
        default:
          jj_la1[24] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      jj_consume_token(26);
    } finally {
      trace_return("block");
    }
}

// stmnt -> ifStmnt | whileStmnt | returnStmnt | methCall | TODO
  final public void stmnt() throws ParseException {
    trace_call("stmnt");
    try {

      methCall();
      jj_consume_token(27);
    } finally {
      trace_return("stmnt");
    }
}

// ifStmnt -> TODO

// whileStmnt -> TODO

// returnStmnt -> TODO

// atom -> IntegerLiteral | DoubleLiteral | Idenftifier ("++" | "--")? | ("++" | "--")? Idenftifier
//         | RegexLiteral | TODO
  final public void atom() throws ParseException {
    trace_call("atom");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IntegerLiteral:{
        jj_consume_token(IntegerLiteral);
        break;
        }
      case DoubleLiteral:{
        jj_consume_token(DoubleLiteral);
        break;
        }
      case Identifier:{
        jj_consume_token(Identifier);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 6:
        case 7:{
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 6:{
            jj_consume_token(6);
            break;
            }
          case 7:{
            jj_consume_token(7);
            break;
            }
          default:
            jj_la1[25] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          break;
          }
        default:
          jj_la1[26] = jj_gen;
          ;
        }
        break;
        }
      case RegexLiteral:{
        jj_consume_token(RegexLiteral);
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("atom");
    }
}

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[28];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0x40000,0x0,0x202000,0x0,0x0,0x0,0x0,0x0,0x0,0x84,0x10810000,0x4,0x4000,0x4,0x1000,0x84,0x84,0x0,0x8000,0x4000,0x0,0x0,0x0,0xc0,0xc0,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x60048,0x60048,0x0,0x60008,0x0,0x60008,0x60008,0x60008,0x60008,0x60008,0x60008,0x0,0xe40,0x0,0x0,0x0,0x0,0x0,0x0,0x8,0x0,0x0,0xe40,0x60048,0x60048,0x0,0x0,0xe40,};
	}

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
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
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
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
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
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
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

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[54];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 28; i++) {
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
	 for (int i = 0; i < 54; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
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

}