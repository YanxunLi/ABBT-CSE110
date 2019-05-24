package com.example.afs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class FoodHistory extends AppCompatActivity {
    private Button addItemButton;
    private SearchView searchBar;
    public ListView foodList;
    public String[] names;
    public String[] calories;
    public ArrayList<String> namesDB;
    public ArrayList<String> caloriesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_history);

        foodList = (ListView)findViewById(R.id.foodHistoryList);

        namesDB = new ArrayList<String>();
        caloriesDB = new ArrayList<String>();

        //TODO Read the list of names and calories from database, store in namesDB and caloriesDB



        //These are jsut tests
        namesDB.add("Apple");
        namesDB.add("Pineapple");
        namesDB.add("Banana");
        namesDB.add("Pork");
        namesDB.add("Chicken");
        namesDB.add("Bag");
        caloriesDB.add(""+200);
        caloriesDB.add(""+300);
        caloriesDB.add(""+400);
        caloriesDB.add(""+200);
        caloriesDB.add(""+300);
        caloriesDB.add(""+400);


        //store the list into array
        updateFoodAdapter(namesDB, caloriesDB);

        //the search functionality is implemented here
        SearchView searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            /**
             * It will update the list by checking if the name contains the query
             * @param query The search pattern entered by the user
             */
            public void callSearch(String query) {
                ArrayList<String> tempName = new ArrayList<String>();
                ArrayList<String> tempCalorie = new ArrayList<String>();
                for(int i = 0; i < namesDB.size(); i++) {
                    String curName = namesDB.get(i).toLowerCase();
                    if (curName.indexOf(query.toLowerCase()) != -1) {
                        tempName.add(namesDB.get(i));
                        tempCalorie.add(caloriesDB.get(i));
                    }
                }
                updateFoodAdapter(tempName, tempCalorie);
            }

        });

        addItemButton = (Button) findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
            }
        });
    }


    public void addItem() {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);
    }

    /**
     * It updates the list with the new name list and calorie list
     * @param nameList The list of names
     * @param calorieList The list of calories
     */
    public void updateFoodAdapter(ArrayList<String> nameList, ArrayList<String> calorieList)
    {
        //store the list into array
        names = new String[nameList.size()];
        calories = new String[calorieList.size()];
        nameList.toArray(names);
        calorieList.toArray(calories);

        //get the food list gadget
        foodList = (ListView)findViewById(R.id.foodHistoryList);

        //set up the adapter
        FoodAdapter foodAdapter= new FoodAdapter(getApplicationContext(), names, calories);
        foodList.setAdapter(foodAdapter);
    }
}
