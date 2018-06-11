package com.androidtutorialshub.LuckyWithYou.sql;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.androidtutorialshub.LuckyWithYou.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class FireBaseHelper{
    private static final String TAG = "AnonymousAuth";
    private String credential="0AsQwhbFLZdHGmSHMarepHKIVZciKTJ5yaTwtCiK";
    private String path="https://luckywithyou-abd13.firebaseio.com/";
    private FirebaseDatabase database =null;
    private FirebaseOptions options;
    private FirebaseApp secondApp;
    private FirebaseDatabase secondDatabase;
    private DatabaseReference myRef;
    public List<User> users;



    public FireBaseHelper(Context appcontext){

        try {
            secondApp = FirebaseApp.getInstance("luckywithyou");

        }
        catch (Exception e) {
            options = new FirebaseOptions.Builder()
                    .setApplicationId("luckywithyou-abd13")
                    .setApiKey("AIzaSyDXwbhGRks_UGm-JttGz3Hixz3CrtOX0PI")
                    .setDatabaseUrl("https://luckywithyou-abd13.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(appcontext, options, "luckywithyou");
            secondApp = FirebaseApp.getInstance("luckywithyou");

        }
        secondDatabase = FirebaseDatabase.getInstance(secondApp);
        myRef = secondDatabase.getReference("users");
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String keyi = dataSnapshot.getKey();
                //long i = dataSnapshot.getChildrenCount();

                users = new ArrayList<User>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = new User();


                    for (DataSnapshot obj : postSnapshot.getChildren()) {

                        String key = obj.getKey();
                        String value = obj.getValue(String.class);
                        user=addProp(user,key, value);
                    }
                    users.add(user);
                }

                int index = checkForUser("DELETETHISUSER", "DELETETHISUSER");
                if (index >= 0) {
                    users.remove(index);
                    myRef.child("none").removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUsers();

    }

    public User addProp(User user, String key, String val){

        switch (key){
            case "countofposts": user.countofposts=val;
                break;
            case "data_display": user.data_display=val;
                break;
            case "name": user.name=val;
                break;
            case "newmessages": user.newmessages=val;
                break;
            case "password": user.password=val;
                break;
            case "typeOfCancer": user.typeOfCancer=val;
                break;
            case "usermail": user.usermail=val;
                break;
            case "score": user.score=val;
                break;
        }
        return user;
    }

    public void setUsers(){


        myRef.child("none").setValue(toMap(new User()));



    }

    public Map<String, Object> toMap(User user) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("countofposts", user.countofposts);
        result.put("data_display",user.data_display);
        result.put("name",user.name );
        result.put("newmessages", user.newmessages);
        result.put("password",user.password );
        result.put("score", user.score);
        result.put("typeOfCancer",user.typeOfCancer );
        result.put("usermail",user.usermail );

        return result;
}


    public void addUser(User user){

        myRef.child(String.valueOf(users.size()+1)).setValue(user);

    }

    public int checkForUser(String mail, String password){

        for(int i=0; i<users.size();i++){

            if(users.get(i).usermail.equals(mail)&&users.get(i).password.equals(password))
                return i;

        }
        return -1;
    }

    public User getUser(String mail,String password){

        int index=checkForUser(mail,password);
        if(index>=0)
            return users.get(index);

        return new User();
    }

    public void writeToFirebase(){

        myRef.removeValue();
        myRef = secondDatabase.getReference("c1");

        for(int i=0;i<users.size();i++){
            myRef.child(String.valueOf(i)).setValue(users.get(i));

        }

    }

    public void updateUser(User user){

        int index=checkForUser(user.usermail,user.password);
        if(index>=0) {
            users.get(index).updateUser(user);

            writeToFirebase();

          //  myRef.child(String.valueOf(index)).setValue(user);

        }


    }
/*
    ValueEventListener changeListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //String keyi = dataSnapshot.getKey();
            //long i = dataSnapshot.getChildrenCount();

            users= new ArrayList<User>();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                User user=new User();

                for (DataSnapshot obj:postSnapshot.getChildren()) {

                     String key = obj.getKey();
                    String value = obj.getValue(String.class);
                     user.addProp(key,value);
                }
                users.add(user);
            }

            int index=checkForUser("DELETETHISUSER","DELETETHISUSER");
            if(index>=0) {
                users.remove(index);
                myRef.child("none").removeValue();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

*/
}
