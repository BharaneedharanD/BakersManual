package modal;

import java.util.ArrayList;

public class BakeItem {

    private String name;
    private int iD;
    private ArrayList<String> steps;
    private ArrayList<String> stepDescription;
    private ArrayList<String> ingredients;
    private ArrayList<String> videoUrls;

    public BakeItem() {
         steps=new ArrayList<>();
         steps.add("Ingredients");
         stepDescription=new ArrayList<>();
         stepDescription.add("Ingredients");
         ingredients=new ArrayList<>();
         videoUrls=new ArrayList<>();
         videoUrls.add("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getVideoUrls() {
        return videoUrls;
    }
    public void addSteps(String step){
        steps.add(step);
    }
    public void addIngredients(String ingredient){
        ingredients.add(ingredient);
    }
    public void addVideoUrl(String url){
        videoUrls.add(url);
    }
    public void addStepDescription(String url){
        stepDescription.add(url);
    }
    public ArrayList<String> getStepDescription() {
        return stepDescription;
    }
}
