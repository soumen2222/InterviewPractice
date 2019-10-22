package com.honeywell.java.memory.model;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class PhantomRefExample {

    public static void main(String[] args) {
        ReferenceQueue<PhantomPerson> queue = new ReferenceQueue<PhantomPerson>();
        ArrayList<FinalizePerson> list = new ArrayList<FinalizePerson>();
        ArrayList<PhantomPerson> people = new ArrayList<PhantomPerson>();

        for (int i = 0; i< 10; i++){
            PhantomPerson p = new PhantomPerson();
            people.add(p);
            list.add(new FinalizePerson(p, queue));
        }

        people = null;
        System.gc();

        for (PhantomReference<PhantomPerson> reference : list) {
            System.out.println(reference.isEnqueued());
        }

        Reference<? extends PhantomPerson> referenceFromQueue;
        while ((referenceFromQueue = queue.poll()) != null) {
            ((FinalizePerson) referenceFromQueue).cleanup();
        }

    }
}

class FinalizePerson extends PhantomReference<PhantomPerson>{

    public FinalizePerson(PhantomPerson referent, ReferenceQueue<? super PhantomPerson> q) {
        super(referent, q);
    }

    public void cleanup() {
        System.out.println("person is finalizing resources");
    }

}

class PhantomPerson {
}