package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonalItemList implements Serializable {
    private ArrayList<PersonalItem> allPersonalItems;

    public PersonalItemList(){
        allPersonalItems = new ArrayList<>();
    }

    public PersonalItem findPersonalItemByID(String id){
        for(PersonalItem i: allPersonalItems){
            if(i.getID().equals(id)){
                return i;
            }
        }
        return null;
    }

    public void add(PersonalItem newPersonalItem){
        allPersonalItems.add(newPersonalItem);
    }

    public void remove(String id){
        allPersonalItems.remove(findPersonalItemByID(id));
    }

    public void replace(String id, PersonalItem newPersonalItem){
        PersonalItem oldPersonalItem = this.findPersonalItemByID(id);
        oldPersonalItem.setName(newPersonalItem.getName());
        oldPersonalItem.setPersonalTotal(newPersonalItem.getPersonalTotal());
    }
}
