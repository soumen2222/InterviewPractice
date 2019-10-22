package com.honeywell.java.memory.model;

import java.lang.ref.WeakReference;

public class WeakRefExample {

    public static void main(String[] args) {

        PersonWeakRef person = new PersonWeakRef();
        WeakReference<PersonWeakRef> wr = new WeakReference<PersonWeakRef>(person);

        PersonWeakRef p1 = wr.get();
        System.out.println(p1);

        person = null;
        p1 = null;
        PersonWeakRef p2 = wr.get();
        System.out.println(p2);

        p2 = null;
        System.gc();
        PersonWeakRef p3 = wr.get();
        System.out.println(p3);

    }
}

class PersonWeakRef {

}
