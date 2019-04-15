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
package org.scribble.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

@Deprecated
public class JavaProcessBuilder {
	private String jvm;
	private Collection<String> classpath = new ArrayList<>();
	private String javaAgent;
	private String javaAgentArgs;
	private String program;
	private Collection<String> programArgs = new ArrayList<>();
	private Map<String, String> systemProps = new HashMap<>();
	public JavaProcessBuilder(String jvm, String program) {
		this.jvm = jvm;
		this.program = program;
	}
	public JavaProcessBuilder(String program) {
		this("java", program);
	}
	public String getJvm() {
		return jvm;
	}
	public void setJvm(String jvm) {
		this.jvm = jvm;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getProgram() {
		return program;
	}
	public void appendClasspath(String param) {
		classpath.add(param);
	}
	public void appendProgramArgument(String arg) {
		programArgs.add(arg);
	}
	public void setJavaAgent(String param) {
		this.javaAgent = param;
	}
	public String getJavaAgent() {
		return javaAgent;
	}
	public void setJavaAgentArgs(String javaAgentArgs) {
		this.javaAgentArgs = javaAgentArgs;
	}
	public String getJavaAgentArgs() {
		return javaAgentArgs;
	}
	
	public void setSystemProperty(String key) {
		setSystemProperty(key, null);
	}
	
	public void setSystemProperty(String key, String value) {
		systemProps.put(key, value);
	}
	
	public void unsetSystemProperty(String key) {
		systemProps.remove(key);
	}
	
	private String buildClasspath() {
		return ExecUtil.join(File.pathSeparator, classpath);
	}
	
	private String buildJavaAgent() {
		StringBuilder builder = new StringBuilder("-javaagent:");
		builder.append(javaAgent);
		if (javaAgentArgs != null) {
			builder.append('=');
			builder.append(javaAgentArgs);
		}
		return builder.toString();
	}
	
	public String[] build() {
		Collection<String> result = new ArrayList<>();
		result.add(jvm);
		if (classpath.size() > 0) {
			result.add("-cp");
			result.add(buildClasspath());
		}
		if (javaAgent != null) {
			result.add(buildJavaAgent());
		}
		buildSystemProperties(result);
		result.add(program);
		result.addAll(programArgs);
		return result.toArray(new String[0]);
	}
	private void buildSystemProperties(Collection<String> result) {
		for (Entry<String, String> entry : systemProps.entrySet()) {
			StringBuilder builder = new StringBuilder();
			builder.append("-D");
			builder.append(entry.getKey());
			if (entry.getValue() != null) {
				builder.append("=");
				builder.append(entry.getValue());
			}
			result.add(builder.toString());
		}
	}
	public void setSystemProperties(Properties props) {
		systemProps.clear();
		for (String key : props.stringPropertyNames()) {
			systemProps.put(key, props.getProperty(key));
		}
	}
	@Override
	public String toString() {
		return ExecUtil.join(" ", build());
	}
}