package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {
    EditText name,quality,price,info,size;

     Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //initializing view objects
        name = findViewById(R.id.editTextName);
        quality = findViewById(R.id.editTextQuality);
        price = findViewById(R.id.editTextPrice);
        info = findViewById(R.id.editTextInfo);
        size = findViewById(R.id.editTextSize);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setProGroupId(1);
                product.setColor("null");
                product.setName(name.getText().toString());
                product.setStock(Integer.parseInt(quality.getText().toString()));
                product.setPrice(Float.parseFloat(price.getText().toString()));
                product.setInformation(info.getText().toString());
                product.setSize(size.getText().toString());
                GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
                Call<Integer> call = service.addProduct(product);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("arrmovie", response.toString());
                        //show san pham
                        int res = response.body();
                        if(res == 1){
                            Toast.makeText(AddProduct.this,"Thêm thành công !!",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("arrmovie", t.toString());
                    }
                });
            }
        });
    }

}