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
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.IOUtils;


public class ExecUtil {
	/**
	 * The summary of a process.
	 * @author Tiago Cogumbreiro (cogumbreiro@users.sf.net)
	 *
	 */
	public static class ProcessSummary {
		/**
		 * The standard output.
		 */
		public final String stdout;
		/**
		 * The standard error output.
		 */
		public final String stderr;
		/**
		 * The exit value of the process.
		 */
		public final int exitValue;
		public ProcessSummary(String stdout, String stderr, int exitValue) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.exitValue = exitValue;
		}
	}
	public static String joinPath(String ...parts) {
		return join(File.separator, parts);
	}
	public static String joinPath(Iterable<String> parts) {
		return join(File.separator, parts);
	}
	public static String join(String separator, String ...parts) {
		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (count > 0) {
				builder.append(separator);
			}
			builder.append(part);
			count++;
		}
		return builder.toString();
	}
	public static String join(String separator, Iterable<String> parts) {
		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (count > 0) {
				builder.append(separator);
			}
			builder.append(part);
			count++;
		}
		return builder.toString();
	}
	
	/**
	 * Runs a process, up to a certain timeout,
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static ProcessSummary execUntil(long timeout, String... command)
			throws IOException, InterruptedException, ExecutionException {
		return execUntil(new TreeMap<String, String>(), timeout, command);
	}
	
	/**
	 * Runs a process, up to a certain timeout,
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static ProcessSummary execUntil(Map<String, String> env, long timeout, String... command)
			throws IOException, InterruptedException, ExecutionException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.environment().putAll(env);
		Process process = builder.start();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		CyclicBarrier onStart = new CyclicBarrier(3);
		Future<String> stderr = executorService.submit(toString(process.getErrorStream(),
				onStart));
		Future<String> stdout = executorService.submit(toString(process.getInputStream(),
				onStart));
		Future<Integer> waitFor = executorService.submit(waitFor(process,
				onStart));
		ProcessSummary result = null;
		try {
			waitFor.get(timeout, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			// timeouts are ok
		} finally {
			process.destroy();
			waitFor.get();
			result = new ProcessSummary(stdout.get(), stderr.get(), process.exitValue());
			executorService.shutdown();
		}
		return result;
	}

	/**
	 * Waits for a process to terminate.
	 * 
	 * @param process The process it should wait for.
	 * @param onStart Notifies others upon starting.
	 * @return The return code.
	 */
	public static Callable<Integer> waitFor(final Process process,
			final CyclicBarrier onStart) {
		return new Callable<Integer>() {
			public Integer call() throws InterruptedException, BrokenBarrierException {
				onStart.await();
				return process.waitFor();
			}
		};
	}

	/**
	 * Converts an input stream to a string as much as possible. It stops
	 * at IOExceptions, but returns what it read thus far.
	 * @param in The input stream.
	 * @param onStart Notify others that it started. 
	 */
	private static Callable<String> toString(final InputStream input, final CyclicBarrier onStart) {
		return new Callable<String>() {
			public String call() throws InterruptedException, BrokenBarrierException, IOException {
				onStart.await();
				try {
					return IOUtils.toString(input);
				} finally {
					IOUtils.closeQuietly(input);
				}
			}
		};
	}

}
