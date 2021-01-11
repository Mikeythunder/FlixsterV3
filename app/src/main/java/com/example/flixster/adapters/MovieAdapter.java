package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout Container;
        TextView tvTitle;
        ItemMovieBinding binding;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            Container = itemView.findViewById(R.id.Container);
            imageView = itemView.findViewById(R.id.playerIcon);



        }

        public void bind(final Movie movie) {
            if(movie.getRating() > 5){
                imageView.setVisibility(View.VISIBLE);
            }
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }


            int radius = 100;
            int margin = 100;


            Glide.with(context).load(imageURL).apply(new RequestOptions().fitCenter().transforms(new RoundedCorners(radius))).into(ivPoster);

//            Glide.with(context).load(imageURL).into(ivPoster);
            //1. Register onClick to work for entire row of information
            Container.setOnClickListener(new View.OnClickListener() {
                @Override
                //2. Navigate to new activity
                public void onClick(View v) {

                    Intent i = new Intent(context, DetailActivity.class);

//                    i.putExtra("title", movie.getTitle());
                    //INSTEAD OF PASSING IN INDIVIDUAL FIELDS, JUST PASS IN THE ENTIRE MOVIE
                        //Parcels allows for movie to be parsed and hence used as the only putExtra
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, tvTitle, ViewCompat.getTransitionName(tvTitle));

                    context.startActivity(i, options.toBundle());

                }
            });
        }
    }
}
