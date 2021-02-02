package com.fstg.deafinterepter.dao;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fstg.deafinterepter.callback.FireBaseCallBack;
import com.fstg.deafinterepter.callback.GetTutorByEmailCallBack;
import com.fstg.deafinterepter.db.ConnexionDb;
import com.fstg.deafinterepter.entities.Tutor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.fstg.deafinterepter.entities.Tutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorDao {
    static Tutor tutor;

    public static void insertUser(Tutor tutor) {
        Map<String, String> tutoro = new HashMap<>();
        tutoro.put("firstName", tutor.getFirstName());
        tutoro.put("lastName", tutor.getLastName());
        tutoro.put("exp", String.valueOf(tutor.getExp()));
        tutoro.put("email", tutor.getEmail());
        tutoro.put("nationality", tutor.getNationality());
        tutoro.put("phone", tutor.getPhone());
        tutoro.put("password", tutor.getPassword());
        tutoro.put("sexe", tutor.getSexe());
        // Add a new document with a generated ID
        ConnexionDb.db().collection("tuttors")
                .add(tutoro)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        Toast.makeText(getApplication, "", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });

    }


//    public static void getTutorByEmail2(String email, GetTutorCallback getTutorCallback) {
//        ConnexionDb.db().collection("tuttors").whereEqualTo("email", email)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            if (document.exists()) {
//                                Tutor tutor = document.toObject(Tutor.class);
//                                getTutorCallback.onComplete(tutor);
//                            } else {
//                                getTutorCallback.onComplete(null);
//                            }
//                            Log.i("TAG  get user", document.getId() + " => " + document.getData().get("userName"));
//                        }
//
//                    } else {
//                        Log.w("TAG", "Error getting documents.", task.getException());
//                    }
//                });
//    }

//    public static void getTutorByEmail2(String email, String password, GetTutorCallback getTutorCallback) {
//        ConnexionDb.db().collection("tuttors").whereEqualTo("email", email)
//                .whereEqualTo("password", password)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            if (document.exists()) {
//                                Tutor tutor = document.toObject(Tutor.class);
//                                getTutorCallback.onComplete(tutor);
//                            } else {
//                                getTutorCallback.onComplete(null);
//                            }
//                            Log.i("TAG  get user", document.getId() + " => " + document.getData().get("userName"));
//                        }
//
//                    } else {
//                        Log.w("TAG", "Error getting documents.", task.getException());
//                    }
//                });
//    }

    public static void findAllTutor(FireBaseCallBack fireBaseCallBack) {
        List<Tutor> tutors = new ArrayList<>();
        ConnexionDb.db().collection("tuttors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                Tutor tutor_obj = document.toObject(Tutor.class);
                                tutors.add(tutor_obj);
                            }
                        }
                        fireBaseCallBack.onComplete(tutors);
                        Log.i("TAG METHODE", tutors.toString());
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    public static void getTutorByEmail(String email, GetTutorByEmailCallBack getTutorByEmailCallBack) {
        ConnexionDb.db().collection("tuttors")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                Tutor tutor_obj = document.toObject(Tutor.class);
                                getTutorByEmailCallBack.onComplete(tutor_obj);
                                Log.i("TAG METHODE", tutor_obj.toString());
                            }
                        }
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    public static Tutor getTutorByEmail(String email) {
        findAllTutor(tutorList -> {
            for (Tutor t : tutorList) {
                if (t.getEmail().equalsIgnoreCase(email)) {
                    tutor = t;
                    break;
                }
                tutor = null;
            }
        });
        Log.i("TAG", tutor.toString());
        if (tutor == null) {
            return null;
        } else {
            return tutor;
        }
    }
}
