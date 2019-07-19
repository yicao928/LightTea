package com.a155337.lighttea.Object;

import java.util.ArrayList;

public class MemberList {
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
}
