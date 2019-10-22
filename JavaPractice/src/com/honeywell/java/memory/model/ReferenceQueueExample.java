package com.honeywell.java.memory.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReferenceQueueExample {

    public static void main(String[] args) throws IOException {
        RefPerson p = new RefPerson();
        final ReferenceQueue<RefPerson> referenceQueue = new ReferenceQueue<RefPerson>();
        PersonCleaner cleaner = new PersonCleaner();
        PersonWeakReference weakReference = new PersonWeakReference(p, cleaner, referenceQueue );

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PersonWeakReference wr = (PersonWeakReference) referenceQueue.remove();
                    wr.clean();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        p = null;
        System.gc();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press any key to continue");
        br.readLine();
        executorService.shutdown();

    }
}

final class RefPerson {

}

class PersonCleaner {

    public void clean() {
        System.out.println("Cleaned");
    }
}

class PersonWeakReference extends WeakReference<RefPerson> {

    PersonCleaner cleaner;
    public PersonWeakReference(RefPerson referent, PersonCleaner cleaner, ReferenceQueue<? super RefPerson> q) {
        super(referent, q);
        this.cleaner = cleaner;
    }

    public void clean(){
        cleaner.clean();
    }
}