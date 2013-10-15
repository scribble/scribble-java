/*
 * Copyright 2009 www.scribble.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.designer.keywords;

/**
 * The protocol notation keyword provider.
 */
public class ProtocolKeyWordProvider extends DefaultKeyWordProvider {
	
	private static final String[] RESERVED_WORDS={
		"and",
		"as",
		"at",
		"by",
		"catch",
		"choice",
		"continue",
		"do",
		"from",
		"global",
		"import",
		"instantiates",
		"interruptible",
		"local",
		"or",
		"par",
		"protocol",
		"rec",
		"role",
		"throw",
		"to",
		"with",
		"module"
	};
    
    /**
     * The default constructor.
     */
    public ProtocolKeyWordProvider() {
        super(RESERVED_WORDS);
    }
    
}
