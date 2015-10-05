package com.alp.android.spacemining;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComonautUpgrade.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComonautUpgrade#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComonautUpgrade extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View myFragmentView;
    private View view;
    private ImageButton mButton;
    private TextView heroCDCostTxt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComonautUpgrade.
     */
    // TODO: Rename and change types and number of parameters
    public static ComonautUpgrade newInstance(String param1, String param2) {
        ComonautUpgrade fragment = new ComonautUpgrade();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ComonautUpgrade() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("", "I'm created!");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_comonaut_upgrade, container, false);
        //heroCDCostTxt = (TextView) myFragmentView.findViewById(R.id.pu_cd_cost);

        return view;


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_comonaut_upgrade, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tv = (TextView) view.findViewById(R.id.pu_cd_cost);
        mButton = (ImageButton) view.findViewById(R.id.imageButton1);
        mButton.setOnClickListener((View.OnClickListener) this);
        tv.setText("10");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        Log.d("", "I'm attached!");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            //mListener.onFragmentInteraction(Uri.parse("doWhatYouWant"));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.d("", "I'm detached!");
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);


    }
    @Override
    public void onClick(View v){
        mListener.onFragmentInteraction(null);
    }

    /* Increase Click Damage */
    public void setClickDmg(int totalCrystal, int heroCDCost, Cosmonaute cosmonaute){

            cosmonaute.setClickDamage(1);
            int clickDmg = cosmonaute.getClickDamage();
            cosmonaute.setCosmonauteLvl(1);
            int heroLvl = cosmonaute.getCosmonauteLvl();
            heroCDCost = (int) Math.floor(heroCDCost * Math.pow(1.07, heroLvl));
            displayNextPuCDCost(heroCDCost);
            displayClickDmg(clickDmg);



            Toast.makeText(
                    ((MainActivity)getActivity()),
                    "Your Cosmonaute is now level " + cosmonaute.getCosmonauteLvl(),
                    Toast.LENGTH_SHORT
            ).show();

    }

    public void displayNextPuCDCost(int heroCDCost){
        TextView tv = (TextView) view.findViewById(R.id.pu_cd_cost);
        tv.setText(String.valueOf(heroCDCost));
    }
    public void displayClickDmg(int clickDmg){
        TextView tv = (TextView) view.findViewById(R.id.totalCDTxt);
        tv.setText(String.valueOf(clickDmg));
    }





}
