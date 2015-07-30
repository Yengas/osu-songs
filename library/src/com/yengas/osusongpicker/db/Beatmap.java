package com.yengas.osusongpicker.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.yengas.osusongpicker.utils.SongPickerUtils;

public class Beatmap implements Serializeable{
	public enum SubmissionStatus{
		U,
		NS,
		P,
		EC,
		R,
		A;
		
		private static SubmissionStatus[] values = values();
	}
	
	public enum Rankings
	{
		N,
		F,
		D,
		C,
		B,
		A,
		S,
		X,
		SH,
		XH;
	    
		private static Rankings[] values = values();
	}
	
	public enum PlayMode
	{
	    O,
	    T,
	    CTB,
	    OM;
	    
		private static PlayMode[] values = values();
	}

	private String artist, artistUnicode, title, titleUnicode;
	private String author, difficulty, soundFile, beatmapHash, osuFile;
	private SubmissionStatus submissionStatus;
	private int circleCount, sliderCount, spinnerCount;
	private Date downloadDate;
	private float HPDrainRate, CircleSize, OverallDifficulty, ApproachRate;
	private double SliderMultiplier;
	private ArrayList<HashMap<Integer, Double>> playModeStars;
	private int songLength, songLengthInMs, previewTime;
	private ArrayList<TimingPoints> list_1;
	private int beatmapId, beatmapSetId, threadId;
	private Rankings osuRanking, catchTheBeatRanking, taikoRanking, maniaRanking;
	private int int_13;
	private float stackLeniency;
	private PlayMode playMode;
	private String string_13, tags;
	private int int_12;
	private String floatingText;
	private boolean bool_13;
	private Date lastPlayedDate;
	private boolean bool_10;
	private long long_0;
	private boolean bool_21, bool_22, bool_23, bool_24, bool_25;
	private int int_7, int_18, objectCount;
	private boolean bool_4;
	private String songPath;
	
	@Override
	public String toString(){
		return this.getTitle() + " - " + this.difficulty;
	}
	
	public String getArtist() {
		return artist;
	}

	public String getArtistUnicode() {
		return artistUnicode;
	}

	public String getTitle(){ return title; }

	public String getTitleSafe() {
		String unicode = this.getTitleUnicode();
		if(unicode == null || unicode.trim().isEmpty()) return title;
		return unicode;
	}

	public String getTitleUnicode() {
		return titleUnicode;
	}

