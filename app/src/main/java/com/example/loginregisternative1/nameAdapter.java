package com.example.loginregisternative1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import static com.example.loginregisternative1.MainActivity.Names;
import static com.example.loginregisternative1.MainActivity.Price;
import static com.example.loginregisternative1.MainActivity.Quantity;
import static com.example.loginregisternative1.MainActivity.TotalPrice;
import static com.example.loginregisternative1.MainActivity.displaysum;
import static com.example.loginregisternative1.MainActivity.quantitychecker;
import static com.example.loginregisternative1.ScanActivity.ttotal;

class nameAdapter extends RecyclerView.Adapter<nameAdapter.ViewHolder> {


    public nameAdapter(ArrayList<String> names) {
        Names=names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        Integer price=Price.get(position);
        String price1=Integer.toString(price);


        Integer quant=Quantity.get(position);
        Integer tprice=quant*price;



        String  tpricer1=Integer.toString(tprice);
        String quant1=Integer.toString(quant);

        holder.prname.setText(Names.get(position));
        holder.prprice.setText(price1);
        holder.pdquantity.setText(quant1);
        holder.tprice1.setText(tpricer1);
         displaysum=0;
        Integer cprice,cquantity,ctotal;
        for(Integer i=0;i<Names.size();i++)
        {
            cprice=Price.get(i);
            cquantity=Quantity.get(i);
            ctotal=cprice*cquantity;
            displaysum=displaysum+ctotal;
        }

        String dsum=Integer.toString(displaysum);
        ttotal.setText(dsum);







        holder.incr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer price=Price.get(position);
                Integer quant=Quantity.get(position);
                quant=quant+1;
                String quant1=Integer.toString(quant);
                holder.pdquantity.setText(quant1);
                Quantity.add(position,quant);
                Integer tprice=quant*price;
                String tpricer1 = Integer.toString(tprice);
                holder.tprice1.setText(tpricer1);

                displaysum=displaysum+price;
                String dsum=Integer.toString(displaysum);
                ttotal.setText(dsum);
                holder.incr.setImageResource(R.drawable.ic_plus);



            }
        });
        holder.decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer price=Price.get(position);

                Integer quant=Quantity.get(position);
                if(quant>1) {
                    quantitychecker=0;
                    quant = quant - 1;
                    String quant1 = Integer.toString(quant);
                    displaysum = displaysum - price;
                    String dsum = Integer.toString(displaysum);
                    ttotal.setText(dsum);

                    holder.pdquantity.setText(quant1);

                    Quantity.add(position, quant);

                    Integer tprice = quant * price;

                    String tpricer1 = Integer.toString(tprice);
                    holder.tprice1.setText(tpricer1);
                }
                else
                {
                    quantitychecker=1;
                   
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return  Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prname;
        public TextView prprice,pdquantity,tprice1;
        public ImageButton incr,decr;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prname=itemView.findViewById(R.id.pdlname);
            prprice=itemView.findViewById(R.id.pdlprice);
            pdquantity=itemView.findViewById(R.id.pdlquantity);
            tprice1=itemView.findViewById(R.id.totalprice);
            incr=itemView.findViewById(R.id.plus);
            decr=itemView.findViewById(R.id.dec);


        }
    }
}
