package kz.pompei.dynamic_compile.wow

class TwoFunctionsExample : kz.pompei.dynamic_compile.TwoFunctions {
  override fun f1(x: Double): Double {
    return 2 * x
  }

  override fun f2(x: Double): Double {
    return x / 2
  }
}