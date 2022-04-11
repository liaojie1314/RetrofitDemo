package io.liaojie1314.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<MainData>dataArrayList = new ArrayList<>();
    MainAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=findViewById(R.id.recycler_view);

        mAdapter=new MainAdapter(MainActivity.this,dataArrayList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mAdapter);

        getData();
    }
    private void getData(){
        ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface=retrofit.create(MainInterface.class);
        Call<String>stringCall=mainInterface.STRING_CALL();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    dialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(response.body());
                        parseArray(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseArray(JSONArray jsonArray) {
        dataArrayList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject= null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                MainData data=new MainData();
                data.setImage(jsonObject.getString("download_url"));
                data.setName(jsonObject.getString("author"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapter=new MainAdapter(MainActivity.this,dataArrayList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}