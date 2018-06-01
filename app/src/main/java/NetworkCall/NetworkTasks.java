package NetworkCall;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bharanee.android.bakersmanual.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.ItemListAdapter;
import Modal.BakeItem;

public class NetworkTasks {
    public static ArrayList<BakeItem> items;
    private  String url;
    private RequestQueue requestQueue;
    public NetworkTasks(Context context) {
        items=new ArrayList<>();
        url=context.getString(R.string.API_URL);
        requestQueue= Volley.newRequestQueue(context);
    }
    public void parse(ItemListAdapter adapter){
        final ItemListAdapter mAdapter=adapter;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //For each item
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        BakeItem newItem=new BakeItem();
                        items.add(parseFurther(newItem,object));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.setData(items);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);

    }

    private BakeItem parseFurther(BakeItem newItem, JSONObject object) {
        try {
            newItem.setName(object.getString("name"));
            newItem.setiD(object.getInt("id"));
            JSONArray ingredientsArray=object.getJSONArray("ingredients");
            StringBuilder content;
            //Add all ingredients to the arraylist
            for (int i=0;i<ingredientsArray.length();i++){
                JSONObject ingredientObject= (JSONObject) ingredientsArray.get(i);
                content=new StringBuilder(ingredientObject.getString("ingredient"));
                content.append(" "+ingredientObject.getInt("quantity"));
                content.append(" "+ingredientObject.getString("measure"));
                newItem.addIngredients(content.toString());
            }
            //add steps to STEPS arraylist
            JSONArray stepsArray=object.getJSONArray("steps");
            for (int i=0;i<stepsArray.length();i++){
                JSONObject stepsObject= (JSONObject) stepsArray.get(i);
                newItem.addStepDescription(stepsObject.getString("shortDescription"));
                newItem.addSteps(stepsObject.getString("description"));
                String videoUrl=stepsObject.getString("videoURL");
                if (videoUrl.equals(""))
                    videoUrl=stepsObject.getString("thumbnailURL");
                newItem.addVideoUrl(videoUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
return newItem;
    }
}
