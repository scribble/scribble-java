package org.scribble2.model.name;

import org.scribble2.model.ModelNodeBase;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Named;

//public abstract class NameNode<T extends IName> extends ModelNodeBase implements Named<T>
public abstract class NameNode<T extends Name<K>, K extends Kind> extends ModelNodeBase implements Named<T, K>
{
}
