package com.yengas.osusongpicker.criterias;

import com.sun.org.apache.xpath.internal.operations.Equals;

public class EqualsCriteria extends Criteria<Object>{
	
	private Object right;

	public EqualsCriteria(){ super(); }
	public EqualsCriteria(Object right){
		super(right);
	}

	@Override
	public boolean evaluate(Object left) {
		return left.equals(this.right);
	}

	@Override
	public void setDefault() {
		this.right = null;
	}

	@Override
	public String toString() {
		return "==";
	}

	@Override
	public int getArgumentCount() {
		return 1;
	}

	@Override
	public void setParameters(Object... params) {
		if(params != null && params.length == 1 && params[0] != null){
			this.right = params[0];
		}else{
			this.setDefault();
		}
	}

}
