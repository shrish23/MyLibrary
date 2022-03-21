package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private TextView txtBookName, txtAuthor, txtPages, txtDexcription,txtLDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorites;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longDescription = "A young woman named Aomame follows a taxi driver’s enigmatic suggestion and begins to notice puzzling discrepancies in the world around her. She has entered, she realizes, a parallel existence, which she calls 1Q84 “Q is for ‘question mark.’ A world that bears a question.” Meanwhile, an aspiring writer named Tengo takes on a suspect ghostwriting project. He becomes so wrapped up with the work and its unusual author that, soon, his previously placid life begins to come unraveled."+
//                "\n"+
//                "As Aomame’s and Tengo’s narratives converge over the course of this single year, we learn of the profound and tangled connections that bind them ever closer: a beautiful, dyslexic teenage girl with a unique vision; a mysterious religious cult that instigated a shoot-out with the metropolitan police; a reclusive, wealthy dowager who runs a shelter for abused women; a hideously ugly private investigator; a mild-mannered yet ruthlessly efficient bodyguard; and a peculiarly insistent television-fee collector."+
//                "\n"+
//                "A love story, a mystery, a fantasy, a novel of self-discovery, a dystopia to rival George Orwell’s 1Q84 is Haruki Murakami’s most ambitious undertaking yet: an instant best seller in his native Japan, and a tremendous feat of imagination from one of our most revered contemporary writers.";
//
//        Book book = new Book(1,"1Q84","Haruki Murakami",1350,"https://images-na.ssl-images-amazon.com/images/I/81M0jxrDz5L.jpg","A work of madening brilliance",longDescription);

        Intent intent = getIntent();
        if(null != intent){
            int bookId = intent.getIntExtra("bookId",-1);
            if(bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(null != incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }

    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for(Book b: favoriteBooks){
            if (b.getId() == book.getId()){
                existInFavoriteBooks = true;
            }
        }

        if (existInFavoriteBooks){
            btnAddToFavorites.setEnabled(false);
        }else{
            btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavorite(book)){
                        Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,FavoriteActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Something Wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInCurrentlyReadingBooks = false;

        for(Book b: currentlyReadingBooks){
            if (b.getId() == book.getId()){
                existInCurrentlyReadingBooks = true;
            }
        }

        if (existInCurrentlyReadingBooks){
            btnAddToCurrentlyReading.setEnabled(false);
        }else{
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
                        Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,CurrentlyReadingActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Something Wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for(Book b: wantToReadBooks){
            if (b.getId() == book.getId()){
                existInWantToReadBooks = true;
            }
        }

        if (existInWantToReadBooks){
            btnAddToWantToRead.setEnabled(false);
        }else{
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,WantToReadBookActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Something Wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Handle the list add book
     * And enabling and disabling of the button
     * @param book
     */

    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existsInAlreadyReadBooks = false;

        for(Book b: alreadyReadBooks){
            if (b.getId() == book.getId()){
                existsInAlreadyReadBooks = true;
            }
        }

        if (existsInAlreadyReadBooks){
            btnAddToAlreadyRead.setEnabled(false);
        }else{
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,AlreadyReadBookActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Something Wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDexcription.setText(book.getShortDesc());
        txtLDescription.setText(book.getLongDesc());

        Glide.with(this).asBitmap().load(book.getImageUrl()).into(bookImage);
    }

    private void initViews() {
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtDexcription = findViewById(R.id.textDescription);
        txtPages = findViewById(R.id.txtPages);
        txtBookName = findViewById(R.id.txtBookName);
        txtLDescription = findViewById(R.id.txtDescription);


        btnAddToAlreadyRead = findViewById(R.id.btnAlreadyread);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);
        btnAddToWantToRead = findViewById(R.id.btnAddToWishlist);

        bookImage = findViewById(R.id.imageBook);
    }
}