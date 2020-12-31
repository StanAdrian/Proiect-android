package eu.ase.proiect.database.service;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.proiect.asyncTask.AsyncTaskRunner;
import eu.ase.proiect.asyncTask.Callback;
import eu.ase.proiect.database.DatabaseManager;
import eu.ase.proiect.database.dao.BookDao;
import eu.ase.proiect.database.model.Book;

public class BookService {

    private final BookDao bookDao;
    private final AsyncTaskRunner taskRunner;


    public BookService(Context context) {
        bookDao = DatabaseManager.getInstance(context).getBookDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Book>> callback){
        Callable<List<Book>> callable = new Callable<List<Book>>() {
            @Override
            public List<Book> call() {
                return bookDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void getAllBooksFavorite(Callback<List<Book>> callback){
        Callable<List<Book>> callable = new Callable<List<Book>>() {
            @Override
            public List<Book> call() {
                return bookDao.getAllBooksFavorite();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }


    public void insertBook(Callback<Book> callback, final Book book){
        Callable<Book> callable = new Callable<Book>() {
            @Override
            public Book call() {
                if(book == null){
                    return null;
                }
                long id = bookDao.insert(book);
                if(id == -1){
                    return null;
                }
                book.setIdBook(id);
                return book;
            }
        };
        taskRunner.executeAsync(callable,callback);
    }


    public void updateBook(Callback<Book> callback, final Book book){
        Callable<Book> callable = new Callable<Book>() {
            @Override
            public Book call() {
                if(book == null){
                    return null;
                }
                int count = bookDao.update(book);
                if(count != 1){
                    return null;
                }
                return book;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Book book){
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if(book == null){
                    return -1;
                }
                return bookDao.delete(book);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }


}
