package nanodegree.nibedit.udacity.popularmoviesp2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieDetails implements Parcelable {


    private int movieID;
    private String movieOriginalTitle;
    private double movieRating;
    private String moviePlot;
    private String movieDate;
    private String imageThumbnail;
    private String imageBackDrop;
    private String adultType;
    private String originalLangauge;
    private int popularity;
    private int voteCount;
    private String videoAvailable;
    private boolean isFavourite;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public MovieDetails() {

    }

    public String getAdultType() {
        return adultType;
    }

    public void setAdultType(String adultType) {
        this.adultType = adultType;
    }

    public String getOriginalLangauge() {
        return originalLangauge;
    }

    public void setOriginalLangauge(String originalLangauge) {
        this.originalLangauge = originalLangauge;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideoAvailable() {
        return videoAvailable;
    }

    public void setVideoAvailable(String videoAvailable) {
        this.videoAvailable = videoAvailable;
    }


    public String getImageBackDrop() {
        return imageBackDrop;
    }

    public void setImageBackDrop(String imageBackDrop) {
        this.imageBackDrop = imageBackDrop;
    }


    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public void setMovieOriginalTitle(String movieOriginalTitle) {
        this.movieOriginalTitle = movieOriginalTitle;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        public MovieDetails createFromParcel(Parcel source) {
            MovieDetails movieDetails = new MovieDetails();
            movieDetails.movieID = source.readInt();
            movieDetails.moviePlot = source.readString();
            movieDetails.imageThumbnail = source.readString();
            movieDetails.movieOriginalTitle = source.readString();
            movieDetails.movieDate = source.readString();
            movieDetails.movieRating = source.readDouble();
            movieDetails.imageBackDrop = source.readString();
            movieDetails.videoAvailable = source.readString();
            movieDetails.adultType = source.readString();
            movieDetails.popularity = source.readInt();
            movieDetails.voteCount = source.readInt();
            movieDetails.originalLangauge = source.readString();
            return movieDetails;
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];

        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieID);
        dest.writeString(moviePlot);
        dest.writeString(imageThumbnail);
        dest.writeString(movieOriginalTitle);
        dest.writeString(movieDate);
        dest.writeDouble(movieRating);
        dest.writeString(imageBackDrop);
        dest.writeString(videoAvailable);
        dest.writeString(adultType);
        dest.writeInt(popularity);
        dest.writeInt(voteCount);
        dest.writeString(originalLangauge);

    }
}
