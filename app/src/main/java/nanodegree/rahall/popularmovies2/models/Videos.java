package nanodegree.rahall.popularmovies2.models;

import java.util.ArrayList;

/**
 * Created by rahall on 11/28/15.
 */
public class Videos {
    private ArrayList<Video> videos;
    public Videos() {
        videos = new ArrayList<>();
    }
    public ArrayList<Video> getVideos() {
        return videos;
    }
    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
    public void addVideo(Video video) {
        videos.add(video);
    }
    public void deleteVideo(Video video) {
        videos.remove(video);
    }

    public Video getVideo(int i) {
        return videos.get(i);
    }

    public int getSize() {
        return videos.size();
    }
}
