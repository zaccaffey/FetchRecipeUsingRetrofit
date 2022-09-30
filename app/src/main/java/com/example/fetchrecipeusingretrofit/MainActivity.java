package com.example.fetchrecipeusingretrofit;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvId, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvId = findViewById(R.id.tvId);
        tvTitle = findViewById(R.id.tvTitle);

        // This creates an instance of the retrofit client
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Grab the list of recipes from the JSON that was fetched using the GET parameters defined in the GetDataService interface
        Call<List<Recipes>> recipesList = service.getAllRecipes();

        // Asynchronously send the request and notify callback of its response or if an error occurred talking to the server,
        // creating the request, or processing the response.
        recipesList.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                generateDataList(response);
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(Response<List<Recipes>> recipesList) {
        //Toast.makeText(this, recipesList.body().get(0).getId().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, recipesList.body().get(0).getTitle(), Toast.LENGTH_SHORT).show();
        String recipe = "Here is the full ingredient list:\n";

        for (int i = 0; i < recipesList.body().get(0).getUsedIngredients().size(); ++i) {
            recipe += recipesList.body().get(0).getUsedIngredients().get(i).get("originalName") + ": " + recipesList.body().get(0).getUsedIngredients().get(i).get("amount") + "\n";
        }

        for (int j = 0; j < recipesList.body().get(0).getMissedIngredients().size(); ++j) {
            recipe += recipesList.body().get(0).getMissedIngredients().get(j).get("originalName") + ": " + recipesList.body().get(0).getMissedIngredients().get(j).get("amount") + "\n";
        }

        tvTitle.setText(recipe);
    }
}


