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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'module'", "';'", "'.'", "'import'", "'as'", "'from'", "'type'", "'<'", "'>'", "'('", "','", "')'", "':'", "'global'", "'protocol'", "'instantiates'", "'role'", "'sig'", "'{'", "'}'", "'to'", "'choice'", "'at'", "'or'", "'rec'", "'continue'", "'par'", "'and'", "'interruptible'", "'with'", "'by'", "'do'", "'local'", "'throw'", "'catches'"
    };
    public static final int RULE_ID=4;
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
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=6;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_SL_COMMENT=8;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_STRING=5;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:76:1: ruleModule returns [EObject current=null] : (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )* ) ;
    public final EObject ruleModule() throws RecognitionException {
        EObject current = null;

        EObject this_ModuleDecl_0 = null;

        EObject lv_imports_1_0 = null;

        EObject lv_types_2_0 = null;

        EObject lv_globals_3_0 = null;

        EObject lv_locals_4_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:79:28: ( (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:80:1: (this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:81:5: this_ModuleDecl_0= ruleModuleDecl ( (lv_imports_1_0= ruleImportDecl ) )* ( (lv_types_2_0= rulePayloadTypeDecl ) )* ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )*
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:3: ( ( (lv_globals_3_0= ruleGlobalProtocolDecl ) ) | ( (lv_locals_4_0= ruleLocalProtocolDecl ) ) )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }
                else if ( (LA3_0==43) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:4: ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:125:4: ( (lv_globals_3_0= ruleGlobalProtocolDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:126:1: (lv_globals_3_0= ruleGlobalProtocolDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:126:1: (lv_globals_3_0= ruleGlobalProtocolDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:127:3: lv_globals_3_0= ruleGlobalProtocolDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getGlobalsGlobalProtocolDeclParserRuleCall_3_0_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_ruleModule197);
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


            	    }
            	    break;
            	case 2 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:144:6: ( (lv_locals_4_0= ruleLocalProtocolDecl ) )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:144:6: ( (lv_locals_4_0= ruleLocalProtocolDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:145:1: (lv_locals_4_0= ruleLocalProtocolDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:145:1: (lv_locals_4_0= ruleLocalProtocolDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:146:3: lv_locals_4_0= ruleLocalProtocolDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getModuleAccess().getLocalsLocalProtocolDeclParserRuleCall_3_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolDecl_in_ruleModule224);
            	    lv_locals_4_0=ruleLocalProtocolDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getModuleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"locals",
            	            		lv_locals_4_0, 
            	            		"LocalProtocolDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:170:1: entryRuleModuleDecl returns [EObject current=null] : iv_ruleModuleDecl= ruleModuleDecl EOF ;
    public final EObject entryRuleModuleDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:171:2: (iv_ruleModuleDecl= ruleModuleDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:172:2: iv_ruleModuleDecl= ruleModuleDecl EOF
            {
             newCompositeNode(grammarAccess.getModuleDeclRule()); 
            pushFollow(FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl262);
            iv_ruleModuleDecl=ruleModuleDecl();

            state._fsp--;

             current =iv_ruleModuleDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleDecl272); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:179:1: ruleModuleDecl returns [EObject current=null] : (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ) ;
    public final EObject ruleModuleDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:182:28: ( (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:183:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:183:1: (otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:183:3: otherlv_0= 'module' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleModuleDecl309); 

                	newLeafNode(otherlv_0, grammarAccess.getModuleDeclAccess().getModuleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:187:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:188:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:188:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:189:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getModuleDeclAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleModuleDecl330);
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

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleModuleDecl342); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:217:1: entryRuleModuleName returns [String current=null] : iv_ruleModuleName= ruleModuleName EOF ;
    public final String entryRuleModuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleModuleName = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:218:2: (iv_ruleModuleName= ruleModuleName EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:219:2: iv_ruleModuleName= ruleModuleName EOF
            {
             newCompositeNode(grammarAccess.getModuleNameRule()); 
            pushFollow(FOLLOW_ruleModuleName_in_entryRuleModuleName379);
            iv_ruleModuleName=ruleModuleName();

            state._fsp--;

             current =iv_ruleModuleName.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleName390); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:226:1: ruleModuleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleModuleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:229:28: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:230:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:230:1: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:230:6: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName430); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_0()); 
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:237:1: (kw= '.' this_ID_2= RULE_ID )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==13) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:238:2: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,13,FOLLOW_13_in_ruleModuleName449); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getModuleNameAccess().getFullStopKeyword_1_0()); 
            	        
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleModuleName464); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:258:1: entryRuleImportDecl returns [EObject current=null] : iv_ruleImportDecl= ruleImportDecl EOF ;
    public final EObject entryRuleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:259:2: (iv_ruleImportDecl= ruleImportDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:260:2: iv_ruleImportDecl= ruleImportDecl EOF
            {
             newCompositeNode(grammarAccess.getImportDeclRule()); 
            pushFollow(FOLLOW_ruleImportDecl_in_entryRuleImportDecl511);
            iv_ruleImportDecl=ruleImportDecl();

            state._fsp--;

             current =iv_ruleImportDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportDecl521); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:267:1: ruleImportDecl returns [EObject current=null] : (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) ;
    public final EObject ruleImportDecl() throws RecognitionException {
        EObject current = null;

        EObject this_ImportModule_0 = null;

        EObject this_ImportMember_1 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:270:28: ( (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:271:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:271:1: (this_ImportModule_0= ruleImportModule | this_ImportMember_1= ruleImportMember )
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:272:5: this_ImportModule_0= ruleImportModule
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportModuleParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleImportModule_in_ruleImportDecl568);
                    this_ImportModule_0=ruleImportModule();

                    state._fsp--;

                     
                            current = this_ImportModule_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:282:5: this_ImportMember_1= ruleImportMember
                    {
                     
                            newCompositeNode(grammarAccess.getImportDeclAccess().getImportMemberParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleImportMember_in_ruleImportDecl595);
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:298:1: entryRuleImportModule returns [EObject current=null] : iv_ruleImportModule= ruleImportModule EOF ;
    public final EObject entryRuleImportModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportModule = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:299:2: (iv_ruleImportModule= ruleImportModule EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:300:2: iv_ruleImportModule= ruleImportModule EOF
            {
             newCompositeNode(grammarAccess.getImportModuleRule()); 
            pushFollow(FOLLOW_ruleImportModule_in_entryRuleImportModule630);
            iv_ruleImportModule=ruleImportModule();

            state._fsp--;

             current =iv_ruleImportModule; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportModule640); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:307:1: ruleImportModule returns [EObject current=null] : (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) ;
    public final EObject ruleImportModule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:310:28: ( (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:311:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:311:1: (otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:311:3: otherlv_0= 'import' ( (lv_name_1_0= ruleModuleName ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? otherlv_4= ';'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_14_in_ruleImportModule677); 

                	newLeafNode(otherlv_0, grammarAccess.getImportModuleAccess().getImportKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:315:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:316:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:316:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:317:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportModuleAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportModule698);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:333:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==15) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:333:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleImportModule711); 

                        	newLeafNode(otherlv_2, grammarAccess.getImportModuleAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:337:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:338:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:338:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:339:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportModule728); 

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

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleImportModule747); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:367:1: entryRuleImportMember returns [EObject current=null] : iv_ruleImportMember= ruleImportMember EOF ;
    public final EObject entryRuleImportMember() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImportMember = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:368:2: (iv_ruleImportMember= ruleImportMember EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:369:2: iv_ruleImportMember= ruleImportMember EOF
            {
             newCompositeNode(grammarAccess.getImportMemberRule()); 
            pushFollow(FOLLOW_ruleImportMember_in_entryRuleImportMember783);
            iv_ruleImportMember=ruleImportMember();

            state._fsp--;

             current =iv_ruleImportMember; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportMember793); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:376:1: ruleImportMember returns [EObject current=null] : (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:379:28: ( (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:380:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:380:1: (otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:380:3: otherlv_0= 'from' ( (lv_name_1_0= ruleModuleName ) ) otherlv_2= 'import' ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleImportMember830); 

                	newLeafNode(otherlv_0, grammarAccess.getImportMemberAccess().getFromKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:384:1: ( (lv_name_1_0= ruleModuleName ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:385:1: (lv_name_1_0= ruleModuleName )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:385:1: (lv_name_1_0= ruleModuleName )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:386:3: lv_name_1_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getImportMemberAccess().getNameModuleNameParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleImportMember851);
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

            otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleImportMember863); 

                	newLeafNode(otherlv_2, grammarAccess.getImportMemberAccess().getImportKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:406:1: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:407:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:407:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:408:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember880); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:424:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==15) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:424:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                    {
                    otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleImportMember898); 

                        	newLeafNode(otherlv_4, grammarAccess.getImportMemberAccess().getAsKeyword_4_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:428:1: ( (lv_alias_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:429:1: (lv_alias_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:429:1: (lv_alias_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:430:3: lv_alias_5_0= RULE_ID
                    {
                    lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleImportMember915); 

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

            otherlv_6=(Token)match(input,12,FOLLOW_12_in_ruleImportMember934); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:458:1: entryRulePayloadTypeDecl returns [EObject current=null] : iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF ;
    public final EObject entryRulePayloadTypeDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadTypeDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:459:2: (iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:460:2: iv_rulePayloadTypeDecl= rulePayloadTypeDecl EOF
            {
             newCompositeNode(grammarAccess.getPayloadTypeDeclRule()); 
            pushFollow(FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl970);
            iv_rulePayloadTypeDecl=rulePayloadTypeDecl();

            state._fsp--;

             current =iv_rulePayloadTypeDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadTypeDecl980); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:467:1: rulePayloadTypeDecl returns [EObject current=null] : (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:470:28: ( (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:471:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:471:1: (otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:471:3: otherlv_0= 'type' otherlv_1= '<' ( (lv_schema_2_0= RULE_ID ) ) otherlv_3= '>' ( (lv_type_4_0= RULE_STRING ) ) otherlv_5= 'from' ( (lv_location_6_0= RULE_STRING ) ) otherlv_7= 'as' ( (lv_alias_8_0= RULE_ID ) ) otherlv_9= ';'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_17_in_rulePayloadTypeDecl1017); 

                	newLeafNode(otherlv_0, grammarAccess.getPayloadTypeDeclAccess().getTypeKeyword_0());
                
            otherlv_1=(Token)match(input,18,FOLLOW_18_in_rulePayloadTypeDecl1029); 

                	newLeafNode(otherlv_1, grammarAccess.getPayloadTypeDeclAccess().getLessThanSignKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:479:1: ( (lv_schema_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:480:1: (lv_schema_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:480:1: (lv_schema_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:481:3: lv_schema_2_0= RULE_ID
            {
            lv_schema_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl1046); 

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

            otherlv_3=(Token)match(input,19,FOLLOW_19_in_rulePayloadTypeDecl1063); 

                	newLeafNode(otherlv_3, grammarAccess.getPayloadTypeDeclAccess().getGreaterThanSignKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:501:1: ( (lv_type_4_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:502:1: (lv_type_4_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:502:1: (lv_type_4_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:503:3: lv_type_4_0= RULE_STRING
            {
            lv_type_4_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1080); 

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

            otherlv_5=(Token)match(input,16,FOLLOW_16_in_rulePayloadTypeDecl1097); 

                	newLeafNode(otherlv_5, grammarAccess.getPayloadTypeDeclAccess().getFromKeyword_5());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:523:1: ( (lv_location_6_0= RULE_STRING ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:524:1: (lv_location_6_0= RULE_STRING )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:524:1: (lv_location_6_0= RULE_STRING )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:525:3: lv_location_6_0= RULE_STRING
            {
            lv_location_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1114); 

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

            otherlv_7=(Token)match(input,15,FOLLOW_15_in_rulePayloadTypeDecl1131); 

                	newLeafNode(otherlv_7, grammarAccess.getPayloadTypeDeclAccess().getAsKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:545:1: ( (lv_alias_8_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:546:1: (lv_alias_8_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:546:1: (lv_alias_8_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:547:3: lv_alias_8_0= RULE_ID
            {
            lv_alias_8_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadTypeDecl1148); 

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

            otherlv_9=(Token)match(input,12,FOLLOW_12_in_rulePayloadTypeDecl1165); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:577:1: entryRuleMessageSignature returns [EObject current=null] : iv_ruleMessageSignature= ruleMessageSignature EOF ;
    public final EObject entryRuleMessageSignature() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessageSignature = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:578:2: (iv_ruleMessageSignature= ruleMessageSignature EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:579:2: iv_ruleMessageSignature= ruleMessageSignature EOF
            {
             newCompositeNode(grammarAccess.getMessageSignatureRule()); 
            pushFollow(FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1203);
            iv_ruleMessageSignature=ruleMessageSignature();

            state._fsp--;

             current =iv_ruleMessageSignature; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessageSignature1213); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:586:1: ruleMessageSignature returns [EObject current=null] : ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:589:28: ( ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:590:1: ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:590:1: ( ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:590:2: ( (lv_operator_0_0= RULE_ID ) ) otherlv_1= '(' ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )? otherlv_5= ')'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:590:2: ( (lv_operator_0_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:591:1: (lv_operator_0_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:591:1: (lv_operator_0_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:592:3: lv_operator_0_0= RULE_ID
            {
            lv_operator_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessageSignature1255); 

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

            otherlv_1=(Token)match(input,20,FOLLOW_20_in_ruleMessageSignature1272); 

                	newLeafNode(otherlv_1, grammarAccess.getMessageSignatureAccess().getLeftParenthesisKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:612:1: ( ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_ID) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:612:2: ( (lv_types_2_0= rulePayloadElement ) ) (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )*
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:612:2: ( (lv_types_2_0= rulePayloadElement ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:613:1: (lv_types_2_0= rulePayloadElement )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:613:1: (lv_types_2_0= rulePayloadElement )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:614:3: lv_types_2_0= rulePayloadElement
                    {
                     
                    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1294);
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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:630:2: (otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==21) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:630:4: otherlv_3= ',' ( (lv_types_4_0= rulePayloadElement ) )
                    	    {
                    	    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleMessageSignature1307); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getMessageSignatureAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:634:1: ( (lv_types_4_0= rulePayloadElement ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:635:1: (lv_types_4_0= rulePayloadElement )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:635:1: (lv_types_4_0= rulePayloadElement )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:636:3: lv_types_4_0= rulePayloadElement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePayloadElement_in_ruleMessageSignature1328);
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

            otherlv_5=(Token)match(input,22,FOLLOW_22_in_ruleMessageSignature1344); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:664:1: entryRulePayloadElement returns [EObject current=null] : iv_rulePayloadElement= rulePayloadElement EOF ;
    public final EObject entryRulePayloadElement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePayloadElement = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:665:2: (iv_rulePayloadElement= rulePayloadElement EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:666:2: iv_rulePayloadElement= rulePayloadElement EOF
            {
             newCompositeNode(grammarAccess.getPayloadElementRule()); 
            pushFollow(FOLLOW_rulePayloadElement_in_entryRulePayloadElement1380);
            iv_rulePayloadElement=rulePayloadElement();

            state._fsp--;

             current =iv_rulePayloadElement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadElement1390); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:673:1: rulePayloadElement returns [EObject current=null] : ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) ;
    public final EObject rulePayloadElement() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_type_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:676:28: ( ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:1: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )? ( (lv_type_2_0= RULE_ID ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' )?
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:677:3: ( (lv_name_0_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:678:1: (lv_name_0_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:678:1: (lv_name_0_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:679:3: lv_name_0_0= RULE_ID
                    {
                    lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1433); 

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

                    otherlv_1=(Token)match(input,23,FOLLOW_23_in_rulePayloadElement1450); 

                        	newLeafNode(otherlv_1, grammarAccess.getPayloadElementAccess().getColonKeyword_0_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:699:3: ( (lv_type_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:700:1: (lv_type_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:700:1: (lv_type_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:701:3: lv_type_2_0= RULE_ID
            {
            lv_type_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulePayloadElement1469); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:725:1: entryRuleGlobalProtocolDecl returns [EObject current=null] : iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF ;
    public final EObject entryRuleGlobalProtocolDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:726:2: (iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:727:2: iv_ruleGlobalProtocolDecl= ruleGlobalProtocolDecl EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1510);
            iv_ruleGlobalProtocolDecl=ruleGlobalProtocolDecl();

            state._fsp--;

             current =iv_ruleGlobalProtocolDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1520); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:734:1: ruleGlobalProtocolDecl returns [EObject current=null] : (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:737:28: ( (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:738:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:738:1: (otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:738:3: otherlv_0= 'global' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )? otherlv_8= '(' ( (lv_roles_9_0= ruleRoleDecl ) ) (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )* otherlv_12= ')' ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
            {
            otherlv_0=(Token)match(input,24,FOLLOW_24_in_ruleGlobalProtocolDecl1557); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalProtocolDeclAccess().getGlobalKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleGlobalProtocolDecl1569); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolDeclAccess().getProtocolKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:746:1: ( (lv_name_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:747:1: (lv_name_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:747:1: (lv_name_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:748:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1586); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:764:2: (otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==18) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:764:4: otherlv_3= '<' ( (lv_parameters_4_0= ruleParameterDecl ) ) (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )* otherlv_7= '>'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1604); 

                        	newLeafNode(otherlv_3, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:768:1: ( (lv_parameters_4_0= ruleParameterDecl ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:769:1: (lv_parameters_4_0= ruleParameterDecl )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:769:1: (lv_parameters_4_0= ruleParameterDecl )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:770:3: lv_parameters_4_0= ruleParameterDecl
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1625);
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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:786:2: (otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==21) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:786:4: otherlv_5= ',' ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    {
                    	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1638); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:790:1: ( (lv_parameters_6_0= ruleParameterDecl ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:791:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:791:1: (lv_parameters_6_0= ruleParameterDecl )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:792:3: lv_parameters_6_0= ruleParameterDecl
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1659);
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

                    otherlv_7=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1673); 

                        	newLeafNode(otherlv_7, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_8=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1687); 

                	newLeafNode(otherlv_8, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:816:1: ( (lv_roles_9_0= ruleRoleDecl ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:817:1: (lv_roles_9_0= ruleRoleDecl )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:817:1: (lv_roles_9_0= ruleRoleDecl )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:818:3: lv_roles_9_0= ruleRoleDecl
            {
             
            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1708);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:834:2: (otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==21) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:834:4: otherlv_10= ',' ( (lv_roles_11_0= ruleRoleDecl ) )
            	    {
            	    otherlv_10=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1721); 

            	        	newLeafNode(otherlv_10, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:838:1: ( (lv_roles_11_0= ruleRoleDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:839:1: (lv_roles_11_0= ruleRoleDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:839:1: (lv_roles_11_0= ruleRoleDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:840:3: lv_roles_11_0= ruleRoleDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1742);
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

            otherlv_12=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1756); 

                	newLeafNode(otherlv_12, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_7());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:1: ( ( (lv_block_13_0= ruleGlobalProtocolBlock ) ) | (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' ) )
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:860:2: ( (lv_block_13_0= ruleGlobalProtocolBlock ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:861:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:861:1: (lv_block_13_0= ruleGlobalProtocolBlock )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:862:3: lv_block_13_0= ruleGlobalProtocolBlock
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getBlockGlobalProtocolBlockParserRuleCall_8_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1778);
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:879:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:879:6: (otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';' )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:879:8: otherlv_14= 'instantiates' ( (lv_instantiates_15_0= RULE_ID ) ) (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )? otherlv_21= '(' ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) ) (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )* otherlv_25= ')' otherlv_26= ';'
                    {
                    otherlv_14=(Token)match(input,26,FOLLOW_26_in_ruleGlobalProtocolDecl1797); 

                        	newLeafNode(otherlv_14, grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesKeyword_8_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:883:1: ( (lv_instantiates_15_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:884:1: (lv_instantiates_15_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:884:1: (lv_instantiates_15_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:885:3: lv_instantiates_15_0= RULE_ID
                    {
                    lv_instantiates_15_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1814); 

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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:901:2: (otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==18) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:901:4: otherlv_16= '<' ( (lv_arguments_17_0= ruleArgument ) ) (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )* otherlv_20= '>'
                            {
                            otherlv_16=(Token)match(input,18,FOLLOW_18_in_ruleGlobalProtocolDecl1832); 

                                	newLeafNode(otherlv_16, grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_8_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:905:1: ( (lv_arguments_17_0= ruleArgument ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:906:1: (lv_arguments_17_0= ruleArgument )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:906:1: (lv_arguments_17_0= ruleArgument )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:907:3: lv_arguments_17_0= ruleArgument
                            {
                             
                            	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1853);
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

                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:923:2: (otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) ) )*
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( (LA14_0==21) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:923:4: otherlv_18= ',' ( (lv_arguments_19_0= ruleArgument ) )
                            	    {
                            	    otherlv_18=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1866); 

                            	        	newLeafNode(otherlv_18, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_2_2_0());
                            	        
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:927:1: ( (lv_arguments_19_0= ruleArgument ) )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:928:1: (lv_arguments_19_0= ruleArgument )
                            	    {
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:928:1: (lv_arguments_19_0= ruleArgument )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:929:3: lv_arguments_19_0= ruleArgument
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1887);
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

                            otherlv_20=(Token)match(input,19,FOLLOW_19_in_ruleGlobalProtocolDecl1901); 

                                	newLeafNode(otherlv_20, grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_8_1_2_3());
                                

                            }
                            break;

                    }

                    otherlv_21=(Token)match(input,20,FOLLOW_20_in_ruleGlobalProtocolDecl1915); 

                        	newLeafNode(otherlv_21, grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_8_1_3());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:953:1: ( (lv_roleInstantiations_22_0= ruleRoleInstantiation ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:954:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:954:1: (lv_roleInstantiations_22_0= ruleRoleInstantiation )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:955:3: lv_roleInstantiations_22_0= ruleRoleInstantiation
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1936);
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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:971:2: (otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==21) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:971:4: otherlv_23= ',' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    {
                    	    otherlv_23=(Token)match(input,21,FOLLOW_21_in_ruleGlobalProtocolDecl1949); 

                    	        	newLeafNode(otherlv_23, grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_5_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:975:1: ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:976:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:976:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:977:3: lv_roleInstantiations_24_0= ruleRoleInstantiation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1970);
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

                    otherlv_25=(Token)match(input,22,FOLLOW_22_in_ruleGlobalProtocolDecl1984); 

                        	newLeafNode(otherlv_25, grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_8_1_6());
                        
                    otherlv_26=(Token)match(input,12,FOLLOW_12_in_ruleGlobalProtocolDecl1996); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1009:1: entryRuleRoleDecl returns [EObject current=null] : iv_ruleRoleDecl= ruleRoleDecl EOF ;
    public final EObject entryRuleRoleDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1010:2: (iv_ruleRoleDecl= ruleRoleDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1011:2: iv_ruleRoleDecl= ruleRoleDecl EOF
            {
             newCompositeNode(grammarAccess.getRoleDeclRule()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl2034);
            iv_ruleRoleDecl=ruleRoleDecl();

            state._fsp--;

             current =iv_ruleRoleDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleDecl2044); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1018:1: ruleRoleDecl returns [EObject current=null] : (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_alias_3_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1021:28: ( (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1022:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1022:1: (otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1022:3: otherlv_0= 'role' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,27,FOLLOW_27_in_ruleRoleDecl2081); 

                	newLeafNode(otherlv_0, grammarAccess.getRoleDeclAccess().getRoleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1026:1: ( (lv_name_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1027:1: (lv_name_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1027:1: (lv_name_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1028:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2098); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1044:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==15) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1044:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleRoleDecl2116); 

                        	newLeafNode(otherlv_2, grammarAccess.getRoleDeclAccess().getAsKeyword_2_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1048:1: ( (lv_alias_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1049:1: (lv_alias_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1049:1: (lv_alias_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1050:3: lv_alias_3_0= RULE_ID
                    {
                    lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleDecl2133); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1074:1: entryRuleParameterDecl returns [EObject current=null] : iv_ruleParameterDecl= ruleParameterDecl EOF ;
    public final EObject entryRuleParameterDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameterDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1075:2: (iv_ruleParameterDecl= ruleParameterDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1076:2: iv_ruleParameterDecl= ruleParameterDecl EOF
            {
             newCompositeNode(grammarAccess.getParameterDeclRule()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2176);
            iv_ruleParameterDecl=ruleParameterDecl();

            state._fsp--;

             current =iv_ruleParameterDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameterDecl2186); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1083:1: ruleParameterDecl returns [EObject current=null] : ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1086:28: ( ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:1: ( (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? ) | (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? ) )
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:2: (otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1087:4: otherlv_0= 'type' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    {
                    otherlv_0=(Token)match(input,17,FOLLOW_17_in_ruleParameterDecl2224); 

                        	newLeafNode(otherlv_0, grammarAccess.getParameterDeclAccess().getTypeKeyword_0_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1091:1: ( (lv_name_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1092:1: (lv_name_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1092:1: (lv_name_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1093:3: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2241); 

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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1109:2: (otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) ) )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==15) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1109:4: otherlv_2= 'as' ( (lv_alias_3_0= RULE_ID ) )
                            {
                            otherlv_2=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2259); 

                                	newLeafNode(otherlv_2, grammarAccess.getParameterDeclAccess().getAsKeyword_0_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1113:1: ( (lv_alias_3_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1114:1: (lv_alias_3_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1114:1: (lv_alias_3_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1115:3: lv_alias_3_0= RULE_ID
                            {
                            lv_alias_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2276); 

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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1132:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1132:6: (otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1132:8: otherlv_4= 'sig' ( (lv_name_5_0= RULE_ID ) ) (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    {
                    otherlv_4=(Token)match(input,28,FOLLOW_28_in_ruleParameterDecl2303); 

                        	newLeafNode(otherlv_4, grammarAccess.getParameterDeclAccess().getSigKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1136:1: ( (lv_name_5_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1137:1: (lv_name_5_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1137:1: (lv_name_5_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1138:3: lv_name_5_0= RULE_ID
                    {
                    lv_name_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2320); 

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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1154:2: (otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==15) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1154:4: otherlv_6= 'as' ( (lv_alias_7_0= RULE_ID ) )
                            {
                            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleParameterDecl2338); 

                                	newLeafNode(otherlv_6, grammarAccess.getParameterDeclAccess().getAsKeyword_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1158:1: ( (lv_alias_7_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1159:1: (lv_alias_7_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1159:1: (lv_alias_7_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1160:3: lv_alias_7_0= RULE_ID
                            {
                            lv_alias_7_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleParameterDecl2355); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1184:1: entryRuleRoleInstantiation returns [EObject current=null] : iv_ruleRoleInstantiation= ruleRoleInstantiation EOF ;
    public final EObject entryRuleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoleInstantiation = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1185:2: (iv_ruleRoleInstantiation= ruleRoleInstantiation EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1186:2: iv_ruleRoleInstantiation= ruleRoleInstantiation EOF
            {
             newCompositeNode(grammarAccess.getRoleInstantiationRule()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2399);
            iv_ruleRoleInstantiation=ruleRoleInstantiation();

            state._fsp--;

             current =iv_ruleRoleInstantiation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleInstantiation2409); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1193:1: ruleRoleInstantiation returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) ;
    public final EObject ruleRoleInstantiation() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_alias_2_0=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1196:28: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1197:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1197:1: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1197:2: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1197:2: ( (lv_name_0_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1198:1: (lv_name_0_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1198:1: (lv_name_0_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1199:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2451); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1215:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==15) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1215:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                    {
                    otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleRoleInstantiation2469); 

                        	newLeafNode(otherlv_1, grammarAccess.getRoleInstantiationAccess().getAsKeyword_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1219:1: ( (lv_alias_2_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1220:1: (lv_alias_2_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1220:1: (lv_alias_2_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1221:3: lv_alias_2_0= RULE_ID
                    {
                    lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleRoleInstantiation2486); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1245:1: entryRuleArgument returns [EObject current=null] : iv_ruleArgument= ruleArgument EOF ;
    public final EObject entryRuleArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgument = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1246:2: (iv_ruleArgument= ruleArgument EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1247:2: iv_ruleArgument= ruleArgument EOF
            {
             newCompositeNode(grammarAccess.getArgumentRule()); 
            pushFollow(FOLLOW_ruleArgument_in_entryRuleArgument2529);
            iv_ruleArgument=ruleArgument();

            state._fsp--;

             current =iv_ruleArgument; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleArgument2539); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1254:1: ruleArgument returns [EObject current=null] : ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1257:28: ( ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:1: ( ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? ) | ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_ID) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==20) ) {
                    alt25=1;
                }
                else if ( (LA25_1==EOF||LA25_1==15||LA25_1==19||LA25_1==21) ) {
                    alt25=2;
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:2: ( ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:3: ( (lv_signature_0_0= ruleMessageSignature ) ) (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1258:3: ( (lv_signature_0_0= ruleMessageSignature ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1259:1: (lv_signature_0_0= ruleMessageSignature )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1259:1: (lv_signature_0_0= ruleMessageSignature )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1260:3: lv_signature_0_0= ruleMessageSignature
                    {
                     
                    	        newCompositeNode(grammarAccess.getArgumentAccess().getSignatureMessageSignatureParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleArgument2586);
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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1276:2: (otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==15) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1276:4: otherlv_1= 'as' ( (lv_alias_2_0= RULE_ID ) )
                            {
                            otherlv_1=(Token)match(input,15,FOLLOW_15_in_ruleArgument2599); 

                                	newLeafNode(otherlv_1, grammarAccess.getArgumentAccess().getAsKeyword_0_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1280:1: ( (lv_alias_2_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1281:1: (lv_alias_2_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1281:1: (lv_alias_2_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1282:3: lv_alias_2_0= RULE_ID
                            {
                            lv_alias_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2616); 

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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1299:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1299:6: ( ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )? )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1299:7: ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1299:7: ( (lv_name_3_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1300:1: (lv_name_3_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1300:1: (lv_name_3_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1301:3: lv_name_3_0= RULE_ID
                    {
                    lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2648); 

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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1317:2: (otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) ) )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==15) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1317:4: otherlv_4= 'as' ( (lv_alias_5_0= RULE_ID ) )
                            {
                            otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleArgument2666); 

                                	newLeafNode(otherlv_4, grammarAccess.getArgumentAccess().getAsKeyword_1_1_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1321:1: ( (lv_alias_5_0= RULE_ID ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1322:1: (lv_alias_5_0= RULE_ID )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1322:1: (lv_alias_5_0= RULE_ID )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1323:3: lv_alias_5_0= RULE_ID
                            {
                            lv_alias_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2683); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1347:1: entryRuleGlobalProtocolBlock returns [EObject current=null] : iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF ;
    public final EObject entryRuleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalProtocolBlock = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1348:2: (iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1349:2: iv_ruleGlobalProtocolBlock= ruleGlobalProtocolBlock EOF
            {
             newCompositeNode(grammarAccess.getGlobalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2727);
            iv_ruleGlobalProtocolBlock=ruleGlobalProtocolBlock();

            state._fsp--;

             current =iv_ruleGlobalProtocolBlock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2737); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1356:1: ruleGlobalProtocolBlock returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) ;
    public final EObject ruleGlobalProtocolBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_activities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1359:28: ( ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1360:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1360:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1360:2: () otherlv_1= '{' ( (lv_activities_2_0= ruleGlobalInteraction ) )* otherlv_3= '}'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1360:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1361:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getGlobalProtocolBlockAccess().getGlobalProtocolBlockAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,29,FOLLOW_29_in_ruleGlobalProtocolBlock2783); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalProtocolBlockAccess().getLeftCurlyBracketKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1370:1: ( (lv_activities_2_0= ruleGlobalInteraction ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_ID||LA26_0==32||(LA26_0>=35 && LA26_0<=37)||LA26_0==39||LA26_0==42) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1371:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1371:1: (lv_activities_2_0= ruleGlobalInteraction )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1372:3: lv_activities_2_0= ruleGlobalInteraction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesGlobalInteractionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2804);
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

            otherlv_3=(Token)match(input,30,FOLLOW_30_in_ruleGlobalProtocolBlock2817); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1400:1: entryRuleGlobalInteraction returns [EObject current=null] : iv_ruleGlobalInteraction= ruleGlobalInteraction EOF ;
    public final EObject entryRuleGlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInteraction = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1401:2: (iv_ruleGlobalInteraction= ruleGlobalInteraction EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1402:2: iv_ruleGlobalInteraction= ruleGlobalInteraction EOF
            {
             newCompositeNode(grammarAccess.getGlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2853);
            iv_ruleGlobalInteraction=ruleGlobalInteraction();

            state._fsp--;

             current =iv_ruleGlobalInteraction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInteraction2863); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1409:1: ruleGlobalInteraction returns [EObject current=null] : (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1412:28: ( (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1413:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1413:1: (this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer | this_GlobalChoice_1= ruleGlobalChoice | this_GlobalRecursion_2= ruleGlobalRecursion | this_GlobalContinue_3= ruleGlobalContinue | this_GlobalParallel_4= ruleGlobalParallel | this_GlobalInterruptible_5= ruleGlobalInterruptible | this_GlobalDo_6= ruleGlobalDo )
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1414:5: this_GlobalMessageTransfer_0= ruleGlobalMessageTransfer
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalMessageTransferParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2910);
                    this_GlobalMessageTransfer_0=ruleGlobalMessageTransfer();

                    state._fsp--;

                     
                            current = this_GlobalMessageTransfer_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1424:5: this_GlobalChoice_1= ruleGlobalChoice
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalChoiceParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2937);
                    this_GlobalChoice_1=ruleGlobalChoice();

                    state._fsp--;

                     
                            current = this_GlobalChoice_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1434:5: this_GlobalRecursion_2= ruleGlobalRecursion
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalRecursionParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2964);
                    this_GlobalRecursion_2=ruleGlobalRecursion();

                    state._fsp--;

                     
                            current = this_GlobalRecursion_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1444:5: this_GlobalContinue_3= ruleGlobalContinue
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalContinueParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2991);
                    this_GlobalContinue_3=ruleGlobalContinue();

                    state._fsp--;

                     
                            current = this_GlobalContinue_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1454:5: this_GlobalParallel_4= ruleGlobalParallel
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalParallelParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction3018);
                    this_GlobalParallel_4=ruleGlobalParallel();

                    state._fsp--;

                     
                            current = this_GlobalParallel_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1464:5: this_GlobalInterruptible_5= ruleGlobalInterruptible
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalInterruptibleParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction3045);
                    this_GlobalInterruptible_5=ruleGlobalInterruptible();

                    state._fsp--;

                     
                            current = this_GlobalInterruptible_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1474:5: this_GlobalDo_6= ruleGlobalDo
                    {
                     
                            newCompositeNode(grammarAccess.getGlobalInteractionAccess().getGlobalDoParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction3072);
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1490:1: entryRuleGlobalMessageTransfer returns [EObject current=null] : iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF ;
    public final EObject entryRuleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalMessageTransfer = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1491:2: (iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1492:2: iv_ruleGlobalMessageTransfer= ruleGlobalMessageTransfer EOF
            {
             newCompositeNode(grammarAccess.getGlobalMessageTransferRule()); 
            pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3107);
            iv_ruleGlobalMessageTransfer=ruleGlobalMessageTransfer();

            state._fsp--;

             current =iv_ruleGlobalMessageTransfer; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3117); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1499:1: ruleGlobalMessageTransfer returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' ) ;
    public final EObject ruleGlobalMessageTransfer() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_fromRole_2_0=null;
        Token otherlv_3=null;
        Token lv_toRoles_4_0=null;
        Token otherlv_5=null;
        Token lv_toRoles_6_0=null;
        Token otherlv_7=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1502:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1503:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1503:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1503:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= 'to' ( (lv_toRoles_4_0= RULE_ID ) ) (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )* otherlv_7= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1503:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1504:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1504:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1505:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalMessageTransferAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3163);
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

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleGlobalMessageTransfer3175); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalMessageTransferAccess().getFromKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1525:1: ( (lv_fromRole_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1526:1: (lv_fromRole_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1526:1: (lv_fromRole_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1527:3: lv_fromRole_2_0= RULE_ID
            {
            lv_fromRole_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3192); 

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

            otherlv_3=(Token)match(input,31,FOLLOW_31_in_ruleGlobalMessageTransfer3209); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalMessageTransferAccess().getToKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1547:1: ( (lv_toRoles_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1548:1: (lv_toRoles_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1548:1: (lv_toRoles_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1549:3: lv_toRoles_4_0= RULE_ID
            {
            lv_toRoles_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3226); 

            			newLeafNode(lv_toRoles_4_0, grammarAccess.getGlobalMessageTransferAccess().getToRolesIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_4_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1565:2: (otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==21) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1565:4: otherlv_5= ',' ( (lv_toRoles_6_0= RULE_ID ) )
            	    {
            	    otherlv_5=(Token)match(input,21,FOLLOW_21_in_ruleGlobalMessageTransfer3244); 

            	        	newLeafNode(otherlv_5, grammarAccess.getGlobalMessageTransferAccess().getCommaKeyword_5_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1569:1: ( (lv_toRoles_6_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1570:1: (lv_toRoles_6_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1570:1: (lv_toRoles_6_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1571:3: lv_toRoles_6_0= RULE_ID
            	    {
            	    lv_toRoles_6_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3261); 

            	    			newLeafNode(lv_toRoles_6_0, grammarAccess.getGlobalMessageTransferAccess().getToRolesIDTerminalRuleCall_5_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getGlobalMessageTransferRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRoles",
            	            		lv_toRoles_6_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            otherlv_7=(Token)match(input,12,FOLLOW_12_in_ruleGlobalMessageTransfer3280); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1599:1: entryRuleMessage returns [EObject current=null] : iv_ruleMessage= ruleMessage EOF ;
    public final EObject entryRuleMessage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMessage = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1600:2: (iv_ruleMessage= ruleMessage EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1601:2: iv_ruleMessage= ruleMessage EOF
            {
             newCompositeNode(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage3316);
            iv_ruleMessage=ruleMessage();

            state._fsp--;

             current =iv_ruleMessage; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage3326); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1608:1: ruleMessage returns [EObject current=null] : (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) ) ;
    public final EObject ruleMessage() throws RecognitionException {
        EObject current = null;

        Token lv_parameter_1_0=null;
        EObject this_MessageSignature_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1611:28: ( (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1612:1: (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1612:1: (this_MessageSignature_0= ruleMessageSignature | ( (lv_parameter_1_0= RULE_ID ) ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RULE_ID) ) {
                int LA29_1 = input.LA(2);

                if ( (LA29_1==EOF||LA29_1==16||LA29_1==21||LA29_1==31||LA29_1==41) ) {
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
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1613:5: this_MessageSignature_0= ruleMessageSignature
                    {
                     
                            newCompositeNode(grammarAccess.getMessageAccess().getMessageSignatureParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleMessageSignature_in_ruleMessage3373);
                    this_MessageSignature_0=ruleMessageSignature();

                    state._fsp--;

                     
                            current = this_MessageSignature_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1622:6: ( (lv_parameter_1_0= RULE_ID ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1622:6: ( (lv_parameter_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1623:1: (lv_parameter_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1623:1: (lv_parameter_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1624:3: lv_parameter_1_0= RULE_ID
                    {
                    lv_parameter_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMessage3395); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1648:1: entryRuleGlobalChoice returns [EObject current=null] : iv_ruleGlobalChoice= ruleGlobalChoice EOF ;
    public final EObject entryRuleGlobalChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalChoice = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1649:2: (iv_ruleGlobalChoice= ruleGlobalChoice EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1650:2: iv_ruleGlobalChoice= ruleGlobalChoice EOF
            {
             newCompositeNode(grammarAccess.getGlobalChoiceRule()); 
            pushFollow(FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3436);
            iv_ruleGlobalChoice=ruleGlobalChoice();

            state._fsp--;

             current =iv_ruleGlobalChoice; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalChoice3446); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1657:1: ruleGlobalChoice returns [EObject current=null] : (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1660:28: ( (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1661:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1661:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1661:3: otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleGlobalChoice3483); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalChoiceAccess().getChoiceKeyword_0());
                
            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleGlobalChoice3495); 

                	newLeafNode(otherlv_1, grammarAccess.getGlobalChoiceAccess().getAtKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1669:1: ( (lv_role_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1670:1: (lv_role_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1670:1: (lv_role_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1671:3: lv_role_2_0= RULE_ID
            {
            lv_role_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalChoice3512); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1687:2: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1688:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1688:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1689:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3538);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1705:2: (otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==34) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1705:4: otherlv_4= 'or' ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleGlobalChoice3551); 

            	        	newLeafNode(otherlv_4, grammarAccess.getGlobalChoiceAccess().getOrKeyword_4_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1709:1: ( (lv_blocks_5_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1710:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1710:1: (lv_blocks_5_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1711:3: lv_blocks_5_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3572);
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1735:1: entryRuleGlobalRecursion returns [EObject current=null] : iv_ruleGlobalRecursion= ruleGlobalRecursion EOF ;
    public final EObject entryRuleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalRecursion = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1736:2: (iv_ruleGlobalRecursion= ruleGlobalRecursion EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1737:2: iv_ruleGlobalRecursion= ruleGlobalRecursion EOF
            {
             newCompositeNode(grammarAccess.getGlobalRecursionRule()); 
            pushFollow(FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3610);
            iv_ruleGlobalRecursion=ruleGlobalRecursion();

            state._fsp--;

             current =iv_ruleGlobalRecursion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalRecursion3620); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1744:1: ruleGlobalRecursion returns [EObject current=null] : (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) ;
    public final EObject ruleGlobalRecursion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        EObject lv_block_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1747:28: ( (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1748:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1748:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1748:3: otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleGlobalRecursion3657); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalRecursionAccess().getRecKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1752:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1753:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1753:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1754:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalRecursion3674); 

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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1770:2: ( (lv_block_2_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1771:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1771:1: (lv_block_2_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1772:3: lv_block_2_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3700);
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1796:1: entryRuleGlobalContinue returns [EObject current=null] : iv_ruleGlobalContinue= ruleGlobalContinue EOF ;
    public final EObject entryRuleGlobalContinue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalContinue = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1797:2: (iv_ruleGlobalContinue= ruleGlobalContinue EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1798:2: iv_ruleGlobalContinue= ruleGlobalContinue EOF
            {
             newCompositeNode(grammarAccess.getGlobalContinueRule()); 
            pushFollow(FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3736);
            iv_ruleGlobalContinue=ruleGlobalContinue();

            state._fsp--;

             current =iv_ruleGlobalContinue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalContinue3746); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1805:1: ruleGlobalContinue returns [EObject current=null] : (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) ;
    public final EObject ruleGlobalContinue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1808:28: ( (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1809:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1809:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1809:3: otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleGlobalContinue3783); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalContinueAccess().getContinueKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1813:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1814:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1814:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1815:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalContinue3800); 

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

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleGlobalContinue3817); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1843:1: entryRuleGlobalParallel returns [EObject current=null] : iv_ruleGlobalParallel= ruleGlobalParallel EOF ;
    public final EObject entryRuleGlobalParallel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalParallel = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1844:2: (iv_ruleGlobalParallel= ruleGlobalParallel EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1845:2: iv_ruleGlobalParallel= ruleGlobalParallel EOF
            {
             newCompositeNode(grammarAccess.getGlobalParallelRule()); 
            pushFollow(FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3853);
            iv_ruleGlobalParallel=ruleGlobalParallel();

            state._fsp--;

             current =iv_ruleGlobalParallel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalParallel3863); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1852:1: ruleGlobalParallel returns [EObject current=null] : (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) ;
    public final EObject ruleGlobalParallel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_blocks_1_0 = null;

        EObject lv_blocks_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1855:28: ( (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1856:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1856:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1856:3: otherlv_0= 'par' ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,37,FOLLOW_37_in_ruleGlobalParallel3900); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalParallelAccess().getParKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1860:1: ( (lv_blocks_1_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1861:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1861:1: (lv_blocks_1_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1862:3: lv_blocks_1_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3921);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1878:2: (otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==38) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1878:4: otherlv_2= 'and' ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    {
            	    otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleGlobalParallel3934); 

            	        	newLeafNode(otherlv_2, grammarAccess.getGlobalParallelAccess().getAndKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1882:1: ( (lv_blocks_3_0= ruleGlobalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1883:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1883:1: (lv_blocks_3_0= ruleGlobalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1884:3: lv_blocks_3_0= ruleGlobalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3955);
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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1908:1: entryRuleGlobalInterruptible returns [EObject current=null] : iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF ;
    public final EObject entryRuleGlobalInterruptible() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterruptible = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1909:2: (iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1910:2: iv_ruleGlobalInterruptible= ruleGlobalInterruptible EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptibleRule()); 
            pushFollow(FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3993);
            iv_ruleGlobalInterruptible=ruleGlobalInterruptible();

            state._fsp--;

             current =iv_ruleGlobalInterruptible; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterruptible4003); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1917:1: ruleGlobalInterruptible returns [EObject current=null] : (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1920:28: ( (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1921:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1921:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1921:3: otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleGlobalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_ruleGlobalInterruptible4040); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalInterruptibleAccess().getInterruptibleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1925:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_ID) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1925:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1925:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1926:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1926:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1927:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterruptible4058); 

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

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalInterruptible4075); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalInterruptibleAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1947:3: ( (lv_block_3_0= ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1948:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1948:1: (lv_block_3_0= ruleGlobalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1949:3: lv_block_3_0= ruleGlobalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4098);
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

            otherlv_4=(Token)match(input,40,FOLLOW_40_in_ruleGlobalInterruptible4110); 

                	newLeafNode(otherlv_4, grammarAccess.getGlobalInterruptibleAccess().getWithKeyword_3());
                
            otherlv_5=(Token)match(input,29,FOLLOW_29_in_ruleGlobalInterruptible4122); 

                	newLeafNode(otherlv_5, grammarAccess.getGlobalInterruptibleAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1973:1: ( (lv_interrupts_6_0= ruleGlobalInterrupt ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1974:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1974:1: (lv_interrupts_6_0= ruleGlobalInterrupt )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:1975:3: lv_interrupts_6_0= ruleGlobalInterrupt
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptibleAccess().getInterruptsGlobalInterruptParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4143);
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

            otherlv_7=(Token)match(input,30,FOLLOW_30_in_ruleGlobalInterruptible4156); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2003:1: entryRuleGlobalInterrupt returns [EObject current=null] : iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF ;
    public final EObject entryRuleGlobalInterrupt() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalInterrupt = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2004:2: (iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2005:2: iv_ruleGlobalInterrupt= ruleGlobalInterrupt EOF
            {
             newCompositeNode(grammarAccess.getGlobalInterruptRule()); 
            pushFollow(FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4192);
            iv_ruleGlobalInterrupt=ruleGlobalInterrupt();

            state._fsp--;

             current =iv_ruleGlobalInterrupt; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterrupt4202); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2012:1: ruleGlobalInterrupt returns [EObject current=null] : ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) ;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2015:28: ( ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2016:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2016:1: ( ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2016:2: ( (lv_messages_0_0= ruleMessage ) ) (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )* otherlv_3= 'by' ( (lv_role_4_0= RULE_ID ) ) otherlv_5= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2016:2: ( (lv_messages_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2017:1: (lv_messages_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2017:1: (lv_messages_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2018:3: lv_messages_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4248);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2034:2: (otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) ) )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==21) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2034:4: otherlv_1= ',' ( (lv_messages_2_0= ruleMessage ) )
            	    {
            	    otherlv_1=(Token)match(input,21,FOLLOW_21_in_ruleGlobalInterrupt4261); 

            	        	newLeafNode(otherlv_1, grammarAccess.getGlobalInterruptAccess().getCommaKeyword_1_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2038:1: ( (lv_messages_2_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2039:1: (lv_messages_2_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2039:1: (lv_messages_2_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2040:3: lv_messages_2_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleGlobalInterrupt4282);
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

            otherlv_3=(Token)match(input,41,FOLLOW_41_in_ruleGlobalInterrupt4296); 

                	newLeafNode(otherlv_3, grammarAccess.getGlobalInterruptAccess().getByKeyword_2());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2060:1: ( (lv_role_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2061:1: (lv_role_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2061:1: (lv_role_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2062:3: lv_role_4_0= RULE_ID
            {
            lv_role_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalInterrupt4313); 

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

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleGlobalInterrupt4330); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2090:1: entryRuleGlobalDo returns [EObject current=null] : iv_ruleGlobalDo= ruleGlobalDo EOF ;
    public final EObject entryRuleGlobalDo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGlobalDo = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2091:2: (iv_ruleGlobalDo= ruleGlobalDo EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2092:2: iv_ruleGlobalDo= ruleGlobalDo EOF
            {
             newCompositeNode(grammarAccess.getGlobalDoRule()); 
            pushFollow(FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4366);
            iv_ruleGlobalDo=ruleGlobalDo();

            state._fsp--;

             current =iv_ruleGlobalDo; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalDo4376); 

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
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2099:1: ruleGlobalDo returns [EObject current=null] : (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) ;
    public final EObject ruleGlobalDo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2102:28: ( (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2103:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2103:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2103:3: otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_42_in_ruleGlobalDo4413); 

                	newLeafNode(otherlv_0, grammarAccess.getGlobalDoAccess().getDoKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2107:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_ID) ) {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==23) ) {
                    alt35=1;
                }
            }
            switch (alt35) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2107:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2107:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2108:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2108:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2109:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4431); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getGlobalDoAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getGlobalDoRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleGlobalDo4448); 

                        	newLeafNode(otherlv_2, grammarAccess.getGlobalDoAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2129:3: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2130:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2130:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2131:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleGlobalDo4467); 

            			newLeafNode(lv_member_3_0, grammarAccess.getGlobalDoAccess().getMemberIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getGlobalDoRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2147:2: (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==18) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2147:4: otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>'
                    {
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_ruleGlobalDo4485); 

                        	newLeafNode(otherlv_4, grammarAccess.getGlobalDoAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2151:1: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2152:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2152:1: (lv_arguments_5_0= ruleArgument )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2153:3: lv_arguments_5_0= ruleArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4506);
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

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2169:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==21) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2169:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
                    	    {
                    	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4519); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getGlobalDoAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2173:1: ( (lv_arguments_7_0= ruleArgument ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2174:1: (lv_arguments_7_0= ruleArgument )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2174:1: (lv_arguments_7_0= ruleArgument )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2175:3: lv_arguments_7_0= ruleArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleArgument_in_ruleGlobalDo4540);
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

                    otherlv_8=(Token)match(input,19,FOLLOW_19_in_ruleGlobalDo4554); 

                        	newLeafNode(otherlv_8, grammarAccess.getGlobalDoAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_9=(Token)match(input,20,FOLLOW_20_in_ruleGlobalDo4568); 

                	newLeafNode(otherlv_9, grammarAccess.getGlobalDoAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2199:1: ( (lv_roles_10_0= ruleRoleInstantiation ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2200:1: (lv_roles_10_0= ruleRoleInstantiation )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2200:1: (lv_roles_10_0= ruleRoleInstantiation )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2201:3: lv_roles_10_0= ruleRoleInstantiation
            {
             
            	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4589);
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

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2217:2: (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==21) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2217:4: otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleGlobalDo4602); 

            	        	newLeafNode(otherlv_11, grammarAccess.getGlobalDoAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2221:1: ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2222:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2222:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2223:3: lv_roles_12_0= ruleRoleInstantiation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4623);
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

            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleGlobalDo4637); 

                	newLeafNode(otherlv_13, grammarAccess.getGlobalDoAccess().getRightParenthesisKeyword_7());
                
            otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleGlobalDo4649); 

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


    // $ANTLR start "entryRuleLocalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2255:1: entryRuleLocalProtocolDecl returns [EObject current=null] : iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF ;
    public final EObject entryRuleLocalProtocolDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalProtocolDecl = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2256:2: (iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2257:2: iv_ruleLocalProtocolDecl= ruleLocalProtocolDecl EOF
            {
             newCompositeNode(grammarAccess.getLocalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleLocalProtocolDecl_in_entryRuleLocalProtocolDecl4685);
            iv_ruleLocalProtocolDecl=ruleLocalProtocolDecl();

            state._fsp--;

             current =iv_ruleLocalProtocolDecl; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalProtocolDecl4695); 

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
    // $ANTLR end "entryRuleLocalProtocolDecl"


    // $ANTLR start "ruleLocalProtocolDecl"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2264:1: ruleLocalProtocolDecl returns [EObject current=null] : (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) ) ;
    public final EObject ruleLocalProtocolDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token lv_role_4_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token lv_instantiates_17_0=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        EObject lv_parameters_6_0 = null;

        EObject lv_parameters_8_0 = null;

        EObject lv_roles_11_0 = null;

        EObject lv_roles_13_0 = null;

        EObject lv_block_15_0 = null;

        EObject lv_arguments_19_0 = null;

        EObject lv_arguments_21_0 = null;

        EObject lv_roleInstantiations_24_0 = null;

        EObject lv_roleInstantiations_26_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2267:28: ( (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2268:1: (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2268:1: (otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2268:3: otherlv_0= 'local' otherlv_1= 'protocol' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'at' ( (lv_role_4_0= RULE_ID ) ) (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )? otherlv_10= '(' ( (lv_roles_11_0= ruleRoleDecl ) ) (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )* otherlv_14= ')' ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) )
            {
            otherlv_0=(Token)match(input,43,FOLLOW_43_in_ruleLocalProtocolDecl4732); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalProtocolDeclAccess().getLocalKeyword_0());
                
            otherlv_1=(Token)match(input,25,FOLLOW_25_in_ruleLocalProtocolDecl4744); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalProtocolDeclAccess().getProtocolKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2276:1: ( (lv_name_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2277:1: (lv_name_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2277:1: (lv_name_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2278:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4761); 

            			newLeafNode(lv_name_2_0, grammarAccess.getLocalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,33,FOLLOW_33_in_ruleLocalProtocolDecl4778); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalProtocolDeclAccess().getAtKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2298:1: ( (lv_role_4_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2299:1: (lv_role_4_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2299:1: (lv_role_4_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2300:3: lv_role_4_0= RULE_ID
            {
            lv_role_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4795); 

            			newLeafNode(lv_role_4_0, grammarAccess.getLocalProtocolDeclAccess().getRoleIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_4_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2316:2: (otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==18) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2316:4: otherlv_5= '<' ( (lv_parameters_6_0= ruleParameterDecl ) ) (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )* otherlv_9= '>'
                    {
                    otherlv_5=(Token)match(input,18,FOLLOW_18_in_ruleLocalProtocolDecl4813); 

                        	newLeafNode(otherlv_5, grammarAccess.getLocalProtocolDeclAccess().getLessThanSignKeyword_5_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2320:1: ( (lv_parameters_6_0= ruleParameterDecl ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2321:1: (lv_parameters_6_0= ruleParameterDecl )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2321:1: (lv_parameters_6_0= ruleParameterDecl )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2322:3: lv_parameters_6_0= ruleParameterDecl
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4834);
                    lv_parameters_6_0=ruleParameterDecl();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"parameters",
                            		lv_parameters_6_0, 
                            		"ParameterDecl");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2338:2: (otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) ) )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==21) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2338:4: otherlv_7= ',' ( (lv_parameters_8_0= ruleParameterDecl ) )
                    	    {
                    	    otherlv_7=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl4847); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_5_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2342:1: ( (lv_parameters_8_0= ruleParameterDecl ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2343:1: (lv_parameters_8_0= ruleParameterDecl )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2343:1: (lv_parameters_8_0= ruleParameterDecl )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2344:3: lv_parameters_8_0= ruleParameterDecl
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_5_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4868);
                    	    lv_parameters_8_0=ruleParameterDecl();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"parameters",
                    	            		lv_parameters_8_0, 
                    	            		"ParameterDecl");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);

                    otherlv_9=(Token)match(input,19,FOLLOW_19_in_ruleLocalProtocolDecl4882); 

                        	newLeafNode(otherlv_9, grammarAccess.getLocalProtocolDeclAccess().getGreaterThanSignKeyword_5_3());
                        

                    }
                    break;

            }

            otherlv_10=(Token)match(input,20,FOLLOW_20_in_ruleLocalProtocolDecl4896); 

                	newLeafNode(otherlv_10, grammarAccess.getLocalProtocolDeclAccess().getLeftParenthesisKeyword_6());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2368:1: ( (lv_roles_11_0= ruleRoleDecl ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2369:1: (lv_roles_11_0= ruleRoleDecl )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2369:1: (lv_roles_11_0= ruleRoleDecl )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2370:3: lv_roles_11_0= ruleRoleDecl
            {
             
            	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_7_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4917);
            lv_roles_11_0=ruleRoleDecl();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_11_0, 
                    		"RoleDecl");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2386:2: (otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) ) )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==21) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2386:4: otherlv_12= ',' ( (lv_roles_13_0= ruleRoleDecl ) )
            	    {
            	    otherlv_12=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl4930); 

            	        	newLeafNode(otherlv_12, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_8_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2390:1: ( (lv_roles_13_0= ruleRoleDecl ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2391:1: (lv_roles_13_0= ruleRoleDecl )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2391:1: (lv_roles_13_0= ruleRoleDecl )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2392:3: lv_roles_13_0= ruleRoleDecl
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_8_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4951);
            	    lv_roles_13_0=ruleRoleDecl();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"roles",
            	            		lv_roles_13_0, 
            	            		"RoleDecl");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            otherlv_14=(Token)match(input,22,FOLLOW_22_in_ruleLocalProtocolDecl4965); 

                	newLeafNode(otherlv_14, grammarAccess.getLocalProtocolDeclAccess().getRightParenthesisKeyword_9());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2412:1: ( ( (lv_block_15_0= ruleLocalProtocolBlock ) ) | (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==29) ) {
                alt45=1;
            }
            else if ( (LA45_0==26) ) {
                alt45=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2412:2: ( (lv_block_15_0= ruleLocalProtocolBlock ) )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2412:2: ( (lv_block_15_0= ruleLocalProtocolBlock ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2413:1: (lv_block_15_0= ruleLocalProtocolBlock )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2413:1: (lv_block_15_0= ruleLocalProtocolBlock )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2414:3: lv_block_15_0= ruleLocalProtocolBlock
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getBlockLocalProtocolBlockParserRuleCall_10_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalProtocolDecl4987);
                    lv_block_15_0=ruleLocalProtocolBlock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		set(
                           			current, 
                           			"block",
                            		lv_block_15_0, 
                            		"LocalProtocolBlock");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2431:6: (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2431:6: (otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';' )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2431:8: otherlv_16= 'instantiates' ( (lv_instantiates_17_0= RULE_ID ) ) (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )? otherlv_23= '(' ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) ) (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )* otherlv_27= ')' otherlv_28= ';'
                    {
                    otherlv_16=(Token)match(input,26,FOLLOW_26_in_ruleLocalProtocolDecl5006); 

                        	newLeafNode(otherlv_16, grammarAccess.getLocalProtocolDeclAccess().getInstantiatesKeyword_10_1_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2435:1: ( (lv_instantiates_17_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2436:1: (lv_instantiates_17_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2436:1: (lv_instantiates_17_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2437:3: lv_instantiates_17_0= RULE_ID
                    {
                    lv_instantiates_17_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalProtocolDecl5023); 

                    			newLeafNode(lv_instantiates_17_0, grammarAccess.getLocalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_10_1_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"instantiates",
                            		lv_instantiates_17_0, 
                            		"ID");
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2453:2: (otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>' )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==18) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2453:4: otherlv_18= '<' ( (lv_arguments_19_0= ruleArgument ) ) (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )* otherlv_22= '>'
                            {
                            otherlv_18=(Token)match(input,18,FOLLOW_18_in_ruleLocalProtocolDecl5041); 

                                	newLeafNode(otherlv_18, grammarAccess.getLocalProtocolDeclAccess().getLessThanSignKeyword_10_1_2_0());
                                
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2457:1: ( (lv_arguments_19_0= ruleArgument ) )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2458:1: (lv_arguments_19_0= ruleArgument )
                            {
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2458:1: (lv_arguments_19_0= ruleArgument )
                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2459:3: lv_arguments_19_0= ruleArgument
                            {
                             
                            	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_10_1_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5062);
                            lv_arguments_19_0=ruleArgument();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"arguments",
                                    		lv_arguments_19_0, 
                                    		"Argument");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2475:2: (otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) ) )*
                            loop42:
                            do {
                                int alt42=2;
                                int LA42_0 = input.LA(1);

                                if ( (LA42_0==21) ) {
                                    alt42=1;
                                }


                                switch (alt42) {
                            	case 1 :
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2475:4: otherlv_20= ',' ( (lv_arguments_21_0= ruleArgument ) )
                            	    {
                            	    otherlv_20=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl5075); 

                            	        	newLeafNode(otherlv_20, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_10_1_2_2_0());
                            	        
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2479:1: ( (lv_arguments_21_0= ruleArgument ) )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2480:1: (lv_arguments_21_0= ruleArgument )
                            	    {
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2480:1: (lv_arguments_21_0= ruleArgument )
                            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2481:3: lv_arguments_21_0= ruleArgument
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_10_1_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5096);
                            	    lv_arguments_21_0=ruleArgument();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"arguments",
                            	            		lv_arguments_21_0, 
                            	            		"Argument");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop42;
                                }
                            } while (true);

                            otherlv_22=(Token)match(input,19,FOLLOW_19_in_ruleLocalProtocolDecl5110); 

                                	newLeafNode(otherlv_22, grammarAccess.getLocalProtocolDeclAccess().getGreaterThanSignKeyword_10_1_2_3());
                                

                            }
                            break;

                    }

                    otherlv_23=(Token)match(input,20,FOLLOW_20_in_ruleLocalProtocolDecl5124); 

                        	newLeafNode(otherlv_23, grammarAccess.getLocalProtocolDeclAccess().getLeftParenthesisKeyword_10_1_3());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2505:1: ( (lv_roleInstantiations_24_0= ruleRoleInstantiation ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2506:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2506:1: (lv_roleInstantiations_24_0= ruleRoleInstantiation )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2507:3: lv_roleInstantiations_24_0= ruleRoleInstantiation
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_10_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5145);
                    lv_roleInstantiations_24_0=ruleRoleInstantiation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	        }
                           		add(
                           			current, 
                           			"roleInstantiations",
                            		lv_roleInstantiations_24_0, 
                            		"RoleInstantiation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2523:2: (otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) ) )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==21) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2523:4: otherlv_25= ',' ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) )
                    	    {
                    	    otherlv_25=(Token)match(input,21,FOLLOW_21_in_ruleLocalProtocolDecl5158); 

                    	        	newLeafNode(otherlv_25, grammarAccess.getLocalProtocolDeclAccess().getCommaKeyword_10_1_5_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2527:1: ( (lv_roleInstantiations_26_0= ruleRoleInstantiation ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2528:1: (lv_roleInstantiations_26_0= ruleRoleInstantiation )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2528:1: (lv_roleInstantiations_26_0= ruleRoleInstantiation )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2529:3: lv_roleInstantiations_26_0= ruleRoleInstantiation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_10_1_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5179);
                    	    lv_roleInstantiations_26_0=ruleRoleInstantiation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolDeclRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"roleInstantiations",
                    	            		lv_roleInstantiations_26_0, 
                    	            		"RoleInstantiation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);

                    otherlv_27=(Token)match(input,22,FOLLOW_22_in_ruleLocalProtocolDecl5193); 

                        	newLeafNode(otherlv_27, grammarAccess.getLocalProtocolDeclAccess().getRightParenthesisKeyword_10_1_6());
                        
                    otherlv_28=(Token)match(input,12,FOLLOW_12_in_ruleLocalProtocolDecl5205); 

                        	newLeafNode(otherlv_28, grammarAccess.getLocalProtocolDeclAccess().getSemicolonKeyword_10_1_7());
                        

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
    // $ANTLR end "ruleLocalProtocolDecl"


    // $ANTLR start "entryRuleLocalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2561:1: entryRuleLocalProtocolBlock returns [EObject current=null] : iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF ;
    public final EObject entryRuleLocalProtocolBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalProtocolBlock = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2562:2: (iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2563:2: iv_ruleLocalProtocolBlock= ruleLocalProtocolBlock EOF
            {
             newCompositeNode(grammarAccess.getLocalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_entryRuleLocalProtocolBlock5243);
            iv_ruleLocalProtocolBlock=ruleLocalProtocolBlock();

            state._fsp--;

             current =iv_ruleLocalProtocolBlock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalProtocolBlock5253); 

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
    // $ANTLR end "entryRuleLocalProtocolBlock"


    // $ANTLR start "ruleLocalProtocolBlock"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2570:1: ruleLocalProtocolBlock returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' ) ;
    public final EObject ruleLocalProtocolBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_activities_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2573:28: ( ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2574:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2574:1: ( () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2574:2: () otherlv_1= '{' ( (lv_activities_2_0= ruleLlobalInteraction ) )* otherlv_3= '}'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2574:2: ()
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2575:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getLocalProtocolBlockAccess().getLocalProtocolBlockAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,29,FOLLOW_29_in_ruleLocalProtocolBlock5299); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalProtocolBlockAccess().getLeftCurlyBracketKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2584:1: ( (lv_activities_2_0= ruleLlobalInteraction ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==RULE_ID||LA46_0==32||(LA46_0>=35 && LA46_0<=37)||LA46_0==39||LA46_0==42) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2585:1: (lv_activities_2_0= ruleLlobalInteraction )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2585:1: (lv_activities_2_0= ruleLlobalInteraction )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2586:3: lv_activities_2_0= ruleLlobalInteraction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalProtocolBlockAccess().getActivitiesLlobalInteractionParserRuleCall_2_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLlobalInteraction_in_ruleLocalProtocolBlock5320);
            	    lv_activities_2_0=ruleLlobalInteraction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalProtocolBlockRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"activities",
            	            		lv_activities_2_0, 
            	            		"LlobalInteraction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            otherlv_3=(Token)match(input,30,FOLLOW_30_in_ruleLocalProtocolBlock5333); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalProtocolBlockAccess().getRightCurlyBracketKeyword_3());
                

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
    // $ANTLR end "ruleLocalProtocolBlock"


    // $ANTLR start "entryRuleLlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2614:1: entryRuleLlobalInteraction returns [EObject current=null] : iv_ruleLlobalInteraction= ruleLlobalInteraction EOF ;
    public final EObject entryRuleLlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLlobalInteraction = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2615:2: (iv_ruleLlobalInteraction= ruleLlobalInteraction EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2616:2: iv_ruleLlobalInteraction= ruleLlobalInteraction EOF
            {
             newCompositeNode(grammarAccess.getLlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleLlobalInteraction_in_entryRuleLlobalInteraction5369);
            iv_ruleLlobalInteraction=ruleLlobalInteraction();

            state._fsp--;

             current =iv_ruleLlobalInteraction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLlobalInteraction5379); 

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
    // $ANTLR end "entryRuleLlobalInteraction"


    // $ANTLR start "ruleLlobalInteraction"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2623:1: ruleLlobalInteraction returns [EObject current=null] : (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_localinterruptible_6= rulelocalinterruptible | this_LocalDo_7= ruleLocalDo ) ;
    public final EObject ruleLlobalInteraction() throws RecognitionException {
        EObject current = null;

        EObject this_LocalSend_0 = null;

        EObject this_LocalReceive_1 = null;

        EObject this_LocalChoice_2 = null;

        EObject this_LocalParallel_3 = null;

        EObject this_LocalRecursion_4 = null;

        EObject this_LocalContinue_5 = null;

        EObject this_localinterruptible_6 = null;

        EObject this_LocalDo_7 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2626:28: ( (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_localinterruptible_6= rulelocalinterruptible | this_LocalDo_7= ruleLocalDo ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2627:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_localinterruptible_6= rulelocalinterruptible | this_LocalDo_7= ruleLocalDo )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2627:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_localinterruptible_6= rulelocalinterruptible | this_LocalDo_7= ruleLocalDo )
            int alt47=8;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2628:5: this_LocalSend_0= ruleLocalSend
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalSendParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleLocalSend_in_ruleLlobalInteraction5426);
                    this_LocalSend_0=ruleLocalSend();

                    state._fsp--;

                     
                            current = this_LocalSend_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2638:5: this_LocalReceive_1= ruleLocalReceive
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalReceiveParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleLocalReceive_in_ruleLlobalInteraction5453);
                    this_LocalReceive_1=ruleLocalReceive();

                    state._fsp--;

                     
                            current = this_LocalReceive_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2648:5: this_LocalChoice_2= ruleLocalChoice
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalChoiceParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleLocalChoice_in_ruleLlobalInteraction5480);
                    this_LocalChoice_2=ruleLocalChoice();

                    state._fsp--;

                     
                            current = this_LocalChoice_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2658:5: this_LocalParallel_3= ruleLocalParallel
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalParallelParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleLocalParallel_in_ruleLlobalInteraction5507);
                    this_LocalParallel_3=ruleLocalParallel();

                    state._fsp--;

                     
                            current = this_LocalParallel_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2668:5: this_LocalRecursion_4= ruleLocalRecursion
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalRecursionParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleLocalRecursion_in_ruleLlobalInteraction5534);
                    this_LocalRecursion_4=ruleLocalRecursion();

                    state._fsp--;

                     
                            current = this_LocalRecursion_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2678:5: this_LocalContinue_5= ruleLocalContinue
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalContinueParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleLocalContinue_in_ruleLlobalInteraction5561);
                    this_LocalContinue_5=ruleLocalContinue();

                    state._fsp--;

                     
                            current = this_LocalContinue_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2688:5: this_localinterruptible_6= rulelocalinterruptible
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalinterruptibleParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_rulelocalinterruptible_in_ruleLlobalInteraction5588);
                    this_localinterruptible_6=rulelocalinterruptible();

                    state._fsp--;

                     
                            current = this_localinterruptible_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2698:5: this_LocalDo_7= ruleLocalDo
                    {
                     
                            newCompositeNode(grammarAccess.getLlobalInteractionAccess().getLocalDoParserRuleCall_7()); 
                        
                    pushFollow(FOLLOW_ruleLocalDo_in_ruleLlobalInteraction5615);
                    this_LocalDo_7=ruleLocalDo();

                    state._fsp--;

                     
                            current = this_LocalDo_7; 
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
    // $ANTLR end "ruleLlobalInteraction"


    // $ANTLR start "entryRuleLocalSend"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2714:1: entryRuleLocalSend returns [EObject current=null] : iv_ruleLocalSend= ruleLocalSend EOF ;
    public final EObject entryRuleLocalSend() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalSend = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2715:2: (iv_ruleLocalSend= ruleLocalSend EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2716:2: iv_ruleLocalSend= ruleLocalSend EOF
            {
             newCompositeNode(grammarAccess.getLocalSendRule()); 
            pushFollow(FOLLOW_ruleLocalSend_in_entryRuleLocalSend5650);
            iv_ruleLocalSend=ruleLocalSend();

            state._fsp--;

             current =iv_ruleLocalSend; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalSend5660); 

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
    // $ANTLR end "entryRuleLocalSend"


    // $ANTLR start "ruleLocalSend"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2723:1: ruleLocalSend returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' ) ;
    public final EObject ruleLocalSend() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_toRoles_2_0=null;
        Token otherlv_3=null;
        Token lv_toRoles_4_0=null;
        Token otherlv_5=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2726:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2727:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2727:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2727:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'to' ( (lv_toRoles_2_0= RULE_ID ) ) (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )* otherlv_5= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2727:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2728:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2728:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2729:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalSendAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalSend5706);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalSendRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,31,FOLLOW_31_in_ruleLocalSend5718); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalSendAccess().getToKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2749:1: ( (lv_toRoles_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2750:1: (lv_toRoles_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2750:1: (lv_toRoles_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2751:3: lv_toRoles_2_0= RULE_ID
            {
            lv_toRoles_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalSend5735); 

            			newLeafNode(lv_toRoles_2_0, grammarAccess.getLocalSendAccess().getToRolesIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalSendRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2767:2: (otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) ) )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==21) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2767:4: otherlv_3= ',' ( (lv_toRoles_4_0= RULE_ID ) )
            	    {
            	    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleLocalSend5753); 

            	        	newLeafNode(otherlv_3, grammarAccess.getLocalSendAccess().getCommaKeyword_3_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2771:1: ( (lv_toRoles_4_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2772:1: (lv_toRoles_4_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2772:1: (lv_toRoles_4_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2773:3: lv_toRoles_4_0= RULE_ID
            	    {
            	    lv_toRoles_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalSend5770); 

            	    			newLeafNode(lv_toRoles_4_0, grammarAccess.getLocalSendAccess().getToRolesIDTerminalRuleCall_3_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getLocalSendRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRoles",
            	            		lv_toRoles_4_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleLocalSend5789); 

                	newLeafNode(otherlv_5, grammarAccess.getLocalSendAccess().getSemicolonKeyword_4());
                

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
    // $ANTLR end "ruleLocalSend"


    // $ANTLR start "entryRuleLocalReceive"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2801:1: entryRuleLocalReceive returns [EObject current=null] : iv_ruleLocalReceive= ruleLocalReceive EOF ;
    public final EObject entryRuleLocalReceive() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalReceive = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2802:2: (iv_ruleLocalReceive= ruleLocalReceive EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2803:2: iv_ruleLocalReceive= ruleLocalReceive EOF
            {
             newCompositeNode(grammarAccess.getLocalReceiveRule()); 
            pushFollow(FOLLOW_ruleLocalReceive_in_entryRuleLocalReceive5825);
            iv_ruleLocalReceive=ruleLocalReceive();

            state._fsp--;

             current =iv_ruleLocalReceive; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalReceive5835); 

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
    // $ANTLR end "entryRuleLocalReceive"


    // $ANTLR start "ruleLocalReceive"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2810:1: ruleLocalReceive returns [EObject current=null] : ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' ) ;
    public final EObject ruleLocalReceive() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_fromRole_2_0=null;
        Token otherlv_3=null;
        EObject lv_message_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2813:28: ( ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2814:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2814:1: ( ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2814:2: ( (lv_message_0_0= ruleMessage ) ) otherlv_1= 'from' ( (lv_fromRole_2_0= RULE_ID ) ) otherlv_3= ';'
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2814:2: ( (lv_message_0_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2815:1: (lv_message_0_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2815:1: (lv_message_0_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2816:3: lv_message_0_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalReceiveAccess().getMessageMessageParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalReceive5881);
            lv_message_0_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalReceiveRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_0_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_16_in_ruleLocalReceive5893); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalReceiveAccess().getFromKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2836:1: ( (lv_fromRole_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2837:1: (lv_fromRole_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2837:1: (lv_fromRole_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2838:3: lv_fromRole_2_0= RULE_ID
            {
            lv_fromRole_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalReceive5910); 

            			newLeafNode(lv_fromRole_2_0, grammarAccess.getLocalReceiveAccess().getFromRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalReceiveRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_2_0, 
                    		"ID");
            	    

            }


            }

            otherlv_3=(Token)match(input,12,FOLLOW_12_in_ruleLocalReceive5927); 

                	newLeafNode(otherlv_3, grammarAccess.getLocalReceiveAccess().getSemicolonKeyword_3());
                

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
    // $ANTLR end "ruleLocalReceive"


    // $ANTLR start "entryRuleLocalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2866:1: entryRuleLocalChoice returns [EObject current=null] : iv_ruleLocalChoice= ruleLocalChoice EOF ;
    public final EObject entryRuleLocalChoice() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalChoice = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2867:2: (iv_ruleLocalChoice= ruleLocalChoice EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2868:2: iv_ruleLocalChoice= ruleLocalChoice EOF
            {
             newCompositeNode(grammarAccess.getLocalChoiceRule()); 
            pushFollow(FOLLOW_ruleLocalChoice_in_entryRuleLocalChoice5963);
            iv_ruleLocalChoice=ruleLocalChoice();

            state._fsp--;

             current =iv_ruleLocalChoice; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalChoice5973); 

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
    // $ANTLR end "entryRuleLocalChoice"


    // $ANTLR start "ruleLocalChoice"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2875:1: ruleLocalChoice returns [EObject current=null] : (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* ) ;
    public final EObject ruleLocalChoice() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_role_2_0=null;
        Token otherlv_4=null;
        EObject lv_blocks_3_0 = null;

        EObject lv_blocks_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2878:28: ( (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2879:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2879:1: (otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2879:3: otherlv_0= 'choice' otherlv_1= 'at' ( (lv_role_2_0= RULE_ID ) ) ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleLocalChoice6010); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalChoiceAccess().getChoiceKeyword_0());
                
            otherlv_1=(Token)match(input,33,FOLLOW_33_in_ruleLocalChoice6022); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalChoiceAccess().getAtKeyword_1());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2887:1: ( (lv_role_2_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2888:1: (lv_role_2_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2888:1: (lv_role_2_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2889:3: lv_role_2_0= RULE_ID
            {
            lv_role_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalChoice6039); 

            			newLeafNode(lv_role_2_0, grammarAccess.getLocalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalChoiceRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"role",
                    		lv_role_2_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2905:2: ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2906:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2906:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2907:3: lv_blocks_3_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalChoiceAccess().getBlocksLocalProtocolBlockParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6065);
            lv_blocks_3_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalChoiceRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_3_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2923:2: (otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) ) )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==34) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2923:4: otherlv_4= 'or' ( (lv_blocks_5_0= ruleLocalProtocolBlock ) )
            	    {
            	    otherlv_4=(Token)match(input,34,FOLLOW_34_in_ruleLocalChoice6078); 

            	        	newLeafNode(otherlv_4, grammarAccess.getLocalChoiceAccess().getOrKeyword_4_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2927:1: ( (lv_blocks_5_0= ruleLocalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2928:1: (lv_blocks_5_0= ruleLocalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2928:1: (lv_blocks_5_0= ruleLocalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2929:3: lv_blocks_5_0= ruleLocalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalChoiceAccess().getBlocksLocalProtocolBlockParserRuleCall_4_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6099);
            	    lv_blocks_5_0=ruleLocalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalChoiceRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_5_0, 
            	            		"LocalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop49;
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
    // $ANTLR end "ruleLocalChoice"


    // $ANTLR start "entryRuleLocalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2953:1: entryRuleLocalRecursion returns [EObject current=null] : iv_ruleLocalRecursion= ruleLocalRecursion EOF ;
    public final EObject entryRuleLocalRecursion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalRecursion = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2954:2: (iv_ruleLocalRecursion= ruleLocalRecursion EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2955:2: iv_ruleLocalRecursion= ruleLocalRecursion EOF
            {
             newCompositeNode(grammarAccess.getLocalRecursionRule()); 
            pushFollow(FOLLOW_ruleLocalRecursion_in_entryRuleLocalRecursion6137);
            iv_ruleLocalRecursion=ruleLocalRecursion();

            state._fsp--;

             current =iv_ruleLocalRecursion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalRecursion6147); 

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
    // $ANTLR end "entryRuleLocalRecursion"


    // $ANTLR start "ruleLocalRecursion"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2962:1: ruleLocalRecursion returns [EObject current=null] : (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) ) ;
    public final EObject ruleLocalRecursion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        EObject lv_block_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2965:28: ( (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2966:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2966:1: (otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2966:3: otherlv_0= 'rec' ( (lv_label_1_0= RULE_ID ) ) ( (lv_block_2_0= ruleLocalProtocolBlock ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_35_in_ruleLocalRecursion6184); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalRecursionAccess().getRecKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2970:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2971:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2971:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2972:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalRecursion6201); 

            			newLeafNode(lv_label_1_0, grammarAccess.getLocalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalRecursionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2988:2: ( (lv_block_2_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2989:1: (lv_block_2_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2989:1: (lv_block_2_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:2990:3: lv_block_2_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalRecursionAccess().getBlockLocalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalRecursion6227);
            lv_block_2_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalRecursionRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_2_0, 
                    		"LocalProtocolBlock");
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
    // $ANTLR end "ruleLocalRecursion"


    // $ANTLR start "entryRuleLocalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3014:1: entryRuleLocalContinue returns [EObject current=null] : iv_ruleLocalContinue= ruleLocalContinue EOF ;
    public final EObject entryRuleLocalContinue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalContinue = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3015:2: (iv_ruleLocalContinue= ruleLocalContinue EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3016:2: iv_ruleLocalContinue= ruleLocalContinue EOF
            {
             newCompositeNode(grammarAccess.getLocalContinueRule()); 
            pushFollow(FOLLOW_ruleLocalContinue_in_entryRuleLocalContinue6263);
            iv_ruleLocalContinue=ruleLocalContinue();

            state._fsp--;

             current =iv_ruleLocalContinue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalContinue6273); 

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
    // $ANTLR end "entryRuleLocalContinue"


    // $ANTLR start "ruleLocalContinue"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3023:1: ruleLocalContinue returns [EObject current=null] : (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) ;
    public final EObject ruleLocalContinue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_label_1_0=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3026:28: ( (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3027:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3027:1: (otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3027:3: otherlv_0= 'continue' ( (lv_label_1_0= RULE_ID ) ) otherlv_2= ';'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleLocalContinue6310); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalContinueAccess().getContinueKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3031:1: ( (lv_label_1_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3032:1: (lv_label_1_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3032:1: (lv_label_1_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3033:3: lv_label_1_0= RULE_ID
            {
            lv_label_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalContinue6327); 

            			newLeafNode(lv_label_1_0, grammarAccess.getLocalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalContinueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"label",
                    		lv_label_1_0, 
                    		"ID");
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleLocalContinue6344); 

                	newLeafNode(otherlv_2, grammarAccess.getLocalContinueAccess().getSemicolonKeyword_2());
                

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
    // $ANTLR end "ruleLocalContinue"


    // $ANTLR start "entryRuleLocalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3061:1: entryRuleLocalParallel returns [EObject current=null] : iv_ruleLocalParallel= ruleLocalParallel EOF ;
    public final EObject entryRuleLocalParallel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalParallel = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3062:2: (iv_ruleLocalParallel= ruleLocalParallel EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3063:2: iv_ruleLocalParallel= ruleLocalParallel EOF
            {
             newCompositeNode(grammarAccess.getLocalParallelRule()); 
            pushFollow(FOLLOW_ruleLocalParallel_in_entryRuleLocalParallel6380);
            iv_ruleLocalParallel=ruleLocalParallel();

            state._fsp--;

             current =iv_ruleLocalParallel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalParallel6390); 

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
    // $ANTLR end "entryRuleLocalParallel"


    // $ANTLR start "ruleLocalParallel"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3070:1: ruleLocalParallel returns [EObject current=null] : (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* ) ;
    public final EObject ruleLocalParallel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_blocks_1_0 = null;

        EObject lv_blocks_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3073:28: ( (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3074:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3074:1: (otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )* )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3074:3: otherlv_0= 'par' ( (lv_blocks_1_0= ruleLocalProtocolBlock ) ) (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )*
            {
            otherlv_0=(Token)match(input,37,FOLLOW_37_in_ruleLocalParallel6427); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalParallelAccess().getParKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3078:1: ( (lv_blocks_1_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3079:1: (lv_blocks_1_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3079:1: (lv_blocks_1_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3080:3: lv_blocks_1_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalParallelAccess().getBlocksLocalProtocolBlockParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6448);
            lv_blocks_1_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalParallelRule());
            	        }
                   		add(
                   			current, 
                   			"blocks",
                    		lv_blocks_1_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3096:2: (otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==38) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3096:4: otherlv_2= 'and' ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            	    {
            	    otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleLocalParallel6461); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalParallelAccess().getAndKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3100:1: ( (lv_blocks_3_0= ruleLocalProtocolBlock ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3101:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3101:1: (lv_blocks_3_0= ruleLocalProtocolBlock )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3102:3: lv_blocks_3_0= ruleLocalProtocolBlock
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalParallelAccess().getBlocksLocalProtocolBlockParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6482);
            	    lv_blocks_3_0=ruleLocalProtocolBlock();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalParallelRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"blocks",
            	            		lv_blocks_3_0, 
            	            		"LocalProtocolBlock");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
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
    // $ANTLR end "ruleLocalParallel"


    // $ANTLR start "entryRulelocalinterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3126:1: entryRulelocalinterruptible returns [EObject current=null] : iv_rulelocalinterruptible= rulelocalinterruptible EOF ;
    public final EObject entryRulelocalinterruptible() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelocalinterruptible = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3127:2: (iv_rulelocalinterruptible= rulelocalinterruptible EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3128:2: iv_rulelocalinterruptible= rulelocalinterruptible EOF
            {
             newCompositeNode(grammarAccess.getLocalinterruptibleRule()); 
            pushFollow(FOLLOW_rulelocalinterruptible_in_entryRulelocalinterruptible6520);
            iv_rulelocalinterruptible=rulelocalinterruptible();

            state._fsp--;

             current =iv_rulelocalinterruptible; 
            match(input,EOF,FOLLOW_EOF_in_entryRulelocalinterruptible6530); 

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
    // $ANTLR end "entryRulelocalinterruptible"


    // $ANTLR start "rulelocalinterruptible"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3135:1: rulelocalinterruptible returns [EObject current=null] : (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' ) ;
    public final EObject rulelocalinterruptible() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        EObject lv_block_3_0 = null;

        EObject lv_throw_6_0 = null;

        EObject lv_catches_7_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3138:28: ( (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3139:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3139:1: (otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3139:3: otherlv_0= 'interruptible' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_block_3_0= ruleLocalProtocolBlock ) ) otherlv_4= 'with' otherlv_5= '{' ( (lv_throw_6_0= ruleLocalThrow ) )? ( (lv_catches_7_0= ruleLocalCatch ) )* otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_rulelocalinterruptible6567); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalinterruptibleAccess().getInterruptibleKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3143:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_ID) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3143:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3143:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3144:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3144:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3145:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulelocalinterruptible6585); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getLocalinterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalinterruptibleRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_rulelocalinterruptible6602); 

                        	newLeafNode(otherlv_2, grammarAccess.getLocalinterruptibleAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3165:3: ( (lv_block_3_0= ruleLocalProtocolBlock ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3166:1: (lv_block_3_0= ruleLocalProtocolBlock )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3166:1: (lv_block_3_0= ruleLocalProtocolBlock )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3167:3: lv_block_3_0= ruleLocalProtocolBlock
            {
             
            	        newCompositeNode(grammarAccess.getLocalinterruptibleAccess().getBlockLocalProtocolBlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalProtocolBlock_in_rulelocalinterruptible6625);
            lv_block_3_0=ruleLocalProtocolBlock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalinterruptibleRule());
            	        }
                   		set(
                   			current, 
                   			"block",
                    		lv_block_3_0, 
                    		"LocalProtocolBlock");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,40,FOLLOW_40_in_rulelocalinterruptible6637); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalinterruptibleAccess().getWithKeyword_3());
                
            otherlv_5=(Token)match(input,29,FOLLOW_29_in_rulelocalinterruptible6649); 

                	newLeafNode(otherlv_5, grammarAccess.getLocalinterruptibleAccess().getLeftCurlyBracketKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3191:1: ( (lv_throw_6_0= ruleLocalThrow ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==44) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3192:1: (lv_throw_6_0= ruleLocalThrow )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3192:1: (lv_throw_6_0= ruleLocalThrow )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3193:3: lv_throw_6_0= ruleLocalThrow
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalinterruptibleAccess().getThrowLocalThrowParserRuleCall_5_0()); 
                    	    
                    pushFollow(FOLLOW_ruleLocalThrow_in_rulelocalinterruptible6670);
                    lv_throw_6_0=ruleLocalThrow();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalinterruptibleRule());
                    	        }
                           		set(
                           			current, 
                           			"throw",
                            		lv_throw_6_0, 
                            		"LocalThrow");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3209:3: ( (lv_catches_7_0= ruleLocalCatch ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==45) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3210:1: (lv_catches_7_0= ruleLocalCatch )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3210:1: (lv_catches_7_0= ruleLocalCatch )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3211:3: lv_catches_7_0= ruleLocalCatch
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalinterruptibleAccess().getCatchesLocalCatchParserRuleCall_6_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalCatch_in_rulelocalinterruptible6692);
            	    lv_catches_7_0=ruleLocalCatch();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalinterruptibleRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"catches",
            	            		lv_catches_7_0, 
            	            		"LocalCatch");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            otherlv_8=(Token)match(input,30,FOLLOW_30_in_rulelocalinterruptible6705); 

                	newLeafNode(otherlv_8, grammarAccess.getLocalinterruptibleAccess().getRightCurlyBracketKeyword_7());
                

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
    // $ANTLR end "rulelocalinterruptible"


    // $ANTLR start "entryRuleLocalThrow"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3239:1: entryRuleLocalThrow returns [EObject current=null] : iv_ruleLocalThrow= ruleLocalThrow EOF ;
    public final EObject entryRuleLocalThrow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalThrow = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3240:2: (iv_ruleLocalThrow= ruleLocalThrow EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3241:2: iv_ruleLocalThrow= ruleLocalThrow EOF
            {
             newCompositeNode(grammarAccess.getLocalThrowRule()); 
            pushFollow(FOLLOW_ruleLocalThrow_in_entryRuleLocalThrow6741);
            iv_ruleLocalThrow=ruleLocalThrow();

            state._fsp--;

             current =iv_ruleLocalThrow; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalThrow6751); 

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
    // $ANTLR end "entryRuleLocalThrow"


    // $ANTLR start "ruleLocalThrow"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3248:1: ruleLocalThrow returns [EObject current=null] : (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )* otherlv_8= ';' ) ;
    public final EObject ruleLocalThrow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_toRoles_5_0=null;
        Token otherlv_6=null;
        Token lv_toRols_7_0=null;
        Token otherlv_8=null;
        EObject lv_messages_1_0 = null;

        EObject lv_messages_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3251:28: ( (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )* otherlv_8= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3252:1: (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )* otherlv_8= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3252:1: (otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )* otherlv_8= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3252:3: otherlv_0= 'throw' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'to' ( (lv_toRoles_5_0= RULE_ID ) ) (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )* otherlv_8= ';'
            {
            otherlv_0=(Token)match(input,44,FOLLOW_44_in_ruleLocalThrow6788); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalThrowAccess().getThrowKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3256:1: ( (lv_messages_1_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3257:1: (lv_messages_1_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3257:1: (lv_messages_1_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3258:3: lv_messages_1_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalThrowAccess().getMessagesMessageParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalThrow6809);
            lv_messages_1_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalThrowRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_1_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3274:2: (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==21) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3274:4: otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) )
            	    {
            	    otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleLocalThrow6822); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalThrowAccess().getCommaKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3278:1: ( (lv_messages_3_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3279:1: (lv_messages_3_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3279:1: (lv_messages_3_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3280:3: lv_messages_3_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalThrowAccess().getMessagesMessageParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleLocalThrow6843);
            	    lv_messages_3_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalThrowRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_3_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            otherlv_4=(Token)match(input,31,FOLLOW_31_in_ruleLocalThrow6857); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalThrowAccess().getToKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3300:1: ( (lv_toRoles_5_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3301:1: (lv_toRoles_5_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3301:1: (lv_toRoles_5_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3302:3: lv_toRoles_5_0= RULE_ID
            {
            lv_toRoles_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalThrow6874); 

            			newLeafNode(lv_toRoles_5_0, grammarAccess.getLocalThrowAccess().getToRolesIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalThrowRule());
            	        }
                   		addWithLastConsumed(
                   			current, 
                   			"toRoles",
                    		lv_toRoles_5_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3318:2: (otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==21) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3318:4: otherlv_6= ',' ( (lv_toRols_7_0= RULE_ID ) )
            	    {
            	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleLocalThrow6892); 

            	        	newLeafNode(otherlv_6, grammarAccess.getLocalThrowAccess().getCommaKeyword_5_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3322:1: ( (lv_toRols_7_0= RULE_ID ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3323:1: (lv_toRols_7_0= RULE_ID )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3323:1: (lv_toRols_7_0= RULE_ID )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3324:3: lv_toRols_7_0= RULE_ID
            	    {
            	    lv_toRols_7_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalThrow6909); 

            	    			newLeafNode(lv_toRols_7_0, grammarAccess.getLocalThrowAccess().getToRolsIDTerminalRuleCall_5_1_0()); 
            	    		

            	    	        if (current==null) {
            	    	            current = createModelElement(grammarAccess.getLocalThrowRule());
            	    	        }
            	           		addWithLastConsumed(
            	           			current, 
            	           			"toRols",
            	            		lv_toRols_7_0, 
            	            		"ID");
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);

            otherlv_8=(Token)match(input,12,FOLLOW_12_in_ruleLocalThrow6928); 

                	newLeafNode(otherlv_8, grammarAccess.getLocalThrowAccess().getSemicolonKeyword_6());
                

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
    // $ANTLR end "ruleLocalThrow"


    // $ANTLR start "entryRuleLocalCatch"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3352:1: entryRuleLocalCatch returns [EObject current=null] : iv_ruleLocalCatch= ruleLocalCatch EOF ;
    public final EObject entryRuleLocalCatch() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalCatch = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3353:2: (iv_ruleLocalCatch= ruleLocalCatch EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3354:2: iv_ruleLocalCatch= ruleLocalCatch EOF
            {
             newCompositeNode(grammarAccess.getLocalCatchRule()); 
            pushFollow(FOLLOW_ruleLocalCatch_in_entryRuleLocalCatch6964);
            iv_ruleLocalCatch=ruleLocalCatch();

            state._fsp--;

             current =iv_ruleLocalCatch; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalCatch6974); 

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
    // $ANTLR end "entryRuleLocalCatch"


    // $ANTLR start "ruleLocalCatch"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3361:1: ruleLocalCatch returns [EObject current=null] : (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' ) ;
    public final EObject ruleLocalCatch() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_fromRole_5_0=null;
        Token otherlv_6=null;
        EObject lv_messages_1_0 = null;

        EObject lv_messages_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3364:28: ( (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3365:1: (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3365:1: (otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3365:3: otherlv_0= 'catches' ( (lv_messages_1_0= ruleMessage ) ) (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )* otherlv_4= 'from' ( (lv_fromRole_5_0= RULE_ID ) ) otherlv_6= ';'
            {
            otherlv_0=(Token)match(input,45,FOLLOW_45_in_ruleLocalCatch7011); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalCatchAccess().getCatchesKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3369:1: ( (lv_messages_1_0= ruleMessage ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3370:1: (lv_messages_1_0= ruleMessage )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3370:1: (lv_messages_1_0= ruleMessage )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3371:3: lv_messages_1_0= ruleMessage
            {
             
            	        newCompositeNode(grammarAccess.getLocalCatchAccess().getMessagesMessageParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleMessage_in_ruleLocalCatch7032);
            lv_messages_1_0=ruleMessage();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalCatchRule());
            	        }
                   		add(
                   			current, 
                   			"messages",
                    		lv_messages_1_0, 
                    		"Message");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3387:2: (otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==21) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3387:4: otherlv_2= ',' ( (lv_messages_3_0= ruleMessage ) )
            	    {
            	    otherlv_2=(Token)match(input,21,FOLLOW_21_in_ruleLocalCatch7045); 

            	        	newLeafNode(otherlv_2, grammarAccess.getLocalCatchAccess().getCommaKeyword_2_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3391:1: ( (lv_messages_3_0= ruleMessage ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3392:1: (lv_messages_3_0= ruleMessage )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3392:1: (lv_messages_3_0= ruleMessage )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3393:3: lv_messages_3_0= ruleMessage
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalCatchAccess().getMessagesMessageParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMessage_in_ruleLocalCatch7066);
            	    lv_messages_3_0=ruleMessage();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalCatchRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"messages",
            	            		lv_messages_3_0, 
            	            		"Message");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            otherlv_4=(Token)match(input,16,FOLLOW_16_in_ruleLocalCatch7080); 

                	newLeafNode(otherlv_4, grammarAccess.getLocalCatchAccess().getFromKeyword_3());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3413:1: ( (lv_fromRole_5_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3414:1: (lv_fromRole_5_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3414:1: (lv_fromRole_5_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3415:3: lv_fromRole_5_0= RULE_ID
            {
            lv_fromRole_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalCatch7097); 

            			newLeafNode(lv_fromRole_5_0, grammarAccess.getLocalCatchAccess().getFromRoleIDTerminalRuleCall_4_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalCatchRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"fromRole",
                    		lv_fromRole_5_0, 
                    		"ID");
            	    

            }


            }

            otherlv_6=(Token)match(input,12,FOLLOW_12_in_ruleLocalCatch7114); 

                	newLeafNode(otherlv_6, grammarAccess.getLocalCatchAccess().getSemicolonKeyword_5());
                

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
    // $ANTLR end "ruleLocalCatch"


    // $ANTLR start "entryRuleLocalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3443:1: entryRuleLocalDo returns [EObject current=null] : iv_ruleLocalDo= ruleLocalDo EOF ;
    public final EObject entryRuleLocalDo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalDo = null;


        try {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3444:2: (iv_ruleLocalDo= ruleLocalDo EOF )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3445:2: iv_ruleLocalDo= ruleLocalDo EOF
            {
             newCompositeNode(grammarAccess.getLocalDoRule()); 
            pushFollow(FOLLOW_ruleLocalDo_in_entryRuleLocalDo7150);
            iv_ruleLocalDo=ruleLocalDo();

            state._fsp--;

             current =iv_ruleLocalDo; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalDo7160); 

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
    // $ANTLR end "entryRuleLocalDo"


    // $ANTLR start "ruleLocalDo"
    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3452:1: ruleLocalDo returns [EObject current=null] : (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) ;
    public final EObject ruleLocalDo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_scope_1_0=null;
        Token otherlv_2=null;
        Token lv_member_3_0=null;
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
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3455:28: ( (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3456:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3456:1: (otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';' )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3456:3: otherlv_0= 'do' ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_member_3_0= RULE_ID ) ) (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )? otherlv_9= '(' ( (lv_roles_10_0= ruleRoleInstantiation ) ) (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )* otherlv_13= ')' otherlv_14= ';'
            {
            otherlv_0=(Token)match(input,42,FOLLOW_42_in_ruleLocalDo7197); 

                	newLeafNode(otherlv_0, grammarAccess.getLocalDoAccess().getDoKeyword_0());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3460:1: ( ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==RULE_ID) ) {
                int LA57_1 = input.LA(2);

                if ( (LA57_1==23) ) {
                    alt57=1;
                }
            }
            switch (alt57) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3460:2: ( (lv_scope_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3460:2: ( (lv_scope_1_0= RULE_ID ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3461:1: (lv_scope_1_0= RULE_ID )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3461:1: (lv_scope_1_0= RULE_ID )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3462:3: lv_scope_1_0= RULE_ID
                    {
                    lv_scope_1_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalDo7215); 

                    			newLeafNode(lv_scope_1_0, grammarAccess.getLocalDoAccess().getScopeIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getLocalDoRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"scope",
                            		lv_scope_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleLocalDo7232); 

                        	newLeafNode(otherlv_2, grammarAccess.getLocalDoAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3482:3: ( (lv_member_3_0= RULE_ID ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3483:1: (lv_member_3_0= RULE_ID )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3483:1: (lv_member_3_0= RULE_ID )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3484:3: lv_member_3_0= RULE_ID
            {
            lv_member_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalDo7251); 

            			newLeafNode(lv_member_3_0, grammarAccess.getLocalDoAccess().getMemberIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalDoRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"member",
                    		lv_member_3_0, 
                    		"ID");
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3500:2: (otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>' )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==18) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3500:4: otherlv_4= '<' ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* otherlv_8= '>'
                    {
                    otherlv_4=(Token)match(input,18,FOLLOW_18_in_ruleLocalDo7269); 

                        	newLeafNode(otherlv_4, grammarAccess.getLocalDoAccess().getLessThanSignKeyword_3_0());
                        
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3504:1: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3505:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3505:1: (lv_arguments_5_0= ruleArgument )
                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3506:3: lv_arguments_5_0= ruleArgument
                    {
                     
                    	        newCompositeNode(grammarAccess.getLocalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleArgument_in_ruleLocalDo7290);
                    lv_arguments_5_0=ruleArgument();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
                    	        }
                           		add(
                           			current, 
                           			"arguments",
                            		lv_arguments_5_0, 
                            		"Argument");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3522:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==21) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3522:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
                    	    {
                    	    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleLocalDo7303); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getLocalDoAccess().getCommaKeyword_3_2_0());
                    	        
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3526:1: ( (lv_arguments_7_0= ruleArgument ) )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3527:1: (lv_arguments_7_0= ruleArgument )
                    	    {
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3527:1: (lv_arguments_7_0= ruleArgument )
                    	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3528:3: lv_arguments_7_0= ruleArgument
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getLocalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleArgument_in_ruleLocalDo7324);
                    	    lv_arguments_7_0=ruleArgument();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
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
                    	    break loop58;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,19,FOLLOW_19_in_ruleLocalDo7338); 

                        	newLeafNode(otherlv_8, grammarAccess.getLocalDoAccess().getGreaterThanSignKeyword_3_3());
                        

                    }
                    break;

            }

            otherlv_9=(Token)match(input,20,FOLLOW_20_in_ruleLocalDo7352); 

                	newLeafNode(otherlv_9, grammarAccess.getLocalDoAccess().getLeftParenthesisKeyword_4());
                
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3552:1: ( (lv_roles_10_0= ruleRoleInstantiation ) )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3553:1: (lv_roles_10_0= ruleRoleInstantiation )
            {
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3553:1: (lv_roles_10_0= ruleRoleInstantiation )
            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3554:3: lv_roles_10_0= ruleRoleInstantiation
            {
             
            	        newCompositeNode(grammarAccess.getLocalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7373);
            lv_roles_10_0=ruleRoleInstantiation();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
            	        }
                   		add(
                   			current, 
                   			"roles",
                    		lv_roles_10_0, 
                    		"RoleInstantiation");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3570:2: (otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==21) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3570:4: otherlv_11= ',' ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    {
            	    otherlv_11=(Token)match(input,21,FOLLOW_21_in_ruleLocalDo7386); 

            	        	newLeafNode(otherlv_11, grammarAccess.getLocalDoAccess().getCommaKeyword_6_0());
            	        
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3574:1: ( (lv_roles_12_0= ruleRoleInstantiation ) )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3575:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    {
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3575:1: (lv_roles_12_0= ruleRoleInstantiation )
            	    // ../org.scribble.editor/src-gen/org/scribble/editor/dsl/parser/antlr/internal/InternalScribbleDsl.g:3576:3: lv_roles_12_0= ruleRoleInstantiation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7407);
            	    lv_roles_12_0=ruleRoleInstantiation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalDoRule());
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
            	    break loop60;
                }
            } while (true);

            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleLocalDo7421); 

                	newLeafNode(otherlv_13, grammarAccess.getLocalDoAccess().getRightParenthesisKeyword_7());
                
            otherlv_14=(Token)match(input,12,FOLLOW_12_in_ruleLocalDo7433); 

                	newLeafNode(otherlv_14, grammarAccess.getLocalDoAccess().getSemicolonKeyword_8());
                

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
    // $ANTLR end "ruleLocalDo"

    // Delegated rules


    protected DFA47 dfa47 = new DFA47(this);
    static final String DFA47_eotS =
        "\23\uffff";
    static final String DFA47_eofS =
        "\23\uffff";
    static final String DFA47_minS =
        "\1\4\1\20\10\uffff\1\4\1\25\1\20\2\4\2\25\1\4\1\25";
    static final String DFA47_maxS =
        "\1\52\1\37\10\uffff\1\26\1\27\1\37\2\4\1\27\1\26\1\4\1\26";
    static final String DFA47_acceptS =
        "\2\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\2\1\1\11\uffff";
    static final String DFA47_specialS =
        "\23\uffff}>";
    static final String[] DFA47_transitionS = {
            "\1\1\33\uffff\1\2\2\uffff\1\4\1\5\1\3\1\uffff\1\6\2\uffff\1"+
            "\7",
            "\1\10\3\uffff\1\12\12\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\13\21\uffff\1\14",
            "\1\15\1\14\1\16",
            "\1\10\16\uffff\1\11",
            "\1\17",
            "\1\20",
            "\1\15\1\14\1\21",
            "\1\15\1\14",
            "\1\22",
            "\1\15\1\14"
    };

    static final short[] DFA47_eot = DFA.unpackEncodedString(DFA47_eotS);
    static final short[] DFA47_eof = DFA.unpackEncodedString(DFA47_eofS);
    static final char[] DFA47_min = DFA.unpackEncodedStringToUnsignedChars(DFA47_minS);
    static final char[] DFA47_max = DFA.unpackEncodedStringToUnsignedChars(DFA47_maxS);
    static final short[] DFA47_accept = DFA.unpackEncodedString(DFA47_acceptS);
    static final short[] DFA47_special = DFA.unpackEncodedString(DFA47_specialS);
    static final short[][] DFA47_transition;

    static {
        int numStates = DFA47_transitionS.length;
        DFA47_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA47_transition[i] = DFA.unpackEncodedString(DFA47_transitionS[i]);
        }
    }

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = DFA47_eot;
            this.eof = DFA47_eof;
            this.min = DFA47_min;
            this.max = DFA47_max;
            this.accept = DFA47_accept;
            this.special = DFA47_special;
            this.transition = DFA47_transition;
        }
        public String getDescription() {
            return "2627:1: (this_LocalSend_0= ruleLocalSend | this_LocalReceive_1= ruleLocalReceive | this_LocalChoice_2= ruleLocalChoice | this_LocalParallel_3= ruleLocalParallel | this_LocalRecursion_4= ruleLocalRecursion | this_LocalContinue_5= ruleLocalContinue | this_localinterruptible_6= rulelocalinterruptible | this_LocalDo_7= ruleLocalDo )";
        }
    }
 

    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_ruleModule132 = new BitSet(new long[]{0x0000080001034002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_ruleModule152 = new BitSet(new long[]{0x0000080001034002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_ruleModule174 = new BitSet(new long[]{0x0000080001020002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_ruleModule197 = new BitSet(new long[]{0x0000080001000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolDecl_in_ruleModule224 = new BitSet(new long[]{0x0000080001000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl262 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleDecl272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleModuleDecl309 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleModuleDecl330 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleModuleDecl342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_entryRuleModuleName379 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleName390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName430 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_13_in_ruleModuleName449 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleModuleName464 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_entryRuleImportDecl511 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportDecl521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_ruleImportDecl568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_ruleImportDecl595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_entryRuleImportModule630 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportModule640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_ruleImportModule677 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportModule698 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportModule711 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportModule728 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportModule747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_entryRuleImportMember783 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportMember793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleImportMember830 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleImportMember851 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleImportMember863 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember880 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_15_in_ruleImportMember898 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleImportMember915 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleImportMember934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl970 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadTypeDecl980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rulePayloadTypeDecl1017 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_rulePayloadTypeDecl1029 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl1046 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_rulePayloadTypeDecl1063 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1080 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_rulePayloadTypeDecl1097 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulePayloadTypeDecl1114 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rulePayloadTypeDecl1131 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadTypeDecl1148 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_rulePayloadTypeDecl1165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature1203 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessageSignature1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessageSignature1255 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleMessageSignature1272 = new BitSet(new long[]{0x0000000000400010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1294 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleMessageSignature1307 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulePayloadElement_in_ruleMessageSignature1328 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleMessageSignature1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_entryRulePayloadElement1380 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadElement1390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1433 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_rulePayloadElement1450 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulePayloadElement1469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl1510 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolDecl1520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleGlobalProtocolDecl1557 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleGlobalProtocolDecl1569 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1586 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1604 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1625 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1638 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleGlobalProtocolDecl1659 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1673 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1687 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1708 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1721 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleGlobalProtocolDecl1742 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1756 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalProtocolDecl1778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleGlobalProtocolDecl1797 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalProtocolDecl1814 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalProtocolDecl1832 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1853 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1866 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalProtocolDecl1887 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalProtocolDecl1901 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalProtocolDecl1915 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1936 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalProtocolDecl1949 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalProtocolDecl1970 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalProtocolDecl1984 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalProtocolDecl1996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl2034 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleDecl2044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleRoleDecl2081 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2098 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleDecl2116 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleDecl2133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl2176 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameterDecl2186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleParameterDecl2224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2241 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2259 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleParameterDecl2303 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2320 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleParameterDecl2338 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleParameterDecl2355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation2399 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleInstantiation2409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2451 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleRoleInstantiation2469 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleRoleInstantiation2486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_entryRuleArgument2529 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArgument2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleArgument2586 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2599 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2648 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleArgument2666 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock2727 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolBlock2737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleGlobalProtocolBlock2783 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_ruleGlobalProtocolBlock2804 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalProtocolBlock2817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction2853 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInteraction2863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_ruleGlobalInteraction2910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_ruleGlobalInteraction2937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_ruleGlobalInteraction2964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_ruleGlobalInteraction2991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_ruleGlobalInteraction3018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_ruleGlobalInteraction3045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_ruleGlobalInteraction3072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer3107 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalMessageTransfer3117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalMessageTransfer3163 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleGlobalMessageTransfer3175 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3192 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleGlobalMessageTransfer3209 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3226 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalMessageTransfer3244 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalMessageTransfer3261 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalMessageTransfer3280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage3316 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage3326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_ruleMessage3373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMessage3395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice3436 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalChoice3446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleGlobalChoice3483 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleGlobalChoice3495 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalChoice3512 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3538 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleGlobalChoice3551 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalChoice3572 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion3610 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalRecursion3620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleGlobalRecursion3657 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalRecursion3674 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalRecursion3700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue3736 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalContinue3746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleGlobalContinue3783 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalContinue3800 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalContinue3817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel3853 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalParallel3863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleGlobalParallel3900 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3921 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_ruleGlobalParallel3934 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalParallel3955 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible3993 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterruptible4003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleGlobalInterruptible4040 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterruptible4058 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalInterruptible4075 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_ruleGlobalInterruptible4098 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_ruleGlobalInterruptible4110 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleGlobalInterruptible4122 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_ruleGlobalInterruptible4143 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_30_in_ruleGlobalInterruptible4156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt4192 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterrupt4202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4248 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalInterrupt4261 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleGlobalInterrupt4282 = new BitSet(new long[]{0x0000020000200000L});
    public static final BitSet FOLLOW_41_in_ruleGlobalInterrupt4296 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalInterrupt4313 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalInterrupt4330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo4366 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalDo4376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleGlobalDo4413 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4431 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleGlobalDo4448 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleGlobalDo4467 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleGlobalDo4485 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4506 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4519 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleGlobalDo4540 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleGlobalDo4554 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleGlobalDo4568 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4589 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleGlobalDo4602 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleGlobalDo4623 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleGlobalDo4637 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleGlobalDo4649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolDecl_in_entryRuleLocalProtocolDecl4685 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalProtocolDecl4695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleLocalProtocolDecl4732 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleLocalProtocolDecl4744 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4761 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleLocalProtocolDecl4778 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl4795 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalProtocolDecl4813 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4834 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl4847 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_ruleLocalProtocolDecl4868 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalProtocolDecl4882 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalProtocolDecl4896 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4917 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl4930 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_ruleLocalProtocolDecl4951 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalProtocolDecl4965 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalProtocolDecl4987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleLocalProtocolDecl5006 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalProtocolDecl5023 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalProtocolDecl5041 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5062 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl5075 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalProtocolDecl5096 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalProtocolDecl5110 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalProtocolDecl5124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5145 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalProtocolDecl5158 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalProtocolDecl5179 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalProtocolDecl5193 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalProtocolDecl5205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_entryRuleLocalProtocolBlock5243 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalProtocolBlock5253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleLocalProtocolBlock5299 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_ruleLlobalInteraction_in_ruleLocalProtocolBlock5320 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_30_in_ruleLocalProtocolBlock5333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLlobalInteraction_in_entryRuleLlobalInteraction5369 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLlobalInteraction5379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalSend_in_ruleLlobalInteraction5426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalReceive_in_ruleLlobalInteraction5453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalChoice_in_ruleLlobalInteraction5480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalParallel_in_ruleLlobalInteraction5507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalRecursion_in_ruleLlobalInteraction5534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalContinue_in_ruleLlobalInteraction5561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelocalinterruptible_in_ruleLlobalInteraction5588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalDo_in_ruleLlobalInteraction5615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalSend_in_entryRuleLocalSend5650 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalSend5660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalSend5706 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleLocalSend5718 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalSend5735 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleLocalSend5753 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalSend5770 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleLocalSend5789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalReceive_in_entryRuleLocalReceive5825 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalReceive5835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalReceive5881 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleLocalReceive5893 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalReceive5910 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalReceive5927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalChoice_in_entryRuleLocalChoice5963 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalChoice5973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleLocalChoice6010 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruleLocalChoice6022 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalChoice6039 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6065 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_ruleLocalChoice6078 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalChoice6099 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleLocalRecursion_in_entryRuleLocalRecursion6137 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalRecursion6147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleLocalRecursion6184 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalRecursion6201 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalRecursion6227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalContinue_in_entryRuleLocalContinue6263 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalContinue6273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleLocalContinue6310 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalContinue6327 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalContinue6344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalParallel_in_entryRuleLocalParallel6380 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalParallel6390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleLocalParallel6427 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6448 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_38_in_ruleLocalParallel6461 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_ruleLocalParallel6482 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_rulelocalinterruptible_in_entryRulelocalinterruptible6520 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulelocalinterruptible6530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rulelocalinterruptible6567 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_RULE_ID_in_rulelocalinterruptible6585 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_rulelocalinterruptible6602 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ruleLocalProtocolBlock_in_rulelocalinterruptible6625 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_rulelocalinterruptible6637 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_rulelocalinterruptible6649 = new BitSet(new long[]{0x0000300040000000L});
    public static final BitSet FOLLOW_ruleLocalThrow_in_rulelocalinterruptible6670 = new BitSet(new long[]{0x0000200040000000L});
    public static final BitSet FOLLOW_ruleLocalCatch_in_rulelocalinterruptible6692 = new BitSet(new long[]{0x0000200040000000L});
    public static final BitSet FOLLOW_30_in_rulelocalinterruptible6705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalThrow_in_entryRuleLocalThrow6741 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalThrow6751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleLocalThrow6788 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalThrow6809 = new BitSet(new long[]{0x0000000080200000L});
    public static final BitSet FOLLOW_21_in_ruleLocalThrow6822 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalThrow6843 = new BitSet(new long[]{0x0000000080200000L});
    public static final BitSet FOLLOW_31_in_ruleLocalThrow6857 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalThrow6874 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_21_in_ruleLocalThrow6892 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalThrow6909 = new BitSet(new long[]{0x0000000000201000L});
    public static final BitSet FOLLOW_12_in_ruleLocalThrow6928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalCatch_in_entryRuleLocalCatch6964 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalCatch6974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleLocalCatch7011 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalCatch7032 = new BitSet(new long[]{0x0000000000210000L});
    public static final BitSet FOLLOW_21_in_ruleLocalCatch7045 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleMessage_in_ruleLocalCatch7066 = new BitSet(new long[]{0x0000000000210000L});
    public static final BitSet FOLLOW_16_in_ruleLocalCatch7080 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalCatch7097 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalCatch7114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalDo_in_entryRuleLocalDo7150 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalDo7160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleLocalDo7197 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalDo7215 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleLocalDo7232 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalDo7251 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_18_in_ruleLocalDo7269 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalDo7290 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_21_in_ruleLocalDo7303 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleLocalDo7324 = new BitSet(new long[]{0x0000000000280000L});
    public static final BitSet FOLLOW_19_in_ruleLocalDo7338 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleLocalDo7352 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7373 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_ruleLocalDo7386 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_ruleLocalDo7407 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_ruleLocalDo7421 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalDo7433 = new BitSet(new long[]{0x0000000000000002L});

}