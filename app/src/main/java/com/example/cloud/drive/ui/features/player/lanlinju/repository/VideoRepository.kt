package com.example.composevideoplayer.repository

import com.example.composevideoplayer.model.Episode
import com.example.composevideoplayer.model.Video

class VideoRepository {
    fun getVideo(url: String, title: String): Video {
        val index = samplePlayList.indexOfFirst { it.url == url }
        return Video(
            title = title,
            url = url,
            episodeName = "",
            currentEpisodeIndex = index,
            episodes = samplePlayList
        )
    }
}


/**
 * Sample video from https://github.com/google/ExoPlayer/blob/release-v2/demos/cast/src/main/java/com/google/android/exoplayer2/castdemo/DemoUtil.java
 */
val samplePlayList = listOf(
    Episode(
        name = "KOTOKO (ことこ)-恋ひ恋う縁 (以恋结缘).mp4",
        url = "https://www.dluserver.cn:8080/api/files/download?fileId=677&preview=true",
    ),
    Episode(
        name = "SWIN-S-只因你太美.mp3",
        url = "https://www.dluserver.cn:8080/api/files/download?fileId=225&preview=true",
    ),
    Episode(
        name = "Clear MP4: Dizzy",
        url = "https://html5demos.com/assets/dizzy.mp4",
    )
)