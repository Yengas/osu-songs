package com.yengas.osusongpicker.criterias;

public class GreaterThanOrEqual extends Criteria<Comparable> {

	private Comparable right;

	public GreaterThanOrEqual(){ super(); }
	public GreaterThanOrEqual(Comparable right){
		super(right);
	}

	@Override
	public boolean evaluate(Comparable left) {
		return left.compareTo(this.right) >= 0;
	}

	@Override
	public void setDefault() {
		this.right = Float.MIN_VALUE;
	}

	@Override
	public String toString() {
		return ">=";
	}

	@Override
	public int getArgumentCount() {
		return 1;
	}

	@Override
	public void setParameters(Object... params) {
		if(params != null && params.length == 1 && params[0] != null && params[0] instanceof Comparable){
			this.right = (Comparable) params[0];
		}else{
			this.setDefault();
		}
	}

}
