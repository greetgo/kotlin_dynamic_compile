package kz.pompei.dynamic_compile

fun main(args: Array<String>) {

  val cl = Class.forName("kz.pompei.dynamic_compile.ParamsOperationOne")
  val operation = cl.newInstance() as ParamsOperation

  val params = Params()
  params.a = 3.5
  params.b = 13.5
  params.c = -13.5

  operation.operate(params)

  println("x = ${params.x}, y = ${params.y}, z = ${params.z}")

}
