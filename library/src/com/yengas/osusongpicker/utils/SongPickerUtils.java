package com.yengas.osusongpicker.utils;

public class SongPickerUtils {
	public static <T> T getSafe(T[] arr, int index){
		if(index < 0 || index >= arr.length) return null;
		return arr[index];
	}
}
