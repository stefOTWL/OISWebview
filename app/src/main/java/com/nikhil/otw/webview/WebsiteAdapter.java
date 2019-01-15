package com.nikhil.otw.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by NiCK on 6/4/2018.
 */

public class WebsiteAdapter extends RecyclerView.Adapter<WebsiteAdapter.WebsiteVH> {
    List<WebsiteModel> list;
    Context context;


    public WebsiteAdapter(List<WebsiteModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public WebsiteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_single_website,parent,false);
        return new WebsiteVH(view);
    }

    @Override
    public void onBindViewHolder(WebsiteVH holder, int position) {
        WebsiteModel model = list.get(position);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
//        int deviceheight = displaymetrics.heightPixels / 3;
//
//
//        holder.itemView.getLayoutParams().height = deviceheight;

        holder.websiteName.setText(model.websiteName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WebsiteVH extends RecyclerView.ViewHolder {

        TextView websiteName;


        public WebsiteVH(View itemView) {
            super(itemView);

            websiteName = (TextView) itemView.findViewById(R.id.tv_websiteName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.startActivity(new Intent(context, WebViewActivity.class)
                            .putExtra("websiteName",list.get(getLayoutPosition()).websiteName)
                            .putExtra("websiteLink", list.get(getLayoutPosition()).websiteLink));

                }
            });

        }
    }
}
