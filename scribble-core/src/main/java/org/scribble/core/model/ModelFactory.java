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
package org.scribble.core.model;

import java.util.function.Function;

import org.scribble.core.model.endpoint.EModelFactory;
import org.scribble.core.model.global.SModelFactory;

public class ModelFactory
{
	public final EModelFactory local;
	public final SModelFactory global;
	
	// Args should be constructor refs that that ModelFactory single arg
	// (Workaround to set mutually referential ModelFactory and E/SModelFactory final fields)
	public ModelFactory(Function<ModelFactory, EModelFactory> ef,
			Function<ModelFactory, SModelFactory> sf)
	{
		this.local = ef.apply(this);
		this.global = sf.apply(this);
	}
}
