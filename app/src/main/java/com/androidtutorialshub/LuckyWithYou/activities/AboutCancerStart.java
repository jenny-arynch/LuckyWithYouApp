package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;

public class AboutCancerStart extends AppCompatActivity implements View.OnClickListener{
    private String userEmail;
    private String userPassword;
    private String cansertype;
    private AppCompatButton back;
    private AppCompatButton next;
    private AppCompatButton back2;
    private AppCompatButton next2;
    private TextView textviewabout;
    private int currentIndex=0;
    private ImageView  imageicon;

    private String[] main_string= {
            "הגוף שלנו מורכב ממילוני תאים קטנים.\n ולכל אחד מהם יש תפקיד אחר.",
            "לפעמים הגוף שלנו מייצר תאים 'מוזרים',\n שדגלים ללא שליטה ואין לנו שום צורך בהם.",
            "תאים אלה נקראים תאים סרטניים\n והם אלה שיכולים לגרום למישהו להיות חולה.",
            "קיימות הרבה סיבות לגלידה של תאים אלה.",
            "תאים קיימים בגוף שלנו כל הזמן מייצרים תאים חדשים,\n כך למשל אנחנו גדלים מהר"
    };
    private String[] brain_string= {
            "תאים סרטניים נוצרים הרבה יותר מהר מהתאים הרגילים.\n הם עוצרים את הגדילה של התאים הרגילים\n וגורמים למחלה.",
            "קיימים יותר מ100 סוגי סרטן שונים.\n בדרך כלל הם נקראים על שם המקום בגוף בו התאים התחילו לגדול לראשונה.",
            "מוח הוא החלק המרכזי של הדוף שלנו\n, מוח גורם לנו להיות איך שאנחנו! להרגיש, להחליט ולחשוב.",
            "סרטן זה מתגלה כאשר מתחילים לגדול תאים לא טובים בתוך המוח."

    };
    private String[] breast_string= {
            "תאים סרטניים נוצרים הרבה יותר מהר מהתאים הרגילים.\n הם עוצרים את הגדילה של התאים הרגילים\n וגורמים למחלה.",
            "קיימים יותר מ100 סוגי סרטן שונים.\n בדרך כלל הם נקראים על שם המקום בגוף בו התאים התחילו לגדול לראשונה.",
            "חזה של בן אדם מורכב מעור, שרירים ושומן.",
            "גדילה של תאים רעים באזור של החזה גורמת לסרטן השד."
    };
    private String[] melanoma_string= {
            "תאים סרטניים נוצרים הרבה יותר מהר מהתאים הרגילים.\n הם עוצרים את הגדילה של התאים הרגילים\n וגורמים למחלה.",
            "קיימים יותר מ100 סוגי סרטן שונים.\n בדרך כלל הם נקראים על שם המקום בגוף בו התאים התחילו לגדול לראשונה.",
            "עור זה החלק האיבר ההכי גדול בגוף שלנו.\n הוא נותן לנו הגנה בפני השמש, זיהומים, חום ומים.",
            "מלנומה נוצרת כאשר תאים רעים מתחילים לגדול מעל העור או בטוח שכבות פנימיות."
    };
    private String[] skin_string= {
            "תאים סרטניים נוצרים הרבה יותר מהר מהתאים הרגילים.\n הם עוצרים את הגדילה של התאים הרגילים\n וגורמים למחלה.",
            "קיימים יותר מ100 סוגי סרטן שונים.\n בדרך כלל הם נקראים על שם המקום בגוף בו התאים התחילו לגדול לראשונה.",
            "ריאות שלנו מקבלות חמצן מהאוויר שאנחנו נושמים.",
            "סרטן הריאות נוצר כאשר כמות גדולה של תאים ראים נוצרת בתוך הריאות.",
            "סיבה עיקרית לסרטן זה הוא עישון."
    };
    private User currentUser;

