package com.example.androidtest;

import android.app.ListFragment;  
import android.os.Bundle;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ListView;  
  
public class TabFirst extends ListFragment {  
  
    private AnimalListAdapter adapter = null;  
      
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
          
        adapter = new AnimalListAdapter (getActivity());  
        setListAdapter(adapter);    
    }  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View v = inflater.inflate(R.layout.tab_first, container, false);  
        return v;  
    }  
      
    @Override  
    public void onListItemClick(ListView l, View v, int position, long id) {  
        System.out.println("Click On List Item!!!");  
        super.onListItemClick(l, v, position, id);  
    }  
}  
