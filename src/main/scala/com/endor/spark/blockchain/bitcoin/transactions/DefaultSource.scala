package com.endor.spark.blockchain.bitcoin.transactions

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{BaseRelation, RelationProvider}

class DefaultSource extends RelationProvider  {
  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
    val path = parameters
      .getOrElse("path", sys.error("'path' must be specified with files containing Bitcoin blockchain data."))

    val network = parameters
      .getOrElse("network", sys.error("'network' must be specified."))

    Relation(path, network)(sqlContext)
  }
}
