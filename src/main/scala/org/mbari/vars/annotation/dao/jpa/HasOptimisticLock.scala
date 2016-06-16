package org.mbari.vars.annotation.dao.jpa

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.{ Column, Version }

/**
 * OUr Entities should use optimixtic locks. This trait allows you to mixin the lock.
 *
 * @author Brian Schlining
 * @since 2016-05-05T16:22:00
 */
trait HasOptimisticLock {

  /** Optimistic lock to prevent concurrent overwrites */
  @Version
  @Column(name = "last_updated_time")
  protected var lastUpdatedTime: Timestamp = _

  def lastUpdated: Option[Instant] = Option(lastUpdatedTime).map(_.toInstant)

}