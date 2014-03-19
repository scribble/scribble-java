package org.scribble.editor.dsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.scribble.editor.dsl.services.ScribbleDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalScribbleDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'module'", "';'", "'.'", "'import'", "'as'", "'from'", "'type'", "'<'", "'>'", "'('", "','", "')'", "':'", "'global'", "'protocol'", "'instantiates'", "'role'", "'sig'", "'{'", "'}'", "'to'", "'choice'", "'at'", "'or'", "'rec'", "'continue'", "'par'", "'and'", "'interruptible'", "'with'", "'by'", "'do'"
    };
    public static final int T__42=42;
    public static final int RULE_ID=4;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
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
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int RULE_STRING=5;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__34=34;
    public static final int T__15=15;
    public static final int T__35=35;
    public static final int T__18=18;
    public static final int T__36=36;
    public static final int T__17=17;
    public static final int T__37=37;
    public static final int T__12=12;
    public static final int T__38=38;
    public static final int T__11=11;
    public static final int T__39=39;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalScribbleDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalScribbleDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalScribbleDslParser.tokenNames; }
    public String getGrammarFileName() { return "../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g"; }



     	private ScribbleDslGrammarAccess grammarAccess;
     	
        public InternalScribbleDslParser(TokenStream input, ScribbleDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Module";	
       	}
       	
       	@Override
       	protected ScribbleDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:67:1: entryRuleModule returns [EObject current=null] : iv_ruleModule= ruleModule EOF ;
    public final EObject entryRuleModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModule = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:68:2: (iv_ruleModule= ruleModule EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:69:2: iv_ruleModule= ruleModule EOF
            {
             newCompositeNode(grammarAccess.getModuleRule()); 
            pushFollow(FOLLOW_ruleModule_in_entryRuleModule75);
            iv_ruleModule=ruleModule();

            state._fsp--;

             current =iv_ruleModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModule85); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:76:1: ruleModule returns [EObject current=null] : (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )* ) ;
    public final EObject ruleModule() throws RecognitionException {
        EObject current = null;

        EObject this_ModuleDecl_0 = null;

        EObject lv_imports_1_0 = null;

        EObject lv_types_2_0 = null;

        EObject lv_globals_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:79:28: ( (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:81:5: this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )*
            {
             
                    newCompositeNode(grammarAccess.getModuleAccess().getModuleDeclParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleModuleDecl_in_ruleModule132);
            this_ModuleDecl_0=ruleModuleDecl();

            state._fsp--;

             
                    current = this_ModuleDecl_0; 
                    afterParserOrEnumRuleCall();
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:89:1: ( (lv_imports_1_0= ruleImportDecl ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==14||LA1_0==16) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:90:1: (lv_imports_1_0= ruleImportDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:90:1: (lv_imports_1_0= ruleImportDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:91:3: lv_imports_1_0= ruleImportDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getImportsImportDeclParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleImportDecl_in_ruleModule152);
            	    lv_imports_1_0=ruleImportDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"imports",
            	            		lv_imports_1_0, 
            	            		"ImportDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:107:3: ( (lv_types_2_0= rulePayloadTypeDecl ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==17) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:108:1: (lv_types_2_0= rulePayloadTypeDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:108:1: (lv_types_2_0= rulePayloadTypeDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:109:3: lv_types_2_0= rulePayloadTypeDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getTypesPayloadTypeDeclParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulePayloadTypeDecl_in_ruleModule174);
            	    lv_types_2_0=rulePayloadTypeDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"types",
            	            		lv_types_2_0, 
            	            		"PayloadTypeDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:3: ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:126:1: (lv_globals_3_0= ruleGlobalProtocolDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:126:1: (lv_globals_3_0= ruleGlobalProtocolDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:127:3: lv_globals_3_0= ruleGlobalProtocolDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getGlobalsGlobalProtocolDeclParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_ruleModule196);
            	    lv_globals_3_0=ruleGlobalProtocolDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"globals",
            	            		lv_globals_3_0, 
            	            		"GlobalProtocolDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
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


    // $ANTLR start "entryRuleModuleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:151:1: entryRuleModuleDecl returns [EObject current=null] : iv_ruleModuleDecl= ruleModuleDecl EOF ;
    public final EObject entryRuleModuleDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:152:2: (iv_ruleModuleDecl= ruleModuleDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:153:2: iv_ruleModuleDecl= ruleModuleDecl EOF
            {
             newCompositeNode(grammarAccess.getModuleDeclRule()); 
            pushFollow(FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl233);
            iv_ruleModuleDecl=ruleModuleDecl();

            state._fsp--;

             current =iv_ruleModuleDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleDecl243); 

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
    // $ANTLR end "entryRuleModuleDecl"


    // $ANTLR start "ruleModuleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:160:1: ruleModuleDecl returns [EObject current=null] : (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ) ;
    public final EObject ruleModuleDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:163:28: ( (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:164:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:164:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:164:3: otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleModuleDecl280); 

                	newLeafNode(otherlv_0, grammarAccess.getModuleDeclAccess().getModuleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:168:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:169:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:169:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:170:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getModuleDeclAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleModuleDecl301);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getModuleDeclRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleModuleDecl313); 

                	newLeafNode(otherlv_2, grammarAccess.getModuleDeclAccess().getSemicolonKeyword_2());
                

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
    // $ANTLR end "ruleModuleDecl"


    // $ANTLR start "entryRuleModuleName"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:198:1: entryRuleModuleName returns [String current=null] : iv_ruleModuleName= ruleModuleName EOF ;
    public final String entryRuleModuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModuleName = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:199:2: (iv_ruleModuleName= ruleModuleName EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:200:2: iv_ruleModuleName= ruleModuleName EOF
            {
             newCompositeNode(grammarAccess.getModuleNameRule()); 
            pushFollow(FOLLOW_ruleModuleName_in_entryRuleModuleName350);
            iv_ruleModuleName=ruleModuleName();

            state._fsp--;

             current =iv_ruleModuleName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleName361); 

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
    // $ANTLR end "entryRuleModuleName"


    // $ANTLR start "ruleModuleName"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:207:1: ruleModuleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleModuleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:210:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:211:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:211:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:211:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName401); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_0()); 
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:218:1: (kw= '.' this_ID_2= RULE_ID )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==13) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:219:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,13,FOLLOW_13_in_ruleModuleName420); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getModuleNameAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName435); 

            	    		current.merge(this_ID_2);
            	        
            	     
            	        newLeafNode(this_ID_2, grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_1_1()); 
            	        

            	    }
            	    break;

            	default :
            	    break loop4;
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
    // $ANTLR end "ruleModuleName"


    // $ANTLR start "entryRuleImportDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:239:1: entryRuleImportDecl returns [EObject current=null] : iv_ruleImportDecl= ruleImportDecl EOF ;
    public final EObject entryRuleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:240:2: (iv_ruleImportDecl= ruleImportDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:241:2: iv_ruleImportDecl= ruleImportDecl EOF
            {
             newCompositeNode(grammarAccess.getImportDeclRule()); 
            pushFollow(FOLLOW_ruleImportDecl_in_entryRuleImportDecl482);
            iv_ruleImportDecl=ruleImportDecl();

            state._fsp--;

             current =iv_ruleImportDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportDecl492); 

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
    // $ANTLR end "entryRuleImportDecl"


    // $ANTLR start "ruleImportDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:248:1: ruleImportDecl returns [EObject current=null] : (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) ;
    public final EObject ruleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject this_ImportModule_0 = null;

        EObject this_ImportMember_1 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:251:28: ( (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:252:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:252:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==14) ) {
                alt5=1;
            }
            else if ( (LA5_0==16) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:253:5: this_ImportModule_0= ruleImportModule
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportModuleParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleImportModule_in_ruleImportDecl539);
                    this_ImportModule_0=ruleImportModule();

                    state._fsp--;

                     
                            current = this_ImportModule_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:263:5: this_ImportMember_1= ruleImportMember
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportMemberParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleImportMember_in_ruleImportDecl566);
                    this_ImportMember_1=ruleImportMember();

                    state._fsp--;

                     
                            current = this_ImportMember_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

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
    // $ANTLR end "ruleImportDecl"


    // $ANTLR start "entryRuleImportModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:279:1: entryRuleImportModule returns [EObject current=null] : iv_ruleImportModule= ruleImportModule EOF ;
    public final EObject entryRuleImportModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportModule = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:280:2: (iv_ruleImportModule= ruleImportModule EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:281:2: iv_ruleImportModule= ruleImportModule EOF
            {
             newCompositeNode(grammarAccess.getImportModuleRule()); 
            pushFollow(FOLLOW_ruleImportModule_in_entryRuleImportModule601);
            iv_ruleImportModule=ruleImportModule();

            state._fsp--;

             current =iv_ruleImportModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportModule611); 

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
    // $ANTLR end "entryRuleImportModule"


    // $ANTLR start "ruleImportModule"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:288:1: ruleImportModule returns [EObject current=null] : (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) ;
    public final EObject ruleImportModule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:291:28: ( (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:292:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:292:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:292:3: otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleImportModule648); 

                	newLeafNode(otherlv_0, grammarAccess.getImportModuleAccess().getImportKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:296:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:297:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:297:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:298:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportModuleAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportModule669);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getImportModuleRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:314:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==15) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:314:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleImportModule682); 

                        	newLeafNode(otherlv_2, grammarAccess.getImportModuleAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:318:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:319:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:319:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:320:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportModule699); 

                    			newLeafNode(lv_alias_3_0, grammarAccess.getImportModuleAccess().getAliasIDTerminalRuleCall_2_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getImportModuleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_3_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleImportModule718); 

                	newLeafNode(otherlv_4, grammarAccess.getImportModuleAccess().getSemicolonKeyword_3());
                

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
    // $ANTLR end "ruleImportModule"


    // $ANTLR start "entryRuleImportMember"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:348:1: entryRuleImportMember returns [EObject current=null] : iv_ruleImportMember= ruleImportMember EOF ;
    public final EObject entryRuleImportMember() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportMember = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:349:2: (iv_ruleImportMember= ruleImportMember EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:350:2: iv_ruleImportMember= ruleImportMember EOF
            {
             newCompositeNode(grammarAccess.getImportMemberRule()); 
            pushFollow(FOLLOW_ruleImportMember_in_entryRuleImportMember754);
            iv_ruleImportMember=ruleImportMember();

            state._fsp--;

             current =iv_ruleImportMember; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportMember764); 

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
    // $ANTLR end "entryRuleImportMember"


    // $ANTLR start "ruleImportMember"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:357:1: ruleImportMember returns [EObject current=null] : (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) ;
    public final EObject ruleImportMember() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
        Token otherlv_4=null;
        Token lv_alias_5_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:360:28: ( (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:361:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:361:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:361:3: otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleImportMember801); 

                	newLeafNode(otherlv_0, grammarAccess.getImportMemberAccess().getFromKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:365:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:366:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:366:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:367:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportMemberAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportMember822);
            lv_name_1_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getImportMemberRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleImportMember834); 

                	newLeafNode(otherlv_2, grammarAccess.getImportMemberAccess().getImportKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:387:1: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:388:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:388:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:389:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember851); 

            			newLeafNode(lv_member_3_0, grammarAccess.getImportMemberAccess().getMemberIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getImportMemberRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:405:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==15) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:405:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                    {
                    otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleImportMember869); 

                        	newLeafNode(otherlv_4, grammarAccess.getImportMemberAccess().getAsKeyword_4_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:409:1: ( (lv_alias_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:410:1: (lv_alias_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:410:1: (lv_alias_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:411:3: lv_alias_5_0= RULE_ID
                    {
                    lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember886); 

                    			newLeafNode(lv_alias_5_0, grammarAccess.getImportMemberAccess().getAliasIDTerminalRuleCall_4_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getImportMemberRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_5_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,12,FOLLOW_12_in_ruleImportMember905); 

                	newLeafNode(otherlv_6, grammarAccess.getImportMemberAccess().getSemicolonKeyword_5());
                

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
    // $ANTLR end "ruleImportMember"


    // $ANTLR start "entryRulePayloadTypeDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:439:1: entryRulePayloadTypeDecl returns [EObject current=null] : iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF ;
    public final EObject entryRulePayloadTypeDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadTypeDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:440:2: (iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:441:2: iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF
            {
             newCompositeNode(grammarAccess.getPayloadTypeDeclRule()); 
            pushFollow(FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl941);
            iv_rulePayloadTypeDecl=rulePayloadTypeDecl();

            state._fsp--;

             current =iv_rulePayloadTypeDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadTypeDecl951); 

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
    // $ANTLR end "entryRulePayloadTypeDecl"


    // $ANTLR start "rulePayloadTypeDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:448:1: rulePayloadTypeDecl returns [EObject current=null] : (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) ;
    public final EObject rulePayloadTypeDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_schema_2_0=null;
        Token otherlv_3=null;
        Token lv_type_4_0=null;
        Token otherlv_5=null;
        Token lv_location_6_0=null;
        Token otherlv_7=null;
        Token lv_alias_8_0=null;
        Token otherlv_9=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:451:28: ( (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:452:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:452:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:452:3: otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_rulePayloadTypeDecl988); 

                	newLeafNode(otherlv_0, grammarAccess.getPayloadTypeDeclAccess().getTypeKeyword_0());
                
            otherlv_1=(Token)match(input,18,FOLLOW_18_in_rulePayloadTypeDecl1000); 

                	newLeafNode(otherlv_1, grammarAccess.getPayloadTypeDeclAccess().getLessThanSignKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:460:1: ( (lv_schema_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:461:1: (lv_schema_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:461:1: (lv_schema_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:462:3: lv_schema_2_0= RULE_ID
            {
            lv_schema_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl1017); 

            			newLeafNode(lv_schema_2_0, grammarAccess.getPayloadTypeDeclAccess().getSchemaIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"schema",
                    		lv_schema_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,19,FOLLOW_19_in_rulePayloadTypeDecl1034); 

                	newLeafNode(otherlv_3, grammarAccess.getPayloadTypeDeclAccess().getGreaterThanSignKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:482:1: ( (lv_type_4_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:483:1: (lv_type_4_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:483:1: (lv_type_4_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:484:3: lv_type_4_0= RULE_STRING
            {
            lv_type_4_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1051); 

            			newLeafNode(lv_type_4_0, grammarAccess.getPayloadTypeDeclAccess().getTypeSTRINGTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"type",
                    		lv_type_4_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_5=(Token)match(input,16,FOLLOW_16_in_rulePayloadTypeDecl1068); 

                	newLeafNode(otherlv_5, grammarAccess.getPayloadTypeDeclAccess().getFromKeyword_5());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:504:1: ( (lv_location_6_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:505:1: (lv_location_6_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:505:1: (lv_location_6_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:506:3: lv_location_6_0= RULE_STRING
            {
            lv_location_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1085); 

            			newLeafNode(lv_location_6_0, grammarAccess.getPayloadTypeDeclAccess().getLocationSTRINGTerminalRuleCall_6_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"location",
                    		lv_location_6_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_7=(Token)match(input,15,FOLLOW_15_in_rulePayloadTypeDecl1102); 

                	newLeafNode(otherlv_7, grammarAccess.getPayloadTypeDeclAccess().getAsKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:526:1: ( (lv_alias_8_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:527:1: (lv_alias_8_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:527:1: (lv_alias_8_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:528:3: lv_alias_8_0= RULE_ID
            {
            lv_alias_8_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl1119); 

            			newLeafNode(lv_alias_8_0, grammarAccess.getPayloadTypeDeclAccess().getAliasIDTerminalRuleCall_8_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadTypeDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"alias",
                    		lv_alias_8_0, 
                    		"ID");
            	    

            }


            }

            otherlv_9=(Token)match(input,12,FOLLOW_12_in_rulePayloadTypeDecl1136); 

                	newLeafNode(otherlv_9, grammarAccess.getPayloadTypeDeclAccess().getSemicolonKeyword_9());
                

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
    // $ANTLR end "rulePayloadTypeDecl"


    // $ANTLR start "entryRuleMessageSignature"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:558:1: entryRuleMessageSignature returns [EObject current=null] : iv_ruleMessageSignature= ruleMessageSignature EOF ;
    public final EObject entryRuleMessageSignature() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageSignature = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:559:2: (iv_ruleMessageSignature= ruleMessageSignature EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:560:2: iv_ruleMessageSignature= ruleMessageSignature EOF
            {
             newCompositeNode(grammarAccess.getMessageSignatureRule()); 
            pushFollow(FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1174);
            iv_ruleMessageSignature=ruleMessageSignature();

            state._fsp--;

             current =iv_ruleMessageSignature; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessageSignature1184); 

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
    // $ANTLR end "entryRuleMessageSignature"


    // $ANTLR start "ruleMessageSignature"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:567:1: ruleMessageSignature returns [EObject current=null] : ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' ) ;
    public final EObject ruleMessageSignature() throws RecognitionException {
        EObject current = null;

        Token lv_operator_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_types_2_0 = null;

        EObject lv_types_4_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:570:28: ( ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:571:1: ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:571:1: ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:571:2: ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:571:2: ( (lv_operator_0_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:572:1: (lv_operator_0_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:572:1: (lv_operator_0_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:573:3: lv_operator_0_0= RULE_ID
            {
            lv_operator_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessageSignature1226); 

            			newLeafNode(lv_operator_0_0, grammarAccess.getMessageSignatureAccess().getOperatorIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMessageSignatureRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"operator",
                    		lv_operator_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,20,FOLLOW_20_in_ruleMessageSignature1243); 

                	newLeafNode(otherlv_1, grammarAccess.getMessageSignatureAccess().getLeftParenthesisKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:593:1: ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_ID) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:593:2: ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )*
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:593:2: ( (lv_types_2_0= rulePayloadElement ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:594:1: (lv_types_2_0= rulePayloadElement )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:594:1: (lv_types_2_0= rulePayloadElement )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:595:3: lv_types_2_0= rulePayloadElement
                    {
                     
                    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1265);
                    lv_types_2_0=rulePayloadElement();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMessageSignatureRule());
                    	        }
                           		add(
                           			current, 
                           			"types",
                            		lv_types_2_0, 
                            		"PayloadElement");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:611:2: (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==21) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:611:4: otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) )
                    	    {
                    	    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleMessageSignature1278); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getMessageSignatureAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:615:1: ( (lv_types_4_0= rulePayloadElement ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:616:1: (lv_types_4_0= rulePayloadElement )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:616:1: (lv_types_4_0= rulePayloadElement )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:617:3: lv_types_4_0= rulePayloadElement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1299);
                    	    lv_types_4_0=rulePayloadElement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMessageSignatureRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"types",
                    	            		lv_types_4_0, 
                    	            		"PayloadElement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,22,FOLLOW_22_in_ruleMessageSignature1315); 

                	newLeafNode(otherlv_5, grammarAccess.getMessageSignatureAccess().getRightParenthesisKeyword_3());
                

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
    // $ANTLR end "ruleMessageSignature"


    // $ANTLR start "entryRulePayloadElement"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:645:1: entryRulePayloadElement returns [EObject current=null] : iv_rulePayloadElement= rulePayloadElement EOF ;
    public final EObject entryRulePayloadElement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadElement = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:646:2: (iv_rulePayloadElement= rulePayloadElement EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:647:2: iv_rulePayloadElement= rulePayloadElement EOF
            {
             newCompositeNode(grammarAccess.getPayloadElementRule()); 
            pushFollow(FOLLOW_rulePayloadElement_in_entryRulePayloadElement1351);
            iv_rulePayloadElement=rulePayloadElement();

            state._fsp--;

             current =iv_rulePayloadElement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadElement1361); 

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
    // $ANTLR end "entryRulePayloadElement"


    // $ANTLR start "rulePayloadElement"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:654:1: rulePayloadElement returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) ;
    public final EObject rulePayloadElement() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_type_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:657:28: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_ID) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==23) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:658:3: ( (lv_name_0_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:659:1: (lv_name_0_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:659:1: (lv_name_0_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:660:3: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1404); 

                    			newLeafNode(lv_name_0_0, grammarAccess.getPayloadElementAccess().getNameIDTerminalRuleCall_0_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPayloadElementRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_0_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,23,FOLLOW_23_in_rulePayloadElement1421); 

                        	newLeafNode(otherlv_1, grammarAccess.getPayloadElementAccess().getColonKeyword_0_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:680:3: ( (lv_type_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:681:1: (lv_type_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:681:1: (lv_type_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:682:3: lv_type_2_0= RULE_ID
            {
            lv_type_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1440); 

            			newLeafNode(lv_type_2_0, grammarAccess.getPayloadElementAccess().getTypeIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPayloadElementRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"type",
                    		lv_type_2_0, 
                    		"ID");
            	    

            }


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
    // $ANTLR end "rulePayloadElement"


    // $ANTLR start "entryRuleGlobalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:706:1: entryRuleGlobalProtocolDecl returns [EObject current=null] : iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF ;
    public final EObject entryRuleGlobalProtocolDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:707:2: (iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:708:2: iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1481);
            iv_ruleGlobalProtocolDecl=ruleGlobalProtocolDecl();

            state._fsp--;

             current =iv_ruleGlobalProtocolDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1491); 

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
    // $ANTLR end "entryRuleGlobalProtocolDecl"


    // $ANTLR start "ruleGlobalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:715:1: ruleGlobalProtocolDecl returns [EObject current=null] : (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) ;
    public final EObject ruleGlobalProtocolDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token lv_instantiates_15_0=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        EObject lv_parameters_4_0 = null;

        EObject lv_parameters_6_0 = null;

        EObject lv_roles_9_0 = null;

        EObject lv_roles_11_0 = null;

        EObject lv_block_13_0 = null;

        EObject lv_arguments_17_0 = null;

        EObject lv_arguments_19_0 = null;

        EObject lv_roleInstantiations_22_0 = null;

        EObject lv_roleInstantiations_24_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:718:28: ( (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:719:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:719:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:719:3: otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_24_in_ruleGlobalProtocolDecl1528); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalProtocolDeclAccess().getGlobalKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleGlobalProtocolDecl1540); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolDeclAccess().getProtocolKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:727:1: ( (lv_name_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:728:1: (lv_name_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:728:1: (lv_name_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:729:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1557); 

            			newLeafNode(lv_name_2_0, grammarAccess.getGlobalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:745:2: (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==18) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:745:4: otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1575); 

                        	newLeafNode(otherlv_3, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:749:1: ( (lv_parameters_4_0= ruleParameterDecl ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:750:1: (lv_parameters_4_0= ruleParameterDecl )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:750:1: (lv_parameters_4_0= ruleParameterDecl )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:751:3: lv_parameters_4_0= ruleParameterDecl
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1596);
                    lv_parameters_4_0=ruleParameterDecl();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_4_0, 
                            		"ParameterDecl");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:767:2: (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==21) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:767:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    {
                    	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1609); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:771:1: ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:772:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:772:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:773:3: lv_parameters_6_0= ruleParameterDecl
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1630);
                    	    lv_parameters_6_0=ruleParameterDecl();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_6_0, 
                    	            		"ParameterDecl");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1644); 

                        	newLeafNode(otherlv_7, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_8=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1658); 

                	newLeafNode(otherlv_8, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:797:1: ( (lv_roles_9_0= ruleRoleDecl ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:798:1: (lv_roles_9_0= ruleRoleDecl )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:798:1: (lv_roles_9_0= ruleRoleDecl )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:799:3: lv_roles_9_0= ruleRoleDecl
            {
             
            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1679);
            lv_roles_9_0=ruleRoleDecl();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_9_0, 
                    		"RoleDecl");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:815:2: (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==21) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:815:4: otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) )
            	    {
            	    otherlv_10=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1692); 

            	        	newLeafNode(otherlv_10, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:819:1: ( (lv_roles_11_0= ruleRoleDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:820:1: (lv_roles_11_0= ruleRoleDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:820:1: (lv_roles_11_0= ruleRoleDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:821:3: lv_roles_11_0= ruleRoleDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1713);
            	    lv_roles_11_0=ruleRoleDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_11_0, 
            	            		"RoleDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            otherlv_12=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1727); 

                	newLeafNode(otherlv_12, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:841:1: ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==29) ) {
                alt17=1;
            }
            else if ( (LA17_0==26) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:841:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:841:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:842:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:842:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:843:3: lv_block_13_0= ruleGlobalProtocolBlock
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getBlockGlobalProtocolBlockParserRuleCall_8_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1749);
                    lv_block_13_0=ruleGlobalProtocolBlock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		set(
                           			current, 
                           			"block",
                            		lv_block_13_0, 
                            		"GlobalProtocolBlock");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:8: otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';'
                    {
                    otherlv_14=(Token)match(input,26,FOLLOW_26_in_ruleGlobalProtocolDecl1768); 

                        	newLeafNode(otherlv_14, grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesKeyword_8_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:864:1: ( (lv_instantiates_15_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:865:1: (lv_instantiates_15_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:865:1: (lv_instantiates_15_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:866:3: lv_instantiates_15_0= RULE_ID
                    {
                    lv_instantiates_15_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1785); 

                    			newLeafNode(lv_instantiates_15_0, grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_8_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"instantiates",
                            		lv_instantiates_15_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:882:2: (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==18) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:882:4: otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>'
                            {
                            otherlv_16=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1803); 

                                	newLeafNode(otherlv_16, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_8_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:886:1: ( (lv_arguments_17_0= ruleArgument ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:887:1: (lv_arguments_17_0= ruleArgument )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:887:1: (lv_arguments_17_0= ruleArgument )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:888:3: lv_arguments_17_0= ruleArgument
                            {
                             
                            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1824);
                            lv_arguments_17_0=ruleArgument();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"arguments",
                                    		lv_arguments_17_0, 
                                    		"Argument");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:904:2: (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==21) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:904:4: otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) )
                            	    {
                            	    otherlv_18=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1837); 

                            	        	newLeafNode(otherlv_18, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_2_2_0());
                            	        
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:908:1: ( (lv_arguments_19_0= ruleArgument ) )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:909:1: (lv_arguments_19_0= ruleArgument )
                            	    {
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:909:1: (lv_arguments_19_0= ruleArgument )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:910:3: lv_arguments_19_0= ruleArgument
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1858);
                            	    lv_arguments_19_0=ruleArgument();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"arguments",
                            	            		lv_arguments_19_0, 
                            	            		"Argument");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop14;
                                }
                            } while (true);

                            otherlv_20=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1872); 

                                	newLeafNode(otherlv_20, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_8_1_2_3());
                                

                            }
                            break;

                    }

                    otherlv_21=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1886); 

                        	newLeafNode(otherlv_21, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_8_1_3());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:934:1: ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:935:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:935:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:936:3: lv_roleInstantiations_22_0= ruleRoleInstantiation
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1907);
                    lv_roleInstantiations_22_0=ruleRoleInstantiation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"roleInstantiations",
                            		lv_roleInstantiations_22_0, 
                            		"RoleInstantiation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:952:2: (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==21) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:952:4: otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    {
                    	    otherlv_23=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1920); 

                    	        	newLeafNode(otherlv_23, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_5_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:956:1: ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:957:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:957:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:958:3: lv_roleInstantiations_24_0= ruleRoleInstantiation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1941);
                    	    lv_roleInstantiations_24_0=ruleRoleInstantiation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"roleInstantiations",
                    	            		lv_roleInstantiations_24_0, 
                    	            		"RoleInstantiation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);

                    otherlv_25=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1955); 

                        	newLeafNode(otherlv_25, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_8_1_6());
                        
                    otherlv_26=(Token)match(input,12,FOLLOW_12_in_ruleGlobalProtocolDecl1967); 

                        	newLeafNode(otherlv_26, grammarAccess.getGlobalProtocolDeclAccess().getSemicolonKeyword_8_1_7());
                        

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
    // $ANTLR end "ruleGlobalProtocolDecl"


    // $ANTLR start "entryRuleRoleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:990:1: entryRuleRoleDecl returns [EObject current=null] : iv_ruleRoleDecl= ruleRoleDecl EOF ;
    public final EObject entryRuleRoleDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:991:2: (iv_ruleRoleDecl= ruleRoleDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:992:2: iv_ruleRoleDecl= ruleRoleDecl EOF
            {
             newCompositeNode(grammarAccess.getRoleDeclRule()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl2005);
            iv_ruleRoleDecl=ruleRoleDecl();

            state._fsp--;

             current =iv_ruleRoleDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleDecl2015); 

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
    // $ANTLR end "entryRuleRoleDecl"


    // $ANTLR start "ruleRoleDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:999:1: ruleRoleDecl returns [EObject current=null] : (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1002:28: ( (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1003:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1003:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1003:3: otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,27,FOLLOW_27_in_ruleRoleDecl2052); 

                	newLeafNode(otherlv_0, grammarAccess.getRoleDeclAccess().getRoleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1007:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1008:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1008:1: (lv_name_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1009:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2069); 

            			newLeafNode(lv_name_1_0, grammarAccess.getRoleDeclAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRoleDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1025:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==15) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1025:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleRoleDecl2087); 

                        	newLeafNode(otherlv_2, grammarAccess.getRoleDeclAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1029:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1030:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1030:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1031:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2104); 

                    			newLeafNode(lv_alias_3_0, grammarAccess.getRoleDeclAccess().getAliasIDTerminalRuleCall_2_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRoleDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_3_0, 
                            		"ID");
                    	    

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
    // $ANTLR end "ruleRoleDecl"


    // $ANTLR start "entryRuleParameterDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1055:1: entryRuleParameterDecl returns [EObject current=null] : iv_ruleParameterDecl= ruleParameterDecl EOF ;
    public final EObject entryRuleParameterDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1056:2: (iv_ruleParameterDecl= ruleParameterDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1057:2: iv_ruleParameterDecl= ruleParameterDecl EOF
            {
             newCompositeNode(grammarAccess.getParameterDeclRule()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2147);
            iv_ruleParameterDecl=ruleParameterDecl();

            state._fsp--;

             current =iv_ruleParameterDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameterDecl2157); 

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
    // $ANTLR end "entryRuleParameterDecl"


    // $ANTLR start "ruleParameterDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1064:1: ruleParameterDecl returns [EObject current=null] : ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) ;
    public final EObject ruleParameterDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;
        Token otherlv_4=null;
        Token lv_name_5_0=null;
        Token otherlv_6=null;
        Token lv_alias_7_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1067:28: ( ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1068:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1068:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==17) ) {
                alt21=1;
            }
            else if ( (LA21_0==28) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1068:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1068:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1068:4: otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    {
                    otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleParameterDecl2195); 

                        	newLeafNode(otherlv_0, grammarAccess.getParameterDeclAccess().getTypeKeyword_0_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1072:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1073:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1073:1: (lv_name_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1074:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2212); 

                    			newLeafNode(lv_name_1_0, grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_0_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1090:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==15) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1090:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                            {
                            otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2230); 

                                	newLeafNode(otherlv_2, grammarAccess.getParameterDeclAccess().getAsKeyword_0_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1094:1: ( (lv_alias_3_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1095:1: (lv_alias_3_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1095:1: (lv_alias_3_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1096:3: lv_alias_3_0= RULE_ID
                            {
                            lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2247); 

                            			newLeafNode(lv_alias_3_0, grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_0_2_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getParameterDeclRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_3_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1113:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1113:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1113:8: otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    {
                    otherlv_4=(Token)match(input,28,FOLLOW_28_in_ruleParameterDecl2274); 

                        	newLeafNode(otherlv_4, grammarAccess.getParameterDeclAccess().getSigKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1117:1: ( (lv_name_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1118:1: (lv_name_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1118:1: (lv_name_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1119:3: lv_name_5_0= RULE_ID
                    {
                    lv_name_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2291); 

                    			newLeafNode(lv_name_5_0, grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_5_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1135:2: (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==15) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1135:4: otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) )
                            {
                            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2309); 

                                	newLeafNode(otherlv_6, grammarAccess.getParameterDeclAccess().getAsKeyword_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1139:1: ( (lv_alias_7_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1140:1: (lv_alias_7_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1140:1: (lv_alias_7_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1141:3: lv_alias_7_0= RULE_ID
                            {
                            lv_alias_7_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2326); 

                            			newLeafNode(lv_alias_7_0, grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_1_2_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getParameterDeclRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_7_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleParameterDecl"


    // $ANTLR start "entryRuleRoleInstantiation"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1165:1: entryRuleRoleInstantiation returns [EObject current=null] : iv_ruleRoleInstantiation= ruleRoleInstantiation EOF ;
    public final EObject entryRuleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleInstantiation = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1166:2: (iv_ruleRoleInstantiation= ruleRoleInstantiation EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1167:2: iv_ruleRoleInstantiation= ruleRoleInstantiation EOF
            {
             newCompositeNode(grammarAccess.getRoleInstantiationRule()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2370);
            iv_ruleRoleInstantiation=ruleRoleInstantiation();

            state._fsp--;

             current =iv_ruleRoleInstantiation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleInstantiation2380); 

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
    // $ANTLR end "entryRuleRoleInstantiation"


    // $ANTLR start "ruleRoleInstantiation"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1174:1: ruleRoleInstantiation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_alias_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1177:28: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1178:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1178:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1178:2: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1178:2: ( (lv_name_0_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1179:1: (lv_name_0_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1179:1: (lv_name_0_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1180:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2422); 

            			newLeafNode(lv_name_0_0, grammarAccess.getRoleInstantiationAccess().getNameIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRoleInstantiationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1196:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==15) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1196:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleRoleInstantiation2440); 

                        	newLeafNode(otherlv_1, grammarAccess.getRoleInstantiationAccess().getAsKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1200:1: ( (lv_alias_2_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1201:1: (lv_alias_2_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1201:1: (lv_alias_2_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1202:3: lv_alias_2_0= RULE_ID
                    {
                    lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2457); 

                    			newLeafNode(lv_alias_2_0, grammarAccess.getRoleInstantiationAccess().getAliasIDTerminalRuleCall_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRoleInstantiationRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"alias",
                            		lv_alias_2_0, 
                            		"ID");
                    	    

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
    // $ANTLR end "ruleRoleInstantiation"


    // $ANTLR start "entryRuleArgument"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1226:1: entryRuleArgument returns [EObject current=null] : iv_ruleArgument= ruleArgument EOF ;
    public final EObject entryRuleArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgument = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1227:2: (iv_ruleArgument= ruleArgument EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1228:2: iv_ruleArgument= ruleArgument EOF
            {
             newCompositeNode(grammarAccess.getArgumentRule()); 
            pushFollow(FOLLOW_ruleArgument_in_entryRuleArgument2500);
            iv_ruleArgument=ruleArgument();

            state._fsp--;

             current =iv_ruleArgument; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleArgument2510); 

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
    // $ANTLR end "entryRuleArgument"


    // $ANTLR start "ruleArgument"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1235:1: ruleArgument returns [EObject current=null] : ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) ;
    public final EObject ruleArgument() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_alias_2_0=null;
        Token lv_name_3_0=null;
        Token otherlv_4=null;
        Token lv_alias_5_0=null;
        EObject lv_signature_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1238:28: ( ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_ID) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==EOF||LA25_1==15||LA25_1==19||LA25_1==21) ) {
                    alt25=2;
                }
                else if ( (LA25_1==20) ) {
                    alt25=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:3: ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1239:3: ( (lv_signature_0_0= ruleMessageSignature ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1240:1: (lv_signature_0_0= ruleMessageSignature )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1240:1: (lv_signature_0_0= ruleMessageSignature )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1241:3: lv_signature_0_0= ruleMessageSignature
                    {
                     
                    	        newCompositeNode(grammarAccess.getArgumentAccess().getSignatureMessageSignatureParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleArgument2557);
                    lv_signature_0_0=ruleMessageSignature();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getArgumentRule());
                    	        }
                           		set(
                           			current, 
                           			"signature",
                            		lv_signature_0_0, 
                            		"MessageSignature");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1257:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==15) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1257:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                            {
                            otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleArgument2570); 

                                	newLeafNode(otherlv_1, grammarAccess.getArgumentAccess().getAsKeyword_0_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1261:1: ( (lv_alias_2_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1262:1: (lv_alias_2_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1262:1: (lv_alias_2_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1263:3: lv_alias_2_0= RULE_ID
                            {
                            lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2587); 

                            			newLeafNode(lv_alias_2_0, grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_0_1_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getArgumentRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_2_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1280:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1280:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1280:7: ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1280:7: ( (lv_name_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1281:1: (lv_name_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1281:1: (lv_name_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1282:3: lv_name_3_0= RULE_ID
                    {
                    lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2619); 

                    			newLeafNode(lv_name_3_0, grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getArgumentRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"name",
                            		lv_name_3_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1298:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==15) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1298:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                            {
                            otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleArgument2637); 

                                	newLeafNode(otherlv_4, grammarAccess.getArgumentAccess().getAsKeyword_1_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1302:1: ( (lv_alias_5_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1303:1: (lv_alias_5_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1303:1: (lv_alias_5_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1304:3: lv_alias_5_0= RULE_ID
                            {
                            lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2654); 

                            			newLeafNode(lv_alias_5_0, grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_1_1_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getArgumentRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"alias",
                                    		lv_alias_5_0, 
                                    		"ID");
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleArgument"


    // $ANTLR start "entryRuleGlobalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1328:1: entryRuleGlobalProtocolBlock returns [EObject current=null] : iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF ;
    public final EObject entryRuleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolBlock = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1329:2: (iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1330:2: iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2698);
            iv_ruleGlobalProtocolBlock=ruleGlobalProtocolBlock();

            state._fsp--;

             current =iv_ruleGlobalProtocolBlock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2708); 

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
    // $ANTLR end "entryRuleGlobalProtocolBlock"


    // $ANTLR start "ruleGlobalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1337:1: ruleGlobalProtocolBlock returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) ;
    public final EObject ruleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_activities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1340:28: ( ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1341:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1341:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1341:2: () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1341:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1342:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getGlobalProtocolBlockAccess().getGlobalProtocolBlockAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,29,FOLLOW_29_in_ruleGlobalProtocolBlock2754); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolBlockAccess().getLeftCurlyBracketKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1351:1: ( (lv_activities_2_0= ruleGlobalInteraction ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_ID||LA26_0==32||(LA26_0>=35 && LA26_0<=37)||LA26_0==39||LA26_0==42) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1352:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1352:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1353:3: lv_activities_2_0= ruleGlobalInteraction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesGlobalInteractionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2775);
            	    lv_activities_2_0=ruleGlobalInteraction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalProtocolBlockRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"activities",
            	            		lv_activities_2_0, 
            	            		"GlobalInteraction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            otherlv_3=(Token)match(input,30,FOLLOW_30_in_ruleGlobalProtocolBlock2788); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalProtocolBlockAccess().getRightCurlyBracketKeyword_3());
                

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
    // $ANTLR end "ruleGlobalProtocolBlock"


    // $ANTLR start "entryRuleGlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1381:1: entryRuleGlobalInteraction returns [EObject current=null] : iv_ruleGlobalInteraction= ruleGlobalInteraction EOF ;
    public final EObject entryRuleGlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInteraction = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1382:2: (iv_ruleGlobalInteraction= ruleGlobalInteraction EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1383:2: iv_ruleGlobalInteraction= ruleGlobalInteraction EOF
            {
             newCompositeNode(grammarAccess.getGlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2824);
            iv_ruleGlobalInteraction=ruleGlobalInteraction();

            state._fsp--;

             current =iv_ruleGlobalInteraction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInteraction2834); 

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
    // $ANTLR end "entryRuleGlobalInteraction"


    // $ANTLR start "ruleGlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1390:1: ruleGlobalInteraction returns [EObject current=null] : (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) ;
    public final EObject ruleGlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject this_GlobalMessageTransfer_0 = null;

        EObject this_GlobalChoice_1 = null;

        EObject this_GlobalRecursion_2 = null;

        EObject this_GlobalContinue_3 = null;

        EObject this_GlobalParallel_4 = null;

        EObject this_GlobalInterruptible_5 = null;

        EObject this_GlobalDo_6 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1393:28: ( (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1394:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1394:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
            int alt27=7;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt27=1;
                }
                break;
            case 32:
                {
                alt27=2;
                }
                break;
            case 35:
                {
                alt27=3;
                }
                break;
            case 36:
                {
                alt27=4;
                }
                break;
            case 37:
                {
                alt27=5;
                }
                break;
            case 39:
                {
                alt27=6;
                }
                break;
            case 42:
                {
                alt27=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1395:5: this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalMessageTransferParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2881);
                    this_GlobalMessageTransfer_0=ruleGlobalMessageTransfer();

                    state._fsp--;

                     
                            current = this_GlobalMessageTransfer_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1405:5: this_GlobalChoice_1= ruleGlobalChoice
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalChoiceParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2908);
                    this_GlobalChoice_1=ruleGlobalChoice();

                    state._fsp--;

                     
                            current = this_GlobalChoice_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1415:5: this_GlobalRecursion_2= ruleGlobalRecursion
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalRecursionParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2935);
                    this_GlobalRecursion_2=ruleGlobalRecursion();

                    state._fsp--;

                     
                            current = this_GlobalRecursion_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1425:5: this_GlobalContinue_3= ruleGlobalContinue
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalContinueParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2962);
                    this_GlobalContinue_3=ruleGlobalContinue();

                    state._fsp--;

                     
                            current = this_GlobalContinue_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1435:5: this_GlobalParallel_4= ruleGlobalParallel
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalParallelParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction2989);
                    this_GlobalParallel_4=ruleGlobalParallel();

                    state._fsp--;

                     
                            current = this_GlobalParallel_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1445:5: this_GlobalInterruptible_5= ruleGlobalInterruptible
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalInterruptibleParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction3016);
                    this_GlobalInterruptible_5=ruleGlobalInterruptible();

                    state._fsp--;

                     
                            current = this_GlobalInterruptible_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1455:5: this_GlobalDo_6= ruleGlobalDo
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalDoParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction3043);
                    this_GlobalDo_6=ruleGlobalDo();

                    state._fsp--;

                     
                            current = this_GlobalDo_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

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
    // $ANTLR end "ruleGlobalInteraction"


    // $ANTLR start "entryRuleGlobalMessageTransfer"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1471:1: entryRuleGlobalMessageTransfer returns [EObject current=null] : iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF ;
    public final EObject entryRuleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalMessageTransfer = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1472:2: (iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1473:2: iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF
            {
             newCompositeNode(grammarAccess.getGlobalMessageTransferRule()); 
            pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3078);
            iv_ruleGlobalMessageTransfer=ruleGlobalMessageTransfer();

            state._fsp--;

             current =iv_ruleGlobalMessageTransfer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3088); 

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
    // $ANTLR end "entryRuleGlobalMessageTransfer"


    // $ANTLR start "ruleGlobalMessageTransfer"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1480:1: ruleGlobalMessageTransfer returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRole_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )* otherlv_7= ';' ) ;
    public final EObject ruleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_fromRole_2_0=null;
        Token otherlv_3=null;
        Token lv_toRole_4_0=null;
        Token otherlv_5=null;
        Token lv_toRole_6_0=null;
        Token otherlv_7=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1483:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRole_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )* otherlv_7= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1484:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRole_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1484:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRole_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1484:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRole_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )* otherlv_7= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1484:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1485:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1485:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1486:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalMessageTransferAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3134);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleGlobalMessageTransfer3146); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalMessageTransferAccess().getFromKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1506:1: ( (lv_fromRole_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1507:1: (lv_fromRole_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1507:1: (lv_fromRole_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1508:3: lv_fromRole_2_0= RULE_ID
            {
            lv_fromRole_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3163); 

            			newLeafNode(lv_fromRole_2_0, grammarAccess.getGlobalMessageTransferAccess().getFromRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,31,FOLLOW_31_in_ruleGlobalMessageTransfer3180); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalMessageTransferAccess().getToKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1528:1: ( (lv_toRole_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1529:1: (lv_toRole_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1529:1: (lv_toRole_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1530:3: lv_toRole_4_0= RULE_ID
            {
            lv_toRole_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3197); 

            			newLeafNode(lv_toRole_4_0, grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRole",
                    		lv_toRole_4_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1546:2: (otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==21) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1546:4: otherlv_5= ',' ( (lv_toRole_6_0= RULE_ID ) )
            	    {
            	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalMessageTransfer3215); 

            	        	newLeafNode(otherlv_5, grammarAccess.getGlobalMessageTransferAccess().getCommaKeyword_5_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1550:1: ( (lv_toRole_6_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1551:1: (lv_toRole_6_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1551:1: (lv_toRole_6_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1552:3: lv_toRole_6_0= RULE_ID
            	    {
            	    lv_toRole_6_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3232); 

            	    			newLeafNode(lv_toRole_6_0, grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_5_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRole",
            	            		lv_toRole_6_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleGlobalMessageTransfer3251); 

                	newLeafNode(otherlv_7, grammarAccess.getGlobalMessageTransferAccess().getSemicolonKeyword_6());
                

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
    // $ANTLR end "ruleGlobalMessageTransfer"


    // $ANTLR start "entryRuleMessage"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1580:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1581:2: (iv_ruleMessage= ruleMessage EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1582:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage3287);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage3297); 

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
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1589:1: ruleMessage returns [EObject current=null] : (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token lv_parameter_1_0=null;
        EObject this_MessageSignature_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1592:28: ( (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1593:1: (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1593:1: (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                int LA29_1 = input.LA(2);

                if ( (LA29_1==EOF||LA29_1==16||LA29_1==21||LA29_1==41) ) {
                    alt29=2;
                }
                else if ( (LA29_1==20) ) {
                    alt29=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1594:5: this_MessageSignature_0= ruleMessageSignature
                    {
                     
                            newCompositeNode(grammarAccess.getMessageAccess().getMessageSignatureParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleMessage3344);
                    this_MessageSignature_0=ruleMessageSignature();

                    state._fsp--;

                     
                            current = this_MessageSignature_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1603:6: ( (lv_parameter_1_0= RULE_ID ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1603:6: ( (lv_parameter_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1604:1: (lv_parameter_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1604:1: (lv_parameter_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1605:3: lv_parameter_1_0= RULE_ID
                    {
                    lv_parameter_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessage3366); 

                    			newLeafNode(lv_parameter_1_0, grammarAccess.getMessageAccess().getParameterIDTerminalRuleCall_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMessageRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"parameter",
                            		lv_parameter_1_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleGlobalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1629:1: entryRuleGlobalChoice returns [EObject current=null] : iv_ruleGlobalChoice= ruleGlobalChoice EOF ;
    public final EObject entryRuleGlobalChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalChoice = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1630:2: (iv_ruleGlobalChoice= ruleGlobalChoice EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1631:2: iv_ruleGlobalChoice= ruleGlobalChoice EOF
            {
             newCompositeNode(grammarAccess.getGlobalChoiceRule()); 
            pushFollow(FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3407);
            iv_ruleGlobalChoice=ruleGlobalChoice();

            state._fsp--;

             current =iv_ruleGlobalChoice; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalChoice3417); 

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
    // $ANTLR end "entryRuleGlobalChoice"


    // $ANTLR start "ruleGlobalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1638:1: ruleGlobalChoice returns [EObject current=null] : (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) ;
    public final EObject ruleGlobalChoice() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_role_2_0=null;
        Token otherlv_4=null;
        EObject lv_blocks_3_0 = null;

        EObject lv_blocks_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1641:28: ( (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1642:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1642:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1642:3: otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleGlobalChoice3454); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalChoiceAccess().getChoiceKeyword_0());
                
            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleGlobalChoice3466); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalChoiceAccess().getAtKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1650:1: ( (lv_role_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1651:1: (lv_role_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1651:1: (lv_role_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1652:3: lv_role_2_0= RULE_ID
            {
            lv_role_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalChoice3483); 

            			newLeafNode(lv_role_2_0, grammarAccess.getGlobalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalChoiceRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1668:2: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1669:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1669:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1670:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3509);
            lv_blocks_3_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalChoiceRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_3_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1686:2: (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==34) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1686:4: otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleGlobalChoice3522); 

            	        	newLeafNode(otherlv_4, grammarAccess.getGlobalChoiceAccess().getOrKeyword_4_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1690:1: ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1691:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1691:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1692:3: lv_blocks_5_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3543);
            	    lv_blocks_5_0=ruleGlobalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalChoiceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_5_0, 
            	            		"GlobalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
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
    // $ANTLR end "ruleGlobalChoice"


    // $ANTLR start "entryRuleGlobalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1716:1: entryRuleGlobalRecursion returns [EObject current=null] : iv_ruleGlobalRecursion= ruleGlobalRecursion EOF ;
    public final EObject entryRuleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalRecursion = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1717:2: (iv_ruleGlobalRecursion= ruleGlobalRecursion EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1718:2: iv_ruleGlobalRecursion= ruleGlobalRecursion EOF
            {
             newCompositeNode(grammarAccess.getGlobalRecursionRule()); 
            pushFollow(FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3581);
            iv_ruleGlobalRecursion=ruleGlobalRecursion();

            state._fsp--;

             current =iv_ruleGlobalRecursion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalRecursion3591); 

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
    // $ANTLR end "entryRuleGlobalRecursion"


    // $ANTLR start "ruleGlobalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1725:1: ruleGlobalRecursion returns [EObject current=null] : (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) ;
    public final EObject ruleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        EObject lv_block_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1728:28: ( (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1729:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1729:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1729:3: otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleGlobalRecursion3628); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalRecursionAccess().getRecKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1733:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1734:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1734:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1735:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalRecursion3645); 

            			newLeafNode(lv_label_1_0, grammarAccess.getGlobalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalRecursionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1751:2: ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1752:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1752:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1753:3: lv_block_2_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3671);
            lv_block_2_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalRecursionRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_2_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


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
    // $ANTLR end "ruleGlobalRecursion"


    // $ANTLR start "entryRuleGlobalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1777:1: entryRuleGlobalContinue returns [EObject current=null] : iv_ruleGlobalContinue= ruleGlobalContinue EOF ;
    public final EObject entryRuleGlobalContinue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalContinue = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1778:2: (iv_ruleGlobalContinue= ruleGlobalContinue EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1779:2: iv_ruleGlobalContinue= ruleGlobalContinue EOF
            {
             newCompositeNode(grammarAccess.getGlobalContinueRule()); 
            pushFollow(FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3707);
            iv_ruleGlobalContinue=ruleGlobalContinue();

            state._fsp--;

             current =iv_ruleGlobalContinue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalContinue3717); 

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
    // $ANTLR end "entryRuleGlobalContinue"


    // $ANTLR start "ruleGlobalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1786:1: ruleGlobalContinue returns [EObject current=null] : (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) ;
    public final EObject ruleGlobalContinue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1789:28: ( (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1790:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1790:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1790:3: otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleGlobalContinue3754); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalContinueAccess().getContinueKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1794:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1795:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1795:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1796:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalContinue3771); 

            			newLeafNode(lv_label_1_0, grammarAccess.getGlobalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalContinueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleGlobalContinue3788); 

                	newLeafNode(otherlv_2, grammarAccess.getGlobalContinueAccess().getSemicolonKeyword_2());
                

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
    // $ANTLR end "ruleGlobalContinue"


    // $ANTLR start "entryRuleGlobalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1824:1: entryRuleGlobalParallel returns [EObject current=null] : iv_ruleGlobalParallel= ruleGlobalParallel EOF ;
    public final EObject entryRuleGlobalParallel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalParallel = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1825:2: (iv_ruleGlobalParallel= ruleGlobalParallel EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1826:2: iv_ruleGlobalParallel= ruleGlobalParallel EOF
            {
             newCompositeNode(grammarAccess.getGlobalParallelRule()); 
            pushFollow(FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3824);
            iv_ruleGlobalParallel=ruleGlobalParallel();

            state._fsp--;

             current =iv_ruleGlobalParallel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalParallel3834); 

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
    // $ANTLR end "entryRuleGlobalParallel"


    // $ANTLR start "ruleGlobalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1833:1: ruleGlobalParallel returns [EObject current=null] : (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) ;
    public final EObject ruleGlobalParallel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_blocks_1_0 = null;

        EObject lv_blocks_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1836:28: ( (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1837:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1837:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1837:3: otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,37,FOLLOW_37_in_ruleGlobalParallel3871); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalParallelAccess().getParKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1841:1: ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1842:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1842:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1843:3: lv_blocks_1_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3892);
            lv_blocks_1_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalParallelRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_1_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1859:2: (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==38) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1859:4: otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleGlobalParallel3905); 

            	        	newLeafNode(otherlv_2, grammarAccess.getGlobalParallelAccess().getAndKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1863:1: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1864:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1864:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1865:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3926);
            	    lv_blocks_3_0=ruleGlobalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalParallelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_3_0, 
            	            		"GlobalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
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
    // $ANTLR end "ruleGlobalParallel"


    // $ANTLR start "entryRuleGlobalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1889:1: entryRuleGlobalInterruptible returns [EObject current=null] : iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF ;
    public final EObject entryRuleGlobalInterruptible() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterruptible = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1890:2: (iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1891:2: iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptibleRule()); 
            pushFollow(FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3964);
            iv_ruleGlobalInterruptible=ruleGlobalInterruptible();

            state._fsp--;

             current =iv_ruleGlobalInterruptible; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterruptible3974); 

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
    // $ANTLR end "entryRuleGlobalInterruptible"


    // $ANTLR start "ruleGlobalInterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1898:1: ruleGlobalInterruptible returns [EObject current=null] : (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) ;
    public final EObject ruleGlobalInterruptible() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_block_3_0 = null;

        EObject lv_interrupts_6_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1901:28: ( (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1902:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1902:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1902:3: otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_ruleGlobalInterruptible4011); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalInterruptibleAccess().getInterruptibleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1906:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_ID) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1906:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1906:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1907:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1907:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1908:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterruptible4029); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getGlobalInterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalInterruptibleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalInterruptible4046); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalInterruptibleAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1928:3: ( (lv_block_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1929:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1929:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1930:3: lv_block_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4069);
            lv_block_3_0=ruleGlobalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalInterruptibleRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_3_0, 
                    		"GlobalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,40,FOLLOW_40_in_ruleGlobalInterruptible4081); 

                	newLeafNode(otherlv_4, grammarAccess.getGlobalInterruptibleAccess().getWithKeyword_3());
                
            otherlv_5=(Token)match(input,29,FOLLOW_29_in_ruleGlobalInterruptible4093); 

                	newLeafNode(otherlv_5, grammarAccess.getGlobalInterruptibleAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1954:1: ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1955:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1955:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1956:3: lv_interrupts_6_0= ruleGlobalInterrupt
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getInterruptsGlobalInterruptParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4114);
            	    lv_interrupts_6_0=ruleGlobalInterrupt();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalInterruptibleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"interrupts",
            	            		lv_interrupts_6_0, 
            	            		"GlobalInterrupt");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            otherlv_7=(Token)match(input,30,FOLLOW_30_in_ruleGlobalInterruptible4127); 

                	newLeafNode(otherlv_7, grammarAccess.getGlobalInterruptibleAccess().getRightCurlyBracketKeyword_6());
                

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
    // $ANTLR end "ruleGlobalInterruptible"


    // $ANTLR start "entryRuleGlobalInterrupt"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1984:1: entryRuleGlobalInterrupt returns [EObject current=null] : iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF ;
    public final EObject entryRuleGlobalInterrupt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterrupt = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1985:2: (iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1986:2: iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptRule()); 
            pushFollow(FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4163);
            iv_ruleGlobalInterrupt=ruleGlobalInterrupt();

            state._fsp--;

             current =iv_ruleGlobalInterrupt; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterrupt4173); 

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
    // $ANTLR end "entryRuleGlobalInterrupt"


    // $ANTLR start "ruleGlobalInterrupt"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1993:1: ruleGlobalInterrupt returns [EObject current=null] : ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) ;
    public final EObject ruleGlobalInterrupt() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_role_4_0=null;
        Token otherlv_5=null;
        EObject lv_messages_0_0 = null;

        EObject lv_messages_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1996:28: ( ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1997:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1997:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1997:2: ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1997:2: ( (lv_messages_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1998:1: (lv_messages_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1998:1: (lv_messages_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1999:3: lv_messages_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4219);
            lv_messages_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalInterruptRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2015:2: (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==21) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2015:4: otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) )
            	    {
            	    otherlv_1=(Token)match(input,21,FOLLOW_21_in_ruleGlobalInterrupt4232); 

            	        	newLeafNode(otherlv_1, grammarAccess.getGlobalInterruptAccess().getCommaKeyword_1_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2019:1: ( (lv_messages_2_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2020:1: (lv_messages_2_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2020:1: (lv_messages_2_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2021:3: lv_messages_2_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4253);
            	    lv_messages_2_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalInterruptRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_2_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            otherlv_3=(Token)match(input,41,FOLLOW_41_in_ruleGlobalInterrupt4267); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalInterruptAccess().getByKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2041:1: ( (lv_role_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2042:1: (lv_role_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2042:1: (lv_role_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2043:3: lv_role_4_0= RULE_ID
            {
            lv_role_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterrupt4284); 

            			newLeafNode(lv_role_4_0, grammarAccess.getGlobalInterruptAccess().getRoleIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalInterruptRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_4_0, 
                    		"ID");
            	    

            }


            }

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleGlobalInterrupt4301); 

                	newLeafNode(otherlv_5, grammarAccess.getGlobalInterruptAccess().getSemicolonKeyword_4());
                

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
    // $ANTLR end "ruleGlobalInterrupt"


    // $ANTLR start "entryRuleGlobalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2071:1: entryRuleGlobalDo returns [EObject current=null] : iv_ruleGlobalDo= ruleGlobalDo EOF ;
    public final EObject entryRuleGlobalDo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalDo = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2072:2: (iv_ruleGlobalDo= ruleGlobalDo EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2073:2: iv_ruleGlobalDo= ruleGlobalDo EOF
            {
             newCompositeNode(grammarAccess.getGlobalDoRule()); 
            pushFollow(FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4337);
            iv_ruleGlobalDo=ruleGlobalDo();

            state._fsp--;

             current =iv_ruleGlobalDo; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalDo4347); 

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
    // $ANTLR end "entryRuleGlobalDo"


    // $ANTLR start "ruleGlobalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2080:1: ruleGlobalDo returns [EObject current=null] : (otherlv_0= 'do' ( (lv_module_1_0= RULE_ID ) ) (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )? (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) ;
    public final EObject ruleGlobalDo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_module_1_0=null;
        Token otherlv_2=null;
        Token lv_scope_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        EObject lv_arguments_5_0 = null;

        EObject lv_arguments_7_0 = null;

        EObject lv_roles_10_0 = null;

        EObject lv_roles_12_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2083:28: ( (otherlv_0= 'do' ( (lv_module_1_0= RULE_ID ) ) (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )? (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2084:1: (otherlv_0= 'do' ( (lv_module_1_0= RULE_ID ) ) (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )? (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2084:1: (otherlv_0= 'do' ( (lv_module_1_0= RULE_ID ) ) (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )? (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2084:3: otherlv_0= 'do' ( (lv_module_1_0= RULE_ID ) ) (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )? (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_42_in_ruleGlobalDo4384); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalDoAccess().getDoKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2088:1: ( (lv_module_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2089:1: (lv_module_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2089:1: (lv_module_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2090:3: lv_module_1_0= RULE_ID
            {
            lv_module_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4401); 

            			newLeafNode(lv_module_1_0, grammarAccess.getGlobalDoAccess().getModuleIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalDoRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"module",
                    		lv_module_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2106:2: (otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==23) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2106:4: otherlv_2= ':' ( (lv_scope_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalDo4419); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalDoAccess().getColonKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2110:1: ( (lv_scope_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2111:1: (lv_scope_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2111:1: (lv_scope_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2112:3: lv_scope_3_0= RULE_ID
                    {
                    lv_scope_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4436); 

                    			newLeafNode(lv_scope_3_0, grammarAccess.getGlobalDoAccess().getScopeIDTerminalRuleCall_2_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalDoRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_3_0, 
                            		"ID");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2128:4: (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==18) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2128:6: otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>'
                    {
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_ruleGlobalDo4456); 

                        	newLeafNode(otherlv_4, grammarAccess.getGlobalDoAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2132:1: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2133:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2133:1: (lv_arguments_5_0= ruleArgument )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2134:3: lv_arguments_5_0= ruleArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4477);
                    lv_arguments_5_0=ruleArgument();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_5_0, 
                            		"Argument");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2150:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==21) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2150:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
                    	    {
                    	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4490); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getGlobalDoAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2154:1: ( (lv_arguments_7_0= ruleArgument ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2155:1: (lv_arguments_7_0= ruleArgument )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2155:1: (lv_arguments_7_0= ruleArgument )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2156:3: lv_arguments_7_0= ruleArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4511);
                    	    lv_arguments_7_0=ruleArgument();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"arguments",
                    	            		lv_arguments_7_0, 
                    	            		"Argument");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,19,FOLLOW_19_in_ruleGlobalDo4525); 

                        	newLeafNode(otherlv_8, grammarAccess.getGlobalDoAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_9=(Token)match(input,20,FOLLOW_20_in_ruleGlobalDo4539); 

                	newLeafNode(otherlv_9, grammarAccess.getGlobalDoAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2180:1: ( (lv_roles_10_0= ruleRoleInstantiation ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2181:1: (lv_roles_10_0= ruleRoleInstantiation )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2181:1: (lv_roles_10_0= ruleRoleInstantiation )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2182:3: lv_roles_10_0= ruleRoleInstantiation
            {
             
            	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4560);
            lv_roles_10_0=ruleRoleInstantiation();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_10_0, 
                    		"RoleInstantiation");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2198:2: (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==21) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2198:4: otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4573); 

            	        	newLeafNode(otherlv_11, grammarAccess.getGlobalDoAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2202:1: ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2203:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2203:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2204:3: lv_roles_12_0= ruleRoleInstantiation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4594);
            	    lv_roles_12_0=ruleRoleInstantiation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getGlobalDoRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_12_0, 
            	            		"RoleInstantiation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleGlobalDo4608); 

                	newLeafNode(otherlv_13, grammarAccess.getGlobalDoAccess().getRightParenthesisKeyword_7());
                
            otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleGlobalDo4620); 

                	newLeafNode(otherlv_14, grammarAccess.getGlobalDoAccess().getSemicolonKeyword_8());
                

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
    // $ANTLR end "ruleGlobalDo"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_ruleModule132 = new BitSet(new long[]{0x0000000001034002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_ruleModule152 = new BitSet(new long[]{0x0000000001034002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_ruleModule174 = new BitSet(new long[]{0x0000000001020002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_ruleModule196 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl233 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleDecl243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleModuleDecl280 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleModuleDecl301 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleModuleDecl313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_entryRuleModuleName350 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleName361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName401 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_ruleModuleName420 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName435 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_entryRuleImportDecl482 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportDecl492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_ruleImportDecl539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_ruleImportDecl566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_entryRuleImportModule601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportModule611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleImportModule648 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportModule669 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportModule682 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportModule699 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportModule718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_entryRuleImportMember754 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportMember764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleImportMember801 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportMember822 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleImportMember834 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember851 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportMember869 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember886 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportMember905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl941 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadTypeDecl951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rulePayloadTypeDecl988 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_rulePayloadTypeDecl1000 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl1017 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_rulePayloadTypeDecl1034 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1051 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rulePayloadTypeDecl1068 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1085 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rulePayloadTypeDecl1102 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl1119 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_rulePayloadTypeDecl1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1174 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessageSignature1184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessageSignature1226 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleMessageSignature1243 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1265 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleMessageSignature1278 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1299 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleMessageSignature1315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_entryRulePayloadElement1351 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadElement1361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1404 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_rulePayloadElement1421 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleGlobalProtocolDecl1528 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleGlobalProtocolDecl1540 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1557 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1575 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1596 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1609 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1630 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1644 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1658 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1679 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1692 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1713 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1727 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleGlobalProtocolDecl1768 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1785 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1803 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1824 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1837 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1858 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1872 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1886 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1907 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1920 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1941 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1955 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalProtocolDecl1967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl2005 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleDecl2015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleRoleDecl2052 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2069 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleDecl2087 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2147 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameterDecl2157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleParameterDecl2195 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2212 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2230 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleParameterDecl2274 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2291 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2309 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2370 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleInstantiation2380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2422 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleInstantiation2440 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_entryRuleArgument2500 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArgument2510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleArgument2557 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2570 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2619 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2637 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2698 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleGlobalProtocolBlock2754 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2775 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalProtocolBlock2788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2824 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInteraction2834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction2989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction3016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction3043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3078 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3134 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleGlobalMessageTransfer3146 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3163 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleGlobalMessageTransfer3180 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3197 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalMessageTransfer3215 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3232 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalMessageTransfer3251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage3287 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage3297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleMessage3344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessage3366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3407 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalChoice3417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleGlobalChoice3454 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleGlobalChoice3466 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalChoice3483 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3509 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleGlobalChoice3522 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3543 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3581 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalRecursion3591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleGlobalRecursion3628 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalRecursion3645 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3707 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalContinue3717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleGlobalContinue3754 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalContinue3771 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalContinue3788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3824 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalParallel3834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleGlobalParallel3871 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3892 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_ruleGlobalParallel3905 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3926 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3964 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterruptible3974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleGlobalInterruptible4011 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterruptible4029 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalInterruptible4046 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4069 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleGlobalInterruptible4081 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleGlobalInterruptible4093 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4114 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalInterruptible4127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4163 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterrupt4173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4219 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalInterrupt4232 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4253 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_41_in_ruleGlobalInterrupt4267 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterrupt4284 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalInterrupt4301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4337 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalDo4347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleGlobalDo4384 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4401 = new BitSet(new long[]{0x0000000000940000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalDo4419 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4436 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalDo4456 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4477 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4490 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4511 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalDo4525 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalDo4539 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4560 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4573 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4594 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalDo4608 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalDo4620 = new BitSet(new long[]{0x0000000000000002L});

}