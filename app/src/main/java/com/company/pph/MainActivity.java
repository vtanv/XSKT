package com.company.pph;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.company.pph.R;
import com.company.pph.helper.InputValidation;
import com.company.pph.model.Customer;
import com.company.pph.activities.CustomerListActivity;
import com.company.pph.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MainActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutCustomerName;
    private TextInputLayout textInputLayoutCustomerEmail;
    private TextInputLayout textInputLayoutCustomerAddress;
    private TextInputLayout textInputLayoutCustomerCountry;
    private TextInputLayout textInputLayoutCustomerId;

    private TextInputEditText textInputEditTextCustomerName;
    private TextInputEditText textInputEditTextCustomerEmail;
    private TextInputEditText textInputEditTextCustomerAddress;
    private TextInputEditText textInputEditTextCustomerCountry;
    private TextInputEditText textInputEditTextCustomerId;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewCustomerList;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Customer customer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();
        initListeners();
    }

    //Initialize Views
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutCustomerName = (TextInputLayout) findViewById(R.id.textInputLayoutCustomerName);
        textInputLayoutCustomerEmail = (TextInputLayout) findViewById(R.id.textInputLayoutCustomerEmail);
        textInputLayoutCustomerAddress = (TextInputLayout) findViewById(R.id.textInputLayoutCustomerAddress);
        textInputLayoutCustomerCountry = (TextInputLayout) findViewById(R.id.textInputLayoutCustomerCountry);
        textInputLayoutCustomerId = (TextInputLayout) findViewById(R.id.textInputLayoutCustomerId);

        textInputEditTextCustomerName = (TextInputEditText) findViewById(R.id.textInputEditTextCustomerName);
        textInputEditTextCustomerEmail = (TextInputEditText) findViewById(R.id.textInputEditTextCustomerEmail);
        textInputEditTextCustomerAddress = (TextInputEditText) findViewById(R.id.textInputEditTextCustomerAddress);
        textInputEditTextCustomerCountry = (TextInputEditText) findViewById(R.id.textInputEditTextCustomerCountry);
        textInputEditTextCustomerId = (TextInputEditText) findViewById(R.id.textInputEditTextCustomerId);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewCustomerList = (AppCompatTextView) findViewById(R.id.appCompatTextViewCustomerList);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewCustomerList.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        customer = new Customer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewCustomerList:
                Intent accountsIntent = new Intent(activity, CustomerListActivity.class);
                accountsIntent.putExtra("ID", textInputEditTextCustomerId.getText().toString().trim());
                accountsIntent.putExtra("NAME", textInputEditTextCustomerName.getText().toString().trim());
                accountsIntent.putExtra("EMAIL", textInputEditTextCustomerEmail.getText().toString().trim());
                accountsIntent.putExtra("ADDRESS", textInputEditTextCustomerAddress.getText().toString().trim());
                accountsIntent.putExtra("COUNTRY", textInputEditTextCustomerCountry.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextCustomerName, textInputLayoutCustomerName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextCustomerId, textInputLayoutCustomerId, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextCustomerEmail, textInputLayoutCustomerEmail, getString(R.string.error_message_email))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextCustomerEmail.getText().toString().trim())) {

            customer.setId(Integer.parseInt(textInputEditTextCustomerId.getText().toString().trim()));
            customer.setName(textInputEditTextCustomerName.getText().toString().trim());
            customer.setEmail(textInputEditTextCustomerEmail.getText().toString().trim());
            customer.setAddress(textInputEditTextCustomerAddress.getText().toString().trim());
            customer.setCountry(textInputEditTextCustomerCountry.getText().toString().trim());


            databaseHelper.addBeneficiary(customer);

            // Snack Bar to show success message that record saved successfully
            Intent accountsIntent = new Intent(activity, CustomerListActivity.class);
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT)
                    .show();
            accountsIntent.putExtra("ID", textInputEditTextCustomerId.getText().toString().trim());
            accountsIntent.putExtra("NAME", textInputEditTextCustomerName.getText().toString().trim());
            accountsIntent.putExtra("EMAIL", textInputEditTextCustomerEmail.getText().toString().trim());
            accountsIntent.putExtra("ADDRESS", textInputEditTextCustomerAddress.getText().toString().trim());
            accountsIntent.putExtra("COUNTRY", textInputEditTextCustomerCountry.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText() {
        textInputEditTextCustomerName.setText(null);
        textInputEditTextCustomerEmail.setText(null);
        textInputEditTextCustomerAddress.setText(null);
        textInputEditTextCustomerCountry.setText(null);
    }
}
