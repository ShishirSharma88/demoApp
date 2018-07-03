package com.mccollins.shishir.mccollins.splash.home;//package com.retro.shishir.retro.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.api.SingleShotLocationProvider;
import com.mccollins.shishir.mccollins.splash.listener.TaskListener;
import com.mccollins.shishir.mccollins.splash.model.MainData;
import com.mccollins.shishir.mccollins.splash.web.WebActivity;
import com.squareup.picasso.Picasso;

import java.security.Provider;
import java.util.ArrayList;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.TourViewHolder> {

    private Context context;
    private MainData tourPlacesList;
    private LocationManager locationManager;

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        LinearLayout tourLayout;
        TextView titleTextView;
        TextView text_description;
        TextView phoneNumberTextView;
        TextView distanceTextView;
        ImageView tourImage;

        public TourViewHolder(View v) {
            super(v);

            tourLayout = (LinearLayout) v.findViewById(R.id.layout);
            titleTextView = (TextView) v.findViewById(R.id.text_title);
            text_description = (TextView) v.findViewById(R.id.text_description);
            tourImage = (ImageView) v.findViewById(R.id.image_tour);
            phoneNumberTextView = (TextView) v.findViewById(R.id.text_phonenumber);
            distanceTextView = (TextView) v.findViewById(R.id.textview_distance);
        }
    }

    public TourListAdapter(MainData mainData, Context context) {
        this.tourPlacesList = mainData;
        this.context = context;
    }

    float getDistance(final String lat, final String lng) {

        final float[] distance = new float[1];
        try {
            SingleShotLocationProvider.requestSingleUpdate(context,
                    new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            Log.d("Location", "my location is " + location.toString());
                            Location location1 = new Location("");
                            location1.setLatitude(Double.parseDouble(lat));
                            location1.setLongitude(Double.parseDouble(lng));

                            Location location2 = new Location("");

                            location2.setLatitude(location.latitude);
                            location2.setLongitude(location.longitude);
                            distance[0] = location1.distanceTo(location2);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance[0];
    }

    @Override
    public TourViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tour, parent, false);
        return new TourViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TourViewHolder holder, final int position) {
        holder.titleTextView.setText(tourPlacesList.getData().get(position).getTitle());

        holder.phoneNumberTextView.setText("Call: " + tourPlacesList.getData().get(position).getContact());
        holder.phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tourPlacesList.getData().get(position).getContact().trim()));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((HomeActivity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        holder.distanceTextView.setText("Distance: "
                + getDistance(tourPlacesList.getData().get(position).getLatitude(),
                tourPlacesList.getData().get(position).getLongitude()));

        holder.tourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("data", tourPlacesList.getData().get(position).getSitelink());
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