package com.yengas.osusongpicker;

import java.io.File;
import java.util.ArrayList;

import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpicker.criterias.EqualsCriteria;
import com.yengas.osusongpicker.criterias.GreaterThanOrEqual;
import com.yengas.osusongpicker.criterias.LowerThanOrEqual;
import com.yengas.osusongpicker.db.Beatmap;
import com.yengas.osusongpicker.db.OsuDatabase;

public class OsuSongPicker{
	
	private final File exeDirectory, songDirectory;
	private OsuDatabase database;
	
	public OsuSongPicker(String exePath){
		this(exePath, exePath + "/Songs");
	}
	
	public OsuSongPicker(String exePath, String songPath){
		this.exeDirectory = new File(exePath);
		this.songDirectory = new File(songPath);
		database = new OsuDatabase(new File(exeDirectory, "osu!.db"));
	}
	
	public ArrayList<Beatmap> pick(SearchOption... options){
		return this.pick(database.getBeatmapList(), options);
	}

	public boolean satisfy(SearchOption[] options, Beatmap beatmap){
		for(SearchOption option : options)
			if(option.criteria.evaluate(PickerUtils.getBeatmapAttributeByEnum(beatmap, option.attribute)) == false) return false;

		return true;
	}
	
	public ArrayList<Beatmap> pick(ArrayList<Beatmap> searchList, SearchOption... options){
		if(options.length == 0) return searchList;
		ArrayList<Beatmap> matched = new ArrayList<Beatmap>();
		
		for(Beatmap beatmap : searchList)
			if(satisfy(options, beatmap) && new File(songDirectory, beatmap.getSongPath()).exists()) matched.add(beatmap);
		
		return matched;
		
	}

	public static boolean validateDirectory(File directory){
		if(directory == null || directory.exists() == false || directory.isDirectory() == false || new File(directory, "osu!.exe").exists() == false || new File(directory, "osu!.db").exists() == false)
			return false;

		File songDirectory = new File(directory, "Songs");
		if(songDirectory.exists() == false || songDirectory.isDirectory() == false)
			return false;

		return true;
	}

	public static void main(String[] args) {
		SearchOption[] options = new SearchOption[]{
				new SearchOption(BeatmapAttribute.ApproachRate, new GreaterThanOrEqual(8.0f)),
				new SearchOption(BeatmapAttribute.OverallDifficulty, new LowerThanOrEqual(5.0f)),
				new SearchOption(BeatmapAttribute.Title, new EqualsCriteria("Gigantic O.T.N"))
		};

		OsuSongPicker picker = new OsuSongPicker("C:\\Users\\Yengas\\AppData\\Local\\osu!");
		ArrayList<Beatmap> beatmaps = picker.pick(options);

		for(Beatmap beatmap : beatmaps){
			System.out.println(beatmap.getTitle() + " - " + beatmap.getDifficulty());
		}

		System.out.println("Total found: " + beatmaps.size());
	}
	
}