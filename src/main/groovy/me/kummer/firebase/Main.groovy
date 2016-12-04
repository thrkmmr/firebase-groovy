package me.kummer.firebase

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot

//import com.firebase.client
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.Query
import com.firebase.client.ValueEventListener
import java.util.concurrent.Semaphore

class Main {

    static void main(args) {

        def semaphore = new Semaphore(1)
        semaphore.acquire()

        Firebase rootRef = new Firebase("https://${('firebase_' as File).text}.firebaseio.com/");

        Query queryRef = rootRef.orderByChild("user");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            void onChildAdded(DataSnapshot snapshot, String previousChild) {
                println "Added: $snapshot.key: $snapshot.value"
                semaphore.release()
            }

            @Override
            void onChildChanged(DataSnapshot snapshot, String s) {
                println "Changed: $snapshot.key: $snapshot.value"
                semaphore.release()
            }

            @Override
            void onChildRemoved(DataSnapshot snapshot) {
                println "Removed: $snapshot.key: $snapshot.value"
                semaphore.release()
            }

            @Override
            void onChildMoved(DataSnapshot snapshot, String s) {
                println "Moved: $snapshot.key: $snapshot.value"
                semaphore.release()
            }

            @Override
            void onCancelled(FirebaseError error) {
                println "Cancelled: $error"
                semaphore.release()
            }
        });


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            void onDataChange(DataSnapshot snapshot) {
                println(snapshot.getValue());
                semaphore.release()
            }
            @Override
            void onCancelled(FirebaseError firebaseError) {
                println("The read failed: " + firebaseError.getMessage());
                semaphore.release()
            }
        });

        println "Aquiring semaphore"
        semaphore.acquire()
    }

}
