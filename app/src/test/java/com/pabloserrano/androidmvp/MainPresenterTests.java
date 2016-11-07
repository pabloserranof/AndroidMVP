package com.pabloserrano.androidmvp;

import com.pabloserrano.androidmvp.model.Book;
import com.pabloserrano.androidmvp.presentation.MainPresenter;
import com.pabloserrano.androidmvp.presentation.MainPresenterImpl;
import com.pabloserrano.androidmvp.repository.BooksRepository;
import com.pabloserrano.androidmvp.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class MainPresenterTests {

    @Mock
    BooksRepository mockBooksRepository;

    @Mock
    MainView mockView;

    MainPresenter presenter;

    @Mock
    BooksRepository.GetBooksCallback getBooksCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksRepository.GetBooksCallback> booksCallbackCaptor;

    @Spy
    private List<Book> listBooks = new ArrayList<>();

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        presenter = new MainPresenterImpl(mockBooksRepository);
    }

    @Test
    public void shouldShowErrorMessageOnViewWhenBooksNotAvailableAndNotCallLoadBooks() {

        presenter.setView(mockView);

        setBooksNotAvailable(mockBooksRepository);

        verify(mockView, times(1)).showBooksNotFound();
        verify(mockView, never()).loadBooks(listBooks);
    }

    @Test
    public void shouldNotShowErrorMessageOnViewWhenBooksAvailableAndCallLoadBooks() {

        presenter.setView(mockView);

        setBooksAvailable(mockBooksRepository);

        verify(mockView, never()).showBooksNotFound();
        verify(mockView, times(1)).loadBooks(listBooks);
    }


    private void setBooksNotAvailable(BooksRepository booksRepository) {

        verify(booksRepository).getBooks(booksCallbackCaptor.capture());
        booksCallbackCaptor.getValue().onDataNotAvailable();
    }

    private void setBooksAvailable(BooksRepository booksRepository) {

        verify(booksRepository).getBooks(booksCallbackCaptor.capture());
        booksCallbackCaptor.getValue().onBooksLoaded(listBooks);
    }
}