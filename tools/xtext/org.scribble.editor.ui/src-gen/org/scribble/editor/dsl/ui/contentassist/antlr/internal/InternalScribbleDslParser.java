package org.scribble.editor.dsl.ui.contentassist.antlr.internal; 

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
import org.scribble.editor.dsl.services.ScribbleDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalScribbleDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'module'", "';'", "'.'", "'import'", "'as'", "'from'", "'type'", "'<'", "'>'", "'('", "')'", "','", "':'", "'global'", "'protocol'", "'instantiates'", "'role'", "'sig'", "'{'", "'}'", "'to'", "'choice'", "'at'", "'or'", "'rec'", "'continue'", "'par'", "'and'", "'interruptible'", "'with'", "'by'", "'do'"
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
    public String getGrammarFileName() { return "../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g"; }


     
     	private ScribbleDslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(ScribbleDslGrammarAccess grammarAccess) {
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




    // $ANTLR start "entryRuleModule"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:60:1: entryRuleModule : ruleModule EOF ;
    public final void entryRuleModule() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:61:1: ( ruleModule EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:62:1: ruleModule EOF
            {
             before(grammarAccess.getModuleRule()); 
            pushFollow(FOLLOW_ruleModule_in_entryRuleModule61);
            ruleModule();

            state._fsp--;

             after(grammarAccess.getModuleRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModule68); 

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
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:69:1: ruleModule : ( ( rule__Module__Group__0 ) ) ;
    public final void ruleModule() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:73:2: ( ( ( rule__Module__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:74:1: ( ( rule__Module__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:74:1: ( ( rule__Module__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:75:1: ( rule__Module__Group__0 )
            {
             before(grammarAccess.getModuleAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:76:1: ( rule__Module__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:76:2: rule__Module__Group__0
            {
            pushFollow(FOLLOW_rule__Module__Group__0_in_ruleModule94);
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


    // $ANTLR start "entryRuleModuleDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:88:1: entryRuleModuleDecl : ruleModuleDecl EOF ;
    public final void entryRuleModuleDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:89:1: ( ruleModuleDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:90:1: ruleModuleDecl EOF
            {
             before(grammarAccess.getModuleDeclRule()); 
            pushFollow(FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl121);
            ruleModuleDecl();

            state._fsp--;

             after(grammarAccess.getModuleDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleDecl128); 

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
    // $ANTLR end "entryRuleModuleDecl"


    // $ANTLR start "ruleModuleDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:97:1: ruleModuleDecl : ( ( rule__ModuleDecl__Group__0 ) ) ;
    public final void ruleModuleDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:101:2: ( ( ( rule__ModuleDecl__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:102:1: ( ( rule__ModuleDecl__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:102:1: ( ( rule__ModuleDecl__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:103:1: ( rule__ModuleDecl__Group__0 )
            {
             before(grammarAccess.getModuleDeclAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:104:1: ( rule__ModuleDecl__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:104:2: rule__ModuleDecl__Group__0
            {
            pushFollow(FOLLOW_rule__ModuleDecl__Group__0_in_ruleModuleDecl154);
            rule__ModuleDecl__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModuleDeclAccess().getGroup()); 

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
    // $ANTLR end "ruleModuleDecl"


    // $ANTLR start "entryRuleModuleName"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:116:1: entryRuleModuleName : ruleModuleName EOF ;
    public final void entryRuleModuleName() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:117:1: ( ruleModuleName EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:118:1: ruleModuleName EOF
            {
             before(grammarAccess.getModuleNameRule()); 
            pushFollow(FOLLOW_ruleModuleName_in_entryRuleModuleName181);
            ruleModuleName();

            state._fsp--;

             after(grammarAccess.getModuleNameRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleName188); 

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
    // $ANTLR end "entryRuleModuleName"


    // $ANTLR start "ruleModuleName"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:125:1: ruleModuleName : ( ( rule__ModuleName__Group__0 ) ) ;
    public final void ruleModuleName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:129:2: ( ( ( rule__ModuleName__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:130:1: ( ( rule__ModuleName__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:130:1: ( ( rule__ModuleName__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:131:1: ( rule__ModuleName__Group__0 )
            {
             before(grammarAccess.getModuleNameAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:132:1: ( rule__ModuleName__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:132:2: rule__ModuleName__Group__0
            {
            pushFollow(FOLLOW_rule__ModuleName__Group__0_in_ruleModuleName214);
            rule__ModuleName__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModuleNameAccess().getGroup()); 

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
    // $ANTLR end "ruleModuleName"


    // $ANTLR start "entryRuleImportDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:144:1: entryRuleImportDecl : ruleImportDecl EOF ;
    public final void entryRuleImportDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:145:1: ( ruleImportDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:146:1: ruleImportDecl EOF
            {
             before(grammarAccess.getImportDeclRule()); 
            pushFollow(FOLLOW_ruleImportDecl_in_entryRuleImportDecl241);
            ruleImportDecl();

            state._fsp--;

             after(grammarAccess.getImportDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportDecl248); 

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
    // $ANTLR end "entryRuleImportDecl"


    // $ANTLR start "ruleImportDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:153:1: ruleImportDecl : ( ( rule__ImportDecl__Alternatives ) ) ;
    public final void ruleImportDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:157:2: ( ( ( rule__ImportDecl__Alternatives ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:158:1: ( ( rule__ImportDecl__Alternatives ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:158:1: ( ( rule__ImportDecl__Alternatives ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:159:1: ( rule__ImportDecl__Alternatives )
            {
             before(grammarAccess.getImportDeclAccess().getAlternatives()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:160:1: ( rule__ImportDecl__Alternatives )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:160:2: rule__ImportDecl__Alternatives
            {
            pushFollow(FOLLOW_rule__ImportDecl__Alternatives_in_ruleImportDecl274);
            rule__ImportDecl__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getImportDeclAccess().getAlternatives()); 

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
    // $ANTLR end "ruleImportDecl"


    // $ANTLR start "entryRuleImportModule"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:172:1: entryRuleImportModule : ruleImportModule EOF ;
    public final void entryRuleImportModule() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:173:1: ( ruleImportModule EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:174:1: ruleImportModule EOF
            {
             before(grammarAccess.getImportModuleRule()); 
            pushFollow(FOLLOW_ruleImportModule_in_entryRuleImportModule301);
            ruleImportModule();

            state._fsp--;

             after(grammarAccess.getImportModuleRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportModule308); 

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
    // $ANTLR end "entryRuleImportModule"


    // $ANTLR start "ruleImportModule"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:181:1: ruleImportModule : ( ( rule__ImportModule__Group__0 ) ) ;
    public final void ruleImportModule() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:185:2: ( ( ( rule__ImportModule__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:186:1: ( ( rule__ImportModule__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:186:1: ( ( rule__ImportModule__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:187:1: ( rule__ImportModule__Group__0 )
            {
             before(grammarAccess.getImportModuleAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:188:1: ( rule__ImportModule__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:188:2: rule__ImportModule__Group__0
            {
            pushFollow(FOLLOW_rule__ImportModule__Group__0_in_ruleImportModule334);
            rule__ImportModule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportModuleAccess().getGroup()); 

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
    // $ANTLR end "ruleImportModule"


    // $ANTLR start "entryRuleImportMember"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:200:1: entryRuleImportMember : ruleImportMember EOF ;
    public final void entryRuleImportMember() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:201:1: ( ruleImportMember EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:202:1: ruleImportMember EOF
            {
             before(grammarAccess.getImportMemberRule()); 
            pushFollow(FOLLOW_ruleImportMember_in_entryRuleImportMember361);
            ruleImportMember();

            state._fsp--;

             after(grammarAccess.getImportMemberRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleImportMember368); 

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
    // $ANTLR end "entryRuleImportMember"


    // $ANTLR start "ruleImportMember"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:209:1: ruleImportMember : ( ( rule__ImportMember__Group__0 ) ) ;
    public final void ruleImportMember() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:213:2: ( ( ( rule__ImportMember__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:214:1: ( ( rule__ImportMember__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:214:1: ( ( rule__ImportMember__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:215:1: ( rule__ImportMember__Group__0 )
            {
             before(grammarAccess.getImportMemberAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:216:1: ( rule__ImportMember__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:216:2: rule__ImportMember__Group__0
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__0_in_ruleImportMember394);
            rule__ImportMember__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportMemberAccess().getGroup()); 

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
    // $ANTLR end "ruleImportMember"


    // $ANTLR start "entryRulePayloadTypeDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:228:1: entryRulePayloadTypeDecl : rulePayloadTypeDecl EOF ;
    public final void entryRulePayloadTypeDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:229:1: ( rulePayloadTypeDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:230:1: rulePayloadTypeDecl EOF
            {
             before(grammarAccess.getPayloadTypeDeclRule()); 
            pushFollow(FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl421);
            rulePayloadTypeDecl();

            state._fsp--;

             after(grammarAccess.getPayloadTypeDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadTypeDecl428); 

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
    // $ANTLR end "entryRulePayloadTypeDecl"


    // $ANTLR start "rulePayloadTypeDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:237:1: rulePayloadTypeDecl : ( ( rule__PayloadTypeDecl__Group__0 ) ) ;
    public final void rulePayloadTypeDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:241:2: ( ( ( rule__PayloadTypeDecl__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:242:1: ( ( rule__PayloadTypeDecl__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:242:1: ( ( rule__PayloadTypeDecl__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:243:1: ( rule__PayloadTypeDecl__Group__0 )
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:244:1: ( rule__PayloadTypeDecl__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:244:2: rule__PayloadTypeDecl__Group__0
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__0_in_rulePayloadTypeDecl454);
            rule__PayloadTypeDecl__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPayloadTypeDeclAccess().getGroup()); 

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
    // $ANTLR end "rulePayloadTypeDecl"


    // $ANTLR start "entryRuleMessageSignature"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:258:1: entryRuleMessageSignature : ruleMessageSignature EOF ;
    public final void entryRuleMessageSignature() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:259:1: ( ruleMessageSignature EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:260:1: ruleMessageSignature EOF
            {
             before(grammarAccess.getMessageSignatureRule()); 
            pushFollow(FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature483);
            ruleMessageSignature();

            state._fsp--;

             after(grammarAccess.getMessageSignatureRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessageSignature490); 

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
    // $ANTLR end "entryRuleMessageSignature"


    // $ANTLR start "ruleMessageSignature"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:267:1: ruleMessageSignature : ( ( rule__MessageSignature__Group__0 ) ) ;
    public final void ruleMessageSignature() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:271:2: ( ( ( rule__MessageSignature__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:272:1: ( ( rule__MessageSignature__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:272:1: ( ( rule__MessageSignature__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:273:1: ( rule__MessageSignature__Group__0 )
            {
             before(grammarAccess.getMessageSignatureAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:274:1: ( rule__MessageSignature__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:274:2: rule__MessageSignature__Group__0
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group__0_in_ruleMessageSignature516);
            rule__MessageSignature__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMessageSignatureAccess().getGroup()); 

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
    // $ANTLR end "ruleMessageSignature"


    // $ANTLR start "entryRulePayloadElement"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:286:1: entryRulePayloadElement : rulePayloadElement EOF ;
    public final void entryRulePayloadElement() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:287:1: ( rulePayloadElement EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:288:1: rulePayloadElement EOF
            {
             before(grammarAccess.getPayloadElementRule()); 
            pushFollow(FOLLOW_rulePayloadElement_in_entryRulePayloadElement543);
            rulePayloadElement();

            state._fsp--;

             after(grammarAccess.getPayloadElementRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRulePayloadElement550); 

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
    // $ANTLR end "entryRulePayloadElement"


    // $ANTLR start "rulePayloadElement"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:295:1: rulePayloadElement : ( ( rule__PayloadElement__Group__0 ) ) ;
    public final void rulePayloadElement() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:299:2: ( ( ( rule__PayloadElement__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:300:1: ( ( rule__PayloadElement__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:300:1: ( ( rule__PayloadElement__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:301:1: ( rule__PayloadElement__Group__0 )
            {
             before(grammarAccess.getPayloadElementAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:302:1: ( rule__PayloadElement__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:302:2: rule__PayloadElement__Group__0
            {
            pushFollow(FOLLOW_rule__PayloadElement__Group__0_in_rulePayloadElement576);
            rule__PayloadElement__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPayloadElementAccess().getGroup()); 

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
    // $ANTLR end "rulePayloadElement"


    // $ANTLR start "entryRuleGlobalProtocolDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:314:1: entryRuleGlobalProtocolDecl : ruleGlobalProtocolDecl EOF ;
    public final void entryRuleGlobalProtocolDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:315:1: ( ruleGlobalProtocolDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:316:1: ruleGlobalProtocolDecl EOF
            {
             before(grammarAccess.getGlobalProtocolDeclRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl603);
            ruleGlobalProtocolDecl();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolDecl610); 

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
    // $ANTLR end "entryRuleGlobalProtocolDecl"


    // $ANTLR start "ruleGlobalProtocolDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:323:1: ruleGlobalProtocolDecl : ( ( rule__GlobalProtocolDecl__Group__0 ) ) ;
    public final void ruleGlobalProtocolDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:327:2: ( ( ( rule__GlobalProtocolDecl__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:328:1: ( ( rule__GlobalProtocolDecl__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:328:1: ( ( rule__GlobalProtocolDecl__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:329:1: ( rule__GlobalProtocolDecl__Group__0 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:330:1: ( rule__GlobalProtocolDecl__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:330:2: rule__GlobalProtocolDecl__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__0_in_ruleGlobalProtocolDecl636);
            rule__GlobalProtocolDecl__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalProtocolDecl"


    // $ANTLR start "entryRuleRoleDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:342:1: entryRuleRoleDecl : ruleRoleDecl EOF ;
    public final void entryRuleRoleDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:343:1: ( ruleRoleDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:344:1: ruleRoleDecl EOF
            {
             before(grammarAccess.getRoleDeclRule()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl663);
            ruleRoleDecl();

            state._fsp--;

             after(grammarAccess.getRoleDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleDecl670); 

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
    // $ANTLR end "entryRuleRoleDecl"


    // $ANTLR start "ruleRoleDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:351:1: ruleRoleDecl : ( ( rule__RoleDecl__Group__0 ) ) ;
    public final void ruleRoleDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:355:2: ( ( ( rule__RoleDecl__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:356:1: ( ( rule__RoleDecl__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:356:1: ( ( rule__RoleDecl__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:357:1: ( rule__RoleDecl__Group__0 )
            {
             before(grammarAccess.getRoleDeclAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:358:1: ( rule__RoleDecl__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:358:2: rule__RoleDecl__Group__0
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group__0_in_ruleRoleDecl696);
            rule__RoleDecl__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRoleDeclAccess().getGroup()); 

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
    // $ANTLR end "ruleRoleDecl"


    // $ANTLR start "entryRuleParameterDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:370:1: entryRuleParameterDecl : ruleParameterDecl EOF ;
    public final void entryRuleParameterDecl() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:371:1: ( ruleParameterDecl EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:372:1: ruleParameterDecl EOF
            {
             before(grammarAccess.getParameterDeclRule()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl723);
            ruleParameterDecl();

            state._fsp--;

             after(grammarAccess.getParameterDeclRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleParameterDecl730); 

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
    // $ANTLR end "entryRuleParameterDecl"


    // $ANTLR start "ruleParameterDecl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:379:1: ruleParameterDecl : ( ( rule__ParameterDecl__Alternatives ) ) ;
    public final void ruleParameterDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:383:2: ( ( ( rule__ParameterDecl__Alternatives ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:384:1: ( ( rule__ParameterDecl__Alternatives ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:384:1: ( ( rule__ParameterDecl__Alternatives ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:385:1: ( rule__ParameterDecl__Alternatives )
            {
             before(grammarAccess.getParameterDeclAccess().getAlternatives()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:386:1: ( rule__ParameterDecl__Alternatives )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:386:2: rule__ParameterDecl__Alternatives
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Alternatives_in_ruleParameterDecl756);
            rule__ParameterDecl__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getParameterDeclAccess().getAlternatives()); 

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
    // $ANTLR end "ruleParameterDecl"


    // $ANTLR start "entryRuleRoleInstantiation"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:398:1: entryRuleRoleInstantiation : ruleRoleInstantiation EOF ;
    public final void entryRuleRoleInstantiation() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:399:1: ( ruleRoleInstantiation EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:400:1: ruleRoleInstantiation EOF
            {
             before(grammarAccess.getRoleInstantiationRule()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation783);
            ruleRoleInstantiation();

            state._fsp--;

             after(grammarAccess.getRoleInstantiationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoleInstantiation790); 

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
    // $ANTLR end "entryRuleRoleInstantiation"


    // $ANTLR start "ruleRoleInstantiation"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:407:1: ruleRoleInstantiation : ( ( rule__RoleInstantiation__Group__0 ) ) ;
    public final void ruleRoleInstantiation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:411:2: ( ( ( rule__RoleInstantiation__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:412:1: ( ( rule__RoleInstantiation__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:412:1: ( ( rule__RoleInstantiation__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:413:1: ( rule__RoleInstantiation__Group__0 )
            {
             before(grammarAccess.getRoleInstantiationAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:414:1: ( rule__RoleInstantiation__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:414:2: rule__RoleInstantiation__Group__0
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__Group__0_in_ruleRoleInstantiation816);
            rule__RoleInstantiation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRoleInstantiationAccess().getGroup()); 

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
    // $ANTLR end "ruleRoleInstantiation"


    // $ANTLR start "entryRuleArgument"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:426:1: entryRuleArgument : ruleArgument EOF ;
    public final void entryRuleArgument() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:427:1: ( ruleArgument EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:428:1: ruleArgument EOF
            {
             before(grammarAccess.getArgumentRule()); 
            pushFollow(FOLLOW_ruleArgument_in_entryRuleArgument843);
            ruleArgument();

            state._fsp--;

             after(grammarAccess.getArgumentRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleArgument850); 

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
    // $ANTLR end "entryRuleArgument"


    // $ANTLR start "ruleArgument"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:435:1: ruleArgument : ( ( rule__Argument__Alternatives ) ) ;
    public final void ruleArgument() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:439:2: ( ( ( rule__Argument__Alternatives ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:440:1: ( ( rule__Argument__Alternatives ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:440:1: ( ( rule__Argument__Alternatives ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:441:1: ( rule__Argument__Alternatives )
            {
             before(grammarAccess.getArgumentAccess().getAlternatives()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:442:1: ( rule__Argument__Alternatives )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:442:2: rule__Argument__Alternatives
            {
            pushFollow(FOLLOW_rule__Argument__Alternatives_in_ruleArgument876);
            rule__Argument__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getArgumentAccess().getAlternatives()); 

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
    // $ANTLR end "ruleArgument"


    // $ANTLR start "entryRuleGlobalProtocolBlock"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:454:1: entryRuleGlobalProtocolBlock : ruleGlobalProtocolBlock EOF ;
    public final void entryRuleGlobalProtocolBlock() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:455:1: ( ruleGlobalProtocolBlock EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:456:1: ruleGlobalProtocolBlock EOF
            {
             before(grammarAccess.getGlobalProtocolBlockRule()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock903);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolBlockRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalProtocolBlock910); 

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
    // $ANTLR end "entryRuleGlobalProtocolBlock"


    // $ANTLR start "ruleGlobalProtocolBlock"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:463:1: ruleGlobalProtocolBlock : ( ( rule__GlobalProtocolBlock__Group__0 ) ) ;
    public final void ruleGlobalProtocolBlock() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:467:2: ( ( ( rule__GlobalProtocolBlock__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:468:1: ( ( rule__GlobalProtocolBlock__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:468:1: ( ( rule__GlobalProtocolBlock__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:469:1: ( rule__GlobalProtocolBlock__Group__0 )
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:470:1: ( rule__GlobalProtocolBlock__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:470:2: rule__GlobalProtocolBlock__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__0_in_ruleGlobalProtocolBlock936);
            rule__GlobalProtocolBlock__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolBlockAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalProtocolBlock"


    // $ANTLR start "entryRuleGlobalInteraction"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:482:1: entryRuleGlobalInteraction : ruleGlobalInteraction EOF ;
    public final void entryRuleGlobalInteraction() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:483:1: ( ruleGlobalInteraction EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:484:1: ruleGlobalInteraction EOF
            {
             before(grammarAccess.getGlobalInteractionRule()); 
            pushFollow(FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction963);
            ruleGlobalInteraction();

            state._fsp--;

             after(grammarAccess.getGlobalInteractionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInteraction970); 

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
    // $ANTLR end "entryRuleGlobalInteraction"


    // $ANTLR start "ruleGlobalInteraction"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:491:1: ruleGlobalInteraction : ( ( rule__GlobalInteraction__Alternatives ) ) ;
    public final void ruleGlobalInteraction() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:495:2: ( ( ( rule__GlobalInteraction__Alternatives ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:496:1: ( ( rule__GlobalInteraction__Alternatives ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:496:1: ( ( rule__GlobalInteraction__Alternatives ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:497:1: ( rule__GlobalInteraction__Alternatives )
            {
             before(grammarAccess.getGlobalInteractionAccess().getAlternatives()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:498:1: ( rule__GlobalInteraction__Alternatives )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:498:2: rule__GlobalInteraction__Alternatives
            {
            pushFollow(FOLLOW_rule__GlobalInteraction__Alternatives_in_ruleGlobalInteraction996);
            rule__GlobalInteraction__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInteractionAccess().getAlternatives()); 

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
    // $ANTLR end "ruleGlobalInteraction"


    // $ANTLR start "entryRuleGlobalMessageTransfer"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:510:1: entryRuleGlobalMessageTransfer : ruleGlobalMessageTransfer EOF ;
    public final void entryRuleGlobalMessageTransfer() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:511:1: ( ruleGlobalMessageTransfer EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:512:1: ruleGlobalMessageTransfer EOF
            {
             before(grammarAccess.getGlobalMessageTransferRule()); 
            pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer1023);
            ruleGlobalMessageTransfer();

            state._fsp--;

             after(grammarAccess.getGlobalMessageTransferRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalMessageTransfer1030); 

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
    // $ANTLR end "entryRuleGlobalMessageTransfer"


    // $ANTLR start "ruleGlobalMessageTransfer"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:519:1: ruleGlobalMessageTransfer : ( ( rule__GlobalMessageTransfer__Group__0 ) ) ;
    public final void ruleGlobalMessageTransfer() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:523:2: ( ( ( rule__GlobalMessageTransfer__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:524:1: ( ( rule__GlobalMessageTransfer__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:524:1: ( ( rule__GlobalMessageTransfer__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:525:1: ( rule__GlobalMessageTransfer__Group__0 )
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:526:1: ( rule__GlobalMessageTransfer__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:526:2: rule__GlobalMessageTransfer__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__0_in_ruleGlobalMessageTransfer1056);
            rule__GlobalMessageTransfer__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalMessageTransferAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalMessageTransfer"


    // $ANTLR start "entryRuleMessage"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:538:1: entryRuleMessage : ruleMessage EOF ;
    public final void entryRuleMessage() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:539:1: ( ruleMessage EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:540:1: ruleMessage EOF
            {
             before(grammarAccess.getMessageRule()); 
            pushFollow(FOLLOW_ruleMessage_in_entryRuleMessage1083);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getMessageRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMessage1090); 

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
    // $ANTLR end "entryRuleMessage"


    // $ANTLR start "ruleMessage"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:547:1: ruleMessage : ( ( rule__Message__Alternatives ) ) ;
    public final void ruleMessage() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:551:2: ( ( ( rule__Message__Alternatives ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:552:1: ( ( rule__Message__Alternatives ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:552:1: ( ( rule__Message__Alternatives ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:553:1: ( rule__Message__Alternatives )
            {
             before(grammarAccess.getMessageAccess().getAlternatives()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:554:1: ( rule__Message__Alternatives )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:554:2: rule__Message__Alternatives
            {
            pushFollow(FOLLOW_rule__Message__Alternatives_in_ruleMessage1116);
            rule__Message__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getMessageAccess().getAlternatives()); 

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
    // $ANTLR end "ruleMessage"


    // $ANTLR start "entryRuleGlobalChoice"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:566:1: entryRuleGlobalChoice : ruleGlobalChoice EOF ;
    public final void entryRuleGlobalChoice() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:567:1: ( ruleGlobalChoice EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:568:1: ruleGlobalChoice EOF
            {
             before(grammarAccess.getGlobalChoiceRule()); 
            pushFollow(FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice1143);
            ruleGlobalChoice();

            state._fsp--;

             after(grammarAccess.getGlobalChoiceRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalChoice1150); 

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
    // $ANTLR end "entryRuleGlobalChoice"


    // $ANTLR start "ruleGlobalChoice"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:575:1: ruleGlobalChoice : ( ( rule__GlobalChoice__Group__0 ) ) ;
    public final void ruleGlobalChoice() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:579:2: ( ( ( rule__GlobalChoice__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:580:1: ( ( rule__GlobalChoice__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:580:1: ( ( rule__GlobalChoice__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:581:1: ( rule__GlobalChoice__Group__0 )
            {
             before(grammarAccess.getGlobalChoiceAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:582:1: ( rule__GlobalChoice__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:582:2: rule__GlobalChoice__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__0_in_ruleGlobalChoice1176);
            rule__GlobalChoice__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalChoiceAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalChoice"


    // $ANTLR start "entryRuleGlobalRecursion"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:594:1: entryRuleGlobalRecursion : ruleGlobalRecursion EOF ;
    public final void entryRuleGlobalRecursion() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:595:1: ( ruleGlobalRecursion EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:596:1: ruleGlobalRecursion EOF
            {
             before(grammarAccess.getGlobalRecursionRule()); 
            pushFollow(FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion1203);
            ruleGlobalRecursion();

            state._fsp--;

             after(grammarAccess.getGlobalRecursionRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalRecursion1210); 

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
    // $ANTLR end "entryRuleGlobalRecursion"


    // $ANTLR start "ruleGlobalRecursion"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:603:1: ruleGlobalRecursion : ( ( rule__GlobalRecursion__Group__0 ) ) ;
    public final void ruleGlobalRecursion() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:607:2: ( ( ( rule__GlobalRecursion__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:608:1: ( ( rule__GlobalRecursion__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:608:1: ( ( rule__GlobalRecursion__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:609:1: ( rule__GlobalRecursion__Group__0 )
            {
             before(grammarAccess.getGlobalRecursionAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:610:1: ( rule__GlobalRecursion__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:610:2: rule__GlobalRecursion__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__Group__0_in_ruleGlobalRecursion1236);
            rule__GlobalRecursion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalRecursionAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalRecursion"


    // $ANTLR start "entryRuleGlobalContinue"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:622:1: entryRuleGlobalContinue : ruleGlobalContinue EOF ;
    public final void entryRuleGlobalContinue() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:623:1: ( ruleGlobalContinue EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:624:1: ruleGlobalContinue EOF
            {
             before(grammarAccess.getGlobalContinueRule()); 
            pushFollow(FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue1263);
            ruleGlobalContinue();

            state._fsp--;

             after(grammarAccess.getGlobalContinueRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalContinue1270); 

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
    // $ANTLR end "entryRuleGlobalContinue"


    // $ANTLR start "ruleGlobalContinue"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:631:1: ruleGlobalContinue : ( ( rule__GlobalContinue__Group__0 ) ) ;
    public final void ruleGlobalContinue() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:635:2: ( ( ( rule__GlobalContinue__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:636:1: ( ( rule__GlobalContinue__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:636:1: ( ( rule__GlobalContinue__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:637:1: ( rule__GlobalContinue__Group__0 )
            {
             before(grammarAccess.getGlobalContinueAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:638:1: ( rule__GlobalContinue__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:638:2: rule__GlobalContinue__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalContinue__Group__0_in_ruleGlobalContinue1296);
            rule__GlobalContinue__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalContinueAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalContinue"


    // $ANTLR start "entryRuleGlobalParallel"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:650:1: entryRuleGlobalParallel : ruleGlobalParallel EOF ;
    public final void entryRuleGlobalParallel() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:651:1: ( ruleGlobalParallel EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:652:1: ruleGlobalParallel EOF
            {
             before(grammarAccess.getGlobalParallelRule()); 
            pushFollow(FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel1323);
            ruleGlobalParallel();

            state._fsp--;

             after(grammarAccess.getGlobalParallelRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalParallel1330); 

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
    // $ANTLR end "entryRuleGlobalParallel"


    // $ANTLR start "ruleGlobalParallel"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:659:1: ruleGlobalParallel : ( ( rule__GlobalParallel__Group__0 ) ) ;
    public final void ruleGlobalParallel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:663:2: ( ( ( rule__GlobalParallel__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:664:1: ( ( rule__GlobalParallel__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:664:1: ( ( rule__GlobalParallel__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:665:1: ( rule__GlobalParallel__Group__0 )
            {
             before(grammarAccess.getGlobalParallelAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:666:1: ( rule__GlobalParallel__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:666:2: rule__GlobalParallel__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group__0_in_ruleGlobalParallel1356);
            rule__GlobalParallel__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalParallelAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalParallel"


    // $ANTLR start "entryRuleGlobalInterruptible"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:678:1: entryRuleGlobalInterruptible : ruleGlobalInterruptible EOF ;
    public final void entryRuleGlobalInterruptible() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:679:1: ( ruleGlobalInterruptible EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:680:1: ruleGlobalInterruptible EOF
            {
             before(grammarAccess.getGlobalInterruptibleRule()); 
            pushFollow(FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible1383);
            ruleGlobalInterruptible();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptibleRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterruptible1390); 

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
    // $ANTLR end "entryRuleGlobalInterruptible"


    // $ANTLR start "ruleGlobalInterruptible"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:687:1: ruleGlobalInterruptible : ( ( rule__GlobalInterruptible__Group__0 ) ) ;
    public final void ruleGlobalInterruptible() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:691:2: ( ( ( rule__GlobalInterruptible__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:692:1: ( ( rule__GlobalInterruptible__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:692:1: ( ( rule__GlobalInterruptible__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:693:1: ( rule__GlobalInterruptible__Group__0 )
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:694:1: ( rule__GlobalInterruptible__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:694:2: rule__GlobalInterruptible__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__0_in_ruleGlobalInterruptible1416);
            rule__GlobalInterruptible__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptibleAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalInterruptible"


    // $ANTLR start "entryRuleGlobalInterrupt"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:706:1: entryRuleGlobalInterrupt : ruleGlobalInterrupt EOF ;
    public final void entryRuleGlobalInterrupt() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:707:1: ( ruleGlobalInterrupt EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:708:1: ruleGlobalInterrupt EOF
            {
             before(grammarAccess.getGlobalInterruptRule()); 
            pushFollow(FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt1443);
            ruleGlobalInterrupt();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalInterrupt1450); 

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
    // $ANTLR end "entryRuleGlobalInterrupt"


    // $ANTLR start "ruleGlobalInterrupt"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:715:1: ruleGlobalInterrupt : ( ( rule__GlobalInterrupt__Group__0 ) ) ;
    public final void ruleGlobalInterrupt() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:719:2: ( ( ( rule__GlobalInterrupt__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:720:1: ( ( rule__GlobalInterrupt__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:720:1: ( ( rule__GlobalInterrupt__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:721:1: ( rule__GlobalInterrupt__Group__0 )
            {
             before(grammarAccess.getGlobalInterruptAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:722:1: ( rule__GlobalInterrupt__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:722:2: rule__GlobalInterrupt__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__0_in_ruleGlobalInterrupt1476);
            rule__GlobalInterrupt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalInterrupt"


    // $ANTLR start "entryRuleGlobalDo"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:734:1: entryRuleGlobalDo : ruleGlobalDo EOF ;
    public final void entryRuleGlobalDo() throws RecognitionException {
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:735:1: ( ruleGlobalDo EOF )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:736:1: ruleGlobalDo EOF
            {
             before(grammarAccess.getGlobalDoRule()); 
            pushFollow(FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo1503);
            ruleGlobalDo();

            state._fsp--;

             after(grammarAccess.getGlobalDoRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleGlobalDo1510); 

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
    // $ANTLR end "entryRuleGlobalDo"


    // $ANTLR start "ruleGlobalDo"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:743:1: ruleGlobalDo : ( ( rule__GlobalDo__Group__0 ) ) ;
    public final void ruleGlobalDo() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:747:2: ( ( ( rule__GlobalDo__Group__0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:748:1: ( ( rule__GlobalDo__Group__0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:748:1: ( ( rule__GlobalDo__Group__0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:749:1: ( rule__GlobalDo__Group__0 )
            {
             before(grammarAccess.getGlobalDoAccess().getGroup()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:750:1: ( rule__GlobalDo__Group__0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:750:2: rule__GlobalDo__Group__0
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__0_in_ruleGlobalDo1536);
            rule__GlobalDo__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getGroup()); 

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
    // $ANTLR end "ruleGlobalDo"


    // $ANTLR start "rule__ImportDecl__Alternatives"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:762:1: rule__ImportDecl__Alternatives : ( ( ruleImportModule ) | ( ruleImportMember ) );
    public final void rule__ImportDecl__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:766:1: ( ( ruleImportModule ) | ( ruleImportMember ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==14) ) {
                alt1=1;
            }
            else if ( (LA1_0==16) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:767:1: ( ruleImportModule )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:767:1: ( ruleImportModule )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:768:1: ruleImportModule
                    {
                     before(grammarAccess.getImportDeclAccess().getImportModuleParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleImportModule_in_rule__ImportDecl__Alternatives1572);
                    ruleImportModule();

                    state._fsp--;

                     after(grammarAccess.getImportDeclAccess().getImportModuleParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:773:6: ( ruleImportMember )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:773:6: ( ruleImportMember )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:774:1: ruleImportMember
                    {
                     before(grammarAccess.getImportDeclAccess().getImportMemberParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleImportMember_in_rule__ImportDecl__Alternatives1589);
                    ruleImportMember();

                    state._fsp--;

                     after(grammarAccess.getImportDeclAccess().getImportMemberParserRuleCall_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__ImportDecl__Alternatives"


    // $ANTLR start "rule__GlobalProtocolDecl__Alternatives_8"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:784:1: rule__GlobalProtocolDecl__Alternatives_8 : ( ( ( rule__GlobalProtocolDecl__BlockAssignment_8_0 ) ) | ( ( rule__GlobalProtocolDecl__Group_8_1__0 ) ) );
    public final void rule__GlobalProtocolDecl__Alternatives_8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:788:1: ( ( ( rule__GlobalProtocolDecl__BlockAssignment_8_0 ) ) | ( ( rule__GlobalProtocolDecl__Group_8_1__0 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==29) ) {
                alt2=1;
            }
            else if ( (LA2_0==26) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:789:1: ( ( rule__GlobalProtocolDecl__BlockAssignment_8_0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:789:1: ( ( rule__GlobalProtocolDecl__BlockAssignment_8_0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:790:1: ( rule__GlobalProtocolDecl__BlockAssignment_8_0 )
                    {
                     before(grammarAccess.getGlobalProtocolDeclAccess().getBlockAssignment_8_0()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:791:1: ( rule__GlobalProtocolDecl__BlockAssignment_8_0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:791:2: rule__GlobalProtocolDecl__BlockAssignment_8_0
                    {
                    pushFollow(FOLLOW_rule__GlobalProtocolDecl__BlockAssignment_8_0_in_rule__GlobalProtocolDecl__Alternatives_81621);
                    rule__GlobalProtocolDecl__BlockAssignment_8_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getGlobalProtocolDeclAccess().getBlockAssignment_8_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:795:6: ( ( rule__GlobalProtocolDecl__Group_8_1__0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:795:6: ( ( rule__GlobalProtocolDecl__Group_8_1__0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:796:1: ( rule__GlobalProtocolDecl__Group_8_1__0 )
                    {
                     before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:797:1: ( rule__GlobalProtocolDecl__Group_8_1__0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:797:2: rule__GlobalProtocolDecl__Group_8_1__0
                    {
                    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__0_in_rule__GlobalProtocolDecl__Alternatives_81639);
                    rule__GlobalProtocolDecl__Group_8_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__GlobalProtocolDecl__Alternatives_8"


    // $ANTLR start "rule__ParameterDecl__Alternatives"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:806:1: rule__ParameterDecl__Alternatives : ( ( ( rule__ParameterDecl__Group_0__0 ) ) | ( ( rule__ParameterDecl__Group_1__0 ) ) );
    public final void rule__ParameterDecl__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:810:1: ( ( ( rule__ParameterDecl__Group_0__0 ) ) | ( ( rule__ParameterDecl__Group_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==17) ) {
                alt3=1;
            }
            else if ( (LA3_0==28) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:811:1: ( ( rule__ParameterDecl__Group_0__0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:811:1: ( ( rule__ParameterDecl__Group_0__0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:812:1: ( rule__ParameterDecl__Group_0__0 )
                    {
                     before(grammarAccess.getParameterDeclAccess().getGroup_0()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:813:1: ( rule__ParameterDecl__Group_0__0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:813:2: rule__ParameterDecl__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__ParameterDecl__Group_0__0_in_rule__ParameterDecl__Alternatives1672);
                    rule__ParameterDecl__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getParameterDeclAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:817:6: ( ( rule__ParameterDecl__Group_1__0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:817:6: ( ( rule__ParameterDecl__Group_1__0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:818:1: ( rule__ParameterDecl__Group_1__0 )
                    {
                     before(grammarAccess.getParameterDeclAccess().getGroup_1()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:819:1: ( rule__ParameterDecl__Group_1__0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:819:2: rule__ParameterDecl__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__ParameterDecl__Group_1__0_in_rule__ParameterDecl__Alternatives1690);
                    rule__ParameterDecl__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getParameterDeclAccess().getGroup_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__ParameterDecl__Alternatives"


    // $ANTLR start "rule__Argument__Alternatives"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:828:1: rule__Argument__Alternatives : ( ( ( rule__Argument__Group_0__0 ) ) | ( ( rule__Argument__Group_1__0 ) ) );
    public final void rule__Argument__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:832:1: ( ( ( rule__Argument__Group_0__0 ) ) | ( ( rule__Argument__Group_1__0 ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_ID) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==20) ) {
                    alt4=1;
                }
                else if ( (LA4_1==EOF||LA4_1==15||LA4_1==19||LA4_1==22) ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:833:1: ( ( rule__Argument__Group_0__0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:833:1: ( ( rule__Argument__Group_0__0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:834:1: ( rule__Argument__Group_0__0 )
                    {
                     before(grammarAccess.getArgumentAccess().getGroup_0()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:835:1: ( rule__Argument__Group_0__0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:835:2: rule__Argument__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__Argument__Group_0__0_in_rule__Argument__Alternatives1723);
                    rule__Argument__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getArgumentAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:839:6: ( ( rule__Argument__Group_1__0 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:839:6: ( ( rule__Argument__Group_1__0 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:840:1: ( rule__Argument__Group_1__0 )
                    {
                     before(grammarAccess.getArgumentAccess().getGroup_1()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:841:1: ( rule__Argument__Group_1__0 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:841:2: rule__Argument__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__Argument__Group_1__0_in_rule__Argument__Alternatives1741);
                    rule__Argument__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getArgumentAccess().getGroup_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__Argument__Alternatives"


    // $ANTLR start "rule__GlobalInteraction__Alternatives"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:850:1: rule__GlobalInteraction__Alternatives : ( ( ruleGlobalMessageTransfer ) | ( ruleGlobalChoice ) | ( ruleGlobalRecursion ) | ( ruleGlobalContinue ) | ( ruleGlobalParallel ) | ( ruleGlobalInterruptible ) | ( ruleGlobalDo ) );
    public final void rule__GlobalInteraction__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:854:1: ( ( ruleGlobalMessageTransfer ) | ( ruleGlobalChoice ) | ( ruleGlobalRecursion ) | ( ruleGlobalContinue ) | ( ruleGlobalParallel ) | ( ruleGlobalInterruptible ) | ( ruleGlobalDo ) )
            int alt5=7;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt5=1;
                }
                break;
            case 32:
                {
                alt5=2;
                }
                break;
            case 35:
                {
                alt5=3;
                }
                break;
            case 36:
                {
                alt5=4;
                }
                break;
            case 37:
                {
                alt5=5;
                }
                break;
            case 39:
                {
                alt5=6;
                }
                break;
            case 42:
                {
                alt5=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:855:1: ( ruleGlobalMessageTransfer )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:855:1: ( ruleGlobalMessageTransfer )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:856:1: ruleGlobalMessageTransfer
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalMessageTransferParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleGlobalMessageTransfer_in_rule__GlobalInteraction__Alternatives1774);
                    ruleGlobalMessageTransfer();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalMessageTransferParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:861:6: ( ruleGlobalChoice )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:861:6: ( ruleGlobalChoice )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:862:1: ruleGlobalChoice
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalChoiceParserRuleCall_1()); 
                    pushFollow(FOLLOW_ruleGlobalChoice_in_rule__GlobalInteraction__Alternatives1791);
                    ruleGlobalChoice();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalChoiceParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:867:6: ( ruleGlobalRecursion )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:867:6: ( ruleGlobalRecursion )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:868:1: ruleGlobalRecursion
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalRecursionParserRuleCall_2()); 
                    pushFollow(FOLLOW_ruleGlobalRecursion_in_rule__GlobalInteraction__Alternatives1808);
                    ruleGlobalRecursion();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalRecursionParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:873:6: ( ruleGlobalContinue )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:873:6: ( ruleGlobalContinue )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:874:1: ruleGlobalContinue
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalContinueParserRuleCall_3()); 
                    pushFollow(FOLLOW_ruleGlobalContinue_in_rule__GlobalInteraction__Alternatives1825);
                    ruleGlobalContinue();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalContinueParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:879:6: ( ruleGlobalParallel )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:879:6: ( ruleGlobalParallel )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:880:1: ruleGlobalParallel
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalParallelParserRuleCall_4()); 
                    pushFollow(FOLLOW_ruleGlobalParallel_in_rule__GlobalInteraction__Alternatives1842);
                    ruleGlobalParallel();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalParallelParserRuleCall_4()); 

                    }


                    }
                    break;
                case 6 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:885:6: ( ruleGlobalInterruptible )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:885:6: ( ruleGlobalInterruptible )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:886:1: ruleGlobalInterruptible
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalInterruptibleParserRuleCall_5()); 
                    pushFollow(FOLLOW_ruleGlobalInterruptible_in_rule__GlobalInteraction__Alternatives1859);
                    ruleGlobalInterruptible();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalInterruptibleParserRuleCall_5()); 

                    }


                    }
                    break;
                case 7 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:891:6: ( ruleGlobalDo )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:891:6: ( ruleGlobalDo )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:892:1: ruleGlobalDo
                    {
                     before(grammarAccess.getGlobalInteractionAccess().getGlobalDoParserRuleCall_6()); 
                    pushFollow(FOLLOW_ruleGlobalDo_in_rule__GlobalInteraction__Alternatives1876);
                    ruleGlobalDo();

                    state._fsp--;

                     after(grammarAccess.getGlobalInteractionAccess().getGlobalDoParserRuleCall_6()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__GlobalInteraction__Alternatives"


    // $ANTLR start "rule__Message__Alternatives"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:902:1: rule__Message__Alternatives : ( ( ruleMessageSignature ) | ( ( rule__Message__ParameterAssignment_1 ) ) );
    public final void rule__Message__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:906:1: ( ( ruleMessageSignature ) | ( ( rule__Message__ParameterAssignment_1 ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_ID) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==EOF||LA6_1==16||LA6_1==22||LA6_1==41) ) {
                    alt6=2;
                }
                else if ( (LA6_1==20) ) {
                    alt6=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:907:1: ( ruleMessageSignature )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:907:1: ( ruleMessageSignature )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:908:1: ruleMessageSignature
                    {
                     before(grammarAccess.getMessageAccess().getMessageSignatureParserRuleCall_0()); 
                    pushFollow(FOLLOW_ruleMessageSignature_in_rule__Message__Alternatives1908);
                    ruleMessageSignature();

                    state._fsp--;

                     after(grammarAccess.getMessageAccess().getMessageSignatureParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:913:6: ( ( rule__Message__ParameterAssignment_1 ) )
                    {
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:913:6: ( ( rule__Message__ParameterAssignment_1 ) )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:914:1: ( rule__Message__ParameterAssignment_1 )
                    {
                     before(grammarAccess.getMessageAccess().getParameterAssignment_1()); 
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:915:1: ( rule__Message__ParameterAssignment_1 )
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:915:2: rule__Message__ParameterAssignment_1
                    {
                    pushFollow(FOLLOW_rule__Message__ParameterAssignment_1_in_rule__Message__Alternatives1925);
                    rule__Message__ParameterAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getMessageAccess().getParameterAssignment_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__Message__Alternatives"


    // $ANTLR start "rule__Module__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:926:1: rule__Module__Group__0 : rule__Module__Group__0__Impl rule__Module__Group__1 ;
    public final void rule__Module__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:930:1: ( rule__Module__Group__0__Impl rule__Module__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:931:2: rule__Module__Group__0__Impl rule__Module__Group__1
            {
            pushFollow(FOLLOW_rule__Module__Group__0__Impl_in_rule__Module__Group__01956);
            rule__Module__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Module__Group__1_in_rule__Module__Group__01959);
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
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:938:1: rule__Module__Group__0__Impl : ( ruleModuleDecl ) ;
    public final void rule__Module__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:942:1: ( ( ruleModuleDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:943:1: ( ruleModuleDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:943:1: ( ruleModuleDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:944:1: ruleModuleDecl
            {
             before(grammarAccess.getModuleAccess().getModuleDeclParserRuleCall_0()); 
            pushFollow(FOLLOW_ruleModuleDecl_in_rule__Module__Group__0__Impl1986);
            ruleModuleDecl();

            state._fsp--;

             after(grammarAccess.getModuleAccess().getModuleDeclParserRuleCall_0()); 

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
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:955:1: rule__Module__Group__1 : rule__Module__Group__1__Impl rule__Module__Group__2 ;
    public final void rule__Module__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:959:1: ( rule__Module__Group__1__Impl rule__Module__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:960:2: rule__Module__Group__1__Impl rule__Module__Group__2
            {
            pushFollow(FOLLOW_rule__Module__Group__1__Impl_in_rule__Module__Group__12015);
            rule__Module__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Module__Group__2_in_rule__Module__Group__12018);
            rule__Module__Group__2();

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
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:967:1: rule__Module__Group__1__Impl : ( ( rule__Module__ImportsAssignment_1 )* ) ;
    public final void rule__Module__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:971:1: ( ( ( rule__Module__ImportsAssignment_1 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:972:1: ( ( rule__Module__ImportsAssignment_1 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:972:1: ( ( rule__Module__ImportsAssignment_1 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:973:1: ( rule__Module__ImportsAssignment_1 )*
            {
             before(grammarAccess.getModuleAccess().getImportsAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:974:1: ( rule__Module__ImportsAssignment_1 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==14||LA7_0==16) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:974:2: rule__Module__ImportsAssignment_1
            	    {
            	    pushFollow(FOLLOW_rule__Module__ImportsAssignment_1_in_rule__Module__Group__1__Impl2045);
            	    rule__Module__ImportsAssignment_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

             after(grammarAccess.getModuleAccess().getImportsAssignment_1()); 

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


    // $ANTLR start "rule__Module__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:984:1: rule__Module__Group__2 : rule__Module__Group__2__Impl rule__Module__Group__3 ;
    public final void rule__Module__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:988:1: ( rule__Module__Group__2__Impl rule__Module__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:989:2: rule__Module__Group__2__Impl rule__Module__Group__3
            {
            pushFollow(FOLLOW_rule__Module__Group__2__Impl_in_rule__Module__Group__22076);
            rule__Module__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Module__Group__3_in_rule__Module__Group__22079);
            rule__Module__Group__3();

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
    // $ANTLR end "rule__Module__Group__2"


    // $ANTLR start "rule__Module__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:996:1: rule__Module__Group__2__Impl : ( ( rule__Module__TypesAssignment_2 )* ) ;
    public final void rule__Module__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1000:1: ( ( ( rule__Module__TypesAssignment_2 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1001:1: ( ( rule__Module__TypesAssignment_2 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1001:1: ( ( rule__Module__TypesAssignment_2 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1002:1: ( rule__Module__TypesAssignment_2 )*
            {
             before(grammarAccess.getModuleAccess().getTypesAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1003:1: ( rule__Module__TypesAssignment_2 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==17) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1003:2: rule__Module__TypesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__Module__TypesAssignment_2_in_rule__Module__Group__2__Impl2106);
            	    rule__Module__TypesAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getModuleAccess().getTypesAssignment_2()); 

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
    // $ANTLR end "rule__Module__Group__2__Impl"


    // $ANTLR start "rule__Module__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1013:1: rule__Module__Group__3 : rule__Module__Group__3__Impl ;
    public final void rule__Module__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1017:1: ( rule__Module__Group__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1018:2: rule__Module__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__Module__Group__3__Impl_in_rule__Module__Group__32137);
            rule__Module__Group__3__Impl();

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
    // $ANTLR end "rule__Module__Group__3"


    // $ANTLR start "rule__Module__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1024:1: rule__Module__Group__3__Impl : ( ( rule__Module__GlobalsAssignment_3 )* ) ;
    public final void rule__Module__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1028:1: ( ( ( rule__Module__GlobalsAssignment_3 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1029:1: ( ( rule__Module__GlobalsAssignment_3 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1029:1: ( ( rule__Module__GlobalsAssignment_3 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1030:1: ( rule__Module__GlobalsAssignment_3 )*
            {
             before(grammarAccess.getModuleAccess().getGlobalsAssignment_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1031:1: ( rule__Module__GlobalsAssignment_3 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==24) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1031:2: rule__Module__GlobalsAssignment_3
            	    {
            	    pushFollow(FOLLOW_rule__Module__GlobalsAssignment_3_in_rule__Module__Group__3__Impl2164);
            	    rule__Module__GlobalsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getModuleAccess().getGlobalsAssignment_3()); 

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
    // $ANTLR end "rule__Module__Group__3__Impl"


    // $ANTLR start "rule__ModuleDecl__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1049:1: rule__ModuleDecl__Group__0 : rule__ModuleDecl__Group__0__Impl rule__ModuleDecl__Group__1 ;
    public final void rule__ModuleDecl__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1053:1: ( rule__ModuleDecl__Group__0__Impl rule__ModuleDecl__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1054:2: rule__ModuleDecl__Group__0__Impl rule__ModuleDecl__Group__1
            {
            pushFollow(FOLLOW_rule__ModuleDecl__Group__0__Impl_in_rule__ModuleDecl__Group__02203);
            rule__ModuleDecl__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ModuleDecl__Group__1_in_rule__ModuleDecl__Group__02206);
            rule__ModuleDecl__Group__1();

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
    // $ANTLR end "rule__ModuleDecl__Group__0"


    // $ANTLR start "rule__ModuleDecl__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1061:1: rule__ModuleDecl__Group__0__Impl : ( 'module' ) ;
    public final void rule__ModuleDecl__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1065:1: ( ( 'module' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1066:1: ( 'module' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1066:1: ( 'module' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1067:1: 'module'
            {
             before(grammarAccess.getModuleDeclAccess().getModuleKeyword_0()); 
            match(input,11,FOLLOW_11_in_rule__ModuleDecl__Group__0__Impl2234); 
             after(grammarAccess.getModuleDeclAccess().getModuleKeyword_0()); 

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
    // $ANTLR end "rule__ModuleDecl__Group__0__Impl"


    // $ANTLR start "rule__ModuleDecl__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1080:1: rule__ModuleDecl__Group__1 : rule__ModuleDecl__Group__1__Impl rule__ModuleDecl__Group__2 ;
    public final void rule__ModuleDecl__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1084:1: ( rule__ModuleDecl__Group__1__Impl rule__ModuleDecl__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1085:2: rule__ModuleDecl__Group__1__Impl rule__ModuleDecl__Group__2
            {
            pushFollow(FOLLOW_rule__ModuleDecl__Group__1__Impl_in_rule__ModuleDecl__Group__12265);
            rule__ModuleDecl__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ModuleDecl__Group__2_in_rule__ModuleDecl__Group__12268);
            rule__ModuleDecl__Group__2();

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
    // $ANTLR end "rule__ModuleDecl__Group__1"


    // $ANTLR start "rule__ModuleDecl__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1092:1: rule__ModuleDecl__Group__1__Impl : ( ( rule__ModuleDecl__NameAssignment_1 ) ) ;
    public final void rule__ModuleDecl__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1096:1: ( ( ( rule__ModuleDecl__NameAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1097:1: ( ( rule__ModuleDecl__NameAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1097:1: ( ( rule__ModuleDecl__NameAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1098:1: ( rule__ModuleDecl__NameAssignment_1 )
            {
             before(grammarAccess.getModuleDeclAccess().getNameAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1099:1: ( rule__ModuleDecl__NameAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1099:2: rule__ModuleDecl__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__ModuleDecl__NameAssignment_1_in_rule__ModuleDecl__Group__1__Impl2295);
            rule__ModuleDecl__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getModuleDeclAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__ModuleDecl__Group__1__Impl"


    // $ANTLR start "rule__ModuleDecl__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1109:1: rule__ModuleDecl__Group__2 : rule__ModuleDecl__Group__2__Impl ;
    public final void rule__ModuleDecl__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1113:1: ( rule__ModuleDecl__Group__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1114:2: rule__ModuleDecl__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__ModuleDecl__Group__2__Impl_in_rule__ModuleDecl__Group__22325);
            rule__ModuleDecl__Group__2__Impl();

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
    // $ANTLR end "rule__ModuleDecl__Group__2"


    // $ANTLR start "rule__ModuleDecl__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1120:1: rule__ModuleDecl__Group__2__Impl : ( ';' ) ;
    public final void rule__ModuleDecl__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1124:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1125:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1125:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1126:1: ';'
            {
             before(grammarAccess.getModuleDeclAccess().getSemicolonKeyword_2()); 
            match(input,12,FOLLOW_12_in_rule__ModuleDecl__Group__2__Impl2353); 
             after(grammarAccess.getModuleDeclAccess().getSemicolonKeyword_2()); 

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
    // $ANTLR end "rule__ModuleDecl__Group__2__Impl"


    // $ANTLR start "rule__ModuleName__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1145:1: rule__ModuleName__Group__0 : rule__ModuleName__Group__0__Impl rule__ModuleName__Group__1 ;
    public final void rule__ModuleName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1149:1: ( rule__ModuleName__Group__0__Impl rule__ModuleName__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1150:2: rule__ModuleName__Group__0__Impl rule__ModuleName__Group__1
            {
            pushFollow(FOLLOW_rule__ModuleName__Group__0__Impl_in_rule__ModuleName__Group__02390);
            rule__ModuleName__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ModuleName__Group__1_in_rule__ModuleName__Group__02393);
            rule__ModuleName__Group__1();

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
    // $ANTLR end "rule__ModuleName__Group__0"


    // $ANTLR start "rule__ModuleName__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1157:1: rule__ModuleName__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__ModuleName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1161:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1162:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1162:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1163:1: RULE_ID
            {
             before(grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ModuleName__Group__0__Impl2420); 
             after(grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_0()); 

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
    // $ANTLR end "rule__ModuleName__Group__0__Impl"


    // $ANTLR start "rule__ModuleName__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1174:1: rule__ModuleName__Group__1 : rule__ModuleName__Group__1__Impl ;
    public final void rule__ModuleName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1178:1: ( rule__ModuleName__Group__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1179:2: rule__ModuleName__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__ModuleName__Group__1__Impl_in_rule__ModuleName__Group__12449);
            rule__ModuleName__Group__1__Impl();

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
    // $ANTLR end "rule__ModuleName__Group__1"


    // $ANTLR start "rule__ModuleName__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1185:1: rule__ModuleName__Group__1__Impl : ( ( rule__ModuleName__Group_1__0 )* ) ;
    public final void rule__ModuleName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1189:1: ( ( ( rule__ModuleName__Group_1__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1190:1: ( ( rule__ModuleName__Group_1__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1190:1: ( ( rule__ModuleName__Group_1__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1191:1: ( rule__ModuleName__Group_1__0 )*
            {
             before(grammarAccess.getModuleNameAccess().getGroup_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1192:1: ( rule__ModuleName__Group_1__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==13) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1192:2: rule__ModuleName__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__ModuleName__Group_1__0_in_rule__ModuleName__Group__1__Impl2476);
            	    rule__ModuleName__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getModuleNameAccess().getGroup_1()); 

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
    // $ANTLR end "rule__ModuleName__Group__1__Impl"


    // $ANTLR start "rule__ModuleName__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1206:1: rule__ModuleName__Group_1__0 : rule__ModuleName__Group_1__0__Impl rule__ModuleName__Group_1__1 ;
    public final void rule__ModuleName__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1210:1: ( rule__ModuleName__Group_1__0__Impl rule__ModuleName__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1211:2: rule__ModuleName__Group_1__0__Impl rule__ModuleName__Group_1__1
            {
            pushFollow(FOLLOW_rule__ModuleName__Group_1__0__Impl_in_rule__ModuleName__Group_1__02511);
            rule__ModuleName__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ModuleName__Group_1__1_in_rule__ModuleName__Group_1__02514);
            rule__ModuleName__Group_1__1();

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
    // $ANTLR end "rule__ModuleName__Group_1__0"


    // $ANTLR start "rule__ModuleName__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1218:1: rule__ModuleName__Group_1__0__Impl : ( '.' ) ;
    public final void rule__ModuleName__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1222:1: ( ( '.' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1223:1: ( '.' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1223:1: ( '.' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1224:1: '.'
            {
             before(grammarAccess.getModuleNameAccess().getFullStopKeyword_1_0()); 
            match(input,13,FOLLOW_13_in_rule__ModuleName__Group_1__0__Impl2542); 
             after(grammarAccess.getModuleNameAccess().getFullStopKeyword_1_0()); 

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
    // $ANTLR end "rule__ModuleName__Group_1__0__Impl"


    // $ANTLR start "rule__ModuleName__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1237:1: rule__ModuleName__Group_1__1 : rule__ModuleName__Group_1__1__Impl ;
    public final void rule__ModuleName__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1241:1: ( rule__ModuleName__Group_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1242:2: rule__ModuleName__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__ModuleName__Group_1__1__Impl_in_rule__ModuleName__Group_1__12573);
            rule__ModuleName__Group_1__1__Impl();

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
    // $ANTLR end "rule__ModuleName__Group_1__1"


    // $ANTLR start "rule__ModuleName__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1248:1: rule__ModuleName__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__ModuleName__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1252:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1253:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1253:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1254:1: RULE_ID
            {
             before(grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_1_1()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ModuleName__Group_1__1__Impl2600); 
             after(grammarAccess.getModuleNameAccess().getIDTerminalRuleCall_1_1()); 

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
    // $ANTLR end "rule__ModuleName__Group_1__1__Impl"


    // $ANTLR start "rule__ImportModule__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1269:1: rule__ImportModule__Group__0 : rule__ImportModule__Group__0__Impl rule__ImportModule__Group__1 ;
    public final void rule__ImportModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1273:1: ( rule__ImportModule__Group__0__Impl rule__ImportModule__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1274:2: rule__ImportModule__Group__0__Impl rule__ImportModule__Group__1
            {
            pushFollow(FOLLOW_rule__ImportModule__Group__0__Impl_in_rule__ImportModule__Group__02633);
            rule__ImportModule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportModule__Group__1_in_rule__ImportModule__Group__02636);
            rule__ImportModule__Group__1();

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
    // $ANTLR end "rule__ImportModule__Group__0"


    // $ANTLR start "rule__ImportModule__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1281:1: rule__ImportModule__Group__0__Impl : ( 'import' ) ;
    public final void rule__ImportModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1285:1: ( ( 'import' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1286:1: ( 'import' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1286:1: ( 'import' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1287:1: 'import'
            {
             before(grammarAccess.getImportModuleAccess().getImportKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__ImportModule__Group__0__Impl2664); 
             after(grammarAccess.getImportModuleAccess().getImportKeyword_0()); 

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
    // $ANTLR end "rule__ImportModule__Group__0__Impl"


    // $ANTLR start "rule__ImportModule__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1300:1: rule__ImportModule__Group__1 : rule__ImportModule__Group__1__Impl rule__ImportModule__Group__2 ;
    public final void rule__ImportModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1304:1: ( rule__ImportModule__Group__1__Impl rule__ImportModule__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1305:2: rule__ImportModule__Group__1__Impl rule__ImportModule__Group__2
            {
            pushFollow(FOLLOW_rule__ImportModule__Group__1__Impl_in_rule__ImportModule__Group__12695);
            rule__ImportModule__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportModule__Group__2_in_rule__ImportModule__Group__12698);
            rule__ImportModule__Group__2();

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
    // $ANTLR end "rule__ImportModule__Group__1"


    // $ANTLR start "rule__ImportModule__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1312:1: rule__ImportModule__Group__1__Impl : ( ( rule__ImportModule__NameAssignment_1 ) ) ;
    public final void rule__ImportModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1316:1: ( ( ( rule__ImportModule__NameAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1317:1: ( ( rule__ImportModule__NameAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1317:1: ( ( rule__ImportModule__NameAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1318:1: ( rule__ImportModule__NameAssignment_1 )
            {
             before(grammarAccess.getImportModuleAccess().getNameAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1319:1: ( rule__ImportModule__NameAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1319:2: rule__ImportModule__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__ImportModule__NameAssignment_1_in_rule__ImportModule__Group__1__Impl2725);
            rule__ImportModule__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportModuleAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__ImportModule__Group__1__Impl"


    // $ANTLR start "rule__ImportModule__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1329:1: rule__ImportModule__Group__2 : rule__ImportModule__Group__2__Impl rule__ImportModule__Group__3 ;
    public final void rule__ImportModule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1333:1: ( rule__ImportModule__Group__2__Impl rule__ImportModule__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1334:2: rule__ImportModule__Group__2__Impl rule__ImportModule__Group__3
            {
            pushFollow(FOLLOW_rule__ImportModule__Group__2__Impl_in_rule__ImportModule__Group__22755);
            rule__ImportModule__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportModule__Group__3_in_rule__ImportModule__Group__22758);
            rule__ImportModule__Group__3();

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
    // $ANTLR end "rule__ImportModule__Group__2"


    // $ANTLR start "rule__ImportModule__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1341:1: rule__ImportModule__Group__2__Impl : ( ( rule__ImportModule__Group_2__0 )? ) ;
    public final void rule__ImportModule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1345:1: ( ( ( rule__ImportModule__Group_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1346:1: ( ( rule__ImportModule__Group_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1346:1: ( ( rule__ImportModule__Group_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1347:1: ( rule__ImportModule__Group_2__0 )?
            {
             before(grammarAccess.getImportModuleAccess().getGroup_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1348:1: ( rule__ImportModule__Group_2__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==15) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1348:2: rule__ImportModule__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__ImportModule__Group_2__0_in_rule__ImportModule__Group__2__Impl2785);
                    rule__ImportModule__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getImportModuleAccess().getGroup_2()); 

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
    // $ANTLR end "rule__ImportModule__Group__2__Impl"


    // $ANTLR start "rule__ImportModule__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1358:1: rule__ImportModule__Group__3 : rule__ImportModule__Group__3__Impl ;
    public final void rule__ImportModule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1362:1: ( rule__ImportModule__Group__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1363:2: rule__ImportModule__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__ImportModule__Group__3__Impl_in_rule__ImportModule__Group__32816);
            rule__ImportModule__Group__3__Impl();

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
    // $ANTLR end "rule__ImportModule__Group__3"


    // $ANTLR start "rule__ImportModule__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1369:1: rule__ImportModule__Group__3__Impl : ( ';' ) ;
    public final void rule__ImportModule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1373:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1374:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1374:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1375:1: ';'
            {
             before(grammarAccess.getImportModuleAccess().getSemicolonKeyword_3()); 
            match(input,12,FOLLOW_12_in_rule__ImportModule__Group__3__Impl2844); 
             after(grammarAccess.getImportModuleAccess().getSemicolonKeyword_3()); 

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
    // $ANTLR end "rule__ImportModule__Group__3__Impl"


    // $ANTLR start "rule__ImportModule__Group_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1396:1: rule__ImportModule__Group_2__0 : rule__ImportModule__Group_2__0__Impl rule__ImportModule__Group_2__1 ;
    public final void rule__ImportModule__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1400:1: ( rule__ImportModule__Group_2__0__Impl rule__ImportModule__Group_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1401:2: rule__ImportModule__Group_2__0__Impl rule__ImportModule__Group_2__1
            {
            pushFollow(FOLLOW_rule__ImportModule__Group_2__0__Impl_in_rule__ImportModule__Group_2__02883);
            rule__ImportModule__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportModule__Group_2__1_in_rule__ImportModule__Group_2__02886);
            rule__ImportModule__Group_2__1();

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
    // $ANTLR end "rule__ImportModule__Group_2__0"


    // $ANTLR start "rule__ImportModule__Group_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1408:1: rule__ImportModule__Group_2__0__Impl : ( 'as' ) ;
    public final void rule__ImportModule__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1412:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1413:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1413:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1414:1: 'as'
            {
             before(grammarAccess.getImportModuleAccess().getAsKeyword_2_0()); 
            match(input,15,FOLLOW_15_in_rule__ImportModule__Group_2__0__Impl2914); 
             after(grammarAccess.getImportModuleAccess().getAsKeyword_2_0()); 

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
    // $ANTLR end "rule__ImportModule__Group_2__0__Impl"


    // $ANTLR start "rule__ImportModule__Group_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1427:1: rule__ImportModule__Group_2__1 : rule__ImportModule__Group_2__1__Impl ;
    public final void rule__ImportModule__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1431:1: ( rule__ImportModule__Group_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1432:2: rule__ImportModule__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ImportModule__Group_2__1__Impl_in_rule__ImportModule__Group_2__12945);
            rule__ImportModule__Group_2__1__Impl();

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
    // $ANTLR end "rule__ImportModule__Group_2__1"


    // $ANTLR start "rule__ImportModule__Group_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1438:1: rule__ImportModule__Group_2__1__Impl : ( ( rule__ImportModule__AliasAssignment_2_1 ) ) ;
    public final void rule__ImportModule__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1442:1: ( ( ( rule__ImportModule__AliasAssignment_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1443:1: ( ( rule__ImportModule__AliasAssignment_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1443:1: ( ( rule__ImportModule__AliasAssignment_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1444:1: ( rule__ImportModule__AliasAssignment_2_1 )
            {
             before(grammarAccess.getImportModuleAccess().getAliasAssignment_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1445:1: ( rule__ImportModule__AliasAssignment_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1445:2: rule__ImportModule__AliasAssignment_2_1
            {
            pushFollow(FOLLOW_rule__ImportModule__AliasAssignment_2_1_in_rule__ImportModule__Group_2__1__Impl2972);
            rule__ImportModule__AliasAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getImportModuleAccess().getAliasAssignment_2_1()); 

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
    // $ANTLR end "rule__ImportModule__Group_2__1__Impl"


    // $ANTLR start "rule__ImportMember__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1459:1: rule__ImportMember__Group__0 : rule__ImportMember__Group__0__Impl rule__ImportMember__Group__1 ;
    public final void rule__ImportMember__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1463:1: ( rule__ImportMember__Group__0__Impl rule__ImportMember__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1464:2: rule__ImportMember__Group__0__Impl rule__ImportMember__Group__1
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__0__Impl_in_rule__ImportMember__Group__03006);
            rule__ImportMember__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group__1_in_rule__ImportMember__Group__03009);
            rule__ImportMember__Group__1();

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
    // $ANTLR end "rule__ImportMember__Group__0"


    // $ANTLR start "rule__ImportMember__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1471:1: rule__ImportMember__Group__0__Impl : ( 'from' ) ;
    public final void rule__ImportMember__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1475:1: ( ( 'from' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1476:1: ( 'from' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1476:1: ( 'from' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1477:1: 'from'
            {
             before(grammarAccess.getImportMemberAccess().getFromKeyword_0()); 
            match(input,16,FOLLOW_16_in_rule__ImportMember__Group__0__Impl3037); 
             after(grammarAccess.getImportMemberAccess().getFromKeyword_0()); 

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
    // $ANTLR end "rule__ImportMember__Group__0__Impl"


    // $ANTLR start "rule__ImportMember__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1490:1: rule__ImportMember__Group__1 : rule__ImportMember__Group__1__Impl rule__ImportMember__Group__2 ;
    public final void rule__ImportMember__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1494:1: ( rule__ImportMember__Group__1__Impl rule__ImportMember__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1495:2: rule__ImportMember__Group__1__Impl rule__ImportMember__Group__2
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__1__Impl_in_rule__ImportMember__Group__13068);
            rule__ImportMember__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group__2_in_rule__ImportMember__Group__13071);
            rule__ImportMember__Group__2();

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
    // $ANTLR end "rule__ImportMember__Group__1"


    // $ANTLR start "rule__ImportMember__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1502:1: rule__ImportMember__Group__1__Impl : ( ( rule__ImportMember__NameAssignment_1 ) ) ;
    public final void rule__ImportMember__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1506:1: ( ( ( rule__ImportMember__NameAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1507:1: ( ( rule__ImportMember__NameAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1507:1: ( ( rule__ImportMember__NameAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1508:1: ( rule__ImportMember__NameAssignment_1 )
            {
             before(grammarAccess.getImportMemberAccess().getNameAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1509:1: ( rule__ImportMember__NameAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1509:2: rule__ImportMember__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__ImportMember__NameAssignment_1_in_rule__ImportMember__Group__1__Impl3098);
            rule__ImportMember__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportMemberAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__ImportMember__Group__1__Impl"


    // $ANTLR start "rule__ImportMember__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1519:1: rule__ImportMember__Group__2 : rule__ImportMember__Group__2__Impl rule__ImportMember__Group__3 ;
    public final void rule__ImportMember__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1523:1: ( rule__ImportMember__Group__2__Impl rule__ImportMember__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1524:2: rule__ImportMember__Group__2__Impl rule__ImportMember__Group__3
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__2__Impl_in_rule__ImportMember__Group__23128);
            rule__ImportMember__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group__3_in_rule__ImportMember__Group__23131);
            rule__ImportMember__Group__3();

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
    // $ANTLR end "rule__ImportMember__Group__2"


    // $ANTLR start "rule__ImportMember__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1531:1: rule__ImportMember__Group__2__Impl : ( 'import' ) ;
    public final void rule__ImportMember__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1535:1: ( ( 'import' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1536:1: ( 'import' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1536:1: ( 'import' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1537:1: 'import'
            {
             before(grammarAccess.getImportMemberAccess().getImportKeyword_2()); 
            match(input,14,FOLLOW_14_in_rule__ImportMember__Group__2__Impl3159); 
             after(grammarAccess.getImportMemberAccess().getImportKeyword_2()); 

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
    // $ANTLR end "rule__ImportMember__Group__2__Impl"


    // $ANTLR start "rule__ImportMember__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1550:1: rule__ImportMember__Group__3 : rule__ImportMember__Group__3__Impl rule__ImportMember__Group__4 ;
    public final void rule__ImportMember__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1554:1: ( rule__ImportMember__Group__3__Impl rule__ImportMember__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1555:2: rule__ImportMember__Group__3__Impl rule__ImportMember__Group__4
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__3__Impl_in_rule__ImportMember__Group__33190);
            rule__ImportMember__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group__4_in_rule__ImportMember__Group__33193);
            rule__ImportMember__Group__4();

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
    // $ANTLR end "rule__ImportMember__Group__3"


    // $ANTLR start "rule__ImportMember__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1562:1: rule__ImportMember__Group__3__Impl : ( ( rule__ImportMember__MemberAssignment_3 ) ) ;
    public final void rule__ImportMember__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1566:1: ( ( ( rule__ImportMember__MemberAssignment_3 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1567:1: ( ( rule__ImportMember__MemberAssignment_3 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1567:1: ( ( rule__ImportMember__MemberAssignment_3 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1568:1: ( rule__ImportMember__MemberAssignment_3 )
            {
             before(grammarAccess.getImportMemberAccess().getMemberAssignment_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1569:1: ( rule__ImportMember__MemberAssignment_3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1569:2: rule__ImportMember__MemberAssignment_3
            {
            pushFollow(FOLLOW_rule__ImportMember__MemberAssignment_3_in_rule__ImportMember__Group__3__Impl3220);
            rule__ImportMember__MemberAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getImportMemberAccess().getMemberAssignment_3()); 

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
    // $ANTLR end "rule__ImportMember__Group__3__Impl"


    // $ANTLR start "rule__ImportMember__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1579:1: rule__ImportMember__Group__4 : rule__ImportMember__Group__4__Impl rule__ImportMember__Group__5 ;
    public final void rule__ImportMember__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1583:1: ( rule__ImportMember__Group__4__Impl rule__ImportMember__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1584:2: rule__ImportMember__Group__4__Impl rule__ImportMember__Group__5
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__4__Impl_in_rule__ImportMember__Group__43250);
            rule__ImportMember__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group__5_in_rule__ImportMember__Group__43253);
            rule__ImportMember__Group__5();

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
    // $ANTLR end "rule__ImportMember__Group__4"


    // $ANTLR start "rule__ImportMember__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1591:1: rule__ImportMember__Group__4__Impl : ( ( rule__ImportMember__Group_4__0 )? ) ;
    public final void rule__ImportMember__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1595:1: ( ( ( rule__ImportMember__Group_4__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1596:1: ( ( rule__ImportMember__Group_4__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1596:1: ( ( rule__ImportMember__Group_4__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1597:1: ( rule__ImportMember__Group_4__0 )?
            {
             before(grammarAccess.getImportMemberAccess().getGroup_4()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1598:1: ( rule__ImportMember__Group_4__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==15) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1598:2: rule__ImportMember__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__ImportMember__Group_4__0_in_rule__ImportMember__Group__4__Impl3280);
                    rule__ImportMember__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getImportMemberAccess().getGroup_4()); 

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
    // $ANTLR end "rule__ImportMember__Group__4__Impl"


    // $ANTLR start "rule__ImportMember__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1608:1: rule__ImportMember__Group__5 : rule__ImportMember__Group__5__Impl ;
    public final void rule__ImportMember__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1612:1: ( rule__ImportMember__Group__5__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1613:2: rule__ImportMember__Group__5__Impl
            {
            pushFollow(FOLLOW_rule__ImportMember__Group__5__Impl_in_rule__ImportMember__Group__53311);
            rule__ImportMember__Group__5__Impl();

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
    // $ANTLR end "rule__ImportMember__Group__5"


    // $ANTLR start "rule__ImportMember__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1619:1: rule__ImportMember__Group__5__Impl : ( ';' ) ;
    public final void rule__ImportMember__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1623:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1624:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1624:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1625:1: ';'
            {
             before(grammarAccess.getImportMemberAccess().getSemicolonKeyword_5()); 
            match(input,12,FOLLOW_12_in_rule__ImportMember__Group__5__Impl3339); 
             after(grammarAccess.getImportMemberAccess().getSemicolonKeyword_5()); 

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
    // $ANTLR end "rule__ImportMember__Group__5__Impl"


    // $ANTLR start "rule__ImportMember__Group_4__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1650:1: rule__ImportMember__Group_4__0 : rule__ImportMember__Group_4__0__Impl rule__ImportMember__Group_4__1 ;
    public final void rule__ImportMember__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1654:1: ( rule__ImportMember__Group_4__0__Impl rule__ImportMember__Group_4__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1655:2: rule__ImportMember__Group_4__0__Impl rule__ImportMember__Group_4__1
            {
            pushFollow(FOLLOW_rule__ImportMember__Group_4__0__Impl_in_rule__ImportMember__Group_4__03382);
            rule__ImportMember__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ImportMember__Group_4__1_in_rule__ImportMember__Group_4__03385);
            rule__ImportMember__Group_4__1();

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
    // $ANTLR end "rule__ImportMember__Group_4__0"


    // $ANTLR start "rule__ImportMember__Group_4__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1662:1: rule__ImportMember__Group_4__0__Impl : ( 'as' ) ;
    public final void rule__ImportMember__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1666:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1667:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1667:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1668:1: 'as'
            {
             before(grammarAccess.getImportMemberAccess().getAsKeyword_4_0()); 
            match(input,15,FOLLOW_15_in_rule__ImportMember__Group_4__0__Impl3413); 
             after(grammarAccess.getImportMemberAccess().getAsKeyword_4_0()); 

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
    // $ANTLR end "rule__ImportMember__Group_4__0__Impl"


    // $ANTLR start "rule__ImportMember__Group_4__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1681:1: rule__ImportMember__Group_4__1 : rule__ImportMember__Group_4__1__Impl ;
    public final void rule__ImportMember__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1685:1: ( rule__ImportMember__Group_4__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1686:2: rule__ImportMember__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__ImportMember__Group_4__1__Impl_in_rule__ImportMember__Group_4__13444);
            rule__ImportMember__Group_4__1__Impl();

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
    // $ANTLR end "rule__ImportMember__Group_4__1"


    // $ANTLR start "rule__ImportMember__Group_4__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1692:1: rule__ImportMember__Group_4__1__Impl : ( ( rule__ImportMember__AliasAssignment_4_1 ) ) ;
    public final void rule__ImportMember__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1696:1: ( ( ( rule__ImportMember__AliasAssignment_4_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1697:1: ( ( rule__ImportMember__AliasAssignment_4_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1697:1: ( ( rule__ImportMember__AliasAssignment_4_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1698:1: ( rule__ImportMember__AliasAssignment_4_1 )
            {
             before(grammarAccess.getImportMemberAccess().getAliasAssignment_4_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1699:1: ( rule__ImportMember__AliasAssignment_4_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1699:2: rule__ImportMember__AliasAssignment_4_1
            {
            pushFollow(FOLLOW_rule__ImportMember__AliasAssignment_4_1_in_rule__ImportMember__Group_4__1__Impl3471);
            rule__ImportMember__AliasAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getImportMemberAccess().getAliasAssignment_4_1()); 

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
    // $ANTLR end "rule__ImportMember__Group_4__1__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1713:1: rule__PayloadTypeDecl__Group__0 : rule__PayloadTypeDecl__Group__0__Impl rule__PayloadTypeDecl__Group__1 ;
    public final void rule__PayloadTypeDecl__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1717:1: ( rule__PayloadTypeDecl__Group__0__Impl rule__PayloadTypeDecl__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1718:2: rule__PayloadTypeDecl__Group__0__Impl rule__PayloadTypeDecl__Group__1
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__0__Impl_in_rule__PayloadTypeDecl__Group__03505);
            rule__PayloadTypeDecl__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__1_in_rule__PayloadTypeDecl__Group__03508);
            rule__PayloadTypeDecl__Group__1();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__0"


    // $ANTLR start "rule__PayloadTypeDecl__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1725:1: rule__PayloadTypeDecl__Group__0__Impl : ( 'type' ) ;
    public final void rule__PayloadTypeDecl__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1729:1: ( ( 'type' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1730:1: ( 'type' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1730:1: ( 'type' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1731:1: 'type'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getTypeKeyword_0()); 
            match(input,17,FOLLOW_17_in_rule__PayloadTypeDecl__Group__0__Impl3536); 
             after(grammarAccess.getPayloadTypeDeclAccess().getTypeKeyword_0()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__0__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1744:1: rule__PayloadTypeDecl__Group__1 : rule__PayloadTypeDecl__Group__1__Impl rule__PayloadTypeDecl__Group__2 ;
    public final void rule__PayloadTypeDecl__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1748:1: ( rule__PayloadTypeDecl__Group__1__Impl rule__PayloadTypeDecl__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1749:2: rule__PayloadTypeDecl__Group__1__Impl rule__PayloadTypeDecl__Group__2
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__1__Impl_in_rule__PayloadTypeDecl__Group__13567);
            rule__PayloadTypeDecl__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__2_in_rule__PayloadTypeDecl__Group__13570);
            rule__PayloadTypeDecl__Group__2();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__1"


    // $ANTLR start "rule__PayloadTypeDecl__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1756:1: rule__PayloadTypeDecl__Group__1__Impl : ( '<' ) ;
    public final void rule__PayloadTypeDecl__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1760:1: ( ( '<' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1761:1: ( '<' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1761:1: ( '<' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1762:1: '<'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getLessThanSignKeyword_1()); 
            match(input,18,FOLLOW_18_in_rule__PayloadTypeDecl__Group__1__Impl3598); 
             after(grammarAccess.getPayloadTypeDeclAccess().getLessThanSignKeyword_1()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__1__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1775:1: rule__PayloadTypeDecl__Group__2 : rule__PayloadTypeDecl__Group__2__Impl rule__PayloadTypeDecl__Group__3 ;
    public final void rule__PayloadTypeDecl__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1779:1: ( rule__PayloadTypeDecl__Group__2__Impl rule__PayloadTypeDecl__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1780:2: rule__PayloadTypeDecl__Group__2__Impl rule__PayloadTypeDecl__Group__3
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__2__Impl_in_rule__PayloadTypeDecl__Group__23629);
            rule__PayloadTypeDecl__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__3_in_rule__PayloadTypeDecl__Group__23632);
            rule__PayloadTypeDecl__Group__3();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__2"


    // $ANTLR start "rule__PayloadTypeDecl__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1787:1: rule__PayloadTypeDecl__Group__2__Impl : ( ( rule__PayloadTypeDecl__SchemaAssignment_2 ) ) ;
    public final void rule__PayloadTypeDecl__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1791:1: ( ( ( rule__PayloadTypeDecl__SchemaAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1792:1: ( ( rule__PayloadTypeDecl__SchemaAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1792:1: ( ( rule__PayloadTypeDecl__SchemaAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1793:1: ( rule__PayloadTypeDecl__SchemaAssignment_2 )
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getSchemaAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1794:1: ( rule__PayloadTypeDecl__SchemaAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1794:2: rule__PayloadTypeDecl__SchemaAssignment_2
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__SchemaAssignment_2_in_rule__PayloadTypeDecl__Group__2__Impl3659);
            rule__PayloadTypeDecl__SchemaAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getPayloadTypeDeclAccess().getSchemaAssignment_2()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__2__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1804:1: rule__PayloadTypeDecl__Group__3 : rule__PayloadTypeDecl__Group__3__Impl rule__PayloadTypeDecl__Group__4 ;
    public final void rule__PayloadTypeDecl__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1808:1: ( rule__PayloadTypeDecl__Group__3__Impl rule__PayloadTypeDecl__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1809:2: rule__PayloadTypeDecl__Group__3__Impl rule__PayloadTypeDecl__Group__4
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__3__Impl_in_rule__PayloadTypeDecl__Group__33689);
            rule__PayloadTypeDecl__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__4_in_rule__PayloadTypeDecl__Group__33692);
            rule__PayloadTypeDecl__Group__4();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__3"


    // $ANTLR start "rule__PayloadTypeDecl__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1816:1: rule__PayloadTypeDecl__Group__3__Impl : ( '>' ) ;
    public final void rule__PayloadTypeDecl__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1820:1: ( ( '>' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1821:1: ( '>' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1821:1: ( '>' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1822:1: '>'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getGreaterThanSignKeyword_3()); 
            match(input,19,FOLLOW_19_in_rule__PayloadTypeDecl__Group__3__Impl3720); 
             after(grammarAccess.getPayloadTypeDeclAccess().getGreaterThanSignKeyword_3()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__3__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1835:1: rule__PayloadTypeDecl__Group__4 : rule__PayloadTypeDecl__Group__4__Impl rule__PayloadTypeDecl__Group__5 ;
    public final void rule__PayloadTypeDecl__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1839:1: ( rule__PayloadTypeDecl__Group__4__Impl rule__PayloadTypeDecl__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1840:2: rule__PayloadTypeDecl__Group__4__Impl rule__PayloadTypeDecl__Group__5
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__4__Impl_in_rule__PayloadTypeDecl__Group__43751);
            rule__PayloadTypeDecl__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__5_in_rule__PayloadTypeDecl__Group__43754);
            rule__PayloadTypeDecl__Group__5();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__4"


    // $ANTLR start "rule__PayloadTypeDecl__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1847:1: rule__PayloadTypeDecl__Group__4__Impl : ( ( rule__PayloadTypeDecl__TypeAssignment_4 ) ) ;
    public final void rule__PayloadTypeDecl__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1851:1: ( ( ( rule__PayloadTypeDecl__TypeAssignment_4 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1852:1: ( ( rule__PayloadTypeDecl__TypeAssignment_4 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1852:1: ( ( rule__PayloadTypeDecl__TypeAssignment_4 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1853:1: ( rule__PayloadTypeDecl__TypeAssignment_4 )
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getTypeAssignment_4()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1854:1: ( rule__PayloadTypeDecl__TypeAssignment_4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1854:2: rule__PayloadTypeDecl__TypeAssignment_4
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__TypeAssignment_4_in_rule__PayloadTypeDecl__Group__4__Impl3781);
            rule__PayloadTypeDecl__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPayloadTypeDeclAccess().getTypeAssignment_4()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__4__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1864:1: rule__PayloadTypeDecl__Group__5 : rule__PayloadTypeDecl__Group__5__Impl rule__PayloadTypeDecl__Group__6 ;
    public final void rule__PayloadTypeDecl__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1868:1: ( rule__PayloadTypeDecl__Group__5__Impl rule__PayloadTypeDecl__Group__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1869:2: rule__PayloadTypeDecl__Group__5__Impl rule__PayloadTypeDecl__Group__6
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__5__Impl_in_rule__PayloadTypeDecl__Group__53811);
            rule__PayloadTypeDecl__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__6_in_rule__PayloadTypeDecl__Group__53814);
            rule__PayloadTypeDecl__Group__6();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__5"


    // $ANTLR start "rule__PayloadTypeDecl__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1876:1: rule__PayloadTypeDecl__Group__5__Impl : ( 'from' ) ;
    public final void rule__PayloadTypeDecl__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1880:1: ( ( 'from' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1881:1: ( 'from' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1881:1: ( 'from' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1882:1: 'from'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getFromKeyword_5()); 
            match(input,16,FOLLOW_16_in_rule__PayloadTypeDecl__Group__5__Impl3842); 
             after(grammarAccess.getPayloadTypeDeclAccess().getFromKeyword_5()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__5__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1895:1: rule__PayloadTypeDecl__Group__6 : rule__PayloadTypeDecl__Group__6__Impl rule__PayloadTypeDecl__Group__7 ;
    public final void rule__PayloadTypeDecl__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1899:1: ( rule__PayloadTypeDecl__Group__6__Impl rule__PayloadTypeDecl__Group__7 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1900:2: rule__PayloadTypeDecl__Group__6__Impl rule__PayloadTypeDecl__Group__7
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__6__Impl_in_rule__PayloadTypeDecl__Group__63873);
            rule__PayloadTypeDecl__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__7_in_rule__PayloadTypeDecl__Group__63876);
            rule__PayloadTypeDecl__Group__7();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__6"


    // $ANTLR start "rule__PayloadTypeDecl__Group__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1907:1: rule__PayloadTypeDecl__Group__6__Impl : ( ( rule__PayloadTypeDecl__LocationAssignment_6 ) ) ;
    public final void rule__PayloadTypeDecl__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1911:1: ( ( ( rule__PayloadTypeDecl__LocationAssignment_6 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1912:1: ( ( rule__PayloadTypeDecl__LocationAssignment_6 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1912:1: ( ( rule__PayloadTypeDecl__LocationAssignment_6 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1913:1: ( rule__PayloadTypeDecl__LocationAssignment_6 )
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getLocationAssignment_6()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1914:1: ( rule__PayloadTypeDecl__LocationAssignment_6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1914:2: rule__PayloadTypeDecl__LocationAssignment_6
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__LocationAssignment_6_in_rule__PayloadTypeDecl__Group__6__Impl3903);
            rule__PayloadTypeDecl__LocationAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getPayloadTypeDeclAccess().getLocationAssignment_6()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__6__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__7"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1924:1: rule__PayloadTypeDecl__Group__7 : rule__PayloadTypeDecl__Group__7__Impl rule__PayloadTypeDecl__Group__8 ;
    public final void rule__PayloadTypeDecl__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1928:1: ( rule__PayloadTypeDecl__Group__7__Impl rule__PayloadTypeDecl__Group__8 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1929:2: rule__PayloadTypeDecl__Group__7__Impl rule__PayloadTypeDecl__Group__8
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__7__Impl_in_rule__PayloadTypeDecl__Group__73933);
            rule__PayloadTypeDecl__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__8_in_rule__PayloadTypeDecl__Group__73936);
            rule__PayloadTypeDecl__Group__8();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__7"


    // $ANTLR start "rule__PayloadTypeDecl__Group__7__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1936:1: rule__PayloadTypeDecl__Group__7__Impl : ( 'as' ) ;
    public final void rule__PayloadTypeDecl__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1940:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1941:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1941:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1942:1: 'as'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getAsKeyword_7()); 
            match(input,15,FOLLOW_15_in_rule__PayloadTypeDecl__Group__7__Impl3964); 
             after(grammarAccess.getPayloadTypeDeclAccess().getAsKeyword_7()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__7__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__8"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1955:1: rule__PayloadTypeDecl__Group__8 : rule__PayloadTypeDecl__Group__8__Impl rule__PayloadTypeDecl__Group__9 ;
    public final void rule__PayloadTypeDecl__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1959:1: ( rule__PayloadTypeDecl__Group__8__Impl rule__PayloadTypeDecl__Group__9 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1960:2: rule__PayloadTypeDecl__Group__8__Impl rule__PayloadTypeDecl__Group__9
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__8__Impl_in_rule__PayloadTypeDecl__Group__83995);
            rule__PayloadTypeDecl__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__9_in_rule__PayloadTypeDecl__Group__83998);
            rule__PayloadTypeDecl__Group__9();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__8"


    // $ANTLR start "rule__PayloadTypeDecl__Group__8__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1967:1: rule__PayloadTypeDecl__Group__8__Impl : ( ( rule__PayloadTypeDecl__AliasAssignment_8 ) ) ;
    public final void rule__PayloadTypeDecl__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1971:1: ( ( ( rule__PayloadTypeDecl__AliasAssignment_8 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1972:1: ( ( rule__PayloadTypeDecl__AliasAssignment_8 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1972:1: ( ( rule__PayloadTypeDecl__AliasAssignment_8 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1973:1: ( rule__PayloadTypeDecl__AliasAssignment_8 )
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getAliasAssignment_8()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1974:1: ( rule__PayloadTypeDecl__AliasAssignment_8 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1974:2: rule__PayloadTypeDecl__AliasAssignment_8
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__AliasAssignment_8_in_rule__PayloadTypeDecl__Group__8__Impl4025);
            rule__PayloadTypeDecl__AliasAssignment_8();

            state._fsp--;


            }

             after(grammarAccess.getPayloadTypeDeclAccess().getAliasAssignment_8()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__8__Impl"


    // $ANTLR start "rule__PayloadTypeDecl__Group__9"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1984:1: rule__PayloadTypeDecl__Group__9 : rule__PayloadTypeDecl__Group__9__Impl ;
    public final void rule__PayloadTypeDecl__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1988:1: ( rule__PayloadTypeDecl__Group__9__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1989:2: rule__PayloadTypeDecl__Group__9__Impl
            {
            pushFollow(FOLLOW_rule__PayloadTypeDecl__Group__9__Impl_in_rule__PayloadTypeDecl__Group__94055);
            rule__PayloadTypeDecl__Group__9__Impl();

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__9"


    // $ANTLR start "rule__PayloadTypeDecl__Group__9__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1995:1: rule__PayloadTypeDecl__Group__9__Impl : ( ';' ) ;
    public final void rule__PayloadTypeDecl__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:1999:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2000:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2000:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2001:1: ';'
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getSemicolonKeyword_9()); 
            match(input,12,FOLLOW_12_in_rule__PayloadTypeDecl__Group__9__Impl4083); 
             after(grammarAccess.getPayloadTypeDeclAccess().getSemicolonKeyword_9()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__Group__9__Impl"


    // $ANTLR start "rule__MessageSignature__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2034:1: rule__MessageSignature__Group__0 : rule__MessageSignature__Group__0__Impl rule__MessageSignature__Group__1 ;
    public final void rule__MessageSignature__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2038:1: ( rule__MessageSignature__Group__0__Impl rule__MessageSignature__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2039:2: rule__MessageSignature__Group__0__Impl rule__MessageSignature__Group__1
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group__0__Impl_in_rule__MessageSignature__Group__04134);
            rule__MessageSignature__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MessageSignature__Group__1_in_rule__MessageSignature__Group__04137);
            rule__MessageSignature__Group__1();

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
    // $ANTLR end "rule__MessageSignature__Group__0"


    // $ANTLR start "rule__MessageSignature__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2046:1: rule__MessageSignature__Group__0__Impl : ( ( rule__MessageSignature__OperatorAssignment_0 ) ) ;
    public final void rule__MessageSignature__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2050:1: ( ( ( rule__MessageSignature__OperatorAssignment_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2051:1: ( ( rule__MessageSignature__OperatorAssignment_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2051:1: ( ( rule__MessageSignature__OperatorAssignment_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2052:1: ( rule__MessageSignature__OperatorAssignment_0 )
            {
             before(grammarAccess.getMessageSignatureAccess().getOperatorAssignment_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2053:1: ( rule__MessageSignature__OperatorAssignment_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2053:2: rule__MessageSignature__OperatorAssignment_0
            {
            pushFollow(FOLLOW_rule__MessageSignature__OperatorAssignment_0_in_rule__MessageSignature__Group__0__Impl4164);
            rule__MessageSignature__OperatorAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getMessageSignatureAccess().getOperatorAssignment_0()); 

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
    // $ANTLR end "rule__MessageSignature__Group__0__Impl"


    // $ANTLR start "rule__MessageSignature__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2063:1: rule__MessageSignature__Group__1 : rule__MessageSignature__Group__1__Impl rule__MessageSignature__Group__2 ;
    public final void rule__MessageSignature__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2067:1: ( rule__MessageSignature__Group__1__Impl rule__MessageSignature__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2068:2: rule__MessageSignature__Group__1__Impl rule__MessageSignature__Group__2
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group__1__Impl_in_rule__MessageSignature__Group__14194);
            rule__MessageSignature__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MessageSignature__Group__2_in_rule__MessageSignature__Group__14197);
            rule__MessageSignature__Group__2();

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
    // $ANTLR end "rule__MessageSignature__Group__1"


    // $ANTLR start "rule__MessageSignature__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2075:1: rule__MessageSignature__Group__1__Impl : ( '(' ) ;
    public final void rule__MessageSignature__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2079:1: ( ( '(' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2080:1: ( '(' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2080:1: ( '(' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2081:1: '('
            {
             before(grammarAccess.getMessageSignatureAccess().getLeftParenthesisKeyword_1()); 
            match(input,20,FOLLOW_20_in_rule__MessageSignature__Group__1__Impl4225); 
             after(grammarAccess.getMessageSignatureAccess().getLeftParenthesisKeyword_1()); 

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
    // $ANTLR end "rule__MessageSignature__Group__1__Impl"


    // $ANTLR start "rule__MessageSignature__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2094:1: rule__MessageSignature__Group__2 : rule__MessageSignature__Group__2__Impl rule__MessageSignature__Group__3 ;
    public final void rule__MessageSignature__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2098:1: ( rule__MessageSignature__Group__2__Impl rule__MessageSignature__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2099:2: rule__MessageSignature__Group__2__Impl rule__MessageSignature__Group__3
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group__2__Impl_in_rule__MessageSignature__Group__24256);
            rule__MessageSignature__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MessageSignature__Group__3_in_rule__MessageSignature__Group__24259);
            rule__MessageSignature__Group__3();

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
    // $ANTLR end "rule__MessageSignature__Group__2"


    // $ANTLR start "rule__MessageSignature__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2106:1: rule__MessageSignature__Group__2__Impl : ( ( rule__MessageSignature__Group_2__0 )? ) ;
    public final void rule__MessageSignature__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2110:1: ( ( ( rule__MessageSignature__Group_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2111:1: ( ( rule__MessageSignature__Group_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2111:1: ( ( rule__MessageSignature__Group_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2112:1: ( rule__MessageSignature__Group_2__0 )?
            {
             before(grammarAccess.getMessageSignatureAccess().getGroup_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2113:1: ( rule__MessageSignature__Group_2__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RULE_ID) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2113:2: rule__MessageSignature__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__MessageSignature__Group_2__0_in_rule__MessageSignature__Group__2__Impl4286);
                    rule__MessageSignature__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMessageSignatureAccess().getGroup_2()); 

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
    // $ANTLR end "rule__MessageSignature__Group__2__Impl"


    // $ANTLR start "rule__MessageSignature__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2123:1: rule__MessageSignature__Group__3 : rule__MessageSignature__Group__3__Impl ;
    public final void rule__MessageSignature__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2127:1: ( rule__MessageSignature__Group__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2128:2: rule__MessageSignature__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group__3__Impl_in_rule__MessageSignature__Group__34317);
            rule__MessageSignature__Group__3__Impl();

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
    // $ANTLR end "rule__MessageSignature__Group__3"


    // $ANTLR start "rule__MessageSignature__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2134:1: rule__MessageSignature__Group__3__Impl : ( ')' ) ;
    public final void rule__MessageSignature__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2138:1: ( ( ')' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2139:1: ( ')' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2139:1: ( ')' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2140:1: ')'
            {
             before(grammarAccess.getMessageSignatureAccess().getRightParenthesisKeyword_3()); 
            match(input,21,FOLLOW_21_in_rule__MessageSignature__Group__3__Impl4345); 
             after(grammarAccess.getMessageSignatureAccess().getRightParenthesisKeyword_3()); 

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
    // $ANTLR end "rule__MessageSignature__Group__3__Impl"


    // $ANTLR start "rule__MessageSignature__Group_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2161:1: rule__MessageSignature__Group_2__0 : rule__MessageSignature__Group_2__0__Impl rule__MessageSignature__Group_2__1 ;
    public final void rule__MessageSignature__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2165:1: ( rule__MessageSignature__Group_2__0__Impl rule__MessageSignature__Group_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2166:2: rule__MessageSignature__Group_2__0__Impl rule__MessageSignature__Group_2__1
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group_2__0__Impl_in_rule__MessageSignature__Group_2__04384);
            rule__MessageSignature__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MessageSignature__Group_2__1_in_rule__MessageSignature__Group_2__04387);
            rule__MessageSignature__Group_2__1();

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
    // $ANTLR end "rule__MessageSignature__Group_2__0"


    // $ANTLR start "rule__MessageSignature__Group_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2173:1: rule__MessageSignature__Group_2__0__Impl : ( ( rule__MessageSignature__TypesAssignment_2_0 ) ) ;
    public final void rule__MessageSignature__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2177:1: ( ( ( rule__MessageSignature__TypesAssignment_2_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2178:1: ( ( rule__MessageSignature__TypesAssignment_2_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2178:1: ( ( rule__MessageSignature__TypesAssignment_2_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2179:1: ( rule__MessageSignature__TypesAssignment_2_0 )
            {
             before(grammarAccess.getMessageSignatureAccess().getTypesAssignment_2_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2180:1: ( rule__MessageSignature__TypesAssignment_2_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2180:2: rule__MessageSignature__TypesAssignment_2_0
            {
            pushFollow(FOLLOW_rule__MessageSignature__TypesAssignment_2_0_in_rule__MessageSignature__Group_2__0__Impl4414);
            rule__MessageSignature__TypesAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getMessageSignatureAccess().getTypesAssignment_2_0()); 

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
    // $ANTLR end "rule__MessageSignature__Group_2__0__Impl"


    // $ANTLR start "rule__MessageSignature__Group_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2190:1: rule__MessageSignature__Group_2__1 : rule__MessageSignature__Group_2__1__Impl ;
    public final void rule__MessageSignature__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2194:1: ( rule__MessageSignature__Group_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2195:2: rule__MessageSignature__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group_2__1__Impl_in_rule__MessageSignature__Group_2__14444);
            rule__MessageSignature__Group_2__1__Impl();

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
    // $ANTLR end "rule__MessageSignature__Group_2__1"


    // $ANTLR start "rule__MessageSignature__Group_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2201:1: rule__MessageSignature__Group_2__1__Impl : ( ( rule__MessageSignature__Group_2_1__0 )* ) ;
    public final void rule__MessageSignature__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2205:1: ( ( ( rule__MessageSignature__Group_2_1__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2206:1: ( ( rule__MessageSignature__Group_2_1__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2206:1: ( ( rule__MessageSignature__Group_2_1__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2207:1: ( rule__MessageSignature__Group_2_1__0 )*
            {
             before(grammarAccess.getMessageSignatureAccess().getGroup_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2208:1: ( rule__MessageSignature__Group_2_1__0 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==22) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2208:2: rule__MessageSignature__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_rule__MessageSignature__Group_2_1__0_in_rule__MessageSignature__Group_2__1__Impl4471);
            	    rule__MessageSignature__Group_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getMessageSignatureAccess().getGroup_2_1()); 

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
    // $ANTLR end "rule__MessageSignature__Group_2__1__Impl"


    // $ANTLR start "rule__MessageSignature__Group_2_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2222:1: rule__MessageSignature__Group_2_1__0 : rule__MessageSignature__Group_2_1__0__Impl rule__MessageSignature__Group_2_1__1 ;
    public final void rule__MessageSignature__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2226:1: ( rule__MessageSignature__Group_2_1__0__Impl rule__MessageSignature__Group_2_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2227:2: rule__MessageSignature__Group_2_1__0__Impl rule__MessageSignature__Group_2_1__1
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group_2_1__0__Impl_in_rule__MessageSignature__Group_2_1__04506);
            rule__MessageSignature__Group_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MessageSignature__Group_2_1__1_in_rule__MessageSignature__Group_2_1__04509);
            rule__MessageSignature__Group_2_1__1();

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
    // $ANTLR end "rule__MessageSignature__Group_2_1__0"


    // $ANTLR start "rule__MessageSignature__Group_2_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2234:1: rule__MessageSignature__Group_2_1__0__Impl : ( ',' ) ;
    public final void rule__MessageSignature__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2238:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2239:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2239:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2240:1: ','
            {
             before(grammarAccess.getMessageSignatureAccess().getCommaKeyword_2_1_0()); 
            match(input,22,FOLLOW_22_in_rule__MessageSignature__Group_2_1__0__Impl4537); 
             after(grammarAccess.getMessageSignatureAccess().getCommaKeyword_2_1_0()); 

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
    // $ANTLR end "rule__MessageSignature__Group_2_1__0__Impl"


    // $ANTLR start "rule__MessageSignature__Group_2_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2253:1: rule__MessageSignature__Group_2_1__1 : rule__MessageSignature__Group_2_1__1__Impl ;
    public final void rule__MessageSignature__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2257:1: ( rule__MessageSignature__Group_2_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2258:2: rule__MessageSignature__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MessageSignature__Group_2_1__1__Impl_in_rule__MessageSignature__Group_2_1__14568);
            rule__MessageSignature__Group_2_1__1__Impl();

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
    // $ANTLR end "rule__MessageSignature__Group_2_1__1"


    // $ANTLR start "rule__MessageSignature__Group_2_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2264:1: rule__MessageSignature__Group_2_1__1__Impl : ( ( rule__MessageSignature__TypesAssignment_2_1_1 ) ) ;
    public final void rule__MessageSignature__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2268:1: ( ( ( rule__MessageSignature__TypesAssignment_2_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2269:1: ( ( rule__MessageSignature__TypesAssignment_2_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2269:1: ( ( rule__MessageSignature__TypesAssignment_2_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2270:1: ( rule__MessageSignature__TypesAssignment_2_1_1 )
            {
             before(grammarAccess.getMessageSignatureAccess().getTypesAssignment_2_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2271:1: ( rule__MessageSignature__TypesAssignment_2_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2271:2: rule__MessageSignature__TypesAssignment_2_1_1
            {
            pushFollow(FOLLOW_rule__MessageSignature__TypesAssignment_2_1_1_in_rule__MessageSignature__Group_2_1__1__Impl4595);
            rule__MessageSignature__TypesAssignment_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getMessageSignatureAccess().getTypesAssignment_2_1_1()); 

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
    // $ANTLR end "rule__MessageSignature__Group_2_1__1__Impl"


    // $ANTLR start "rule__PayloadElement__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2285:1: rule__PayloadElement__Group__0 : rule__PayloadElement__Group__0__Impl rule__PayloadElement__Group__1 ;
    public final void rule__PayloadElement__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2289:1: ( rule__PayloadElement__Group__0__Impl rule__PayloadElement__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2290:2: rule__PayloadElement__Group__0__Impl rule__PayloadElement__Group__1
            {
            pushFollow(FOLLOW_rule__PayloadElement__Group__0__Impl_in_rule__PayloadElement__Group__04629);
            rule__PayloadElement__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadElement__Group__1_in_rule__PayloadElement__Group__04632);
            rule__PayloadElement__Group__1();

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
    // $ANTLR end "rule__PayloadElement__Group__0"


    // $ANTLR start "rule__PayloadElement__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2297:1: rule__PayloadElement__Group__0__Impl : ( ( rule__PayloadElement__Group_0__0 )? ) ;
    public final void rule__PayloadElement__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2301:1: ( ( ( rule__PayloadElement__Group_0__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2302:1: ( ( rule__PayloadElement__Group_0__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2302:1: ( ( rule__PayloadElement__Group_0__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2303:1: ( rule__PayloadElement__Group_0__0 )?
            {
             before(grammarAccess.getPayloadElementAccess().getGroup_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2304:1: ( rule__PayloadElement__Group_0__0 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_ID) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==23) ) {
                    alt15=1;
                }
            }
            switch (alt15) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2304:2: rule__PayloadElement__Group_0__0
                    {
                    pushFollow(FOLLOW_rule__PayloadElement__Group_0__0_in_rule__PayloadElement__Group__0__Impl4659);
                    rule__PayloadElement__Group_0__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPayloadElementAccess().getGroup_0()); 

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
    // $ANTLR end "rule__PayloadElement__Group__0__Impl"


    // $ANTLR start "rule__PayloadElement__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2314:1: rule__PayloadElement__Group__1 : rule__PayloadElement__Group__1__Impl ;
    public final void rule__PayloadElement__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2318:1: ( rule__PayloadElement__Group__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2319:2: rule__PayloadElement__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__PayloadElement__Group__1__Impl_in_rule__PayloadElement__Group__14690);
            rule__PayloadElement__Group__1__Impl();

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
    // $ANTLR end "rule__PayloadElement__Group__1"


    // $ANTLR start "rule__PayloadElement__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2325:1: rule__PayloadElement__Group__1__Impl : ( ( rule__PayloadElement__TypeAssignment_1 ) ) ;
    public final void rule__PayloadElement__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2329:1: ( ( ( rule__PayloadElement__TypeAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2330:1: ( ( rule__PayloadElement__TypeAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2330:1: ( ( rule__PayloadElement__TypeAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2331:1: ( rule__PayloadElement__TypeAssignment_1 )
            {
             before(grammarAccess.getPayloadElementAccess().getTypeAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2332:1: ( rule__PayloadElement__TypeAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2332:2: rule__PayloadElement__TypeAssignment_1
            {
            pushFollow(FOLLOW_rule__PayloadElement__TypeAssignment_1_in_rule__PayloadElement__Group__1__Impl4717);
            rule__PayloadElement__TypeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getPayloadElementAccess().getTypeAssignment_1()); 

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
    // $ANTLR end "rule__PayloadElement__Group__1__Impl"


    // $ANTLR start "rule__PayloadElement__Group_0__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2346:1: rule__PayloadElement__Group_0__0 : rule__PayloadElement__Group_0__0__Impl rule__PayloadElement__Group_0__1 ;
    public final void rule__PayloadElement__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2350:1: ( rule__PayloadElement__Group_0__0__Impl rule__PayloadElement__Group_0__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2351:2: rule__PayloadElement__Group_0__0__Impl rule__PayloadElement__Group_0__1
            {
            pushFollow(FOLLOW_rule__PayloadElement__Group_0__0__Impl_in_rule__PayloadElement__Group_0__04751);
            rule__PayloadElement__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__PayloadElement__Group_0__1_in_rule__PayloadElement__Group_0__04754);
            rule__PayloadElement__Group_0__1();

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
    // $ANTLR end "rule__PayloadElement__Group_0__0"


    // $ANTLR start "rule__PayloadElement__Group_0__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2358:1: rule__PayloadElement__Group_0__0__Impl : ( ( rule__PayloadElement__NameAssignment_0_0 ) ) ;
    public final void rule__PayloadElement__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2362:1: ( ( ( rule__PayloadElement__NameAssignment_0_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2363:1: ( ( rule__PayloadElement__NameAssignment_0_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2363:1: ( ( rule__PayloadElement__NameAssignment_0_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2364:1: ( rule__PayloadElement__NameAssignment_0_0 )
            {
             before(grammarAccess.getPayloadElementAccess().getNameAssignment_0_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2365:1: ( rule__PayloadElement__NameAssignment_0_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2365:2: rule__PayloadElement__NameAssignment_0_0
            {
            pushFollow(FOLLOW_rule__PayloadElement__NameAssignment_0_0_in_rule__PayloadElement__Group_0__0__Impl4781);
            rule__PayloadElement__NameAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getPayloadElementAccess().getNameAssignment_0_0()); 

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
    // $ANTLR end "rule__PayloadElement__Group_0__0__Impl"


    // $ANTLR start "rule__PayloadElement__Group_0__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2375:1: rule__PayloadElement__Group_0__1 : rule__PayloadElement__Group_0__1__Impl ;
    public final void rule__PayloadElement__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2379:1: ( rule__PayloadElement__Group_0__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2380:2: rule__PayloadElement__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__PayloadElement__Group_0__1__Impl_in_rule__PayloadElement__Group_0__14811);
            rule__PayloadElement__Group_0__1__Impl();

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
    // $ANTLR end "rule__PayloadElement__Group_0__1"


    // $ANTLR start "rule__PayloadElement__Group_0__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2386:1: rule__PayloadElement__Group_0__1__Impl : ( ':' ) ;
    public final void rule__PayloadElement__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2390:1: ( ( ':' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2391:1: ( ':' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2391:1: ( ':' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2392:1: ':'
            {
             before(grammarAccess.getPayloadElementAccess().getColonKeyword_0_1()); 
            match(input,23,FOLLOW_23_in_rule__PayloadElement__Group_0__1__Impl4839); 
             after(grammarAccess.getPayloadElementAccess().getColonKeyword_0_1()); 

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
    // $ANTLR end "rule__PayloadElement__Group_0__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2409:1: rule__GlobalProtocolDecl__Group__0 : rule__GlobalProtocolDecl__Group__0__Impl rule__GlobalProtocolDecl__Group__1 ;
    public final void rule__GlobalProtocolDecl__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2413:1: ( rule__GlobalProtocolDecl__Group__0__Impl rule__GlobalProtocolDecl__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2414:2: rule__GlobalProtocolDecl__Group__0__Impl rule__GlobalProtocolDecl__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__0__Impl_in_rule__GlobalProtocolDecl__Group__04874);
            rule__GlobalProtocolDecl__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__1_in_rule__GlobalProtocolDecl__Group__04877);
            rule__GlobalProtocolDecl__Group__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2421:1: rule__GlobalProtocolDecl__Group__0__Impl : ( 'global' ) ;
    public final void rule__GlobalProtocolDecl__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2425:1: ( ( 'global' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2426:1: ( 'global' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2426:1: ( 'global' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2427:1: 'global'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGlobalKeyword_0()); 
            match(input,24,FOLLOW_24_in_rule__GlobalProtocolDecl__Group__0__Impl4905); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getGlobalKeyword_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2440:1: rule__GlobalProtocolDecl__Group__1 : rule__GlobalProtocolDecl__Group__1__Impl rule__GlobalProtocolDecl__Group__2 ;
    public final void rule__GlobalProtocolDecl__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2444:1: ( rule__GlobalProtocolDecl__Group__1__Impl rule__GlobalProtocolDecl__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2445:2: rule__GlobalProtocolDecl__Group__1__Impl rule__GlobalProtocolDecl__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__1__Impl_in_rule__GlobalProtocolDecl__Group__14936);
            rule__GlobalProtocolDecl__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__2_in_rule__GlobalProtocolDecl__Group__14939);
            rule__GlobalProtocolDecl__Group__2();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2452:1: rule__GlobalProtocolDecl__Group__1__Impl : ( 'protocol' ) ;
    public final void rule__GlobalProtocolDecl__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2456:1: ( ( 'protocol' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2457:1: ( 'protocol' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2457:1: ( 'protocol' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2458:1: 'protocol'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getProtocolKeyword_1()); 
            match(input,25,FOLLOW_25_in_rule__GlobalProtocolDecl__Group__1__Impl4967); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getProtocolKeyword_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2471:1: rule__GlobalProtocolDecl__Group__2 : rule__GlobalProtocolDecl__Group__2__Impl rule__GlobalProtocolDecl__Group__3 ;
    public final void rule__GlobalProtocolDecl__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2475:1: ( rule__GlobalProtocolDecl__Group__2__Impl rule__GlobalProtocolDecl__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2476:2: rule__GlobalProtocolDecl__Group__2__Impl rule__GlobalProtocolDecl__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__2__Impl_in_rule__GlobalProtocolDecl__Group__24998);
            rule__GlobalProtocolDecl__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__3_in_rule__GlobalProtocolDecl__Group__25001);
            rule__GlobalProtocolDecl__Group__3();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__2"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2483:1: rule__GlobalProtocolDecl__Group__2__Impl : ( ( rule__GlobalProtocolDecl__NameAssignment_2 ) ) ;
    public final void rule__GlobalProtocolDecl__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2487:1: ( ( ( rule__GlobalProtocolDecl__NameAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2488:1: ( ( rule__GlobalProtocolDecl__NameAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2488:1: ( ( rule__GlobalProtocolDecl__NameAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2489:1: ( rule__GlobalProtocolDecl__NameAssignment_2 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getNameAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2490:1: ( rule__GlobalProtocolDecl__NameAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2490:2: rule__GlobalProtocolDecl__NameAssignment_2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__NameAssignment_2_in_rule__GlobalProtocolDecl__Group__2__Impl5028);
            rule__GlobalProtocolDecl__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getNameAssignment_2()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__2__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2500:1: rule__GlobalProtocolDecl__Group__3 : rule__GlobalProtocolDecl__Group__3__Impl rule__GlobalProtocolDecl__Group__4 ;
    public final void rule__GlobalProtocolDecl__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2504:1: ( rule__GlobalProtocolDecl__Group__3__Impl rule__GlobalProtocolDecl__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2505:2: rule__GlobalProtocolDecl__Group__3__Impl rule__GlobalProtocolDecl__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__3__Impl_in_rule__GlobalProtocolDecl__Group__35058);
            rule__GlobalProtocolDecl__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__4_in_rule__GlobalProtocolDecl__Group__35061);
            rule__GlobalProtocolDecl__Group__4();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__3"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2512:1: rule__GlobalProtocolDecl__Group__3__Impl : ( ( rule__GlobalProtocolDecl__Group_3__0 )? ) ;
    public final void rule__GlobalProtocolDecl__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2516:1: ( ( ( rule__GlobalProtocolDecl__Group_3__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2517:1: ( ( rule__GlobalProtocolDecl__Group_3__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2517:1: ( ( rule__GlobalProtocolDecl__Group_3__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2518:1: ( rule__GlobalProtocolDecl__Group_3__0 )?
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2519:1: ( rule__GlobalProtocolDecl__Group_3__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==18) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2519:2: rule__GlobalProtocolDecl__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__0_in_rule__GlobalProtocolDecl__Group__3__Impl5088);
                    rule__GlobalProtocolDecl__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_3()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__3__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2529:1: rule__GlobalProtocolDecl__Group__4 : rule__GlobalProtocolDecl__Group__4__Impl rule__GlobalProtocolDecl__Group__5 ;
    public final void rule__GlobalProtocolDecl__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2533:1: ( rule__GlobalProtocolDecl__Group__4__Impl rule__GlobalProtocolDecl__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2534:2: rule__GlobalProtocolDecl__Group__4__Impl rule__GlobalProtocolDecl__Group__5
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__4__Impl_in_rule__GlobalProtocolDecl__Group__45119);
            rule__GlobalProtocolDecl__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__5_in_rule__GlobalProtocolDecl__Group__45122);
            rule__GlobalProtocolDecl__Group__5();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__4"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2541:1: rule__GlobalProtocolDecl__Group__4__Impl : ( '(' ) ;
    public final void rule__GlobalProtocolDecl__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2545:1: ( ( '(' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2546:1: ( '(' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2546:1: ( '(' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2547:1: '('
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_4()); 
            match(input,20,FOLLOW_20_in_rule__GlobalProtocolDecl__Group__4__Impl5150); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_4()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__4__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2560:1: rule__GlobalProtocolDecl__Group__5 : rule__GlobalProtocolDecl__Group__5__Impl rule__GlobalProtocolDecl__Group__6 ;
    public final void rule__GlobalProtocolDecl__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2564:1: ( rule__GlobalProtocolDecl__Group__5__Impl rule__GlobalProtocolDecl__Group__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2565:2: rule__GlobalProtocolDecl__Group__5__Impl rule__GlobalProtocolDecl__Group__6
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__5__Impl_in_rule__GlobalProtocolDecl__Group__55181);
            rule__GlobalProtocolDecl__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__6_in_rule__GlobalProtocolDecl__Group__55184);
            rule__GlobalProtocolDecl__Group__6();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__5"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2572:1: rule__GlobalProtocolDecl__Group__5__Impl : ( ( rule__GlobalProtocolDecl__RolesAssignment_5 ) ) ;
    public final void rule__GlobalProtocolDecl__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2576:1: ( ( ( rule__GlobalProtocolDecl__RolesAssignment_5 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2577:1: ( ( rule__GlobalProtocolDecl__RolesAssignment_5 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2577:1: ( ( rule__GlobalProtocolDecl__RolesAssignment_5 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2578:1: ( rule__GlobalProtocolDecl__RolesAssignment_5 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRolesAssignment_5()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2579:1: ( rule__GlobalProtocolDecl__RolesAssignment_5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2579:2: rule__GlobalProtocolDecl__RolesAssignment_5
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__RolesAssignment_5_in_rule__GlobalProtocolDecl__Group__5__Impl5211);
            rule__GlobalProtocolDecl__RolesAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getRolesAssignment_5()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__5__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2589:1: rule__GlobalProtocolDecl__Group__6 : rule__GlobalProtocolDecl__Group__6__Impl rule__GlobalProtocolDecl__Group__7 ;
    public final void rule__GlobalProtocolDecl__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2593:1: ( rule__GlobalProtocolDecl__Group__6__Impl rule__GlobalProtocolDecl__Group__7 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2594:2: rule__GlobalProtocolDecl__Group__6__Impl rule__GlobalProtocolDecl__Group__7
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__6__Impl_in_rule__GlobalProtocolDecl__Group__65241);
            rule__GlobalProtocolDecl__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__7_in_rule__GlobalProtocolDecl__Group__65244);
            rule__GlobalProtocolDecl__Group__7();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__6"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2601:1: rule__GlobalProtocolDecl__Group__6__Impl : ( ( rule__GlobalProtocolDecl__Group_6__0 )* ) ;
    public final void rule__GlobalProtocolDecl__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2605:1: ( ( ( rule__GlobalProtocolDecl__Group_6__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2606:1: ( ( rule__GlobalProtocolDecl__Group_6__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2606:1: ( ( rule__GlobalProtocolDecl__Group_6__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2607:1: ( rule__GlobalProtocolDecl__Group_6__0 )*
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_6()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2608:1: ( rule__GlobalProtocolDecl__Group_6__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==22) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2608:2: rule__GlobalProtocolDecl__Group_6__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_6__0_in_rule__GlobalProtocolDecl__Group__6__Impl5271);
            	    rule__GlobalProtocolDecl__Group_6__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_6()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__6__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__7"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2618:1: rule__GlobalProtocolDecl__Group__7 : rule__GlobalProtocolDecl__Group__7__Impl rule__GlobalProtocolDecl__Group__8 ;
    public final void rule__GlobalProtocolDecl__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2622:1: ( rule__GlobalProtocolDecl__Group__7__Impl rule__GlobalProtocolDecl__Group__8 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2623:2: rule__GlobalProtocolDecl__Group__7__Impl rule__GlobalProtocolDecl__Group__8
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__7__Impl_in_rule__GlobalProtocolDecl__Group__75302);
            rule__GlobalProtocolDecl__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__8_in_rule__GlobalProtocolDecl__Group__75305);
            rule__GlobalProtocolDecl__Group__8();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__7"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__7__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2630:1: rule__GlobalProtocolDecl__Group__7__Impl : ( ')' ) ;
    public final void rule__GlobalProtocolDecl__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2634:1: ( ( ')' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2635:1: ( ')' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2635:1: ( ')' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2636:1: ')'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_7()); 
            match(input,21,FOLLOW_21_in_rule__GlobalProtocolDecl__Group__7__Impl5333); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_7()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__7__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__8"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2649:1: rule__GlobalProtocolDecl__Group__8 : rule__GlobalProtocolDecl__Group__8__Impl ;
    public final void rule__GlobalProtocolDecl__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2653:1: ( rule__GlobalProtocolDecl__Group__8__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2654:2: rule__GlobalProtocolDecl__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group__8__Impl_in_rule__GlobalProtocolDecl__Group__85364);
            rule__GlobalProtocolDecl__Group__8__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__8"


    // $ANTLR start "rule__GlobalProtocolDecl__Group__8__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2660:1: rule__GlobalProtocolDecl__Group__8__Impl : ( ( rule__GlobalProtocolDecl__Alternatives_8 ) ) ;
    public final void rule__GlobalProtocolDecl__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2664:1: ( ( ( rule__GlobalProtocolDecl__Alternatives_8 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2665:1: ( ( rule__GlobalProtocolDecl__Alternatives_8 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2665:1: ( ( rule__GlobalProtocolDecl__Alternatives_8 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2666:1: ( rule__GlobalProtocolDecl__Alternatives_8 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getAlternatives_8()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2667:1: ( rule__GlobalProtocolDecl__Alternatives_8 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2667:2: rule__GlobalProtocolDecl__Alternatives_8
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Alternatives_8_in_rule__GlobalProtocolDecl__Group__8__Impl5391);
            rule__GlobalProtocolDecl__Alternatives_8();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getAlternatives_8()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group__8__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2695:1: rule__GlobalProtocolDecl__Group_3__0 : rule__GlobalProtocolDecl__Group_3__0__Impl rule__GlobalProtocolDecl__Group_3__1 ;
    public final void rule__GlobalProtocolDecl__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2699:1: ( rule__GlobalProtocolDecl__Group_3__0__Impl rule__GlobalProtocolDecl__Group_3__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2700:2: rule__GlobalProtocolDecl__Group_3__0__Impl rule__GlobalProtocolDecl__Group_3__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__0__Impl_in_rule__GlobalProtocolDecl__Group_3__05439);
            rule__GlobalProtocolDecl__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__1_in_rule__GlobalProtocolDecl__Group_3__05442);
            rule__GlobalProtocolDecl__Group_3__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2707:1: rule__GlobalProtocolDecl__Group_3__0__Impl : ( '<' ) ;
    public final void rule__GlobalProtocolDecl__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2711:1: ( ( '<' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2712:1: ( '<' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2712:1: ( '<' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2713:1: '<'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_3_0()); 
            match(input,18,FOLLOW_18_in_rule__GlobalProtocolDecl__Group_3__0__Impl5470); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_3_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2726:1: rule__GlobalProtocolDecl__Group_3__1 : rule__GlobalProtocolDecl__Group_3__1__Impl rule__GlobalProtocolDecl__Group_3__2 ;
    public final void rule__GlobalProtocolDecl__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2730:1: ( rule__GlobalProtocolDecl__Group_3__1__Impl rule__GlobalProtocolDecl__Group_3__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2731:2: rule__GlobalProtocolDecl__Group_3__1__Impl rule__GlobalProtocolDecl__Group_3__2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__1__Impl_in_rule__GlobalProtocolDecl__Group_3__15501);
            rule__GlobalProtocolDecl__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__2_in_rule__GlobalProtocolDecl__Group_3__15504);
            rule__GlobalProtocolDecl__Group_3__2();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2738:1: rule__GlobalProtocolDecl__Group_3__1__Impl : ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2742:1: ( ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2743:1: ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2743:1: ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2744:1: ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getParametersAssignment_3_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2745:1: ( rule__GlobalProtocolDecl__ParametersAssignment_3_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2745:2: rule__GlobalProtocolDecl__ParametersAssignment_3_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__ParametersAssignment_3_1_in_rule__GlobalProtocolDecl__Group_3__1__Impl5531);
            rule__GlobalProtocolDecl__ParametersAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getParametersAssignment_3_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2755:1: rule__GlobalProtocolDecl__Group_3__2 : rule__GlobalProtocolDecl__Group_3__2__Impl rule__GlobalProtocolDecl__Group_3__3 ;
    public final void rule__GlobalProtocolDecl__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2759:1: ( rule__GlobalProtocolDecl__Group_3__2__Impl rule__GlobalProtocolDecl__Group_3__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2760:2: rule__GlobalProtocolDecl__Group_3__2__Impl rule__GlobalProtocolDecl__Group_3__3
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__2__Impl_in_rule__GlobalProtocolDecl__Group_3__25561);
            rule__GlobalProtocolDecl__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__3_in_rule__GlobalProtocolDecl__Group_3__25564);
            rule__GlobalProtocolDecl__Group_3__3();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__2"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2767:1: rule__GlobalProtocolDecl__Group_3__2__Impl : ( ( rule__GlobalProtocolDecl__Group_3_2__0 )* ) ;
    public final void rule__GlobalProtocolDecl__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2771:1: ( ( ( rule__GlobalProtocolDecl__Group_3_2__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2772:1: ( ( rule__GlobalProtocolDecl__Group_3_2__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2772:1: ( ( rule__GlobalProtocolDecl__Group_3_2__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2773:1: ( rule__GlobalProtocolDecl__Group_3_2__0 )*
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_3_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2774:1: ( rule__GlobalProtocolDecl__Group_3_2__0 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==22) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2774:2: rule__GlobalProtocolDecl__Group_3_2__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3_2__0_in_rule__GlobalProtocolDecl__Group_3__2__Impl5591);
            	    rule__GlobalProtocolDecl__Group_3_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_3_2()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__2__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2784:1: rule__GlobalProtocolDecl__Group_3__3 : rule__GlobalProtocolDecl__Group_3__3__Impl ;
    public final void rule__GlobalProtocolDecl__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2788:1: ( rule__GlobalProtocolDecl__Group_3__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2789:2: rule__GlobalProtocolDecl__Group_3__3__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3__3__Impl_in_rule__GlobalProtocolDecl__Group_3__35622);
            rule__GlobalProtocolDecl__Group_3__3__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__3"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2795:1: rule__GlobalProtocolDecl__Group_3__3__Impl : ( '>' ) ;
    public final void rule__GlobalProtocolDecl__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2799:1: ( ( '>' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2800:1: ( '>' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2800:1: ( '>' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2801:1: '>'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_3_3()); 
            match(input,19,FOLLOW_19_in_rule__GlobalProtocolDecl__Group_3__3__Impl5650); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_3_3()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3__3__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2822:1: rule__GlobalProtocolDecl__Group_3_2__0 : rule__GlobalProtocolDecl__Group_3_2__0__Impl rule__GlobalProtocolDecl__Group_3_2__1 ;
    public final void rule__GlobalProtocolDecl__Group_3_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2826:1: ( rule__GlobalProtocolDecl__Group_3_2__0__Impl rule__GlobalProtocolDecl__Group_3_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2827:2: rule__GlobalProtocolDecl__Group_3_2__0__Impl rule__GlobalProtocolDecl__Group_3_2__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3_2__0__Impl_in_rule__GlobalProtocolDecl__Group_3_2__05689);
            rule__GlobalProtocolDecl__Group_3_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3_2__1_in_rule__GlobalProtocolDecl__Group_3_2__05692);
            rule__GlobalProtocolDecl__Group_3_2__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3_2__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2834:1: rule__GlobalProtocolDecl__Group_3_2__0__Impl : ( ',' ) ;
    public final void rule__GlobalProtocolDecl__Group_3_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2838:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2839:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2839:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2840:1: ','
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_3_2_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalProtocolDecl__Group_3_2__0__Impl5720); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_3_2_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3_2__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2853:1: rule__GlobalProtocolDecl__Group_3_2__1 : rule__GlobalProtocolDecl__Group_3_2__1__Impl ;
    public final void rule__GlobalProtocolDecl__Group_3_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2857:1: ( rule__GlobalProtocolDecl__Group_3_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2858:2: rule__GlobalProtocolDecl__Group_3_2__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_3_2__1__Impl_in_rule__GlobalProtocolDecl__Group_3_2__15751);
            rule__GlobalProtocolDecl__Group_3_2__1__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3_2__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_3_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2864:1: rule__GlobalProtocolDecl__Group_3_2__1__Impl : ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_3_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2868:1: ( ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2869:1: ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2869:1: ( ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2870:1: ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getParametersAssignment_3_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2871:1: ( rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2871:2: rule__GlobalProtocolDecl__ParametersAssignment_3_2_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__ParametersAssignment_3_2_1_in_rule__GlobalProtocolDecl__Group_3_2__1__Impl5778);
            rule__GlobalProtocolDecl__ParametersAssignment_3_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getParametersAssignment_3_2_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_3_2__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_6__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2885:1: rule__GlobalProtocolDecl__Group_6__0 : rule__GlobalProtocolDecl__Group_6__0__Impl rule__GlobalProtocolDecl__Group_6__1 ;
    public final void rule__GlobalProtocolDecl__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2889:1: ( rule__GlobalProtocolDecl__Group_6__0__Impl rule__GlobalProtocolDecl__Group_6__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2890:2: rule__GlobalProtocolDecl__Group_6__0__Impl rule__GlobalProtocolDecl__Group_6__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_6__0__Impl_in_rule__GlobalProtocolDecl__Group_6__05812);
            rule__GlobalProtocolDecl__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_6__1_in_rule__GlobalProtocolDecl__Group_6__05815);
            rule__GlobalProtocolDecl__Group_6__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_6__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_6__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2897:1: rule__GlobalProtocolDecl__Group_6__0__Impl : ( ',' ) ;
    public final void rule__GlobalProtocolDecl__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2901:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2902:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2902:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2903:1: ','
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_6_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalProtocolDecl__Group_6__0__Impl5843); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_6_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_6__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_6__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2916:1: rule__GlobalProtocolDecl__Group_6__1 : rule__GlobalProtocolDecl__Group_6__1__Impl ;
    public final void rule__GlobalProtocolDecl__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2920:1: ( rule__GlobalProtocolDecl__Group_6__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2921:2: rule__GlobalProtocolDecl__Group_6__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_6__1__Impl_in_rule__GlobalProtocolDecl__Group_6__15874);
            rule__GlobalProtocolDecl__Group_6__1__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_6__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_6__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2927:1: rule__GlobalProtocolDecl__Group_6__1__Impl : ( ( rule__GlobalProtocolDecl__RolesAssignment_6_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2931:1: ( ( ( rule__GlobalProtocolDecl__RolesAssignment_6_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2932:1: ( ( rule__GlobalProtocolDecl__RolesAssignment_6_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2932:1: ( ( rule__GlobalProtocolDecl__RolesAssignment_6_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2933:1: ( rule__GlobalProtocolDecl__RolesAssignment_6_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRolesAssignment_6_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2934:1: ( rule__GlobalProtocolDecl__RolesAssignment_6_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2934:2: rule__GlobalProtocolDecl__RolesAssignment_6_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__RolesAssignment_6_1_in_rule__GlobalProtocolDecl__Group_6__1__Impl5901);
            rule__GlobalProtocolDecl__RolesAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getRolesAssignment_6_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_6__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2948:1: rule__GlobalProtocolDecl__Group_8_1__0 : rule__GlobalProtocolDecl__Group_8_1__0__Impl rule__GlobalProtocolDecl__Group_8_1__1 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2952:1: ( rule__GlobalProtocolDecl__Group_8_1__0__Impl rule__GlobalProtocolDecl__Group_8_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2953:2: rule__GlobalProtocolDecl__Group_8_1__0__Impl rule__GlobalProtocolDecl__Group_8_1__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1__05935);
            rule__GlobalProtocolDecl__Group_8_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__1_in_rule__GlobalProtocolDecl__Group_8_1__05938);
            rule__GlobalProtocolDecl__Group_8_1__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2960:1: rule__GlobalProtocolDecl__Group_8_1__0__Impl : ( 'instantiates' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2964:1: ( ( 'instantiates' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2965:1: ( 'instantiates' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2965:1: ( 'instantiates' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2966:1: 'instantiates'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesKeyword_8_1_0()); 
            match(input,26,FOLLOW_26_in_rule__GlobalProtocolDecl__Group_8_1__0__Impl5966); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesKeyword_8_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2979:1: rule__GlobalProtocolDecl__Group_8_1__1 : rule__GlobalProtocolDecl__Group_8_1__1__Impl rule__GlobalProtocolDecl__Group_8_1__2 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2983:1: ( rule__GlobalProtocolDecl__Group_8_1__1__Impl rule__GlobalProtocolDecl__Group_8_1__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2984:2: rule__GlobalProtocolDecl__Group_8_1__1__Impl rule__GlobalProtocolDecl__Group_8_1__2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1__15997);
            rule__GlobalProtocolDecl__Group_8_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__2_in_rule__GlobalProtocolDecl__Group_8_1__16000);
            rule__GlobalProtocolDecl__Group_8_1__2();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2991:1: rule__GlobalProtocolDecl__Group_8_1__1__Impl : ( ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2995:1: ( ( ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2996:1: ( ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2996:1: ( ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2997:1: ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesAssignment_8_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2998:1: ( rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:2998:2: rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1_in_rule__GlobalProtocolDecl__Group_8_1__1__Impl6027);
            rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesAssignment_8_1_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3008:1: rule__GlobalProtocolDecl__Group_8_1__2 : rule__GlobalProtocolDecl__Group_8_1__2__Impl rule__GlobalProtocolDecl__Group_8_1__3 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3012:1: ( rule__GlobalProtocolDecl__Group_8_1__2__Impl rule__GlobalProtocolDecl__Group_8_1__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3013:2: rule__GlobalProtocolDecl__Group_8_1__2__Impl rule__GlobalProtocolDecl__Group_8_1__3
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__2__Impl_in_rule__GlobalProtocolDecl__Group_8_1__26057);
            rule__GlobalProtocolDecl__Group_8_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__3_in_rule__GlobalProtocolDecl__Group_8_1__26060);
            rule__GlobalProtocolDecl__Group_8_1__3();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__2"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3020:1: rule__GlobalProtocolDecl__Group_8_1__2__Impl : ( ( rule__GlobalProtocolDecl__Group_8_1_2__0 )? ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3024:1: ( ( ( rule__GlobalProtocolDecl__Group_8_1_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3025:1: ( ( rule__GlobalProtocolDecl__Group_8_1_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3025:1: ( ( rule__GlobalProtocolDecl__Group_8_1_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3026:1: ( rule__GlobalProtocolDecl__Group_8_1_2__0 )?
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3027:1: ( rule__GlobalProtocolDecl__Group_8_1_2__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==18) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3027:2: rule__GlobalProtocolDecl__Group_8_1_2__0
                    {
                    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__0_in_rule__GlobalProtocolDecl__Group_8_1__2__Impl6087);
                    rule__GlobalProtocolDecl__Group_8_1_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_2()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__2__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3037:1: rule__GlobalProtocolDecl__Group_8_1__3 : rule__GlobalProtocolDecl__Group_8_1__3__Impl rule__GlobalProtocolDecl__Group_8_1__4 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3041:1: ( rule__GlobalProtocolDecl__Group_8_1__3__Impl rule__GlobalProtocolDecl__Group_8_1__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3042:2: rule__GlobalProtocolDecl__Group_8_1__3__Impl rule__GlobalProtocolDecl__Group_8_1__4
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__3__Impl_in_rule__GlobalProtocolDecl__Group_8_1__36118);
            rule__GlobalProtocolDecl__Group_8_1__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__4_in_rule__GlobalProtocolDecl__Group_8_1__36121);
            rule__GlobalProtocolDecl__Group_8_1__4();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__3"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3049:1: rule__GlobalProtocolDecl__Group_8_1__3__Impl : ( '(' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3053:1: ( ( '(' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3054:1: ( '(' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3054:1: ( '(' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3055:1: '('
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_8_1_3()); 
            match(input,20,FOLLOW_20_in_rule__GlobalProtocolDecl__Group_8_1__3__Impl6149); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getLeftParenthesisKeyword_8_1_3()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__3__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3068:1: rule__GlobalProtocolDecl__Group_8_1__4 : rule__GlobalProtocolDecl__Group_8_1__4__Impl rule__GlobalProtocolDecl__Group_8_1__5 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3072:1: ( rule__GlobalProtocolDecl__Group_8_1__4__Impl rule__GlobalProtocolDecl__Group_8_1__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3073:2: rule__GlobalProtocolDecl__Group_8_1__4__Impl rule__GlobalProtocolDecl__Group_8_1__5
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__4__Impl_in_rule__GlobalProtocolDecl__Group_8_1__46180);
            rule__GlobalProtocolDecl__Group_8_1__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__5_in_rule__GlobalProtocolDecl__Group_8_1__46183);
            rule__GlobalProtocolDecl__Group_8_1__5();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__4"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3080:1: rule__GlobalProtocolDecl__Group_8_1__4__Impl : ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3084:1: ( ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3085:1: ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3085:1: ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3086:1: ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsAssignment_8_1_4()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3087:1: ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3087:2: rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4_in_rule__GlobalProtocolDecl__Group_8_1__4__Impl6210);
            rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsAssignment_8_1_4()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__4__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3097:1: rule__GlobalProtocolDecl__Group_8_1__5 : rule__GlobalProtocolDecl__Group_8_1__5__Impl rule__GlobalProtocolDecl__Group_8_1__6 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3101:1: ( rule__GlobalProtocolDecl__Group_8_1__5__Impl rule__GlobalProtocolDecl__Group_8_1__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3102:2: rule__GlobalProtocolDecl__Group_8_1__5__Impl rule__GlobalProtocolDecl__Group_8_1__6
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__5__Impl_in_rule__GlobalProtocolDecl__Group_8_1__56240);
            rule__GlobalProtocolDecl__Group_8_1__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__6_in_rule__GlobalProtocolDecl__Group_8_1__56243);
            rule__GlobalProtocolDecl__Group_8_1__6();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__5"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3109:1: rule__GlobalProtocolDecl__Group_8_1__5__Impl : ( ( rule__GlobalProtocolDecl__Group_8_1_5__0 )* ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3113:1: ( ( ( rule__GlobalProtocolDecl__Group_8_1_5__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3114:1: ( ( rule__GlobalProtocolDecl__Group_8_1_5__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3114:1: ( ( rule__GlobalProtocolDecl__Group_8_1_5__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3115:1: ( rule__GlobalProtocolDecl__Group_8_1_5__0 )*
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_5()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3116:1: ( rule__GlobalProtocolDecl__Group_8_1_5__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==22) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3116:2: rule__GlobalProtocolDecl__Group_8_1_5__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__0_in_rule__GlobalProtocolDecl__Group_8_1__5__Impl6270);
            	    rule__GlobalProtocolDecl__Group_8_1_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_5()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__5__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3126:1: rule__GlobalProtocolDecl__Group_8_1__6 : rule__GlobalProtocolDecl__Group_8_1__6__Impl rule__GlobalProtocolDecl__Group_8_1__7 ;
    public final void rule__GlobalProtocolDecl__Group_8_1__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3130:1: ( rule__GlobalProtocolDecl__Group_8_1__6__Impl rule__GlobalProtocolDecl__Group_8_1__7 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3131:2: rule__GlobalProtocolDecl__Group_8_1__6__Impl rule__GlobalProtocolDecl__Group_8_1__7
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__6__Impl_in_rule__GlobalProtocolDecl__Group_8_1__66301);
            rule__GlobalProtocolDecl__Group_8_1__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__7_in_rule__GlobalProtocolDecl__Group_8_1__66304);
            rule__GlobalProtocolDecl__Group_8_1__7();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__6"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3138:1: rule__GlobalProtocolDecl__Group_8_1__6__Impl : ( ')' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3142:1: ( ( ')' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3143:1: ( ')' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3143:1: ( ')' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3144:1: ')'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_8_1_6()); 
            match(input,21,FOLLOW_21_in_rule__GlobalProtocolDecl__Group_8_1__6__Impl6332); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getRightParenthesisKeyword_8_1_6()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__6__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__7"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3157:1: rule__GlobalProtocolDecl__Group_8_1__7 : rule__GlobalProtocolDecl__Group_8_1__7__Impl ;
    public final void rule__GlobalProtocolDecl__Group_8_1__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3161:1: ( rule__GlobalProtocolDecl__Group_8_1__7__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3162:2: rule__GlobalProtocolDecl__Group_8_1__7__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1__7__Impl_in_rule__GlobalProtocolDecl__Group_8_1__76363);
            rule__GlobalProtocolDecl__Group_8_1__7__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__7"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1__7__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3168:1: rule__GlobalProtocolDecl__Group_8_1__7__Impl : ( ';' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3172:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3173:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3173:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3174:1: ';'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getSemicolonKeyword_8_1_7()); 
            match(input,12,FOLLOW_12_in_rule__GlobalProtocolDecl__Group_8_1__7__Impl6391); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getSemicolonKeyword_8_1_7()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1__7__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3203:1: rule__GlobalProtocolDecl__Group_8_1_2__0 : rule__GlobalProtocolDecl__Group_8_1_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2__1 ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3207:1: ( rule__GlobalProtocolDecl__Group_8_1_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3208:2: rule__GlobalProtocolDecl__Group_8_1_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__06438);
            rule__GlobalProtocolDecl__Group_8_1_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__1_in_rule__GlobalProtocolDecl__Group_8_1_2__06441);
            rule__GlobalProtocolDecl__Group_8_1_2__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3215:1: rule__GlobalProtocolDecl__Group_8_1_2__0__Impl : ( '<' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3219:1: ( ( '<' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3220:1: ( '<' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3220:1: ( '<' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3221:1: '<'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_8_1_2_0()); 
            match(input,18,FOLLOW_18_in_rule__GlobalProtocolDecl__Group_8_1_2__0__Impl6469); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getLessThanSignKeyword_8_1_2_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3234:1: rule__GlobalProtocolDecl__Group_8_1_2__1 : rule__GlobalProtocolDecl__Group_8_1_2__1__Impl rule__GlobalProtocolDecl__Group_8_1_2__2 ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3238:1: ( rule__GlobalProtocolDecl__Group_8_1_2__1__Impl rule__GlobalProtocolDecl__Group_8_1_2__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3239:2: rule__GlobalProtocolDecl__Group_8_1_2__1__Impl rule__GlobalProtocolDecl__Group_8_1_2__2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__16500);
            rule__GlobalProtocolDecl__Group_8_1_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__2_in_rule__GlobalProtocolDecl__Group_8_1_2__16503);
            rule__GlobalProtocolDecl__Group_8_1_2__2();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3246:1: rule__GlobalProtocolDecl__Group_8_1_2__1__Impl : ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3250:1: ( ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3251:1: ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3251:1: ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3252:1: ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsAssignment_8_1_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3253:1: ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3253:2: rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1_in_rule__GlobalProtocolDecl__Group_8_1_2__1__Impl6530);
            rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsAssignment_8_1_2_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3263:1: rule__GlobalProtocolDecl__Group_8_1_2__2 : rule__GlobalProtocolDecl__Group_8_1_2__2__Impl rule__GlobalProtocolDecl__Group_8_1_2__3 ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3267:1: ( rule__GlobalProtocolDecl__Group_8_1_2__2__Impl rule__GlobalProtocolDecl__Group_8_1_2__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3268:2: rule__GlobalProtocolDecl__Group_8_1_2__2__Impl rule__GlobalProtocolDecl__Group_8_1_2__3
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__2__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__26560);
            rule__GlobalProtocolDecl__Group_8_1_2__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__3_in_rule__GlobalProtocolDecl__Group_8_1_2__26563);
            rule__GlobalProtocolDecl__Group_8_1_2__3();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__2"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3275:1: rule__GlobalProtocolDecl__Group_8_1_2__2__Impl : ( ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )* ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3279:1: ( ( ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3280:1: ( ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3280:1: ( ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3281:1: ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )*
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_2_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3282:1: ( rule__GlobalProtocolDecl__Group_8_1_2_2__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==22) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3282:2: rule__GlobalProtocolDecl__Group_8_1_2_2__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__0_in_rule__GlobalProtocolDecl__Group_8_1_2__2__Impl6590);
            	    rule__GlobalProtocolDecl__Group_8_1_2_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getGlobalProtocolDeclAccess().getGroup_8_1_2_2()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__2__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3292:1: rule__GlobalProtocolDecl__Group_8_1_2__3 : rule__GlobalProtocolDecl__Group_8_1_2__3__Impl ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3296:1: ( rule__GlobalProtocolDecl__Group_8_1_2__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3297:2: rule__GlobalProtocolDecl__Group_8_1_2__3__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__3__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__36621);
            rule__GlobalProtocolDecl__Group_8_1_2__3__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__3"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3303:1: rule__GlobalProtocolDecl__Group_8_1_2__3__Impl : ( '>' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3307:1: ( ( '>' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3308:1: ( '>' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3308:1: ( '>' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3309:1: '>'
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_8_1_2_3()); 
            match(input,19,FOLLOW_19_in_rule__GlobalProtocolDecl__Group_8_1_2__3__Impl6649); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getGreaterThanSignKeyword_8_1_2_3()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2__3__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3330:1: rule__GlobalProtocolDecl__Group_8_1_2_2__0 : rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2_2__1 ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3334:1: ( rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3335:2: rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl rule__GlobalProtocolDecl__Group_8_1_2_2__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2_2__06688);
            rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__1_in_rule__GlobalProtocolDecl__Group_8_1_2_2__06691);
            rule__GlobalProtocolDecl__Group_8_1_2_2__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2_2__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3342:1: rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl : ( ',' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3346:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3347:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3347:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3348:1: ','
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_2_2_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl6719); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_2_2_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3361:1: rule__GlobalProtocolDecl__Group_8_1_2_2__1 : rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3365:1: ( rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3366:2: rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2_2__16750);
            rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2_2__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3372:1: rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl : ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3376:1: ( ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3377:1: ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3377:1: ( ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3378:1: ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsAssignment_8_1_2_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3379:1: ( rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3379:2: rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1_in_rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl6777);
            rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsAssignment_8_1_2_2_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_5__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3393:1: rule__GlobalProtocolDecl__Group_8_1_5__0 : rule__GlobalProtocolDecl__Group_8_1_5__0__Impl rule__GlobalProtocolDecl__Group_8_1_5__1 ;
    public final void rule__GlobalProtocolDecl__Group_8_1_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3397:1: ( rule__GlobalProtocolDecl__Group_8_1_5__0__Impl rule__GlobalProtocolDecl__Group_8_1_5__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3398:2: rule__GlobalProtocolDecl__Group_8_1_5__0__Impl rule__GlobalProtocolDecl__Group_8_1_5__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_5__06811);
            rule__GlobalProtocolDecl__Group_8_1_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__1_in_rule__GlobalProtocolDecl__Group_8_1_5__06814);
            rule__GlobalProtocolDecl__Group_8_1_5__1();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_5__0"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_5__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3405:1: rule__GlobalProtocolDecl__Group_8_1_5__0__Impl : ( ',' ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3409:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3410:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3410:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3411:1: ','
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_5_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalProtocolDecl__Group_8_1_5__0__Impl6842); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getCommaKeyword_8_1_5_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_5__0__Impl"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_5__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3424:1: rule__GlobalProtocolDecl__Group_8_1_5__1 : rule__GlobalProtocolDecl__Group_8_1_5__1__Impl ;
    public final void rule__GlobalProtocolDecl__Group_8_1_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3428:1: ( rule__GlobalProtocolDecl__Group_8_1_5__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3429:2: rule__GlobalProtocolDecl__Group_8_1_5__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_5__16873);
            rule__GlobalProtocolDecl__Group_8_1_5__1__Impl();

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_5__1"


    // $ANTLR start "rule__GlobalProtocolDecl__Group_8_1_5__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3435:1: rule__GlobalProtocolDecl__Group_8_1_5__1__Impl : ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 ) ) ;
    public final void rule__GlobalProtocolDecl__Group_8_1_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3439:1: ( ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3440:1: ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3440:1: ( ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3441:1: ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 )
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsAssignment_8_1_5_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3442:1: ( rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3442:2: rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1_in_rule__GlobalProtocolDecl__Group_8_1_5__1__Impl6900);
            rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsAssignment_8_1_5_1()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__Group_8_1_5__1__Impl"


    // $ANTLR start "rule__RoleDecl__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3456:1: rule__RoleDecl__Group__0 : rule__RoleDecl__Group__0__Impl rule__RoleDecl__Group__1 ;
    public final void rule__RoleDecl__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3460:1: ( rule__RoleDecl__Group__0__Impl rule__RoleDecl__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3461:2: rule__RoleDecl__Group__0__Impl rule__RoleDecl__Group__1
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group__0__Impl_in_rule__RoleDecl__Group__06934);
            rule__RoleDecl__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RoleDecl__Group__1_in_rule__RoleDecl__Group__06937);
            rule__RoleDecl__Group__1();

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
    // $ANTLR end "rule__RoleDecl__Group__0"


    // $ANTLR start "rule__RoleDecl__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3468:1: rule__RoleDecl__Group__0__Impl : ( 'role' ) ;
    public final void rule__RoleDecl__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3472:1: ( ( 'role' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3473:1: ( 'role' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3473:1: ( 'role' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3474:1: 'role'
            {
             before(grammarAccess.getRoleDeclAccess().getRoleKeyword_0()); 
            match(input,27,FOLLOW_27_in_rule__RoleDecl__Group__0__Impl6965); 
             after(grammarAccess.getRoleDeclAccess().getRoleKeyword_0()); 

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
    // $ANTLR end "rule__RoleDecl__Group__0__Impl"


    // $ANTLR start "rule__RoleDecl__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3487:1: rule__RoleDecl__Group__1 : rule__RoleDecl__Group__1__Impl rule__RoleDecl__Group__2 ;
    public final void rule__RoleDecl__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3491:1: ( rule__RoleDecl__Group__1__Impl rule__RoleDecl__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3492:2: rule__RoleDecl__Group__1__Impl rule__RoleDecl__Group__2
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group__1__Impl_in_rule__RoleDecl__Group__16996);
            rule__RoleDecl__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RoleDecl__Group__2_in_rule__RoleDecl__Group__16999);
            rule__RoleDecl__Group__2();

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
    // $ANTLR end "rule__RoleDecl__Group__1"


    // $ANTLR start "rule__RoleDecl__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3499:1: rule__RoleDecl__Group__1__Impl : ( ( rule__RoleDecl__NameAssignment_1 ) ) ;
    public final void rule__RoleDecl__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3503:1: ( ( ( rule__RoleDecl__NameAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3504:1: ( ( rule__RoleDecl__NameAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3504:1: ( ( rule__RoleDecl__NameAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3505:1: ( rule__RoleDecl__NameAssignment_1 )
            {
             before(grammarAccess.getRoleDeclAccess().getNameAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3506:1: ( rule__RoleDecl__NameAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3506:2: rule__RoleDecl__NameAssignment_1
            {
            pushFollow(FOLLOW_rule__RoleDecl__NameAssignment_1_in_rule__RoleDecl__Group__1__Impl7026);
            rule__RoleDecl__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getRoleDeclAccess().getNameAssignment_1()); 

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
    // $ANTLR end "rule__RoleDecl__Group__1__Impl"


    // $ANTLR start "rule__RoleDecl__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3516:1: rule__RoleDecl__Group__2 : rule__RoleDecl__Group__2__Impl ;
    public final void rule__RoleDecl__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3520:1: ( rule__RoleDecl__Group__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3521:2: rule__RoleDecl__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group__2__Impl_in_rule__RoleDecl__Group__27056);
            rule__RoleDecl__Group__2__Impl();

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
    // $ANTLR end "rule__RoleDecl__Group__2"


    // $ANTLR start "rule__RoleDecl__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3527:1: rule__RoleDecl__Group__2__Impl : ( ( rule__RoleDecl__Group_2__0 )? ) ;
    public final void rule__RoleDecl__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3531:1: ( ( ( rule__RoleDecl__Group_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3532:1: ( ( rule__RoleDecl__Group_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3532:1: ( ( rule__RoleDecl__Group_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3533:1: ( rule__RoleDecl__Group_2__0 )?
            {
             before(grammarAccess.getRoleDeclAccess().getGroup_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3534:1: ( rule__RoleDecl__Group_2__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==15) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3534:2: rule__RoleDecl__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__RoleDecl__Group_2__0_in_rule__RoleDecl__Group__2__Impl7083);
                    rule__RoleDecl__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRoleDeclAccess().getGroup_2()); 

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
    // $ANTLR end "rule__RoleDecl__Group__2__Impl"


    // $ANTLR start "rule__RoleDecl__Group_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3550:1: rule__RoleDecl__Group_2__0 : rule__RoleDecl__Group_2__0__Impl rule__RoleDecl__Group_2__1 ;
    public final void rule__RoleDecl__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3554:1: ( rule__RoleDecl__Group_2__0__Impl rule__RoleDecl__Group_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3555:2: rule__RoleDecl__Group_2__0__Impl rule__RoleDecl__Group_2__1
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group_2__0__Impl_in_rule__RoleDecl__Group_2__07120);
            rule__RoleDecl__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RoleDecl__Group_2__1_in_rule__RoleDecl__Group_2__07123);
            rule__RoleDecl__Group_2__1();

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
    // $ANTLR end "rule__RoleDecl__Group_2__0"


    // $ANTLR start "rule__RoleDecl__Group_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3562:1: rule__RoleDecl__Group_2__0__Impl : ( 'as' ) ;
    public final void rule__RoleDecl__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3566:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3567:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3567:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3568:1: 'as'
            {
             before(grammarAccess.getRoleDeclAccess().getAsKeyword_2_0()); 
            match(input,15,FOLLOW_15_in_rule__RoleDecl__Group_2__0__Impl7151); 
             after(grammarAccess.getRoleDeclAccess().getAsKeyword_2_0()); 

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
    // $ANTLR end "rule__RoleDecl__Group_2__0__Impl"


    // $ANTLR start "rule__RoleDecl__Group_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3581:1: rule__RoleDecl__Group_2__1 : rule__RoleDecl__Group_2__1__Impl ;
    public final void rule__RoleDecl__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3585:1: ( rule__RoleDecl__Group_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3586:2: rule__RoleDecl__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__RoleDecl__Group_2__1__Impl_in_rule__RoleDecl__Group_2__17182);
            rule__RoleDecl__Group_2__1__Impl();

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
    // $ANTLR end "rule__RoleDecl__Group_2__1"


    // $ANTLR start "rule__RoleDecl__Group_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3592:1: rule__RoleDecl__Group_2__1__Impl : ( ( rule__RoleDecl__AliasAssignment_2_1 ) ) ;
    public final void rule__RoleDecl__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3596:1: ( ( ( rule__RoleDecl__AliasAssignment_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3597:1: ( ( rule__RoleDecl__AliasAssignment_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3597:1: ( ( rule__RoleDecl__AliasAssignment_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3598:1: ( rule__RoleDecl__AliasAssignment_2_1 )
            {
             before(grammarAccess.getRoleDeclAccess().getAliasAssignment_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3599:1: ( rule__RoleDecl__AliasAssignment_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3599:2: rule__RoleDecl__AliasAssignment_2_1
            {
            pushFollow(FOLLOW_rule__RoleDecl__AliasAssignment_2_1_in_rule__RoleDecl__Group_2__1__Impl7209);
            rule__RoleDecl__AliasAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getRoleDeclAccess().getAliasAssignment_2_1()); 

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
    // $ANTLR end "rule__RoleDecl__Group_2__1__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_0__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3613:1: rule__ParameterDecl__Group_0__0 : rule__ParameterDecl__Group_0__0__Impl rule__ParameterDecl__Group_0__1 ;
    public final void rule__ParameterDecl__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3617:1: ( rule__ParameterDecl__Group_0__0__Impl rule__ParameterDecl__Group_0__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3618:2: rule__ParameterDecl__Group_0__0__Impl rule__ParameterDecl__Group_0__1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_0__0__Impl_in_rule__ParameterDecl__Group_0__07243);
            rule__ParameterDecl__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_0__1_in_rule__ParameterDecl__Group_0__07246);
            rule__ParameterDecl__Group_0__1();

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
    // $ANTLR end "rule__ParameterDecl__Group_0__0"


    // $ANTLR start "rule__ParameterDecl__Group_0__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3625:1: rule__ParameterDecl__Group_0__0__Impl : ( 'type' ) ;
    public final void rule__ParameterDecl__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3629:1: ( ( 'type' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3630:1: ( 'type' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3630:1: ( 'type' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3631:1: 'type'
            {
             before(grammarAccess.getParameterDeclAccess().getTypeKeyword_0_0()); 
            match(input,17,FOLLOW_17_in_rule__ParameterDecl__Group_0__0__Impl7274); 
             after(grammarAccess.getParameterDeclAccess().getTypeKeyword_0_0()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_0__0__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_0__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3644:1: rule__ParameterDecl__Group_0__1 : rule__ParameterDecl__Group_0__1__Impl rule__ParameterDecl__Group_0__2 ;
    public final void rule__ParameterDecl__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3648:1: ( rule__ParameterDecl__Group_0__1__Impl rule__ParameterDecl__Group_0__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3649:2: rule__ParameterDecl__Group_0__1__Impl rule__ParameterDecl__Group_0__2
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_0__1__Impl_in_rule__ParameterDecl__Group_0__17305);
            rule__ParameterDecl__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_0__2_in_rule__ParameterDecl__Group_0__17308);
            rule__ParameterDecl__Group_0__2();

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
    // $ANTLR end "rule__ParameterDecl__Group_0__1"


    // $ANTLR start "rule__ParameterDecl__Group_0__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3656:1: rule__ParameterDecl__Group_0__1__Impl : ( ( rule__ParameterDecl__NameAssignment_0_1 ) ) ;
    public final void rule__ParameterDecl__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3660:1: ( ( ( rule__ParameterDecl__NameAssignment_0_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3661:1: ( ( rule__ParameterDecl__NameAssignment_0_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3661:1: ( ( rule__ParameterDecl__NameAssignment_0_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3662:1: ( rule__ParameterDecl__NameAssignment_0_1 )
            {
             before(grammarAccess.getParameterDeclAccess().getNameAssignment_0_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3663:1: ( rule__ParameterDecl__NameAssignment_0_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3663:2: rule__ParameterDecl__NameAssignment_0_1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__NameAssignment_0_1_in_rule__ParameterDecl__Group_0__1__Impl7335);
            rule__ParameterDecl__NameAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getParameterDeclAccess().getNameAssignment_0_1()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_0__1__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_0__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3673:1: rule__ParameterDecl__Group_0__2 : rule__ParameterDecl__Group_0__2__Impl ;
    public final void rule__ParameterDecl__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3677:1: ( rule__ParameterDecl__Group_0__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3678:2: rule__ParameterDecl__Group_0__2__Impl
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_0__2__Impl_in_rule__ParameterDecl__Group_0__27365);
            rule__ParameterDecl__Group_0__2__Impl();

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
    // $ANTLR end "rule__ParameterDecl__Group_0__2"


    // $ANTLR start "rule__ParameterDecl__Group_0__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3684:1: rule__ParameterDecl__Group_0__2__Impl : ( ( rule__ParameterDecl__Group_0_2__0 )? ) ;
    public final void rule__ParameterDecl__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3688:1: ( ( ( rule__ParameterDecl__Group_0_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3689:1: ( ( rule__ParameterDecl__Group_0_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3689:1: ( ( rule__ParameterDecl__Group_0_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3690:1: ( rule__ParameterDecl__Group_0_2__0 )?
            {
             before(grammarAccess.getParameterDeclAccess().getGroup_0_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3691:1: ( rule__ParameterDecl__Group_0_2__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==15) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3691:2: rule__ParameterDecl__Group_0_2__0
                    {
                    pushFollow(FOLLOW_rule__ParameterDecl__Group_0_2__0_in_rule__ParameterDecl__Group_0__2__Impl7392);
                    rule__ParameterDecl__Group_0_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getParameterDeclAccess().getGroup_0_2()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_0__2__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_0_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3707:1: rule__ParameterDecl__Group_0_2__0 : rule__ParameterDecl__Group_0_2__0__Impl rule__ParameterDecl__Group_0_2__1 ;
    public final void rule__ParameterDecl__Group_0_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3711:1: ( rule__ParameterDecl__Group_0_2__0__Impl rule__ParameterDecl__Group_0_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3712:2: rule__ParameterDecl__Group_0_2__0__Impl rule__ParameterDecl__Group_0_2__1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_0_2__0__Impl_in_rule__ParameterDecl__Group_0_2__07429);
            rule__ParameterDecl__Group_0_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_0_2__1_in_rule__ParameterDecl__Group_0_2__07432);
            rule__ParameterDecl__Group_0_2__1();

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
    // $ANTLR end "rule__ParameterDecl__Group_0_2__0"


    // $ANTLR start "rule__ParameterDecl__Group_0_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3719:1: rule__ParameterDecl__Group_0_2__0__Impl : ( 'as' ) ;
    public final void rule__ParameterDecl__Group_0_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3723:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3724:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3724:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3725:1: 'as'
            {
             before(grammarAccess.getParameterDeclAccess().getAsKeyword_0_2_0()); 
            match(input,15,FOLLOW_15_in_rule__ParameterDecl__Group_0_2__0__Impl7460); 
             after(grammarAccess.getParameterDeclAccess().getAsKeyword_0_2_0()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_0_2__0__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_0_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3738:1: rule__ParameterDecl__Group_0_2__1 : rule__ParameterDecl__Group_0_2__1__Impl ;
    public final void rule__ParameterDecl__Group_0_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3742:1: ( rule__ParameterDecl__Group_0_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3743:2: rule__ParameterDecl__Group_0_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_0_2__1__Impl_in_rule__ParameterDecl__Group_0_2__17491);
            rule__ParameterDecl__Group_0_2__1__Impl();

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
    // $ANTLR end "rule__ParameterDecl__Group_0_2__1"


    // $ANTLR start "rule__ParameterDecl__Group_0_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3749:1: rule__ParameterDecl__Group_0_2__1__Impl : ( ( rule__ParameterDecl__AliasAssignment_0_2_1 ) ) ;
    public final void rule__ParameterDecl__Group_0_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3753:1: ( ( ( rule__ParameterDecl__AliasAssignment_0_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3754:1: ( ( rule__ParameterDecl__AliasAssignment_0_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3754:1: ( ( rule__ParameterDecl__AliasAssignment_0_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3755:1: ( rule__ParameterDecl__AliasAssignment_0_2_1 )
            {
             before(grammarAccess.getParameterDeclAccess().getAliasAssignment_0_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3756:1: ( rule__ParameterDecl__AliasAssignment_0_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3756:2: rule__ParameterDecl__AliasAssignment_0_2_1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__AliasAssignment_0_2_1_in_rule__ParameterDecl__Group_0_2__1__Impl7518);
            rule__ParameterDecl__AliasAssignment_0_2_1();

            state._fsp--;


            }

             after(grammarAccess.getParameterDeclAccess().getAliasAssignment_0_2_1()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_0_2__1__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3770:1: rule__ParameterDecl__Group_1__0 : rule__ParameterDecl__Group_1__0__Impl rule__ParameterDecl__Group_1__1 ;
    public final void rule__ParameterDecl__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3774:1: ( rule__ParameterDecl__Group_1__0__Impl rule__ParameterDecl__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3775:2: rule__ParameterDecl__Group_1__0__Impl rule__ParameterDecl__Group_1__1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_1__0__Impl_in_rule__ParameterDecl__Group_1__07552);
            rule__ParameterDecl__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_1__1_in_rule__ParameterDecl__Group_1__07555);
            rule__ParameterDecl__Group_1__1();

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
    // $ANTLR end "rule__ParameterDecl__Group_1__0"


    // $ANTLR start "rule__ParameterDecl__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3782:1: rule__ParameterDecl__Group_1__0__Impl : ( 'sig' ) ;
    public final void rule__ParameterDecl__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3786:1: ( ( 'sig' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3787:1: ( 'sig' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3787:1: ( 'sig' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3788:1: 'sig'
            {
             before(grammarAccess.getParameterDeclAccess().getSigKeyword_1_0()); 
            match(input,28,FOLLOW_28_in_rule__ParameterDecl__Group_1__0__Impl7583); 
             after(grammarAccess.getParameterDeclAccess().getSigKeyword_1_0()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_1__0__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3801:1: rule__ParameterDecl__Group_1__1 : rule__ParameterDecl__Group_1__1__Impl rule__ParameterDecl__Group_1__2 ;
    public final void rule__ParameterDecl__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3805:1: ( rule__ParameterDecl__Group_1__1__Impl rule__ParameterDecl__Group_1__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3806:2: rule__ParameterDecl__Group_1__1__Impl rule__ParameterDecl__Group_1__2
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_1__1__Impl_in_rule__ParameterDecl__Group_1__17614);
            rule__ParameterDecl__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_1__2_in_rule__ParameterDecl__Group_1__17617);
            rule__ParameterDecl__Group_1__2();

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
    // $ANTLR end "rule__ParameterDecl__Group_1__1"


    // $ANTLR start "rule__ParameterDecl__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3813:1: rule__ParameterDecl__Group_1__1__Impl : ( ( rule__ParameterDecl__NameAssignment_1_1 ) ) ;
    public final void rule__ParameterDecl__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3817:1: ( ( ( rule__ParameterDecl__NameAssignment_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3818:1: ( ( rule__ParameterDecl__NameAssignment_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3818:1: ( ( rule__ParameterDecl__NameAssignment_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3819:1: ( rule__ParameterDecl__NameAssignment_1_1 )
            {
             before(grammarAccess.getParameterDeclAccess().getNameAssignment_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3820:1: ( rule__ParameterDecl__NameAssignment_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3820:2: rule__ParameterDecl__NameAssignment_1_1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__NameAssignment_1_1_in_rule__ParameterDecl__Group_1__1__Impl7644);
            rule__ParameterDecl__NameAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getParameterDeclAccess().getNameAssignment_1_1()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_1__1__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_1__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3830:1: rule__ParameterDecl__Group_1__2 : rule__ParameterDecl__Group_1__2__Impl ;
    public final void rule__ParameterDecl__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3834:1: ( rule__ParameterDecl__Group_1__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3835:2: rule__ParameterDecl__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_1__2__Impl_in_rule__ParameterDecl__Group_1__27674);
            rule__ParameterDecl__Group_1__2__Impl();

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
    // $ANTLR end "rule__ParameterDecl__Group_1__2"


    // $ANTLR start "rule__ParameterDecl__Group_1__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3841:1: rule__ParameterDecl__Group_1__2__Impl : ( ( rule__ParameterDecl__Group_1_2__0 )? ) ;
    public final void rule__ParameterDecl__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3845:1: ( ( ( rule__ParameterDecl__Group_1_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3846:1: ( ( rule__ParameterDecl__Group_1_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3846:1: ( ( rule__ParameterDecl__Group_1_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3847:1: ( rule__ParameterDecl__Group_1_2__0 )?
            {
             before(grammarAccess.getParameterDeclAccess().getGroup_1_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3848:1: ( rule__ParameterDecl__Group_1_2__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==15) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3848:2: rule__ParameterDecl__Group_1_2__0
                    {
                    pushFollow(FOLLOW_rule__ParameterDecl__Group_1_2__0_in_rule__ParameterDecl__Group_1__2__Impl7701);
                    rule__ParameterDecl__Group_1_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getParameterDeclAccess().getGroup_1_2()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_1__2__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_1_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3864:1: rule__ParameterDecl__Group_1_2__0 : rule__ParameterDecl__Group_1_2__0__Impl rule__ParameterDecl__Group_1_2__1 ;
    public final void rule__ParameterDecl__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3868:1: ( rule__ParameterDecl__Group_1_2__0__Impl rule__ParameterDecl__Group_1_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3869:2: rule__ParameterDecl__Group_1_2__0__Impl rule__ParameterDecl__Group_1_2__1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_1_2__0__Impl_in_rule__ParameterDecl__Group_1_2__07738);
            rule__ParameterDecl__Group_1_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__ParameterDecl__Group_1_2__1_in_rule__ParameterDecl__Group_1_2__07741);
            rule__ParameterDecl__Group_1_2__1();

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
    // $ANTLR end "rule__ParameterDecl__Group_1_2__0"


    // $ANTLR start "rule__ParameterDecl__Group_1_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3876:1: rule__ParameterDecl__Group_1_2__0__Impl : ( 'as' ) ;
    public final void rule__ParameterDecl__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3880:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3881:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3881:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3882:1: 'as'
            {
             before(grammarAccess.getParameterDeclAccess().getAsKeyword_1_2_0()); 
            match(input,15,FOLLOW_15_in_rule__ParameterDecl__Group_1_2__0__Impl7769); 
             after(grammarAccess.getParameterDeclAccess().getAsKeyword_1_2_0()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_1_2__0__Impl"


    // $ANTLR start "rule__ParameterDecl__Group_1_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3895:1: rule__ParameterDecl__Group_1_2__1 : rule__ParameterDecl__Group_1_2__1__Impl ;
    public final void rule__ParameterDecl__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3899:1: ( rule__ParameterDecl__Group_1_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3900:2: rule__ParameterDecl__Group_1_2__1__Impl
            {
            pushFollow(FOLLOW_rule__ParameterDecl__Group_1_2__1__Impl_in_rule__ParameterDecl__Group_1_2__17800);
            rule__ParameterDecl__Group_1_2__1__Impl();

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
    // $ANTLR end "rule__ParameterDecl__Group_1_2__1"


    // $ANTLR start "rule__ParameterDecl__Group_1_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3906:1: rule__ParameterDecl__Group_1_2__1__Impl : ( ( rule__ParameterDecl__AliasAssignment_1_2_1 ) ) ;
    public final void rule__ParameterDecl__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3910:1: ( ( ( rule__ParameterDecl__AliasAssignment_1_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3911:1: ( ( rule__ParameterDecl__AliasAssignment_1_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3911:1: ( ( rule__ParameterDecl__AliasAssignment_1_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3912:1: ( rule__ParameterDecl__AliasAssignment_1_2_1 )
            {
             before(grammarAccess.getParameterDeclAccess().getAliasAssignment_1_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3913:1: ( rule__ParameterDecl__AliasAssignment_1_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3913:2: rule__ParameterDecl__AliasAssignment_1_2_1
            {
            pushFollow(FOLLOW_rule__ParameterDecl__AliasAssignment_1_2_1_in_rule__ParameterDecl__Group_1_2__1__Impl7827);
            rule__ParameterDecl__AliasAssignment_1_2_1();

            state._fsp--;


            }

             after(grammarAccess.getParameterDeclAccess().getAliasAssignment_1_2_1()); 

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
    // $ANTLR end "rule__ParameterDecl__Group_1_2__1__Impl"


    // $ANTLR start "rule__RoleInstantiation__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3927:1: rule__RoleInstantiation__Group__0 : rule__RoleInstantiation__Group__0__Impl rule__RoleInstantiation__Group__1 ;
    public final void rule__RoleInstantiation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3931:1: ( rule__RoleInstantiation__Group__0__Impl rule__RoleInstantiation__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3932:2: rule__RoleInstantiation__Group__0__Impl rule__RoleInstantiation__Group__1
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__Group__0__Impl_in_rule__RoleInstantiation__Group__07861);
            rule__RoleInstantiation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RoleInstantiation__Group__1_in_rule__RoleInstantiation__Group__07864);
            rule__RoleInstantiation__Group__1();

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
    // $ANTLR end "rule__RoleInstantiation__Group__0"


    // $ANTLR start "rule__RoleInstantiation__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3939:1: rule__RoleInstantiation__Group__0__Impl : ( ( rule__RoleInstantiation__NameAssignment_0 ) ) ;
    public final void rule__RoleInstantiation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3943:1: ( ( ( rule__RoleInstantiation__NameAssignment_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3944:1: ( ( rule__RoleInstantiation__NameAssignment_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3944:1: ( ( rule__RoleInstantiation__NameAssignment_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3945:1: ( rule__RoleInstantiation__NameAssignment_0 )
            {
             before(grammarAccess.getRoleInstantiationAccess().getNameAssignment_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3946:1: ( rule__RoleInstantiation__NameAssignment_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3946:2: rule__RoleInstantiation__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__NameAssignment_0_in_rule__RoleInstantiation__Group__0__Impl7891);
            rule__RoleInstantiation__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getRoleInstantiationAccess().getNameAssignment_0()); 

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
    // $ANTLR end "rule__RoleInstantiation__Group__0__Impl"


    // $ANTLR start "rule__RoleInstantiation__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3956:1: rule__RoleInstantiation__Group__1 : rule__RoleInstantiation__Group__1__Impl ;
    public final void rule__RoleInstantiation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3960:1: ( rule__RoleInstantiation__Group__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3961:2: rule__RoleInstantiation__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__Group__1__Impl_in_rule__RoleInstantiation__Group__17921);
            rule__RoleInstantiation__Group__1__Impl();

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
    // $ANTLR end "rule__RoleInstantiation__Group__1"


    // $ANTLR start "rule__RoleInstantiation__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3967:1: rule__RoleInstantiation__Group__1__Impl : ( ( rule__RoleInstantiation__Group_1__0 )? ) ;
    public final void rule__RoleInstantiation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3971:1: ( ( ( rule__RoleInstantiation__Group_1__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3972:1: ( ( rule__RoleInstantiation__Group_1__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3972:1: ( ( rule__RoleInstantiation__Group_1__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3973:1: ( rule__RoleInstantiation__Group_1__0 )?
            {
             before(grammarAccess.getRoleInstantiationAccess().getGroup_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3974:1: ( rule__RoleInstantiation__Group_1__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==15) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3974:2: rule__RoleInstantiation__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__RoleInstantiation__Group_1__0_in_rule__RoleInstantiation__Group__1__Impl7948);
                    rule__RoleInstantiation__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRoleInstantiationAccess().getGroup_1()); 

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
    // $ANTLR end "rule__RoleInstantiation__Group__1__Impl"


    // $ANTLR start "rule__RoleInstantiation__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3988:1: rule__RoleInstantiation__Group_1__0 : rule__RoleInstantiation__Group_1__0__Impl rule__RoleInstantiation__Group_1__1 ;
    public final void rule__RoleInstantiation__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3992:1: ( rule__RoleInstantiation__Group_1__0__Impl rule__RoleInstantiation__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:3993:2: rule__RoleInstantiation__Group_1__0__Impl rule__RoleInstantiation__Group_1__1
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__Group_1__0__Impl_in_rule__RoleInstantiation__Group_1__07983);
            rule__RoleInstantiation__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__RoleInstantiation__Group_1__1_in_rule__RoleInstantiation__Group_1__07986);
            rule__RoleInstantiation__Group_1__1();

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
    // $ANTLR end "rule__RoleInstantiation__Group_1__0"


    // $ANTLR start "rule__RoleInstantiation__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4000:1: rule__RoleInstantiation__Group_1__0__Impl : ( 'as' ) ;
    public final void rule__RoleInstantiation__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4004:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4005:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4005:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4006:1: 'as'
            {
             before(grammarAccess.getRoleInstantiationAccess().getAsKeyword_1_0()); 
            match(input,15,FOLLOW_15_in_rule__RoleInstantiation__Group_1__0__Impl8014); 
             after(grammarAccess.getRoleInstantiationAccess().getAsKeyword_1_0()); 

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
    // $ANTLR end "rule__RoleInstantiation__Group_1__0__Impl"


    // $ANTLR start "rule__RoleInstantiation__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4019:1: rule__RoleInstantiation__Group_1__1 : rule__RoleInstantiation__Group_1__1__Impl ;
    public final void rule__RoleInstantiation__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4023:1: ( rule__RoleInstantiation__Group_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4024:2: rule__RoleInstantiation__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__Group_1__1__Impl_in_rule__RoleInstantiation__Group_1__18045);
            rule__RoleInstantiation__Group_1__1__Impl();

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
    // $ANTLR end "rule__RoleInstantiation__Group_1__1"


    // $ANTLR start "rule__RoleInstantiation__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4030:1: rule__RoleInstantiation__Group_1__1__Impl : ( ( rule__RoleInstantiation__AliasAssignment_1_1 ) ) ;
    public final void rule__RoleInstantiation__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4034:1: ( ( ( rule__RoleInstantiation__AliasAssignment_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4035:1: ( ( rule__RoleInstantiation__AliasAssignment_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4035:1: ( ( rule__RoleInstantiation__AliasAssignment_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4036:1: ( rule__RoleInstantiation__AliasAssignment_1_1 )
            {
             before(grammarAccess.getRoleInstantiationAccess().getAliasAssignment_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4037:1: ( rule__RoleInstantiation__AliasAssignment_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4037:2: rule__RoleInstantiation__AliasAssignment_1_1
            {
            pushFollow(FOLLOW_rule__RoleInstantiation__AliasAssignment_1_1_in_rule__RoleInstantiation__Group_1__1__Impl8072);
            rule__RoleInstantiation__AliasAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getRoleInstantiationAccess().getAliasAssignment_1_1()); 

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
    // $ANTLR end "rule__RoleInstantiation__Group_1__1__Impl"


    // $ANTLR start "rule__Argument__Group_0__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4051:1: rule__Argument__Group_0__0 : rule__Argument__Group_0__0__Impl rule__Argument__Group_0__1 ;
    public final void rule__Argument__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4055:1: ( rule__Argument__Group_0__0__Impl rule__Argument__Group_0__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4056:2: rule__Argument__Group_0__0__Impl rule__Argument__Group_0__1
            {
            pushFollow(FOLLOW_rule__Argument__Group_0__0__Impl_in_rule__Argument__Group_0__08106);
            rule__Argument__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Argument__Group_0__1_in_rule__Argument__Group_0__08109);
            rule__Argument__Group_0__1();

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
    // $ANTLR end "rule__Argument__Group_0__0"


    // $ANTLR start "rule__Argument__Group_0__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4063:1: rule__Argument__Group_0__0__Impl : ( ( rule__Argument__SignatureAssignment_0_0 ) ) ;
    public final void rule__Argument__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4067:1: ( ( ( rule__Argument__SignatureAssignment_0_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4068:1: ( ( rule__Argument__SignatureAssignment_0_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4068:1: ( ( rule__Argument__SignatureAssignment_0_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4069:1: ( rule__Argument__SignatureAssignment_0_0 )
            {
             before(grammarAccess.getArgumentAccess().getSignatureAssignment_0_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4070:1: ( rule__Argument__SignatureAssignment_0_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4070:2: rule__Argument__SignatureAssignment_0_0
            {
            pushFollow(FOLLOW_rule__Argument__SignatureAssignment_0_0_in_rule__Argument__Group_0__0__Impl8136);
            rule__Argument__SignatureAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getArgumentAccess().getSignatureAssignment_0_0()); 

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
    // $ANTLR end "rule__Argument__Group_0__0__Impl"


    // $ANTLR start "rule__Argument__Group_0__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4080:1: rule__Argument__Group_0__1 : rule__Argument__Group_0__1__Impl ;
    public final void rule__Argument__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4084:1: ( rule__Argument__Group_0__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4085:2: rule__Argument__Group_0__1__Impl
            {
            pushFollow(FOLLOW_rule__Argument__Group_0__1__Impl_in_rule__Argument__Group_0__18166);
            rule__Argument__Group_0__1__Impl();

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
    // $ANTLR end "rule__Argument__Group_0__1"


    // $ANTLR start "rule__Argument__Group_0__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4091:1: rule__Argument__Group_0__1__Impl : ( ( rule__Argument__Group_0_1__0 )? ) ;
    public final void rule__Argument__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4095:1: ( ( ( rule__Argument__Group_0_1__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4096:1: ( ( rule__Argument__Group_0_1__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4096:1: ( ( rule__Argument__Group_0_1__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4097:1: ( rule__Argument__Group_0_1__0 )?
            {
             before(grammarAccess.getArgumentAccess().getGroup_0_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4098:1: ( rule__Argument__Group_0_1__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==15) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4098:2: rule__Argument__Group_0_1__0
                    {
                    pushFollow(FOLLOW_rule__Argument__Group_0_1__0_in_rule__Argument__Group_0__1__Impl8193);
                    rule__Argument__Group_0_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getArgumentAccess().getGroup_0_1()); 

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
    // $ANTLR end "rule__Argument__Group_0__1__Impl"


    // $ANTLR start "rule__Argument__Group_0_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4112:1: rule__Argument__Group_0_1__0 : rule__Argument__Group_0_1__0__Impl rule__Argument__Group_0_1__1 ;
    public final void rule__Argument__Group_0_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4116:1: ( rule__Argument__Group_0_1__0__Impl rule__Argument__Group_0_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4117:2: rule__Argument__Group_0_1__0__Impl rule__Argument__Group_0_1__1
            {
            pushFollow(FOLLOW_rule__Argument__Group_0_1__0__Impl_in_rule__Argument__Group_0_1__08228);
            rule__Argument__Group_0_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Argument__Group_0_1__1_in_rule__Argument__Group_0_1__08231);
            rule__Argument__Group_0_1__1();

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
    // $ANTLR end "rule__Argument__Group_0_1__0"


    // $ANTLR start "rule__Argument__Group_0_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4124:1: rule__Argument__Group_0_1__0__Impl : ( 'as' ) ;
    public final void rule__Argument__Group_0_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4128:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4129:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4129:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4130:1: 'as'
            {
             before(grammarAccess.getArgumentAccess().getAsKeyword_0_1_0()); 
            match(input,15,FOLLOW_15_in_rule__Argument__Group_0_1__0__Impl8259); 
             after(grammarAccess.getArgumentAccess().getAsKeyword_0_1_0()); 

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
    // $ANTLR end "rule__Argument__Group_0_1__0__Impl"


    // $ANTLR start "rule__Argument__Group_0_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4143:1: rule__Argument__Group_0_1__1 : rule__Argument__Group_0_1__1__Impl ;
    public final void rule__Argument__Group_0_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4147:1: ( rule__Argument__Group_0_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4148:2: rule__Argument__Group_0_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Argument__Group_0_1__1__Impl_in_rule__Argument__Group_0_1__18290);
            rule__Argument__Group_0_1__1__Impl();

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
    // $ANTLR end "rule__Argument__Group_0_1__1"


    // $ANTLR start "rule__Argument__Group_0_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4154:1: rule__Argument__Group_0_1__1__Impl : ( ( rule__Argument__AliasAssignment_0_1_1 ) ) ;
    public final void rule__Argument__Group_0_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4158:1: ( ( ( rule__Argument__AliasAssignment_0_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4159:1: ( ( rule__Argument__AliasAssignment_0_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4159:1: ( ( rule__Argument__AliasAssignment_0_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4160:1: ( rule__Argument__AliasAssignment_0_1_1 )
            {
             before(grammarAccess.getArgumentAccess().getAliasAssignment_0_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4161:1: ( rule__Argument__AliasAssignment_0_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4161:2: rule__Argument__AliasAssignment_0_1_1
            {
            pushFollow(FOLLOW_rule__Argument__AliasAssignment_0_1_1_in_rule__Argument__Group_0_1__1__Impl8317);
            rule__Argument__AliasAssignment_0_1_1();

            state._fsp--;


            }

             after(grammarAccess.getArgumentAccess().getAliasAssignment_0_1_1()); 

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
    // $ANTLR end "rule__Argument__Group_0_1__1__Impl"


    // $ANTLR start "rule__Argument__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4175:1: rule__Argument__Group_1__0 : rule__Argument__Group_1__0__Impl rule__Argument__Group_1__1 ;
    public final void rule__Argument__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4179:1: ( rule__Argument__Group_1__0__Impl rule__Argument__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4180:2: rule__Argument__Group_1__0__Impl rule__Argument__Group_1__1
            {
            pushFollow(FOLLOW_rule__Argument__Group_1__0__Impl_in_rule__Argument__Group_1__08351);
            rule__Argument__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Argument__Group_1__1_in_rule__Argument__Group_1__08354);
            rule__Argument__Group_1__1();

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
    // $ANTLR end "rule__Argument__Group_1__0"


    // $ANTLR start "rule__Argument__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4187:1: rule__Argument__Group_1__0__Impl : ( ( rule__Argument__NameAssignment_1_0 ) ) ;
    public final void rule__Argument__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4191:1: ( ( ( rule__Argument__NameAssignment_1_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4192:1: ( ( rule__Argument__NameAssignment_1_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4192:1: ( ( rule__Argument__NameAssignment_1_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4193:1: ( rule__Argument__NameAssignment_1_0 )
            {
             before(grammarAccess.getArgumentAccess().getNameAssignment_1_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4194:1: ( rule__Argument__NameAssignment_1_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4194:2: rule__Argument__NameAssignment_1_0
            {
            pushFollow(FOLLOW_rule__Argument__NameAssignment_1_0_in_rule__Argument__Group_1__0__Impl8381);
            rule__Argument__NameAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getArgumentAccess().getNameAssignment_1_0()); 

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
    // $ANTLR end "rule__Argument__Group_1__0__Impl"


    // $ANTLR start "rule__Argument__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4204:1: rule__Argument__Group_1__1 : rule__Argument__Group_1__1__Impl ;
    public final void rule__Argument__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4208:1: ( rule__Argument__Group_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4209:2: rule__Argument__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Argument__Group_1__1__Impl_in_rule__Argument__Group_1__18411);
            rule__Argument__Group_1__1__Impl();

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
    // $ANTLR end "rule__Argument__Group_1__1"


    // $ANTLR start "rule__Argument__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4215:1: rule__Argument__Group_1__1__Impl : ( ( rule__Argument__Group_1_1__0 )? ) ;
    public final void rule__Argument__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4219:1: ( ( ( rule__Argument__Group_1_1__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4220:1: ( ( rule__Argument__Group_1_1__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4220:1: ( ( rule__Argument__Group_1_1__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4221:1: ( rule__Argument__Group_1_1__0 )?
            {
             before(grammarAccess.getArgumentAccess().getGroup_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4222:1: ( rule__Argument__Group_1_1__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==15) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4222:2: rule__Argument__Group_1_1__0
                    {
                    pushFollow(FOLLOW_rule__Argument__Group_1_1__0_in_rule__Argument__Group_1__1__Impl8438);
                    rule__Argument__Group_1_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getArgumentAccess().getGroup_1_1()); 

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
    // $ANTLR end "rule__Argument__Group_1__1__Impl"


    // $ANTLR start "rule__Argument__Group_1_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4236:1: rule__Argument__Group_1_1__0 : rule__Argument__Group_1_1__0__Impl rule__Argument__Group_1_1__1 ;
    public final void rule__Argument__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4240:1: ( rule__Argument__Group_1_1__0__Impl rule__Argument__Group_1_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4241:2: rule__Argument__Group_1_1__0__Impl rule__Argument__Group_1_1__1
            {
            pushFollow(FOLLOW_rule__Argument__Group_1_1__0__Impl_in_rule__Argument__Group_1_1__08473);
            rule__Argument__Group_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Argument__Group_1_1__1_in_rule__Argument__Group_1_1__08476);
            rule__Argument__Group_1_1__1();

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
    // $ANTLR end "rule__Argument__Group_1_1__0"


    // $ANTLR start "rule__Argument__Group_1_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4248:1: rule__Argument__Group_1_1__0__Impl : ( 'as' ) ;
    public final void rule__Argument__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4252:1: ( ( 'as' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4253:1: ( 'as' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4253:1: ( 'as' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4254:1: 'as'
            {
             before(grammarAccess.getArgumentAccess().getAsKeyword_1_1_0()); 
            match(input,15,FOLLOW_15_in_rule__Argument__Group_1_1__0__Impl8504); 
             after(grammarAccess.getArgumentAccess().getAsKeyword_1_1_0()); 

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
    // $ANTLR end "rule__Argument__Group_1_1__0__Impl"


    // $ANTLR start "rule__Argument__Group_1_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4267:1: rule__Argument__Group_1_1__1 : rule__Argument__Group_1_1__1__Impl ;
    public final void rule__Argument__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4271:1: ( rule__Argument__Group_1_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4272:2: rule__Argument__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_rule__Argument__Group_1_1__1__Impl_in_rule__Argument__Group_1_1__18535);
            rule__Argument__Group_1_1__1__Impl();

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
    // $ANTLR end "rule__Argument__Group_1_1__1"


    // $ANTLR start "rule__Argument__Group_1_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4278:1: rule__Argument__Group_1_1__1__Impl : ( ( rule__Argument__AliasAssignment_1_1_1 ) ) ;
    public final void rule__Argument__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4282:1: ( ( ( rule__Argument__AliasAssignment_1_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4283:1: ( ( rule__Argument__AliasAssignment_1_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4283:1: ( ( rule__Argument__AliasAssignment_1_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4284:1: ( rule__Argument__AliasAssignment_1_1_1 )
            {
             before(grammarAccess.getArgumentAccess().getAliasAssignment_1_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4285:1: ( rule__Argument__AliasAssignment_1_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4285:2: rule__Argument__AliasAssignment_1_1_1
            {
            pushFollow(FOLLOW_rule__Argument__AliasAssignment_1_1_1_in_rule__Argument__Group_1_1__1__Impl8562);
            rule__Argument__AliasAssignment_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getArgumentAccess().getAliasAssignment_1_1_1()); 

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
    // $ANTLR end "rule__Argument__Group_1_1__1__Impl"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4299:1: rule__GlobalProtocolBlock__Group__0 : rule__GlobalProtocolBlock__Group__0__Impl rule__GlobalProtocolBlock__Group__1 ;
    public final void rule__GlobalProtocolBlock__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4303:1: ( rule__GlobalProtocolBlock__Group__0__Impl rule__GlobalProtocolBlock__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4304:2: rule__GlobalProtocolBlock__Group__0__Impl rule__GlobalProtocolBlock__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__0__Impl_in_rule__GlobalProtocolBlock__Group__08596);
            rule__GlobalProtocolBlock__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__1_in_rule__GlobalProtocolBlock__Group__08599);
            rule__GlobalProtocolBlock__Group__1();

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__0"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4311:1: rule__GlobalProtocolBlock__Group__0__Impl : ( () ) ;
    public final void rule__GlobalProtocolBlock__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4315:1: ( ( () ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4316:1: ( () )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4316:1: ( () )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4317:1: ()
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getGlobalProtocolBlockAction_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4318:1: ()
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4320:1: 
            {
            }

             after(grammarAccess.getGlobalProtocolBlockAccess().getGlobalProtocolBlockAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GlobalProtocolBlock__Group__0__Impl"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4330:1: rule__GlobalProtocolBlock__Group__1 : rule__GlobalProtocolBlock__Group__1__Impl rule__GlobalProtocolBlock__Group__2 ;
    public final void rule__GlobalProtocolBlock__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4334:1: ( rule__GlobalProtocolBlock__Group__1__Impl rule__GlobalProtocolBlock__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4335:2: rule__GlobalProtocolBlock__Group__1__Impl rule__GlobalProtocolBlock__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__1__Impl_in_rule__GlobalProtocolBlock__Group__18657);
            rule__GlobalProtocolBlock__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__2_in_rule__GlobalProtocolBlock__Group__18660);
            rule__GlobalProtocolBlock__Group__2();

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__1"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4342:1: rule__GlobalProtocolBlock__Group__1__Impl : ( '{' ) ;
    public final void rule__GlobalProtocolBlock__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4346:1: ( ( '{' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4347:1: ( '{' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4347:1: ( '{' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4348:1: '{'
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,29,FOLLOW_29_in_rule__GlobalProtocolBlock__Group__1__Impl8688); 
             after(grammarAccess.getGlobalProtocolBlockAccess().getLeftCurlyBracketKeyword_1()); 

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__1__Impl"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4361:1: rule__GlobalProtocolBlock__Group__2 : rule__GlobalProtocolBlock__Group__2__Impl rule__GlobalProtocolBlock__Group__3 ;
    public final void rule__GlobalProtocolBlock__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4365:1: ( rule__GlobalProtocolBlock__Group__2__Impl rule__GlobalProtocolBlock__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4366:2: rule__GlobalProtocolBlock__Group__2__Impl rule__GlobalProtocolBlock__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__2__Impl_in_rule__GlobalProtocolBlock__Group__28719);
            rule__GlobalProtocolBlock__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__3_in_rule__GlobalProtocolBlock__Group__28722);
            rule__GlobalProtocolBlock__Group__3();

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__2"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4373:1: rule__GlobalProtocolBlock__Group__2__Impl : ( ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )* ) ;
    public final void rule__GlobalProtocolBlock__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4377:1: ( ( ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4378:1: ( ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4378:1: ( ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4379:1: ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )*
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4380:1: ( rule__GlobalProtocolBlock__ActivitiesAssignment_2 )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_ID||LA28_0==32||(LA28_0>=35 && LA28_0<=37)||LA28_0==39||LA28_0==42) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4380:2: rule__GlobalProtocolBlock__ActivitiesAssignment_2
            	    {
            	    pushFollow(FOLLOW_rule__GlobalProtocolBlock__ActivitiesAssignment_2_in_rule__GlobalProtocolBlock__Group__2__Impl8749);
            	    rule__GlobalProtocolBlock__ActivitiesAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

             after(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesAssignment_2()); 

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__2__Impl"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4390:1: rule__GlobalProtocolBlock__Group__3 : rule__GlobalProtocolBlock__Group__3__Impl ;
    public final void rule__GlobalProtocolBlock__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4394:1: ( rule__GlobalProtocolBlock__Group__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4395:2: rule__GlobalProtocolBlock__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__GlobalProtocolBlock__Group__3__Impl_in_rule__GlobalProtocolBlock__Group__38780);
            rule__GlobalProtocolBlock__Group__3__Impl();

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__3"


    // $ANTLR start "rule__GlobalProtocolBlock__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4401:1: rule__GlobalProtocolBlock__Group__3__Impl : ( '}' ) ;
    public final void rule__GlobalProtocolBlock__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4405:1: ( ( '}' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4406:1: ( '}' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4406:1: ( '}' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4407:1: '}'
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getRightCurlyBracketKeyword_3()); 
            match(input,30,FOLLOW_30_in_rule__GlobalProtocolBlock__Group__3__Impl8808); 
             after(grammarAccess.getGlobalProtocolBlockAccess().getRightCurlyBracketKeyword_3()); 

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
    // $ANTLR end "rule__GlobalProtocolBlock__Group__3__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4428:1: rule__GlobalMessageTransfer__Group__0 : rule__GlobalMessageTransfer__Group__0__Impl rule__GlobalMessageTransfer__Group__1 ;
    public final void rule__GlobalMessageTransfer__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4432:1: ( rule__GlobalMessageTransfer__Group__0__Impl rule__GlobalMessageTransfer__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4433:2: rule__GlobalMessageTransfer__Group__0__Impl rule__GlobalMessageTransfer__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__0__Impl_in_rule__GlobalMessageTransfer__Group__08847);
            rule__GlobalMessageTransfer__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__1_in_rule__GlobalMessageTransfer__Group__08850);
            rule__GlobalMessageTransfer__Group__1();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__0"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4440:1: rule__GlobalMessageTransfer__Group__0__Impl : ( ( rule__GlobalMessageTransfer__MessageAssignment_0 ) ) ;
    public final void rule__GlobalMessageTransfer__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4444:1: ( ( ( rule__GlobalMessageTransfer__MessageAssignment_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4445:1: ( ( rule__GlobalMessageTransfer__MessageAssignment_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4445:1: ( ( rule__GlobalMessageTransfer__MessageAssignment_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4446:1: ( rule__GlobalMessageTransfer__MessageAssignment_0 )
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getMessageAssignment_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4447:1: ( rule__GlobalMessageTransfer__MessageAssignment_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4447:2: rule__GlobalMessageTransfer__MessageAssignment_0
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__MessageAssignment_0_in_rule__GlobalMessageTransfer__Group__0__Impl8877);
            rule__GlobalMessageTransfer__MessageAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalMessageTransferAccess().getMessageAssignment_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__0__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4457:1: rule__GlobalMessageTransfer__Group__1 : rule__GlobalMessageTransfer__Group__1__Impl rule__GlobalMessageTransfer__Group__2 ;
    public final void rule__GlobalMessageTransfer__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4461:1: ( rule__GlobalMessageTransfer__Group__1__Impl rule__GlobalMessageTransfer__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4462:2: rule__GlobalMessageTransfer__Group__1__Impl rule__GlobalMessageTransfer__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__1__Impl_in_rule__GlobalMessageTransfer__Group__18907);
            rule__GlobalMessageTransfer__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__2_in_rule__GlobalMessageTransfer__Group__18910);
            rule__GlobalMessageTransfer__Group__2();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__1"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4469:1: rule__GlobalMessageTransfer__Group__1__Impl : ( 'from' ) ;
    public final void rule__GlobalMessageTransfer__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4473:1: ( ( 'from' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4474:1: ( 'from' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4474:1: ( 'from' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4475:1: 'from'
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getFromKeyword_1()); 
            match(input,16,FOLLOW_16_in_rule__GlobalMessageTransfer__Group__1__Impl8938); 
             after(grammarAccess.getGlobalMessageTransferAccess().getFromKeyword_1()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__1__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4488:1: rule__GlobalMessageTransfer__Group__2 : rule__GlobalMessageTransfer__Group__2__Impl rule__GlobalMessageTransfer__Group__3 ;
    public final void rule__GlobalMessageTransfer__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4492:1: ( rule__GlobalMessageTransfer__Group__2__Impl rule__GlobalMessageTransfer__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4493:2: rule__GlobalMessageTransfer__Group__2__Impl rule__GlobalMessageTransfer__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__2__Impl_in_rule__GlobalMessageTransfer__Group__28969);
            rule__GlobalMessageTransfer__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__3_in_rule__GlobalMessageTransfer__Group__28972);
            rule__GlobalMessageTransfer__Group__3();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__2"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4500:1: rule__GlobalMessageTransfer__Group__2__Impl : ( ( rule__GlobalMessageTransfer__FromRoleAssignment_2 ) ) ;
    public final void rule__GlobalMessageTransfer__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4504:1: ( ( ( rule__GlobalMessageTransfer__FromRoleAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4505:1: ( ( rule__GlobalMessageTransfer__FromRoleAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4505:1: ( ( rule__GlobalMessageTransfer__FromRoleAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4506:1: ( rule__GlobalMessageTransfer__FromRoleAssignment_2 )
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getFromRoleAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4507:1: ( rule__GlobalMessageTransfer__FromRoleAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4507:2: rule__GlobalMessageTransfer__FromRoleAssignment_2
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__FromRoleAssignment_2_in_rule__GlobalMessageTransfer__Group__2__Impl8999);
            rule__GlobalMessageTransfer__FromRoleAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGlobalMessageTransferAccess().getFromRoleAssignment_2()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__2__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4517:1: rule__GlobalMessageTransfer__Group__3 : rule__GlobalMessageTransfer__Group__3__Impl rule__GlobalMessageTransfer__Group__4 ;
    public final void rule__GlobalMessageTransfer__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4521:1: ( rule__GlobalMessageTransfer__Group__3__Impl rule__GlobalMessageTransfer__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4522:2: rule__GlobalMessageTransfer__Group__3__Impl rule__GlobalMessageTransfer__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__3__Impl_in_rule__GlobalMessageTransfer__Group__39029);
            rule__GlobalMessageTransfer__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__4_in_rule__GlobalMessageTransfer__Group__39032);
            rule__GlobalMessageTransfer__Group__4();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__3"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4529:1: rule__GlobalMessageTransfer__Group__3__Impl : ( 'to' ) ;
    public final void rule__GlobalMessageTransfer__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4533:1: ( ( 'to' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4534:1: ( 'to' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4534:1: ( 'to' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4535:1: 'to'
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getToKeyword_3()); 
            match(input,31,FOLLOW_31_in_rule__GlobalMessageTransfer__Group__3__Impl9060); 
             after(grammarAccess.getGlobalMessageTransferAccess().getToKeyword_3()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__3__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4548:1: rule__GlobalMessageTransfer__Group__4 : rule__GlobalMessageTransfer__Group__4__Impl rule__GlobalMessageTransfer__Group__5 ;
    public final void rule__GlobalMessageTransfer__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4552:1: ( rule__GlobalMessageTransfer__Group__4__Impl rule__GlobalMessageTransfer__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4553:2: rule__GlobalMessageTransfer__Group__4__Impl rule__GlobalMessageTransfer__Group__5
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__4__Impl_in_rule__GlobalMessageTransfer__Group__49091);
            rule__GlobalMessageTransfer__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__5_in_rule__GlobalMessageTransfer__Group__49094);
            rule__GlobalMessageTransfer__Group__5();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__4"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4560:1: rule__GlobalMessageTransfer__Group__4__Impl : ( ( rule__GlobalMessageTransfer__ToRoleAssignment_4 ) ) ;
    public final void rule__GlobalMessageTransfer__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4564:1: ( ( ( rule__GlobalMessageTransfer__ToRoleAssignment_4 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4565:1: ( ( rule__GlobalMessageTransfer__ToRoleAssignment_4 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4565:1: ( ( rule__GlobalMessageTransfer__ToRoleAssignment_4 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4566:1: ( rule__GlobalMessageTransfer__ToRoleAssignment_4 )
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getToRoleAssignment_4()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4567:1: ( rule__GlobalMessageTransfer__ToRoleAssignment_4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4567:2: rule__GlobalMessageTransfer__ToRoleAssignment_4
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__ToRoleAssignment_4_in_rule__GlobalMessageTransfer__Group__4__Impl9121);
            rule__GlobalMessageTransfer__ToRoleAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getGlobalMessageTransferAccess().getToRoleAssignment_4()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__4__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4577:1: rule__GlobalMessageTransfer__Group__5 : rule__GlobalMessageTransfer__Group__5__Impl rule__GlobalMessageTransfer__Group__6 ;
    public final void rule__GlobalMessageTransfer__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4581:1: ( rule__GlobalMessageTransfer__Group__5__Impl rule__GlobalMessageTransfer__Group__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4582:2: rule__GlobalMessageTransfer__Group__5__Impl rule__GlobalMessageTransfer__Group__6
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__5__Impl_in_rule__GlobalMessageTransfer__Group__59151);
            rule__GlobalMessageTransfer__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__6_in_rule__GlobalMessageTransfer__Group__59154);
            rule__GlobalMessageTransfer__Group__6();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__5"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4589:1: rule__GlobalMessageTransfer__Group__5__Impl : ( ( rule__GlobalMessageTransfer__Group_5__0 )* ) ;
    public final void rule__GlobalMessageTransfer__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4593:1: ( ( ( rule__GlobalMessageTransfer__Group_5__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4594:1: ( ( rule__GlobalMessageTransfer__Group_5__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4594:1: ( ( rule__GlobalMessageTransfer__Group_5__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4595:1: ( rule__GlobalMessageTransfer__Group_5__0 )*
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getGroup_5()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4596:1: ( rule__GlobalMessageTransfer__Group_5__0 )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==22) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4596:2: rule__GlobalMessageTransfer__Group_5__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group_5__0_in_rule__GlobalMessageTransfer__Group__5__Impl9181);
            	    rule__GlobalMessageTransfer__Group_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

             after(grammarAccess.getGlobalMessageTransferAccess().getGroup_5()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__5__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4606:1: rule__GlobalMessageTransfer__Group__6 : rule__GlobalMessageTransfer__Group__6__Impl ;
    public final void rule__GlobalMessageTransfer__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4610:1: ( rule__GlobalMessageTransfer__Group__6__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4611:2: rule__GlobalMessageTransfer__Group__6__Impl
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group__6__Impl_in_rule__GlobalMessageTransfer__Group__69212);
            rule__GlobalMessageTransfer__Group__6__Impl();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__6"


    // $ANTLR start "rule__GlobalMessageTransfer__Group__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4617:1: rule__GlobalMessageTransfer__Group__6__Impl : ( ';' ) ;
    public final void rule__GlobalMessageTransfer__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4621:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4622:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4622:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4623:1: ';'
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getSemicolonKeyword_6()); 
            match(input,12,FOLLOW_12_in_rule__GlobalMessageTransfer__Group__6__Impl9240); 
             after(grammarAccess.getGlobalMessageTransferAccess().getSemicolonKeyword_6()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group__6__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group_5__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4650:1: rule__GlobalMessageTransfer__Group_5__0 : rule__GlobalMessageTransfer__Group_5__0__Impl rule__GlobalMessageTransfer__Group_5__1 ;
    public final void rule__GlobalMessageTransfer__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4654:1: ( rule__GlobalMessageTransfer__Group_5__0__Impl rule__GlobalMessageTransfer__Group_5__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4655:2: rule__GlobalMessageTransfer__Group_5__0__Impl rule__GlobalMessageTransfer__Group_5__1
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group_5__0__Impl_in_rule__GlobalMessageTransfer__Group_5__09285);
            rule__GlobalMessageTransfer__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group_5__1_in_rule__GlobalMessageTransfer__Group_5__09288);
            rule__GlobalMessageTransfer__Group_5__1();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group_5__0"


    // $ANTLR start "rule__GlobalMessageTransfer__Group_5__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4662:1: rule__GlobalMessageTransfer__Group_5__0__Impl : ( ',' ) ;
    public final void rule__GlobalMessageTransfer__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4666:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4667:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4667:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4668:1: ','
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getCommaKeyword_5_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalMessageTransfer__Group_5__0__Impl9316); 
             after(grammarAccess.getGlobalMessageTransferAccess().getCommaKeyword_5_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group_5__0__Impl"


    // $ANTLR start "rule__GlobalMessageTransfer__Group_5__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4681:1: rule__GlobalMessageTransfer__Group_5__1 : rule__GlobalMessageTransfer__Group_5__1__Impl ;
    public final void rule__GlobalMessageTransfer__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4685:1: ( rule__GlobalMessageTransfer__Group_5__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4686:2: rule__GlobalMessageTransfer__Group_5__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__Group_5__1__Impl_in_rule__GlobalMessageTransfer__Group_5__19347);
            rule__GlobalMessageTransfer__Group_5__1__Impl();

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group_5__1"


    // $ANTLR start "rule__GlobalMessageTransfer__Group_5__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4692:1: rule__GlobalMessageTransfer__Group_5__1__Impl : ( ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 ) ) ;
    public final void rule__GlobalMessageTransfer__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4696:1: ( ( ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4697:1: ( ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4697:1: ( ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4698:1: ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 )
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getToRoleAssignment_5_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4699:1: ( rule__GlobalMessageTransfer__ToRoleAssignment_5_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4699:2: rule__GlobalMessageTransfer__ToRoleAssignment_5_1
            {
            pushFollow(FOLLOW_rule__GlobalMessageTransfer__ToRoleAssignment_5_1_in_rule__GlobalMessageTransfer__Group_5__1__Impl9374);
            rule__GlobalMessageTransfer__ToRoleAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalMessageTransferAccess().getToRoleAssignment_5_1()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__Group_5__1__Impl"


    // $ANTLR start "rule__GlobalChoice__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4713:1: rule__GlobalChoice__Group__0 : rule__GlobalChoice__Group__0__Impl rule__GlobalChoice__Group__1 ;
    public final void rule__GlobalChoice__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4717:1: ( rule__GlobalChoice__Group__0__Impl rule__GlobalChoice__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4718:2: rule__GlobalChoice__Group__0__Impl rule__GlobalChoice__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__0__Impl_in_rule__GlobalChoice__Group__09408);
            rule__GlobalChoice__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalChoice__Group__1_in_rule__GlobalChoice__Group__09411);
            rule__GlobalChoice__Group__1();

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
    // $ANTLR end "rule__GlobalChoice__Group__0"


    // $ANTLR start "rule__GlobalChoice__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4725:1: rule__GlobalChoice__Group__0__Impl : ( 'choice' ) ;
    public final void rule__GlobalChoice__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4729:1: ( ( 'choice' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4730:1: ( 'choice' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4730:1: ( 'choice' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4731:1: 'choice'
            {
             before(grammarAccess.getGlobalChoiceAccess().getChoiceKeyword_0()); 
            match(input,32,FOLLOW_32_in_rule__GlobalChoice__Group__0__Impl9439); 
             after(grammarAccess.getGlobalChoiceAccess().getChoiceKeyword_0()); 

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
    // $ANTLR end "rule__GlobalChoice__Group__0__Impl"


    // $ANTLR start "rule__GlobalChoice__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4744:1: rule__GlobalChoice__Group__1 : rule__GlobalChoice__Group__1__Impl rule__GlobalChoice__Group__2 ;
    public final void rule__GlobalChoice__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4748:1: ( rule__GlobalChoice__Group__1__Impl rule__GlobalChoice__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4749:2: rule__GlobalChoice__Group__1__Impl rule__GlobalChoice__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__1__Impl_in_rule__GlobalChoice__Group__19470);
            rule__GlobalChoice__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalChoice__Group__2_in_rule__GlobalChoice__Group__19473);
            rule__GlobalChoice__Group__2();

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
    // $ANTLR end "rule__GlobalChoice__Group__1"


    // $ANTLR start "rule__GlobalChoice__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4756:1: rule__GlobalChoice__Group__1__Impl : ( 'at' ) ;
    public final void rule__GlobalChoice__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4760:1: ( ( 'at' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4761:1: ( 'at' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4761:1: ( 'at' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4762:1: 'at'
            {
             before(grammarAccess.getGlobalChoiceAccess().getAtKeyword_1()); 
            match(input,33,FOLLOW_33_in_rule__GlobalChoice__Group__1__Impl9501); 
             after(grammarAccess.getGlobalChoiceAccess().getAtKeyword_1()); 

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
    // $ANTLR end "rule__GlobalChoice__Group__1__Impl"


    // $ANTLR start "rule__GlobalChoice__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4775:1: rule__GlobalChoice__Group__2 : rule__GlobalChoice__Group__2__Impl rule__GlobalChoice__Group__3 ;
    public final void rule__GlobalChoice__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4779:1: ( rule__GlobalChoice__Group__2__Impl rule__GlobalChoice__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4780:2: rule__GlobalChoice__Group__2__Impl rule__GlobalChoice__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__2__Impl_in_rule__GlobalChoice__Group__29532);
            rule__GlobalChoice__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalChoice__Group__3_in_rule__GlobalChoice__Group__29535);
            rule__GlobalChoice__Group__3();

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
    // $ANTLR end "rule__GlobalChoice__Group__2"


    // $ANTLR start "rule__GlobalChoice__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4787:1: rule__GlobalChoice__Group__2__Impl : ( ( rule__GlobalChoice__RoleAssignment_2 ) ) ;
    public final void rule__GlobalChoice__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4791:1: ( ( ( rule__GlobalChoice__RoleAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4792:1: ( ( rule__GlobalChoice__RoleAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4792:1: ( ( rule__GlobalChoice__RoleAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4793:1: ( rule__GlobalChoice__RoleAssignment_2 )
            {
             before(grammarAccess.getGlobalChoiceAccess().getRoleAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4794:1: ( rule__GlobalChoice__RoleAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4794:2: rule__GlobalChoice__RoleAssignment_2
            {
            pushFollow(FOLLOW_rule__GlobalChoice__RoleAssignment_2_in_rule__GlobalChoice__Group__2__Impl9562);
            rule__GlobalChoice__RoleAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGlobalChoiceAccess().getRoleAssignment_2()); 

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
    // $ANTLR end "rule__GlobalChoice__Group__2__Impl"


    // $ANTLR start "rule__GlobalChoice__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4804:1: rule__GlobalChoice__Group__3 : rule__GlobalChoice__Group__3__Impl rule__GlobalChoice__Group__4 ;
    public final void rule__GlobalChoice__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4808:1: ( rule__GlobalChoice__Group__3__Impl rule__GlobalChoice__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4809:2: rule__GlobalChoice__Group__3__Impl rule__GlobalChoice__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__3__Impl_in_rule__GlobalChoice__Group__39592);
            rule__GlobalChoice__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalChoice__Group__4_in_rule__GlobalChoice__Group__39595);
            rule__GlobalChoice__Group__4();

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
    // $ANTLR end "rule__GlobalChoice__Group__3"


    // $ANTLR start "rule__GlobalChoice__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4816:1: rule__GlobalChoice__Group__3__Impl : ( ( rule__GlobalChoice__BlocksAssignment_3 ) ) ;
    public final void rule__GlobalChoice__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4820:1: ( ( ( rule__GlobalChoice__BlocksAssignment_3 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4821:1: ( ( rule__GlobalChoice__BlocksAssignment_3 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4821:1: ( ( rule__GlobalChoice__BlocksAssignment_3 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4822:1: ( rule__GlobalChoice__BlocksAssignment_3 )
            {
             before(grammarAccess.getGlobalChoiceAccess().getBlocksAssignment_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4823:1: ( rule__GlobalChoice__BlocksAssignment_3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4823:2: rule__GlobalChoice__BlocksAssignment_3
            {
            pushFollow(FOLLOW_rule__GlobalChoice__BlocksAssignment_3_in_rule__GlobalChoice__Group__3__Impl9622);
            rule__GlobalChoice__BlocksAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getGlobalChoiceAccess().getBlocksAssignment_3()); 

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
    // $ANTLR end "rule__GlobalChoice__Group__3__Impl"


    // $ANTLR start "rule__GlobalChoice__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4833:1: rule__GlobalChoice__Group__4 : rule__GlobalChoice__Group__4__Impl ;
    public final void rule__GlobalChoice__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4837:1: ( rule__GlobalChoice__Group__4__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4838:2: rule__GlobalChoice__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group__4__Impl_in_rule__GlobalChoice__Group__49652);
            rule__GlobalChoice__Group__4__Impl();

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
    // $ANTLR end "rule__GlobalChoice__Group__4"


    // $ANTLR start "rule__GlobalChoice__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4844:1: rule__GlobalChoice__Group__4__Impl : ( ( rule__GlobalChoice__Group_4__0 )* ) ;
    public final void rule__GlobalChoice__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4848:1: ( ( ( rule__GlobalChoice__Group_4__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4849:1: ( ( rule__GlobalChoice__Group_4__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4849:1: ( ( rule__GlobalChoice__Group_4__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4850:1: ( rule__GlobalChoice__Group_4__0 )*
            {
             before(grammarAccess.getGlobalChoiceAccess().getGroup_4()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4851:1: ( rule__GlobalChoice__Group_4__0 )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==34) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4851:2: rule__GlobalChoice__Group_4__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalChoice__Group_4__0_in_rule__GlobalChoice__Group__4__Impl9679);
            	    rule__GlobalChoice__Group_4__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

             after(grammarAccess.getGlobalChoiceAccess().getGroup_4()); 

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
    // $ANTLR end "rule__GlobalChoice__Group__4__Impl"


    // $ANTLR start "rule__GlobalChoice__Group_4__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4871:1: rule__GlobalChoice__Group_4__0 : rule__GlobalChoice__Group_4__0__Impl rule__GlobalChoice__Group_4__1 ;
    public final void rule__GlobalChoice__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4875:1: ( rule__GlobalChoice__Group_4__0__Impl rule__GlobalChoice__Group_4__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4876:2: rule__GlobalChoice__Group_4__0__Impl rule__GlobalChoice__Group_4__1
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group_4__0__Impl_in_rule__GlobalChoice__Group_4__09720);
            rule__GlobalChoice__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalChoice__Group_4__1_in_rule__GlobalChoice__Group_4__09723);
            rule__GlobalChoice__Group_4__1();

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
    // $ANTLR end "rule__GlobalChoice__Group_4__0"


    // $ANTLR start "rule__GlobalChoice__Group_4__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4883:1: rule__GlobalChoice__Group_4__0__Impl : ( 'or' ) ;
    public final void rule__GlobalChoice__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4887:1: ( ( 'or' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4888:1: ( 'or' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4888:1: ( 'or' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4889:1: 'or'
            {
             before(grammarAccess.getGlobalChoiceAccess().getOrKeyword_4_0()); 
            match(input,34,FOLLOW_34_in_rule__GlobalChoice__Group_4__0__Impl9751); 
             after(grammarAccess.getGlobalChoiceAccess().getOrKeyword_4_0()); 

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
    // $ANTLR end "rule__GlobalChoice__Group_4__0__Impl"


    // $ANTLR start "rule__GlobalChoice__Group_4__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4902:1: rule__GlobalChoice__Group_4__1 : rule__GlobalChoice__Group_4__1__Impl ;
    public final void rule__GlobalChoice__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4906:1: ( rule__GlobalChoice__Group_4__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4907:2: rule__GlobalChoice__Group_4__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalChoice__Group_4__1__Impl_in_rule__GlobalChoice__Group_4__19782);
            rule__GlobalChoice__Group_4__1__Impl();

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
    // $ANTLR end "rule__GlobalChoice__Group_4__1"


    // $ANTLR start "rule__GlobalChoice__Group_4__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4913:1: rule__GlobalChoice__Group_4__1__Impl : ( ( rule__GlobalChoice__BlocksAssignment_4_1 ) ) ;
    public final void rule__GlobalChoice__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4917:1: ( ( ( rule__GlobalChoice__BlocksAssignment_4_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4918:1: ( ( rule__GlobalChoice__BlocksAssignment_4_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4918:1: ( ( rule__GlobalChoice__BlocksAssignment_4_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4919:1: ( rule__GlobalChoice__BlocksAssignment_4_1 )
            {
             before(grammarAccess.getGlobalChoiceAccess().getBlocksAssignment_4_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4920:1: ( rule__GlobalChoice__BlocksAssignment_4_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4920:2: rule__GlobalChoice__BlocksAssignment_4_1
            {
            pushFollow(FOLLOW_rule__GlobalChoice__BlocksAssignment_4_1_in_rule__GlobalChoice__Group_4__1__Impl9809);
            rule__GlobalChoice__BlocksAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalChoiceAccess().getBlocksAssignment_4_1()); 

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
    // $ANTLR end "rule__GlobalChoice__Group_4__1__Impl"


    // $ANTLR start "rule__GlobalRecursion__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4934:1: rule__GlobalRecursion__Group__0 : rule__GlobalRecursion__Group__0__Impl rule__GlobalRecursion__Group__1 ;
    public final void rule__GlobalRecursion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4938:1: ( rule__GlobalRecursion__Group__0__Impl rule__GlobalRecursion__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4939:2: rule__GlobalRecursion__Group__0__Impl rule__GlobalRecursion__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__Group__0__Impl_in_rule__GlobalRecursion__Group__09843);
            rule__GlobalRecursion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalRecursion__Group__1_in_rule__GlobalRecursion__Group__09846);
            rule__GlobalRecursion__Group__1();

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
    // $ANTLR end "rule__GlobalRecursion__Group__0"


    // $ANTLR start "rule__GlobalRecursion__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4946:1: rule__GlobalRecursion__Group__0__Impl : ( 'rec' ) ;
    public final void rule__GlobalRecursion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4950:1: ( ( 'rec' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4951:1: ( 'rec' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4951:1: ( 'rec' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4952:1: 'rec'
            {
             before(grammarAccess.getGlobalRecursionAccess().getRecKeyword_0()); 
            match(input,35,FOLLOW_35_in_rule__GlobalRecursion__Group__0__Impl9874); 
             after(grammarAccess.getGlobalRecursionAccess().getRecKeyword_0()); 

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
    // $ANTLR end "rule__GlobalRecursion__Group__0__Impl"


    // $ANTLR start "rule__GlobalRecursion__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4965:1: rule__GlobalRecursion__Group__1 : rule__GlobalRecursion__Group__1__Impl rule__GlobalRecursion__Group__2 ;
    public final void rule__GlobalRecursion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4969:1: ( rule__GlobalRecursion__Group__1__Impl rule__GlobalRecursion__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4970:2: rule__GlobalRecursion__Group__1__Impl rule__GlobalRecursion__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__Group__1__Impl_in_rule__GlobalRecursion__Group__19905);
            rule__GlobalRecursion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalRecursion__Group__2_in_rule__GlobalRecursion__Group__19908);
            rule__GlobalRecursion__Group__2();

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
    // $ANTLR end "rule__GlobalRecursion__Group__1"


    // $ANTLR start "rule__GlobalRecursion__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4977:1: rule__GlobalRecursion__Group__1__Impl : ( ( rule__GlobalRecursion__LabelAssignment_1 ) ) ;
    public final void rule__GlobalRecursion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4981:1: ( ( ( rule__GlobalRecursion__LabelAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4982:1: ( ( rule__GlobalRecursion__LabelAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4982:1: ( ( rule__GlobalRecursion__LabelAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4983:1: ( rule__GlobalRecursion__LabelAssignment_1 )
            {
             before(grammarAccess.getGlobalRecursionAccess().getLabelAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4984:1: ( rule__GlobalRecursion__LabelAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4984:2: rule__GlobalRecursion__LabelAssignment_1
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__LabelAssignment_1_in_rule__GlobalRecursion__Group__1__Impl9935);
            rule__GlobalRecursion__LabelAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalRecursionAccess().getLabelAssignment_1()); 

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
    // $ANTLR end "rule__GlobalRecursion__Group__1__Impl"


    // $ANTLR start "rule__GlobalRecursion__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4994:1: rule__GlobalRecursion__Group__2 : rule__GlobalRecursion__Group__2__Impl ;
    public final void rule__GlobalRecursion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4998:1: ( rule__GlobalRecursion__Group__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:4999:2: rule__GlobalRecursion__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__Group__2__Impl_in_rule__GlobalRecursion__Group__29965);
            rule__GlobalRecursion__Group__2__Impl();

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
    // $ANTLR end "rule__GlobalRecursion__Group__2"


    // $ANTLR start "rule__GlobalRecursion__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5005:1: rule__GlobalRecursion__Group__2__Impl : ( ( rule__GlobalRecursion__BlockAssignment_2 ) ) ;
    public final void rule__GlobalRecursion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5009:1: ( ( ( rule__GlobalRecursion__BlockAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5010:1: ( ( rule__GlobalRecursion__BlockAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5010:1: ( ( rule__GlobalRecursion__BlockAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5011:1: ( rule__GlobalRecursion__BlockAssignment_2 )
            {
             before(grammarAccess.getGlobalRecursionAccess().getBlockAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5012:1: ( rule__GlobalRecursion__BlockAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5012:2: rule__GlobalRecursion__BlockAssignment_2
            {
            pushFollow(FOLLOW_rule__GlobalRecursion__BlockAssignment_2_in_rule__GlobalRecursion__Group__2__Impl9992);
            rule__GlobalRecursion__BlockAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGlobalRecursionAccess().getBlockAssignment_2()); 

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
    // $ANTLR end "rule__GlobalRecursion__Group__2__Impl"


    // $ANTLR start "rule__GlobalContinue__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5028:1: rule__GlobalContinue__Group__0 : rule__GlobalContinue__Group__0__Impl rule__GlobalContinue__Group__1 ;
    public final void rule__GlobalContinue__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5032:1: ( rule__GlobalContinue__Group__0__Impl rule__GlobalContinue__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5033:2: rule__GlobalContinue__Group__0__Impl rule__GlobalContinue__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalContinue__Group__0__Impl_in_rule__GlobalContinue__Group__010028);
            rule__GlobalContinue__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalContinue__Group__1_in_rule__GlobalContinue__Group__010031);
            rule__GlobalContinue__Group__1();

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
    // $ANTLR end "rule__GlobalContinue__Group__0"


    // $ANTLR start "rule__GlobalContinue__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5040:1: rule__GlobalContinue__Group__0__Impl : ( 'continue' ) ;
    public final void rule__GlobalContinue__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5044:1: ( ( 'continue' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5045:1: ( 'continue' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5045:1: ( 'continue' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5046:1: 'continue'
            {
             before(grammarAccess.getGlobalContinueAccess().getContinueKeyword_0()); 
            match(input,36,FOLLOW_36_in_rule__GlobalContinue__Group__0__Impl10059); 
             after(grammarAccess.getGlobalContinueAccess().getContinueKeyword_0()); 

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
    // $ANTLR end "rule__GlobalContinue__Group__0__Impl"


    // $ANTLR start "rule__GlobalContinue__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5059:1: rule__GlobalContinue__Group__1 : rule__GlobalContinue__Group__1__Impl rule__GlobalContinue__Group__2 ;
    public final void rule__GlobalContinue__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5063:1: ( rule__GlobalContinue__Group__1__Impl rule__GlobalContinue__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5064:2: rule__GlobalContinue__Group__1__Impl rule__GlobalContinue__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalContinue__Group__1__Impl_in_rule__GlobalContinue__Group__110090);
            rule__GlobalContinue__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalContinue__Group__2_in_rule__GlobalContinue__Group__110093);
            rule__GlobalContinue__Group__2();

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
    // $ANTLR end "rule__GlobalContinue__Group__1"


    // $ANTLR start "rule__GlobalContinue__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5071:1: rule__GlobalContinue__Group__1__Impl : ( ( rule__GlobalContinue__LabelAssignment_1 ) ) ;
    public final void rule__GlobalContinue__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5075:1: ( ( ( rule__GlobalContinue__LabelAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5076:1: ( ( rule__GlobalContinue__LabelAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5076:1: ( ( rule__GlobalContinue__LabelAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5077:1: ( rule__GlobalContinue__LabelAssignment_1 )
            {
             before(grammarAccess.getGlobalContinueAccess().getLabelAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5078:1: ( rule__GlobalContinue__LabelAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5078:2: rule__GlobalContinue__LabelAssignment_1
            {
            pushFollow(FOLLOW_rule__GlobalContinue__LabelAssignment_1_in_rule__GlobalContinue__Group__1__Impl10120);
            rule__GlobalContinue__LabelAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalContinueAccess().getLabelAssignment_1()); 

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
    // $ANTLR end "rule__GlobalContinue__Group__1__Impl"


    // $ANTLR start "rule__GlobalContinue__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5088:1: rule__GlobalContinue__Group__2 : rule__GlobalContinue__Group__2__Impl ;
    public final void rule__GlobalContinue__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5092:1: ( rule__GlobalContinue__Group__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5093:2: rule__GlobalContinue__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__GlobalContinue__Group__2__Impl_in_rule__GlobalContinue__Group__210150);
            rule__GlobalContinue__Group__2__Impl();

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
    // $ANTLR end "rule__GlobalContinue__Group__2"


    // $ANTLR start "rule__GlobalContinue__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5099:1: rule__GlobalContinue__Group__2__Impl : ( ';' ) ;
    public final void rule__GlobalContinue__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5103:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5104:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5104:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5105:1: ';'
            {
             before(grammarAccess.getGlobalContinueAccess().getSemicolonKeyword_2()); 
            match(input,12,FOLLOW_12_in_rule__GlobalContinue__Group__2__Impl10178); 
             after(grammarAccess.getGlobalContinueAccess().getSemicolonKeyword_2()); 

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
    // $ANTLR end "rule__GlobalContinue__Group__2__Impl"


    // $ANTLR start "rule__GlobalParallel__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5124:1: rule__GlobalParallel__Group__0 : rule__GlobalParallel__Group__0__Impl rule__GlobalParallel__Group__1 ;
    public final void rule__GlobalParallel__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5128:1: ( rule__GlobalParallel__Group__0__Impl rule__GlobalParallel__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5129:2: rule__GlobalParallel__Group__0__Impl rule__GlobalParallel__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group__0__Impl_in_rule__GlobalParallel__Group__010215);
            rule__GlobalParallel__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalParallel__Group__1_in_rule__GlobalParallel__Group__010218);
            rule__GlobalParallel__Group__1();

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
    // $ANTLR end "rule__GlobalParallel__Group__0"


    // $ANTLR start "rule__GlobalParallel__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5136:1: rule__GlobalParallel__Group__0__Impl : ( 'par' ) ;
    public final void rule__GlobalParallel__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5140:1: ( ( 'par' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5141:1: ( 'par' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5141:1: ( 'par' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5142:1: 'par'
            {
             before(grammarAccess.getGlobalParallelAccess().getParKeyword_0()); 
            match(input,37,FOLLOW_37_in_rule__GlobalParallel__Group__0__Impl10246); 
             after(grammarAccess.getGlobalParallelAccess().getParKeyword_0()); 

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
    // $ANTLR end "rule__GlobalParallel__Group__0__Impl"


    // $ANTLR start "rule__GlobalParallel__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5155:1: rule__GlobalParallel__Group__1 : rule__GlobalParallel__Group__1__Impl rule__GlobalParallel__Group__2 ;
    public final void rule__GlobalParallel__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5159:1: ( rule__GlobalParallel__Group__1__Impl rule__GlobalParallel__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5160:2: rule__GlobalParallel__Group__1__Impl rule__GlobalParallel__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group__1__Impl_in_rule__GlobalParallel__Group__110277);
            rule__GlobalParallel__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalParallel__Group__2_in_rule__GlobalParallel__Group__110280);
            rule__GlobalParallel__Group__2();

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
    // $ANTLR end "rule__GlobalParallel__Group__1"


    // $ANTLR start "rule__GlobalParallel__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5167:1: rule__GlobalParallel__Group__1__Impl : ( ( rule__GlobalParallel__BlocksAssignment_1 ) ) ;
    public final void rule__GlobalParallel__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5171:1: ( ( ( rule__GlobalParallel__BlocksAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5172:1: ( ( rule__GlobalParallel__BlocksAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5172:1: ( ( rule__GlobalParallel__BlocksAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5173:1: ( rule__GlobalParallel__BlocksAssignment_1 )
            {
             before(grammarAccess.getGlobalParallelAccess().getBlocksAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5174:1: ( rule__GlobalParallel__BlocksAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5174:2: rule__GlobalParallel__BlocksAssignment_1
            {
            pushFollow(FOLLOW_rule__GlobalParallel__BlocksAssignment_1_in_rule__GlobalParallel__Group__1__Impl10307);
            rule__GlobalParallel__BlocksAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalParallelAccess().getBlocksAssignment_1()); 

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
    // $ANTLR end "rule__GlobalParallel__Group__1__Impl"


    // $ANTLR start "rule__GlobalParallel__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5184:1: rule__GlobalParallel__Group__2 : rule__GlobalParallel__Group__2__Impl ;
    public final void rule__GlobalParallel__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5188:1: ( rule__GlobalParallel__Group__2__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5189:2: rule__GlobalParallel__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group__2__Impl_in_rule__GlobalParallel__Group__210337);
            rule__GlobalParallel__Group__2__Impl();

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
    // $ANTLR end "rule__GlobalParallel__Group__2"


    // $ANTLR start "rule__GlobalParallel__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5195:1: rule__GlobalParallel__Group__2__Impl : ( ( rule__GlobalParallel__Group_2__0 )* ) ;
    public final void rule__GlobalParallel__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5199:1: ( ( ( rule__GlobalParallel__Group_2__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5200:1: ( ( rule__GlobalParallel__Group_2__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5200:1: ( ( rule__GlobalParallel__Group_2__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5201:1: ( rule__GlobalParallel__Group_2__0 )*
            {
             before(grammarAccess.getGlobalParallelAccess().getGroup_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5202:1: ( rule__GlobalParallel__Group_2__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==38) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5202:2: rule__GlobalParallel__Group_2__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalParallel__Group_2__0_in_rule__GlobalParallel__Group__2__Impl10364);
            	    rule__GlobalParallel__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

             after(grammarAccess.getGlobalParallelAccess().getGroup_2()); 

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
    // $ANTLR end "rule__GlobalParallel__Group__2__Impl"


    // $ANTLR start "rule__GlobalParallel__Group_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5218:1: rule__GlobalParallel__Group_2__0 : rule__GlobalParallel__Group_2__0__Impl rule__GlobalParallel__Group_2__1 ;
    public final void rule__GlobalParallel__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5222:1: ( rule__GlobalParallel__Group_2__0__Impl rule__GlobalParallel__Group_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5223:2: rule__GlobalParallel__Group_2__0__Impl rule__GlobalParallel__Group_2__1
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group_2__0__Impl_in_rule__GlobalParallel__Group_2__010401);
            rule__GlobalParallel__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalParallel__Group_2__1_in_rule__GlobalParallel__Group_2__010404);
            rule__GlobalParallel__Group_2__1();

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
    // $ANTLR end "rule__GlobalParallel__Group_2__0"


    // $ANTLR start "rule__GlobalParallel__Group_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5230:1: rule__GlobalParallel__Group_2__0__Impl : ( 'and' ) ;
    public final void rule__GlobalParallel__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5234:1: ( ( 'and' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5235:1: ( 'and' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5235:1: ( 'and' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5236:1: 'and'
            {
             before(grammarAccess.getGlobalParallelAccess().getAndKeyword_2_0()); 
            match(input,38,FOLLOW_38_in_rule__GlobalParallel__Group_2__0__Impl10432); 
             after(grammarAccess.getGlobalParallelAccess().getAndKeyword_2_0()); 

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
    // $ANTLR end "rule__GlobalParallel__Group_2__0__Impl"


    // $ANTLR start "rule__GlobalParallel__Group_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5249:1: rule__GlobalParallel__Group_2__1 : rule__GlobalParallel__Group_2__1__Impl ;
    public final void rule__GlobalParallel__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5253:1: ( rule__GlobalParallel__Group_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5254:2: rule__GlobalParallel__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalParallel__Group_2__1__Impl_in_rule__GlobalParallel__Group_2__110463);
            rule__GlobalParallel__Group_2__1__Impl();

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
    // $ANTLR end "rule__GlobalParallel__Group_2__1"


    // $ANTLR start "rule__GlobalParallel__Group_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5260:1: rule__GlobalParallel__Group_2__1__Impl : ( ( rule__GlobalParallel__BlocksAssignment_2_1 ) ) ;
    public final void rule__GlobalParallel__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5264:1: ( ( ( rule__GlobalParallel__BlocksAssignment_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5265:1: ( ( rule__GlobalParallel__BlocksAssignment_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5265:1: ( ( rule__GlobalParallel__BlocksAssignment_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5266:1: ( rule__GlobalParallel__BlocksAssignment_2_1 )
            {
             before(grammarAccess.getGlobalParallelAccess().getBlocksAssignment_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5267:1: ( rule__GlobalParallel__BlocksAssignment_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5267:2: rule__GlobalParallel__BlocksAssignment_2_1
            {
            pushFollow(FOLLOW_rule__GlobalParallel__BlocksAssignment_2_1_in_rule__GlobalParallel__Group_2__1__Impl10490);
            rule__GlobalParallel__BlocksAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalParallelAccess().getBlocksAssignment_2_1()); 

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
    // $ANTLR end "rule__GlobalParallel__Group_2__1__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5281:1: rule__GlobalInterruptible__Group__0 : rule__GlobalInterruptible__Group__0__Impl rule__GlobalInterruptible__Group__1 ;
    public final void rule__GlobalInterruptible__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5285:1: ( rule__GlobalInterruptible__Group__0__Impl rule__GlobalInterruptible__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5286:2: rule__GlobalInterruptible__Group__0__Impl rule__GlobalInterruptible__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__0__Impl_in_rule__GlobalInterruptible__Group__010524);
            rule__GlobalInterruptible__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__1_in_rule__GlobalInterruptible__Group__010527);
            rule__GlobalInterruptible__Group__1();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__0"


    // $ANTLR start "rule__GlobalInterruptible__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5293:1: rule__GlobalInterruptible__Group__0__Impl : ( 'interruptible' ) ;
    public final void rule__GlobalInterruptible__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5297:1: ( ( 'interruptible' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5298:1: ( 'interruptible' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5298:1: ( 'interruptible' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5299:1: 'interruptible'
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getInterruptibleKeyword_0()); 
            match(input,39,FOLLOW_39_in_rule__GlobalInterruptible__Group__0__Impl10555); 
             after(grammarAccess.getGlobalInterruptibleAccess().getInterruptibleKeyword_0()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__0__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5312:1: rule__GlobalInterruptible__Group__1 : rule__GlobalInterruptible__Group__1__Impl rule__GlobalInterruptible__Group__2 ;
    public final void rule__GlobalInterruptible__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5316:1: ( rule__GlobalInterruptible__Group__1__Impl rule__GlobalInterruptible__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5317:2: rule__GlobalInterruptible__Group__1__Impl rule__GlobalInterruptible__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__1__Impl_in_rule__GlobalInterruptible__Group__110586);
            rule__GlobalInterruptible__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__2_in_rule__GlobalInterruptible__Group__110589);
            rule__GlobalInterruptible__Group__2();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__1"


    // $ANTLR start "rule__GlobalInterruptible__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5324:1: rule__GlobalInterruptible__Group__1__Impl : ( ( rule__GlobalInterruptible__Group_1__0 )? ) ;
    public final void rule__GlobalInterruptible__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5328:1: ( ( ( rule__GlobalInterruptible__Group_1__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5329:1: ( ( rule__GlobalInterruptible__Group_1__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5329:1: ( ( rule__GlobalInterruptible__Group_1__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5330:1: ( rule__GlobalInterruptible__Group_1__0 )?
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getGroup_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5331:1: ( rule__GlobalInterruptible__Group_1__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_ID) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5331:2: rule__GlobalInterruptible__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__GlobalInterruptible__Group_1__0_in_rule__GlobalInterruptible__Group__1__Impl10616);
                    rule__GlobalInterruptible__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGlobalInterruptibleAccess().getGroup_1()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__1__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5341:1: rule__GlobalInterruptible__Group__2 : rule__GlobalInterruptible__Group__2__Impl rule__GlobalInterruptible__Group__3 ;
    public final void rule__GlobalInterruptible__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5345:1: ( rule__GlobalInterruptible__Group__2__Impl rule__GlobalInterruptible__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5346:2: rule__GlobalInterruptible__Group__2__Impl rule__GlobalInterruptible__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__2__Impl_in_rule__GlobalInterruptible__Group__210647);
            rule__GlobalInterruptible__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__3_in_rule__GlobalInterruptible__Group__210650);
            rule__GlobalInterruptible__Group__3();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__2"


    // $ANTLR start "rule__GlobalInterruptible__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5353:1: rule__GlobalInterruptible__Group__2__Impl : ( ( rule__GlobalInterruptible__BlockAssignment_2 ) ) ;
    public final void rule__GlobalInterruptible__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5357:1: ( ( ( rule__GlobalInterruptible__BlockAssignment_2 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5358:1: ( ( rule__GlobalInterruptible__BlockAssignment_2 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5358:1: ( ( rule__GlobalInterruptible__BlockAssignment_2 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5359:1: ( rule__GlobalInterruptible__BlockAssignment_2 )
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getBlockAssignment_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5360:1: ( rule__GlobalInterruptible__BlockAssignment_2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5360:2: rule__GlobalInterruptible__BlockAssignment_2
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__BlockAssignment_2_in_rule__GlobalInterruptible__Group__2__Impl10677);
            rule__GlobalInterruptible__BlockAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptibleAccess().getBlockAssignment_2()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__2__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5370:1: rule__GlobalInterruptible__Group__3 : rule__GlobalInterruptible__Group__3__Impl rule__GlobalInterruptible__Group__4 ;
    public final void rule__GlobalInterruptible__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5374:1: ( rule__GlobalInterruptible__Group__3__Impl rule__GlobalInterruptible__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5375:2: rule__GlobalInterruptible__Group__3__Impl rule__GlobalInterruptible__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__3__Impl_in_rule__GlobalInterruptible__Group__310707);
            rule__GlobalInterruptible__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__4_in_rule__GlobalInterruptible__Group__310710);
            rule__GlobalInterruptible__Group__4();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__3"


    // $ANTLR start "rule__GlobalInterruptible__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5382:1: rule__GlobalInterruptible__Group__3__Impl : ( 'with' ) ;
    public final void rule__GlobalInterruptible__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5386:1: ( ( 'with' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5387:1: ( 'with' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5387:1: ( 'with' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5388:1: 'with'
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getWithKeyword_3()); 
            match(input,40,FOLLOW_40_in_rule__GlobalInterruptible__Group__3__Impl10738); 
             after(grammarAccess.getGlobalInterruptibleAccess().getWithKeyword_3()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__3__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5401:1: rule__GlobalInterruptible__Group__4 : rule__GlobalInterruptible__Group__4__Impl rule__GlobalInterruptible__Group__5 ;
    public final void rule__GlobalInterruptible__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5405:1: ( rule__GlobalInterruptible__Group__4__Impl rule__GlobalInterruptible__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5406:2: rule__GlobalInterruptible__Group__4__Impl rule__GlobalInterruptible__Group__5
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__4__Impl_in_rule__GlobalInterruptible__Group__410769);
            rule__GlobalInterruptible__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__5_in_rule__GlobalInterruptible__Group__410772);
            rule__GlobalInterruptible__Group__5();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__4"


    // $ANTLR start "rule__GlobalInterruptible__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5413:1: rule__GlobalInterruptible__Group__4__Impl : ( '{' ) ;
    public final void rule__GlobalInterruptible__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5417:1: ( ( '{' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5418:1: ( '{' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5418:1: ( '{' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5419:1: '{'
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getLeftCurlyBracketKeyword_4()); 
            match(input,29,FOLLOW_29_in_rule__GlobalInterruptible__Group__4__Impl10800); 
             after(grammarAccess.getGlobalInterruptibleAccess().getLeftCurlyBracketKeyword_4()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__4__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5432:1: rule__GlobalInterruptible__Group__5 : rule__GlobalInterruptible__Group__5__Impl rule__GlobalInterruptible__Group__6 ;
    public final void rule__GlobalInterruptible__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5436:1: ( rule__GlobalInterruptible__Group__5__Impl rule__GlobalInterruptible__Group__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5437:2: rule__GlobalInterruptible__Group__5__Impl rule__GlobalInterruptible__Group__6
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__5__Impl_in_rule__GlobalInterruptible__Group__510831);
            rule__GlobalInterruptible__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__6_in_rule__GlobalInterruptible__Group__510834);
            rule__GlobalInterruptible__Group__6();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__5"


    // $ANTLR start "rule__GlobalInterruptible__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5444:1: rule__GlobalInterruptible__Group__5__Impl : ( ( rule__GlobalInterruptible__InterruptsAssignment_5 )* ) ;
    public final void rule__GlobalInterruptible__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5448:1: ( ( ( rule__GlobalInterruptible__InterruptsAssignment_5 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5449:1: ( ( rule__GlobalInterruptible__InterruptsAssignment_5 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5449:1: ( ( rule__GlobalInterruptible__InterruptsAssignment_5 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5450:1: ( rule__GlobalInterruptible__InterruptsAssignment_5 )*
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getInterruptsAssignment_5()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5451:1: ( rule__GlobalInterruptible__InterruptsAssignment_5 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==RULE_ID) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5451:2: rule__GlobalInterruptible__InterruptsAssignment_5
            	    {
            	    pushFollow(FOLLOW_rule__GlobalInterruptible__InterruptsAssignment_5_in_rule__GlobalInterruptible__Group__5__Impl10861);
            	    rule__GlobalInterruptible__InterruptsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

             after(grammarAccess.getGlobalInterruptibleAccess().getInterruptsAssignment_5()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__5__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5461:1: rule__GlobalInterruptible__Group__6 : rule__GlobalInterruptible__Group__6__Impl ;
    public final void rule__GlobalInterruptible__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5465:1: ( rule__GlobalInterruptible__Group__6__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5466:2: rule__GlobalInterruptible__Group__6__Impl
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group__6__Impl_in_rule__GlobalInterruptible__Group__610892);
            rule__GlobalInterruptible__Group__6__Impl();

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
    // $ANTLR end "rule__GlobalInterruptible__Group__6"


    // $ANTLR start "rule__GlobalInterruptible__Group__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5472:1: rule__GlobalInterruptible__Group__6__Impl : ( '}' ) ;
    public final void rule__GlobalInterruptible__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5476:1: ( ( '}' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5477:1: ( '}' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5477:1: ( '}' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5478:1: '}'
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getRightCurlyBracketKeyword_6()); 
            match(input,30,FOLLOW_30_in_rule__GlobalInterruptible__Group__6__Impl10920); 
             after(grammarAccess.getGlobalInterruptibleAccess().getRightCurlyBracketKeyword_6()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group__6__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5505:1: rule__GlobalInterruptible__Group_1__0 : rule__GlobalInterruptible__Group_1__0__Impl rule__GlobalInterruptible__Group_1__1 ;
    public final void rule__GlobalInterruptible__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5509:1: ( rule__GlobalInterruptible__Group_1__0__Impl rule__GlobalInterruptible__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5510:2: rule__GlobalInterruptible__Group_1__0__Impl rule__GlobalInterruptible__Group_1__1
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group_1__0__Impl_in_rule__GlobalInterruptible__Group_1__010965);
            rule__GlobalInterruptible__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterruptible__Group_1__1_in_rule__GlobalInterruptible__Group_1__010968);
            rule__GlobalInterruptible__Group_1__1();

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
    // $ANTLR end "rule__GlobalInterruptible__Group_1__0"


    // $ANTLR start "rule__GlobalInterruptible__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5517:1: rule__GlobalInterruptible__Group_1__0__Impl : ( ( rule__GlobalInterruptible__ScopeAssignment_1_0 ) ) ;
    public final void rule__GlobalInterruptible__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5521:1: ( ( ( rule__GlobalInterruptible__ScopeAssignment_1_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5522:1: ( ( rule__GlobalInterruptible__ScopeAssignment_1_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5522:1: ( ( rule__GlobalInterruptible__ScopeAssignment_1_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5523:1: ( rule__GlobalInterruptible__ScopeAssignment_1_0 )
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getScopeAssignment_1_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5524:1: ( rule__GlobalInterruptible__ScopeAssignment_1_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5524:2: rule__GlobalInterruptible__ScopeAssignment_1_0
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__ScopeAssignment_1_0_in_rule__GlobalInterruptible__Group_1__0__Impl10995);
            rule__GlobalInterruptible__ScopeAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptibleAccess().getScopeAssignment_1_0()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group_1__0__Impl"


    // $ANTLR start "rule__GlobalInterruptible__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5534:1: rule__GlobalInterruptible__Group_1__1 : rule__GlobalInterruptible__Group_1__1__Impl ;
    public final void rule__GlobalInterruptible__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5538:1: ( rule__GlobalInterruptible__Group_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5539:2: rule__GlobalInterruptible__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalInterruptible__Group_1__1__Impl_in_rule__GlobalInterruptible__Group_1__111025);
            rule__GlobalInterruptible__Group_1__1__Impl();

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
    // $ANTLR end "rule__GlobalInterruptible__Group_1__1"


    // $ANTLR start "rule__GlobalInterruptible__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5545:1: rule__GlobalInterruptible__Group_1__1__Impl : ( ':' ) ;
    public final void rule__GlobalInterruptible__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5549:1: ( ( ':' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5550:1: ( ':' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5550:1: ( ':' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5551:1: ':'
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getColonKeyword_1_1()); 
            match(input,23,FOLLOW_23_in_rule__GlobalInterruptible__Group_1__1__Impl11053); 
             after(grammarAccess.getGlobalInterruptibleAccess().getColonKeyword_1_1()); 

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
    // $ANTLR end "rule__GlobalInterruptible__Group_1__1__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5568:1: rule__GlobalInterrupt__Group__0 : rule__GlobalInterrupt__Group__0__Impl rule__GlobalInterrupt__Group__1 ;
    public final void rule__GlobalInterrupt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5572:1: ( rule__GlobalInterrupt__Group__0__Impl rule__GlobalInterrupt__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5573:2: rule__GlobalInterrupt__Group__0__Impl rule__GlobalInterrupt__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__0__Impl_in_rule__GlobalInterrupt__Group__011088);
            rule__GlobalInterrupt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__1_in_rule__GlobalInterrupt__Group__011091);
            rule__GlobalInterrupt__Group__1();

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
    // $ANTLR end "rule__GlobalInterrupt__Group__0"


    // $ANTLR start "rule__GlobalInterrupt__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5580:1: rule__GlobalInterrupt__Group__0__Impl : ( ( rule__GlobalInterrupt__MessagesAssignment_0 ) ) ;
    public final void rule__GlobalInterrupt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5584:1: ( ( ( rule__GlobalInterrupt__MessagesAssignment_0 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5585:1: ( ( rule__GlobalInterrupt__MessagesAssignment_0 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5585:1: ( ( rule__GlobalInterrupt__MessagesAssignment_0 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5586:1: ( rule__GlobalInterrupt__MessagesAssignment_0 )
            {
             before(grammarAccess.getGlobalInterruptAccess().getMessagesAssignment_0()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5587:1: ( rule__GlobalInterrupt__MessagesAssignment_0 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5587:2: rule__GlobalInterrupt__MessagesAssignment_0
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__MessagesAssignment_0_in_rule__GlobalInterrupt__Group__0__Impl11118);
            rule__GlobalInterrupt__MessagesAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptAccess().getMessagesAssignment_0()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group__0__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5597:1: rule__GlobalInterrupt__Group__1 : rule__GlobalInterrupt__Group__1__Impl rule__GlobalInterrupt__Group__2 ;
    public final void rule__GlobalInterrupt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5601:1: ( rule__GlobalInterrupt__Group__1__Impl rule__GlobalInterrupt__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5602:2: rule__GlobalInterrupt__Group__1__Impl rule__GlobalInterrupt__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__1__Impl_in_rule__GlobalInterrupt__Group__111148);
            rule__GlobalInterrupt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__2_in_rule__GlobalInterrupt__Group__111151);
            rule__GlobalInterrupt__Group__2();

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
    // $ANTLR end "rule__GlobalInterrupt__Group__1"


    // $ANTLR start "rule__GlobalInterrupt__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5609:1: rule__GlobalInterrupt__Group__1__Impl : ( ( rule__GlobalInterrupt__Group_1__0 )* ) ;
    public final void rule__GlobalInterrupt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5613:1: ( ( ( rule__GlobalInterrupt__Group_1__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5614:1: ( ( rule__GlobalInterrupt__Group_1__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5614:1: ( ( rule__GlobalInterrupt__Group_1__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5615:1: ( rule__GlobalInterrupt__Group_1__0 )*
            {
             before(grammarAccess.getGlobalInterruptAccess().getGroup_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5616:1: ( rule__GlobalInterrupt__Group_1__0 )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==22) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5616:2: rule__GlobalInterrupt__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalInterrupt__Group_1__0_in_rule__GlobalInterrupt__Group__1__Impl11178);
            	    rule__GlobalInterrupt__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

             after(grammarAccess.getGlobalInterruptAccess().getGroup_1()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group__1__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5626:1: rule__GlobalInterrupt__Group__2 : rule__GlobalInterrupt__Group__2__Impl rule__GlobalInterrupt__Group__3 ;
    public final void rule__GlobalInterrupt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5630:1: ( rule__GlobalInterrupt__Group__2__Impl rule__GlobalInterrupt__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5631:2: rule__GlobalInterrupt__Group__2__Impl rule__GlobalInterrupt__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__2__Impl_in_rule__GlobalInterrupt__Group__211209);
            rule__GlobalInterrupt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__3_in_rule__GlobalInterrupt__Group__211212);
            rule__GlobalInterrupt__Group__3();

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
    // $ANTLR end "rule__GlobalInterrupt__Group__2"


    // $ANTLR start "rule__GlobalInterrupt__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5638:1: rule__GlobalInterrupt__Group__2__Impl : ( 'by' ) ;
    public final void rule__GlobalInterrupt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5642:1: ( ( 'by' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5643:1: ( 'by' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5643:1: ( 'by' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5644:1: 'by'
            {
             before(grammarAccess.getGlobalInterruptAccess().getByKeyword_2()); 
            match(input,41,FOLLOW_41_in_rule__GlobalInterrupt__Group__2__Impl11240); 
             after(grammarAccess.getGlobalInterruptAccess().getByKeyword_2()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group__2__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5657:1: rule__GlobalInterrupt__Group__3 : rule__GlobalInterrupt__Group__3__Impl rule__GlobalInterrupt__Group__4 ;
    public final void rule__GlobalInterrupt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5661:1: ( rule__GlobalInterrupt__Group__3__Impl rule__GlobalInterrupt__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5662:2: rule__GlobalInterrupt__Group__3__Impl rule__GlobalInterrupt__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__3__Impl_in_rule__GlobalInterrupt__Group__311271);
            rule__GlobalInterrupt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__4_in_rule__GlobalInterrupt__Group__311274);
            rule__GlobalInterrupt__Group__4();

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
    // $ANTLR end "rule__GlobalInterrupt__Group__3"


    // $ANTLR start "rule__GlobalInterrupt__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5669:1: rule__GlobalInterrupt__Group__3__Impl : ( ( rule__GlobalInterrupt__RoleAssignment_3 ) ) ;
    public final void rule__GlobalInterrupt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5673:1: ( ( ( rule__GlobalInterrupt__RoleAssignment_3 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5674:1: ( ( rule__GlobalInterrupt__RoleAssignment_3 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5674:1: ( ( rule__GlobalInterrupt__RoleAssignment_3 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5675:1: ( rule__GlobalInterrupt__RoleAssignment_3 )
            {
             before(grammarAccess.getGlobalInterruptAccess().getRoleAssignment_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5676:1: ( rule__GlobalInterrupt__RoleAssignment_3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5676:2: rule__GlobalInterrupt__RoleAssignment_3
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__RoleAssignment_3_in_rule__GlobalInterrupt__Group__3__Impl11301);
            rule__GlobalInterrupt__RoleAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptAccess().getRoleAssignment_3()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group__3__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5686:1: rule__GlobalInterrupt__Group__4 : rule__GlobalInterrupt__Group__4__Impl ;
    public final void rule__GlobalInterrupt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5690:1: ( rule__GlobalInterrupt__Group__4__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5691:2: rule__GlobalInterrupt__Group__4__Impl
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group__4__Impl_in_rule__GlobalInterrupt__Group__411331);
            rule__GlobalInterrupt__Group__4__Impl();

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
    // $ANTLR end "rule__GlobalInterrupt__Group__4"


    // $ANTLR start "rule__GlobalInterrupt__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5697:1: rule__GlobalInterrupt__Group__4__Impl : ( ';' ) ;
    public final void rule__GlobalInterrupt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5701:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5702:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5702:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5703:1: ';'
            {
             before(grammarAccess.getGlobalInterruptAccess().getSemicolonKeyword_4()); 
            match(input,12,FOLLOW_12_in_rule__GlobalInterrupt__Group__4__Impl11359); 
             after(grammarAccess.getGlobalInterruptAccess().getSemicolonKeyword_4()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group__4__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group_1__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5726:1: rule__GlobalInterrupt__Group_1__0 : rule__GlobalInterrupt__Group_1__0__Impl rule__GlobalInterrupt__Group_1__1 ;
    public final void rule__GlobalInterrupt__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5730:1: ( rule__GlobalInterrupt__Group_1__0__Impl rule__GlobalInterrupt__Group_1__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5731:2: rule__GlobalInterrupt__Group_1__0__Impl rule__GlobalInterrupt__Group_1__1
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group_1__0__Impl_in_rule__GlobalInterrupt__Group_1__011400);
            rule__GlobalInterrupt__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalInterrupt__Group_1__1_in_rule__GlobalInterrupt__Group_1__011403);
            rule__GlobalInterrupt__Group_1__1();

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
    // $ANTLR end "rule__GlobalInterrupt__Group_1__0"


    // $ANTLR start "rule__GlobalInterrupt__Group_1__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5738:1: rule__GlobalInterrupt__Group_1__0__Impl : ( ',' ) ;
    public final void rule__GlobalInterrupt__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5742:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5743:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5743:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5744:1: ','
            {
             before(grammarAccess.getGlobalInterruptAccess().getCommaKeyword_1_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalInterrupt__Group_1__0__Impl11431); 
             after(grammarAccess.getGlobalInterruptAccess().getCommaKeyword_1_0()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group_1__0__Impl"


    // $ANTLR start "rule__GlobalInterrupt__Group_1__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5757:1: rule__GlobalInterrupt__Group_1__1 : rule__GlobalInterrupt__Group_1__1__Impl ;
    public final void rule__GlobalInterrupt__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5761:1: ( rule__GlobalInterrupt__Group_1__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5762:2: rule__GlobalInterrupt__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__Group_1__1__Impl_in_rule__GlobalInterrupt__Group_1__111462);
            rule__GlobalInterrupt__Group_1__1__Impl();

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
    // $ANTLR end "rule__GlobalInterrupt__Group_1__1"


    // $ANTLR start "rule__GlobalInterrupt__Group_1__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5768:1: rule__GlobalInterrupt__Group_1__1__Impl : ( ( rule__GlobalInterrupt__MessagesAssignment_1_1 ) ) ;
    public final void rule__GlobalInterrupt__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5772:1: ( ( ( rule__GlobalInterrupt__MessagesAssignment_1_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5773:1: ( ( rule__GlobalInterrupt__MessagesAssignment_1_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5773:1: ( ( rule__GlobalInterrupt__MessagesAssignment_1_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5774:1: ( rule__GlobalInterrupt__MessagesAssignment_1_1 )
            {
             before(grammarAccess.getGlobalInterruptAccess().getMessagesAssignment_1_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5775:1: ( rule__GlobalInterrupt__MessagesAssignment_1_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5775:2: rule__GlobalInterrupt__MessagesAssignment_1_1
            {
            pushFollow(FOLLOW_rule__GlobalInterrupt__MessagesAssignment_1_1_in_rule__GlobalInterrupt__Group_1__1__Impl11489);
            rule__GlobalInterrupt__MessagesAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalInterruptAccess().getMessagesAssignment_1_1()); 

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
    // $ANTLR end "rule__GlobalInterrupt__Group_1__1__Impl"


    // $ANTLR start "rule__GlobalDo__Group__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5789:1: rule__GlobalDo__Group__0 : rule__GlobalDo__Group__0__Impl rule__GlobalDo__Group__1 ;
    public final void rule__GlobalDo__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5793:1: ( rule__GlobalDo__Group__0__Impl rule__GlobalDo__Group__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5794:2: rule__GlobalDo__Group__0__Impl rule__GlobalDo__Group__1
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__0__Impl_in_rule__GlobalDo__Group__011523);
            rule__GlobalDo__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__1_in_rule__GlobalDo__Group__011526);
            rule__GlobalDo__Group__1();

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
    // $ANTLR end "rule__GlobalDo__Group__0"


    // $ANTLR start "rule__GlobalDo__Group__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5801:1: rule__GlobalDo__Group__0__Impl : ( 'do' ) ;
    public final void rule__GlobalDo__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5805:1: ( ( 'do' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5806:1: ( 'do' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5806:1: ( 'do' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5807:1: 'do'
            {
             before(grammarAccess.getGlobalDoAccess().getDoKeyword_0()); 
            match(input,42,FOLLOW_42_in_rule__GlobalDo__Group__0__Impl11554); 
             after(grammarAccess.getGlobalDoAccess().getDoKeyword_0()); 

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
    // $ANTLR end "rule__GlobalDo__Group__0__Impl"


    // $ANTLR start "rule__GlobalDo__Group__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5820:1: rule__GlobalDo__Group__1 : rule__GlobalDo__Group__1__Impl rule__GlobalDo__Group__2 ;
    public final void rule__GlobalDo__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5824:1: ( rule__GlobalDo__Group__1__Impl rule__GlobalDo__Group__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5825:2: rule__GlobalDo__Group__1__Impl rule__GlobalDo__Group__2
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__1__Impl_in_rule__GlobalDo__Group__111585);
            rule__GlobalDo__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__2_in_rule__GlobalDo__Group__111588);
            rule__GlobalDo__Group__2();

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
    // $ANTLR end "rule__GlobalDo__Group__1"


    // $ANTLR start "rule__GlobalDo__Group__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5832:1: rule__GlobalDo__Group__1__Impl : ( ( rule__GlobalDo__ModuleAssignment_1 ) ) ;
    public final void rule__GlobalDo__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5836:1: ( ( ( rule__GlobalDo__ModuleAssignment_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5837:1: ( ( rule__GlobalDo__ModuleAssignment_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5837:1: ( ( rule__GlobalDo__ModuleAssignment_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5838:1: ( rule__GlobalDo__ModuleAssignment_1 )
            {
             before(grammarAccess.getGlobalDoAccess().getModuleAssignment_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5839:1: ( rule__GlobalDo__ModuleAssignment_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5839:2: rule__GlobalDo__ModuleAssignment_1
            {
            pushFollow(FOLLOW_rule__GlobalDo__ModuleAssignment_1_in_rule__GlobalDo__Group__1__Impl11615);
            rule__GlobalDo__ModuleAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getModuleAssignment_1()); 

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
    // $ANTLR end "rule__GlobalDo__Group__1__Impl"


    // $ANTLR start "rule__GlobalDo__Group__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5849:1: rule__GlobalDo__Group__2 : rule__GlobalDo__Group__2__Impl rule__GlobalDo__Group__3 ;
    public final void rule__GlobalDo__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5853:1: ( rule__GlobalDo__Group__2__Impl rule__GlobalDo__Group__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5854:2: rule__GlobalDo__Group__2__Impl rule__GlobalDo__Group__3
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__2__Impl_in_rule__GlobalDo__Group__211645);
            rule__GlobalDo__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__3_in_rule__GlobalDo__Group__211648);
            rule__GlobalDo__Group__3();

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
    // $ANTLR end "rule__GlobalDo__Group__2"


    // $ANTLR start "rule__GlobalDo__Group__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5861:1: rule__GlobalDo__Group__2__Impl : ( ( rule__GlobalDo__Group_2__0 )? ) ;
    public final void rule__GlobalDo__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5865:1: ( ( ( rule__GlobalDo__Group_2__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5866:1: ( ( rule__GlobalDo__Group_2__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5866:1: ( ( rule__GlobalDo__Group_2__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5867:1: ( rule__GlobalDo__Group_2__0 )?
            {
             before(grammarAccess.getGlobalDoAccess().getGroup_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5868:1: ( rule__GlobalDo__Group_2__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==23) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5868:2: rule__GlobalDo__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__GlobalDo__Group_2__0_in_rule__GlobalDo__Group__2__Impl11675);
                    rule__GlobalDo__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGlobalDoAccess().getGroup_2()); 

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
    // $ANTLR end "rule__GlobalDo__Group__2__Impl"


    // $ANTLR start "rule__GlobalDo__Group__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5878:1: rule__GlobalDo__Group__3 : rule__GlobalDo__Group__3__Impl rule__GlobalDo__Group__4 ;
    public final void rule__GlobalDo__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5882:1: ( rule__GlobalDo__Group__3__Impl rule__GlobalDo__Group__4 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5883:2: rule__GlobalDo__Group__3__Impl rule__GlobalDo__Group__4
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__3__Impl_in_rule__GlobalDo__Group__311706);
            rule__GlobalDo__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__4_in_rule__GlobalDo__Group__311709);
            rule__GlobalDo__Group__4();

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
    // $ANTLR end "rule__GlobalDo__Group__3"


    // $ANTLR start "rule__GlobalDo__Group__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5890:1: rule__GlobalDo__Group__3__Impl : ( ( rule__GlobalDo__Group_3__0 )? ) ;
    public final void rule__GlobalDo__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5894:1: ( ( ( rule__GlobalDo__Group_3__0 )? ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5895:1: ( ( rule__GlobalDo__Group_3__0 )? )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5895:1: ( ( rule__GlobalDo__Group_3__0 )? )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5896:1: ( rule__GlobalDo__Group_3__0 )?
            {
             before(grammarAccess.getGlobalDoAccess().getGroup_3()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5897:1: ( rule__GlobalDo__Group_3__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==18) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5897:2: rule__GlobalDo__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__GlobalDo__Group_3__0_in_rule__GlobalDo__Group__3__Impl11736);
                    rule__GlobalDo__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGlobalDoAccess().getGroup_3()); 

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
    // $ANTLR end "rule__GlobalDo__Group__3__Impl"


    // $ANTLR start "rule__GlobalDo__Group__4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5907:1: rule__GlobalDo__Group__4 : rule__GlobalDo__Group__4__Impl rule__GlobalDo__Group__5 ;
    public final void rule__GlobalDo__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5911:1: ( rule__GlobalDo__Group__4__Impl rule__GlobalDo__Group__5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5912:2: rule__GlobalDo__Group__4__Impl rule__GlobalDo__Group__5
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__4__Impl_in_rule__GlobalDo__Group__411767);
            rule__GlobalDo__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__5_in_rule__GlobalDo__Group__411770);
            rule__GlobalDo__Group__5();

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
    // $ANTLR end "rule__GlobalDo__Group__4"


    // $ANTLR start "rule__GlobalDo__Group__4__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5919:1: rule__GlobalDo__Group__4__Impl : ( '(' ) ;
    public final void rule__GlobalDo__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5923:1: ( ( '(' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5924:1: ( '(' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5924:1: ( '(' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5925:1: '('
            {
             before(grammarAccess.getGlobalDoAccess().getLeftParenthesisKeyword_4()); 
            match(input,20,FOLLOW_20_in_rule__GlobalDo__Group__4__Impl11798); 
             after(grammarAccess.getGlobalDoAccess().getLeftParenthesisKeyword_4()); 

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
    // $ANTLR end "rule__GlobalDo__Group__4__Impl"


    // $ANTLR start "rule__GlobalDo__Group__5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5938:1: rule__GlobalDo__Group__5 : rule__GlobalDo__Group__5__Impl rule__GlobalDo__Group__6 ;
    public final void rule__GlobalDo__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5942:1: ( rule__GlobalDo__Group__5__Impl rule__GlobalDo__Group__6 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5943:2: rule__GlobalDo__Group__5__Impl rule__GlobalDo__Group__6
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__5__Impl_in_rule__GlobalDo__Group__511829);
            rule__GlobalDo__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__6_in_rule__GlobalDo__Group__511832);
            rule__GlobalDo__Group__6();

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
    // $ANTLR end "rule__GlobalDo__Group__5"


    // $ANTLR start "rule__GlobalDo__Group__5__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5950:1: rule__GlobalDo__Group__5__Impl : ( ( rule__GlobalDo__RolesAssignment_5 ) ) ;
    public final void rule__GlobalDo__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5954:1: ( ( ( rule__GlobalDo__RolesAssignment_5 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5955:1: ( ( rule__GlobalDo__RolesAssignment_5 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5955:1: ( ( rule__GlobalDo__RolesAssignment_5 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5956:1: ( rule__GlobalDo__RolesAssignment_5 )
            {
             before(grammarAccess.getGlobalDoAccess().getRolesAssignment_5()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5957:1: ( rule__GlobalDo__RolesAssignment_5 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5957:2: rule__GlobalDo__RolesAssignment_5
            {
            pushFollow(FOLLOW_rule__GlobalDo__RolesAssignment_5_in_rule__GlobalDo__Group__5__Impl11859);
            rule__GlobalDo__RolesAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getRolesAssignment_5()); 

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
    // $ANTLR end "rule__GlobalDo__Group__5__Impl"


    // $ANTLR start "rule__GlobalDo__Group__6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5967:1: rule__GlobalDo__Group__6 : rule__GlobalDo__Group__6__Impl rule__GlobalDo__Group__7 ;
    public final void rule__GlobalDo__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5971:1: ( rule__GlobalDo__Group__6__Impl rule__GlobalDo__Group__7 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5972:2: rule__GlobalDo__Group__6__Impl rule__GlobalDo__Group__7
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__6__Impl_in_rule__GlobalDo__Group__611889);
            rule__GlobalDo__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__7_in_rule__GlobalDo__Group__611892);
            rule__GlobalDo__Group__7();

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
    // $ANTLR end "rule__GlobalDo__Group__6"


    // $ANTLR start "rule__GlobalDo__Group__6__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5979:1: rule__GlobalDo__Group__6__Impl : ( ( rule__GlobalDo__Group_6__0 )* ) ;
    public final void rule__GlobalDo__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5983:1: ( ( ( rule__GlobalDo__Group_6__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5984:1: ( ( rule__GlobalDo__Group_6__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5984:1: ( ( rule__GlobalDo__Group_6__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5985:1: ( rule__GlobalDo__Group_6__0 )*
            {
             before(grammarAccess.getGlobalDoAccess().getGroup_6()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5986:1: ( rule__GlobalDo__Group_6__0 )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==22) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5986:2: rule__GlobalDo__Group_6__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalDo__Group_6__0_in_rule__GlobalDo__Group__6__Impl11919);
            	    rule__GlobalDo__Group_6__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

             after(grammarAccess.getGlobalDoAccess().getGroup_6()); 

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
    // $ANTLR end "rule__GlobalDo__Group__6__Impl"


    // $ANTLR start "rule__GlobalDo__Group__7"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:5996:1: rule__GlobalDo__Group__7 : rule__GlobalDo__Group__7__Impl rule__GlobalDo__Group__8 ;
    public final void rule__GlobalDo__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6000:1: ( rule__GlobalDo__Group__7__Impl rule__GlobalDo__Group__8 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6001:2: rule__GlobalDo__Group__7__Impl rule__GlobalDo__Group__8
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__7__Impl_in_rule__GlobalDo__Group__711950);
            rule__GlobalDo__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group__8_in_rule__GlobalDo__Group__711953);
            rule__GlobalDo__Group__8();

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
    // $ANTLR end "rule__GlobalDo__Group__7"


    // $ANTLR start "rule__GlobalDo__Group__7__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6008:1: rule__GlobalDo__Group__7__Impl : ( ')' ) ;
    public final void rule__GlobalDo__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6012:1: ( ( ')' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6013:1: ( ')' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6013:1: ( ')' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6014:1: ')'
            {
             before(grammarAccess.getGlobalDoAccess().getRightParenthesisKeyword_7()); 
            match(input,21,FOLLOW_21_in_rule__GlobalDo__Group__7__Impl11981); 
             after(grammarAccess.getGlobalDoAccess().getRightParenthesisKeyword_7()); 

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
    // $ANTLR end "rule__GlobalDo__Group__7__Impl"


    // $ANTLR start "rule__GlobalDo__Group__8"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6027:1: rule__GlobalDo__Group__8 : rule__GlobalDo__Group__8__Impl ;
    public final void rule__GlobalDo__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6031:1: ( rule__GlobalDo__Group__8__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6032:2: rule__GlobalDo__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group__8__Impl_in_rule__GlobalDo__Group__812012);
            rule__GlobalDo__Group__8__Impl();

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
    // $ANTLR end "rule__GlobalDo__Group__8"


    // $ANTLR start "rule__GlobalDo__Group__8__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6038:1: rule__GlobalDo__Group__8__Impl : ( ';' ) ;
    public final void rule__GlobalDo__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6042:1: ( ( ';' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6043:1: ( ';' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6043:1: ( ';' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6044:1: ';'
            {
             before(grammarAccess.getGlobalDoAccess().getSemicolonKeyword_8()); 
            match(input,12,FOLLOW_12_in_rule__GlobalDo__Group__8__Impl12040); 
             after(grammarAccess.getGlobalDoAccess().getSemicolonKeyword_8()); 

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
    // $ANTLR end "rule__GlobalDo__Group__8__Impl"


    // $ANTLR start "rule__GlobalDo__Group_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6075:1: rule__GlobalDo__Group_2__0 : rule__GlobalDo__Group_2__0__Impl rule__GlobalDo__Group_2__1 ;
    public final void rule__GlobalDo__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6079:1: ( rule__GlobalDo__Group_2__0__Impl rule__GlobalDo__Group_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6080:2: rule__GlobalDo__Group_2__0__Impl rule__GlobalDo__Group_2__1
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_2__0__Impl_in_rule__GlobalDo__Group_2__012089);
            rule__GlobalDo__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_2__1_in_rule__GlobalDo__Group_2__012092);
            rule__GlobalDo__Group_2__1();

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
    // $ANTLR end "rule__GlobalDo__Group_2__0"


    // $ANTLR start "rule__GlobalDo__Group_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6087:1: rule__GlobalDo__Group_2__0__Impl : ( ':' ) ;
    public final void rule__GlobalDo__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6091:1: ( ( ':' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6092:1: ( ':' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6092:1: ( ':' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6093:1: ':'
            {
             before(grammarAccess.getGlobalDoAccess().getColonKeyword_2_0()); 
            match(input,23,FOLLOW_23_in_rule__GlobalDo__Group_2__0__Impl12120); 
             after(grammarAccess.getGlobalDoAccess().getColonKeyword_2_0()); 

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
    // $ANTLR end "rule__GlobalDo__Group_2__0__Impl"


    // $ANTLR start "rule__GlobalDo__Group_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6106:1: rule__GlobalDo__Group_2__1 : rule__GlobalDo__Group_2__1__Impl ;
    public final void rule__GlobalDo__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6110:1: ( rule__GlobalDo__Group_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6111:2: rule__GlobalDo__Group_2__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_2__1__Impl_in_rule__GlobalDo__Group_2__112151);
            rule__GlobalDo__Group_2__1__Impl();

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
    // $ANTLR end "rule__GlobalDo__Group_2__1"


    // $ANTLR start "rule__GlobalDo__Group_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6117:1: rule__GlobalDo__Group_2__1__Impl : ( ( rule__GlobalDo__ScopeAssignment_2_1 ) ) ;
    public final void rule__GlobalDo__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6121:1: ( ( ( rule__GlobalDo__ScopeAssignment_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6122:1: ( ( rule__GlobalDo__ScopeAssignment_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6122:1: ( ( rule__GlobalDo__ScopeAssignment_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6123:1: ( rule__GlobalDo__ScopeAssignment_2_1 )
            {
             before(grammarAccess.getGlobalDoAccess().getScopeAssignment_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6124:1: ( rule__GlobalDo__ScopeAssignment_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6124:2: rule__GlobalDo__ScopeAssignment_2_1
            {
            pushFollow(FOLLOW_rule__GlobalDo__ScopeAssignment_2_1_in_rule__GlobalDo__Group_2__1__Impl12178);
            rule__GlobalDo__ScopeAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getScopeAssignment_2_1()); 

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
    // $ANTLR end "rule__GlobalDo__Group_2__1__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6138:1: rule__GlobalDo__Group_3__0 : rule__GlobalDo__Group_3__0__Impl rule__GlobalDo__Group_3__1 ;
    public final void rule__GlobalDo__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6142:1: ( rule__GlobalDo__Group_3__0__Impl rule__GlobalDo__Group_3__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6143:2: rule__GlobalDo__Group_3__0__Impl rule__GlobalDo__Group_3__1
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3__0__Impl_in_rule__GlobalDo__Group_3__012212);
            rule__GlobalDo__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_3__1_in_rule__GlobalDo__Group_3__012215);
            rule__GlobalDo__Group_3__1();

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
    // $ANTLR end "rule__GlobalDo__Group_3__0"


    // $ANTLR start "rule__GlobalDo__Group_3__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6150:1: rule__GlobalDo__Group_3__0__Impl : ( '<' ) ;
    public final void rule__GlobalDo__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6154:1: ( ( '<' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6155:1: ( '<' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6155:1: ( '<' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6156:1: '<'
            {
             before(grammarAccess.getGlobalDoAccess().getLessThanSignKeyword_3_0()); 
            match(input,18,FOLLOW_18_in_rule__GlobalDo__Group_3__0__Impl12243); 
             after(grammarAccess.getGlobalDoAccess().getLessThanSignKeyword_3_0()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3__0__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6169:1: rule__GlobalDo__Group_3__1 : rule__GlobalDo__Group_3__1__Impl rule__GlobalDo__Group_3__2 ;
    public final void rule__GlobalDo__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6173:1: ( rule__GlobalDo__Group_3__1__Impl rule__GlobalDo__Group_3__2 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6174:2: rule__GlobalDo__Group_3__1__Impl rule__GlobalDo__Group_3__2
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3__1__Impl_in_rule__GlobalDo__Group_3__112274);
            rule__GlobalDo__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_3__2_in_rule__GlobalDo__Group_3__112277);
            rule__GlobalDo__Group_3__2();

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
    // $ANTLR end "rule__GlobalDo__Group_3__1"


    // $ANTLR start "rule__GlobalDo__Group_3__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6181:1: rule__GlobalDo__Group_3__1__Impl : ( ( rule__GlobalDo__ArgumentsAssignment_3_1 ) ) ;
    public final void rule__GlobalDo__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6185:1: ( ( ( rule__GlobalDo__ArgumentsAssignment_3_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6186:1: ( ( rule__GlobalDo__ArgumentsAssignment_3_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6186:1: ( ( rule__GlobalDo__ArgumentsAssignment_3_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6187:1: ( rule__GlobalDo__ArgumentsAssignment_3_1 )
            {
             before(grammarAccess.getGlobalDoAccess().getArgumentsAssignment_3_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6188:1: ( rule__GlobalDo__ArgumentsAssignment_3_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6188:2: rule__GlobalDo__ArgumentsAssignment_3_1
            {
            pushFollow(FOLLOW_rule__GlobalDo__ArgumentsAssignment_3_1_in_rule__GlobalDo__Group_3__1__Impl12304);
            rule__GlobalDo__ArgumentsAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getArgumentsAssignment_3_1()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3__1__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3__2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6198:1: rule__GlobalDo__Group_3__2 : rule__GlobalDo__Group_3__2__Impl rule__GlobalDo__Group_3__3 ;
    public final void rule__GlobalDo__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6202:1: ( rule__GlobalDo__Group_3__2__Impl rule__GlobalDo__Group_3__3 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6203:2: rule__GlobalDo__Group_3__2__Impl rule__GlobalDo__Group_3__3
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3__2__Impl_in_rule__GlobalDo__Group_3__212334);
            rule__GlobalDo__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_3__3_in_rule__GlobalDo__Group_3__212337);
            rule__GlobalDo__Group_3__3();

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
    // $ANTLR end "rule__GlobalDo__Group_3__2"


    // $ANTLR start "rule__GlobalDo__Group_3__2__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6210:1: rule__GlobalDo__Group_3__2__Impl : ( ( rule__GlobalDo__Group_3_2__0 )* ) ;
    public final void rule__GlobalDo__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6214:1: ( ( ( rule__GlobalDo__Group_3_2__0 )* ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6215:1: ( ( rule__GlobalDo__Group_3_2__0 )* )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6215:1: ( ( rule__GlobalDo__Group_3_2__0 )* )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6216:1: ( rule__GlobalDo__Group_3_2__0 )*
            {
             before(grammarAccess.getGlobalDoAccess().getGroup_3_2()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6217:1: ( rule__GlobalDo__Group_3_2__0 )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==22) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6217:2: rule__GlobalDo__Group_3_2__0
            	    {
            	    pushFollow(FOLLOW_rule__GlobalDo__Group_3_2__0_in_rule__GlobalDo__Group_3__2__Impl12364);
            	    rule__GlobalDo__Group_3_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

             after(grammarAccess.getGlobalDoAccess().getGroup_3_2()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3__2__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3__3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6227:1: rule__GlobalDo__Group_3__3 : rule__GlobalDo__Group_3__3__Impl ;
    public final void rule__GlobalDo__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6231:1: ( rule__GlobalDo__Group_3__3__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6232:2: rule__GlobalDo__Group_3__3__Impl
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3__3__Impl_in_rule__GlobalDo__Group_3__312395);
            rule__GlobalDo__Group_3__3__Impl();

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
    // $ANTLR end "rule__GlobalDo__Group_3__3"


    // $ANTLR start "rule__GlobalDo__Group_3__3__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6238:1: rule__GlobalDo__Group_3__3__Impl : ( '>' ) ;
    public final void rule__GlobalDo__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6242:1: ( ( '>' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6243:1: ( '>' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6243:1: ( '>' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6244:1: '>'
            {
             before(grammarAccess.getGlobalDoAccess().getGreaterThanSignKeyword_3_3()); 
            match(input,19,FOLLOW_19_in_rule__GlobalDo__Group_3__3__Impl12423); 
             after(grammarAccess.getGlobalDoAccess().getGreaterThanSignKeyword_3_3()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3__3__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3_2__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6265:1: rule__GlobalDo__Group_3_2__0 : rule__GlobalDo__Group_3_2__0__Impl rule__GlobalDo__Group_3_2__1 ;
    public final void rule__GlobalDo__Group_3_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6269:1: ( rule__GlobalDo__Group_3_2__0__Impl rule__GlobalDo__Group_3_2__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6270:2: rule__GlobalDo__Group_3_2__0__Impl rule__GlobalDo__Group_3_2__1
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3_2__0__Impl_in_rule__GlobalDo__Group_3_2__012462);
            rule__GlobalDo__Group_3_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_3_2__1_in_rule__GlobalDo__Group_3_2__012465);
            rule__GlobalDo__Group_3_2__1();

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
    // $ANTLR end "rule__GlobalDo__Group_3_2__0"


    // $ANTLR start "rule__GlobalDo__Group_3_2__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6277:1: rule__GlobalDo__Group_3_2__0__Impl : ( ',' ) ;
    public final void rule__GlobalDo__Group_3_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6281:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6282:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6282:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6283:1: ','
            {
             before(grammarAccess.getGlobalDoAccess().getCommaKeyword_3_2_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalDo__Group_3_2__0__Impl12493); 
             after(grammarAccess.getGlobalDoAccess().getCommaKeyword_3_2_0()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3_2__0__Impl"


    // $ANTLR start "rule__GlobalDo__Group_3_2__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6296:1: rule__GlobalDo__Group_3_2__1 : rule__GlobalDo__Group_3_2__1__Impl ;
    public final void rule__GlobalDo__Group_3_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6300:1: ( rule__GlobalDo__Group_3_2__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6301:2: rule__GlobalDo__Group_3_2__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_3_2__1__Impl_in_rule__GlobalDo__Group_3_2__112524);
            rule__GlobalDo__Group_3_2__1__Impl();

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
    // $ANTLR end "rule__GlobalDo__Group_3_2__1"


    // $ANTLR start "rule__GlobalDo__Group_3_2__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6307:1: rule__GlobalDo__Group_3_2__1__Impl : ( ( rule__GlobalDo__ArgumentsAssignment_3_2_1 ) ) ;
    public final void rule__GlobalDo__Group_3_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6311:1: ( ( ( rule__GlobalDo__ArgumentsAssignment_3_2_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6312:1: ( ( rule__GlobalDo__ArgumentsAssignment_3_2_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6312:1: ( ( rule__GlobalDo__ArgumentsAssignment_3_2_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6313:1: ( rule__GlobalDo__ArgumentsAssignment_3_2_1 )
            {
             before(grammarAccess.getGlobalDoAccess().getArgumentsAssignment_3_2_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6314:1: ( rule__GlobalDo__ArgumentsAssignment_3_2_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6314:2: rule__GlobalDo__ArgumentsAssignment_3_2_1
            {
            pushFollow(FOLLOW_rule__GlobalDo__ArgumentsAssignment_3_2_1_in_rule__GlobalDo__Group_3_2__1__Impl12551);
            rule__GlobalDo__ArgumentsAssignment_3_2_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getArgumentsAssignment_3_2_1()); 

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
    // $ANTLR end "rule__GlobalDo__Group_3_2__1__Impl"


    // $ANTLR start "rule__GlobalDo__Group_6__0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6328:1: rule__GlobalDo__Group_6__0 : rule__GlobalDo__Group_6__0__Impl rule__GlobalDo__Group_6__1 ;
    public final void rule__GlobalDo__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6332:1: ( rule__GlobalDo__Group_6__0__Impl rule__GlobalDo__Group_6__1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6333:2: rule__GlobalDo__Group_6__0__Impl rule__GlobalDo__Group_6__1
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_6__0__Impl_in_rule__GlobalDo__Group_6__012585);
            rule__GlobalDo__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__GlobalDo__Group_6__1_in_rule__GlobalDo__Group_6__012588);
            rule__GlobalDo__Group_6__1();

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
    // $ANTLR end "rule__GlobalDo__Group_6__0"


    // $ANTLR start "rule__GlobalDo__Group_6__0__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6340:1: rule__GlobalDo__Group_6__0__Impl : ( ',' ) ;
    public final void rule__GlobalDo__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6344:1: ( ( ',' ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6345:1: ( ',' )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6345:1: ( ',' )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6346:1: ','
            {
             before(grammarAccess.getGlobalDoAccess().getCommaKeyword_6_0()); 
            match(input,22,FOLLOW_22_in_rule__GlobalDo__Group_6__0__Impl12616); 
             after(grammarAccess.getGlobalDoAccess().getCommaKeyword_6_0()); 

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
    // $ANTLR end "rule__GlobalDo__Group_6__0__Impl"


    // $ANTLR start "rule__GlobalDo__Group_6__1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6359:1: rule__GlobalDo__Group_6__1 : rule__GlobalDo__Group_6__1__Impl ;
    public final void rule__GlobalDo__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6363:1: ( rule__GlobalDo__Group_6__1__Impl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6364:2: rule__GlobalDo__Group_6__1__Impl
            {
            pushFollow(FOLLOW_rule__GlobalDo__Group_6__1__Impl_in_rule__GlobalDo__Group_6__112647);
            rule__GlobalDo__Group_6__1__Impl();

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
    // $ANTLR end "rule__GlobalDo__Group_6__1"


    // $ANTLR start "rule__GlobalDo__Group_6__1__Impl"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6370:1: rule__GlobalDo__Group_6__1__Impl : ( ( rule__GlobalDo__RolesAssignment_6_1 ) ) ;
    public final void rule__GlobalDo__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6374:1: ( ( ( rule__GlobalDo__RolesAssignment_6_1 ) ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6375:1: ( ( rule__GlobalDo__RolesAssignment_6_1 ) )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6375:1: ( ( rule__GlobalDo__RolesAssignment_6_1 ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6376:1: ( rule__GlobalDo__RolesAssignment_6_1 )
            {
             before(grammarAccess.getGlobalDoAccess().getRolesAssignment_6_1()); 
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6377:1: ( rule__GlobalDo__RolesAssignment_6_1 )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6377:2: rule__GlobalDo__RolesAssignment_6_1
            {
            pushFollow(FOLLOW_rule__GlobalDo__RolesAssignment_6_1_in_rule__GlobalDo__Group_6__1__Impl12674);
            rule__GlobalDo__RolesAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getGlobalDoAccess().getRolesAssignment_6_1()); 

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
    // $ANTLR end "rule__GlobalDo__Group_6__1__Impl"


    // $ANTLR start "rule__Module__ImportsAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6392:1: rule__Module__ImportsAssignment_1 : ( ruleImportDecl ) ;
    public final void rule__Module__ImportsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6396:1: ( ( ruleImportDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6397:1: ( ruleImportDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6397:1: ( ruleImportDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6398:1: ruleImportDecl
            {
             before(grammarAccess.getModuleAccess().getImportsImportDeclParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleImportDecl_in_rule__Module__ImportsAssignment_112713);
            ruleImportDecl();

            state._fsp--;

             after(grammarAccess.getModuleAccess().getImportsImportDeclParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__Module__ImportsAssignment_1"


    // $ANTLR start "rule__Module__TypesAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6407:1: rule__Module__TypesAssignment_2 : ( rulePayloadTypeDecl ) ;
    public final void rule__Module__TypesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6411:1: ( ( rulePayloadTypeDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6412:1: ( rulePayloadTypeDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6412:1: ( rulePayloadTypeDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6413:1: rulePayloadTypeDecl
            {
             before(grammarAccess.getModuleAccess().getTypesPayloadTypeDeclParserRuleCall_2_0()); 
            pushFollow(FOLLOW_rulePayloadTypeDecl_in_rule__Module__TypesAssignment_212744);
            rulePayloadTypeDecl();

            state._fsp--;

             after(grammarAccess.getModuleAccess().getTypesPayloadTypeDeclParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__Module__TypesAssignment_2"


    // $ANTLR start "rule__Module__GlobalsAssignment_3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6422:1: rule__Module__GlobalsAssignment_3 : ( ruleGlobalProtocolDecl ) ;
    public final void rule__Module__GlobalsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6426:1: ( ( ruleGlobalProtocolDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6427:1: ( ruleGlobalProtocolDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6427:1: ( ruleGlobalProtocolDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6428:1: ruleGlobalProtocolDecl
            {
             before(grammarAccess.getModuleAccess().getGlobalsGlobalProtocolDeclParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolDecl_in_rule__Module__GlobalsAssignment_312775);
            ruleGlobalProtocolDecl();

            state._fsp--;

             after(grammarAccess.getModuleAccess().getGlobalsGlobalProtocolDeclParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__Module__GlobalsAssignment_3"


    // $ANTLR start "rule__ModuleDecl__NameAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6437:1: rule__ModuleDecl__NameAssignment_1 : ( ruleModuleName ) ;
    public final void rule__ModuleDecl__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6441:1: ( ( ruleModuleName ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6442:1: ( ruleModuleName )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6442:1: ( ruleModuleName )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6443:1: ruleModuleName
            {
             before(grammarAccess.getModuleDeclAccess().getNameModuleNameParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleName_in_rule__ModuleDecl__NameAssignment_112806);
            ruleModuleName();

            state._fsp--;

             after(grammarAccess.getModuleDeclAccess().getNameModuleNameParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__ModuleDecl__NameAssignment_1"


    // $ANTLR start "rule__ImportModule__NameAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6452:1: rule__ImportModule__NameAssignment_1 : ( ruleModuleName ) ;
    public final void rule__ImportModule__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6456:1: ( ( ruleModuleName ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6457:1: ( ruleModuleName )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6457:1: ( ruleModuleName )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6458:1: ruleModuleName
            {
             before(grammarAccess.getImportModuleAccess().getNameModuleNameParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleName_in_rule__ImportModule__NameAssignment_112837);
            ruleModuleName();

            state._fsp--;

             after(grammarAccess.getImportModuleAccess().getNameModuleNameParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__ImportModule__NameAssignment_1"


    // $ANTLR start "rule__ImportModule__AliasAssignment_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6467:1: rule__ImportModule__AliasAssignment_2_1 : ( RULE_ID ) ;
    public final void rule__ImportModule__AliasAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6471:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6472:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6472:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6473:1: RULE_ID
            {
             before(grammarAccess.getImportModuleAccess().getAliasIDTerminalRuleCall_2_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ImportModule__AliasAssignment_2_112868); 
             after(grammarAccess.getImportModuleAccess().getAliasIDTerminalRuleCall_2_1_0()); 

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
    // $ANTLR end "rule__ImportModule__AliasAssignment_2_1"


    // $ANTLR start "rule__ImportMember__NameAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6482:1: rule__ImportMember__NameAssignment_1 : ( ruleModuleName ) ;
    public final void rule__ImportMember__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6486:1: ( ( ruleModuleName ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6487:1: ( ruleModuleName )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6487:1: ( ruleModuleName )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6488:1: ruleModuleName
            {
             before(grammarAccess.getImportMemberAccess().getNameModuleNameParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleModuleName_in_rule__ImportMember__NameAssignment_112899);
            ruleModuleName();

            state._fsp--;

             after(grammarAccess.getImportMemberAccess().getNameModuleNameParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__ImportMember__NameAssignment_1"


    // $ANTLR start "rule__ImportMember__MemberAssignment_3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6497:1: rule__ImportMember__MemberAssignment_3 : ( RULE_ID ) ;
    public final void rule__ImportMember__MemberAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6501:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6502:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6502:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6503:1: RULE_ID
            {
             before(grammarAccess.getImportMemberAccess().getMemberIDTerminalRuleCall_3_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ImportMember__MemberAssignment_312930); 
             after(grammarAccess.getImportMemberAccess().getMemberIDTerminalRuleCall_3_0()); 

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
    // $ANTLR end "rule__ImportMember__MemberAssignment_3"


    // $ANTLR start "rule__ImportMember__AliasAssignment_4_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6512:1: rule__ImportMember__AliasAssignment_4_1 : ( RULE_ID ) ;
    public final void rule__ImportMember__AliasAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6516:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6517:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6517:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6518:1: RULE_ID
            {
             before(grammarAccess.getImportMemberAccess().getAliasIDTerminalRuleCall_4_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ImportMember__AliasAssignment_4_112961); 
             after(grammarAccess.getImportMemberAccess().getAliasIDTerminalRuleCall_4_1_0()); 

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
    // $ANTLR end "rule__ImportMember__AliasAssignment_4_1"


    // $ANTLR start "rule__PayloadTypeDecl__SchemaAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6527:1: rule__PayloadTypeDecl__SchemaAssignment_2 : ( RULE_ID ) ;
    public final void rule__PayloadTypeDecl__SchemaAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6531:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6532:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6532:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6533:1: RULE_ID
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getSchemaIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PayloadTypeDecl__SchemaAssignment_212992); 
             after(grammarAccess.getPayloadTypeDeclAccess().getSchemaIDTerminalRuleCall_2_0()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__SchemaAssignment_2"


    // $ANTLR start "rule__PayloadTypeDecl__TypeAssignment_4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6542:1: rule__PayloadTypeDecl__TypeAssignment_4 : ( RULE_STRING ) ;
    public final void rule__PayloadTypeDecl__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6546:1: ( ( RULE_STRING ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6547:1: ( RULE_STRING )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6547:1: ( RULE_STRING )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6548:1: RULE_STRING
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getTypeSTRINGTerminalRuleCall_4_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__PayloadTypeDecl__TypeAssignment_413023); 
             after(grammarAccess.getPayloadTypeDeclAccess().getTypeSTRINGTerminalRuleCall_4_0()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__TypeAssignment_4"


    // $ANTLR start "rule__PayloadTypeDecl__LocationAssignment_6"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6557:1: rule__PayloadTypeDecl__LocationAssignment_6 : ( RULE_STRING ) ;
    public final void rule__PayloadTypeDecl__LocationAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6561:1: ( ( RULE_STRING ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6562:1: ( RULE_STRING )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6562:1: ( RULE_STRING )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6563:1: RULE_STRING
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getLocationSTRINGTerminalRuleCall_6_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__PayloadTypeDecl__LocationAssignment_613054); 
             after(grammarAccess.getPayloadTypeDeclAccess().getLocationSTRINGTerminalRuleCall_6_0()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__LocationAssignment_6"


    // $ANTLR start "rule__PayloadTypeDecl__AliasAssignment_8"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6572:1: rule__PayloadTypeDecl__AliasAssignment_8 : ( RULE_ID ) ;
    public final void rule__PayloadTypeDecl__AliasAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6576:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6577:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6577:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6578:1: RULE_ID
            {
             before(grammarAccess.getPayloadTypeDeclAccess().getAliasIDTerminalRuleCall_8_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PayloadTypeDecl__AliasAssignment_813085); 
             after(grammarAccess.getPayloadTypeDeclAccess().getAliasIDTerminalRuleCall_8_0()); 

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
    // $ANTLR end "rule__PayloadTypeDecl__AliasAssignment_8"


    // $ANTLR start "rule__MessageSignature__OperatorAssignment_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6587:1: rule__MessageSignature__OperatorAssignment_0 : ( RULE_ID ) ;
    public final void rule__MessageSignature__OperatorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6591:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6592:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6592:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6593:1: RULE_ID
            {
             before(grammarAccess.getMessageSignatureAccess().getOperatorIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__MessageSignature__OperatorAssignment_013116); 
             after(grammarAccess.getMessageSignatureAccess().getOperatorIDTerminalRuleCall_0_0()); 

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
    // $ANTLR end "rule__MessageSignature__OperatorAssignment_0"


    // $ANTLR start "rule__MessageSignature__TypesAssignment_2_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6602:1: rule__MessageSignature__TypesAssignment_2_0 : ( rulePayloadElement ) ;
    public final void rule__MessageSignature__TypesAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6606:1: ( ( rulePayloadElement ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6607:1: ( rulePayloadElement )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6607:1: ( rulePayloadElement )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6608:1: rulePayloadElement
            {
             before(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_0_0()); 
            pushFollow(FOLLOW_rulePayloadElement_in_rule__MessageSignature__TypesAssignment_2_013147);
            rulePayloadElement();

            state._fsp--;

             after(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_0_0()); 

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
    // $ANTLR end "rule__MessageSignature__TypesAssignment_2_0"


    // $ANTLR start "rule__MessageSignature__TypesAssignment_2_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6617:1: rule__MessageSignature__TypesAssignment_2_1_1 : ( rulePayloadElement ) ;
    public final void rule__MessageSignature__TypesAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6621:1: ( ( rulePayloadElement ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6622:1: ( rulePayloadElement )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6622:1: ( rulePayloadElement )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6623:1: rulePayloadElement
            {
             before(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_rulePayloadElement_in_rule__MessageSignature__TypesAssignment_2_1_113178);
            rulePayloadElement();

            state._fsp--;

             after(grammarAccess.getMessageSignatureAccess().getTypesPayloadElementParserRuleCall_2_1_1_0()); 

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
    // $ANTLR end "rule__MessageSignature__TypesAssignment_2_1_1"


    // $ANTLR start "rule__PayloadElement__NameAssignment_0_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6632:1: rule__PayloadElement__NameAssignment_0_0 : ( RULE_ID ) ;
    public final void rule__PayloadElement__NameAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6636:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6637:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6637:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6638:1: RULE_ID
            {
             before(grammarAccess.getPayloadElementAccess().getNameIDTerminalRuleCall_0_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PayloadElement__NameAssignment_0_013209); 
             after(grammarAccess.getPayloadElementAccess().getNameIDTerminalRuleCall_0_0_0()); 

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
    // $ANTLR end "rule__PayloadElement__NameAssignment_0_0"


    // $ANTLR start "rule__PayloadElement__TypeAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6647:1: rule__PayloadElement__TypeAssignment_1 : ( RULE_ID ) ;
    public final void rule__PayloadElement__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6651:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6652:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6652:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6653:1: RULE_ID
            {
             before(grammarAccess.getPayloadElementAccess().getTypeIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__PayloadElement__TypeAssignment_113240); 
             after(grammarAccess.getPayloadElementAccess().getTypeIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__PayloadElement__TypeAssignment_1"


    // $ANTLR start "rule__GlobalProtocolDecl__NameAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6662:1: rule__GlobalProtocolDecl__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__GlobalProtocolDecl__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6666:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6667:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6667:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6668:1: RULE_ID
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalProtocolDecl__NameAssignment_213271); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getNameIDTerminalRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__NameAssignment_2"


    // $ANTLR start "rule__GlobalProtocolDecl__ParametersAssignment_3_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6677:1: rule__GlobalProtocolDecl__ParametersAssignment_3_1 : ( ruleParameterDecl ) ;
    public final void rule__GlobalProtocolDecl__ParametersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6681:1: ( ( ruleParameterDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6682:1: ( ruleParameterDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6682:1: ( ruleParameterDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6683:1: ruleParameterDecl
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_rule__GlobalProtocolDecl__ParametersAssignment_3_113302);
            ruleParameterDecl();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__ParametersAssignment_3_1"


    // $ANTLR start "rule__GlobalProtocolDecl__ParametersAssignment_3_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6692:1: rule__GlobalProtocolDecl__ParametersAssignment_3_2_1 : ( ruleParameterDecl ) ;
    public final void rule__GlobalProtocolDecl__ParametersAssignment_3_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6696:1: ( ( ruleParameterDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6697:1: ( ruleParameterDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6697:1: ( ruleParameterDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6698:1: ruleParameterDecl
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_2_1_0()); 
            pushFollow(FOLLOW_ruleParameterDecl_in_rule__GlobalProtocolDecl__ParametersAssignment_3_2_113333);
            ruleParameterDecl();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getParametersParameterDeclParserRuleCall_3_2_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__ParametersAssignment_3_2_1"


    // $ANTLR start "rule__GlobalProtocolDecl__RolesAssignment_5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6707:1: rule__GlobalProtocolDecl__RolesAssignment_5 : ( ruleRoleDecl ) ;
    public final void rule__GlobalProtocolDecl__RolesAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6711:1: ( ( ruleRoleDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6712:1: ( ruleRoleDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6712:1: ( ruleRoleDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6713:1: ruleRoleDecl
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_5_0()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_rule__GlobalProtocolDecl__RolesAssignment_513364);
            ruleRoleDecl();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_5_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__RolesAssignment_5"


    // $ANTLR start "rule__GlobalProtocolDecl__RolesAssignment_6_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6722:1: rule__GlobalProtocolDecl__RolesAssignment_6_1 : ( ruleRoleDecl ) ;
    public final void rule__GlobalProtocolDecl__RolesAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6726:1: ( ( ruleRoleDecl ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6727:1: ( ruleRoleDecl )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6727:1: ( ruleRoleDecl )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6728:1: ruleRoleDecl
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_ruleRoleDecl_in_rule__GlobalProtocolDecl__RolesAssignment_6_113395);
            ruleRoleDecl();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getRolesRoleDeclParserRuleCall_6_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__RolesAssignment_6_1"


    // $ANTLR start "rule__GlobalProtocolDecl__BlockAssignment_8_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6737:1: rule__GlobalProtocolDecl__BlockAssignment_8_0 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalProtocolDecl__BlockAssignment_8_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6741:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6742:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6742:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6743:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getBlockGlobalProtocolBlockParserRuleCall_8_0_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalProtocolDecl__BlockAssignment_8_013426);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getBlockGlobalProtocolBlockParserRuleCall_8_0_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__BlockAssignment_8_0"


    // $ANTLR start "rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6752:1: rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1 : ( RULE_ID ) ;
    public final void rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6756:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6757:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6757:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6758:1: RULE_ID
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_8_1_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_113457); 
             after(grammarAccess.getGlobalProtocolDeclAccess().getInstantiatesIDTerminalRuleCall_8_1_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1"


    // $ANTLR start "rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6767:1: rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1 : ( ruleArgument ) ;
    public final void rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6771:1: ( ( ruleArgument ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6772:1: ( ruleArgument )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6772:1: ( ruleArgument )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6773:1: ruleArgument
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_1_0()); 
            pushFollow(FOLLOW_ruleArgument_in_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_113488);
            ruleArgument();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1"


    // $ANTLR start "rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6782:1: rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1 : ( ruleArgument ) ;
    public final void rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6786:1: ( ( ruleArgument ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6787:1: ( ruleArgument )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6787:1: ( ruleArgument )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6788:1: ruleArgument
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_2_1_0()); 
            pushFollow(FOLLOW_ruleArgument_in_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_113519);
            ruleArgument();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getArgumentsArgumentParserRuleCall_8_1_2_2_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1"


    // $ANTLR start "rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6797:1: rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4 : ( ruleRoleInstantiation ) ;
    public final void rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6801:1: ( ( ruleRoleInstantiation ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6802:1: ( ruleRoleInstantiation )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6802:1: ( ruleRoleInstantiation )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6803:1: ruleRoleInstantiation
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_4_0()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_413550);
            ruleRoleInstantiation();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_4_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4"


    // $ANTLR start "rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6812:1: rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1 : ( ruleRoleInstantiation ) ;
    public final void rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6816:1: ( ( ruleRoleInstantiation ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6817:1: ( ruleRoleInstantiation )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6817:1: ( ruleRoleInstantiation )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6818:1: ruleRoleInstantiation
            {
             before(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_5_1_0()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_113581);
            ruleRoleInstantiation();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolDeclAccess().getRoleInstantiationsRoleInstantiationParserRuleCall_8_1_5_1_0()); 

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
    // $ANTLR end "rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1"


    // $ANTLR start "rule__RoleDecl__NameAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6827:1: rule__RoleDecl__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__RoleDecl__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6831:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6832:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6832:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6833:1: RULE_ID
            {
             before(grammarAccess.getRoleDeclAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__RoleDecl__NameAssignment_113612); 
             after(grammarAccess.getRoleDeclAccess().getNameIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__RoleDecl__NameAssignment_1"


    // $ANTLR start "rule__RoleDecl__AliasAssignment_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6842:1: rule__RoleDecl__AliasAssignment_2_1 : ( RULE_ID ) ;
    public final void rule__RoleDecl__AliasAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6846:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6847:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6847:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6848:1: RULE_ID
            {
             before(grammarAccess.getRoleDeclAccess().getAliasIDTerminalRuleCall_2_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__RoleDecl__AliasAssignment_2_113643); 
             after(grammarAccess.getRoleDeclAccess().getAliasIDTerminalRuleCall_2_1_0()); 

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
    // $ANTLR end "rule__RoleDecl__AliasAssignment_2_1"


    // $ANTLR start "rule__ParameterDecl__NameAssignment_0_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6857:1: rule__ParameterDecl__NameAssignment_0_1 : ( RULE_ID ) ;
    public final void rule__ParameterDecl__NameAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6861:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6862:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6862:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6863:1: RULE_ID
            {
             before(grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_0_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ParameterDecl__NameAssignment_0_113674); 
             after(grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_0_1_0()); 

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
    // $ANTLR end "rule__ParameterDecl__NameAssignment_0_1"


    // $ANTLR start "rule__ParameterDecl__AliasAssignment_0_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6872:1: rule__ParameterDecl__AliasAssignment_0_2_1 : ( RULE_ID ) ;
    public final void rule__ParameterDecl__AliasAssignment_0_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6876:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6877:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6877:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6878:1: RULE_ID
            {
             before(grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_0_2_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ParameterDecl__AliasAssignment_0_2_113705); 
             after(grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_0_2_1_0()); 

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
    // $ANTLR end "rule__ParameterDecl__AliasAssignment_0_2_1"


    // $ANTLR start "rule__ParameterDecl__NameAssignment_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6887:1: rule__ParameterDecl__NameAssignment_1_1 : ( RULE_ID ) ;
    public final void rule__ParameterDecl__NameAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6891:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6892:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6892:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6893:1: RULE_ID
            {
             before(grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_1_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ParameterDecl__NameAssignment_1_113736); 
             after(grammarAccess.getParameterDeclAccess().getNameIDTerminalRuleCall_1_1_0()); 

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
    // $ANTLR end "rule__ParameterDecl__NameAssignment_1_1"


    // $ANTLR start "rule__ParameterDecl__AliasAssignment_1_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6902:1: rule__ParameterDecl__AliasAssignment_1_2_1 : ( RULE_ID ) ;
    public final void rule__ParameterDecl__AliasAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6906:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6907:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6907:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6908:1: RULE_ID
            {
             before(grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_1_2_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__ParameterDecl__AliasAssignment_1_2_113767); 
             after(grammarAccess.getParameterDeclAccess().getAliasIDTerminalRuleCall_1_2_1_0()); 

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
    // $ANTLR end "rule__ParameterDecl__AliasAssignment_1_2_1"


    // $ANTLR start "rule__RoleInstantiation__NameAssignment_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6917:1: rule__RoleInstantiation__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__RoleInstantiation__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6921:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6922:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6922:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6923:1: RULE_ID
            {
             before(grammarAccess.getRoleInstantiationAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__RoleInstantiation__NameAssignment_013798); 
             after(grammarAccess.getRoleInstantiationAccess().getNameIDTerminalRuleCall_0_0()); 

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
    // $ANTLR end "rule__RoleInstantiation__NameAssignment_0"


    // $ANTLR start "rule__RoleInstantiation__AliasAssignment_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6932:1: rule__RoleInstantiation__AliasAssignment_1_1 : ( RULE_ID ) ;
    public final void rule__RoleInstantiation__AliasAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6936:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6937:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6937:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6938:1: RULE_ID
            {
             before(grammarAccess.getRoleInstantiationAccess().getAliasIDTerminalRuleCall_1_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__RoleInstantiation__AliasAssignment_1_113829); 
             after(grammarAccess.getRoleInstantiationAccess().getAliasIDTerminalRuleCall_1_1_0()); 

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
    // $ANTLR end "rule__RoleInstantiation__AliasAssignment_1_1"


    // $ANTLR start "rule__Argument__SignatureAssignment_0_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6947:1: rule__Argument__SignatureAssignment_0_0 : ( ruleMessageSignature ) ;
    public final void rule__Argument__SignatureAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6951:1: ( ( ruleMessageSignature ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6952:1: ( ruleMessageSignature )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6952:1: ( ruleMessageSignature )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6953:1: ruleMessageSignature
            {
             before(grammarAccess.getArgumentAccess().getSignatureMessageSignatureParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_ruleMessageSignature_in_rule__Argument__SignatureAssignment_0_013860);
            ruleMessageSignature();

            state._fsp--;

             after(grammarAccess.getArgumentAccess().getSignatureMessageSignatureParserRuleCall_0_0_0()); 

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
    // $ANTLR end "rule__Argument__SignatureAssignment_0_0"


    // $ANTLR start "rule__Argument__AliasAssignment_0_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6962:1: rule__Argument__AliasAssignment_0_1_1 : ( RULE_ID ) ;
    public final void rule__Argument__AliasAssignment_0_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6966:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6967:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6967:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6968:1: RULE_ID
            {
             before(grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_0_1_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Argument__AliasAssignment_0_1_113891); 
             after(grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_0_1_1_0()); 

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
    // $ANTLR end "rule__Argument__AliasAssignment_0_1_1"


    // $ANTLR start "rule__Argument__NameAssignment_1_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6977:1: rule__Argument__NameAssignment_1_0 : ( RULE_ID ) ;
    public final void rule__Argument__NameAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6981:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6982:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6982:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6983:1: RULE_ID
            {
             before(grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_1_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Argument__NameAssignment_1_013922); 
             after(grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_1_0_0()); 

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
    // $ANTLR end "rule__Argument__NameAssignment_1_0"


    // $ANTLR start "rule__Argument__AliasAssignment_1_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6992:1: rule__Argument__AliasAssignment_1_1_1 : ( RULE_ID ) ;
    public final void rule__Argument__AliasAssignment_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6996:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6997:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6997:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:6998:1: RULE_ID
            {
             before(grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_1_1_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Argument__AliasAssignment_1_1_113953); 
             after(grammarAccess.getArgumentAccess().getAliasIDTerminalRuleCall_1_1_1_0()); 

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
    // $ANTLR end "rule__Argument__AliasAssignment_1_1_1"


    // $ANTLR start "rule__GlobalProtocolBlock__ActivitiesAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7007:1: rule__GlobalProtocolBlock__ActivitiesAssignment_2 : ( ruleGlobalInteraction ) ;
    public final void rule__GlobalProtocolBlock__ActivitiesAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7011:1: ( ( ruleGlobalInteraction ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7012:1: ( ruleGlobalInteraction )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7012:1: ( ruleGlobalInteraction )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7013:1: ruleGlobalInteraction
            {
             before(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesGlobalInteractionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleGlobalInteraction_in_rule__GlobalProtocolBlock__ActivitiesAssignment_213984);
            ruleGlobalInteraction();

            state._fsp--;

             after(grammarAccess.getGlobalProtocolBlockAccess().getActivitiesGlobalInteractionParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalProtocolBlock__ActivitiesAssignment_2"


    // $ANTLR start "rule__GlobalMessageTransfer__MessageAssignment_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7022:1: rule__GlobalMessageTransfer__MessageAssignment_0 : ( ruleMessage ) ;
    public final void rule__GlobalMessageTransfer__MessageAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7026:1: ( ( ruleMessage ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7027:1: ( ruleMessage )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7027:1: ( ruleMessage )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7028:1: ruleMessage
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getMessageMessageParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__GlobalMessageTransfer__MessageAssignment_014015);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getGlobalMessageTransferAccess().getMessageMessageParserRuleCall_0_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__MessageAssignment_0"


    // $ANTLR start "rule__GlobalMessageTransfer__FromRoleAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7037:1: rule__GlobalMessageTransfer__FromRoleAssignment_2 : ( RULE_ID ) ;
    public final void rule__GlobalMessageTransfer__FromRoleAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7041:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7042:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7042:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7043:1: RULE_ID
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getFromRoleIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__FromRoleAssignment_214046); 
             after(grammarAccess.getGlobalMessageTransferAccess().getFromRoleIDTerminalRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__FromRoleAssignment_2"


    // $ANTLR start "rule__GlobalMessageTransfer__ToRoleAssignment_4"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7052:1: rule__GlobalMessageTransfer__ToRoleAssignment_4 : ( RULE_ID ) ;
    public final void rule__GlobalMessageTransfer__ToRoleAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7056:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7057:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7057:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7058:1: RULE_ID
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_4_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__ToRoleAssignment_414077); 
             after(grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_4_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__ToRoleAssignment_4"


    // $ANTLR start "rule__GlobalMessageTransfer__ToRoleAssignment_5_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7067:1: rule__GlobalMessageTransfer__ToRoleAssignment_5_1 : ( RULE_ID ) ;
    public final void rule__GlobalMessageTransfer__ToRoleAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7071:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7072:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7072:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7073:1: RULE_ID
            {
             before(grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_5_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__ToRoleAssignment_5_114108); 
             after(grammarAccess.getGlobalMessageTransferAccess().getToRoleIDTerminalRuleCall_5_1_0()); 

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
    // $ANTLR end "rule__GlobalMessageTransfer__ToRoleAssignment_5_1"


    // $ANTLR start "rule__Message__ParameterAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7082:1: rule__Message__ParameterAssignment_1 : ( RULE_ID ) ;
    public final void rule__Message__ParameterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7086:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7087:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7087:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7088:1: RULE_ID
            {
             before(grammarAccess.getMessageAccess().getParameterIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Message__ParameterAssignment_114139); 
             after(grammarAccess.getMessageAccess().getParameterIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__Message__ParameterAssignment_1"


    // $ANTLR start "rule__GlobalChoice__RoleAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7097:1: rule__GlobalChoice__RoleAssignment_2 : ( RULE_ID ) ;
    public final void rule__GlobalChoice__RoleAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7101:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7102:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7102:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7103:1: RULE_ID
            {
             before(grammarAccess.getGlobalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalChoice__RoleAssignment_214170); 
             after(grammarAccess.getGlobalChoiceAccess().getRoleIDTerminalRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalChoice__RoleAssignment_2"


    // $ANTLR start "rule__GlobalChoice__BlocksAssignment_3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7112:1: rule__GlobalChoice__BlocksAssignment_3 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalChoice__BlocksAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7116:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7117:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7117:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7118:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalChoice__BlocksAssignment_314201);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__GlobalChoice__BlocksAssignment_3"


    // $ANTLR start "rule__GlobalChoice__BlocksAssignment_4_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7127:1: rule__GlobalChoice__BlocksAssignment_4_1 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalChoice__BlocksAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7131:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7132:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7132:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7133:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalChoice__BlocksAssignment_4_114232);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalChoiceAccess().getBlocksGlobalProtocolBlockParserRuleCall_4_1_0()); 

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
    // $ANTLR end "rule__GlobalChoice__BlocksAssignment_4_1"


    // $ANTLR start "rule__GlobalRecursion__LabelAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7142:1: rule__GlobalRecursion__LabelAssignment_1 : ( RULE_ID ) ;
    public final void rule__GlobalRecursion__LabelAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7146:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7147:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7147:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7148:1: RULE_ID
            {
             before(grammarAccess.getGlobalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalRecursion__LabelAssignment_114263); 
             after(grammarAccess.getGlobalRecursionAccess().getLabelIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__GlobalRecursion__LabelAssignment_1"


    // $ANTLR start "rule__GlobalRecursion__BlockAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7157:1: rule__GlobalRecursion__BlockAssignment_2 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalRecursion__BlockAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7161:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7162:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7162:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7163:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalRecursion__BlockAssignment_214294);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalRecursionAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalRecursion__BlockAssignment_2"


    // $ANTLR start "rule__GlobalContinue__LabelAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7172:1: rule__GlobalContinue__LabelAssignment_1 : ( RULE_ID ) ;
    public final void rule__GlobalContinue__LabelAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7176:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7177:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7177:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7178:1: RULE_ID
            {
             before(grammarAccess.getGlobalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalContinue__LabelAssignment_114325); 
             after(grammarAccess.getGlobalContinueAccess().getLabelIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__GlobalContinue__LabelAssignment_1"


    // $ANTLR start "rule__GlobalParallel__BlocksAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7187:1: rule__GlobalParallel__BlocksAssignment_1 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalParallel__BlocksAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7191:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7192:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7192:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7193:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_1_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalParallel__BlocksAssignment_114356);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__GlobalParallel__BlocksAssignment_1"


    // $ANTLR start "rule__GlobalParallel__BlocksAssignment_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7202:1: rule__GlobalParallel__BlocksAssignment_2_1 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalParallel__BlocksAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7206:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7207:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7207:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7208:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalParallel__BlocksAssignment_2_114387);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalParallelAccess().getBlocksGlobalProtocolBlockParserRuleCall_2_1_0()); 

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
    // $ANTLR end "rule__GlobalParallel__BlocksAssignment_2_1"


    // $ANTLR start "rule__GlobalInterruptible__ScopeAssignment_1_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7217:1: rule__GlobalInterruptible__ScopeAssignment_1_0 : ( RULE_ID ) ;
    public final void rule__GlobalInterruptible__ScopeAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7221:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7222:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7222:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7223:1: RULE_ID
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalInterruptible__ScopeAssignment_1_014418); 
             after(grammarAccess.getGlobalInterruptibleAccess().getScopeIDTerminalRuleCall_1_0_0()); 

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
    // $ANTLR end "rule__GlobalInterruptible__ScopeAssignment_1_0"


    // $ANTLR start "rule__GlobalInterruptible__BlockAssignment_2"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7232:1: rule__GlobalInterruptible__BlockAssignment_2 : ( ruleGlobalProtocolBlock ) ;
    public final void rule__GlobalInterruptible__BlockAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7236:1: ( ( ruleGlobalProtocolBlock ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7237:1: ( ruleGlobalProtocolBlock )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7237:1: ( ruleGlobalProtocolBlock )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7238:1: ruleGlobalProtocolBlock
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalInterruptible__BlockAssignment_214449);
            ruleGlobalProtocolBlock();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptibleAccess().getBlockGlobalProtocolBlockParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__GlobalInterruptible__BlockAssignment_2"


    // $ANTLR start "rule__GlobalInterruptible__InterruptsAssignment_5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7247:1: rule__GlobalInterruptible__InterruptsAssignment_5 : ( ruleGlobalInterrupt ) ;
    public final void rule__GlobalInterruptible__InterruptsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7251:1: ( ( ruleGlobalInterrupt ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7252:1: ( ruleGlobalInterrupt )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7252:1: ( ruleGlobalInterrupt )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7253:1: ruleGlobalInterrupt
            {
             before(grammarAccess.getGlobalInterruptibleAccess().getInterruptsGlobalInterruptParserRuleCall_5_0()); 
            pushFollow(FOLLOW_ruleGlobalInterrupt_in_rule__GlobalInterruptible__InterruptsAssignment_514480);
            ruleGlobalInterrupt();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptibleAccess().getInterruptsGlobalInterruptParserRuleCall_5_0()); 

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
    // $ANTLR end "rule__GlobalInterruptible__InterruptsAssignment_5"


    // $ANTLR start "rule__GlobalInterrupt__MessagesAssignment_0"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7262:1: rule__GlobalInterrupt__MessagesAssignment_0 : ( ruleMessage ) ;
    public final void rule__GlobalInterrupt__MessagesAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7266:1: ( ( ruleMessage ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7267:1: ( ruleMessage )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7267:1: ( ruleMessage )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7268:1: ruleMessage
            {
             before(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__GlobalInterrupt__MessagesAssignment_014511);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_0_0()); 

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
    // $ANTLR end "rule__GlobalInterrupt__MessagesAssignment_0"


    // $ANTLR start "rule__GlobalInterrupt__MessagesAssignment_1_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7277:1: rule__GlobalInterrupt__MessagesAssignment_1_1 : ( ruleMessage ) ;
    public final void rule__GlobalInterrupt__MessagesAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7281:1: ( ( ruleMessage ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7282:1: ( ruleMessage )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7282:1: ( ruleMessage )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7283:1: ruleMessage
            {
             before(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleMessage_in_rule__GlobalInterrupt__MessagesAssignment_1_114542);
            ruleMessage();

            state._fsp--;

             after(grammarAccess.getGlobalInterruptAccess().getMessagesMessageParserRuleCall_1_1_0()); 

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
    // $ANTLR end "rule__GlobalInterrupt__MessagesAssignment_1_1"


    // $ANTLR start "rule__GlobalInterrupt__RoleAssignment_3"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7292:1: rule__GlobalInterrupt__RoleAssignment_3 : ( RULE_ID ) ;
    public final void rule__GlobalInterrupt__RoleAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7296:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7297:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7297:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7298:1: RULE_ID
            {
             before(grammarAccess.getGlobalInterruptAccess().getRoleIDTerminalRuleCall_3_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalInterrupt__RoleAssignment_314573); 
             after(grammarAccess.getGlobalInterruptAccess().getRoleIDTerminalRuleCall_3_0()); 

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
    // $ANTLR end "rule__GlobalInterrupt__RoleAssignment_3"


    // $ANTLR start "rule__GlobalDo__ModuleAssignment_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7307:1: rule__GlobalDo__ModuleAssignment_1 : ( RULE_ID ) ;
    public final void rule__GlobalDo__ModuleAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7311:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7312:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7312:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7313:1: RULE_ID
            {
             before(grammarAccess.getGlobalDoAccess().getModuleIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalDo__ModuleAssignment_114604); 
             after(grammarAccess.getGlobalDoAccess().getModuleIDTerminalRuleCall_1_0()); 

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
    // $ANTLR end "rule__GlobalDo__ModuleAssignment_1"


    // $ANTLR start "rule__GlobalDo__ScopeAssignment_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7322:1: rule__GlobalDo__ScopeAssignment_2_1 : ( RULE_ID ) ;
    public final void rule__GlobalDo__ScopeAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7326:1: ( ( RULE_ID ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7327:1: ( RULE_ID )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7327:1: ( RULE_ID )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7328:1: RULE_ID
            {
             before(grammarAccess.getGlobalDoAccess().getScopeIDTerminalRuleCall_2_1_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__GlobalDo__ScopeAssignment_2_114635); 
             after(grammarAccess.getGlobalDoAccess().getScopeIDTerminalRuleCall_2_1_0()); 

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
    // $ANTLR end "rule__GlobalDo__ScopeAssignment_2_1"


    // $ANTLR start "rule__GlobalDo__ArgumentsAssignment_3_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7337:1: rule__GlobalDo__ArgumentsAssignment_3_1 : ( ruleArgument ) ;
    public final void rule__GlobalDo__ArgumentsAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7341:1: ( ( ruleArgument ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7342:1: ( ruleArgument )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7342:1: ( ruleArgument )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7343:1: ruleArgument
            {
             before(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_ruleArgument_in_rule__GlobalDo__ArgumentsAssignment_3_114666);
            ruleArgument();

            state._fsp--;

             after(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_1_0()); 

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
    // $ANTLR end "rule__GlobalDo__ArgumentsAssignment_3_1"


    // $ANTLR start "rule__GlobalDo__ArgumentsAssignment_3_2_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7352:1: rule__GlobalDo__ArgumentsAssignment_3_2_1 : ( ruleArgument ) ;
    public final void rule__GlobalDo__ArgumentsAssignment_3_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7356:1: ( ( ruleArgument ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7357:1: ( ruleArgument )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7357:1: ( ruleArgument )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7358:1: ruleArgument
            {
             before(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 
            pushFollow(FOLLOW_ruleArgument_in_rule__GlobalDo__ArgumentsAssignment_3_2_114697);
            ruleArgument();

            state._fsp--;

             after(grammarAccess.getGlobalDoAccess().getArgumentsArgumentParserRuleCall_3_2_1_0()); 

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
    // $ANTLR end "rule__GlobalDo__ArgumentsAssignment_3_2_1"


    // $ANTLR start "rule__GlobalDo__RolesAssignment_5"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7367:1: rule__GlobalDo__RolesAssignment_5 : ( ruleRoleInstantiation ) ;
    public final void rule__GlobalDo__RolesAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7371:1: ( ( ruleRoleInstantiation ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7372:1: ( ruleRoleInstantiation )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7372:1: ( ruleRoleInstantiation )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7373:1: ruleRoleInstantiation
            {
             before(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_rule__GlobalDo__RolesAssignment_514728);
            ruleRoleInstantiation();

            state._fsp--;

             after(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_5_0()); 

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
    // $ANTLR end "rule__GlobalDo__RolesAssignment_5"


    // $ANTLR start "rule__GlobalDo__RolesAssignment_6_1"
    // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7382:1: rule__GlobalDo__RolesAssignment_6_1 : ( ruleRoleInstantiation ) ;
    public final void rule__GlobalDo__RolesAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7386:1: ( ( ruleRoleInstantiation ) )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7387:1: ( ruleRoleInstantiation )
            {
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7387:1: ( ruleRoleInstantiation )
            // ../org.scribble.editor.ui/src-gen/org/scribble/editor/dsl/ui/contentassist/antlr/internal/InternalScribbleDsl.g:7388:1: ruleRoleInstantiation
            {
             before(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_ruleRoleInstantiation_in_rule__GlobalDo__RolesAssignment_6_114759);
            ruleRoleInstantiation();

            state._fsp--;

             after(grammarAccess.getGlobalDoAccess().getRolesRoleInstantiationParserRuleCall_6_1_0()); 

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
    // $ANTLR end "rule__GlobalDo__RolesAssignment_6_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModule_in_entryRuleModule61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModule68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__0_in_ruleModule94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_entryRuleModuleDecl121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleDecl128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__0_in_ruleModuleDecl154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_entryRuleModuleName181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleName188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group__0_in_ruleModuleName214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_entryRuleImportDecl241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportDecl248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportDecl__Alternatives_in_ruleImportDecl274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_entryRuleImportModule301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportModule308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__0_in_ruleImportModule334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_entryRuleImportMember361 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleImportMember368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__0_in_ruleImportMember394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_entryRulePayloadTypeDecl421 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadTypeDecl428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__0_in_rulePayloadTypeDecl454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_entryRuleMessageSignature483 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessageSignature490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__0_in_ruleMessageSignature516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_entryRulePayloadElement543 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePayloadElement550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group__0_in_rulePayloadElement576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_entryRuleGlobalProtocolDecl603 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolDecl610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__0_in_ruleGlobalProtocolDecl636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_entryRuleRoleDecl663 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleDecl670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__0_in_ruleRoleDecl696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_entryRuleParameterDecl723 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleParameterDecl730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Alternatives_in_ruleParameterDecl756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_entryRuleRoleInstantiation783 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoleInstantiation790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group__0_in_ruleRoleInstantiation816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_entryRuleArgument843 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArgument850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Alternatives_in_ruleArgument876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_entryRuleGlobalProtocolBlock903 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalProtocolBlock910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__0_in_ruleGlobalProtocolBlock936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_entryRuleGlobalInteraction963 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInteraction970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInteraction__Alternatives_in_ruleGlobalInteraction996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_entryRuleGlobalMessageTransfer1023 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalMessageTransfer1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__0_in_ruleGlobalMessageTransfer1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_entryRuleMessage1083 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMessage1090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__Alternatives_in_ruleMessage1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_entryRuleGlobalChoice1143 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalChoice1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__0_in_ruleGlobalChoice1176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_entryRuleGlobalRecursion1203 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalRecursion1210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__0_in_ruleGlobalRecursion1236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_entryRuleGlobalContinue1263 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalContinue1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__0_in_ruleGlobalContinue1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_entryRuleGlobalParallel1323 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalParallel1330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__0_in_ruleGlobalParallel1356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_entryRuleGlobalInterruptible1383 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterruptible1390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__0_in_ruleGlobalInterruptible1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_entryRuleGlobalInterrupt1443 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalInterrupt1450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__0_in_ruleGlobalInterrupt1476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_entryRuleGlobalDo1503 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleGlobalDo1510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__0_in_ruleGlobalDo1536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportModule_in_rule__ImportDecl__Alternatives1572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportMember_in_rule__ImportDecl__Alternatives1589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__BlockAssignment_8_0_in_rule__GlobalProtocolDecl__Alternatives_81621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__0_in_rule__GlobalProtocolDecl__Alternatives_81639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__0_in_rule__ParameterDecl__Alternatives1672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__0_in_rule__ParameterDecl__Alternatives1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0__0_in_rule__Argument__Alternatives1723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1__0_in_rule__Argument__Alternatives1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalMessageTransfer_in_rule__GlobalInteraction__Alternatives1774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalChoice_in_rule__GlobalInteraction__Alternatives1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalRecursion_in_rule__GlobalInteraction__Alternatives1808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalContinue_in_rule__GlobalInteraction__Alternatives1825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalParallel_in_rule__GlobalInteraction__Alternatives1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterruptible_in_rule__GlobalInteraction__Alternatives1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalDo_in_rule__GlobalInteraction__Alternatives1876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_rule__Message__Alternatives1908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Message__ParameterAssignment_1_in_rule__Message__Alternatives1925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__0__Impl_in_rule__Module__Group__01956 = new BitSet(new long[]{0x0000000001034000L});
    public static final BitSet FOLLOW_rule__Module__Group__1_in_rule__Module__Group__01959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleDecl_in_rule__Module__Group__0__Impl1986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__Group__1__Impl_in_rule__Module__Group__12015 = new BitSet(new long[]{0x0000000001034000L});
    public static final BitSet FOLLOW_rule__Module__Group__2_in_rule__Module__Group__12018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__ImportsAssignment_1_in_rule__Module__Group__1__Impl2045 = new BitSet(new long[]{0x0000000000014002L});
    public static final BitSet FOLLOW_rule__Module__Group__2__Impl_in_rule__Module__Group__22076 = new BitSet(new long[]{0x0000000001034000L});
    public static final BitSet FOLLOW_rule__Module__Group__3_in_rule__Module__Group__22079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__TypesAssignment_2_in_rule__Module__Group__2__Impl2106 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_rule__Module__Group__3__Impl_in_rule__Module__Group__32137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Module__GlobalsAssignment_3_in_rule__Module__Group__3__Impl2164 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__0__Impl_in_rule__ModuleDecl__Group__02203 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__1_in_rule__ModuleDecl__Group__02206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__ModuleDecl__Group__0__Impl2234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__1__Impl_in_rule__ModuleDecl__Group__12265 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__2_in_rule__ModuleDecl__Group__12268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleDecl__NameAssignment_1_in_rule__ModuleDecl__Group__1__Impl2295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleDecl__Group__2__Impl_in_rule__ModuleDecl__Group__22325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__ModuleDecl__Group__2__Impl2353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group__0__Impl_in_rule__ModuleName__Group__02390 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__ModuleName__Group__1_in_rule__ModuleName__Group__02393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ModuleName__Group__0__Impl2420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group__1__Impl_in_rule__ModuleName__Group__12449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group_1__0_in_rule__ModuleName__Group__1__Impl2476 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group_1__0__Impl_in_rule__ModuleName__Group_1__02511 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ModuleName__Group_1__1_in_rule__ModuleName__Group_1__02514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__ModuleName__Group_1__0__Impl2542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ModuleName__Group_1__1__Impl_in_rule__ModuleName__Group_1__12573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ModuleName__Group_1__1__Impl2600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__0__Impl_in_rule__ImportModule__Group__02633 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__1_in_rule__ImportModule__Group__02636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__ImportModule__Group__0__Impl2664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__1__Impl_in_rule__ImportModule__Group__12695 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__2_in_rule__ImportModule__Group__12698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__NameAssignment_1_in_rule__ImportModule__Group__1__Impl2725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__2__Impl_in_rule__ImportModule__Group__22755 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__3_in_rule__ImportModule__Group__22758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group_2__0_in_rule__ImportModule__Group__2__Impl2785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group__3__Impl_in_rule__ImportModule__Group__32816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__ImportModule__Group__3__Impl2844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group_2__0__Impl_in_rule__ImportModule__Group_2__02883 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ImportModule__Group_2__1_in_rule__ImportModule__Group_2__02886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ImportModule__Group_2__0__Impl2914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__Group_2__1__Impl_in_rule__ImportModule__Group_2__12945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportModule__AliasAssignment_2_1_in_rule__ImportModule__Group_2__1__Impl2972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__0__Impl_in_rule__ImportMember__Group__03006 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__1_in_rule__ImportMember__Group__03009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__ImportMember__Group__0__Impl3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__1__Impl_in_rule__ImportMember__Group__13068 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__2_in_rule__ImportMember__Group__13071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__NameAssignment_1_in_rule__ImportMember__Group__1__Impl3098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__2__Impl_in_rule__ImportMember__Group__23128 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__3_in_rule__ImportMember__Group__23131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__ImportMember__Group__2__Impl3159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__3__Impl_in_rule__ImportMember__Group__33190 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__4_in_rule__ImportMember__Group__33193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__MemberAssignment_3_in_rule__ImportMember__Group__3__Impl3220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__4__Impl_in_rule__ImportMember__Group__43250 = new BitSet(new long[]{0x0000000000009000L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__5_in_rule__ImportMember__Group__43253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group_4__0_in_rule__ImportMember__Group__4__Impl3280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group__5__Impl_in_rule__ImportMember__Group__53311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__ImportMember__Group__5__Impl3339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group_4__0__Impl_in_rule__ImportMember__Group_4__03382 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ImportMember__Group_4__1_in_rule__ImportMember__Group_4__03385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ImportMember__Group_4__0__Impl3413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__Group_4__1__Impl_in_rule__ImportMember__Group_4__13444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ImportMember__AliasAssignment_4_1_in_rule__ImportMember__Group_4__1__Impl3471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__0__Impl_in_rule__PayloadTypeDecl__Group__03505 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__1_in_rule__PayloadTypeDecl__Group__03508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__PayloadTypeDecl__Group__0__Impl3536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__1__Impl_in_rule__PayloadTypeDecl__Group__13567 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__2_in_rule__PayloadTypeDecl__Group__13570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__PayloadTypeDecl__Group__1__Impl3598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__2__Impl_in_rule__PayloadTypeDecl__Group__23629 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__3_in_rule__PayloadTypeDecl__Group__23632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__SchemaAssignment_2_in_rule__PayloadTypeDecl__Group__2__Impl3659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__3__Impl_in_rule__PayloadTypeDecl__Group__33689 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__4_in_rule__PayloadTypeDecl__Group__33692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__PayloadTypeDecl__Group__3__Impl3720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__4__Impl_in_rule__PayloadTypeDecl__Group__43751 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__5_in_rule__PayloadTypeDecl__Group__43754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__TypeAssignment_4_in_rule__PayloadTypeDecl__Group__4__Impl3781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__5__Impl_in_rule__PayloadTypeDecl__Group__53811 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__6_in_rule__PayloadTypeDecl__Group__53814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__PayloadTypeDecl__Group__5__Impl3842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__6__Impl_in_rule__PayloadTypeDecl__Group__63873 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__7_in_rule__PayloadTypeDecl__Group__63876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__LocationAssignment_6_in_rule__PayloadTypeDecl__Group__6__Impl3903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__7__Impl_in_rule__PayloadTypeDecl__Group__73933 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__8_in_rule__PayloadTypeDecl__Group__73936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__PayloadTypeDecl__Group__7__Impl3964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__8__Impl_in_rule__PayloadTypeDecl__Group__83995 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__9_in_rule__PayloadTypeDecl__Group__83998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__AliasAssignment_8_in_rule__PayloadTypeDecl__Group__8__Impl4025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadTypeDecl__Group__9__Impl_in_rule__PayloadTypeDecl__Group__94055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__PayloadTypeDecl__Group__9__Impl4083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__0__Impl_in_rule__MessageSignature__Group__04134 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__1_in_rule__MessageSignature__Group__04137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__OperatorAssignment_0_in_rule__MessageSignature__Group__0__Impl4164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__1__Impl_in_rule__MessageSignature__Group__14194 = new BitSet(new long[]{0x0000000000200010L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__2_in_rule__MessageSignature__Group__14197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__MessageSignature__Group__1__Impl4225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__2__Impl_in_rule__MessageSignature__Group__24256 = new BitSet(new long[]{0x0000000000200010L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__3_in_rule__MessageSignature__Group__24259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2__0_in_rule__MessageSignature__Group__2__Impl4286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group__3__Impl_in_rule__MessageSignature__Group__34317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__MessageSignature__Group__3__Impl4345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2__0__Impl_in_rule__MessageSignature__Group_2__04384 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2__1_in_rule__MessageSignature__Group_2__04387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__TypesAssignment_2_0_in_rule__MessageSignature__Group_2__0__Impl4414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2__1__Impl_in_rule__MessageSignature__Group_2__14444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2_1__0_in_rule__MessageSignature__Group_2__1__Impl4471 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2_1__0__Impl_in_rule__MessageSignature__Group_2_1__04506 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2_1__1_in_rule__MessageSignature__Group_2_1__04509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MessageSignature__Group_2_1__0__Impl4537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__Group_2_1__1__Impl_in_rule__MessageSignature__Group_2_1__14568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MessageSignature__TypesAssignment_2_1_1_in_rule__MessageSignature__Group_2_1__1__Impl4595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group__0__Impl_in_rule__PayloadElement__Group__04629 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group__1_in_rule__PayloadElement__Group__04632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group_0__0_in_rule__PayloadElement__Group__0__Impl4659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group__1__Impl_in_rule__PayloadElement__Group__14690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__TypeAssignment_1_in_rule__PayloadElement__Group__1__Impl4717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group_0__0__Impl_in_rule__PayloadElement__Group_0__04751 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group_0__1_in_rule__PayloadElement__Group_0__04754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__NameAssignment_0_0_in_rule__PayloadElement__Group_0__0__Impl4781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__PayloadElement__Group_0__1__Impl_in_rule__PayloadElement__Group_0__14811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__PayloadElement__Group_0__1__Impl4839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__0__Impl_in_rule__GlobalProtocolDecl__Group__04874 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__1_in_rule__GlobalProtocolDecl__Group__04877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__GlobalProtocolDecl__Group__0__Impl4905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__1__Impl_in_rule__GlobalProtocolDecl__Group__14936 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__2_in_rule__GlobalProtocolDecl__Group__14939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__GlobalProtocolDecl__Group__1__Impl4967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__2__Impl_in_rule__GlobalProtocolDecl__Group__24998 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__3_in_rule__GlobalProtocolDecl__Group__25001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__NameAssignment_2_in_rule__GlobalProtocolDecl__Group__2__Impl5028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__3__Impl_in_rule__GlobalProtocolDecl__Group__35058 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__4_in_rule__GlobalProtocolDecl__Group__35061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__0_in_rule__GlobalProtocolDecl__Group__3__Impl5088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__4__Impl_in_rule__GlobalProtocolDecl__Group__45119 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__5_in_rule__GlobalProtocolDecl__Group__45122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__GlobalProtocolDecl__Group__4__Impl5150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__5__Impl_in_rule__GlobalProtocolDecl__Group__55181 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__6_in_rule__GlobalProtocolDecl__Group__55184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__RolesAssignment_5_in_rule__GlobalProtocolDecl__Group__5__Impl5211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__6__Impl_in_rule__GlobalProtocolDecl__Group__65241 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__7_in_rule__GlobalProtocolDecl__Group__65244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_6__0_in_rule__GlobalProtocolDecl__Group__6__Impl5271 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__7__Impl_in_rule__GlobalProtocolDecl__Group__75302 = new BitSet(new long[]{0x0000000024000000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__8_in_rule__GlobalProtocolDecl__Group__75305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__GlobalProtocolDecl__Group__7__Impl5333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group__8__Impl_in_rule__GlobalProtocolDecl__Group__85364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Alternatives_8_in_rule__GlobalProtocolDecl__Group__8__Impl5391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__0__Impl_in_rule__GlobalProtocolDecl__Group_3__05439 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__1_in_rule__GlobalProtocolDecl__Group_3__05442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__GlobalProtocolDecl__Group_3__0__Impl5470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__1__Impl_in_rule__GlobalProtocolDecl__Group_3__15501 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__2_in_rule__GlobalProtocolDecl__Group_3__15504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__ParametersAssignment_3_1_in_rule__GlobalProtocolDecl__Group_3__1__Impl5531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__2__Impl_in_rule__GlobalProtocolDecl__Group_3__25561 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__3_in_rule__GlobalProtocolDecl__Group_3__25564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3_2__0_in_rule__GlobalProtocolDecl__Group_3__2__Impl5591 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3__3__Impl_in_rule__GlobalProtocolDecl__Group_3__35622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__GlobalProtocolDecl__Group_3__3__Impl5650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3_2__0__Impl_in_rule__GlobalProtocolDecl__Group_3_2__05689 = new BitSet(new long[]{0x0000000010020000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3_2__1_in_rule__GlobalProtocolDecl__Group_3_2__05692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalProtocolDecl__Group_3_2__0__Impl5720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_3_2__1__Impl_in_rule__GlobalProtocolDecl__Group_3_2__15751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__ParametersAssignment_3_2_1_in_rule__GlobalProtocolDecl__Group_3_2__1__Impl5778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_6__0__Impl_in_rule__GlobalProtocolDecl__Group_6__05812 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_6__1_in_rule__GlobalProtocolDecl__Group_6__05815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalProtocolDecl__Group_6__0__Impl5843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_6__1__Impl_in_rule__GlobalProtocolDecl__Group_6__15874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__RolesAssignment_6_1_in_rule__GlobalProtocolDecl__Group_6__1__Impl5901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1__05935 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__1_in_rule__GlobalProtocolDecl__Group_8_1__05938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__GlobalProtocolDecl__Group_8_1__0__Impl5966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1__15997 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__2_in_rule__GlobalProtocolDecl__Group_8_1__16000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_1_in_rule__GlobalProtocolDecl__Group_8_1__1__Impl6027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__2__Impl_in_rule__GlobalProtocolDecl__Group_8_1__26057 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__3_in_rule__GlobalProtocolDecl__Group_8_1__26060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__0_in_rule__GlobalProtocolDecl__Group_8_1__2__Impl6087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__3__Impl_in_rule__GlobalProtocolDecl__Group_8_1__36118 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__4_in_rule__GlobalProtocolDecl__Group_8_1__36121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__GlobalProtocolDecl__Group_8_1__3__Impl6149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__4__Impl_in_rule__GlobalProtocolDecl__Group_8_1__46180 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__5_in_rule__GlobalProtocolDecl__Group_8_1__46183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_4_in_rule__GlobalProtocolDecl__Group_8_1__4__Impl6210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__5__Impl_in_rule__GlobalProtocolDecl__Group_8_1__56240 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__6_in_rule__GlobalProtocolDecl__Group_8_1__56243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__0_in_rule__GlobalProtocolDecl__Group_8_1__5__Impl6270 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__6__Impl_in_rule__GlobalProtocolDecl__Group_8_1__66301 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__7_in_rule__GlobalProtocolDecl__Group_8_1__66304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__GlobalProtocolDecl__Group_8_1__6__Impl6332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1__7__Impl_in_rule__GlobalProtocolDecl__Group_8_1__76363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GlobalProtocolDecl__Group_8_1__7__Impl6391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__06438 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__1_in_rule__GlobalProtocolDecl__Group_8_1_2__06441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__GlobalProtocolDecl__Group_8_1_2__0__Impl6469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__16500 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__2_in_rule__GlobalProtocolDecl__Group_8_1_2__16503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_1_in_rule__GlobalProtocolDecl__Group_8_1_2__1__Impl6530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__2__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__26560 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__3_in_rule__GlobalProtocolDecl__Group_8_1_2__26563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__0_in_rule__GlobalProtocolDecl__Group_8_1_2__2__Impl6590 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2__3__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2__36621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__GlobalProtocolDecl__Group_8_1_2__3__Impl6649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2_2__06688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__1_in_rule__GlobalProtocolDecl__Group_8_1_2_2__06691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalProtocolDecl__Group_8_1_2_2__0__Impl6719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_2_2__16750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_1_in_rule__GlobalProtocolDecl__Group_8_1_2_2__1__Impl6777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__0__Impl_in_rule__GlobalProtocolDecl__Group_8_1_5__06811 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__1_in_rule__GlobalProtocolDecl__Group_8_1_5__06814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalProtocolDecl__Group_8_1_5__0__Impl6842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__Group_8_1_5__1__Impl_in_rule__GlobalProtocolDecl__Group_8_1_5__16873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_1_in_rule__GlobalProtocolDecl__Group_8_1_5__1__Impl6900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__0__Impl_in_rule__RoleDecl__Group__06934 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__1_in_rule__RoleDecl__Group__06937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__RoleDecl__Group__0__Impl6965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__1__Impl_in_rule__RoleDecl__Group__16996 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__2_in_rule__RoleDecl__Group__16999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__NameAssignment_1_in_rule__RoleDecl__Group__1__Impl7026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group__2__Impl_in_rule__RoleDecl__Group__27056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group_2__0_in_rule__RoleDecl__Group__2__Impl7083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group_2__0__Impl_in_rule__RoleDecl__Group_2__07120 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group_2__1_in_rule__RoleDecl__Group_2__07123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__RoleDecl__Group_2__0__Impl7151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__Group_2__1__Impl_in_rule__RoleDecl__Group_2__17182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleDecl__AliasAssignment_2_1_in_rule__RoleDecl__Group_2__1__Impl7209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__0__Impl_in_rule__ParameterDecl__Group_0__07243 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__1_in_rule__ParameterDecl__Group_0__07246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__ParameterDecl__Group_0__0__Impl7274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__1__Impl_in_rule__ParameterDecl__Group_0__17305 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__2_in_rule__ParameterDecl__Group_0__17308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__NameAssignment_0_1_in_rule__ParameterDecl__Group_0__1__Impl7335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0__2__Impl_in_rule__ParameterDecl__Group_0__27365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0_2__0_in_rule__ParameterDecl__Group_0__2__Impl7392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0_2__0__Impl_in_rule__ParameterDecl__Group_0_2__07429 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0_2__1_in_rule__ParameterDecl__Group_0_2__07432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ParameterDecl__Group_0_2__0__Impl7460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_0_2__1__Impl_in_rule__ParameterDecl__Group_0_2__17491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__AliasAssignment_0_2_1_in_rule__ParameterDecl__Group_0_2__1__Impl7518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__0__Impl_in_rule__ParameterDecl__Group_1__07552 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__1_in_rule__ParameterDecl__Group_1__07555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rule__ParameterDecl__Group_1__0__Impl7583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__1__Impl_in_rule__ParameterDecl__Group_1__17614 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__2_in_rule__ParameterDecl__Group_1__17617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__NameAssignment_1_1_in_rule__ParameterDecl__Group_1__1__Impl7644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1__2__Impl_in_rule__ParameterDecl__Group_1__27674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1_2__0_in_rule__ParameterDecl__Group_1__2__Impl7701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1_2__0__Impl_in_rule__ParameterDecl__Group_1_2__07738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1_2__1_in_rule__ParameterDecl__Group_1_2__07741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__ParameterDecl__Group_1_2__0__Impl7769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__Group_1_2__1__Impl_in_rule__ParameterDecl__Group_1_2__17800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__ParameterDecl__AliasAssignment_1_2_1_in_rule__ParameterDecl__Group_1_2__1__Impl7827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group__0__Impl_in_rule__RoleInstantiation__Group__07861 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group__1_in_rule__RoleInstantiation__Group__07864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__NameAssignment_0_in_rule__RoleInstantiation__Group__0__Impl7891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group__1__Impl_in_rule__RoleInstantiation__Group__17921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group_1__0_in_rule__RoleInstantiation__Group__1__Impl7948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group_1__0__Impl_in_rule__RoleInstantiation__Group_1__07983 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group_1__1_in_rule__RoleInstantiation__Group_1__07986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__RoleInstantiation__Group_1__0__Impl8014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__Group_1__1__Impl_in_rule__RoleInstantiation__Group_1__18045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__RoleInstantiation__AliasAssignment_1_1_in_rule__RoleInstantiation__Group_1__1__Impl8072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0__0__Impl_in_rule__Argument__Group_0__08106 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Argument__Group_0__1_in_rule__Argument__Group_0__08109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__SignatureAssignment_0_0_in_rule__Argument__Group_0__0__Impl8136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0__1__Impl_in_rule__Argument__Group_0__18166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0_1__0_in_rule__Argument__Group_0__1__Impl8193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0_1__0__Impl_in_rule__Argument__Group_0_1__08228 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Argument__Group_0_1__1_in_rule__Argument__Group_0_1__08231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Argument__Group_0_1__0__Impl8259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_0_1__1__Impl_in_rule__Argument__Group_0_1__18290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__AliasAssignment_0_1_1_in_rule__Argument__Group_0_1__1__Impl8317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1__0__Impl_in_rule__Argument__Group_1__08351 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Argument__Group_1__1_in_rule__Argument__Group_1__08354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__NameAssignment_1_0_in_rule__Argument__Group_1__0__Impl8381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1__1__Impl_in_rule__Argument__Group_1__18411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1_1__0_in_rule__Argument__Group_1__1__Impl8438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1_1__0__Impl_in_rule__Argument__Group_1_1__08473 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Argument__Group_1_1__1_in_rule__Argument__Group_1_1__08476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Argument__Group_1_1__0__Impl8504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__Group_1_1__1__Impl_in_rule__Argument__Group_1_1__18535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Argument__AliasAssignment_1_1_1_in_rule__Argument__Group_1_1__1__Impl8562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__0__Impl_in_rule__GlobalProtocolBlock__Group__08596 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__1_in_rule__GlobalProtocolBlock__Group__08599 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__1__Impl_in_rule__GlobalProtocolBlock__Group__18657 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__2_in_rule__GlobalProtocolBlock__Group__18660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GlobalProtocolBlock__Group__1__Impl8688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__2__Impl_in_rule__GlobalProtocolBlock__Group__28719 = new BitSet(new long[]{0x000004B940000010L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__3_in_rule__GlobalProtocolBlock__Group__28722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__ActivitiesAssignment_2_in_rule__GlobalProtocolBlock__Group__2__Impl8749 = new BitSet(new long[]{0x000004B900000012L});
    public static final BitSet FOLLOW_rule__GlobalProtocolBlock__Group__3__Impl_in_rule__GlobalProtocolBlock__Group__38780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__GlobalProtocolBlock__Group__3__Impl8808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__0__Impl_in_rule__GlobalMessageTransfer__Group__08847 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__1_in_rule__GlobalMessageTransfer__Group__08850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__MessageAssignment_0_in_rule__GlobalMessageTransfer__Group__0__Impl8877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__1__Impl_in_rule__GlobalMessageTransfer__Group__18907 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__2_in_rule__GlobalMessageTransfer__Group__18910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__GlobalMessageTransfer__Group__1__Impl8938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__2__Impl_in_rule__GlobalMessageTransfer__Group__28969 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__3_in_rule__GlobalMessageTransfer__Group__28972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__FromRoleAssignment_2_in_rule__GlobalMessageTransfer__Group__2__Impl8999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__3__Impl_in_rule__GlobalMessageTransfer__Group__39029 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__4_in_rule__GlobalMessageTransfer__Group__39032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_rule__GlobalMessageTransfer__Group__3__Impl9060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__4__Impl_in_rule__GlobalMessageTransfer__Group__49091 = new BitSet(new long[]{0x0000000000401000L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__5_in_rule__GlobalMessageTransfer__Group__49094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__ToRoleAssignment_4_in_rule__GlobalMessageTransfer__Group__4__Impl9121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__5__Impl_in_rule__GlobalMessageTransfer__Group__59151 = new BitSet(new long[]{0x0000000000401000L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__6_in_rule__GlobalMessageTransfer__Group__59154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group_5__0_in_rule__GlobalMessageTransfer__Group__5__Impl9181 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group__6__Impl_in_rule__GlobalMessageTransfer__Group__69212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GlobalMessageTransfer__Group__6__Impl9240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group_5__0__Impl_in_rule__GlobalMessageTransfer__Group_5__09285 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group_5__1_in_rule__GlobalMessageTransfer__Group_5__09288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalMessageTransfer__Group_5__0__Impl9316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__Group_5__1__Impl_in_rule__GlobalMessageTransfer__Group_5__19347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalMessageTransfer__ToRoleAssignment_5_1_in_rule__GlobalMessageTransfer__Group_5__1__Impl9374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__0__Impl_in_rule__GlobalChoice__Group__09408 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__1_in_rule__GlobalChoice__Group__09411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_rule__GlobalChoice__Group__0__Impl9439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__1__Impl_in_rule__GlobalChoice__Group__19470 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__2_in_rule__GlobalChoice__Group__19473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rule__GlobalChoice__Group__1__Impl9501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__2__Impl_in_rule__GlobalChoice__Group__29532 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__3_in_rule__GlobalChoice__Group__29535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__RoleAssignment_2_in_rule__GlobalChoice__Group__2__Impl9562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__3__Impl_in_rule__GlobalChoice__Group__39592 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__4_in_rule__GlobalChoice__Group__39595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__BlocksAssignment_3_in_rule__GlobalChoice__Group__3__Impl9622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group__4__Impl_in_rule__GlobalChoice__Group__49652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group_4__0_in_rule__GlobalChoice__Group__4__Impl9679 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group_4__0__Impl_in_rule__GlobalChoice__Group_4__09720 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group_4__1_in_rule__GlobalChoice__Group_4__09723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rule__GlobalChoice__Group_4__0__Impl9751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__Group_4__1__Impl_in_rule__GlobalChoice__Group_4__19782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalChoice__BlocksAssignment_4_1_in_rule__GlobalChoice__Group_4__1__Impl9809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__0__Impl_in_rule__GlobalRecursion__Group__09843 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__1_in_rule__GlobalRecursion__Group__09846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rule__GlobalRecursion__Group__0__Impl9874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__1__Impl_in_rule__GlobalRecursion__Group__19905 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__2_in_rule__GlobalRecursion__Group__19908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__LabelAssignment_1_in_rule__GlobalRecursion__Group__1__Impl9935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__Group__2__Impl_in_rule__GlobalRecursion__Group__29965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalRecursion__BlockAssignment_2_in_rule__GlobalRecursion__Group__2__Impl9992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__0__Impl_in_rule__GlobalContinue__Group__010028 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__1_in_rule__GlobalContinue__Group__010031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rule__GlobalContinue__Group__0__Impl10059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__1__Impl_in_rule__GlobalContinue__Group__110090 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__2_in_rule__GlobalContinue__Group__110093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalContinue__LabelAssignment_1_in_rule__GlobalContinue__Group__1__Impl10120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalContinue__Group__2__Impl_in_rule__GlobalContinue__Group__210150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GlobalContinue__Group__2__Impl10178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__0__Impl_in_rule__GlobalParallel__Group__010215 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__1_in_rule__GlobalParallel__Group__010218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_rule__GlobalParallel__Group__0__Impl10246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__1__Impl_in_rule__GlobalParallel__Group__110277 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__2_in_rule__GlobalParallel__Group__110280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__BlocksAssignment_1_in_rule__GlobalParallel__Group__1__Impl10307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group__2__Impl_in_rule__GlobalParallel__Group__210337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group_2__0_in_rule__GlobalParallel__Group__2__Impl10364 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group_2__0__Impl_in_rule__GlobalParallel__Group_2__010401 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group_2__1_in_rule__GlobalParallel__Group_2__010404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rule__GlobalParallel__Group_2__0__Impl10432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__Group_2__1__Impl_in_rule__GlobalParallel__Group_2__110463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalParallel__BlocksAssignment_2_1_in_rule__GlobalParallel__Group_2__1__Impl10490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__0__Impl_in_rule__GlobalInterruptible__Group__010524 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__1_in_rule__GlobalInterruptible__Group__010527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rule__GlobalInterruptible__Group__0__Impl10555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__1__Impl_in_rule__GlobalInterruptible__Group__110586 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__2_in_rule__GlobalInterruptible__Group__110589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group_1__0_in_rule__GlobalInterruptible__Group__1__Impl10616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__2__Impl_in_rule__GlobalInterruptible__Group__210647 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__3_in_rule__GlobalInterruptible__Group__210650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__BlockAssignment_2_in_rule__GlobalInterruptible__Group__2__Impl10677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__3__Impl_in_rule__GlobalInterruptible__Group__310707 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__4_in_rule__GlobalInterruptible__Group__310710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_rule__GlobalInterruptible__Group__3__Impl10738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__4__Impl_in_rule__GlobalInterruptible__Group__410769 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__5_in_rule__GlobalInterruptible__Group__410772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_rule__GlobalInterruptible__Group__4__Impl10800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__5__Impl_in_rule__GlobalInterruptible__Group__510831 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__6_in_rule__GlobalInterruptible__Group__510834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__InterruptsAssignment_5_in_rule__GlobalInterruptible__Group__5__Impl10861 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group__6__Impl_in_rule__GlobalInterruptible__Group__610892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_rule__GlobalInterruptible__Group__6__Impl10920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group_1__0__Impl_in_rule__GlobalInterruptible__Group_1__010965 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group_1__1_in_rule__GlobalInterruptible__Group_1__010968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__ScopeAssignment_1_0_in_rule__GlobalInterruptible__Group_1__0__Impl10995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterruptible__Group_1__1__Impl_in_rule__GlobalInterruptible__Group_1__111025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__GlobalInterruptible__Group_1__1__Impl11053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__0__Impl_in_rule__GlobalInterrupt__Group__011088 = new BitSet(new long[]{0x0000020000400000L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__1_in_rule__GlobalInterrupt__Group__011091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__MessagesAssignment_0_in_rule__GlobalInterrupt__Group__0__Impl11118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__1__Impl_in_rule__GlobalInterrupt__Group__111148 = new BitSet(new long[]{0x0000020000400000L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__2_in_rule__GlobalInterrupt__Group__111151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group_1__0_in_rule__GlobalInterrupt__Group__1__Impl11178 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__2__Impl_in_rule__GlobalInterrupt__Group__211209 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__3_in_rule__GlobalInterrupt__Group__211212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_rule__GlobalInterrupt__Group__2__Impl11240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__3__Impl_in_rule__GlobalInterrupt__Group__311271 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__4_in_rule__GlobalInterrupt__Group__311274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__RoleAssignment_3_in_rule__GlobalInterrupt__Group__3__Impl11301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group__4__Impl_in_rule__GlobalInterrupt__Group__411331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GlobalInterrupt__Group__4__Impl11359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group_1__0__Impl_in_rule__GlobalInterrupt__Group_1__011400 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group_1__1_in_rule__GlobalInterrupt__Group_1__011403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalInterrupt__Group_1__0__Impl11431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__Group_1__1__Impl_in_rule__GlobalInterrupt__Group_1__111462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalInterrupt__MessagesAssignment_1_1_in_rule__GlobalInterrupt__Group_1__1__Impl11489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__0__Impl_in_rule__GlobalDo__Group__011523 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__1_in_rule__GlobalDo__Group__011526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_rule__GlobalDo__Group__0__Impl11554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__1__Impl_in_rule__GlobalDo__Group__111585 = new BitSet(new long[]{0x0000000000940000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__2_in_rule__GlobalDo__Group__111588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__ModuleAssignment_1_in_rule__GlobalDo__Group__1__Impl11615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__2__Impl_in_rule__GlobalDo__Group__211645 = new BitSet(new long[]{0x0000000000940000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__3_in_rule__GlobalDo__Group__211648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_2__0_in_rule__GlobalDo__Group__2__Impl11675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__3__Impl_in_rule__GlobalDo__Group__311706 = new BitSet(new long[]{0x0000000000940000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__4_in_rule__GlobalDo__Group__311709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__0_in_rule__GlobalDo__Group__3__Impl11736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__4__Impl_in_rule__GlobalDo__Group__411767 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__5_in_rule__GlobalDo__Group__411770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__GlobalDo__Group__4__Impl11798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__5__Impl_in_rule__GlobalDo__Group__511829 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__6_in_rule__GlobalDo__Group__511832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__RolesAssignment_5_in_rule__GlobalDo__Group__5__Impl11859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__6__Impl_in_rule__GlobalDo__Group__611889 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__7_in_rule__GlobalDo__Group__611892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_6__0_in_rule__GlobalDo__Group__6__Impl11919 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__7__Impl_in_rule__GlobalDo__Group__711950 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__8_in_rule__GlobalDo__Group__711953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__GlobalDo__Group__7__Impl11981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group__8__Impl_in_rule__GlobalDo__Group__812012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__GlobalDo__Group__8__Impl12040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_2__0__Impl_in_rule__GlobalDo__Group_2__012089 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_2__1_in_rule__GlobalDo__Group_2__012092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__GlobalDo__Group_2__0__Impl12120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_2__1__Impl_in_rule__GlobalDo__Group_2__112151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__ScopeAssignment_2_1_in_rule__GlobalDo__Group_2__1__Impl12178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__0__Impl_in_rule__GlobalDo__Group_3__012212 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__1_in_rule__GlobalDo__Group_3__012215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__GlobalDo__Group_3__0__Impl12243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__1__Impl_in_rule__GlobalDo__Group_3__112274 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__2_in_rule__GlobalDo__Group_3__112277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__ArgumentsAssignment_3_1_in_rule__GlobalDo__Group_3__1__Impl12304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__2__Impl_in_rule__GlobalDo__Group_3__212334 = new BitSet(new long[]{0x0000000000480000L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__3_in_rule__GlobalDo__Group_3__212337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3_2__0_in_rule__GlobalDo__Group_3__2__Impl12364 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3__3__Impl_in_rule__GlobalDo__Group_3__312395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__GlobalDo__Group_3__3__Impl12423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3_2__0__Impl_in_rule__GlobalDo__Group_3_2__012462 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3_2__1_in_rule__GlobalDo__Group_3_2__012465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalDo__Group_3_2__0__Impl12493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_3_2__1__Impl_in_rule__GlobalDo__Group_3_2__112524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__ArgumentsAssignment_3_2_1_in_rule__GlobalDo__Group_3_2__1__Impl12551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_6__0__Impl_in_rule__GlobalDo__Group_6__012585 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_6__1_in_rule__GlobalDo__Group_6__012588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__GlobalDo__Group_6__0__Impl12616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__Group_6__1__Impl_in_rule__GlobalDo__Group_6__112647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__GlobalDo__RolesAssignment_6_1_in_rule__GlobalDo__Group_6__1__Impl12674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleImportDecl_in_rule__Module__ImportsAssignment_112713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadTypeDecl_in_rule__Module__TypesAssignment_212744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolDecl_in_rule__Module__GlobalsAssignment_312775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_rule__ModuleDecl__NameAssignment_112806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_rule__ImportModule__NameAssignment_112837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ImportModule__AliasAssignment_2_112868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_rule__ImportMember__NameAssignment_112899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ImportMember__MemberAssignment_312930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ImportMember__AliasAssignment_4_112961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PayloadTypeDecl__SchemaAssignment_212992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__PayloadTypeDecl__TypeAssignment_413023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__PayloadTypeDecl__LocationAssignment_613054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PayloadTypeDecl__AliasAssignment_813085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__MessageSignature__OperatorAssignment_013116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_rule__MessageSignature__TypesAssignment_2_013147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePayloadElement_in_rule__MessageSignature__TypesAssignment_2_1_113178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PayloadElement__NameAssignment_0_013209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__PayloadElement__TypeAssignment_113240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalProtocolDecl__NameAssignment_213271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_rule__GlobalProtocolDecl__ParametersAssignment_3_113302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleParameterDecl_in_rule__GlobalProtocolDecl__ParametersAssignment_3_2_113333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_rule__GlobalProtocolDecl__RolesAssignment_513364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleDecl_in_rule__GlobalProtocolDecl__RolesAssignment_6_113395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalProtocolDecl__BlockAssignment_8_013426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalProtocolDecl__InstantiatesAssignment_8_1_113457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_113488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_rule__GlobalProtocolDecl__ArgumentsAssignment_8_1_2_2_113519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_413550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_rule__GlobalProtocolDecl__RoleInstantiationsAssignment_8_1_5_113581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__RoleDecl__NameAssignment_113612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__RoleDecl__AliasAssignment_2_113643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ParameterDecl__NameAssignment_0_113674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ParameterDecl__AliasAssignment_0_2_113705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ParameterDecl__NameAssignment_1_113736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__ParameterDecl__AliasAssignment_1_2_113767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__RoleInstantiation__NameAssignment_013798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__RoleInstantiation__AliasAssignment_1_113829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessageSignature_in_rule__Argument__SignatureAssignment_0_013860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Argument__AliasAssignment_0_1_113891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Argument__NameAssignment_1_013922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Argument__AliasAssignment_1_1_113953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInteraction_in_rule__GlobalProtocolBlock__ActivitiesAssignment_213984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__GlobalMessageTransfer__MessageAssignment_014015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__FromRoleAssignment_214046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__ToRoleAssignment_414077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalMessageTransfer__ToRoleAssignment_5_114108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Message__ParameterAssignment_114139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalChoice__RoleAssignment_214170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalChoice__BlocksAssignment_314201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalChoice__BlocksAssignment_4_114232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalRecursion__LabelAssignment_114263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalRecursion__BlockAssignment_214294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalContinue__LabelAssignment_114325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalParallel__BlocksAssignment_114356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalParallel__BlocksAssignment_2_114387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalInterruptible__ScopeAssignment_1_014418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalProtocolBlock_in_rule__GlobalInterruptible__BlockAssignment_214449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleGlobalInterrupt_in_rule__GlobalInterruptible__InterruptsAssignment_514480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__GlobalInterrupt__MessagesAssignment_014511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMessage_in_rule__GlobalInterrupt__MessagesAssignment_1_114542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalInterrupt__RoleAssignment_314573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalDo__ModuleAssignment_114604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__GlobalDo__ScopeAssignment_2_114635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_rule__GlobalDo__ArgumentsAssignment_3_114666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_rule__GlobalDo__ArgumentsAssignment_3_2_114697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_rule__GlobalDo__RolesAssignment_514728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoleInstantiation_in_rule__GlobalDo__RolesAssignment_6_114759 = new BitSet(new long[]{0x0000000000000002L});

}