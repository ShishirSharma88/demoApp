package com.mccollins.shishir.mccollins.splash.home;//package com.retro.shishir.retro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.model.MainData;
import com.mccollins.shishir.mccollins.splash.web.WebActivity;
import com.squareup.picasso.Picasso;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourViewHolder> {

    private Context context;
    private MainData tourPlacesList;
    private HomePresenterImpl homePresenterImpl;

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        LinearLayout tourLayout;
        TextView titleTextView;
        TextView text_description;
        TextView phoneNumberTextView;
        TextView distanceTextView;
        ImageView tourImage;

        TourViewHolder(View v) {
            super(v);

            tourLayout = (LinearLayout) v.findViewById(R.id.layout);
            titleTextView = (TextView) v.findViewById(R.id.text_title);
            text_description = (TextView) v.findViewById(R.id.text_description);
            tourImage = (ImageView) v.findViewById(R.id.image_tour);
            phoneNumberTextView = (TextView) v.findViewById(R.id.text_phonenumber);
            distanceTextView = (TextView) v.findViewById(R.id.textview_distance);
        }
    }

    TourListAdapter(MainData mainData, Context context, HomePresenterImpl homePresenterImpl) {
        this.tourPlacesList = mainData;
        this.context = context;
        this.homePresenterImpl = homePresenterImpl;
    }

    private float getDistance(final String lat, final String lng) {

        final float[] distance = new float[1];

        Location location1 = new Location("");
        location1.setLatitude(Double.parseDouble(lat));
        location1.setLongitude(Double.parseDouble(lng));

        Location location2 = homePresenterImpl.getLocation();
        distance[0] = location1.distanceTo(location2);

        return distance[0];
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tour, parent, false);
        return new TourViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TourViewHolder holder, int position) {
        holder.titleTextView.setText(tourPlacesList.getData().get(holder.getAdapterPosition()).getTitle());

        holder.phoneNumberTextView.setText("Call: " + tourPlacesList.getData().get(holder.getAdapterPosition()).getContact());
        holder.phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tourPlacesList.getData().get(holder.getAdapterPosition()).getContact().trim()));
                context.startActivity(callIntent);
            }
        });

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.distanceTextView.setText("Distance: "
                        + getDistance(tourPlacesList.getData().get(holder.getAdapterPosition()).getLatitude(),
                        tourPlacesList.getData().get(holder.getAdapterPosition()).getLongitude()));

            }
        }, 1000);

        holder.tourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("data", tourPlacesList.getData().get(holder.getAdapterPosition()).getSitelink());
                context.startActivity(intent);
            }
        });

        Picasso.with(context).load(tourPlacesList.getData().get(position).getImage()).into(holder.tourImage);
        holder.text_description.setText(tourPlacesList.getData().get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tourPlacesList.getData().size();
    }


}