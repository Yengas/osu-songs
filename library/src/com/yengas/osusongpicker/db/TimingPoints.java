package com.yengas.osusongpicker.db;

public class TimingPoints implements Serializeable {
	private double double_0, double_1;
	private boolean bool_0;
	
	public TimingPoints(){
		
	}

	@Override
	public void readFromStream(OsuDatabase database, OsuDatabaseReader reader) {
		try{
			double_0 = reader.readDouble();
			double_1 = reader.readDouble();
			bool_0 = reader.readBoolean();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
