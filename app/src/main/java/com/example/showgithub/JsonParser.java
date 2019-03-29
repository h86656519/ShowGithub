package com.example.showgithub;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Gson
public class JsonParser {
    public  List<Repo> parse(String jsonString){ //jsonString = 要被解析的json字串
        List<Repo> persons = new ArrayList<>();
        Log.i("suvini", "jsonString : " + jsonString);
        try{
         //   JSONObject jsonObject = new JSONObject(jsonString); //JSONObject or JSONArray，只能2選一用，不然會被catch
            JSONArray  jsonArray = new JSONArray(jsonString);
            int length = jsonArray.length();
            for(int i = 0; i < length; i++){
                JSONObject personObject = jsonArray.getJSONObject(i);
                String id = jsonArray.getJSONObject(i).getString("id");
                String nodeId = jsonArray.getJSONObject(i).getString("node_id");
                String name = personObject.getString("name");
                String fullName = personObject.getString("full_name");

                Repo person = new Repo();
                person.setId(id);
                person.setNodeId(nodeId);
                person.setName(name);
                person.setfull_name(fullName);
                persons.add(person);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return persons;
    }
}
