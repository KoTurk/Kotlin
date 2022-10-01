# How we built and migrated our Spring Boot applications to Kotlin

<img src="https://miro.medium.com/max/602/1*9KyJBCm1q-CyN6-8pN0rPA.png" />

## Prerequisites
- IDE
- Minimal Java 11
- Maven (Optional)

### Steps to convert your Spring Boot REST application to Kotlin
1. Migrate PaymentEngineApplication to Kotlin (check that pom is changed)
   1. use migration wizard
   2. add dependencies (if not asked by IDEA)
   3. add kotlin maven plugin
2. Rewrite PaymentController to Kotlin
   1. Delete @Slf4j
   2. delete @RequiredArgConstructor (lombok)
   3. @Autowired
      1. lateinit 
      2. constructor
   4. Delete throws and create something better
   5. Rewrite to runCatching
3. Rewrite Payment class to data class (no lombok). from JDK 14 you've the record class
4. Go to PaymentService (in basic project), and click on revert (IDEA migration wizard)
   1. delete the @Slf4j and @RequiredArgsConstructor
   2. PaymentService constructor
   3. change log.debug
   4. Don't use stream
   5. Use type alias
   6. Add coroutines dependency to pom.xml
   7. Replace async future to coroutines
   8. Rewrite to multiple catch
   9. Replace null checks with let and use .also
5. Rewrite the rest of the application
7. Rename package to kotlin
