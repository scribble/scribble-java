package scribtest;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.AbstractFileFilter;

public class IgnoredDirectoryWhenFilter extends AbstractFileFilter {
	private final FileFilter filter;

	public IgnoredDirectoryWhenFilter(FileFilter filter) {
		this.filter = filter;
	}

	@Override
	public boolean accept(File file) {
		if (!file.isDirectory()) {
			return false;
		}
		return file.listFiles(filter).length == 0;
	}
}
