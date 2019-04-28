/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.visit;

import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.session.STypeFactory;
import org.scribble.job.Job;

// Both Ast and SimpleVisitor
public class VisitorFactoryImpl implements VisitorFactory
{
	// SimpleVisitors

	@Override
	public GTypeTranslator GTypeTranslator(Job job, ModuleName rootFullname,
			STypeFactory tf)
	{
		return new GTypeTranslator(job, rootFullname, tf);
	}
	
	
	// AstVisitors

	@Override
	public DelDecorator DelDecorator(Job job)
	{
		return new DelDecorator(job);
	}

	@Override
	public NameDisambiguator NameDisambiguator(Job job)
	{
		return new NameDisambiguator(job);
	}
}
