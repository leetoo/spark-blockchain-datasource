package com.endor.spark.blockchain.ethereum.block.streaming

import java.io.File

import com.endor.spark.blockchain.ethereum.block.SimpleEthereumBlock
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.Source
import org.apache.spark.sql.sources.StreamSourceProvider
import org.apache.spark.sql.types.StructType

import scala.reflect.io.Path

class DefaultSource extends StreamSourceProvider{
  override def sourceSchema(sqlContext: SQLContext, schema: Option[StructType], providerName: String,
                            parameters: Map[String, String]): (String, StructType) =
    ("ethereumBlocks", SimpleEthereumBlock.encoder.schema)



  override def createSource(sqlContext: SQLContext, metadataPath: String, schema: Option[StructType],
                            providerName: String, parameters: Map[String, String]): Source = {
    val syncEnabled = parameters.get("syncEnabled").forall(_.toBoolean)
    val databaseLocation = parameters.getOrElse("databaseLocation", (Path(new File(".")) / "database").toAbsolute.toString())
    new BlockStreamSource(databaseLocation, syncEnabled)(sqlContext.sparkSession)
  }
}