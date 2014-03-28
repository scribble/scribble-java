/*
 * Copyright 2009-11 www.scribble.org
 *
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
package org.scribble.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribble.context.ModuleCache;
import org.scribble.context.ModuleLoader;
import org.scribble.logging.IssueLogger;
import org.scribble.model.Module;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;

/**
 * This class provides a default implementation of the ModuleLoader interface.
 *
 */
public class ProtocolModuleLoader implements ModuleLoader {
	
	private static final Logger LOG=Logger.getLogger(ProtocolModuleLoader.class.getName());

	private ResourceLocator _locator=null;
	private IssueLogger _logger=null;
	private ModuleCache _cache=null;
	private ProtocolParser _parser=null;
	
	/**
	 * This constructor initializes the module loader.
	 * 
	 * @param parser The parser
	 * @param locator The locator
	 * @param cache The optional module cache
	 * @param logger The logger
	 */
	public ProtocolModuleLoader(ProtocolParser parser, ResourceLocator locator,
					ModuleCache cache, IssueLogger logger) {
		_parser = parser;
		_locator = locator;
		_cache = cache;
		_logger = logger;
		
		if (_cache == null) {
			_cache = new ModuleCache();
		}
	}
	
	/**
	 * This constructor initializes the module loader.
	 * 
	 * @param parser The parser
	 * @param locator The locator
	 * @param logger The logger
	 */
	public ProtocolModuleLoader(ProtocolParser parser, ResourceLocator locator,
					IssueLogger logger) {
		this(parser, locator, null, logger);
	}

	/**
	 * {@inheritDoc}
	 */
	public Module loadModule(String module) {
		Module ret=_cache.getModule(module);
		
		if (ret == null) {
			String relativePath=module.replace('.', java.io.File.separatorChar)+".scr";
	
			Resource resource=_locator.getResource(relativePath);
			
			if (resource != null) {
				try {
					ret = _parser.parse(resource, this, _cache, _logger);
				} catch (Exception e) {
					LOG.log(Level.SEVERE, "Failed to parse imported module '"+module+"'", e);
				}
			}
		}
		
		return (ret);
	}
}
