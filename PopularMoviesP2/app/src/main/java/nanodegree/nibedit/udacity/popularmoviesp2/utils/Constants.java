package nanodegree.nibedit.udacity.popularmoviesp2.utils;

public class Constants {
    public final static String PARCELABLE_KEY = "nanodegree.nibedit.udacity.popularmoviesp2.parcelable";
    public static final String MOVIE_DB_API_KEY = "ENTER_YOUR_API_KEY_HERE";
    public static final String MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String MOVIE_DB_IMAGE_BASE_URL_w500 = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_DB_SORT_BASE_URL="http://api.themoviedb.org/3/discover/movie";
    public static final String MOVIE_DB_SORT_POPULAR_URL = "popularity.desc";
    public static final String MOVIE_DB_SORT_VOTE_AVERAGE_URL = "vote_average.desc";
    public static final String MOVIE_DB_REVIEWS_URL = "http://api.themoviedb.org/3/movie/$/reviews?api_key=" + MOVIE_DB_API_KEY;
    public static final String MOVIE_DB_TRAILERS_URL = "http://api.themoviedb.org/3/movie/$/videos?api_key=" + MOVIE_DB_API_KEY;

    public static final String YOUTUBE_TRAILER_THUMBNAIL="http://img.youtube.com/vi/$/0.jpg";
    /*API Constants*/
    public static final String
            KEY_ADULT = "adult",
            KEY_BACKDROP_PATH = "backdrop_path",
            KEY_ID = "id",
            KEY_ORIGINAL_LANGUAGE = "original_language",
            KEY_ORIGINAL_TITLE = "original_title",
            KEY_OVERVIEW = "overview",
            KEY_RELEASE_DATE = "release_date",
            KEY_POSTER_PATH = "poster_path",
            KEY_POPULARITY = "popularity",
            KEY_VIDEO = "video",
            KEY_VOTE_AVEARGE = "vote_average",
            KEY_VOTE_COUNT = "vote_count";
    /*For Movie Reviews*/
    public static final String
            KEY_MOVIE_REVIEW_ID = "id",
            KEY_MOVIE_REVIEW_AUTHOR = "author",
            KEY_MOVIE_REVIEW_CONTENT = "content";

    /*For Movie Trailer"*/

    public static final String
            KEY_MOVIE_TRAILER_KEY = "key",
            KEY_MOVIE_TRAILER_SITE = "site";



}
