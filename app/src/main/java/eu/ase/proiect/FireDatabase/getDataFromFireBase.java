package eu.ase.proiect.FireDatabase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import eu.ase.proiect.database.model.Book;

public class getDataFromFireBase {

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference=firebaseDatabase.getReference("Carti");

    public static void getaBook(final ArrayList<Book> listBooks) {
        firebaseFirestore.collection("Carti").document("solo_leveling")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                      Book  carte = documentSnapshot.toObject(Book.class);
                      listBooks.add(carte);
                    }
                });
    }
   /* public static void getaCarteDinDB(List<Book> listBooks)
    {
        databaseReference.child().
    }*/
}
