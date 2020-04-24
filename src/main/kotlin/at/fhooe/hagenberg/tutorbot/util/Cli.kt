package at.fhooe.hagenberg.tutorbot.util

import java.io.OutputStream
import java.io.PrintStream

fun promptTextInput(prompt: String): String {
    print("$prompt ")
    return readLine() ?: ""
}

fun promptPasswordInput(prompt: String): String {
    print("$prompt ")
    return System.console().readPassword().joinToString(separator = "")
}

fun promptBooleanInput(prompt: String): Boolean {
    val positiveAnswers = listOf("", "y", "yes")
    val textInput = promptTextInput("$prompt [Y/N]")
    return textInput.trim().toLowerCase() in positiveAnswers
}

fun exitWithError(message: String): Nothing {
    System.err.println("ERROR: $message")
    throw ProgramExitError()
}

inline fun runWithCapturedOutput(block: () -> Unit) {
    val out = System.out
    val err = System.err

    // Redirect all outputs to a dummy stream
    val dummyStream = PrintStream(OutputStream.nullOutputStream())
    System.setOut(dummyStream)
    System.setErr(dummyStream)

    block() // Execute the code

    // Restore original streams
    System.setOut(out)
    System.setErr(err)
}

class ProgramExitError : Error()
