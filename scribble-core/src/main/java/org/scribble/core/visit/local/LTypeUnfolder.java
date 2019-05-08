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
package org.scribble.core.visit.local;

import org.scribble.core.job.Core;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.session.Continue;
import org.scribble.core.type.session.local.LSeq;
import org.scribble.core.type.session.local.LType;
import org.scribble.core.visit.STypeUnfolder;

public class LTypeUnfolder extends STypeUnfolder<Local, LSeq>
{
	
	protected LTypeUnfolder(Core core)
	{
		super(core);
	}

	@Override
	public LType visitContinue(Continue<Local, LSeq> n)
	{
		return this.core.config.tf.local.LRecursion(n.getSource(), n.recvar,
				(LSeq) getRec(n.recvar));
	}
}
