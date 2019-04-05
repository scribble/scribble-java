package org.scribble.lang;

import org.antlr.runtime.tree.CommonTree;

public interface SNode
{
	boolean hasSource();  // i.e., was parsed
	CommonTree getSource();  // Pre: hasSource
}
