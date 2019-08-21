package com.a155337.lighttea.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemberList implements Serializable {
    ArrayList<Member> allMembers;

    public MemberList(){
        allMembers = new ArrayList<>();
    }

    public Member findMemberByName(String name){
        for( Member i: allMembers){
            if(i.getName().equals(name))
                return i;
        }
        return null;
    }

    public String[] getNameList(){
        String[] nameList = new String[allMembers.size()];
        for(int i = 0; i < allMembers.size(); i++){
            nameList[i] = allMembers.get(i).getName();
        }
        return nameList;
    }

    public int size(){
        return allMembers.size();
    }

    public void add(Member newMember){
        allMembers.add(newMember);
    }

    public void decreaseBalanceForAll(float total){
        float average = total / this.size();
        for(Member i: allMembers){
            i.decreaseBalance(average);
        }
    }

    public void clearBalance(){
        for(Member i: allMembers){
            i.setBalance(0);
        }
    }

    public List<Member> getAllMembers(){
        return allMembers;
    }

    public int memberIndex(Member target){
        return allMembers.indexOf(target);
    }
}
