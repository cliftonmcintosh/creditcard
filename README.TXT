REQUIREMENTS
Java 8 must be installed to run this software.

=======================================================================
USAGE INSTRUCTIONS

STARTING UP
While in the creditcard directory, you can invoke the program via “java -jar creditcard.jar.”

To give a data file to the command for processing invoke the program via “java -jar creditcard.jar /home/cmcintosh/code/braintree/creditcard/my-data-file.txt ,” replacing “/home/cmcintosh/code/braintree/creditcard/my-data-file.txt” with the path to your data file. The path to the data file can be relative (like “../my-data-file.txt”). as well as absolute. I haven’t tried it on a Windows machine, but I think it should work there, too.

Once the program has started, you will receive a prompt at which you can enter commands as per the specs for the assignment.

ENDING THE PROGRAM
You can exit the program by typing “exit” at the prompt.

BUILDING THE PROJECT
The project uses Gradle. A Gradle wrapper is included. On a Mac or Linux, you can invoke it from the project directory with “./gradlew someCommand.” The commands you are most likely to want are “test” and “build.” Please see the Gradle documentation online for more.

CLONING THE PROJECT
The project uses git for version control. You can clone the “project” directory somewhere else if you want. You can also browse my commit history. Keep in mind that I was aware of the possibility that you could do that, so I tried to behave well while committing.

=======================================================================
BEHAVIORS

=======================================================================

There are a few cases that are not explicitly dealt with in the directions. Here is how you should expect the program to behave in specific circumstances.
1. If an operation has invalid parameters, the operation should fail.

2. All "Add" operations with invalid parameters will generate an error message like the Luhn 10 failures. For example, if "Add Lisa 5454545454545454" is provided as input, the output will be "Lisa: error" because there are not enough parameters . The first parameter to the "Add" operation will be treated as the "name." For example, if "Add 5454545454545454 $3000" is given as input, the output will be "5454545454545454: error".

3. "Charge" or "Credit" operations that have invalid parameters or missing parameters will be ignored in the same way that charges above the limit are ignored and charges and credits to invalid Luhn 10s are ignored. For example, "Charge $1000" will be ignored because there are not enough parameters.

4. A line with no parameters will be ignored. For example, if "Add" or "Add       " is the input, it should be ignored.

5. An empty line will be ignored. This is true in a file and via the command line.

6. Any extra parameters will be ignored. "Add Lisa 5454545454545454 $3000 Extra" will be processed just like "Add Lisa 5454545454545454 $3000".

7. A line that does not start with a valid operation will be ignored. For example, if someone writes "Create Lisa 5454545454545454 $3000," the line will be ignored.

8. After processing a file with input, my program will present the appropriate output as per the specification and then provide a prompt for further input. In other words, if you specify a file at the command line when starting the program, it will process the file, present the output and prompt for more input.

9. A summary of all accounts will be presented after each input. If you input three people at the command line one-by-one, then the output will be cumulative. After the first command, you will see the result of that command. After the second command, you will see the result of both commands. After the third command, you will see the result of all three commands.

10. The name is the unique identifier for an account. This is simply because that is the key by which the errors and balances are reported, as per the specification.

11. If a second Add is attempted for a name that has already been used successfully, the second Add will be ignored.

12. If an Add failed and then a future valid Add with the same name is attempted, the Add will succeed and the error will be replaced a the valid account for that name.

=======================================================================
LANGUAGE CHOICE AND DESIGN DECISIONS

=======================================================================

The primary reason I chose Java is simple: I chose it because Ryan Thorpe suggested that it was wise to choose the language with which I am most familiar, and Java is that language for me. I could have done it in Groovy in a similar way. I am reasonably confident that could have done it in Python successfully, but I am not as proficient in that language as in Java or Groovy. Please note Groovy is used for testing in this project.

Within the code, design decisions were guided by these factors:
1. I wanted to follow good design principles. This includes having each class be responsible for one thing.

2. I wanted to make the program flexible and extendable. This is why you will see the use of interfaces and some (roll-your-own) dependency injection in the Application class.

3. I wanted to make it testable. This is another reason for the dependency injection and for small bits that are individually testable and composable into a whole.

4. I wanted to play with Gradle. This is why the dependency and build management is done in Gradle.

5. I chose Spock for my testing framework because it is a framework that allows a developer to build out comprehensive tests with less ceremony and less hassle than JUnit. When we lower the barriers to testing, we increase the chances that developers will do the right thing. Spock is expressive. Spock has nice features for mocking and testing interactions. Spock has some excellent features for varying the data with which tests are run.

6. I wanted to enjoy what I was doing.

