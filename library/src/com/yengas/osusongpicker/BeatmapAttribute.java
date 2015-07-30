package com.yengas.osusongpicker;

import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpicker.db.Beatmap;

public enum BeatmapAttribute {
	HPDrainRate(Float.class), 
	CircleSize(Float.class), 
	OverallDifficulty(Float.class), 
	ApproachRate(Float.class), 
	LastPlayed(Float.class), 
	SongLength(Float.class),
	Title(String.class),
	RankAchieved(Beatmap.Rankings.class),
	PlayMode(Beatmap.PlayMode.class),
	SubmissionStatus(Beatmap.SubmissionStatus.class);
	
	private final Class type;
	private final Class<? extends Criteria>[] criterias;

	private BeatmapAttribute(Class<?> type){
		this.type = type;
		this.criterias = Criteria.getCriteriasForClass(type);
	}

	public Class<? extends Criteria>[] getApplyableCriterias() { return this.criterias; }
	public Class getType(){
		return this.type;
	}
}
