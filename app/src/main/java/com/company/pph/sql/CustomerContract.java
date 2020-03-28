package com.company.pph.sql;

import android.provider.BaseColumns;

public class CustomerContract {

    public static final class CustomerEntry implements BaseColumns {

        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_CUSTOMER_NAME = "customer_name";
        public static final String COLUMN_CUSTOMER_EMAIL = "customer_email";
        public static final String COLUMN_CUSTOMER_ADDRESS = "customer_address";
        public static final String COLUMN_CUSTOMER_COUNTRY = "customer_country";
    }
}
