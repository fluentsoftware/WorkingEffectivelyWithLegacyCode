package com.thoughtworks.legacycode.parameterize_method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Library.class)
public class LibraryTest {
//  Parameterize Method
//  Inject a dependency instead of leaving it internal to a method
//
//  Break the dependency on BufferedReader by injecting it into the method

    @Test
    public void shouldPrintTheBookNameWhenThereIsOneBook() throws Exception {
        PrintStream printStream = mock(PrintStream.class);
        Library library = new Library(printStream);
        List<Book> books = new ArrayList<Book>();
        BufferedReader r = new BufferedReader(new StringReader("Title"));
        whenNew(BufferedReader.class).withAnyArguments().thenReturn(r);
        books.add(new Book("House of Leaves"));

        library.printBooks(books);

        verify(printStream).println("Title");
        verify(printStream).println("House of Leaves");
    }
}
