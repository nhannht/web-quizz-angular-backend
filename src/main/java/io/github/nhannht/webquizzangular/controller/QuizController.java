package io.github.nhannht.webquizzangular.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.nhannht.webquizzangular.dto.*;
import io.github.nhannht.webquizzangular.entity.Quiz;
import io.github.nhannht.webquizzangular.entity.User;
import io.github.nhannht.webquizzangular.repository.QuizRepository;
import io.github.nhannht.webquizzangular.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class QuizController {
    Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Operation(description = "get a page with maximum 10 quizzes ")
    @GetMapping("/api/quizzes")
    public ResponseEntity<Object> getQuiz(@RequestParam Integer page) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page, 10);
        logger.error("We are using get quiz");
        ModelMapper model = new ModelMapper();
        Page<Quiz> quizListPageObject = quizRepository.findAll(pageable);
        List<Quiz> quizList = quizListPageObject.getContent();
        List<QuizDTOWithoutAns> quizDTOList = new ArrayList<>();
        for (Quiz q : quizList) {
            quizDTOList.add(model.map(q, QuizDTOWithoutAns.class));
        }
        HashMap<String, Object> node = new HashMap<>();
        node.put("totalPages", quizListPageObject.getTotalPages());
        node.put("totalElements", quizListPageObject.getTotalElements());
        node.put("last", quizListPageObject.isLast());
        node.put("first", quizListPageObject.isFirst());
        node.put("sort", quizListPageObject.getSort().isSorted());
        node.put("number", quizListPageObject.getTotalElements());
        node.put("numberOfElements", quizListPageObject.getTotalElements());
        node.put("size", 10);
        node.put("empty", quizListPageObject.isEmpty());
        node.put("pagable", true);
        node.put("content", quizDTOList);
        ObjectMapper mapperJ = new ObjectMapper();
        logger.error("The content return is " + mapperJ.writerWithDefaultPrettyPrinter().writeValueAsString(node));
        return ResponseEntity.ok(node);
    }

    @Operation(description = "create new quizz")
    @PostMapping("/api/quizzes")
    public ResponseEntity<Object> postQuiz(@RequestBody @Valid QuizDTO quizDTO, Authentication auth) {
        logger.error("We are using create quiz at /api/quizzes");
        logger.error(String.valueOf(auth));
        ModelMapper model = new ModelMapper();
        Quiz quiz = model.map(quizDTO, Quiz.class);
        if (quizDTO.getAnswer() == null) {
            quiz.setAnswer(Collections.emptyList());
        }
        quiz.setUser(userRepository.findByEmail(auth.getName()).orElseThrow());
        Quiz savedQuiz = quizRepository.save(quiz);
        savedQuiz.setUser(null);
        QuizDTOWithoutAns returnQuiz = model.map(savedQuiz, QuizDTOWithoutAns.class);
        return ResponseEntity.ok(returnQuiz);
    }

