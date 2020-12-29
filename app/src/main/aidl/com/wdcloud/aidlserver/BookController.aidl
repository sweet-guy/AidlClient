// BookController.aidl
package com.wdcloud.aidlserver;
import com.wdcloud.aidlserver.Book;
// Declare any non-default types here with import statements
interface BookController {
    List<Book> getBookList();
    void addBookInOut(inout Book book);
}
