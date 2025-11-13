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

- Following entities were introduced: `Bookshelf`, `Book`, `BookAuthor`, `Statistics` ,  
- Leveraged `JsonParser` from the `Jackson JSON` library to read attributes in a lazy fashion.
- Leveraged `Apache Commons CLI` to provide a convenient way to control the program startup.
- `Architectural layers`, `Decoupling`, `SOLID` were maintained to provide a clear structure of the application. `*` In particular was developed modular structure with the usage of `Gradle` to separate benchmarks (`jmh`) and main program (`core`) logic

### Notes
- Two `Python` scripts were written to populate mock entities for the benchmarks with a `mediocre` data and a `large` data. They are located in the the underlying [JMH module](./app/jmh)
- The necessary input data located in the `core` [module](./app/core/data) and the artifacts in the [build folder](./app/core/build) (after the compilation using `gradle build`)
- `JMH` was used for more precise benchmarks on the file processing time with multithreading and without it, but the naive calculation is also present below:

| Execution type           | Execution time (JMH, for `genre` extraction, throughput) | Execution time (Naive, for `genre` extraction) |
|--------------------------|---------------------------------------------------------|------------------------------------------------|
| 1 thread                 | 59,980 ± 3,189 ops/s                                        | 310,298 file / s                               | 
| 2 threads                | 92,367 ± 18,198 ops/s                                   | 330,555 file / s                               |
| 4 threads                | 161,969 ± 17,437 ops/s                                  | 212,195 file / s                               |
| 8 threads                | 174,280 ± 13,457 ops/s                                  | 364,266 file / s                               | 
| 7 threads = files number | 181,748 ± 2,900 ops/s                                   | 263,422 file / s                               |

- Make sure to run the `core` project from a corresponding `working directory`. Otherwise the program would not find the
