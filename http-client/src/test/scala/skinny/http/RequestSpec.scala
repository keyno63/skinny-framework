package skinny.http

import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable

class RequestSpec extends WordSpec with Matchers {

  val request = new Request("http://example.com/")

  "Request" should {
    "be available" in {
      request.enableThrowingIOException(true)
      request.url("http://www.example.com")
      request.followRedirects(true)
      request.connectTimeoutMillis(100)
      request.readTimeoutMillis(100)
      request.referer("foo")
      request.userAgent("ua")
      request.contentType("text/html")
      request.header("foo") should equal(None)
      request.header("foo", "bar")
      request.headerNames.size should equal(1)
    }
  }

  "Test queryParams" should {
    "add queryParams value" in {
      val target = request.queryParams(("k", "v"), ("k1", "v1"))
      val expected = List(QueryParam("k", "v"), QueryParam("k1", "v1"))
      target.queryParams should equal(expected)
    }
  }

}
