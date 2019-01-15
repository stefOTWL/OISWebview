package com.nikhil.otw.webview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nikhil.otw.webview.utils.SharedPrefUtils;
import com.nikhil.otw.webview.utils.UIUtils;
import com.nikhil.otw.webview.utils.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nikhil.otw.webview.utils.Constants.getAppVersion;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rv_websites)
    RecyclerView rvWebsites;
    List<WebsiteModel> list;
    WebsiteAdapter adapter;

    private static final String TAG = "HomeActivity";
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_notif_icon)
    ImageView ivNotifIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.toolbar_container)
    LinearLayout toolbarContainer;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        list = new ArrayList<>();

        //title.setText("Ocean");
        title.setVisibility(View.INVISIBLE);

        version.setText('v' + getAppVersion(this));

        ivNotifIcon.setVisibility(View.VISIBLE);
        ivNotifIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.icons8shutdown48));
        ivNotifIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
        init();
    }

    private void init() {


        getWebsites();

    }

    private void showAlert() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Choose an action");

// add a list
        String[] animals = {"Exit Application", "Logout"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        exitApplication();
                        break;
                    case 1:
                        SharedPrefUtils.put(HomeActivity.this,"mobile","");
                        exitApplication();
                        break;

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void exitApplication() {

        finish();
        moveTaskToBack(true);
    }

    private void getWebsites() {

        progressDialog = UIUtils.createProgressDialog(this, false);
        progressDialog.setMessage("Loading.....Please wait....");
        progressDialog.show();

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL.BASE_URL + SharedPrefUtils.getString(HomeActivity.this, "mobile");

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                WebsiteModel model = new WebsiteModel(object.getString("v_desc"), object.getString("v_site"));
                                list.add(model);
                            }

                            adapter = new WebsiteAdapter(list, HomeActivity.this);
                            rvWebsites.setAdapter(adapter);
                            rvWebsites.setLayoutManager(new LinearLayoutManager(HomeActivity.this));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
                error.printStackTrace();
                progressDialog.dismiss();

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
