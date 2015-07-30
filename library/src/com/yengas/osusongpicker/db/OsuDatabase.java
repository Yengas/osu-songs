package com.yengas.osusongpicker.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class OsuDatabase {
	private File databaseFile;
	private int databaseVersion, totalSongs;
	private boolean bool_0;
	private Date date_0;
	private String user;
	private ArrayList<Beatmap> beatmapList;
	
	public OsuDatabase(File databaseFile){
		this.databaseFile = databaseFile;
		this.reload();
	}
	
	public void reload(){
		OsuDatabaseReader reader = null;
		
		try {
			reader = new OsuDatabaseReader(new FileInputStream(databaseFile));
			
			this.databaseVersion = reader.readInt32();
			this.totalSongs = reader.readInt32();
			this.bool_0 = reader.readBoolean();
			this.date_0 = reader.readDate();
			this.user = reader.readString();
			beatmapList = new ArrayList<Beatmap>();
			reader.readList(this, beatmapList, Beatmap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getVersion(){
		return this.databaseVersion;
	}
	
	public int getTotalSongs(){
		return this.totalSongs;
	}
	
	public ArrayList<Beatmap> getBeatmapList(){
		return this.beatmapList;
	}
}
