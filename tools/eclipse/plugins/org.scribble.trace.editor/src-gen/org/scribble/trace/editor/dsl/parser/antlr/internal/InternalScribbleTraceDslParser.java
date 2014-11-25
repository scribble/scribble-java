package org.scribble.trace.editor.dsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.scribble.trace.editor.dsl.services.ScribbleTraceDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalScribbleTraceDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'trace'", "'by'", "'shows'", "';'", "'.'", "'role'", "'simulating'", "'protocol'", "'as'", "'('", "','", "')'", "'from'", "'to'", "'='"
    };
    public static final int RULE_ID=4;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__19=19;
    public static final int RULE_STRING=5;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalScribbleTraceDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalScribbleTraceDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalScribbleTraceDslParser.tokenNames; }
    public String getGrammarFileName() { return "../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g"; }



     	private ScribbleTraceDslGrammarAccess grammarAccess;
     	
        public InternalScribbleTraceDslParser(TokenStream input, ScribbleTraceDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Trace";	
       	}
       	
       	@Override
       	protected ScribbleTraceDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleTrace"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:67:1: entryRuleTrace returns [EObject current=null] : iv_ruleTrace= ruleTrace EOF ;
    public final EObject entryRuleTrace() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrace = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:68:2: (iv_ruleTrace= ruleTrace EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:69:2: iv_ruleTrace= ruleTrace EOF
            {
             newCompositeNode(grammarAccess.getTraceRule()); 
            pushFollow(FOLLOW_ruleTrace_in_entryRuleTrace75);
            iv_ruleTrace=ruleTrace();

            state._fsp--;

             current =iv_ruleTrace; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrace85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrace"


    // $ANTLR start "ruleTrace"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:76:1: ruleTrace returns [EObject current=null] : ( () ruleTracedefn ( (lv_roles_2_0= ruleRoledefn ) )* ( (lv_steps_3_0= ruleStepdefn ) )* ) ;
    public final EObject ruleTrace() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_roles_2_0 = null;

        EObject lv_steps_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:79:28: ( ( () ruleTracedefn ( (lv_roles_2_0= ruleRoledefn ) )* ( (lv_steps_3_0= ruleStepdefn ) )* ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:80:1: ( () ruleTracedefn ( (lv_roles_2_0= ruleRoledefn ) )* ( (lv_steps_3_0= ruleStepdefn ) )* )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:80:1: ( () ruleTracedefn ( (lv_roles_2_0= ruleRoledefn ) )* ( (lv_steps_3_0= ruleStepdefn ) )* )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:80:2: () ruleTracedefn ( (lv_roles_2_0= ruleRoledefn ) )* ( (lv_steps_3_0= ruleStepdefn ) )*
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:80:2: ()
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:81:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getTraceAccess().getTraceAction_0(),
                        current);
                

            }

             
                    newCompositeNode(grammarAccess.getTraceAccess().getTracedefnParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleTracedefn_in_ruleTrace135);
            ruleTracedefn();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:94:1: ( (lv_roles_2_0= ruleRoledefn ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==16) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:95:1: (lv_roles_2_0= ruleRoledefn )
            	    {
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:95:1: (lv_roles_2_0= ruleRoledefn )
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:96:3: lv_roles_2_0= ruleRoledefn
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTraceAccess().getRolesRoledefnParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoledefn_in_ruleTrace155);
            	    lv_roles_2_0=ruleRoledefn();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTraceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_2_0, 
            	            		"Roledefn");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:112:3: ( (lv_steps_3_0= ruleStepdefn ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:113:1: (lv_steps_3_0= ruleStepdefn )
            	    {
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:113:1: (lv_steps_3_0= ruleStepdefn )
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:114:3: lv_steps_3_0= ruleStepdefn
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getTraceAccess().getStepsStepdefnParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleStepdefn_in_ruleTrace177);
            	    lv_steps_3_0=ruleStepdefn();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getTraceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"steps",
            	            		lv_steps_3_0, 
            	            		"Stepdefn");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrace"


    // $ANTLR start "entryRuleSentence"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:138:1: entryRuleSentence returns [String current=null] : iv_ruleSentence= ruleSentence EOF ;
    public final String entryRuleSentence() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSentence = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:139:2: (iv_ruleSentence= ruleSentence EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:140:2: iv_ruleSentence= ruleSentence EOF
            {
             newCompositeNode(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_ruleSentence_in_entryRuleSentence215);
            iv_ruleSentence=ruleSentence();

            state._fsp--;

             current =iv_ruleSentence.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSentence226); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSentence"


    // $ANTLR start "ruleSentence"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:147:1: ruleSentence returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID )+ ;
    public final AntlrDatatypeRuleToken ruleSentence() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:150:28: ( (this_ID_0= RULE_ID )+ )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:151:1: (this_ID_0= RULE_ID )+
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:151:1: (this_ID_0= RULE_ID )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==RULE_ID) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:151:6: this_ID_0= RULE_ID
            	    {
            	    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSentence266); 

            	    		current.merge(this_ID_0);
            	        
            	     
            	        newLeafNode(this_ID_0, grammarAccess.getSentenceAccess().getIDTerminalRuleCall()); 
            	        

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSentence"


    // $ANTLR start "entryRuleTracedefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:166:1: entryRuleTracedefn returns [String current=null] : iv_ruleTracedefn= ruleTracedefn EOF ;
    public final String entryRuleTracedefn() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTracedefn = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:167:2: (iv_ruleTracedefn= ruleTracedefn EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:168:2: iv_ruleTracedefn= ruleTracedefn EOF
            {
             newCompositeNode(grammarAccess.getTracedefnRule()); 
            pushFollow(FOLLOW_ruleTracedefn_in_entryRuleTracedefn313);
            iv_ruleTracedefn=ruleTracedefn();

            state._fsp--;

             current =iv_ruleTracedefn.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTracedefn324); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTracedefn"


    // $ANTLR start "ruleTracedefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:175:1: ruleTracedefn returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'trace' this_ID_1= RULE_ID (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )? kw= ';' ) ;
    public final AntlrDatatypeRuleToken ruleTracedefn() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ID_1=null;
        AntlrDatatypeRuleToken this_Sentence_3 = null;

        AntlrDatatypeRuleToken this_Sentence_5 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:178:28: ( (kw= 'trace' this_ID_1= RULE_ID (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )? kw= ';' ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:179:1: (kw= 'trace' this_ID_1= RULE_ID (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )? kw= ';' )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:179:1: (kw= 'trace' this_ID_1= RULE_ID (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )? kw= ';' )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:180:2: kw= 'trace' this_ID_1= RULE_ID (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )? kw= ';'
            {
            kw=(Token)match(input,11,FOLLOW_11_in_ruleTracedefn362); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getTracedefnAccess().getTraceKeyword_0()); 
                
            this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTracedefn377); 

            		current.merge(this_ID_1);
                
             
                newLeafNode(this_ID_1, grammarAccess.getTracedefnAccess().getIDTerminalRuleCall_1()); 
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:192:1: (kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )? )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==12) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:193:2: kw= 'by' this_Sentence_3= ruleSentence (kw= 'shows' this_Sentence_5= ruleSentence )?
                    {
                    kw=(Token)match(input,12,FOLLOW_12_in_ruleTracedefn396); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getTracedefnAccess().getByKeyword_2_0()); 
                        
                     
                            newCompositeNode(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleSentence_in_ruleTracedefn418);
                    this_Sentence_3=ruleSentence();

                    state._fsp--;


                    		current.merge(this_Sentence_3);
                        
                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:209:1: (kw= 'shows' this_Sentence_5= ruleSentence )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==13) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:210:2: kw= 'shows' this_Sentence_5= ruleSentence
                            {
                            kw=(Token)match(input,13,FOLLOW_13_in_ruleTracedefn437); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getTracedefnAccess().getShowsKeyword_2_2_0()); 
                                
                             
                                    newCompositeNode(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_2_1()); 
                                
                            pushFollow(FOLLOW_ruleSentence_in_ruleTracedefn459);
                            this_Sentence_5=ruleSentence();

                            state._fsp--;


                            		current.merge(this_Sentence_5);
                                
                             
                                    afterParserOrEnumRuleCall();
                                

                            }
                            break;

                    }


                    }
                    break;

            }

            kw=(Token)match(input,14,FOLLOW_14_in_ruleTracedefn481); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getTracedefnAccess().getSemicolonKeyword_3()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTracedefn"


    // $ANTLR start "entryRuleModule"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:240:1: entryRuleModule returns [String current=null] : iv_ruleModule= ruleModule EOF ;
    public final String entryRuleModule() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModule = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:241:2: (iv_ruleModule= ruleModule EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:242:2: iv_ruleModule= ruleModule EOF
            {
             newCompositeNode(grammarAccess.getModuleRule()); 
            pushFollow(FOLLOW_ruleModule_in_entryRuleModule522);
            iv_ruleModule=ruleModule();

            state._fsp--;

             current =iv_ruleModule.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModule533); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModule"


    // $ANTLR start "ruleModule"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:249:1: ruleModule returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleModule() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:252:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:253:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:253:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:253:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModule573); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getModuleAccess().getIDTerminalRuleCall_0()); 
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:260:1: (kw= '.' this_ID_2= RULE_ID )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==15) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:261:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,15,FOLLOW_15_in_ruleModule592); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getModuleAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModule607); 

            	    		current.merge(this_ID_2);
            	        
            	     
            	        newLeafNode(this_ID_2, grammarAccess.getModuleAccess().getIDTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModule"


    // $ANTLR start "entryRuleRoledefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:281:1: entryRuleRoledefn returns [String current=null] : iv_ruleRoledefn= ruleRoledefn EOF ;
    public final String entryRuleRoledefn() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRoledefn = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:282:2: (iv_ruleRoledefn= ruleRoledefn EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:283:2: iv_ruleRoledefn= ruleRoledefn EOF
            {
             newCompositeNode(grammarAccess.getRoledefnRule()); 
            pushFollow(FOLLOW_ruleRoledefn_in_entryRuleRoledefn655);
            iv_ruleRoledefn=ruleRoledefn();

            state._fsp--;

             current =iv_ruleRoledefn.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoledefn666); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRoledefn"


    // $ANTLR start "ruleRoledefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:290:1: ruleRoledefn returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'role' this_ID_1= RULE_ID (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )? kw= ';' ) ;
    public final AntlrDatatypeRuleToken ruleRoledefn() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ID_1=null;
        Token this_ID_5=null;
        Token this_ID_7=null;
        AntlrDatatypeRuleToken this_Module_3 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:293:28: ( (kw= 'role' this_ID_1= RULE_ID (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )? kw= ';' ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:294:1: (kw= 'role' this_ID_1= RULE_ID (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )? kw= ';' )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:294:1: (kw= 'role' this_ID_1= RULE_ID (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )? kw= ';' )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:295:2: kw= 'role' this_ID_1= RULE_ID (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )? kw= ';'
            {
            kw=(Token)match(input,16,FOLLOW_16_in_ruleRoledefn704); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRoledefnAccess().getRoleKeyword_0()); 
                
            this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoledefn719); 

            		current.merge(this_ID_1);
                
             
                newLeafNode(this_ID_1, grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_1()); 
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:307:1: (kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )? )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==17) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:308:2: kw= 'simulating' this_Module_3= ruleModule kw= 'protocol' this_ID_5= RULE_ID (kw= 'as' this_ID_7= RULE_ID )?
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleRoledefn738); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRoledefnAccess().getSimulatingKeyword_2_0()); 
                        
                     
                            newCompositeNode(grammarAccess.getRoledefnAccess().getModuleParserRuleCall_2_1()); 
                        
                    pushFollow(FOLLOW_ruleModule_in_ruleRoledefn760);
                    this_Module_3=ruleModule();

                    state._fsp--;


                    		current.merge(this_Module_3);
                        
                     
                            afterParserOrEnumRuleCall();
                        
                    kw=(Token)match(input,18,FOLLOW_18_in_ruleRoledefn778); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRoledefnAccess().getProtocolKeyword_2_2()); 
                        
                    this_ID_5=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoledefn793); 

                    		current.merge(this_ID_5);
                        
                     
                        newLeafNode(this_ID_5, grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_3()); 
                        
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:337:1: (kw= 'as' this_ID_7= RULE_ID )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==19) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:338:2: kw= 'as' this_ID_7= RULE_ID
                            {
                            kw=(Token)match(input,19,FOLLOW_19_in_ruleRoledefn812); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getRoledefnAccess().getAsKeyword_2_4_0()); 
                                
                            this_ID_7=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoledefn827); 

                            		current.merge(this_ID_7);
                                
                             
                                newLeafNode(this_ID_7, grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_4_1()); 
                                

                            }
                            break;

                    }


                    }
                    break;

            }

            kw=(Token)match(input,14,FOLLOW_14_in_ruleRoledefn849); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRoledefnAccess().getSemicolonKeyword_3()); 
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRoledefn"


    // $ANTLR start "entryRuleStepdefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:364:1: entryRuleStepdefn returns [EObject current=null] : iv_ruleStepdefn= ruleStepdefn EOF ;
    public final EObject entryRuleStepdefn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStepdefn = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:365:2: (iv_ruleStepdefn= ruleStepdefn EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:366:2: iv_ruleStepdefn= ruleStepdefn EOF
            {
             newCompositeNode(grammarAccess.getStepdefnRule()); 
            pushFollow(FOLLOW_ruleStepdefn_in_entryRuleStepdefn889);
            iv_ruleStepdefn=ruleStepdefn();

            state._fsp--;

             current =iv_ruleStepdefn; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStepdefn899); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStepdefn"


    // $ANTLR start "ruleStepdefn"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:373:1: ruleStepdefn returns [EObject current=null] : this_Messagetransfer_0= ruleMessagetransfer ;
    public final EObject ruleStepdefn() throws RecognitionException {
        EObject current = null;

        EObject this_Messagetransfer_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:376:28: (this_Messagetransfer_0= ruleMessagetransfer )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:378:5: this_Messagetransfer_0= ruleMessagetransfer
            {
             
                    newCompositeNode(grammarAccess.getStepdefnAccess().getMessagetransferParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleMessagetransfer_in_ruleStepdefn945);
            this_Messagetransfer_0=ruleMessagetransfer();

            state._fsp--;

             
                    current = this_Messagetransfer_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStepdefn"


    // $ANTLR start "entryRuleMessagetransfer"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:394:1: entryRuleMessagetransfer returns [EObject current=null] : iv_ruleMessagetransfer= ruleMessagetransfer EOF ;
    public final EObject entryRuleMessagetransfer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessagetransfer = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:395:2: (iv_ruleMessagetransfer= ruleMessagetransfer EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:396:2: iv_ruleMessagetransfer= ruleMessagetransfer EOF
            {
             newCompositeNode(grammarAccess.getMessagetransferRule()); 
            pushFollow(FOLLOW_ruleMessagetransfer_in_entryRuleMessagetransfer979);
            iv_ruleMessagetransfer=ruleMessagetransfer();

            state._fsp--;

             current =iv_ruleMessagetransfer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessagetransfer989); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMessagetransfer"


    // $ANTLR start "ruleMessagetransfer"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:403:1: ruleMessagetransfer returns [EObject current=null] : ( () this_ID_1= RULE_ID (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )? otherlv_7= 'from' this_ID_8= RULE_ID otherlv_9= 'to' this_ID_10= RULE_ID (otherlv_11= ',' this_ID_12= RULE_ID )* otherlv_13= ';' ) ;
    public final EObject ruleMessagetransfer() throws RecognitionException {
        EObject current = null;

        Token this_ID_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token this_ID_8=null;
        Token otherlv_9=null;
        Token this_ID_10=null;
        Token otherlv_11=null;
        Token this_ID_12=null;
        Token otherlv_13=null;
        EObject lv_parameters_3_0 = null;

        EObject lv_parameters_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:406:28: ( ( () this_ID_1= RULE_ID (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )? otherlv_7= 'from' this_ID_8= RULE_ID otherlv_9= 'to' this_ID_10= RULE_ID (otherlv_11= ',' this_ID_12= RULE_ID )* otherlv_13= ';' ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:407:1: ( () this_ID_1= RULE_ID (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )? otherlv_7= 'from' this_ID_8= RULE_ID otherlv_9= 'to' this_ID_10= RULE_ID (otherlv_11= ',' this_ID_12= RULE_ID )* otherlv_13= ';' )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:407:1: ( () this_ID_1= RULE_ID (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )? otherlv_7= 'from' this_ID_8= RULE_ID otherlv_9= 'to' this_ID_10= RULE_ID (otherlv_11= ',' this_ID_12= RULE_ID )* otherlv_13= ';' )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:407:2: () this_ID_1= RULE_ID (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )? otherlv_7= 'from' this_ID_8= RULE_ID otherlv_9= 'to' this_ID_10= RULE_ID (otherlv_11= ',' this_ID_12= RULE_ID )* otherlv_13= ';'
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:407:2: ()
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:408:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMessagetransferAccess().getMessagetransferAction_0(),
                        current);
                

            }

            this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessagetransfer1034); 
             
                newLeafNode(this_ID_1, grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_1()); 
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:417:1: (otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==20) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:417:3: otherlv_2= '(' ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )? otherlv_6= ')'
                    {
                    otherlv_2=(Token)match(input,20,FOLLOW_20_in_ruleMessagetransfer1046); 

                        	newLeafNode(otherlv_2, grammarAccess.getMessagetransferAccess().getLeftParenthesisKeyword_2_0());
                        
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:421:1: ( ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )* )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==RULE_STRING) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:421:2: ( (lv_parameters_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                            {
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:421:2: ( (lv_parameters_3_0= ruleParameter ) )
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:422:1: (lv_parameters_3_0= ruleParameter )
                            {
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:422:1: (lv_parameters_3_0= ruleParameter )
                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:423:3: lv_parameters_3_0= ruleParameter
                            {
                             
                            	        newCompositeNode(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_0_0()); 
                            	    
                            pushFollow(FOLLOW_ruleParameter_in_ruleMessagetransfer1068);
                            lv_parameters_3_0=ruleParameter();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getMessagetransferRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"parameters",
                                    		lv_parameters_3_0, 
                                    		"Parameter");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:439:2: (otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) ) )*
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==21) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:439:4: otherlv_4= ',' ( (lv_parameters_5_0= ruleParameter ) )
                            	    {
                            	    otherlv_4=(Token)match(input,21,FOLLOW_21_in_ruleMessagetransfer1081); 

                            	        	newLeafNode(otherlv_4, grammarAccess.getMessagetransferAccess().getCommaKeyword_2_1_1_0());
                            	        
                            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:443:1: ( (lv_parameters_5_0= ruleParameter ) )
                            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:444:1: (lv_parameters_5_0= ruleParameter )
                            	    {
                            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:444:1: (lv_parameters_5_0= ruleParameter )
                            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:445:3: lv_parameters_5_0= ruleParameter
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_1_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleParameter_in_ruleMessagetransfer1102);
                            	    lv_parameters_5_0=ruleParameter();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getMessagetransferRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"parameters",
                            	            		lv_parameters_5_0, 
                            	            		"Parameter");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop9;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,22,FOLLOW_22_in_ruleMessagetransfer1118); 

                        	newLeafNode(otherlv_6, grammarAccess.getMessagetransferAccess().getRightParenthesisKeyword_2_2());
                        

                    }
                    break;

            }

            otherlv_7=(Token)match(input,23,FOLLOW_23_in_ruleMessagetransfer1132); 

                	newLeafNode(otherlv_7, grammarAccess.getMessagetransferAccess().getFromKeyword_3());
                
            this_ID_8=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessagetransfer1143); 
             
                newLeafNode(this_ID_8, grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_4()); 
                
            otherlv_9=(Token)match(input,24,FOLLOW_24_in_ruleMessagetransfer1154); 

                	newLeafNode(otherlv_9, grammarAccess.getMessagetransferAccess().getToKeyword_5());
                
            this_ID_10=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessagetransfer1165); 
             
                newLeafNode(this_ID_10, grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_6()); 
                
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:481:1: (otherlv_11= ',' this_ID_12= RULE_ID )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==21) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:481:3: otherlv_11= ',' this_ID_12= RULE_ID
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleMessagetransfer1177); 

            	        	newLeafNode(otherlv_11, grammarAccess.getMessagetransferAccess().getCommaKeyword_7_0());
            	        
            	    this_ID_12=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessagetransfer1188); 
            	     
            	        newLeafNode(this_ID_12, grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_7_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            otherlv_13=(Token)match(input,14,FOLLOW_14_in_ruleMessagetransfer1201); 

                	newLeafNode(otherlv_13, grammarAccess.getMessagetransferAccess().getSemicolonKeyword_8());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMessagetransfer"


    // $ANTLR start "entryRuleParameter"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:501:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:502:2: (iv_ruleParameter= ruleParameter EOF )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:503:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_ruleParameter_in_entryRuleParameter1237);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameter1247); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:510:1: ruleParameter returns [EObject current=null] : ( ( (lv_type_0_0= RULE_STRING ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_type_0_0=null;
        Token otherlv_1=null;
        Token lv_value_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:513:28: ( ( ( (lv_type_0_0= RULE_STRING ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:514:1: ( ( (lv_type_0_0= RULE_STRING ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:514:1: ( ( (lv_type_0_0= RULE_STRING ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )? )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:514:2: ( (lv_type_0_0= RULE_STRING ) ) (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )?
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:514:2: ( (lv_type_0_0= RULE_STRING ) )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:515:1: (lv_type_0_0= RULE_STRING )
            {
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:515:1: (lv_type_0_0= RULE_STRING )
            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:516:3: lv_type_0_0= RULE_STRING
            {
            lv_type_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleParameter1289); 

            			newLeafNode(lv_type_0_0, grammarAccess.getParameterAccess().getTypeSTRINGTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getParameterRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"type",
                    		lv_type_0_0, 
                    		"STRING");
            	    

            }


            }

            // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:532:2: (otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==25) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:532:4: otherlv_1= '=' ( (lv_value_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleParameter1307); 

                        	newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getEqualsSignKeyword_1_0());
                        
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:536:1: ( (lv_value_2_0= RULE_STRING ) )
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:537:1: (lv_value_2_0= RULE_STRING )
                    {
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:537:1: (lv_value_2_0= RULE_STRING )
                    // ../org.scribble.trace.editor/src-gen/org/scribble/trace/editor/dsl/parser/antlr/internal/InternalScribbleTraceDsl.g:538:3: lv_value_2_0= RULE_STRING
                    {
                    lv_value_2_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleParameter1324); 

                    			newLeafNode(lv_value_2_0, grammarAccess.getParameterAccess().getValueSTRINGTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleTrace_in_entryRuleTrace75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrace85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTracedefn_in_ruleTrace135 = new BitSet(new long[]{0x0000000000010012L});
    public static final BitSet FOLLOW_ruleRoledefn_in_ruleTrace155 = new BitSet(new long[]{0x0000000000010012L});
    public static final BitSet FOLLOW_ruleStepdefn_in_ruleTrace177 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_ruleSentence_in_entryRuleSentence215 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSentence226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSentence266 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_ruleTracedefn_in_entryRuleTracedefn313 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTracedefn324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleTracedefn362 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTracedefn377 = new BitSet(new long[]{0x0000000000005000L});
    public static final BitSet FOLLOW_12_in_ruleTracedefn396 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleSentence_in_ruleTracedefn418 = new BitSet(new long[]{0x0000000000006000L});
    public static final BitSet FOLLOW_13_in_ruleTracedefn437 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleSentence_in_ruleTracedefn459 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTracedefn481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule522 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModule573 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleModule592 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModule607 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ruleRoledefn_in_entryRuleRoledefn655 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoledefn666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleRoledefn704 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoledefn719 = new BitSet(new long[]{0x0000000000024000L});
    public static final BitSet FOLLOW_17_in_ruleRoledefn738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModule_in_ruleRoledefn760 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleRoledefn778 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoledefn793 = new BitSet(new long[]{0x0000000000084000L});
    public static final BitSet FOLLOW_19_in_ruleRoledefn812 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoledefn827 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleRoledefn849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStepdefn_in_entryRuleStepdefn889 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStepdefn899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessagetransfer_in_ruleStepdefn945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessagetransfer_in_entryRuleMessagetransfer979 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessagetransfer989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessagetransfer1034 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_20_in_ruleMessagetransfer1046 = new BitSet(new long[]{0x0000000000400020L});
    public static final BitSet FOLLOW_ruleParameter_in_ruleMessagetransfer1068 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleMessagetransfer1081 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleParameter_in_ruleMessagetransfer1102 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleMessagetransfer1118 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleMessagetransfer1132 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessagetransfer1143 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleMessagetransfer1154 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessagetransfer1165 = new BitSet(new long[]{0x0000000000204000L});
    public static final BitSet FOLLOW_21_in_ruleMessagetransfer1177 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessagetransfer1188 = new BitSet(new long[]{0x0000000000204000L});
    public static final BitSet FOLLOW_14_in_ruleMessagetransfer1201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameter_in_entryRuleParameter1237 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameter1247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleParameter1289 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_ruleParameter1307 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleParameter1324 = new BitSet(new long[]{0x0000000000000002L});

}