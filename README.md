osu!songs
========
osu!songs is a tiny gui tool written with JavaFX to filter and export OSU! songs. It works by reversing the osu!.db file and using the information in there to filter beatmaps.

### Usage
![Tutorial](http://i.imgur.com/jFlQf2S.gif)

Download the .jar file from the latest releases or clone the project and build it with gradle. Then you can double click the jar file to launch the tool. After you have launched the tool, apply filters according to your needs and press the "Filter and add" button. This button searches the whole osu!.db database with the specified criterias and adds the results to your 'Batch List'.

If you want to remove the beatmaps you have found from your computer; click the 'Remove Songs' button.
If you want to export the beatmaps you have found into a .zip file or .m3u8 playlist; click the 'Export' button.

'Filter and add' button adds beatmaps to your batch list, so if you filter for the beatmaps you've played in the last 1 month and then filter for the beatmaps you got S+ rank on, your 'Batch List' will consists of the beatmaps you have played in the last 1 month and the beatmaps you got A+ rank on. If you only want to see the beatmaps you got A+ rank on, you need to clear your 'Batch List' before filtering for the second time. You can clear your batch list by clicking the 'Clear list' button.

### Filter Types

- Numeric: You can enter any decimal integers for numeric attributes. (e.g. 4.5, 7, 1.23)

- Date: You can use smhdMy (seconds, minutes, hours, days, months, years) while filtering for Last Played. (e.g. 1d, 1M 25d, 1h 45m)

- String: You can any UTF-8 string.

- Choice: Choose one of the Enum types from the dropdown list to filter Enum attributes.

## Library

Library is the core implementation of osu!songs database searching feature. It includes a class called OsuSongPicker which is pretty easy to use if you want to use osu!songs's library in your own project. Here is an example snippet.

```java
SearchOption[] options = new SearchOption[]{
		new SearchOption(BeatmapAttribute.ApproachRate, new GreaterThanOrEqual(8.0f)),
		new SearchOption(BeatmapAttribute.OverallDifficulty, new LowerThanOrEqual(5.0f)),
		new SearchOption(BeatmapAttribute.Title, new EqualsCriteria("Gigantic O.T.N"))
};

OsuSongPicker picker = new OsuSongPicker("C:\\Users\\Yengas\\AppData\\Local\\osu!");
ArrayList<Beatmap> beatmaps = picker.pick(options);

for(Beatmap beatmap : beatmaps){
	System.out.println(beatmap.getTitle() + " - " + beatmap.getDifficulty());
}

System.out.println("Total found: " + beatmaps.size());
```
