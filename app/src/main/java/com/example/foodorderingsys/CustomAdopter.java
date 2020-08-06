package com.example.foodorderingsys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CustomAdopter extends RecyclerView.Adapter<CustomAdopter.MyviewHolder> implements Filterable {

    private Context context;
    private   ArrayList<String> cate;
    private   ArrayList<String> copylist;

    private String s;
    private int intt2;
    CustomAdopter( Context context,ArrayList cate,String s,int intt2)
    {
        this.context=context;
        this.cate=cate;
        copylist =new ArrayList(cate);
        this.s=s;
        this.intt2=intt2;
    }

    public ArrayList<String> getCate() {
        return cate;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.newrow,parent,false);
        return new MyviewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        holder.textView.setText(String.valueOf(cate.get(position)));
        //String s=String.valueOf(cate.get(position));
        final String imgname=String.valueOf(cate.get(position));
        String uri = "drawable/"+imgname;
        String PACKAGE_NAME = context.getApplicationContext().getPackageName();
        int icon = context.getResources().getIdentifier(uri, "drawable",PACKAGE_NAME);

        holder.imageView.setImageResource(icon);
      /*  if(s.equalsIgnoreCase("starters")) {
            holder.imageView.setImageResource(R.drawable.starters);
        }
        if(s.equalsIgnoreCase("burgers")) {
            holder.imageView.setImageResource(R.drawable.burgers);
        }
        if(s.equalsIgnoreCase("Chinese")) {
            holder.imageView.setImageResource(R.drawable.chinese);
        }
        if(s.equalsIgnoreCase("italian")) {
            holder.imageView.setImageResource(R.drawable.italian);
        }
        if(s.equalsIgnoreCase("desserts")) {
            holder.imageView.setImageResource(R.drawable.dessert);
        }*/

        holder.lLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on "+cate.get(position));
                Intent intent= new Intent(context.getApplicationContext(), GirdView.class);
                intent.putExtra("category",imgname);
                intent.putExtra("ename",s);
                intent.putExtra("num1",intt2);
                context.startActivity(intent);
                Toast.makeText(context, "you entered in " +imgname+" menu", Toast.LENGTH_SHORT).show();
                //  ((CategoryMenu)context).finish();




            }
        });


    }

    @Override
    public int getItemCount() {
        return cate.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout lLayout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewcata);
            textView=itemView.findViewById(R.id.tvcate);
            lLayout=itemView.findViewById(R.id.rowlinear);
        }
    }

    @Override
    public Filter getFilter() {
        return listfilter;
    }
    private Filter listfilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList filteredList=new ArrayList<>();
            if(constraint==null||constraint.length()==0)
            {
                filteredList.addAll(copylist);
            }
            else
            {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for(String item: copylist){
                    if(item.toLowerCase().contains(filterpattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cate.clear();
            cate.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };
}
