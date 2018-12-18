package kz.pompei.dynamic_compile

import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.config.Services
import java.io.File

object JvmCompile {

  fun exe(input: File, output: File, cp: List<File> = emptyList()): Boolean = K2JVMCompiler().run {
    val args = K2JVMCompilerArguments().apply {
      freeArgs = listOf(input.absolutePath)
      loadBuiltInsFromDependencies = true
      destination = output.absolutePath
      classpath = System.getProperty("java.class.path")
        .split(System.getProperty("path.separator"))
        .filter {
          File(it).exists() && File(it).canRead()
        }.joinToString(":") + ":" + cp.map { it.absolutePath }.joinToString(":")

      println("************** classpath = $classpath")

      noStdlib = true
      noReflect = true
      skipRuntimeVersionCheck = true
      reportPerf = true
    }
//    output.deleteOnExit()
    execImpl(
      PrintingMessageCollector(
        System.out,
        MessageRenderer.WITHOUT_PATHS, true
      ),
      Services.EMPTY,
      args
    )
  }.code == 0

}