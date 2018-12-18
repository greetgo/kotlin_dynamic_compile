package kz.pompei

import kz.pompei.dynamic_compile.ParamsOperation
import kz.pompei.dynamic_compile.Params

class Impl01 : ParamsOperation {
  override fun operate(params: Params) {
    params.x = params.a * 10
    params.y = params.b * 10
    params.z = params.a * 10
  }
}
