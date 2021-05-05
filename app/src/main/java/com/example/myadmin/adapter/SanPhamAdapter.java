package com.example.myadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadmin.AddProduct;
import com.example.myadmin.EditProduct;
import com.example.myadmin.MainActivity;
import com.example.myadmin.ProductActivity;
import com.example.myadmin.R;
import com.example.myadmin.listProductInvoice;
import com.example.myadmin.model.SanPham;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    Context context,context1;
    List<SanPham> sanPhamList;
    ItemClickListener clickListener;
    public SanPhamAdapter(Context context, List<SanPham> sanPhamList,ItemClickListener clickListener) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SanPhamAdapter.SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanpham_row_item, parent, false);
        return new SanPhamAdapter.SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SanPhamAdapter.SanPhamViewHolder holder, int position) {

        boolean display = sanPhamList.get(position).getDisplay();
        int idProduct = sanPhamList.get(position).getId();
        Glide.with(context).load(sanPhamList.get(position).getImage1()).into(holder.sanpham_image);
        holder.name.setText(sanPhamList.get(position).getName());
        holder.quality.setText(("SL: " + sanPhamList.get(position).getStock()));
        holder.price.setText(("Giá: " + sanPhamList.get(position).getPrice()));
        if(display)
            holder.btnDelete.setText("Ẩn");
        else
            holder.btnDelete.setText("Hiện");
        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.ClickItem(idProduct);
            }
        });


        holder.itemView.findViewById((R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditProduct.class);
                i.putExtra("product", sanPhamList.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }


    public static final class SanPhamViewHolder extends RecyclerView.ViewHolder {

        Button btnDelete;
        ImageView sanpham_image;
        TextView price, name, quality;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            sanpham_image = itemView.findViewById(R.id.sanpham_image);
            quality = itemView.findViewById(R.id.quality);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.sanpham_name);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
