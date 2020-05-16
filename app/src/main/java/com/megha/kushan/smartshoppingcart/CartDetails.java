package com.megha.kushan.smartshoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartDetails extends AppCompatActivity {

    private String cart_id;
    private String TAG = "KUSHANMEHTA";

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private RecyclerView mRecyclerView;

    private List<DB_Cart_Details> products = new ArrayList<>();

    Integer total_cost = 0;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cart_id = extras.getString("cart_id");
//            TextView cart_id_text = (TextView) findViewById(R.id.cart_id_text);
//            cart_id_text.setText("Order Summary for Cart #" + cart_id + ":");

            setTitle("Order Summary for Cart #" + cart_id);

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("cart_details").child(cart_id.toString());

        }

        progressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                products.clear();
                total_cost = 0;
                List<String> keys = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    keys.add(ds.getKey());
                    Integer product_cost = ds.child("product_cost").getValue(Integer.class);
                    String product_name = ds.child("product_name").getValue(String.class);
//                    Toast.makeText(CartDetails.this, product_name, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, product_cost.toString() + " / " + product_name);
                    products.add(new DB_Cart_Details(product_cost, product_name));
                    total_cost += product_cost;
                }

                progressBar.setVisibility(View.GONE);

                new RecyclerView_Config().setConfig(mRecyclerView, CartDetails.this, products, keys);

                ((TextView) findViewById(R.id.total_cost_textview)).setText("₹ " + total_cost.toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(CartDetails.this, "Failed to read value", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
