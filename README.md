## PS_Task_1

<p>This project features one of the applications developed under the guidance and mentoring during an internship at the company ProfItSoft, winter fall 2025.</p>

### Objective 
Develop a console application that utilizes parsing capabilities of Java to parse multiple files of a given structure (**data domain**) and produce corresponding statistics over the usage of particular attributes in them.

![img.png](img.png)

### Requirements

- The program must support multiple attributes and the user is up to select one of them.
- Establish coverage with `Unit` tests for the files mapping logic and the statistics formation.
- Avoid loading a full file content in the `RAM`. 
- Leverage usage of a `ThreadPool` from `Java Core` to read the files.
- Do not use `RDBMS` and `Spring`, work should only proceed with `Collections` in the `RAM`.
- Structure the code. Decouple the entities, extract mapping logic and console interface, statistics calculation logic etc. in separate classes and methods. 
- Take in consideration: Number of files can be deviously large as well as their size.  
- The statistics output must be in a `XML` format, the input data is expected to be `JSON`.

### Implementation

- Following entities were introduced: `Bookshelf`, `Book`, `Statistics`. Although existence of different POJO entities more than Bookshelf or Book seems to be not necessary for the task resolution.  
- Leveraged `JsonParser` from the `Jackson JSON` library to read attributes in a lazy fashion.
- Leveraged `Apache Commons CLI` to provide a convenient way to control the program startup.
- `Architectural layers`, `Decoupling`, `SOLID` were maintained to provide a clear structure of the application. `*` In particular was developed modular structure with the usage of `Gradle` to separate benchmarks (`jmh`) and main program (`core`) logic

### Building the application
Use the task `fatJar` in the `core` module or select the `./app/core` as your working directory and run it from the IDE.

### Notes
- Make sure to run the `core` project from a corresponding `working directory`. Otherwise the program would not find the needed files.
- Two `Python` scripts were written to populate mock entities for the benchmarks with a `mediocre` data and a `large` data. They are located in the the underlying [JMH module](./app/jmh)
- The necessary input data located in the `core` [module](./app/core/data) and the artifacts in the [build folder](./app/core/build) (after the compilation using `gradle fatJar`)
- `JMH` was used for more precise benchmarks on the file processing (Parsing-IO) time with multithreading and without it, but the naive calculation is also present below:

| Execution type            | Execution time (JMH, for `genre` extraction, avg time) | Execution time (Naive, for `genre` extraction)                                                     |
|---------------------------|--------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| 1 thread                  | 0,008 ±      0,001  ms/op                              | 0.06883 ms                                                                                         | 
| 2 threads                 | 3,004 ±      0,061  ms/op                              | 0.37824 ms                                                                                         |
| 4 threads                 | 3,218 ±      0,263  ms/op                              | 0.34431 ms                                                                                                  |
| 8 threads                 | 3,311 ±      0,077  ms/op                              | 0.34656 ms                                                                                         | 
| 7 threads = files number  | 3,376 ±      0,124  ms/op                              | 0.36191 ms                                                                                         |
| 16 threads                | 3,366 ±      0,079  ms/op                              | 0.41862 ms                                                                                         | 
| (processor cores) threads | 3,194 ±      0,187  ms/op                              | 0.33183 ms                                                                                         |

So essentially we could conclude, 
that for each increase of threads (threads > 1) 
we get a slightly better overall execution time, 
which the data shows. The best possible time is at (processor cores) threads, which is unexpected behaviour (due to oft context-switching, etc.), but can be explained with the fact, that most of the parsing is highly CPU bound.

The single thread execution stays the most optimal way for the objective.

