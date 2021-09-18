package io.github.nhannht.webquizzangular.repository;

import io.github.nhannht.webquizzangular.entity.Quiz;
import io.github.nhannht.webquizzangular.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Page<Quiz> findAllBy(Pageable pageable);
    Page<Quiz> findAllByUser(User user, Pageable pageable);
    @Query(value = "select * from quiz where user_answered contains ?1 and completed_at is not null",nativeQuery = true)
    Page<Quiz> findAllQuizThatAnswerByUser(User user,Pageable pageable);
    @Query(value = "select * from quiz where completed_at is not null",nativeQuery = true)
    Page<Quiz> findAllByAndCompletedAtIsNotNull(Pageable pageable);
}

