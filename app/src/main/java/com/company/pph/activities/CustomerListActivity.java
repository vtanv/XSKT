package com.company.pph.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.company.pph.adapter.CustomerRecyclerAdapter;
import com.company.pph.model.Customer;
import com.company.pph.sql.DatabaseHelper;
import com.company.pph.R;

import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity {

    private AppCompatActivity activity = CustomerListActivity.this;
    Context context = CustomerListActivity.this;
    private RecyclerView recyclerViewCustomer;
    private ArrayList<Customer> listCustomer;
    private CustomerRecyclerAdapter customerRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    SearchView searchBox;
    private ArrayList<Customer> filteredList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_customer_list);
	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	initViews();
	initObjects();

	Intent intentThatStartedThisActivity = getIntent();
	if (intentThatStartedThisActivity.hasExtra("NAME")) {

	//get all needed extras intent
	int id = getIntent().getExtras().getInt("ID");
	String email = getIntent().getExtras().getString("EMAIL");
	String address = getIntent().getExtras().getString("ADDRESS");
	String country = getIntent().getExtras().getString("COUNTRY");

	} else {
	Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
	}

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
	recyclerViewCustomer = (RecyclerView) findViewById(R.id.recyclerViewCustomer);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
	listCustomer = new ArrayList<>();
	customerRecyclerAdapter = new CustomerRecyclerAdapter(listCustomer, this);

	RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
	recyclerViewCustomer.setLayoutManager(mLayoutManager);
	recyclerViewCustomer.setItemAnimator(new DefaultItemAnimator());
	recyclerViewCustomer.setHasFixedSize(true);
	recyclerViewCustomer.setAdapter(customerRecyclerAdapter);
	databaseHelper = new DatabaseHelper(activity);

	getDataFromSQLite();

    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
	// AsyncTask is used that SQLite operation not blocks the UI Thread.
	new AsyncTask<Void, Void, Void>() {
		@Override
		protected Void doInBackground(Void... params) {
		listCustomer.clear();
		listCustomer.addAll(databaseHelper. getAllBeneficiary());
		return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		customerRecyclerAdapter.notifyDataSetChanged();
		}
	}.execute();
    }
}
