package com.megha.kushan.smartshoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext;
    private ProductAdapter mProductAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<DB_Cart_Details> products, List<String> keys){
        mContext = context;
        mProductAdapter = new ProductAdapter(products, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mProductAdapter);
    }

    class ProductItemView extends RecyclerView.ViewHolder {
        private TextView product_cost_textview;
        private TextView product_name_textview;

        private String key;

        public ProductItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.cart_list_item, parent, false));

            product_cost_textview = (TextView) itemView.findViewById(R.id.product_cost_textview);
            product_name_textview = (TextView) itemView.findViewById(R.id.product_name_textview);
        }

        public void bind(DB_Cart_Details product, String key) {
            product_cost_textview.setText(product.getProduct_cost());
            product_name_textview.setText(product.getProduct_name());
            this.key = key;
        }
    }

    class ProductAdapter extends RecyclerView.Adapter<ProductItemView>{
        private List<DB_Cart_Details> mProductList;
        private List<String> mKeys;

        public ProductAdapter(List<DB_Cart_Details> mProductList, List<String> mKeys) {
            this.mProductList = mProductList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView holder, int position) {
            holder.bind(mProductList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }
}
