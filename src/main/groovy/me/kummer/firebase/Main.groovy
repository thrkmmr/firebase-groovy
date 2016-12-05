package me.kummer.firebase

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.Query
import com.firebase.client.ValueEventListener

class Main {

    static void main(args) {

        Firebase rootRef = new Firebase("https://${('firebase_' as File).text}.firebaseio.com/");

        Query queryRef = rootRef.orderByChild("user");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            void onChildAdded(DataSnapshot snapshot, String previousChild) {
                println "Added: $snapshot.key: $snapshot.value"
            }

            @Override
            void onChildChanged(DataSnapshot snapshot, String s) {
                println "Changed: $snapshot.key: $snapshot.value"
            }

            @Override
            void onChildRemoved(DataSnapshot snapshot) {
                println "Removed: $snapshot.key: $snapshot.value"
            }

            @Override
            void onChildMoved(DataSnapshot snapshot, String s) {
                println "Moved: $snapshot.key: $snapshot.value"
            }

            @Override
            void onCancelled(FirebaseError error) {
                println "Cancelled: $error"
            }
        });


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            void onDataChange(DataSnapshot snapshot) {
                println(snapshot.getValue());
            }

            @Override
            void onCancelled(FirebaseError firebaseError) {
                println "The read failed: ${firebaseError.message}"
            }
        });

        Thread.sleep 60000

        println "Exiting."
    }

}
