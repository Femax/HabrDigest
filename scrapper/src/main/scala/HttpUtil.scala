import akka.http.scaladsl.model.headers.RawHeader

object HttpUtil {
  val habrHeader = List(RawHeader("Accept-Encoding", "gzip, deflate"),
    RawHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"),
    RawHeader("Connection", "keep-alive"),
    RawHeader("Cookie", "_ga=GA1.2.435286178.1545131093;" +
      " _ym_uid=1545131094188580070;" +
      " _ym_d=1545131094;" +
      " __gads=ID=54e43e76baa10bd5:T=1545131101:S=ALNI_MZDopNarvyVSJ7qeaQUJpWvRlH8-Q;" +
      " hl=ru; fl=ru;" +
      " habrsession_id=habrsession_id_960c28af951bc48e034dd98eebc917e0;" +
      " PHPSESSID=7234fteoakbifco4o6dgfpqrum;" +
      " hsec_id=818dbd00211bda3bd83ba349e7569685;" +
      " ab_test_vacancies_block_group=A; " +
      "_gid=GA1.2.178341996.1555002804;" +
      " feed_flow=top; " +
      "_ym_visorc_24049213=w; " +
      "_ym_isad=1;" +
      " _gat=1; " +
      "_gat_HGM=1"),
    RawHeader("DNT", "1"),
    RawHeader("Host", "habr.com"),
    RawHeader("Referer", "https://habr.com/ru/top/"),
    RawHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36"),
    RawHeader("X-Requested-With", "XMLHttpRequest"))
}