@Operation(description = "get one quiz")
    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Object> getOneQuiz(@PathVariable Long id) {
        logger.error("We are using get one quiz");
        ModelMapper model = new ModelMapper();
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            QuizDTOWithoutAns returnQuiz = model.map(quiz.get(), QuizDTOWithoutAns.class);
            return ResponseEntity.ok(returnQuiz);
        }
    }

    @Operation(summary = "Solve a quiz by known it id")
    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Object> solveQuiz(@PathVariable Long id, @RequestBody @Valid AnswerDTO answer, Authentication auth) throws JsonProcessingException {
        logger.error("we are testing question solve");
        List<Integer> answerListFromCustomer = answer.getAnswer();
        logger.error("The answer is " + answerListFromCustomer);

        ObjectMapper mapperJ = new ObjectMapper();
        ObjectNode node = mapperJ.createObjectNode();

        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User currentUser = userRepository.findByEmail(auth.getName()).orElseThrow();

        List<Integer> quizAnswer = quiz.get().getAnswer();
        logger.error("The answer of the quiz is " + quizAnswer);
        if (CollectionUtils.isEqualCollection(answerListFromCustomer,quizAnswer)) {
            node.put("success", true);
            node.put("feedback", "Congratulations, you're right!");
            Quiz solvedQuiz = quiz.get();
//            solvedQuiz.setCompletedAt(Instant.now().toString());
            currentUser.addIdQuizSolve(LocalDateTime.now().toString(), id);
            userRepository.save(currentUser);
            quizRepository.save(solvedQuiz);
            return ResponseEntity.ok(mapperJ.writerWithDefaultPrettyPrinter().writeValueAsString(node));
        } else {
            node.put("success", false);
            node.put("feedback", "Wrong answer! Please, try again.");
            return ResponseEntity.ok(mapperJ.writerWithDefaultPrettyPrinter().writeValueAsString(node));
        }
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Object> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page, Authentication auth) throws JsonProcessingException {
        logger.error("We are using get all completed quizz");
        logger.error("Page number is " + page);
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Map<String, Long> quizAnswerByUser = user.getIdOfQuizCollection();
        logger.error("User is " + user.getEmail());
//        List<Quiz> quizzesSolvedByUser = user.getAnsweredQuiz();

        Pageable pageable = PageRequest.of(page, 10);


        /**
         * For logger purpose
         */
        Page<Quiz> allQuiz = quizRepository.findAll(pageable);

        Map<String, Long> quizMapToReturn = user.getIdOfQuizCollection();
        logger.error("quiz solved of User is " + user.getIdOfQuizCollection());
        List<QuizOnlyWithIdAndDateCompleted> quizListToReturn = new ArrayList<>();
        quizMapToReturn.entrySet().forEach(e -> quizListToReturn.add(new QuizOnlyWithIdAndDateCompleted(e.getValue(), e.getKey())));

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), quizListToReturn.size());
        Page<QuizOnlyWithIdAndDateCompleted> pageO = new PageImpl<>(quizListToReturn.subList(start,end),pageable,quizListToReturn.size());


//        Page<Quiz> quizPageObject = new PageImpl<>(quizzesSolvedByUser,pageable,quizzesSolvedByUser.size());
        var result = pageO.getContent();
        var result2 = result.stream().sorted(Comparator.comparing(QuizOnlyWithIdAndDateCompleted::getCompletedAt)).collect(Collectors.toList());
        Collections.reverse(result2);
        Map<String, Object> node = new HashMap<>();
//        node.put("totalPages",quizzesSolvedByUser.getTotalPages());
//        node.put("totalElements",quizzesSolvedByUser.getTotalElements());
//        node.put("last",quizzesSolvedByUser.isLast());
//        node.put("first",quizzesSolvedByUser.isFirst());
//        node.put("empty",quizzesSolvedByUser.isEmpty());
        node.put("content", result2);
        ObjectMapper mapperJ = new ObjectMapper();
        Map<String, Object> allQuizMap = new HashMap<>();

        allQuizMap.put("All_quiz", allQuiz.getContent());
        logger.error("All quiz in db is " + mapperJ.writerWithDefaultPrettyPrinter().writeValueAsString(allQuizMap));

        return ResponseEntity.ok(node);
    }
    @Operation(description = "register new user")
    @PostMapping("/api/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDTO userDTO) {
        logger.error("We are using register user");
        logger.error("The user is registing is " + userDTO.getEmail() + userDTO.getPassword());
        Optional<User> testUser = userRepository.findByEmail(userDTO.getEmail());
        if (testUser.isPresent()) {
            logger.error("Bug here? ");
            return ResponseEntity.badRequest().build();
        }
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id, Authentication auth) {
        logger.error("We are using delete ");
        Optional<Quiz> quizFromDb = quizRepository.findById(id);
        if (quizFromDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> userFromDb = userRepository.findByEmail(auth.getName());
        if (!userFromDb.orElseThrow().getId().equals(quizFromDb.get().getUser().getId())) {
            return ResponseEntity.status(403).build();
        }
        quizRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
