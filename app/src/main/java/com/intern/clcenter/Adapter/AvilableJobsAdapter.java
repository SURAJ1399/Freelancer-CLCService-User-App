package com.intern.clcenter.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.intern.clcenter.Fragment.ArtistProfileFragment;

import com.intern.clcenter.Model.AvilableArtistModel;
import com.intern.clcenter.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AvilableJobsAdapter extends RecyclerView.Adapter<AvilableJobsAdapter.ViewHolder>{


    public Context context;

   public List<AvilableArtistModel> avilableArtistModelList;


    public AvilableJobsAdapter(List<AvilableArtistModel> avilableArtistModelList) {
        this.avilableArtistModelList = avilableArtistModelList;

    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_avilable_artistst,viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

       holder.artistwork.setText(avilableArtistModelList.get(position).getWorkcat());
       holder.artistname.setText(avilableArtistModelList.get(position).getFullname());

        holder.location.setText(avilableArtistModelList.get(position).getLocation());
        holder.jobdone.setText(avilableArtistModelList.get(position).getJobcomp());
        String featured=avilableArtistModelList.get(position).getFeatured();
        String ratetype=avilableArtistModelList.get(position).getRatetype();
        if(ratetype.equals("FIXED"))
        {
            holder.artistcharge.setText("₹ "+avilableArtistModelList.get(position).getPrice()+" Fixed Rate");

        }
        else {
            holder.artistcharge.setText("₹ "+avilableArtistModelList.get(position).getPrice()+" Hourly Rate");

        }

        if(featured.equals("1"))
            holder.featured.setVisibility(View.VISIBLE);
        else
            holder.featured.setVisibility(View.INVISIBLE);

        int rating =avilableArtistModelList.get(position).getRating();
        holder.ratingBar.setRating(rating);
        holder.setblogimage(avilableArtistModelList.get(position).getArtispic());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context, ""+avilableArtistModelList.get(position).getUsername(), Toast.LENGTH_SHORT).show();

             //   Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show()
                String usernme= avilableArtistModelList.get(position).getUsername();
                Bundle bundle=new Bundle();
                bundle.putString("username",usernme);

                SharedPreferences.Editor editor = context.getSharedPreferences("xyz", Context.MODE_PRIVATE).edit();
                editor.putString("usernam", usernme);
                editor.apply();


                ArtistProfileFragment artistprofile=new ArtistProfileFragment();
                artistprofile.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.discover_fragment, artistprofile).addToBackStack(null).commit();



            }
        });



    }

    @Override
    public int getItemCount() {
        return avilableArtistModelList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView artistwork, artistname, artistcharge, location, jobdone;
        ImageView fav, featured;
        RatingBar ratingBar;
        CircleImageView artistpic;
        View mView;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            artistwork = itemView.findViewById(R.id.artistwork);
            artistname = itemView.findViewById(R.id.artistname);
            artistcharge = itemView.findViewById(R.id.artistcharge);
            location = itemView.findViewById(R.id.location);
            jobdone = itemView.findViewById(R.id.jobdone);
            fav = itemView.findViewById(R.id.fav);
            featured = itemView.findViewById(R.id.featured);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            artistpic = itemView.findViewById(R.id.artistpic);
            cardView=itemView.findViewById(R.id.cardview);

        }


        public void setblogimage(String imageurl) {

            Glide.with(context).load(imageurl).into(artistpic);


        }



    }



}
