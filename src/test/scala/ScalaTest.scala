import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import org.scalatest.FlatSpec

class ScalaTest extends FlatSpec {

  "asd" should "asd" in {
    val browser = JsoupBrowser()
    val doc = browser.get("https://habr.com/ru/top/")
    val list = doc >> elementList(".content-list__item_post")
    //    println(list)
    val listTitle = list.map(ele => {
      val title = ele >> elementList(".post__title") >> text("a")
      title
    }).filter(ele => ele.length > 0)
      .map(ele => ele(0))
    print(listTitle)
    assert(list.length > 0)
  }

}
