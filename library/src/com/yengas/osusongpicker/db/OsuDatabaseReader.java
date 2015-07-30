package com.yengas.osusongpicker.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OsuDatabaseReader extends BinaryReader {

	public OsuDatabaseReader(InputStream in) {
		super(in);
	}
	
	@Override
	public String readString() throws IOException{
		if(this.read() == 0){
			return null;
		}
		
		return super.readString();
	}
	
	public Date readDate() throws IOException{
		long unix = this.readInt64();
		if(unix > 0){
			unix = (unix - 621355968000000000L) / 10000;
		}
		return new Date( unix );
	}
	
	public byte[] readByteArray() throws IOException{
		int count = this.readInt32();
		if(count <= 0) return null;
		byte[] bytes = new byte[count];
		this.read(bytes);
		return bytes;
	}
	
	public char[] readCharArray() throws IOException{
		int count = this.readInt32();
		if(count <= 0) return null;
		return this.readChars(count);
	}
	
	public <T extends Serializeable> void readList(OsuDatabase database, ArrayList<T> list, Class<T> clazz) throws Exception{
		int length = this.readInt32();
		
		for(int i = 0; i < length; i++){
			T instance = clazz.newInstance();
			instance.readFromStream(database, this);
			list.add(instance);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T, U> HashMap<T, U> readDictionary() throws IOException{
		int count = this.readInt32();
		HashMap<T, U> hashMap = new HashMap<T, U>();
		
		for(int i = 0; i < count; i++){
			hashMap.put((T) this.readObject(), (U) this.readObject());
		}
		return hashMap;
	}
	
	public Object readObject() throws IOException{
		switch(this.read()){
		case 1:
			return this.readBoolean();
		case 2:
			return this.read();
		case 3:
			return this.readUInt16();
		case 4:
			return this.readUInt32();
		case 5:
			return this.readUInt64();
		case 6:
			return this.read();
		case 7:
			return this.readInt16();
		case 8:
			return this.readInt32();
		case 9:
			return this.readInt64();
		case 10:
			return this.readChar();
		case 11:
			return super.readString();
		case 12:
			return this.readSingle();
		case 13:
			return this.readDouble();
		case 14:
			return this.readDecimal();
		case 15:
			return this.readDate();
		case 16:
			return this.readByteArray();
		case 17:
			return this.readCharArray();
		case 18:
			throw new IOException("Unimplemented case for read object...");
		}
		return null;
	}

}
