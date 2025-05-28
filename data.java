package com.mouli.QuestionService.config;

import com.mouli.QuestionService.model.Question;
import com.mouli.QuestionService.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionDataLoader {

    @Autowired
    private QuestionRepository questionRepository;

    @PostConstruct
    public void loadData() {
        questionRepository.save(new Question(null, "What is responsible for executing Java bytecode?", "JVM", "JRE", "JDK", "JAR", "Easy", "java", "JVM"));
        questionRepository.save(new Question(null, "What is the blueprint for creating objects in Java?", "Object", "Class", "Interface", "Method", "Medium", "java", "Class"));
        questionRepository.save(new Question(null, "Which keyword is used to prevent inheritance in Java?", "Static", "Final", "Private", "Public", "Hard", "java", "Final"));
        questionRepository.save(new Question(null, "Which of the following is a primitive data type?", "int", "Integer", "String", "Boolean", "Easy", "java", "int"));
        questionRepository.save(new Question(null, "Which method is used to compare two strings by content in Java?", "==", "equals()", "!=", "equalsIgnoreCase()", "Medium", "java", "equals()"));
        questionRepository.save(new Question(null, "Which Java collection class maintains insertion order?", "ArrayList", "LinkedList", "HashMap", "TreeMap", "Hard", "java", "LinkedList"));
        questionRepository.save(new Question(null, "Which block is always executed in exception handling?", "try", "catch", "finally", "throw", "Medium", "java", "finally"));
        questionRepository.save(new Question(null, "Which keyword is used to define a function in Python?", "def", "function", "fun", "define", "Easy", "python", "def"));
        questionRepository.save(new Question(null, "Which Python data type is immutable?", "list", "tuple", "set", "dict", "Medium", "python", "tuple"));
        questionRepository.save(new Question(null, "Which function is used to apply a function cumulatively to items in a list?", "lambda", "map", "filter", "reduce", "Hard", "python", "reduce"));
        questionRepository.save(new Question(null, "What is used to define blocks of code in Python?", "indentation", "brackets", "semicolons", "parentheses", "Easy", "python", "indentation"));
        questionRepository.save(new Question(null, "Which operator checks for identity in Python?", "is", "==", "=", "equals", "Medium", "python", "is"));
        questionRepository.save(new Question(null, "Which special variable in Python is used to check if the script is run directly?", "__init__", "__main__", "__str__", "__name__", "Hard", "python", "__name__"));
        questionRepository.save(new Question(null, "What error is raised when accessing an invalid list index?", "TypeError", "SyntaxError", "IndexError", "IndentationError", "Medium", "python", "IndexError"));
        questionRepository.save(new Question(null, "Which is the correct syntax to display output in Python?", "print()", "echo", "System.out.println", "console.log", "Easy", "python", "print()"));
    }
}


// --- Fallback Methods ---

    // default ResponseEntity<List<Integer>> getQuestionFallback(String category, Integer numQ, Throwable t) {
    //     System.out.println("Fallback: Question Service is down. Returning empty list. Reason: "+ t.getMessage());
    //     return ResponseEntity.status(503).body(List.of());
    // }

    // default ResponseEntity<List<QuestionWrapper>> getQuestionsFallback(List<Integer> ids, Throwable t) {
    //     System.out.println("Fallback: getQuestions. Reason: "+ t.getMessage());
    //     return ResponseEntity.status(503).body(new ArrayList<>());
    // }

    // default ResponseEntity<Integer> getScoreFallback(List<Response> responses, Throwable t) {
    //     System.out.println("Fallback: getScore. Reason: "+ t.getMessage());
    //     return ResponseEntity.status(503).body(0);
    // }


// # Retry config
// resilience4j.retry.instances.quizServiceRetry.max-attempts=3
// resilience4j.retry.instances.quizServiceRetry.wait-duration=500ms

// # Enable jitter/random delay between retries
// resilience4j.retry.instances.quizServiceRetry.enable-randomized-wait=true
// resilience4j.retry.instances.quizServiceRetry.randomized-wait-factor=0.5

//# circuit breaker config
// resilience4j.circuitbreaker.instances.questionServiceCB.sliding-window-type=count_based
// resilience4j.circuitbreaker.instances.questionServiceCB.sliding-window-size=10
// resilience4j.circuitbreaker.instances.questionServiceCB.failure-rate-threshold=50
// resilience4j.circuitbreaker.instances.questionServiceCB.wait-duration-in-open-state=10s
// resilience4j.circuitbreaker.instances.questionServiceCB.permitted-number-of-calls-in-half-open-state=3


package com.mouli.QuizService;


import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RetryLogger {

    private final RetryRegistry retryRegistry;

    public RetryLogger(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void registerRetryEventLogger() {
        retryRegistry.retry("quizServiceRetry")
                .getEventPublisher()
                .onRetry(event -> logRetryEvent(event));
    }

    private void logRetryEvent(RetryOnRetryEvent event) {
        log.warn("RETRY attempt #{} for [{}], wait before next attempt: {} ms, reason: {}",
                event.getNumberOfRetryAttempts(),
                event.getName(),
                event.getWaitInterval().toMillis(),
                event.getLastThrowable() != null ? event.getLastThrowable().toString() : "unknown");
    }
}

# Retry config for Feign calls to Question Service
resilience4j.retry.instances.quizServiceRetry.max-attempts=3
resilience4j.retry.instances.quizServiceRetry.wait-duration=1s
resilience4j.retry.instances.quizServiceRetry.retry-exceptions=feign.RetryableException,java.net.ConnectException,java.net.SocketTimeoutException

