package org.mbari.vars.annotation.dao.jpa;

/**
 * @author Brian Schlining
 * @since 2016-05-06T13:55:00
 */
public enum TransactionType {
    REMOVE,
    FIND,
    CREATE,
    MERGE;
}