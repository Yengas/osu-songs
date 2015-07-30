package com.yengas.osusongpicker.criterias;

public class ContainsCriteria extends Criteria<String> {
	
	private String right;

	public ContainsCriteria(){ super(); }
	public ContainsCriteria(String right){ super(right); }

	@Override
	public void setDefault() {
		this.right = "";
	}

	@Override
	public boolean evaluate(String left) {
		return left.contains(this.right);
	}

	@Override
	public String toString() {
		return "cnts";
	}

	@Override
	public int getArgumentCount() {
		return 1;
	}

	@Override
	public void setParameters(Object... params) {
		if(params != null && params.length == 1 && params[0] != null && params[0] instanceof String){
			this.right = (String) params[0];
		}else{
			this.setDefault();
		}
	}

}
