package com.yengas.osusongpicker.db;

public interface Serializeable {
	public void readFromStream(OsuDatabase database, OsuDatabaseReader reader);
}
