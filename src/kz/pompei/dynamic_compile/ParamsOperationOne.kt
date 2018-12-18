package kz.pompei.dynamic_compile

class ParamsOperationOne : ParamsOperation {
  override fun operate(params: Params) {
    params.x = params.a + params.a
    params.y = params.b + params.c
    params.z = params.a + params.b + params.c
  }
}
