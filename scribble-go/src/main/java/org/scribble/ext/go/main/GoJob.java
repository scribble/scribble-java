package org.scribble.ext.go.main;

import java.util.Map;

import org.scribble.ast.AstFactory;
import org.scribble.ast.Module;
import org.scribble.main.Job;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.global.SModelFactory;
import org.scribble.type.name.ModuleName;

public class GoJob extends Job
{
	public final boolean selectApi;
	public final boolean noCopy;
	public final boolean parForeach;
	public final boolean dotApi;
	
	public GoJob(boolean debug, Map<ModuleName, Module> parsed, ModuleName main,
			boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, 
			AstFactory af, EModelFactory ef, SModelFactory sf, boolean noCopy, boolean selectApi, boolean parForeach, boolean dotApi)
	{
		super(debug, parsed, main, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, af, ef, sf);
		this.noCopy = noCopy;
		this.selectApi = selectApi;
		this.parForeach = parForeach;
		this.dotApi = dotApi;
	}
	
	/*// FIXME: move to MainContext::newJob?
	@Override
	public ParamEGraphBuilderUtil newEGraphBuilderUtil()
	{
		return new ParamEGraphBuilderUtil((ParamEModelFactory) this.ef);
	}
	
	@Override
	public void runContextBuildingPasses() throws ScribbleException
	{
		runVisitorPassOnAllModules(ModuleContextBuilder.class);

		runVisitorPassOnAllModules(ParamNameDisambiguator.class);  // FIXME: factor out overriding pattern?

		runVisitorPassOnAllModules(ProtocolDeclContextBuilder.class);
		runVisitorPassOnAllModules(DelegationProtocolRefChecker.class);
		runVisitorPassOnAllModules(RoleCollector.class);
		runVisitorPassOnAllModules(ProtocolDefInliner.class);
	}
		
	@Override
	public void runUnfoldingPass() throws ScribbleException
	{
		//runVisitorPassOnAllModules(ParamInlinedProtocolUnfolder.class);  // FIXME: skipping for now
	}

	@Override
	protected void runProjectionUnfoldingPass() throws ScribbleException
	{
		//runVisitorPassOnProjectedModules(ParamInlinedProtocolUnfolder.class);  // FIXME
	}

	@Override
	public void runWellFormednessPasses() throws ScribbleException
	{
		super.runWellFormednessPasses();

		// Additional
		if (!this.noValidation)
		{
			runVisitorPassOnAllModules(ParamAnnotationChecker.class);
		}
	}*/
}
