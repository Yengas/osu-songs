package com.yengas.osusongpicker;

import com.yengas.osusongpicker.criterias.Criteria;

public class SearchOption {
	public final BeatmapAttribute attribute;
	public final Criteria criteria;
	
	public SearchOption(BeatmapAttribute attribute, Criteria criteria){
		this.attribute = attribute;
		this.criteria = criteria;
	}
}
