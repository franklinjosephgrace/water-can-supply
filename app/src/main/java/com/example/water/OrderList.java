package com.example.water;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderList extends ArrayAdapter<Order> {
    private Activity context;
    //list of users
    List<Order> orders;

    public OrderList(Activity context, List<Order> orders) {
        super(context, R.layout.layout_order_list, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_order_list, null, true);
        //initialize
        TextView textViewnumofcans = (TextView) listViewItem.findViewById(R.id.textViewnumofcans);
        TextView textviewamount = (TextView) listViewItem.findViewById(R.id.textviewamount);
        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textviewname);
        TextView textViewphone = (TextView) listViewItem.findViewById(R.id.textViewphone);
        TextView textviewdate = (TextView) listViewItem.findViewById(R.id.textviewdate);
        TextView textviewaddress = (TextView) listViewItem.findViewById(R.id.textviewaddress);
        TextView textviewlandmark = (TextView) listViewItem.findViewById(R.id.textviewlandmark);
        //getting user at position
        Order order = orders.get(position);
        //set Number of Cans
        textViewnumofcans.setText("No of Cans : "+order.getNumofcans());
        //set Amount
        textviewamount.setText("Total Amount : "+order.getAmount());
        //set Name
        textviewname.setText("Name : "+order.getName());
        //set Phone
        textViewphone.setText("Phone Number : "+order.getPhone());
        //set Date
        textviewdate.setText("Date of Delivery : "+order.getDate());
        //set Address
        textviewaddress.setText("Address : "+order.getAddress());
        //set Landmark
        textviewlandmark.setText("Land-Mark : "+order.getLandmark());
        return listViewItem;
    }
}