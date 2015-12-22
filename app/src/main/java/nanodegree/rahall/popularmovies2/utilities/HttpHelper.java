package nanodegree.rahall.popularmovies2.utilities;

/**
 * Created by rahall on 8/23/15.
 */
public class HttpHelper {
    private static final String key = "008a6ada949696651640b0b92d5c506c";
    private static final String apiKey = "api_key=";
    private static final String baseJsonDiscoverRequest = "http://api.themoviedb.org/3/discover/movie?";
    private static final String popularitySort = "sort_by=popularity.desc&";
    private static final String ratingsSort = "sort_by=vote_average.desc&vote_count.gte=500&";
    private static final String baseImageSize185 = "http://image.tmdb.org/t/p/w185";
    private static final String baseMovieDetailRequest = "http://api.themoviedb.org/3/movie/";
    private static final String ReviewsAndVideos ="&append_to_response=reviews,videos";

    public static String getSortByPopularHttpRequest() {
        return baseJsonDiscoverRequest + popularitySort + apiKey + key;
    }
    public static String getSortByRatingsHttpRequest() {
        return baseJsonDiscoverRequest + ratingsSort + apiKey + key;
    }

    public static String getImageSize185HttpRequest(String imageFile) {
        return baseImageSize185 + imageFile;
    }
    public static String getMovieDetailInfo(String id) {
        return baseMovieDetailRequest + id+ "?" + apiKey + key + ReviewsAndVideos;

    }


}
