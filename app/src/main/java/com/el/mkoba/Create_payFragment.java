package com.el.mkoba;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Create_payFragment extends Fragment {
    View root;
    private TextView mpesa,equity,visa,airtell;
    int pay_method = 0;



    public Create_payFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_create_pay, container, false);

        mpesa = root.findViewById(R.id.mpesa);
        equity = root.findViewById(R.id.equity);
        visa = root.findViewById(R.id.visa);
        airtell = root.findViewById(R.id.airtel);




        mpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toMpesa= new Intent(getContext(),Add_trans_Activity.class);
                toMpesa.putExtra("Mpesa",1);
                startActivity(toMpesa);



            }
        });

        equity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getContext(), "Not available for now..", Toast.LENGTH_SHORT).show();

//                Intent toMpesa= new Intent(getContext(),Add_trans_Activity.class);
//                toMpesa.putExtra("Mpesa",2);
//                startActivity(toMpesa);



            }
        });
        airtell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Not available for now..", Toast.LENGTH_SHORT).show();

//                Intent toMpesa= new Intent(getContext(),Add_trans_Activity.class);
//                toMpesa.putExtra("Mpesa",4);
//                startActivity(toMpesa);



            }
        });

        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getContext(), "Not available for now..", Toast.LENGTH_SHORT).show();

//                Intent toMpesa= new Intent(getContext(),Add_trans_Activity.class);
//                toMpesa.putExtra("Mpesa",3);
//                startActivity(toMpesa);



            }
        });





        return root;
    }

}
