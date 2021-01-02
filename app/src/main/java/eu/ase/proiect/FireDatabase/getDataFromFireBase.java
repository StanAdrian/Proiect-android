package eu.ase.proiect.FireDatabase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import eu.ase.proiect.database.model.Book;
import eu.ase.proiect.util.Author;

public class getDataFromFireBase {

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference=firebaseDatabase.getReference("Carti");
    public static String TAG;

//    public static void getaBook(final ArrayList<Book> listBooks) {
//        firebaseFirestore.collection("Carti").document("solo_leveling")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                      Book  carte = documentSnapshot.toObject(Book.class);
//                      listBooks.add(carte);
//                    }
//                });
//    }

    public static void getBooks(final List<Book> listBooks){
        firebaseFirestore.collection("Carti").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Book carte = document.toObject(Book.class);
                        listBooks.add(carte);
                    }
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public static void getAuthors(final List<Author> listaAutori)
    {
        firebaseFirestore.collection("Autori").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult())
                    {
                        Author autor = document.toObject(Author.class);
                        listaAutori.add(autor);
                    }
                }
            }
        });
    }


   /* public static void getaCarteDinDB(List<Book> listBooks)
    {
        databaseReference.child().
    }*/


    }
