package nanodegree.rahall.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by rahall on 11/28/15.
 */
public class Videos implements Parcelable{
    private ArrayList<Video> videos;
    public Videos() {
        videos = new ArrayList<>();
    }
    public Videos(Parcel in) {
        videos = in.readArrayList(Video.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(videos);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }


    };
}
