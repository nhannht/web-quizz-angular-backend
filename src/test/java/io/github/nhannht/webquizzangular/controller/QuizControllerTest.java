package io.github.nhannht.webquizzangular.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nhannht.webquizzangular.dto.UserDTO;
import io.github.nhannht.webquizzangular.entity.Quiz;
import io.github.nhannht.webquizzangular.entity.User;
import io.github.nhannht.webquizzangular.repository.QuizRepository;
import io.github.nhannht.webquizzangular.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    Logger log = LoggerFactory.getLogger(QuizControllerTest.class);
    @MockBean
    QuizRepository quizRepository;

    @MockBean
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getQuiz() {
    }

    @Test
    void login() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/login")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void postQuiz() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes"))
                .andDo(print()).andExpect(status().isUnauthorized());

    }

    @Test
    void postQuizWithUser() throws Exception {

        HashMap<String, Object> node = new HashMap<>();
        ArrayList<Integer> answerList = new ArrayList<Integer>();
        answerList.add(1);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Milk");
        options.add("Banana");
        node.put("title", "Title");
        node.put("text", "Text");
        node.put("options", options);
        node.put("answer", answerList);
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        log.error(() -> data);
        User mockUser = new User();
        mockUser.setEmail("nhannht");
        mockUser.setPassword("password");
        Quiz mockQuiz = new Quiz();
        mockQuiz.setUser(mockUser);
        mockQuiz.setAnswer(answerList);
        mockQuiz.setOptions(options);
        mockQuiz.setTitle("Title");
        mockQuiz.setText("Text");

        Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(mockQuiz);
        Mockito.when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(java.util.Optional.of(mockUser));
        this.mockMvc.perform(MockMvcRequestBuilders.post(
                                        "/api/quizzes"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                                .with(user("nhannht").password("password"))
                )
                .andExpect(status().isOk());
    }

    @Test
    void registerUser() throws Exception {
        User mockUser = Mockito.mock(User.class);
        mockUser.setEmail("nhannht@gmail.com");
        mockUser.setPassword("password");
//        Mockito.when(userRepository.findByEmail("nhannht@gmail.com")).thenReturn(java.util.Optional.of(new User()));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);
        HashMap<String,String> data = new HashMap<>();
        data.put("email","nhannht@gmail.com");
        data.put("password","password");
        ObjectMapper mapper = new ObjectMapper();
        String dataString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataString)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOneQuiz() {
    }

    @Test
    void solveQuiz() {
    }

//    @Test
//    void getAllCompletedQuizzes() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes")
//                .queryParam("page", "0").with(user("user").password("password"))).andDo(print()).andExpect(status().isOk());
//    }

    @Test
    void loginTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "nhanclassroom@gmail.com")
                        .param("password", "password")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteQuiz() {
    }
}
