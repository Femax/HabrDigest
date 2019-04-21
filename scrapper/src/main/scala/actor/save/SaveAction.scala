package actor.save

import ru.test.fedosov.habt.digest.common.Post.Post


case class SaveAction(list: List[Post])
