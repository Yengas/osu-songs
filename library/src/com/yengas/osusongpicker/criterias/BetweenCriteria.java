package com.yengas.osusongpicker.criterias;

import java.util.Arrays;

public class BetweenCriteria extends Criteria<Comparable> {
	
	private Comparable min, max;

	public BetweenCriteria(){ super(); }
	public BetweenCriteria(Comparable arg1, Comparable arg2){
		super(arg1, arg2);
	}

	@Override
	public void setDefault() {
		this.min = Float.MIN_VALUE;
		this.max = Float.MAX_VALUE;
	}

	@Override
	public boolean evaluate(Comparable left) {
		return left.compareTo(this.min) >= 0 && left.compareTo(this.max) <= 0;
	}

	@Override
	public String toString() {
		return "<=>";
	}

	@Override
	public int getArgumentCount() {
		return 2;
	}

	@Override
	public void setParameters(Object... params) {
		if(params != null && params.length == 2 && params[0] != null && params[1] != null && params[0] instanceof Comparable && params[1] instanceof Comparable){
			Comparable[] ordered = BetweenCriteria.orderParameters((Comparable[]) params);

			this.min = ordered[0];
			this.max = ordered[1];
		}else{
			this.setDefault();
		}
	}

	private static Comparable[] orderParameters(Comparable... params){
		Arrays.sort(params);
		return params;
	}

}
