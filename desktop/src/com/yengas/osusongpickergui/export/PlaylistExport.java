package com.yengas.osusongpickergui.export;

import java.io.File;
import java.io.FileOutputStream;

import com.iheartradio.m3u8.Encoding;
import com.iheartradio.m3u8.Format;
import com.iheartradio.m3u8.PlaylistWriter;
import com.iheartradio.m3u8.data.MediaPlaylist;
import com.iheartradio.m3u8.data.Playlist;
import com.iheartradio.m3u8.data.TrackData;
import com.iheartradio.m3u8.data.TrackInfo;

public class PlaylistExport extends Exporter<TrackData> {

	@Override
	public boolean output(File file) throws Exception {
		if(file.isDirectory()) return false;
		MediaPlaylist mediaPlaylist = new MediaPlaylist.Builder()
				.withMediaSequenceNumber(1).withTargetDuration(3)
				.withTracks(singles).build();

		Playlist playlist = new Playlist.Builder().withCompatibilityVersion(5)
				.withMediaPlaylist(mediaPlaylist).build();

		FileOutputStream outputStream = new FileOutputStream(file);
		PlaylistWriter writer = new PlaylistWriter();
		writer.write(outputStream, playlist, Format.EXT_M3U, Encoding.UTF_8);
		outputStream.close();
		return true;
	}

	@Override
	protected TrackData getSingle(File input, String name) {
		return new TrackData.Builder().withPath(input.getAbsolutePath()).withTrackInfo(new TrackInfo(-1, name)).build();
	}

	@Override
	public String getExtension() {
		return "*.m3u8";
	}

	@Override
	public String getInitialName() {
		return "playlist.m3u8";
	}

}
