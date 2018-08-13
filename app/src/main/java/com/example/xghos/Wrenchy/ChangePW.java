package com.example.xghos.Wrenchy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;


public class ChangePW extends Fragment {

    /*
    ChangePW, TODO in fragment_change_pw, asezarea layoutului incat sa arate decent
    fragmentul apare momentan doar dupa schimbarea parolei prin Forgot Password, si ne cere sa schimbam parola primita prin email din forgot password cu una noua
     */

    private TextView mOldPass;
    private TextView mNewPass;
    private TextView mConPass;
    private Button mChangePass;

    public ChangePW() {
        // Required empty public constructor
    }
    public static ChangePW newInstance() {
        ChangePW fragment = new ChangePW();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pw, container, false);
        mOldPass = view.findViewById(R.id.ETOldPass);
        mOldPass.setText(CurrentUser.getOldpw());
        Log.d("+++", "" + CurrentUser.getOldpw());

        mOldPass.setText(CurrentUser.getOldpw());

        mNewPass = view.findViewById(R.id.ETNpass);
        mConPass = view.findViewById(R.id.ETCpass);

        mChangePass = view.findViewById(R.id.BChangePass);
        mChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.getINSTANCE().changePasswordValidation(mOldPass.getText().toString(), mNewPass.getText().toString(),
                        mConPass.getText().toString(), getContext())){
                    new ChangePassword().execute();
                }
            }
        });
        ((MainActivity)getActivity()).toolbar.setVisibility(View.GONE);
        return view;
    }
    private class ChangePassword extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... objects) {
            HashMap<String, String> getParams = new HashMap<>();

            String oldp = mOldPass.getText().toString();
            String newp = mNewPass.getText().toString();
            String id = CurrentUser.getId();

            getParams.put("oldpw", oldp);
            getParams.put("newpw", newp);
            getParams.put("id_user", id);
            getParams.put("request", "changepw");

            try {
                String response = new HttpRequest(getParams, "http://students.doubleuchat.com/changepw.php").connect();
                JSONObject responseObject = new JSONObject(response);
                String message = responseObject.getString("msg");
                String Object = responseObject.getString("response");

                if (message.equals("error"))
                {
                    return Object;
                }

            }
            catch (Exception e)
            {
                return "nuok";
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("ok")){
                Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new NavigationFragment()).commit();
                }
            else {
                Log.d("+++", s);
                Toast.makeText(getActivity(), "Ples tri agan", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
