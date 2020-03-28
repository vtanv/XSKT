package com.company.pph.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.company.pph.R;
import com.company.pph.model.Customer;

import java.util.ArrayList;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.CustomerViewHolder> {

    private ArrayList<Customer> listCustomer;
    public ImageView overflow;
    private Context mContext;
    private ArrayList<Customer> mFilteredList;


    public CustomerRecyclerAdapter(ArrayList<Customer> listCustomer, Context mContext) {
        this.listCustomer = listCustomer;
        this.mContext = mContext;
        this.mFilteredList = listCustomer;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewAddress;
        public AppCompatTextView textViewCountry;
        public ImageView overflow;

        public CustomerViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewAddress = (AppCompatTextView) view.findViewById(R.id.textViewAddress);
            textViewCountry = (AppCompatTextView) view.findViewById(R.id.textViewCountry);

        }
    }
    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_recycler, parent, false);

        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomerViewHolder holder, int position) {
        holder.textViewName.setText(listCustomer.get(position).getName());
        holder.textViewEmail.setText(listCustomer.get(position).getEmail());
        holder.textViewAddress.setText(listCustomer.get(position).getAddress());
        holder.textViewCountry.setText(listCustomer.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
}
