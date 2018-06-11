package com.androidtutorialshub.LuckyWithYou.model;

public class TriviaQues {

    private String Ques="";
    private String Ans1="";
    private String Ans2="";
    private String Ans3="";
    private String Ans4="";
    private String answer="";
    private boolean used=false;

    public String getQues() {
        return Ques;
    }
    public String getAns1() {
        return Ans1;
    }
    public String getAns2() {
        return Ans2;
    }
    public String getAns3() {
        return Ans3;
    }
    public String getAns4() {
        return Ans4;
    }
    public String getAns() {
        return answer;
    }

    public String getCorrectAns(){

        if(answer.equals(getAns1()))
            return getAns1();
        else if(answer.equals(getAns2()))
            return getAns2();
        else if(answer.equals(getAns3()))
            return getAns3();

        return getAns4();
    }

    //TODO
    public TriviaQues(String text){
        setData(text);
    }

    public  boolean checkUsed(){
        return used;
    }
    public void markAsUsed(){
        used=true;
    }

    public void setData(String text){


        int index1=text.indexOf("1)");
        int index2=text.indexOf("2)");
        int index3=text.indexOf("3)");
        int index4=text.indexOf("4)");

        Ans1=text.substring(index1+2, index2-1);
        Ans2=text.substring(index2+2, index3-1);
        Ans3=text.substring(index3+2, index4-1);
        Ans4=text.substring(index4+2, text.length()-2);

        answer=text.substring(text.length()-2, text.length()-1);
        Ques=text.substring(0, index1-1);

    }

}
