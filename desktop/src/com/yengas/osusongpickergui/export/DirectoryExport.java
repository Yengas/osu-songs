package com.yengas.osusongpickergui.export;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.yengas.osusongpickergui.export.DirectoryExport.DirSingle;

public class DirectoryExport extends Exporter<DirSingle> {
	public class DirSingle{
		public final File input;
		public final String outputName;
		public DirSingle(File input, String outputName){
			this.input = input;
			this.outputName = outputName;
		}
	}

	@Override
	protected DirSingle getSingle(File input, String name) {
		return new DirSingle(input, name);
	}

	@Override
	public boolean output(File file) throws Exception {
		if(file.isFile()) return false;
		file.mkdirs();
		
		for(DirSingle single : singles){
			Files.copy(single.input.toPath(), Paths.get(file.getAbsolutePath(), single.outputName), StandardCopyOption.REPLACE_EXISTING);
			updateProgress(single.input);
		}
		return true;
	}

	@Override
	public String getExtension() {
		return "";
	}

	@Override
	public String getInitialName() {
		return "archive-folder";
	}
}
