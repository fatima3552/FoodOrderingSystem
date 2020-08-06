package com.example.foodorderingsys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Product>
{
    customButtonListener customListner;
    private Activity activity;
    private Context context;
    List<Product> scores;
    MySharedPreference sharedPreference;
    int totBill=0;

    public ListViewAdapter(Activity activity, int resource, List<Product> scores)
    {
        super(activity, resource, scores);
        this.activity = activity;

    }
    public interface customButtonListener {
        void onButtonClickListner(int position, String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null)
        {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        }

        else
        {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        Product score = getItem(position);

        holder.name.setText(score.getName());
        holder.price.setText(score.getPrice());
        holder.qty.setText(score.getQty());
        final String imgname = String.valueOf(score.getImg());
        String uri = "drawable/" + imgname;
        String PACKAGE_NAME = activity.getApplicationContext().getPackageName();
        int icon = activity.getResources().getIdentifier(uri, "drawable", PACKAGE_NAME);
        holder.img.setImageResource(icon);
        int bill = ((Integer.valueOf(score.getPrice())) * (Integer.valueOf(score.getQty())));
        holder.bill.setText(String.valueOf(bill));




        //totBill += bill;
/*        final int temp = 100;
        //holder.totBill.setText(totBill);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    customListner.onButtonClickListner(temp);
                }
        });

        final String temp = score.getQty();
        holder.nQty.setText(score.getQty());
        final int count = Integer.valueOf(temp);
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    count = count - 1;
                }
                nQty.setText(String.valueOf(count));
            });
        customListner.onButtonClickListner(temp);*/
        /*holder.bill.setText(String.valueOf(totBill));
        holder.add.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    Toast.makeText(activity.getApplicationContext(), "Button click " , Toast.LENGTH_SHORT).show();
                    customListner.onButtonClickListner(position, temp);
                }
            }
        });*/

        final ViewHolder finalHolder = holder;
        holder.add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                final String name = String.valueOf(finalHolder.name.getText());
                final String qty = String.valueOf(finalHolder.qty.getText());
                final String price = String.valueOf(finalHolder.price.getText());
                Intent view_order_intent = new Intent(activity.getApplicationContext(), custSeeProduct.class);
                view_order_intent.putExtra("name1", "edit");
                view_order_intent.putExtra("n", name);
                view_order_intent.putExtra("quantity", qty);
                view_order_intent.putExtra("img1", imgname);
                view_order_intent.putExtra("price", price);
                activity.getApplicationContext().startActivity(view_order_intent);
            }
        });
        return convertView;
    }


    private static class ViewHolder {
        private TextView name;
        private TextView price;
        private TextView qty;
        private ImageView img;
        private TextView bill;
        private TextView totBill;
        private TextView nQty;
        private Button add, confirm, del;



        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.tvitemname);
            price = (TextView) v.findViewById(R.id.tvitemprice);
            qty = (TextView) v.findViewById(R.id.tvitemqty);
            img = (ImageView) v.findViewById(R.id.ivitemimg);
            bill = (TextView) v.findViewById(R.id.tvBill);
            totBill = (TextView) v.findViewById(R.id.tvBill);
            add = (Button) v.findViewById(R.id.buttonadd2);
            confirm = (Button) v.findViewById(R.id.confirm_order);
            nQty = (TextView) v.findViewById(R.id.tvitemqty);
            //del = (Button) v.findViewById(R.id.buttondel);

        }
    }

    @Override
    public void remove(Product product) {
        super.remove(product);
        scores.remove(product);
        notifyDataSetChanged();
    }
}