package com.asisctf.ShareL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import java.io.Serializable;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

public class TopUserAdapter extends Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Context context;
    List<Integer> list;

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public TextView id;
        public LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.number);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.linear_number);
        }
    }

    public TopUserAdapter(Context context2, List<Integer> list2) {
        this.context = context2;
        this.list = list2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final List<Integer> list2 = this.list;
        TextView textView = viewHolder.id;
        StringBuilder sb = new StringBuilder();
        sb.append(list2.get(i));
        sb.append("");
        textView.setText(sb.toString());
        viewHolder.linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(TopUserAdapter.this.context, ProfileActivity.class);
                intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                intent.putExtra("uid", (Serializable) list2.get(i));
                TopUserAdapter.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }
}
