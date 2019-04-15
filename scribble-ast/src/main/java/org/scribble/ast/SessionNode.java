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
package org.scribble.ast;

import org.scribble.core.type.kind.ProtoKind;

// A SessionNode is an AST node that corresponds to a session type constructor -- elements of InteractionSeq nodes
// The SessionNodeDel hierarchy corresponds to this SessionNode hierarchy
public interface SessionNode<K extends ProtoKind> extends ProtoKindNode<K>
{

}
