package com.yengas.osusongpicker;

import java.util.Date;

import com.yengas.osusongpicker.criterias.BetweenCriteria;
import com.yengas.osusongpicker.criterias.ContainsCriteria;
import com.yengas.osusongpicker.criterias.Criteria;
import com.yengas.osusongpicker.criterias.EqualsCriteria;
import com.yengas.osusongpicker.criterias.GreaterThanOrEqual;
import com.yengas.osusongpicker.criterias.LowerThanOrEqual;
import com.yengas.osusongpicker.db.Beatmap;

public class PickerUtils {
	
	public static Object getBeatmapAttributeByEnum(Beatmap beatmap, BeatmapAttribute attribute){
		switch(attribute){
			case ApproachRate:
				return beatmap.getApproachRate();
			case CircleSize:
				return beatmap.getCircleSize();
			case HPDrainRate:
				return beatmap.getHPDrainRate();
			case LastPlayed:
				return (float)((new Date().getTime() - beatmap.getLastPlayedDate().getTime()) / 1000);
			case OverallDifficulty:
				return beatmap.getOverallDifficulty();
			case SongLength:
				return (float)(beatmap.getSongLengthInMs() / 1000);
			case Title:
				return beatmap.getTitle();
			case RankAchieved:
				return beatmap.getOsuRanking().ordinal();
			case PlayMode:
				return beatmap.getPlayMode().ordinal();
			case SubmissionStatus:
				return beatmap.getSubmissionStatus().ordinal();
		}
		return null;
	}
}
