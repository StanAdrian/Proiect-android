package eu.ase.proiect.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import eu.ase.proiect.Glide.GlideApp;
import eu.ase.proiect.Glide.MyAppGlideModule;
import eu.ase.proiect.R;
import eu.ase.proiect.database.model.Book;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context context;
    private List<Book> listBooks;
    private LayoutInflater inflater;
    private int resource;

    public BookAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List<Book> objects,
                       LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listBooks = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false);
        Book book = listBooks.get(position);
        boolean b = false;
        if(book != null) {
            addBookTitle(view,book.getTitle());
            addBookAuthor(view, book.getAuthor());
            addRatingBar(view, book.getRating());
            addNbPages(view, book.getPages(), book.getReview());
            addBookImg(view, book.getImgUrl(), book.getDrawableResource());
            if(User.mapFavoriteBook.containsValue(book)){
                b = true;
            }
            addFavoriteImg(view, b);
        }

        return view;
    }

    private void addBookTitle(View view, String title) {
        TextView textView = view.findViewById(R.id.item_book_title);
        populateFromView(title, textView);
    }

    private void addBookAuthor(View view, String author) {
        TextView textView = view.findViewById(R.id.item_book_author);
        populateFromView(author, textView);
    }

    private void addRatingBar(View view, float rating){
        RatingBar ratingBar = view.findViewById(R.id.item_book_ratingbar);
        if(rating >= 0){
            ratingBar.setRating(rating);
        } else{
            ratingBar.setRating(0);
        }
    }

    private void addNbPages(View view, int pages, int review){
        TextView textView = view.findViewById(R.id.item_book_pagesrev);
        if (pages >= 0 && review >=0) {
            textView.setText(pages + " pages | " + review + " review");
        } else {
            textView.setText(R.string.no_content);
        }
    }

    private void addBookImg(View view, String imgUrl, int drawableResource){
        final ImageView imageView = view.findViewById(R.id.item_book_img);
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("Img_Carti/"+imgUrl);
// R.drawable.ic_a   MERGE
//        int NU MERGE

                try {
                imageView.setImageResource(drawableResource);
                }
                catch (Exception e){
                    if (imgUrl!=null || imgUrl!=""){
                        GlideApp.with(context).load(storageReference).into(imageView);
//                        try {
//                            final File localFile = File.createTempFile("images", "png");
//                            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                    imageView.setImageBitmap(bitmap);
//                                }
//                            });
//                        }
//                        catch (IOException x){
//                            x.printStackTrace();
//                        }

                    }
                    else {
                        imageView.setImageResource(R.drawable.ic_uploading_photo);
                        e.printStackTrace();
                    }

                }
            }


    private void addFavoriteImg(View view, boolean fav){
        ImageView imageView = view.findViewById(R.id.item_img_favorite);
        if (!fav) {
            imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_red_24);
        }

    }


    private void populateFromView(String string, TextView textView) {
        if (string != null && !string.trim().isEmpty()) {
            textView.setText(string);
        } else {
            textView.setText(R.string.no_content);
        }
    }

}
