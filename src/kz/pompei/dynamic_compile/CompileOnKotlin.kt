package kz.pompei.dynamic_compile

import org.fest.assertions.api.Assertions.assertThat
import java.io.File

fun main(args: Array<String>) {
  val cp = File("build/classes/main").absoluteFile

  println("cp = $cp")

  val input = File("source_dir/src01")
  val output = File("source_dir/out01")

  assertThat(JvmCompile.exe(input, output)).isTrue

  val instance = Initializer(output).createInstance<ParamsOperation>("kz.pompei.Impl01")

  val params = Params()
  params.a = 3.1
  params.b = 3.1
  params.c = 3.1

  instance.operate(params)

  println("x = ${params.x}, y = ${params.y}, z = ${params.z}")

  println(input.absolutePath)
}
