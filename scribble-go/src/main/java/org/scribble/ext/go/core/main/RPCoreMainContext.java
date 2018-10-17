package org.scribble.ext.go.core.main;

import java.nio.file.Path;

import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.RPAstFactoryImpl;
import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactory;
import org.scribble.ext.go.core.model.endpoint.RPCoreEModelFactoryImpl;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.parser.scribble.RPAntlrToScribParser;
import org.scribble.ext.go.parser.scribble.RPScribbleAntlrWrapper;
import org.scribble.main.MainContext;
import org.scribble.main.ScribbleException;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.parser.scribble.ScribbleAntlrWrapper;
import org.scribble.util.ScribParserException;

public class RPCoreMainContext extends MainContext
{
	protected final boolean selectApi;
	protected final boolean noCopy;
	protected final boolean parForeach;
	
	// Load main module from file system
	public RPCoreMainContext(boolean debug, ResourceLocator locator, Path mainpath, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation, boolean noCopy, boolean selectApi, boolean parForeach)
					throws ScribParserException, ScribbleException
	{
		super(debug, locator, mainpath, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);
		this.noCopy = noCopy;
		this.selectApi = selectApi;
		this.parForeach = parForeach;
	}

	@Override
	public GoJob newJob()  // FIXME: make RPCoreJob?
	{
		return new GoJob(this.debug, this.getParsedModules(), this.main, this.useOldWF, this.noLiveness, this.minEfsm, this.fair,
				this.noLocalChoiceSubjectCheck, this.noAcceptCorrelationCheck, this.noValidation,
				this.af, this.ef, this.sf, this.noCopy, this.selectApi, this.parForeach);
	}

	@Override
	protected ScribbleAntlrWrapper newAntlrParser()
	{
		return new RPScribbleAntlrWrapper();
	}
	
	@Override
	protected RPAntlrToScribParser newScribParser()
	{
		return new RPAntlrToScribParser();
	}
	
	@Override
	protected RPAstFactory newAstFactory()
	{
		return new RPAstFactoryImpl();
	}
	
	@Override
	protected RPCoreEModelFactory newEModelFactory()
	{
		return new RPCoreEModelFactoryImpl();  // HACK FIXME
	}
	
	/*@Override
	protected SModelFactory newSModelFactory()
	{
		//return new ParamSModelFactoryImpl();
		return new ParamCoreSModelFactoryImpl();  // HACK FIXME
	}*/
}
