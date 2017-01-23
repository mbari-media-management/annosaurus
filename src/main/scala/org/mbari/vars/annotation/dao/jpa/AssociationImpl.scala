package org.mbari.vars.annotation.dao.jpa

import javax.persistence.{Index, NamedQuery, _}

import com.google.gson.annotations.Expose
import org.mbari.vars.annotation.model.{Association, Observation}

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-06-17T11:36:00
 */
@Entity(name = "Association")
@Table(name = "associations", indexes = Array(
  new Index(name = "idx_link_name", columnList = "link_name"),
  new Index(name = "idx_link_value", columnList = "link_value"),
  new Index(name = "idx_to_concept", columnList = "to_concept")
))
@EntityListeners(value = Array(classOf[TransactionLogger]))
@NamedQueries(Array(
  new NamedQuery(
    name = "Association.findAll",
    query = "SELECT a FROM Association a"
  ),
  new NamedQuery(
    name = "Association.findByLinkName",
    query = "SELECT a FROM Association a WHERE a.linkName = :linkName"
  ),
  new NamedQuery(
    name = "Association.findByLinkNameAndVideoReferenceUUID",
    query = "SELECT a FROM Association a INNER JOIN FETCH a.observation o INNER JOIN FETCH o.imagedMoment im WHERE im.videoReferenceUUID = :videoReferenceUuid AND a.linkName = :linkName"
  )
))
class AssociationImpl extends Association with JPAPersistentObject {

  @Expose(serialize = true)
  @Column(
    name = "link_name",
    length = 128,
    nullable = false
  )
  override var linkName: String = _

  @Expose(serialize = true)
  @Column(
    name = "link_value",
    length = 1024,
    nullable = true
  )
  override var linkValue: String = _

  @Expose(serialize = false)
  @ManyToOne(
    cascade = Array(CascadeType.PERSIST, CascadeType.DETACH),
    optional = false,
    targetEntity = classOf[ObservationImpl]
  )
  @JoinColumn(name = "observation_uuid", nullable = false)
  override var observation: Observation = _

  @Expose(serialize = true)
  @Column(
    name = "to_concept",
    length = 128,
    nullable = true
  )
  override var toConcept: String = _

  override def toString: String = Association.asString(this)

}

object AssociationImpl {

  def apply(linkName: String, toConcept: String, linkValue: String): AssociationImpl = {
    val a = new AssociationImpl
    a.linkName = linkName
    a.toConcept = toConcept
    a.linkValue = linkValue
    a
  }

  def apply(
    linkName: String,
    toConcept: Option[String] = None,
    linkValue: Option[String] = None
  ): Unit = {
    val a = new AssociationImpl
    a.linkName = linkName
    toConcept.foreach(a.toConcept = _)
    linkValue.foreach(a.linkValue = _)
  }

}
