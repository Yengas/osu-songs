package com.yengas.osusongpickergui.export;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipExport extends Exporter<ZipExport.ZipSingle>{
	private byte[] buffer;
	
	public class ZipSingle{
		public final File file;
		public final ZipEntry entry;
		public ZipSingle(File file, ZipEntry entry){ this.file = file; this.entry = entry; }
	}
	
	@Override
	protected ZipSingle getSingle(File input, String name) {
		return new ZipSingle(input, new ZipEntry(name));
	}
	
	@Override
	public boolean output(File outputFile) throws Exception{
		if(outputFile.isDirectory()) return false;
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream( outputFile ));
		buffer = new byte[256 * 1024];
		
		for(ZipSingle single : singles) this.write(single, zos);
		zos.closeEntry();
		zos.close();
		
		buffer = null;
		return true;
	}
	
	private void write(ZipSingle single, ZipOutputStream zos) throws Exception{
		FileInputStream reader = new FileInputStream( single.file );
		int read = -1;
		
		zos.putNextEntry( single.entry );
		while((read = reader.read(buffer)) != -1){
			zos.write(buffer, 0, read);
		}
		reader.close();
		updateProgress(single.file);
	}

	@Override
	public String getExtension() {
		return "*.zip";
	}

	@Override
	public String getInitialName() {
		return "archive.zip";
	}
}
