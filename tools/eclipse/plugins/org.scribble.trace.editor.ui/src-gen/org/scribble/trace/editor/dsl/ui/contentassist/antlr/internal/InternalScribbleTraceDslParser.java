package org.scribble.trace.editor.dsl.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import org.scribble.trace.editor.dsl.services.ScribbleTraceDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalScribbleTraceDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'trace'", "';'", "'by'", "'shows'", "'.'", "'role'", "'simulating'", "'protocol'", "'as'", "'from'", "'to'", "'('", "')'", "','", "'='"
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
    public String getGrammarFileName() { return "../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g"; }


     
     	private ScribbleTraceDslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(ScribbleTraceDslGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleTrace"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:60:1: entryRuleTrace : ruleTrace EOF ;
    public final void entryRuleTrace() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:61:1: ( ruleTrace EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:62:1: ruleTrace EOF
            {
             before(grammarAccess.getTraceRule()); 
            pushFollow(FOLLOW_ruleTrace_in_entryRuleTrace61);
            ruleTrace();

            state._fsp--;

             after(grammarAccess.getTraceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTrace68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTrace"


    // $ANTLR start "ruleTrace"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:69:1: ruleTrace : ( ( rule__Trace__Group__0 ) ) ;
    public final void ruleTrace() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:73:2: ( ( ( rule__Trace__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:74:1: ( ( rule__Trace__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:74:1: ( ( rule__Trace__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:75:1: ( rule__Trace__Group__0 )
            {
             before(grammarAccess.getTraceAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:76:1: ( rule__Trace__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:76:2: rule__Trace__Group__0
            {
            pushFollow(FOLLOW_rule__Trace__Group__0_in_ruleTrace94);
            rule__Trace__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTraceAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTrace"


    // $ANTLR start "entryRuleSentence"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:88:1: entryRuleSentence : ruleSentence EOF ;
    public final void entryRuleSentence() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:89:1: ( ruleSentence EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:90:1: ruleSentence EOF
            {
             before(grammarAccess.getSentenceRule()); 
            pushFollow(FOLLOW_ruleSentence_in_entryRuleSentence121);
            ruleSentence();

            state._fsp--;

             after(grammarAccess.getSentenceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSentence128); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSentence"


    // $ANTLR start "ruleSentence"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:97:1: ruleSentence : ( ( ( RULE_ID ) ) ( ( RULE_ID )* ) ) ;
    public final void ruleSentence() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:101:2: ( ( ( ( RULE_ID ) ) ( ( RULE_ID )* ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:102:1: ( ( ( RULE_ID ) ) ( ( RULE_ID )* ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:102:1: ( ( ( RULE_ID ) ) ( ( RULE_ID )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:103:1: ( ( RULE_ID ) ) ( ( RULE_ID )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:103:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:104:1: ( RULE_ID )
            {
             before(grammarAccess.getSentenceAccess().getIDTerminalRuleCall()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:105:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:105:3: RULE_ID
            {
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSentence157); 

            }

             after(grammarAccess.getSentenceAccess().getIDTerminalRuleCall()); 

            }

            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:108:1: ( ( RULE_ID )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:109:1: ( RULE_ID )*
            {
             before(grammarAccess.getSentenceAccess().getIDTerminalRuleCall()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:110:1: ( RULE_ID )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:110:3: RULE_ID
            	    {
            	    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleSentence170); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getSentenceAccess().getIDTerminalRuleCall()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSentence"


    // $ANTLR start "entryRuleTracedefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:123:1: entryRuleTracedefn : ruleTracedefn EOF ;
    public final void entryRuleTracedefn() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:124:1: ( ruleTracedefn EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:125:1: ruleTracedefn EOF
            {
             before(grammarAccess.getTracedefnRule()); 
            pushFollow(FOLLOW_ruleTracedefn_in_entryRuleTracedefn200);
            ruleTracedefn();

            state._fsp--;

             after(grammarAccess.getTracedefnRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTracedefn207); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTracedefn"


    // $ANTLR start "ruleTracedefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:132:1: ruleTracedefn : ( ( rule__Tracedefn__Group__0 ) ) ;
    public final void ruleTracedefn() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:136:2: ( ( ( rule__Tracedefn__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:137:1: ( ( rule__Tracedefn__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:137:1: ( ( rule__Tracedefn__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:138:1: ( rule__Tracedefn__Group__0 )
            {
             before(grammarAccess.getTracedefnAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:139:1: ( rule__Tracedefn__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:139:2: rule__Tracedefn__Group__0
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group__0_in_ruleTracedefn233);
            rule__Tracedefn__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTracedefnAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTracedefn"


    // $ANTLR start "entryRuleModule"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:151:1: entryRuleModule : ruleModule EOF ;
    public final void entryRuleModule() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:152:1: ( ruleModule EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:153:1: ruleModule EOF
            {
             before(grammarAccess.getModuleRule()); 
            pushFollow(FOLLOW_ruleModule_in_entryRuleModule260);
            ruleModule();

            state._fsp--;

             after(grammarAccess.getModuleRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModule267); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModule"


    // $ANTLR start "ruleModule"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:160:1: ruleModule : ( ( rule__Module__Group__0 ) ) ;
    public final void ruleModule() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:164:2: ( ( ( rule__Module__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:165:1: ( ( rule__Module__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:165:1: ( ( rule__Module__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:166:1: ( rule__Module__Group__0 )
            {
             before(grammarAccess.getModuleAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:167:1: ( rule__Module__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:167:2: rule__Module__Group__0
            {
            pushFollow(FOLLOW_rule__Module__Group__0_in_ruleModule293);
            rule__Module__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModule"


    // $ANTLR start "entryRuleRoledefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:179:1: entryRuleRoledefn : ruleRoledefn EOF ;
    public final void entryRuleRoledefn() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:180:1: ( ruleRoledefn EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:181:1: ruleRoledefn EOF
            {
             before(grammarAccess.getRoledefnRule()); 
            pushFollow(FOLLOW_ruleRoledefn_in_entryRuleRoledefn320);
            ruleRoledefn();

            state._fsp--;

             after(grammarAccess.getRoledefnRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoledefn327); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRoledefn"


    // $ANTLR start "ruleRoledefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:188:1: ruleRoledefn : ( ( rule__Roledefn__Group__0 ) ) ;
    public final void ruleRoledefn() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:192:2: ( ( ( rule__Roledefn__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:193:1: ( ( rule__Roledefn__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:193:1: ( ( rule__Roledefn__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:194:1: ( rule__Roledefn__Group__0 )
            {
             before(grammarAccess.getRoledefnAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:195:1: ( rule__Roledefn__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:195:2: rule__Roledefn__Group__0
            {
            pushFollow(FOLLOW_rule__Roledefn__Group__0_in_ruleRoledefn353);
            rule__Roledefn__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRoledefnAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRoledefn"


    // $ANTLR start "entryRuleStepdefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:207:1: entryRuleStepdefn : ruleStepdefn EOF ;
    public final void entryRuleStepdefn() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:208:1: ( ruleStepdefn EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:209:1: ruleStepdefn EOF
            {
             before(grammarAccess.getStepdefnRule()); 
            pushFollow(FOLLOW_ruleStepdefn_in_entryRuleStepdefn380);
            ruleStepdefn();

            state._fsp--;

             after(grammarAccess.getStepdefnRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStepdefn387); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStepdefn"


    // $ANTLR start "ruleStepdefn"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:216:1: ruleStepdefn : ( ruleMessagetransfer ) ;
    public final void ruleStepdefn() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:220:2: ( ( ruleMessagetransfer ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:221:1: ( ruleMessagetransfer )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:221:1: ( ruleMessagetransfer )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:222:1: ruleMessagetransfer
            {
             before(grammarAccess.getStepdefnAccess().getMessagetransferParserRuleCall()); 
            pushFollow(FOLLOW_ruleMessagetransfer_in_ruleStepdefn413);
            ruleMessagetransfer();

            state._fsp--;

             after(grammarAccess.getStepdefnAccess().getMessagetransferParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStepdefn"


    // $ANTLR start "entryRuleMessagetransfer"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:235:1: entryRuleMessagetransfer : ruleMessagetransfer EOF ;
    public final void entryRuleMessagetransfer() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:236:1: ( ruleMessagetransfer EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:237:1: ruleMessagetransfer EOF
            {
             before(grammarAccess.getMessagetransferRule()); 
            pushFollow(FOLLOW_ruleMessagetransfer_in_entryRuleMessagetransfer439);
            ruleMessagetransfer();

            state._fsp--;

             after(grammarAccess.getMessagetransferRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessagetransfer446); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMessagetransfer"


    // $ANTLR start "ruleMessagetransfer"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:244:1: ruleMessagetransfer : ( ( rule__Messagetransfer__Group__0 ) ) ;
    public final void ruleMessagetransfer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:248:2: ( ( ( rule__Messagetransfer__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:249:1: ( ( rule__Messagetransfer__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:249:1: ( ( rule__Messagetransfer__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:250:1: ( rule__Messagetransfer__Group__0 )
            {
             before(grammarAccess.getMessagetransferAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:251:1: ( rule__Messagetransfer__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:251:2: rule__Messagetransfer__Group__0
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__0_in_ruleMessagetransfer472);
            rule__Messagetransfer__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMessagetransferAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMessagetransfer"


    // $ANTLR start "entryRuleParameter"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:263:1: entryRuleParameter : ruleParameter EOF ;
    public final void entryRuleParameter() throws RecognitionException {
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:264:1: ( ruleParameter EOF )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:265:1: ruleParameter EOF
            {
             before(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_ruleParameter_in_entryRuleParameter499);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getParameterRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameter506); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:272:1: ruleParameter : ( ( rule__Parameter__Group__0 ) ) ;
    public final void ruleParameter() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:276:2: ( ( ( rule__Parameter__Group__0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:277:1: ( ( rule__Parameter__Group__0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:277:1: ( ( rule__Parameter__Group__0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:278:1: ( rule__Parameter__Group__0 )
            {
             before(grammarAccess.getParameterAccess().getGroup()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:279:1: ( rule__Parameter__Group__0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:279:2: rule__Parameter__Group__0
            {
            pushFollow(FOLLOW_rule__Parameter__Group__0_in_ruleParameter532);
            rule__Parameter__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "rule__Trace__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:293:1: rule__Trace__Group__0 : rule__Trace__Group__0__Impl rule__Trace__Group__1 ;
    public final void rule__Trace__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:297:1: ( rule__Trace__Group__0__Impl rule__Trace__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:298:2: rule__Trace__Group__0__Impl rule__Trace__Group__1
            {
            pushFollow(FOLLOW_rule__Trace__Group__0__Impl_in_rule__Trace__Group__0566);
            rule__Trace__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Trace__Group__1_in_rule__Trace__Group__0569);
            rule__Trace__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__0"


    // $ANTLR start "rule__Trace__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:305:1: rule__Trace__Group__0__Impl : ( () ) ;
    public final void rule__Trace__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:309:1: ( ( () ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:310:1: ( () )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:310:1: ( () )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:311:1: ()
            {
             before(grammarAccess.getTraceAccess().getTraceAction_0()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:312:1: ()
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:314:1: 
            {
            }

             after(grammarAccess.getTraceAccess().getTraceAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__0__Impl"


    // $ANTLR start "rule__Trace__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:324:1: rule__Trace__Group__1 : rule__Trace__Group__1__Impl rule__Trace__Group__2 ;
    public final void rule__Trace__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:328:1: ( rule__Trace__Group__1__Impl rule__Trace__Group__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:329:2: rule__Trace__Group__1__Impl rule__Trace__Group__2
            {
            pushFollow(FOLLOW_rule__Trace__Group__1__Impl_in_rule__Trace__Group__1627);
            rule__Trace__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Trace__Group__2_in_rule__Trace__Group__1630);
            rule__Trace__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__1"


    // $ANTLR start "rule__Trace__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:336:1: rule__Trace__Group__1__Impl : ( ruleTracedefn ) ;
    public final void rule__Trace__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:340:1: ( ( ruleTracedefn ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:341:1: ( ruleTracedefn )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:341:1: ( ruleTracedefn )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:342:1: ruleTracedefn
            {
             before(grammarAccess.getTraceAccess().getTracedefnParserRuleCall_1()); 
            pushFollow(FOLLOW_ruleTracedefn_in_rule__Trace__Group__1__Impl657);
            ruleTracedefn();

            state._fsp--;

             after(grammarAccess.getTraceAccess().getTracedefnParserRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__1__Impl"


    // $ANTLR start "rule__Trace__Group__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:353:1: rule__Trace__Group__2 : rule__Trace__Group__2__Impl rule__Trace__Group__3 ;
    public final void rule__Trace__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:357:1: ( rule__Trace__Group__2__Impl rule__Trace__Group__3 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:358:2: rule__Trace__Group__2__Impl rule__Trace__Group__3
            {
            pushFollow(FOLLOW_rule__Trace__Group__2__Impl_in_rule__Trace__Group__2686);
            rule__Trace__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Trace__Group__3_in_rule__Trace__Group__2689);
            rule__Trace__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__2"


    // $ANTLR start "rule__Trace__Group__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:365:1: rule__Trace__Group__2__Impl : ( ( rule__Trace__RolesAssignment_2 )* ) ;
    public final void rule__Trace__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:369:1: ( ( ( rule__Trace__RolesAssignment_2 )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:370:1: ( ( rule__Trace__RolesAssignment_2 )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:370:1: ( ( rule__Trace__RolesAssignment_2 )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:371:1: ( rule__Trace__RolesAssignment_2 )*
            {
             before(grammarAccess.getTraceAccess().getRolesAssignment_2()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:372:1: ( rule__Trace__RolesAssignment_2 )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==16) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:372:2: rule__Trace__RolesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Trace__RolesAssignment_2_in_rule__Trace__Group__2__Impl716);
            	    rule__Trace__RolesAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

             after(grammarAccess.getTraceAccess().getRolesAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__2__Impl"


    // $ANTLR start "rule__Trace__Group__3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:382:1: rule__Trace__Group__3 : rule__Trace__Group__3__Impl ;
    public final void rule__Trace__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:386:1: ( rule__Trace__Group__3__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:387:2: rule__Trace__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Trace__Group__3__Impl_in_rule__Trace__Group__3747);
            rule__Trace__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__3"


    // $ANTLR start "rule__Trace__Group__3__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:393:1: rule__Trace__Group__3__Impl : ( ( rule__Trace__StepsAssignment_3 )* ) ;
    public final void rule__Trace__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:397:1: ( ( ( rule__Trace__StepsAssignment_3 )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:398:1: ( ( rule__Trace__StepsAssignment_3 )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:398:1: ( ( rule__Trace__StepsAssignment_3 )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:399:1: ( rule__Trace__StepsAssignment_3 )*
            {
             before(grammarAccess.getTraceAccess().getStepsAssignment_3()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:400:1: ( rule__Trace__StepsAssignment_3 )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==RULE_ID) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:400:2: rule__Trace__StepsAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__Trace__StepsAssignment_3_in_rule__Trace__Group__3__Impl774);
            	    rule__Trace__StepsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

             after(grammarAccess.getTraceAccess().getStepsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__Group__3__Impl"


    // $ANTLR start "rule__Tracedefn__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:418:1: rule__Tracedefn__Group__0 : rule__Tracedefn__Group__0__Impl rule__Tracedefn__Group__1 ;
    public final void rule__Tracedefn__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:422:1: ( rule__Tracedefn__Group__0__Impl rule__Tracedefn__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:423:2: rule__Tracedefn__Group__0__Impl rule__Tracedefn__Group__1
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group__0__Impl_in_rule__Tracedefn__Group__0813);
            rule__Tracedefn__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group__1_in_rule__Tracedefn__Group__0816);
            rule__Tracedefn__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__0"


    // $ANTLR start "rule__Tracedefn__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:430:1: rule__Tracedefn__Group__0__Impl : ( 'trace' ) ;
    public final void rule__Tracedefn__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:434:1: ( ( 'trace' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:435:1: ( 'trace' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:435:1: ( 'trace' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:436:1: 'trace'
            {
             before(grammarAccess.getTracedefnAccess().getTraceKeyword_0()); 
            match(input,11,FOLLOW_11_in_rule__Tracedefn__Group__0__Impl844); 
             after(grammarAccess.getTracedefnAccess().getTraceKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__0__Impl"


    // $ANTLR start "rule__Tracedefn__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:449:1: rule__Tracedefn__Group__1 : rule__Tracedefn__Group__1__Impl rule__Tracedefn__Group__2 ;
    public final void rule__Tracedefn__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:453:1: ( rule__Tracedefn__Group__1__Impl rule__Tracedefn__Group__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:454:2: rule__Tracedefn__Group__1__Impl rule__Tracedefn__Group__2
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group__1__Impl_in_rule__Tracedefn__Group__1875);
            rule__Tracedefn__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group__2_in_rule__Tracedefn__Group__1878);
            rule__Tracedefn__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__1"


    // $ANTLR start "rule__Tracedefn__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:461:1: rule__Tracedefn__Group__1__Impl : ( RULE_ID ) ;
    public final void rule__Tracedefn__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:465:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:466:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:466:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:467:1: RULE_ID
            {
             before(grammarAccess.getTracedefnAccess().getIDTerminalRuleCall_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Tracedefn__Group__1__Impl905); 
             after(grammarAccess.getTracedefnAccess().getIDTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__1__Impl"


    // $ANTLR start "rule__Tracedefn__Group__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:478:1: rule__Tracedefn__Group__2 : rule__Tracedefn__Group__2__Impl rule__Tracedefn__Group__3 ;
    public final void rule__Tracedefn__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:482:1: ( rule__Tracedefn__Group__2__Impl rule__Tracedefn__Group__3 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:483:2: rule__Tracedefn__Group__2__Impl rule__Tracedefn__Group__3
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group__2__Impl_in_rule__Tracedefn__Group__2934);
            rule__Tracedefn__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group__3_in_rule__Tracedefn__Group__2937);
            rule__Tracedefn__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__2"


    // $ANTLR start "rule__Tracedefn__Group__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:490:1: rule__Tracedefn__Group__2__Impl : ( ( rule__Tracedefn__Group_2__0 )? ) ;
    public final void rule__Tracedefn__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:494:1: ( ( ( rule__Tracedefn__Group_2__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:495:1: ( ( rule__Tracedefn__Group_2__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:495:1: ( ( rule__Tracedefn__Group_2__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:496:1: ( rule__Tracedefn__Group_2__0 )?
            {
             before(grammarAccess.getTracedefnAccess().getGroup_2()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:497:1: ( rule__Tracedefn__Group_2__0 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==13) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:497:2: rule__Tracedefn__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Tracedefn__Group_2__0_in_rule__Tracedefn__Group__2__Impl964);
                    rule__Tracedefn__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTracedefnAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__2__Impl"


    // $ANTLR start "rule__Tracedefn__Group__3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:507:1: rule__Tracedefn__Group__3 : rule__Tracedefn__Group__3__Impl ;
    public final void rule__Tracedefn__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:511:1: ( rule__Tracedefn__Group__3__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:512:2: rule__Tracedefn__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group__3__Impl_in_rule__Tracedefn__Group__3995);
            rule__Tracedefn__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__3"


    // $ANTLR start "rule__Tracedefn__Group__3__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:518:1: rule__Tracedefn__Group__3__Impl : ( ';' ) ;
    public final void rule__Tracedefn__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:522:1: ( ( ';' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:523:1: ( ';' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:523:1: ( ';' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:524:1: ';'
            {
             before(grammarAccess.getTracedefnAccess().getSemicolonKeyword_3()); 
            match(input,12,FOLLOW_12_in_rule__Tracedefn__Group__3__Impl1023); 
             after(grammarAccess.getTracedefnAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group__3__Impl"


    // $ANTLR start "rule__Tracedefn__Group_2__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:545:1: rule__Tracedefn__Group_2__0 : rule__Tracedefn__Group_2__0__Impl rule__Tracedefn__Group_2__1 ;
    public final void rule__Tracedefn__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:549:1: ( rule__Tracedefn__Group_2__0__Impl rule__Tracedefn__Group_2__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:550:2: rule__Tracedefn__Group_2__0__Impl rule__Tracedefn__Group_2__1
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group_2__0__Impl_in_rule__Tracedefn__Group_2__01062);
            rule__Tracedefn__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group_2__1_in_rule__Tracedefn__Group_2__01065);
            rule__Tracedefn__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__0"


    // $ANTLR start "rule__Tracedefn__Group_2__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:557:1: rule__Tracedefn__Group_2__0__Impl : ( 'by' ) ;
    public final void rule__Tracedefn__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:561:1: ( ( 'by' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:562:1: ( 'by' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:562:1: ( 'by' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:563:1: 'by'
            {
             before(grammarAccess.getTracedefnAccess().getByKeyword_2_0()); 
            match(input,13,FOLLOW_13_in_rule__Tracedefn__Group_2__0__Impl1093); 
             after(grammarAccess.getTracedefnAccess().getByKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__0__Impl"


    // $ANTLR start "rule__Tracedefn__Group_2__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:576:1: rule__Tracedefn__Group_2__1 : rule__Tracedefn__Group_2__1__Impl rule__Tracedefn__Group_2__2 ;
    public final void rule__Tracedefn__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:580:1: ( rule__Tracedefn__Group_2__1__Impl rule__Tracedefn__Group_2__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:581:2: rule__Tracedefn__Group_2__1__Impl rule__Tracedefn__Group_2__2
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group_2__1__Impl_in_rule__Tracedefn__Group_2__11124);
            rule__Tracedefn__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group_2__2_in_rule__Tracedefn__Group_2__11127);
            rule__Tracedefn__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__1"


    // $ANTLR start "rule__Tracedefn__Group_2__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:588:1: rule__Tracedefn__Group_2__1__Impl : ( ruleSentence ) ;
    public final void rule__Tracedefn__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:592:1: ( ( ruleSentence ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:593:1: ( ruleSentence )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:593:1: ( ruleSentence )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:594:1: ruleSentence
            {
             before(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_1()); 
            pushFollow(FOLLOW_ruleSentence_in_rule__Tracedefn__Group_2__1__Impl1154);
            ruleSentence();

            state._fsp--;

             after(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__1__Impl"


    // $ANTLR start "rule__Tracedefn__Group_2__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:605:1: rule__Tracedefn__Group_2__2 : rule__Tracedefn__Group_2__2__Impl ;
    public final void rule__Tracedefn__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:609:1: ( rule__Tracedefn__Group_2__2__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:610:2: rule__Tracedefn__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group_2__2__Impl_in_rule__Tracedefn__Group_2__21183);
            rule__Tracedefn__Group_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__2"


    // $ANTLR start "rule__Tracedefn__Group_2__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:616:1: rule__Tracedefn__Group_2__2__Impl : ( ( rule__Tracedefn__Group_2_2__0 )? ) ;
    public final void rule__Tracedefn__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:620:1: ( ( ( rule__Tracedefn__Group_2_2__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:621:1: ( ( rule__Tracedefn__Group_2_2__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:621:1: ( ( rule__Tracedefn__Group_2_2__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:622:1: ( rule__Tracedefn__Group_2_2__0 )?
            {
             before(grammarAccess.getTracedefnAccess().getGroup_2_2()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:623:1: ( rule__Tracedefn__Group_2_2__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==14) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:623:2: rule__Tracedefn__Group_2_2__0
                    {
                    pushFollow(FOLLOW_rule__Tracedefn__Group_2_2__0_in_rule__Tracedefn__Group_2__2__Impl1210);
                    rule__Tracedefn__Group_2_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTracedefnAccess().getGroup_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2__2__Impl"


    // $ANTLR start "rule__Tracedefn__Group_2_2__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:639:1: rule__Tracedefn__Group_2_2__0 : rule__Tracedefn__Group_2_2__0__Impl rule__Tracedefn__Group_2_2__1 ;
    public final void rule__Tracedefn__Group_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:643:1: ( rule__Tracedefn__Group_2_2__0__Impl rule__Tracedefn__Group_2_2__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:644:2: rule__Tracedefn__Group_2_2__0__Impl rule__Tracedefn__Group_2_2__1
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group_2_2__0__Impl_in_rule__Tracedefn__Group_2_2__01247);
            rule__Tracedefn__Group_2_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Tracedefn__Group_2_2__1_in_rule__Tracedefn__Group_2_2__01250);
            rule__Tracedefn__Group_2_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2_2__0"


    // $ANTLR start "rule__Tracedefn__Group_2_2__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:651:1: rule__Tracedefn__Group_2_2__0__Impl : ( 'shows' ) ;
    public final void rule__Tracedefn__Group_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:655:1: ( ( 'shows' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:656:1: ( 'shows' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:656:1: ( 'shows' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:657:1: 'shows'
            {
             before(grammarAccess.getTracedefnAccess().getShowsKeyword_2_2_0()); 
            match(input,14,FOLLOW_14_in_rule__Tracedefn__Group_2_2__0__Impl1278); 
             after(grammarAccess.getTracedefnAccess().getShowsKeyword_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2_2__0__Impl"


    // $ANTLR start "rule__Tracedefn__Group_2_2__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:670:1: rule__Tracedefn__Group_2_2__1 : rule__Tracedefn__Group_2_2__1__Impl ;
    public final void rule__Tracedefn__Group_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:674:1: ( rule__Tracedefn__Group_2_2__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:675:2: rule__Tracedefn__Group_2_2__1__Impl
            {
            pushFollow(FOLLOW_rule__Tracedefn__Group_2_2__1__Impl_in_rule__Tracedefn__Group_2_2__11309);
            rule__Tracedefn__Group_2_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2_2__1"


    // $ANTLR start "rule__Tracedefn__Group_2_2__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:681:1: rule__Tracedefn__Group_2_2__1__Impl : ( ruleSentence ) ;
    public final void rule__Tracedefn__Group_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:685:1: ( ( ruleSentence ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:686:1: ( ruleSentence )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:686:1: ( ruleSentence )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:687:1: ruleSentence
            {
             before(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_2_1()); 
            pushFollow(FOLLOW_ruleSentence_in_rule__Tracedefn__Group_2_2__1__Impl1336);
            ruleSentence();

            state._fsp--;

             after(grammarAccess.getTracedefnAccess().getSentenceParserRuleCall_2_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Tracedefn__Group_2_2__1__Impl"


    // $ANTLR start "rule__Module__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:702:1: rule__Module__Group__0 : rule__Module__Group__0__Impl rule__Module__Group__1 ;
    public final void rule__Module__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:706:1: ( rule__Module__Group__0__Impl rule__Module__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:707:2: rule__Module__Group__0__Impl rule__Module__Group__1
            {
            pushFollow(FOLLOW_rule__Module__Group__0__Impl_in_rule__Module__Group__01369);
            rule__Module__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Module__Group__1_in_rule__Module__Group__01372);
            rule__Module__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group__0"


    // $ANTLR start "rule__Module__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:714:1: rule__Module__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__Module__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:718:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:719:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:719:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:720:1: RULE_ID
            {
             before(grammarAccess.getModuleAccess().getIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Module__Group__0__Impl1399); 
             after(grammarAccess.getModuleAccess().getIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group__0__Impl"


    // $ANTLR start "rule__Module__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:731:1: rule__Module__Group__1 : rule__Module__Group__1__Impl ;
    public final void rule__Module__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:735:1: ( rule__Module__Group__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:736:2: rule__Module__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Module__Group__1__Impl_in_rule__Module__Group__11428);
            rule__Module__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group__1"


    // $ANTLR start "rule__Module__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:742:1: rule__Module__Group__1__Impl : ( ( rule__Module__Group_1__0 )* ) ;
    public final void rule__Module__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:746:1: ( ( ( rule__Module__Group_1__0 )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:747:1: ( ( rule__Module__Group_1__0 )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:747:1: ( ( rule__Module__Group_1__0 )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:748:1: ( rule__Module__Group_1__0 )*
            {
             before(grammarAccess.getModuleAccess().getGroup_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:749:1: ( rule__Module__Group_1__0 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==15) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:749:2: rule__Module__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Module__Group_1__0_in_rule__Module__Group__1__Impl1455);
            	    rule__Module__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getModuleAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group__1__Impl"


    // $ANTLR start "rule__Module__Group_1__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:763:1: rule__Module__Group_1__0 : rule__Module__Group_1__0__Impl rule__Module__Group_1__1 ;
    public final void rule__Module__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:767:1: ( rule__Module__Group_1__0__Impl rule__Module__Group_1__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:768:2: rule__Module__Group_1__0__Impl rule__Module__Group_1__1
            {
            pushFollow(FOLLOW_rule__Module__Group_1__0__Impl_in_rule__Module__Group_1__01490);
            rule__Module__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Module__Group_1__1_in_rule__Module__Group_1__01493);
            rule__Module__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group_1__0"


    // $ANTLR start "rule__Module__Group_1__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:775:1: rule__Module__Group_1__0__Impl : ( '.' ) ;
    public final void rule__Module__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:779:1: ( ( '.' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:780:1: ( '.' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:780:1: ( '.' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:781:1: '.'
            {
             before(grammarAccess.getModuleAccess().getFullStopKeyword_1_0()); 
            match(input,15,FOLLOW_15_in_rule__Module__Group_1__0__Impl1521); 
             after(grammarAccess.getModuleAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group_1__0__Impl"


    // $ANTLR start "rule__Module__Group_1__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:794:1: rule__Module__Group_1__1 : rule__Module__Group_1__1__Impl ;
    public final void rule__Module__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:798:1: ( rule__Module__Group_1__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:799:2: rule__Module__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Module__Group_1__1__Impl_in_rule__Module__Group_1__11552);
            rule__Module__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group_1__1"


    // $ANTLR start "rule__Module__Group_1__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:805:1: rule__Module__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__Module__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:809:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:810:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:810:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:811:1: RULE_ID
            {
             before(grammarAccess.getModuleAccess().getIDTerminalRuleCall_1_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Module__Group_1__1__Impl1579); 
             after(grammarAccess.getModuleAccess().getIDTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Module__Group_1__1__Impl"


    // $ANTLR start "rule__Roledefn__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:826:1: rule__Roledefn__Group__0 : rule__Roledefn__Group__0__Impl rule__Roledefn__Group__1 ;
    public final void rule__Roledefn__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:830:1: ( rule__Roledefn__Group__0__Impl rule__Roledefn__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:831:2: rule__Roledefn__Group__0__Impl rule__Roledefn__Group__1
            {
            pushFollow(FOLLOW_rule__Roledefn__Group__0__Impl_in_rule__Roledefn__Group__01612);
            rule__Roledefn__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group__1_in_rule__Roledefn__Group__01615);
            rule__Roledefn__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__0"


    // $ANTLR start "rule__Roledefn__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:838:1: rule__Roledefn__Group__0__Impl : ( 'role' ) ;
    public final void rule__Roledefn__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:842:1: ( ( 'role' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:843:1: ( 'role' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:843:1: ( 'role' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:844:1: 'role'
            {
             before(grammarAccess.getRoledefnAccess().getRoleKeyword_0()); 
            match(input,16,FOLLOW_16_in_rule__Roledefn__Group__0__Impl1643); 
             after(grammarAccess.getRoledefnAccess().getRoleKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__0__Impl"


    // $ANTLR start "rule__Roledefn__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:857:1: rule__Roledefn__Group__1 : rule__Roledefn__Group__1__Impl rule__Roledefn__Group__2 ;
    public final void rule__Roledefn__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:861:1: ( rule__Roledefn__Group__1__Impl rule__Roledefn__Group__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:862:2: rule__Roledefn__Group__1__Impl rule__Roledefn__Group__2
            {
            pushFollow(FOLLOW_rule__Roledefn__Group__1__Impl_in_rule__Roledefn__Group__11674);
            rule__Roledefn__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group__2_in_rule__Roledefn__Group__11677);
            rule__Roledefn__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__1"


    // $ANTLR start "rule__Roledefn__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:869:1: rule__Roledefn__Group__1__Impl : ( RULE_ID ) ;
    public final void rule__Roledefn__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:873:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:874:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:874:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:875:1: RULE_ID
            {
             before(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Roledefn__Group__1__Impl1704); 
             after(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__1__Impl"


    // $ANTLR start "rule__Roledefn__Group__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:886:1: rule__Roledefn__Group__2 : rule__Roledefn__Group__2__Impl rule__Roledefn__Group__3 ;
    public final void rule__Roledefn__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:890:1: ( rule__Roledefn__Group__2__Impl rule__Roledefn__Group__3 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:891:2: rule__Roledefn__Group__2__Impl rule__Roledefn__Group__3
            {
            pushFollow(FOLLOW_rule__Roledefn__Group__2__Impl_in_rule__Roledefn__Group__21733);
            rule__Roledefn__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group__3_in_rule__Roledefn__Group__21736);
            rule__Roledefn__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__2"


    // $ANTLR start "rule__Roledefn__Group__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:898:1: rule__Roledefn__Group__2__Impl : ( ( rule__Roledefn__Group_2__0 )? ) ;
    public final void rule__Roledefn__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:902:1: ( ( ( rule__Roledefn__Group_2__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:903:1: ( ( rule__Roledefn__Group_2__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:903:1: ( ( rule__Roledefn__Group_2__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:904:1: ( rule__Roledefn__Group_2__0 )?
            {
             before(grammarAccess.getRoledefnAccess().getGroup_2()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:905:1: ( rule__Roledefn__Group_2__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==17) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:905:2: rule__Roledefn__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Roledefn__Group_2__0_in_rule__Roledefn__Group__2__Impl1763);
                    rule__Roledefn__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRoledefnAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__2__Impl"


    // $ANTLR start "rule__Roledefn__Group__3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:915:1: rule__Roledefn__Group__3 : rule__Roledefn__Group__3__Impl ;
    public final void rule__Roledefn__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:919:1: ( rule__Roledefn__Group__3__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:920:2: rule__Roledefn__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Roledefn__Group__3__Impl_in_rule__Roledefn__Group__31794);
            rule__Roledefn__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__3"


    // $ANTLR start "rule__Roledefn__Group__3__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:926:1: rule__Roledefn__Group__3__Impl : ( ';' ) ;
    public final void rule__Roledefn__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:930:1: ( ( ';' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:931:1: ( ';' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:931:1: ( ';' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:932:1: ';'
            {
             before(grammarAccess.getRoledefnAccess().getSemicolonKeyword_3()); 
            match(input,12,FOLLOW_12_in_rule__Roledefn__Group__3__Impl1822); 
             after(grammarAccess.getRoledefnAccess().getSemicolonKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group__3__Impl"


    // $ANTLR start "rule__Roledefn__Group_2__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:953:1: rule__Roledefn__Group_2__0 : rule__Roledefn__Group_2__0__Impl rule__Roledefn__Group_2__1 ;
    public final void rule__Roledefn__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:957:1: ( rule__Roledefn__Group_2__0__Impl rule__Roledefn__Group_2__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:958:2: rule__Roledefn__Group_2__0__Impl rule__Roledefn__Group_2__1
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2__0__Impl_in_rule__Roledefn__Group_2__01861);
            rule__Roledefn__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group_2__1_in_rule__Roledefn__Group_2__01864);
            rule__Roledefn__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__0"


    // $ANTLR start "rule__Roledefn__Group_2__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:965:1: rule__Roledefn__Group_2__0__Impl : ( 'simulating' ) ;
    public final void rule__Roledefn__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:969:1: ( ( 'simulating' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:970:1: ( 'simulating' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:970:1: ( 'simulating' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:971:1: 'simulating'
            {
             before(grammarAccess.getRoledefnAccess().getSimulatingKeyword_2_0()); 
            match(input,17,FOLLOW_17_in_rule__Roledefn__Group_2__0__Impl1892); 
             after(grammarAccess.getRoledefnAccess().getSimulatingKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__0__Impl"


    // $ANTLR start "rule__Roledefn__Group_2__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:984:1: rule__Roledefn__Group_2__1 : rule__Roledefn__Group_2__1__Impl rule__Roledefn__Group_2__2 ;
    public final void rule__Roledefn__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:988:1: ( rule__Roledefn__Group_2__1__Impl rule__Roledefn__Group_2__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:989:2: rule__Roledefn__Group_2__1__Impl rule__Roledefn__Group_2__2
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2__1__Impl_in_rule__Roledefn__Group_2__11923);
            rule__Roledefn__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group_2__2_in_rule__Roledefn__Group_2__11926);
            rule__Roledefn__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__1"


    // $ANTLR start "rule__Roledefn__Group_2__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:996:1: rule__Roledefn__Group_2__1__Impl : ( ruleModule ) ;
    public final void rule__Roledefn__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1000:1: ( ( ruleModule ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1001:1: ( ruleModule )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1001:1: ( ruleModule )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1002:1: ruleModule
            {
             before(grammarAccess.getRoledefnAccess().getModuleParserRuleCall_2_1()); 
            pushFollow(FOLLOW_ruleModule_in_rule__Roledefn__Group_2__1__Impl1953);
            ruleModule();

            state._fsp--;

             after(grammarAccess.getRoledefnAccess().getModuleParserRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__1__Impl"


    // $ANTLR start "rule__Roledefn__Group_2__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1013:1: rule__Roledefn__Group_2__2 : rule__Roledefn__Group_2__2__Impl rule__Roledefn__Group_2__3 ;
    public final void rule__Roledefn__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1017:1: ( rule__Roledefn__Group_2__2__Impl rule__Roledefn__Group_2__3 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1018:2: rule__Roledefn__Group_2__2__Impl rule__Roledefn__Group_2__3
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2__2__Impl_in_rule__Roledefn__Group_2__21982);
            rule__Roledefn__Group_2__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group_2__3_in_rule__Roledefn__Group_2__21985);
            rule__Roledefn__Group_2__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__2"


    // $ANTLR start "rule__Roledefn__Group_2__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1025:1: rule__Roledefn__Group_2__2__Impl : ( 'protocol' ) ;
    public final void rule__Roledefn__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1029:1: ( ( 'protocol' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1030:1: ( 'protocol' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1030:1: ( 'protocol' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1031:1: 'protocol'
            {
             before(grammarAccess.getRoledefnAccess().getProtocolKeyword_2_2()); 
            match(input,18,FOLLOW_18_in_rule__Roledefn__Group_2__2__Impl2013); 
             after(grammarAccess.getRoledefnAccess().getProtocolKeyword_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__2__Impl"


    // $ANTLR start "rule__Roledefn__Group_2__3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1044:1: rule__Roledefn__Group_2__3 : rule__Roledefn__Group_2__3__Impl rule__Roledefn__Group_2__4 ;
    public final void rule__Roledefn__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1048:1: ( rule__Roledefn__Group_2__3__Impl rule__Roledefn__Group_2__4 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1049:2: rule__Roledefn__Group_2__3__Impl rule__Roledefn__Group_2__4
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2__3__Impl_in_rule__Roledefn__Group_2__32044);
            rule__Roledefn__Group_2__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group_2__4_in_rule__Roledefn__Group_2__32047);
            rule__Roledefn__Group_2__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__3"


    // $ANTLR start "rule__Roledefn__Group_2__3__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1056:1: rule__Roledefn__Group_2__3__Impl : ( RULE_ID ) ;
    public final void rule__Roledefn__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1060:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1061:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1061:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1062:1: RULE_ID
            {
             before(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_3()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Roledefn__Group_2__3__Impl2074); 
             after(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__3__Impl"


    // $ANTLR start "rule__Roledefn__Group_2__4"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1073:1: rule__Roledefn__Group_2__4 : rule__Roledefn__Group_2__4__Impl ;
    public final void rule__Roledefn__Group_2__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1077:1: ( rule__Roledefn__Group_2__4__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1078:2: rule__Roledefn__Group_2__4__Impl
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2__4__Impl_in_rule__Roledefn__Group_2__42103);
            rule__Roledefn__Group_2__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__4"


    // $ANTLR start "rule__Roledefn__Group_2__4__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1084:1: rule__Roledefn__Group_2__4__Impl : ( ( rule__Roledefn__Group_2_4__0 )? ) ;
    public final void rule__Roledefn__Group_2__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1088:1: ( ( ( rule__Roledefn__Group_2_4__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1089:1: ( ( rule__Roledefn__Group_2_4__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1089:1: ( ( rule__Roledefn__Group_2_4__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1090:1: ( rule__Roledefn__Group_2_4__0 )?
            {
             before(grammarAccess.getRoledefnAccess().getGroup_2_4()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1091:1: ( rule__Roledefn__Group_2_4__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1091:2: rule__Roledefn__Group_2_4__0
                    {
                    pushFollow(FOLLOW_rule__Roledefn__Group_2_4__0_in_rule__Roledefn__Group_2__4__Impl2130);
                    rule__Roledefn__Group_2_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRoledefnAccess().getGroup_2_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2__4__Impl"


    // $ANTLR start "rule__Roledefn__Group_2_4__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1111:1: rule__Roledefn__Group_2_4__0 : rule__Roledefn__Group_2_4__0__Impl rule__Roledefn__Group_2_4__1 ;
    public final void rule__Roledefn__Group_2_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1115:1: ( rule__Roledefn__Group_2_4__0__Impl rule__Roledefn__Group_2_4__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1116:2: rule__Roledefn__Group_2_4__0__Impl rule__Roledefn__Group_2_4__1
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2_4__0__Impl_in_rule__Roledefn__Group_2_4__02171);
            rule__Roledefn__Group_2_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Roledefn__Group_2_4__1_in_rule__Roledefn__Group_2_4__02174);
            rule__Roledefn__Group_2_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2_4__0"


    // $ANTLR start "rule__Roledefn__Group_2_4__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1123:1: rule__Roledefn__Group_2_4__0__Impl : ( 'as' ) ;
    public final void rule__Roledefn__Group_2_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1127:1: ( ( 'as' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1128:1: ( 'as' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1128:1: ( 'as' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1129:1: 'as'
            {
             before(grammarAccess.getRoledefnAccess().getAsKeyword_2_4_0()); 
            match(input,19,FOLLOW_19_in_rule__Roledefn__Group_2_4__0__Impl2202); 
             after(grammarAccess.getRoledefnAccess().getAsKeyword_2_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2_4__0__Impl"


    // $ANTLR start "rule__Roledefn__Group_2_4__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1142:1: rule__Roledefn__Group_2_4__1 : rule__Roledefn__Group_2_4__1__Impl ;
    public final void rule__Roledefn__Group_2_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1146:1: ( rule__Roledefn__Group_2_4__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1147:2: rule__Roledefn__Group_2_4__1__Impl
            {
            pushFollow(FOLLOW_rule__Roledefn__Group_2_4__1__Impl_in_rule__Roledefn__Group_2_4__12233);
            rule__Roledefn__Group_2_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2_4__1"


    // $ANTLR start "rule__Roledefn__Group_2_4__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1153:1: rule__Roledefn__Group_2_4__1__Impl : ( RULE_ID ) ;
    public final void rule__Roledefn__Group_2_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1157:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1158:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1158:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1159:1: RULE_ID
            {
             before(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_4_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Roledefn__Group_2_4__1__Impl2260); 
             after(grammarAccess.getRoledefnAccess().getIDTerminalRuleCall_2_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Roledefn__Group_2_4__1__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1174:1: rule__Messagetransfer__Group__0 : rule__Messagetransfer__Group__0__Impl rule__Messagetransfer__Group__1 ;
    public final void rule__Messagetransfer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1178:1: ( rule__Messagetransfer__Group__0__Impl rule__Messagetransfer__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1179:2: rule__Messagetransfer__Group__0__Impl rule__Messagetransfer__Group__1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__0__Impl_in_rule__Messagetransfer__Group__02293);
            rule__Messagetransfer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__1_in_rule__Messagetransfer__Group__02296);
            rule__Messagetransfer__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__0"


    // $ANTLR start "rule__Messagetransfer__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1186:1: rule__Messagetransfer__Group__0__Impl : ( () ) ;
    public final void rule__Messagetransfer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1190:1: ( ( () ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1191:1: ( () )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1191:1: ( () )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1192:1: ()
            {
             before(grammarAccess.getMessagetransferAccess().getMessagetransferAction_0()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1193:1: ()
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1195:1: 
            {
            }

             after(grammarAccess.getMessagetransferAccess().getMessagetransferAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__0__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1205:1: rule__Messagetransfer__Group__1 : rule__Messagetransfer__Group__1__Impl rule__Messagetransfer__Group__2 ;
    public final void rule__Messagetransfer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1209:1: ( rule__Messagetransfer__Group__1__Impl rule__Messagetransfer__Group__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1210:2: rule__Messagetransfer__Group__1__Impl rule__Messagetransfer__Group__2
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__1__Impl_in_rule__Messagetransfer__Group__12354);
            rule__Messagetransfer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__2_in_rule__Messagetransfer__Group__12357);
            rule__Messagetransfer__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__1"


    // $ANTLR start "rule__Messagetransfer__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1217:1: rule__Messagetransfer__Group__1__Impl : ( RULE_ID ) ;
    public final void rule__Messagetransfer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1221:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1222:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1222:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1223:1: RULE_ID
            {
             before(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__1__Impl2384); 
             after(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__1__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1234:1: rule__Messagetransfer__Group__2 : rule__Messagetransfer__Group__2__Impl rule__Messagetransfer__Group__3 ;
    public final void rule__Messagetransfer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1238:1: ( rule__Messagetransfer__Group__2__Impl rule__Messagetransfer__Group__3 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1239:2: rule__Messagetransfer__Group__2__Impl rule__Messagetransfer__Group__3
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__2__Impl_in_rule__Messagetransfer__Group__22413);
            rule__Messagetransfer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__3_in_rule__Messagetransfer__Group__22416);
            rule__Messagetransfer__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__2"


    // $ANTLR start "rule__Messagetransfer__Group__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1246:1: rule__Messagetransfer__Group__2__Impl : ( ( rule__Messagetransfer__Group_2__0 )? ) ;
    public final void rule__Messagetransfer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1250:1: ( ( ( rule__Messagetransfer__Group_2__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1251:1: ( ( rule__Messagetransfer__Group_2__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1251:1: ( ( rule__Messagetransfer__Group_2__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1252:1: ( rule__Messagetransfer__Group_2__0 )?
            {
             before(grammarAccess.getMessagetransferAccess().getGroup_2()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1253:1: ( rule__Messagetransfer__Group_2__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1253:2: rule__Messagetransfer__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__Messagetransfer__Group_2__0_in_rule__Messagetransfer__Group__2__Impl2443);
                    rule__Messagetransfer__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMessagetransferAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__2__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1263:1: rule__Messagetransfer__Group__3 : rule__Messagetransfer__Group__3__Impl rule__Messagetransfer__Group__4 ;
    public final void rule__Messagetransfer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1267:1: ( rule__Messagetransfer__Group__3__Impl rule__Messagetransfer__Group__4 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1268:2: rule__Messagetransfer__Group__3__Impl rule__Messagetransfer__Group__4
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__3__Impl_in_rule__Messagetransfer__Group__32474);
            rule__Messagetransfer__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__4_in_rule__Messagetransfer__Group__32477);
            rule__Messagetransfer__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__3"


    // $ANTLR start "rule__Messagetransfer__Group__3__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1275:1: rule__Messagetransfer__Group__3__Impl : ( 'from' ) ;
    public final void rule__Messagetransfer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1279:1: ( ( 'from' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1280:1: ( 'from' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1280:1: ( 'from' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1281:1: 'from'
            {
             before(grammarAccess.getMessagetransferAccess().getFromKeyword_3()); 
            match(input,20,FOLLOW_20_in_rule__Messagetransfer__Group__3__Impl2505); 
             after(grammarAccess.getMessagetransferAccess().getFromKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__3__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__4"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1294:1: rule__Messagetransfer__Group__4 : rule__Messagetransfer__Group__4__Impl rule__Messagetransfer__Group__5 ;
    public final void rule__Messagetransfer__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1298:1: ( rule__Messagetransfer__Group__4__Impl rule__Messagetransfer__Group__5 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1299:2: rule__Messagetransfer__Group__4__Impl rule__Messagetransfer__Group__5
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__4__Impl_in_rule__Messagetransfer__Group__42536);
            rule__Messagetransfer__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__5_in_rule__Messagetransfer__Group__42539);
            rule__Messagetransfer__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__4"


    // $ANTLR start "rule__Messagetransfer__Group__4__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1306:1: rule__Messagetransfer__Group__4__Impl : ( RULE_ID ) ;
    public final void rule__Messagetransfer__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1310:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1311:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1311:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1312:1: RULE_ID
            {
             before(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_4()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__4__Impl2566); 
             after(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__4__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__5"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1323:1: rule__Messagetransfer__Group__5 : rule__Messagetransfer__Group__5__Impl rule__Messagetransfer__Group__6 ;
    public final void rule__Messagetransfer__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1327:1: ( rule__Messagetransfer__Group__5__Impl rule__Messagetransfer__Group__6 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1328:2: rule__Messagetransfer__Group__5__Impl rule__Messagetransfer__Group__6
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__5__Impl_in_rule__Messagetransfer__Group__52595);
            rule__Messagetransfer__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__6_in_rule__Messagetransfer__Group__52598);
            rule__Messagetransfer__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__5"


    // $ANTLR start "rule__Messagetransfer__Group__5__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1335:1: rule__Messagetransfer__Group__5__Impl : ( 'to' ) ;
    public final void rule__Messagetransfer__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1339:1: ( ( 'to' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1340:1: ( 'to' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1340:1: ( 'to' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1341:1: 'to'
            {
             before(grammarAccess.getMessagetransferAccess().getToKeyword_5()); 
            match(input,21,FOLLOW_21_in_rule__Messagetransfer__Group__5__Impl2626); 
             after(grammarAccess.getMessagetransferAccess().getToKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__5__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__6"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1354:1: rule__Messagetransfer__Group__6 : rule__Messagetransfer__Group__6__Impl rule__Messagetransfer__Group__7 ;
    public final void rule__Messagetransfer__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1358:1: ( rule__Messagetransfer__Group__6__Impl rule__Messagetransfer__Group__7 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1359:2: rule__Messagetransfer__Group__6__Impl rule__Messagetransfer__Group__7
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__6__Impl_in_rule__Messagetransfer__Group__62657);
            rule__Messagetransfer__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__7_in_rule__Messagetransfer__Group__62660);
            rule__Messagetransfer__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__6"


    // $ANTLR start "rule__Messagetransfer__Group__6__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1366:1: rule__Messagetransfer__Group__6__Impl : ( RULE_ID ) ;
    public final void rule__Messagetransfer__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1370:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1371:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1371:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1372:1: RULE_ID
            {
             before(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_6()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__6__Impl2687); 
             after(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__6__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__7"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1383:1: rule__Messagetransfer__Group__7 : rule__Messagetransfer__Group__7__Impl rule__Messagetransfer__Group__8 ;
    public final void rule__Messagetransfer__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1387:1: ( rule__Messagetransfer__Group__7__Impl rule__Messagetransfer__Group__8 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1388:2: rule__Messagetransfer__Group__7__Impl rule__Messagetransfer__Group__8
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__7__Impl_in_rule__Messagetransfer__Group__72716);
            rule__Messagetransfer__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group__8_in_rule__Messagetransfer__Group__72719);
            rule__Messagetransfer__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__7"


    // $ANTLR start "rule__Messagetransfer__Group__7__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1395:1: rule__Messagetransfer__Group__7__Impl : ( ( rule__Messagetransfer__Group_7__0 )* ) ;
    public final void rule__Messagetransfer__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1399:1: ( ( ( rule__Messagetransfer__Group_7__0 )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1400:1: ( ( rule__Messagetransfer__Group_7__0 )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1400:1: ( ( rule__Messagetransfer__Group_7__0 )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1401:1: ( rule__Messagetransfer__Group_7__0 )*
            {
             before(grammarAccess.getMessagetransferAccess().getGroup_7()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1402:1: ( rule__Messagetransfer__Group_7__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==24) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1402:2: rule__Messagetransfer__Group_7__0
            	    {
            	    pushFollow(FOLLOW_rule__Messagetransfer__Group_7__0_in_rule__Messagetransfer__Group__7__Impl2746);
            	    rule__Messagetransfer__Group_7__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getMessagetransferAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__7__Impl"


    // $ANTLR start "rule__Messagetransfer__Group__8"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1412:1: rule__Messagetransfer__Group__8 : rule__Messagetransfer__Group__8__Impl ;
    public final void rule__Messagetransfer__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1416:1: ( rule__Messagetransfer__Group__8__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1417:2: rule__Messagetransfer__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group__8__Impl_in_rule__Messagetransfer__Group__82777);
            rule__Messagetransfer__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__8"


    // $ANTLR start "rule__Messagetransfer__Group__8__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1423:1: rule__Messagetransfer__Group__8__Impl : ( ';' ) ;
    public final void rule__Messagetransfer__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1427:1: ( ( ';' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1428:1: ( ';' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1428:1: ( ';' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1429:1: ';'
            {
             before(grammarAccess.getMessagetransferAccess().getSemicolonKeyword_8()); 
            match(input,12,FOLLOW_12_in_rule__Messagetransfer__Group__8__Impl2805); 
             after(grammarAccess.getMessagetransferAccess().getSemicolonKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group__8__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1460:1: rule__Messagetransfer__Group_2__0 : rule__Messagetransfer__Group_2__0__Impl rule__Messagetransfer__Group_2__1 ;
    public final void rule__Messagetransfer__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1464:1: ( rule__Messagetransfer__Group_2__0__Impl rule__Messagetransfer__Group_2__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1465:2: rule__Messagetransfer__Group_2__0__Impl rule__Messagetransfer__Group_2__1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2__0__Impl_in_rule__Messagetransfer__Group_2__02854);
            rule__Messagetransfer__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group_2__1_in_rule__Messagetransfer__Group_2__02857);
            rule__Messagetransfer__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__0"


    // $ANTLR start "rule__Messagetransfer__Group_2__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1472:1: rule__Messagetransfer__Group_2__0__Impl : ( '(' ) ;
    public final void rule__Messagetransfer__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1476:1: ( ( '(' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1477:1: ( '(' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1477:1: ( '(' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1478:1: '('
            {
             before(grammarAccess.getMessagetransferAccess().getLeftParenthesisKeyword_2_0()); 
            match(input,22,FOLLOW_22_in_rule__Messagetransfer__Group_2__0__Impl2885); 
             after(grammarAccess.getMessagetransferAccess().getLeftParenthesisKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__0__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1491:1: rule__Messagetransfer__Group_2__1 : rule__Messagetransfer__Group_2__1__Impl rule__Messagetransfer__Group_2__2 ;
    public final void rule__Messagetransfer__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1495:1: ( rule__Messagetransfer__Group_2__1__Impl rule__Messagetransfer__Group_2__2 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1496:2: rule__Messagetransfer__Group_2__1__Impl rule__Messagetransfer__Group_2__2
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2__1__Impl_in_rule__Messagetransfer__Group_2__12916);
            rule__Messagetransfer__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group_2__2_in_rule__Messagetransfer__Group_2__12919);
            rule__Messagetransfer__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__1"


    // $ANTLR start "rule__Messagetransfer__Group_2__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1503:1: rule__Messagetransfer__Group_2__1__Impl : ( ( rule__Messagetransfer__Group_2_1__0 )? ) ;
    public final void rule__Messagetransfer__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1507:1: ( ( ( rule__Messagetransfer__Group_2_1__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1508:1: ( ( rule__Messagetransfer__Group_2_1__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1508:1: ( ( rule__Messagetransfer__Group_2_1__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1509:1: ( rule__Messagetransfer__Group_2_1__0 )?
            {
             before(grammarAccess.getMessagetransferAccess().getGroup_2_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1510:1: ( rule__Messagetransfer__Group_2_1__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_STRING) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1510:2: rule__Messagetransfer__Group_2_1__0
                    {
                    pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1__0_in_rule__Messagetransfer__Group_2__1__Impl2946);
                    rule__Messagetransfer__Group_2_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMessagetransferAccess().getGroup_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__1__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2__2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1520:1: rule__Messagetransfer__Group_2__2 : rule__Messagetransfer__Group_2__2__Impl ;
    public final void rule__Messagetransfer__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1524:1: ( rule__Messagetransfer__Group_2__2__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1525:2: rule__Messagetransfer__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2__2__Impl_in_rule__Messagetransfer__Group_2__22977);
            rule__Messagetransfer__Group_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__2"


    // $ANTLR start "rule__Messagetransfer__Group_2__2__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1531:1: rule__Messagetransfer__Group_2__2__Impl : ( ')' ) ;
    public final void rule__Messagetransfer__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1535:1: ( ( ')' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1536:1: ( ')' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1536:1: ( ')' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1537:1: ')'
            {
             before(grammarAccess.getMessagetransferAccess().getRightParenthesisKeyword_2_2()); 
            match(input,23,FOLLOW_23_in_rule__Messagetransfer__Group_2__2__Impl3005); 
             after(grammarAccess.getMessagetransferAccess().getRightParenthesisKeyword_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2__2__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2_1__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1556:1: rule__Messagetransfer__Group_2_1__0 : rule__Messagetransfer__Group_2_1__0__Impl rule__Messagetransfer__Group_2_1__1 ;
    public final void rule__Messagetransfer__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1560:1: ( rule__Messagetransfer__Group_2_1__0__Impl rule__Messagetransfer__Group_2_1__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1561:2: rule__Messagetransfer__Group_2_1__0__Impl rule__Messagetransfer__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1__0__Impl_in_rule__Messagetransfer__Group_2_1__03042);
            rule__Messagetransfer__Group_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1__1_in_rule__Messagetransfer__Group_2_1__03045);
            rule__Messagetransfer__Group_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1__0"


    // $ANTLR start "rule__Messagetransfer__Group_2_1__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1568:1: rule__Messagetransfer__Group_2_1__0__Impl : ( ( rule__Messagetransfer__ParametersAssignment_2_1_0 ) ) ;
    public final void rule__Messagetransfer__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1572:1: ( ( ( rule__Messagetransfer__ParametersAssignment_2_1_0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1573:1: ( ( rule__Messagetransfer__ParametersAssignment_2_1_0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1573:1: ( ( rule__Messagetransfer__ParametersAssignment_2_1_0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1574:1: ( rule__Messagetransfer__ParametersAssignment_2_1_0 )
            {
             before(grammarAccess.getMessagetransferAccess().getParametersAssignment_2_1_0()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1575:1: ( rule__Messagetransfer__ParametersAssignment_2_1_0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1575:2: rule__Messagetransfer__ParametersAssignment_2_1_0
            {
            pushFollow(FOLLOW_rule__Messagetransfer__ParametersAssignment_2_1_0_in_rule__Messagetransfer__Group_2_1__0__Impl3072);
            rule__Messagetransfer__ParametersAssignment_2_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMessagetransferAccess().getParametersAssignment_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1__0__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2_1__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1585:1: rule__Messagetransfer__Group_2_1__1 : rule__Messagetransfer__Group_2_1__1__Impl ;
    public final void rule__Messagetransfer__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1589:1: ( rule__Messagetransfer__Group_2_1__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1590:2: rule__Messagetransfer__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1__1__Impl_in_rule__Messagetransfer__Group_2_1__13102);
            rule__Messagetransfer__Group_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1__1"


    // $ANTLR start "rule__Messagetransfer__Group_2_1__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1596:1: rule__Messagetransfer__Group_2_1__1__Impl : ( ( rule__Messagetransfer__Group_2_1_1__0 )* ) ;
    public final void rule__Messagetransfer__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1600:1: ( ( ( rule__Messagetransfer__Group_2_1_1__0 )* ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1601:1: ( ( rule__Messagetransfer__Group_2_1_1__0 )* )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1601:1: ( ( rule__Messagetransfer__Group_2_1_1__0 )* )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1602:1: ( rule__Messagetransfer__Group_2_1_1__0 )*
            {
             before(grammarAccess.getMessagetransferAccess().getGroup_2_1_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1603:1: ( rule__Messagetransfer__Group_2_1_1__0 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==24) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1603:2: rule__Messagetransfer__Group_2_1_1__0
            	    {
            	    pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1_1__0_in_rule__Messagetransfer__Group_2_1__1__Impl3129);
            	    rule__Messagetransfer__Group_2_1_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getMessagetransferAccess().getGroup_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1__1__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2_1_1__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1617:1: rule__Messagetransfer__Group_2_1_1__0 : rule__Messagetransfer__Group_2_1_1__0__Impl rule__Messagetransfer__Group_2_1_1__1 ;
    public final void rule__Messagetransfer__Group_2_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1621:1: ( rule__Messagetransfer__Group_2_1_1__0__Impl rule__Messagetransfer__Group_2_1_1__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1622:2: rule__Messagetransfer__Group_2_1_1__0__Impl rule__Messagetransfer__Group_2_1_1__1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1_1__0__Impl_in_rule__Messagetransfer__Group_2_1_1__03164);
            rule__Messagetransfer__Group_2_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1_1__1_in_rule__Messagetransfer__Group_2_1_1__03167);
            rule__Messagetransfer__Group_2_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1_1__0"


    // $ANTLR start "rule__Messagetransfer__Group_2_1_1__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1629:1: rule__Messagetransfer__Group_2_1_1__0__Impl : ( ',' ) ;
    public final void rule__Messagetransfer__Group_2_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1633:1: ( ( ',' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1634:1: ( ',' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1634:1: ( ',' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1635:1: ','
            {
             before(grammarAccess.getMessagetransferAccess().getCommaKeyword_2_1_1_0()); 
            match(input,24,FOLLOW_24_in_rule__Messagetransfer__Group_2_1_1__0__Impl3195); 
             after(grammarAccess.getMessagetransferAccess().getCommaKeyword_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1_1__0__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_2_1_1__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1648:1: rule__Messagetransfer__Group_2_1_1__1 : rule__Messagetransfer__Group_2_1_1__1__Impl ;
    public final void rule__Messagetransfer__Group_2_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1652:1: ( rule__Messagetransfer__Group_2_1_1__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1653:2: rule__Messagetransfer__Group_2_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_2_1_1__1__Impl_in_rule__Messagetransfer__Group_2_1_1__13226);
            rule__Messagetransfer__Group_2_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1_1__1"


    // $ANTLR start "rule__Messagetransfer__Group_2_1_1__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1659:1: rule__Messagetransfer__Group_2_1_1__1__Impl : ( ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 ) ) ;
    public final void rule__Messagetransfer__Group_2_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1663:1: ( ( ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1664:1: ( ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1664:1: ( ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1665:1: ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 )
            {
             before(grammarAccess.getMessagetransferAccess().getParametersAssignment_2_1_1_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1666:1: ( rule__Messagetransfer__ParametersAssignment_2_1_1_1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1666:2: rule__Messagetransfer__ParametersAssignment_2_1_1_1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__ParametersAssignment_2_1_1_1_in_rule__Messagetransfer__Group_2_1_1__1__Impl3253);
            rule__Messagetransfer__ParametersAssignment_2_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getMessagetransferAccess().getParametersAssignment_2_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_2_1_1__1__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_7__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1680:1: rule__Messagetransfer__Group_7__0 : rule__Messagetransfer__Group_7__0__Impl rule__Messagetransfer__Group_7__1 ;
    public final void rule__Messagetransfer__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1684:1: ( rule__Messagetransfer__Group_7__0__Impl rule__Messagetransfer__Group_7__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1685:2: rule__Messagetransfer__Group_7__0__Impl rule__Messagetransfer__Group_7__1
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_7__0__Impl_in_rule__Messagetransfer__Group_7__03287);
            rule__Messagetransfer__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Messagetransfer__Group_7__1_in_rule__Messagetransfer__Group_7__03290);
            rule__Messagetransfer__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_7__0"


    // $ANTLR start "rule__Messagetransfer__Group_7__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1692:1: rule__Messagetransfer__Group_7__0__Impl : ( ',' ) ;
    public final void rule__Messagetransfer__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1696:1: ( ( ',' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1697:1: ( ',' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1697:1: ( ',' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1698:1: ','
            {
             before(grammarAccess.getMessagetransferAccess().getCommaKeyword_7_0()); 
            match(input,24,FOLLOW_24_in_rule__Messagetransfer__Group_7__0__Impl3318); 
             after(grammarAccess.getMessagetransferAccess().getCommaKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_7__0__Impl"


    // $ANTLR start "rule__Messagetransfer__Group_7__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1711:1: rule__Messagetransfer__Group_7__1 : rule__Messagetransfer__Group_7__1__Impl ;
    public final void rule__Messagetransfer__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1715:1: ( rule__Messagetransfer__Group_7__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1716:2: rule__Messagetransfer__Group_7__1__Impl
            {
            pushFollow(FOLLOW_rule__Messagetransfer__Group_7__1__Impl_in_rule__Messagetransfer__Group_7__13349);
            rule__Messagetransfer__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_7__1"


    // $ANTLR start "rule__Messagetransfer__Group_7__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1722:1: rule__Messagetransfer__Group_7__1__Impl : ( RULE_ID ) ;
    public final void rule__Messagetransfer__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1726:1: ( ( RULE_ID ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1727:1: ( RULE_ID )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1727:1: ( RULE_ID )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1728:1: RULE_ID
            {
             before(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_7_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Messagetransfer__Group_7__1__Impl3376); 
             after(grammarAccess.getMessagetransferAccess().getIDTerminalRuleCall_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__Group_7__1__Impl"


    // $ANTLR start "rule__Parameter__Group__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1743:1: rule__Parameter__Group__0 : rule__Parameter__Group__0__Impl rule__Parameter__Group__1 ;
    public final void rule__Parameter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1747:1: ( rule__Parameter__Group__0__Impl rule__Parameter__Group__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1748:2: rule__Parameter__Group__0__Impl rule__Parameter__Group__1
            {
            pushFollow(FOLLOW_rule__Parameter__Group__0__Impl_in_rule__Parameter__Group__03409);
            rule__Parameter__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Parameter__Group__1_in_rule__Parameter__Group__03412);
            rule__Parameter__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group__0"


    // $ANTLR start "rule__Parameter__Group__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1755:1: rule__Parameter__Group__0__Impl : ( ( rule__Parameter__TypeAssignment_0 ) ) ;
    public final void rule__Parameter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1759:1: ( ( ( rule__Parameter__TypeAssignment_0 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1760:1: ( ( rule__Parameter__TypeAssignment_0 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1760:1: ( ( rule__Parameter__TypeAssignment_0 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1761:1: ( rule__Parameter__TypeAssignment_0 )
            {
             before(grammarAccess.getParameterAccess().getTypeAssignment_0()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1762:1: ( rule__Parameter__TypeAssignment_0 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1762:2: rule__Parameter__TypeAssignment_0
            {
            pushFollow(FOLLOW_rule__Parameter__TypeAssignment_0_in_rule__Parameter__Group__0__Impl3439);
            rule__Parameter__TypeAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getTypeAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group__0__Impl"


    // $ANTLR start "rule__Parameter__Group__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1772:1: rule__Parameter__Group__1 : rule__Parameter__Group__1__Impl ;
    public final void rule__Parameter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1776:1: ( rule__Parameter__Group__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1777:2: rule__Parameter__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__Parameter__Group__1__Impl_in_rule__Parameter__Group__13469);
            rule__Parameter__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group__1"


    // $ANTLR start "rule__Parameter__Group__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1783:1: rule__Parameter__Group__1__Impl : ( ( rule__Parameter__Group_1__0 )? ) ;
    public final void rule__Parameter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1787:1: ( ( ( rule__Parameter__Group_1__0 )? ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1788:1: ( ( rule__Parameter__Group_1__0 )? )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1788:1: ( ( rule__Parameter__Group_1__0 )? )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1789:1: ( rule__Parameter__Group_1__0 )?
            {
             before(grammarAccess.getParameterAccess().getGroup_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1790:1: ( rule__Parameter__Group_1__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==25) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1790:2: rule__Parameter__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__Parameter__Group_1__0_in_rule__Parameter__Group__1__Impl3496);
                    rule__Parameter__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getParameterAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group__1__Impl"


    // $ANTLR start "rule__Parameter__Group_1__0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1804:1: rule__Parameter__Group_1__0 : rule__Parameter__Group_1__0__Impl rule__Parameter__Group_1__1 ;
    public final void rule__Parameter__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1808:1: ( rule__Parameter__Group_1__0__Impl rule__Parameter__Group_1__1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1809:2: rule__Parameter__Group_1__0__Impl rule__Parameter__Group_1__1
            {
            pushFollow(FOLLOW_rule__Parameter__Group_1__0__Impl_in_rule__Parameter__Group_1__03531);
            rule__Parameter__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Parameter__Group_1__1_in_rule__Parameter__Group_1__03534);
            rule__Parameter__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group_1__0"


    // $ANTLR start "rule__Parameter__Group_1__0__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1816:1: rule__Parameter__Group_1__0__Impl : ( '=' ) ;
    public final void rule__Parameter__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1820:1: ( ( '=' ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1821:1: ( '=' )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1821:1: ( '=' )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1822:1: '='
            {
             before(grammarAccess.getParameterAccess().getEqualsSignKeyword_1_0()); 
            match(input,25,FOLLOW_25_in_rule__Parameter__Group_1__0__Impl3562); 
             after(grammarAccess.getParameterAccess().getEqualsSignKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group_1__0__Impl"


    // $ANTLR start "rule__Parameter__Group_1__1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1835:1: rule__Parameter__Group_1__1 : rule__Parameter__Group_1__1__Impl ;
    public final void rule__Parameter__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1839:1: ( rule__Parameter__Group_1__1__Impl )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1840:2: rule__Parameter__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Parameter__Group_1__1__Impl_in_rule__Parameter__Group_1__13593);
            rule__Parameter__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group_1__1"


    // $ANTLR start "rule__Parameter__Group_1__1__Impl"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1846:1: rule__Parameter__Group_1__1__Impl : ( ( rule__Parameter__ValueAssignment_1_1 ) ) ;
    public final void rule__Parameter__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1850:1: ( ( ( rule__Parameter__ValueAssignment_1_1 ) ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1851:1: ( ( rule__Parameter__ValueAssignment_1_1 ) )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1851:1: ( ( rule__Parameter__ValueAssignment_1_1 ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1852:1: ( rule__Parameter__ValueAssignment_1_1 )
            {
             before(grammarAccess.getParameterAccess().getValueAssignment_1_1()); 
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1853:1: ( rule__Parameter__ValueAssignment_1_1 )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1853:2: rule__Parameter__ValueAssignment_1_1
            {
            pushFollow(FOLLOW_rule__Parameter__ValueAssignment_1_1_in_rule__Parameter__Group_1__1__Impl3620);
            rule__Parameter__ValueAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getValueAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__Group_1__1__Impl"


    // $ANTLR start "rule__Trace__RolesAssignment_2"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1868:1: rule__Trace__RolesAssignment_2 : ( ruleRoledefn ) ;
    public final void rule__Trace__RolesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1872:1: ( ( ruleRoledefn ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1873:1: ( ruleRoledefn )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1873:1: ( ruleRoledefn )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1874:1: ruleRoledefn
            {
             before(grammarAccess.getTraceAccess().getRolesRoledefnParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleRoledefn_in_rule__Trace__RolesAssignment_23659);
            ruleRoledefn();

            state._fsp--;

             after(grammarAccess.getTraceAccess().getRolesRoledefnParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__RolesAssignment_2"


    // $ANTLR start "rule__Trace__StepsAssignment_3"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1883:1: rule__Trace__StepsAssignment_3 : ( ruleStepdefn ) ;
    public final void rule__Trace__StepsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1887:1: ( ( ruleStepdefn ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1888:1: ( ruleStepdefn )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1888:1: ( ruleStepdefn )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1889:1: ruleStepdefn
            {
             before(grammarAccess.getTraceAccess().getStepsStepdefnParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleStepdefn_in_rule__Trace__StepsAssignment_33690);
            ruleStepdefn();

            state._fsp--;

             after(grammarAccess.getTraceAccess().getStepsStepdefnParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Trace__StepsAssignment_3"


    // $ANTLR start "rule__Messagetransfer__ParametersAssignment_2_1_0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1898:1: rule__Messagetransfer__ParametersAssignment_2_1_0 : ( ruleParameter ) ;
    public final void rule__Messagetransfer__ParametersAssignment_2_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1902:1: ( ( ruleParameter ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1903:1: ( ruleParameter )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1903:1: ( ruleParameter )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1904:1: ruleParameter
            {
             before(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_0_0()); 
            pushFollow(FOLLOW_ruleParameter_in_rule__Messagetransfer__ParametersAssignment_2_1_03721);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__ParametersAssignment_2_1_0"


    // $ANTLR start "rule__Messagetransfer__ParametersAssignment_2_1_1_1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1913:1: rule__Messagetransfer__ParametersAssignment_2_1_1_1 : ( ruleParameter ) ;
    public final void rule__Messagetransfer__ParametersAssignment_2_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1917:1: ( ( ruleParameter ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1918:1: ( ruleParameter )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1918:1: ( ruleParameter )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1919:1: ruleParameter
            {
             before(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_1_1_0()); 
            pushFollow(FOLLOW_ruleParameter_in_rule__Messagetransfer__ParametersAssignment_2_1_1_13752);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getMessagetransferAccess().getParametersParameterParserRuleCall_2_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Messagetransfer__ParametersAssignment_2_1_1_1"


    // $ANTLR start "rule__Parameter__TypeAssignment_0"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1928:1: rule__Parameter__TypeAssignment_0 : ( RULE_STRING ) ;
    public final void rule__Parameter__TypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1932:1: ( ( RULE_STRING ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1933:1: ( RULE_STRING )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1933:1: ( RULE_STRING )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1934:1: RULE_STRING
            {
             before(grammarAccess.getParameterAccess().getTypeSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Parameter__TypeAssignment_03783); 
             after(grammarAccess.getParameterAccess().getTypeSTRINGTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__TypeAssignment_0"


    // $ANTLR start "rule__Parameter__ValueAssignment_1_1"
    // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1943:1: rule__Parameter__ValueAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__Parameter__ValueAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1947:1: ( ( RULE_STRING ) )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1948:1: ( RULE_STRING )
            {
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1948:1: ( RULE_STRING )
            // ../org.scribble.trace.editor.ui/src-gen/org/scribble/trace/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleTraceDsl.g:1949:1: RULE_STRING
            {
             before(grammarAccess.getParameterAccess().getValueSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Parameter__ValueAssignment_1_13814); 
             after(grammarAccess.getParameterAccess().getValueSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Parameter__ValueAssignment_1_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleTrace_in_entryRuleTrace61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTrace68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__Group__0_in_ruleTrace94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSentence_in_entryRuleSentence121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSentence128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSentence157 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleSentence170 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_ruleTracedefn_in_entryRuleTracedefn200 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTracedefn207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__0_in_ruleTracedefn233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule260 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__0_in_ruleModule293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoledefn_in_entryRuleRoledefn320 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoledefn327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__0_in_ruleRoledefn353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStepdefn_in_entryRuleStepdefn380 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStepdefn387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessagetransfer_in_ruleStepdefn413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessagetransfer_in_entryRuleMessagetransfer439 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessagetransfer446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__0_in_ruleMessagetransfer472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameter_in_entryRuleParameter499 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameter506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group__0_in_ruleParameter532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__Group__0__Impl_in_rule__Trace__Group__0566 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_rule__Trace__Group__1_in_rule__Trace__Group__0569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__Group__1__Impl_in_rule__Trace__Group__1627 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_rule__Trace__Group__2_in_rule__Trace__Group__1630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTracedefn_in_rule__Trace__Group__1__Impl657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__Group__2__Impl_in_rule__Trace__Group__2686 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_rule__Trace__Group__3_in_rule__Trace__Group__2689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__RolesAssignment_2_in_rule__Trace__Group__2__Impl716 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_rule__Trace__Group__3__Impl_in_rule__Trace__Group__3747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Trace__StepsAssignment_3_in_rule__Trace__Group__3__Impl774 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__0__Impl_in_rule__Tracedefn__Group__0813 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__1_in_rule__Tracedefn__Group__0816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Tracedefn__Group__0__Impl844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__1__Impl_in_rule__Tracedefn__Group__1875 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__2_in_rule__Tracedefn__Group__1878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Tracedefn__Group__1__Impl905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__2__Impl_in_rule__Tracedefn__Group__2934 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__3_in_rule__Tracedefn__Group__2937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__0_in_rule__Tracedefn__Group__2__Impl964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group__3__Impl_in_rule__Tracedefn__Group__3995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Tracedefn__Group__3__Impl1023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__0__Impl_in_rule__Tracedefn__Group_2__01062 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__1_in_rule__Tracedefn__Group_2__01065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Tracedefn__Group_2__0__Impl1093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__1__Impl_in_rule__Tracedefn__Group_2__11124 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__2_in_rule__Tracedefn__Group_2__11127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSentence_in_rule__Tracedefn__Group_2__1__Impl1154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2__2__Impl_in_rule__Tracedefn__Group_2__21183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2_2__0_in_rule__Tracedefn__Group_2__2__Impl1210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2_2__0__Impl_in_rule__Tracedefn__Group_2_2__01247 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2_2__1_in_rule__Tracedefn__Group_2_2__01250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__Tracedefn__Group_2_2__0__Impl1278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Tracedefn__Group_2_2__1__Impl_in_rule__Tracedefn__Group_2_2__11309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSentence_in_rule__Tracedefn__Group_2_2__1__Impl1336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__0__Impl_in_rule__Module__Group__01369 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Module__Group__1_in_rule__Module__Group__01372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Module__Group__0__Impl1399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__1__Impl_in_rule__Module__Group__11428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group_1__0_in_rule__Module__Group__1__Impl1455 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_rule__Module__Group_1__0__Impl_in_rule__Module__Group_1__01490 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Module__Group_1__1_in_rule__Module__Group_1__01493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Module__Group_1__0__Impl1521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group_1__1__Impl_in_rule__Module__Group_1__11552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Module__Group_1__1__Impl1579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__0__Impl_in_rule__Roledefn__Group__01612 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__1_in_rule__Roledefn__Group__01615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__Roledefn__Group__0__Impl1643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__1__Impl_in_rule__Roledefn__Group__11674 = new BitSet(new long[]{0x0000000000021000L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__2_in_rule__Roledefn__Group__11677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Roledefn__Group__1__Impl1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__2__Impl_in_rule__Roledefn__Group__21733 = new BitSet(new long[]{0x0000000000021000L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__3_in_rule__Roledefn__Group__21736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__0_in_rule__Roledefn__Group__2__Impl1763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group__3__Impl_in_rule__Roledefn__Group__31794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Roledefn__Group__3__Impl1822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__0__Impl_in_rule__Roledefn__Group_2__01861 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__1_in_rule__Roledefn__Group_2__01864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__Roledefn__Group_2__0__Impl1892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__1__Impl_in_rule__Roledefn__Group_2__11923 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__2_in_rule__Roledefn__Group_2__11926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModule_in_rule__Roledefn__Group_2__1__Impl1953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__2__Impl_in_rule__Roledefn__Group_2__21982 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__3_in_rule__Roledefn__Group_2__21985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__Roledefn__Group_2__2__Impl2013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__3__Impl_in_rule__Roledefn__Group_2__32044 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__4_in_rule__Roledefn__Group_2__32047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Roledefn__Group_2__3__Impl2074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2__4__Impl_in_rule__Roledefn__Group_2__42103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2_4__0_in_rule__Roledefn__Group_2__4__Impl2130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2_4__0__Impl_in_rule__Roledefn__Group_2_4__02171 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2_4__1_in_rule__Roledefn__Group_2_4__02174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__Roledefn__Group_2_4__0__Impl2202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Roledefn__Group_2_4__1__Impl_in_rule__Roledefn__Group_2_4__12233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Roledefn__Group_2_4__1__Impl2260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__0__Impl_in_rule__Messagetransfer__Group__02293 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__1_in_rule__Messagetransfer__Group__02296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__1__Impl_in_rule__Messagetransfer__Group__12354 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__2_in_rule__Messagetransfer__Group__12357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__1__Impl2384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__2__Impl_in_rule__Messagetransfer__Group__22413 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__3_in_rule__Messagetransfer__Group__22416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__0_in_rule__Messagetransfer__Group__2__Impl2443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__3__Impl_in_rule__Messagetransfer__Group__32474 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__4_in_rule__Messagetransfer__Group__32477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__Messagetransfer__Group__3__Impl2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__4__Impl_in_rule__Messagetransfer__Group__42536 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__5_in_rule__Messagetransfer__Group__42539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__4__Impl2566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__5__Impl_in_rule__Messagetransfer__Group__52595 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__6_in_rule__Messagetransfer__Group__52598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__Messagetransfer__Group__5__Impl2626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__6__Impl_in_rule__Messagetransfer__Group__62657 = new BitSet(new long[]{0x0000000001001000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__7_in_rule__Messagetransfer__Group__62660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Messagetransfer__Group__6__Impl2687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__7__Impl_in_rule__Messagetransfer__Group__72716 = new BitSet(new long[]{0x0000000001001000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__8_in_rule__Messagetransfer__Group__72719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_7__0_in_rule__Messagetransfer__Group__7__Impl2746 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group__8__Impl_in_rule__Messagetransfer__Group__82777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Messagetransfer__Group__8__Impl2805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__0__Impl_in_rule__Messagetransfer__Group_2__02854 = new BitSet(new long[]{0x0000000000800020L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__1_in_rule__Messagetransfer__Group_2__02857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__Messagetransfer__Group_2__0__Impl2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__1__Impl_in_rule__Messagetransfer__Group_2__12916 = new BitSet(new long[]{0x0000000000800020L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__2_in_rule__Messagetransfer__Group_2__12919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1__0_in_rule__Messagetransfer__Group_2__1__Impl2946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2__2__Impl_in_rule__Messagetransfer__Group_2__22977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__Messagetransfer__Group_2__2__Impl3005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1__0__Impl_in_rule__Messagetransfer__Group_2_1__03042 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1__1_in_rule__Messagetransfer__Group_2_1__03045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__ParametersAssignment_2_1_0_in_rule__Messagetransfer__Group_2_1__0__Impl3072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1__1__Impl_in_rule__Messagetransfer__Group_2_1__13102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1_1__0_in_rule__Messagetransfer__Group_2_1__1__Impl3129 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1_1__0__Impl_in_rule__Messagetransfer__Group_2_1_1__03164 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1_1__1_in_rule__Messagetransfer__Group_2_1_1__03167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__Messagetransfer__Group_2_1_1__0__Impl3195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_2_1_1__1__Impl_in_rule__Messagetransfer__Group_2_1_1__13226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__ParametersAssignment_2_1_1_1_in_rule__Messagetransfer__Group_2_1_1__1__Impl3253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_7__0__Impl_in_rule__Messagetransfer__Group_7__03287 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_7__1_in_rule__Messagetransfer__Group_7__03290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__Messagetransfer__Group_7__0__Impl3318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Messagetransfer__Group_7__1__Impl_in_rule__Messagetransfer__Group_7__13349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Messagetransfer__Group_7__1__Impl3376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group__0__Impl_in_rule__Parameter__Group__03409 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__Parameter__Group__1_in_rule__Parameter__Group__03412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__TypeAssignment_0_in_rule__Parameter__Group__0__Impl3439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group__1__Impl_in_rule__Parameter__Group__13469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group_1__0_in_rule__Parameter__Group__1__Impl3496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group_1__0__Impl_in_rule__Parameter__Group_1__03531 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__Parameter__Group_1__1_in_rule__Parameter__Group_1__03534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__Parameter__Group_1__0__Impl3562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__Group_1__1__Impl_in_rule__Parameter__Group_1__13593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Parameter__ValueAssignment_1_1_in_rule__Parameter__Group_1__1__Impl3620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoledefn_in_rule__Trace__RolesAssignment_23659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStepdefn_in_rule__Trace__StepsAssignment_33690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameter_in_rule__Messagetransfer__ParametersAssignment_2_1_03721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameter_in_rule__Messagetransfer__ParametersAssignment_2_1_1_13752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Parameter__TypeAssignment_03783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Parameter__ValueAssignment_1_13814 = new BitSet(new long[]{0x0000000000000002L});

}