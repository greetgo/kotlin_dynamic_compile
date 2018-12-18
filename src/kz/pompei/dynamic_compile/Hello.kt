package kz.pompei.dynamic_compile

fun main(args: Array<String>) {
  val client = Client("wow")
  client.name = "WOW"
  println(client)
}