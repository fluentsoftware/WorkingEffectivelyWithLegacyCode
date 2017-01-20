package com.thoughtworks.legacycode.expose_static_method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Library.class)
public class LibraryTest {
//  Expose Static Method

//  Change existing method to be static (if it can be). You can test without an instance

    @Test
    public void shouldFindOverdueBooks() {
        List<Book> books = new ArrayList<Book>();
        Book book = new Book("Starship Troopers", true);
        books.add(book);

        // Test will pause here while waiting for user input
        suppress(constructor(Library.class, Console.class));
        Library library = new Library(System.console());

        // If overdueBooks was static we wouldn't have to create a new library
        List<Book> overdueBooks = library.overdueBooks(books);

        assertThat(overdueBooks, hasItem(book));
    }
}
