package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) { //book dto
        bookRepository.save(new Book(request.getName()));
        System.out.println(request.getName());

    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        //1. 책정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        //2. 대출기록 정보를 확인해서 대출중인지 확인
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(),false)){
            //3. 만약에 대출중이면 예외를 발생
            throw new IllegalArgumentException("대출되어있습니다.");
        }

        //4. 유저정보 가져오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        //5.유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName(),false));

        user.loanBook(book.getName());

    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

       /* UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        history.doReturn();*/
        user.returnBook(request.getBookName());

        //userLoanHistoryRepository.save(history); 트랜잭셔널 기능으로 자동 저장
    }

}