	public String getAuthor() {
		return author;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public String getSoundFile() {
		return soundFile;
	}

	public String getBeatmapHash() {
		return beatmapHash;
	}

	public String getOsuFile() {
		return osuFile;
	}

	public SubmissionStatus getSubmissionStatus() {
		return submissionStatus;
	}

	public int getCircleCount() {
		return circleCount;
	}

	public int getSliderCount() {
		return sliderCount;
	}

	public int getSpinnerCount() {
		return spinnerCount;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public float getHPDrainRate() {
		return HPDrainRate;
	}

	public float getCircleSize() {
		return CircleSize;
	}

	public float getOverallDifficulty() {
		return OverallDifficulty;
	}

	public float getApproachRate() {
		return ApproachRate;
	}

	public double getSliderMultiplier() {
		return SliderMultiplier;
	}

	public ArrayList<HashMap<Integer, Double>> getPlayModeStars() {
		return playModeStars;
	}

	public int getSongLength() {
		return songLength;
	}

	public int getSongLengthInMs() {
		return songLengthInMs;
	}

	public int getPreviewTime() {
		return previewTime;
	}

	public int getBeatmapId() {
		return beatmapId;
	}

	public int getBeatmapSetId() {
		return beatmapSetId;
	}

	public int getThreadId() {
		return threadId;
	}

	public Rankings getOsuRanking() {
		return osuRanking;
	}

	public Rankings getCatchTheBeatRanking() {
		return catchTheBeatRanking;
	}

	public Rankings getTaikoRanking() {
		return taikoRanking;
	}

	public Rankings getManiaRanking() {
		return maniaRanking;
	}

	public float getStackLeniency() {
		return stackLeniency;
	}

	public PlayMode getPlayMode() {
		return playMode;
	}

	public String getTags() {
		return tags;
	}

	public String getFloatingText() {
		return floatingText;
	}

	public Date getLastPlayedDate() {
		return lastPlayedDate;
	}

	public int getObjectCount() {
		return objectCount;
	}

	public String getSongPath() {
		return songPath;
	}
	
	@Override
	public void readFromStream(OsuDatabase database, OsuDatabaseReader reader) {
		try{
			artist = reader.readString();
			artistUnicode = reader.readString();
			title = reader.readString();
			titleUnicode = reader.readString();
			author = reader.readString();
			difficulty = reader.readString();
			soundFile = reader.readString();
			beatmapHash = reader.readString();
			osuFile = reader.readString();
			submissionStatus = SongPickerUtils.getSafe(SubmissionStatus.values, reader.read());
			circleCount = reader.readUInt16();
			sliderCount = reader.readUInt16();
			spinnerCount = reader.readUInt16();
			downloadDate = reader.readDate();
			
			if(database.getVersion() >= 20140612){
				ApproachRate = reader.readSingle();
				CircleSize = reader.readSingle();
				HPDrainRate = reader.readSingle();
				OverallDifficulty = reader.readSingle();
			}else{
				ApproachRate = reader.read();
				CircleSize = reader.read();
				HPDrainRate = reader.read();
				OverallDifficulty = reader.read();
			}
			SliderMultiplier = reader.readDouble();
			
			if(database.getVersion() >= 20140609){
				playModeStars = new ArrayList<HashMap<Integer, Double>>();
				for(int i = 0; i < 4; i++){
					HashMap<Integer, Double> dict = reader.readDictionary();
					playModeStars.add(dict);
				}
			}
			
			songLength = reader.readInt32();
			songLengthInMs = reader.readInt32();
			previewTime = reader.readInt32();
			list_1 = new ArrayList<TimingPoints>();
			reader.readList(database, list_1, TimingPoints.class);
			beatmapId = reader.readInt32();
			beatmapSetId = reader.readInt32();
			threadId = reader.readInt32();
			osuRanking = SongPickerUtils.getSafe(Rankings.values, Rankings.values.length - 1 - reader.read());
			catchTheBeatRanking = SongPickerUtils.getSafe(Rankings.values, Rankings.values.length - 1 - reader.read());
			taikoRanking = SongPickerUtils.getSafe(Rankings.values, Rankings.values.length - 1 - reader.read());
			maniaRanking = SongPickerUtils.getSafe(Rankings.values, Rankings.values.length - 1 - reader.read());
			int_13 = reader.readInt16();
			stackLeniency = reader.readSingle();
			playMode = SongPickerUtils.getSafe(PlayMode.values, reader.read());
			string_13 = reader.readString();
			tags = reader.readString();
			int_12 = reader.readInt16();
			floatingText = reader.readString();
			bool_13 = reader.readBoolean();
			lastPlayedDate = reader.readDate();
			bool_10 = reader.readBoolean();
			songPath = reader.readString();
			this.long_0 = reader.readInt64();
			bool_21 = reader.readBoolean();
			bool_22 = reader.readBoolean();
			bool_23 = reader.readBoolean();
			
			if(database.getVersion() >= 20130624){
				bool_24 = reader.readBoolean();
			}
			
			if(database.getVersion() >= 20130913){
				bool_25 = reader.readBoolean();
			}else{
				bool_25 = (bool_24 || bool_23) || bool_22;
			}
			
			if(database.getVersion() < 20140608){
				reader.readInt16();
			}
			
			int_7 = reader.readInt32();
			int_18 = reader.read();
			objectCount = circleCount + spinnerCount + sliderCount;
			bool_4 = beatmapHash != null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
