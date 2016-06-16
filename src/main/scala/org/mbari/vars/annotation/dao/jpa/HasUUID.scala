package org.mbari.vars.annotation.dao.jpa

import java.util.UUID
import javax.persistence._

import org.mbari.vars.annotation.dao.PersistentObject

/**
 * Mixin that supports the UUID fields
 *
 * @author Brian Schlining
 * @since 2016-05-05T17:50:00
 */
trait HasUUID extends PersistentObject {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @Column(
    name = "uuid",
    nullable = false,
    updatable = false
  )
  @Convert(converter = classOf[UUIDConverter])
  var uuid: UUID = _

  def primaryKey: Option[UUID] = Option(uuid)

}