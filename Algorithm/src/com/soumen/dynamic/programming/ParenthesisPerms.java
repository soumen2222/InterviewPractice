package com.soumen.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParenthesisPerms {


    public static List<String> parenthesisPerms(int num){

       /* int open =num-1;
        int close = num;
        return parenthesisPermsUtil('(',open,close);*/

        char[] str = new char[num*2];
        List<String> list = new ArrayList<>();
        parenthesisPermsUtil1(list,num,num,str,0);
        return list;
    }

    private static List<String> parenthesisPermsUtil(char s, int open, int close) {
        if(open <0 || close <open) return new ArrayList<>();
        if(open==0 && close ==0) {
            List<String> list= new ArrayList<>();
            list.add(Character.toString(s));
            return list;
        }

        List<String> finalPerms = new ArrayList<>();
        if(close>open){
            List<String> perms1 = parenthesisPermsUtil(')', open, close - 1);
            for (String c:perms1) {
                StringBuilder sb = new StringBuilder(c);
                sb.insert(0, s);
                finalPerms.add(sb.toString());
            }
        }

        if(open>0){
            List<String> perms2 = parenthesisPermsUtil('(',open-1,close);
            for (String c:perms2) {
                StringBuilder sb = new StringBuilder(c);
                sb.insert(0, s);
                finalPerms.add(sb.toString());
            }
        }
        return finalPerms;
    }

    private static void parenthesisPermsUtil1(List<String> list,int open, int close ,char[] str, int count) {
       if(open <0 || close <open) return;

        if(open==0 && close ==0) {
            String s = String.copyValueOf(str);
            list.add(s);
        }else{
            if(open>0){
                str[count]='(';
                parenthesisPermsUtil1(list,open-1,close,str,count+1);
            }
            if(close>open){
                str[count]=')';
                parenthesisPermsUtil1(list, open, close - 1,str,count+1);
            }
        }
    }

    public static void main(String[] args){
        System.out.println("All Perms:" + parenthesisPerms(3));
    }
}
