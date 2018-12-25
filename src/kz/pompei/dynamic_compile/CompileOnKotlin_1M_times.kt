package kz.pompei.dynamic_compile

import org.fest.assertions.api.Assertions.assertThat
import java.io.File
import java.security.MessageDigest


fun main(args: Array<String>) {

  val c = Caller()

  for (i in (3..1000)) {
    val source = " x * $i | x / $i "
    println("i = $i :: result 1 = " + c.callF(2.3, 1, source) + ", 2 = " + c.callF(2.3, 2, source))
  }

  println(c.aveCompileTime)

}

class Caller {

  var totalCompileNanos: Long = 0L
  var compileCount: Int = 0
  val aveCompileTime: Double
    get() {
      return totalCompileNanos / 1e9 / compileCount
    }

  fun callF(x: Double, fIndex: Int, source: String): Double {
    val input = "build/two_functions/src"
    val output = "build/two_functions/out"
    val pack = "asd"

    val digest = MessageDigest.getInstance("SHA-256")

    val hash = bytesToHex(digest.digest(source.toByteArray()))

    val src = "$input/p$hash"
    val out = "$output/p$hash"

    if (!File(out).isDirectory) {

      val startedAt = System.nanoTime()

      val srcDir = "$src/$pack"
      val outDir = "$out/$pack"

      File(srcDir).mkdirs()
      File(outDir).mkdirs()

      val s12 = source.split("|")

      File("$srcDir/Impl$hash.kt").writeText(
        """
    | package asd
    |
    | class Impl$hash : kz.pompei.dynamic_compile.TwoFunctions {
    |
    |   override fun f1(x: Double): Double {
    |     return ${s12[0]}
    |   }
    |
    |   override fun f2(x: Double): Double {
    |     return ${s12[1]}
    |   }
    |
    | }
    | """.trimMargin()
      )

      assertThat(JvmCompile.exe(File(src), File(out))).isTrue

      val compileTime: Long = System.nanoTime() - startedAt
      totalCompileNanos += compileTime
      compileCount++
    }

    val instance = Initializer(File(out)).createInstance<TwoFunctions>("asd.Impl$hash")

    if (fIndex == 1) {
      return instance.f1(x)
    }
    if (fIndex == 2) {
      return instance.f2(x)
    }

    throw Exception("Illegal fIndex == $fIndex")
  }

}

private fun bytesToHex(hash: ByteArray): String {
  val hexString = StringBuffer()
  for (i in hash.indices) {
    val hex = Integer.toHexString(0xff and hash[i].toInt())
    if (hex.length == 1) hexString.append('0')
    hexString.append(hex)
  }
  return hexString.toString()
}