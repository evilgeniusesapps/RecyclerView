package com.evilgeniusesapps.recyclerview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniusesapps.recyclerview.R;
import com.evilgeniusesapps.recyclerview.models.Customer;
import com.evilgeniusesapps.recyclerview.databases.TinyDB;

import java.util.ArrayList;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private ArrayList<Customer> customers;
    private Context context;
    private TinyDB tinyDB;

    public SimpleAdapter(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
        tinyDB = new TinyDB (context);
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ViewHolder viewHolder, final int position) {
        final Customer customer = customers.get(position);

        TextView textView = viewHolder.textViewName;
        textView.setText(customer.getName());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                customers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, customers.size());

                // Save data
                ArrayList<Object> playerObjects = new ArrayList<>();
                for(Customer a : customers){
                    playerObjects.add((Object)a);
                }

                tinyDB.putListObject("arrayCustomers", playerObjects);
            }
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}