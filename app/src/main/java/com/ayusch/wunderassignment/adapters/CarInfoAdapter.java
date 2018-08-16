package com.ayusch.wunderassignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;
import com.daimajia.numberprogressbar.NumberProgressBar;

import org.w3c.dom.Text;

import java.util.List;

public class CarInfoAdapter extends RecyclerView.Adapter<CarInfoAdapter.CarsViewHolder> {

    List<CarsInfoResponse.PlaceMarks> placeMarksList;
    CarClickedListener listener;
    Context context;

    public CarInfoAdapter(List<CarsInfoResponse.PlaceMarks> placeMarksList, CarClickedListener listener, Context context) {
        this.placeMarksList = placeMarksList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_info_item, parent, false);
        return new CarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final CarsInfoResponse.PlaceMarks placeMarks = placeMarksList.get(position);

        holder.expandableCardView.setTitle(placeMarks.getAddress());

        holder.expandableCardView.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View view, boolean isExpanded) {
                TextView address = view.findViewById(R.id.car_address);
                TextView coordinates = view.findViewById(R.id.car_coordinates);
                TextView engineType = view.findViewById(R.id.car_engineType);
                TextView exterior = view.findViewById(R.id.car_exterior);
                TextView interior = view.findViewById(R.id.car_interior);
                TextView name = view.findViewById(R.id.car_name);
                TextView vin = view.findViewById(R.id.car_vin);
                NumberProgressBar fuel = view.findViewById(R.id.car_fuel);

                address.setText(placeMarks.getAddress());
                coordinates.setText(String.format("%s , %s", String.valueOf(placeMarks.getCoordinates().get(0)), String.valueOf(placeMarks.getCoordinates().get(1))));
                engineType.setText(placeMarks.getEngineType());
                exterior.setText(placeMarks.getExterior());
                fuel.setProgress(placeMarks.getFuel());
                interior.setText(placeMarks.getInterior());
                name.setText(placeMarks.getName());
                vin.setText(placeMarks.getVin());
            }
        });

    }


    @Override
    public int getItemCount() {
        return placeMarksList.size();
    }

    public class CarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ExpandableCardView expandableCardView;

        public CarsViewHolder(View itemView) {
            super(itemView);
            expandableCardView = itemView.findViewById(R.id.car);
        }

        @Override
        public void onClick(View v) {
            listener.onCarClicked(placeMarksList.get(getAdapterPosition()));
        }
    }

    public interface CarClickedListener {
        void onCarClicked(CarsInfoResponse.PlaceMarks placeMark);
    }

}
