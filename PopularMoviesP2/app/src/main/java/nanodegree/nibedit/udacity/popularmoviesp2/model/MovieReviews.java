package nanodegree.nibedit.udacity.popularmoviesp2.model;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieReviews {

    public MovieReviews(){}

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String id;
    private String author;
    private String content;
}
