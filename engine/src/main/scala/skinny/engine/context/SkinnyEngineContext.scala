package skinny.engine.context

import javax.servlet.ServletContext
import javax.servlet.http.{ HttpServletRequest, HttpServletResponse }

import skinny.engine.implicits.{ CookiesImplicits, ServletApiImplicits, SessionImplicits }
import skinny.engine.request.StableHttpServletRequest

object SkinnyEngineContext {

  private class StableSkinnyEngineContext(
      implicit val request: HttpServletRequest,
      val response: HttpServletResponse,
      val servletContext: ServletContext) extends SkinnyEngineContext {
  }

  def toStable(ctx: SkinnyEngineContext): SkinnyEngineContext = {
    new StableSkinnyEngineContext()(StableHttpServletRequest(ctx.request), ctx.response, ctx.servletContext)
  }

  def build(implicit ctx: ServletContext, req: HttpServletRequest, resp: HttpServletResponse): SkinnyEngineContext = {
    new StableSkinnyEngineContext()(StableHttpServletRequest(req), resp, ctx)
  }

  def buildWithRequest(req: HttpServletRequest): SkinnyEngineContext = {
    new StableSkinnyEngineContext()(StableHttpServletRequest(req), null, null)
  }

}

trait SkinnyEngineContext
    extends ServletApiImplicits
    with SessionImplicits
    with CookiesImplicits {

  val request: HttpServletRequest

  val response: HttpServletResponse

  val servletContext: ServletContext

  def toStable(): SkinnyEngineContext = SkinnyEngineContext.toStable(this)

}
