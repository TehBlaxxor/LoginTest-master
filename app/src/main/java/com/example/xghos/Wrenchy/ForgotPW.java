package com.example.xghos.Wrenchy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;


public class ForgotPW extends Fragment {

    EditText mEmail;
    Button mSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_pw, container, false);
        mEmail = view.findViewById(R.id.email_here);

        mSend = view.findViewById(R.id.BSend);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.getINSTANCE().isEmail(mEmail.getText().toString())){
                    new ForgotPassAsyncTask().execute();
                }
                else
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private class ForgotPassAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... objects) {
            HashMap<String, String> getParams = new HashMap<>();

            String email = mEmail.getText().toString();

            getParams.put("mail", email);
            getParams.put("request", "forgotpw");


            try {
                String response = new HttpRequest(getParams, "http://students.doubleuchat.com/forgotpw.php").connect();
                JSONObject responseObject = new JSONObject(response);
                String message = responseObject.getString("msg");
                return message;
            }
            catch (Exception e)
            {
                return "Unknown Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (s) {
                case "success":
                    Toast.makeText(getContext(), "Check your email for your new password", Toast.LENGTH_SHORT).show();
                    break;
                case "Unknown Error":
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
