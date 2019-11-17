package com.asisctf.ShareL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.asisctf.ShareL.models.UsrPLink.Links;
import java.util.List;

public class UserPubLinkAdapter extends Adapter<ViewHolder> {
    private Context context;
    List<Links> list;

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public TextView lName;
        public LinearLayout linearLayout;
        public TextView link;

        public ViewHolder(View view) {
            super(view);
            this.link = (TextView) view.findViewById(R.id.link_self);
            this.lName = (TextView) view.findViewById(R.id.linkName);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.linear_number);
        }
    }

    public UserPubLinkAdapter(Context context2, List<Links> list2) {
        this.context = context2;
        this.list = list2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.uplink_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        List<Links> list2 = this.list;
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
    }

    public int getItemCount() {
        return this.list.size();
    }
}
