package com.yengas.osusongpicker.criterias;

public abstract class Criteria<T> {
	public Criteria(){
		this.setDefault();
	}
	public Criteria(Object... params){ this.setParameters(params); }
	public abstract void setDefault();
	public abstract boolean evaluate(T arg);
	public abstract String toString();
	public abstract int getArgumentCount();
	public abstract void setParameters(Object... params);

	private static final Class<? extends Criteria>[] numbers = new Class[]{ BetweenCriteria.class, EqualsCriteria.class, GreaterThanOrEqual.class, LowerThanOrEqual.class },
													string = new Class[]{ ContainsCriteria.class, EqualsCriteria.class };
	public static Class<? extends Criteria>[] getCriteriasForClass(Class klass){
		if(klass == String.class)
			return string;
		else if(klass == Float.class || klass.isEnum())
			return numbers;
		return null;
	}
}
