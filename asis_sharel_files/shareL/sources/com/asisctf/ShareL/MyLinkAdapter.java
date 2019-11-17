package com.asisctf.ShareL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.asisctf.ShareL.models.MyLinks.Links;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class MyLinkAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Context context;
    List<Links> list;

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public TextView lName;
        public LinearLayout linearLayout;
        public TextView link;
        public Button share;

        public ViewHolder(View view) {
            super(view);
            this.link = (TextView) view.findViewById(R.id.link_self);
            this.lName = (TextView) view.findViewById(R.id.linkName);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.linear_number);
            this.share = (Button) view.findViewById(R.id.btn_share_user);
        }
    }

    public MyLinkAdapter(Context context2, List<Links> list2) {
        this.context = context2;
        this.list = list2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.uplink_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final List<Links> list2 = this.list;
        TextView textView = viewHolder.link;
        StringBuilder sb = new StringBuilder();
        sb.append(((Links) list2.get(i)).link);
        String str = "";
        sb.append(str);
        textView.setText(sb.toString());
        TextView textView2 = viewHolder.lName;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(((Links) list2.get(i)).link_name);
        sb2.append(str);
        textView2.setText(sb2.toString());
        if (((Links) list2.get(i)).privat == 1) {
            viewHolder.share.setVisibility(0);
            viewHolder.share.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(MyLinkAdapter.this.context.getApplicationContext(), ShareLink2UserActivity.class);
                    intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    intent.putExtra("lid", ((Links) list2.get(i)).link_id);
                    intent.putExtra("lname", ((Links) list2.get(i)).link_name);
                    intent.putExtra("link", ((Links) list2.get(i)).link);
                    MyLinkAdapter.this.context.startActivity(intent);
                }
            });
        }
    }

    public int getItemCount() {
        return this.list.size();
    }
}
