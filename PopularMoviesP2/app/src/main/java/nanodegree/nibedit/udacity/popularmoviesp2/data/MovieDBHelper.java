package nanodegree.nibedit.udacity.popularmoviesp2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import nanodegree.nibedit.udacity.popularmoviesp2.data.MovieDBContract.MovieEntry;


/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "favmovies.db";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieEntry._ID + " INTEGER PRIMARY KEY," +
                        MovieEntry.COLUMN_MOVIES_ID + " INTEGER NOT NULL," +
                        MovieEntry.COLUMN_VOTE_COUNT + " INTEGER," +
                        MovieEntry.COLUMN_MOVIE_NAME + " TEXT," +
                        MovieEntry.COLUMN_VIDEO + " TEXT," +
                        MovieEntry.COLUMN_POPULARITY + " INTEGER," +
                        MovieEntry.COLUMN_RATING + " DOUBLE," +
                        MovieEntry.COLUMN_OVERVIEW + " TEXT," +
                        MovieEntry.COLUMN_ADULT + " TEXT," +
                        MovieEntry.COLUMN_IMAGE_URL + " TEXT," +
                        MovieEntry.COLUMN_RELEASE_DATE + " TEXT," +
                       MovieEntry.COLUMN_BACKDROP_PATH + " TEXT," +
                        MovieEntry.COLUMN_LANGUAGE + " TEXT " +


                        " )";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);


       /* final String SQL_CREATE_REVIEW_TABLE =
                "CREATE TABLE " + MovieDBContract.ReviewEntry.TABLE_NAME + " (" +
                        ReviewEntry._ID + " INTEGER PRIMARY KEY," +
                        ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        ReviewEntry.COLUMN_REVIEW_API_ID + " TEXT UNIQUE NOT NULL, " +
                        ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                        ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + " ) ON DELETE CASCADE)";
        db.execSQL(SQL_CREATE_REVIEW_TABLE);

        final String SQL_CREATE_VIDEO_TABLE =
                "CREATE TABLE " + MovieDBContract.VideoEntry.TABLE_NAME + " (" +
                        VideoEntry._ID + " INTEGER PRIMARY KEY," +
                        VideoEntry.COLUMN_VIDEO_API_ID + " TEXT UNIQUE NOT NULL, " +
                        VideoEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        VideoEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        VideoEntry.COLUMN_SITE + " TEXT NOT NULL, " +
                        VideoEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                        VideoEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + VideoEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + " ) ON DELETE CASCADE)";
        db.execSQL(SQL_CREATE_VIDEO_TABLE);*/
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not needed for now
    }
}
