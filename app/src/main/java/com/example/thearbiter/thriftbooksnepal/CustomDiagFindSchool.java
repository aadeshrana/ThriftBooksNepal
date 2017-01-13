package com.example.thearbiter.thriftbooksnepal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import com.example.thearbiter.thriftbooksnepal.Adapters.AdapterFindSchool;
import com.example.thearbiter.thriftbooksnepal.ExtraClasses.JSONParser;
import com.example.thearbiter.thriftbooksnepal.Information.InformationFindSchool;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 12-01-17.
 */
public class CustomDiagFindSchool extends DialogFragment implements SearchView.OnQueryTextListener {
    public RecyclerView recyclerView;
    public AdapterFindSchool adapterFindSchool;
    public Context context;

    SearchView searchView;

    public static String collegesName[];
    public static String collegesViewed[];
    static final ArrayList<String> tempCollegeName = new ArrayList<>();
    static final ArrayList<String> tempCollegesViewed = new ArrayList<>();

    JSONParser jsonParser = new JSONParser();
    private static final String PULLALLORDERS = "http://frame.ueuo.com/thriftbooks/FetchingData.php";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_find_school_recycler, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.findSchoolRecycler);
        adapterFindSchool = new AdapterFindSchool(getActivity(), getData());
        recyclerView.setAdapter(adapterFindSchool);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDialog().setTitle("Choose Your College");
        context = getActivity();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().setCancelable(true);

        window.setAttributes(lp);

        Button dismiss = (Button) layout.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                AdapterFindSchool.selected=-1;
            }
        });

        searchView = (SearchView)layout.findViewById(R.id.searchView);
        setUpSearchView();

        return layout;
    }
    public void findAllSchool(){
       new findSchool().execute();
    }



    class findSchool extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                //Username halera pathauna ko lagi ready parirako ho
                List<NameValuePair> params1 = new ArrayList<>();

                params1.add(new BasicNameValuePair("food", "jpt"));

                JSONObject json3 = jsonParser.makeHttpRequest(PULLALLORDERS, "POST", params1);
                AdapterFindSchool.count = json3.length();
                for (int i = 0; i < json3.length(); i++) {
                    try {
                        tempCollegeName.add(json3.getString("a" + i));
                        tempCollegesViewed.add(json3.getString("b" + i));
                        CustomDiagFindSchool.collegesName = new String[tempCollegeName.size()];
                        CustomDiagFindSchool.collegesViewed = new String[tempCollegesViewed.size()];

                        CustomDiagFindSchool.collegesName = tempCollegeName.toArray(new String[tempCollegeName.size()]);
                        CustomDiagFindSchool.collegesViewed = tempCollegesViewed.toArray(new String[tempCollegeName.size()]);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            } catch (Exception e) {

            }


            return null;

        }


    }
    public List<InformationFindSchool> getData() {
        List<InformationFindSchool> data = new ArrayList<>();
        try {
            for (int i = 0; i < CustomDiagFindSchool.tempCollegeName.size() + 1; i++) {
                InformationFindSchool current = new InformationFindSchool();

                current.collegeName = CustomDiagFindSchool.collegesName[i];

                data.add(current);
            }
        }
        catch (Exception e){
            
        }
        return data;
    }
    public void setUpSearchView(){
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        searchView.setQueryHint("Search Here");

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
      adapterFindSchool.filter(newText);
        Log.d("Why emplt", "ss" + newText);

        return  true;
    }

}
