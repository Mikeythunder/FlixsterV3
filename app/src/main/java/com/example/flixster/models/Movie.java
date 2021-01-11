package com.example.flixster.models;

import androidx.databinding.DataBindingUtil;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    int movieId;


    //Empty constructor needed by Parcel library
    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {

        rating = jsonObject.getDouble("vote_average");
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        movieId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJSONArray.length(); i++) {
            movies.add(new Movie(movieJSONArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath(){
        return  String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId(){
        return movieId;
    }
}