    private boolean flipButtons=true;
    private String[] current_string;
    private int MAX_ABOUT=main_string.length;
    private int MAX_NEXT_STRING; //main_string size + next_string size
    private FireBaseHelper firebaseData;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_cancer_start);
        getSupportActionBar();


        firebaseData=new FireBaseHelper(this.getApplicationContext());
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        setEmail(savedInstanceState);


        initViews();
        initListeners();
        changeButton();
        setCancertype(savedInstanceState);

        textviewabout.setText(main_string[currentIndex]);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_back).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intentRegister = new Intent(getApplicationContext(), AboutCancerActivity.class);
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                intentRegister.putExtra("currentUser", currentUser);
                startActivity(intentRegister);
                finish();
                return (true);
            case R.id.action_logout:
                intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                //currentUser=firebaseData.getUser(currentUser.usermail,currentUser.password);
                intentRegister.putExtra("currentUser", currentUser);
                startActivity(intentRegister);
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void initListeners() {

        back.setOnClickListener(this);
        next.setOnClickListener(this);
        back2.setOnClickListener(this);
        next2.setOnClickListener(this);

    }
    private void initViews() {
        next = (AppCompatButton) findViewById(R.id.next);
        back = (AppCompatButton) findViewById(R.id.back);
        next2 = (AppCompatButton) findViewById(R.id.next2);
        back2 = (AppCompatButton) findViewById(R.id.back2);
        textviewabout =(TextView) findViewById(R.id.textviewabout);
        imageicon= (ImageView) findViewById(R.id.imageicon);

    }
    private void setEmail(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userEmail= null;
                userPassword=null;

            } else {
                userEmail= extras.getString("EMAIL");
                userPassword= extras.getString("PASSWORD");

            }
        } else {
            userEmail= (String) savedInstanceState.getSerializable("EMAIL");
            userPassword= (String) savedInstanceState.getSerializable("PASSWORD");

        }
      //  currentUser = (User) getIntent().getSerializableExtra("currentUser");

    }

    private void setCancertype(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                cansertype= null;
            } else {
                cansertype= extras.getString("CANCER");
            }
        } else {
            cansertype= (String) savedInstanceState.getSerializable("CANCER");
        }

        switch (cansertype){
            case "Brain":
                current_string=brain_string;
                MAX_NEXT_STRING=MAX_ABOUT+brain_string.length;
                imageicon.setImageResource(R.drawable.aboutcancer5);
                break;
            case "Breast":
                current_string=breast_string;
                MAX_NEXT_STRING=MAX_ABOUT+breast_string.length;
                imageicon.setImageResource(R.drawable.aboutcancer6);
                break;
            case "Melanoma":
                current_string=melanoma_string;
                MAX_NEXT_STRING=MAX_ABOUT+melanoma_string.length;
                imageicon.setImageResource(R.drawable.aboutcancer4);
                break;
            case "Skin":
                current_string=skin_string;
                MAX_NEXT_STRING=MAX_ABOUT+skin_string.length;
                imageicon.setImageResource(R.drawable.aboutcancer2);
                break;

        }

    }


    public void onClick(View v) {
        Intent intentRegister=null;

        int id=v.getId();
        int buttonCase=-1;
        if(id==R.id.next||id==R.id.next2)
            buttonCase=0;
        else if(id==R.id.back ||id==R.id.back2)
            buttonCase=1;


        switch (buttonCase) {
            case 0:
                if(currentIndex>=0 && currentIndex<MAX_ABOUT-1)
                    setNextText();

                else if(currentIndex>=MAX_ABOUT-1 && currentIndex<MAX_NEXT_STRING-1){
                    setNextTextType();
                }
                else if(currentIndex==MAX_NEXT_STRING-1){
                    textviewabout.setText("עכשיו אפשר לעבור למשחק טריוויה");
                    currentIndex++;
                    next.setText("לשחק!");
                }
                else if (currentIndex>MAX_NEXT_STRING-1){
                    intentRegister = new Intent(getApplicationContext(), TriviaGameActivity.class);
                    intentRegister.putExtra("EMAIL", userEmail.toString());
                    intentRegister.putExtra("PASSWORD", userPassword.toString());
                    intentRegister.putExtra("currentUser", currentUser);
                    startActivity(intentRegister);
                    finish();
                }
                changeButton();
                break;

            case 1:
                if(currentIndex>0 && currentIndex<=MAX_ABOUT) {
                    getPrevText();
                    changeButton();
                    break;
                }
                else if(currentIndex>MAX_ABOUT && currentIndex<MAX_NEXT_STRING-1) {
                    getPrevTextType();
                    changeButton();
                    break;

                }
                else{

                    intentRegister = new Intent(getApplicationContext(), AboutCancerActivity.class);
                    intentRegister.putExtra("EMAIL", userEmail.toString());
                    intentRegister.putExtra("PASSWORD", userPassword.toString());
                    intentRegister.putExtra("currentUser", currentUser);

                    startActivity(intentRegister);
                    break;

                }

        }


    }

    private void setNextText(){

        currentIndex++;
        textviewabout.setText(main_string[currentIndex]);

    }

    private void getPrevText(){
        currentIndex--;
        textviewabout.setText(main_string[currentIndex]);

    }
    private void setNextTextType(){
        currentIndex++;

        textviewabout.setText(current_string[currentIndex-MAX_ABOUT]);
    }

    private void getPrevTextType(){
        currentIndex--;
        textviewabout.setText(current_string[currentIndex-MAX_ABOUT]);

    }


    private void changeButton(){
        if(flipButtons){
            back2.setVisibility(View.INVISIBLE);
            next2.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);

            flipButtons=false;
            return;
        }
        else{
            back2.setVisibility(View.VISIBLE);
            next2.setVisibility(View.VISIBLE);
            back.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            flipButtons=true;
            return;
        }
    }

}
