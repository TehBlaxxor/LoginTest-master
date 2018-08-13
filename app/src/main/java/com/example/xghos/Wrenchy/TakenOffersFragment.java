package com.example.xghos.Wrenchy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TakenOffersFragment extends Fragment {

    private ArrayList<MyOffer> mOffers;
    private SwipeRecyclerViewAdapter offerAdapter;
    private ArrayList<String> offerIDs;

    public TakenOffersFragment() {
        // Required empty public constructor
    }

    public static TakenOffersFragment newInstance() {
        return new TakenOffersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOffers = new ArrayList<>();
        offerIDs = new ArrayList<>();
        offerAdapter = new SwipeRecyclerViewAdapter(getContext(), mOffers, false);
        new GetTakenOffersAsync().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_taken_offers, container, false);
        RecyclerView mTakenOffers = v.findViewById(R.id.takenOffers);
        mTakenOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mTakenOffers.setAdapter(offerAdapter);
        return v;
    }

    private class GetTakenOffersAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mOffers.clear();
            offerAdapter.notifyDataSetChanged();
            offerIDs.clear();
        }

        @Override
        protected String doInBackground(String... objects) {
            HashMap<String, String> getParams = new HashMap<>();

            getParams.put("id_user", CurrentUser.getId());
            getParams.put("request", "getTakenOffers");

            try {
                String response = new HttpRequest(getParams, "http://students.doubleuchat.com/gethybridoffers.php").connect();
                JSONObject responseObject = new JSONObject(response);
                String message = responseObject.getString("msg");
                JSONObject object = responseObject.getJSONObject("result");
                JSONArray takenOffers = object.getJSONArray("oferteluate");
                for (int i = 0; i < takenOffers.length(); i++) {
                    MyOffer offer = new MyOffer();
                    offer.setName(takenOffers.getJSONObject(i).getString("titlu_oferta"));
                    offer.setPrice(takenOffers.getJSONObject(i).getString("pret_oferta") + "€");
                    offer.setLocation(takenOffers.getJSONObject(i).getString("nume_locatie"));
                    offer.setOffer_id(takenOffers.getJSONObject(i).getString("id_oferta"));
                    offer.setOfferer(takenOffers.getJSONObject(i).getString("nume_angajator"));
                    if (!offerIDs.contains(takenOffers.getJSONObject(i).getString("id_oferta"))) {
                        offerIDs.add(takenOffers.getJSONObject(i).getString("id_oferta"));
                        mOffers.add(offer);
                    }
                }
            } catch (Exception e) {
                return "nuok";
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            offerAdapter.notifyDataSetChanged();
        }
    }
}


