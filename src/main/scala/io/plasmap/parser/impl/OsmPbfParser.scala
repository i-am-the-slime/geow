package io.plasmap.parser.impl

import io.plasmap.model._
import io.plasmap.model.geometry.Point
import io.plasmap.parser.OsmParser

import java.io.BufferedInputStream
import java.io.InputStream
import java.io.FileInputStream
import java.io.File
import collection.JavaConversions._

import scala.io.Source
import scala.io.BufferedSource
import scala.util.{Failure, Try, Success}
import org.joda.time.format.ISODateTimeFormat

import org.openstreetmap.osmosis.osmbinary.BinaryParser
import org.openstreetmap.osmosis.osmbinary.file.BlockReaderAdapter
import org.openstreetmap.osmosis.osmbinary.file._
import org.openstreetmap.osmosis.osmbinary.Osmformat._


/**
 * @author jm
 */

case class OsmPbfParser(source: File) extends OsmParser {
  
  val inputStream = new FileInputStream(source)
  val callBackPbfParser = new CallBackPbfParser()
  val blockInputStream = new BlockInputStream(inputStream, callBackPbfParser)
  
  override def next() : Option[OsmObject] = {
    blockInputStream.process()
    inputStream.close()
    None
  }
  
  override def hasNext() : Boolean = {
    true
  }
}

class CallBackPbfParser extends BinaryParser{
  
  
  override def parseRelations( rels : java.util.List[Relation]): Unit = {
    println(rels.size() + " Relations!")
  }

  override def parseDense( nodes : DenseNodes): Unit = {
    println(nodes.getIdCount() + " DenseNodes!")
  }

  override def parseNodes( nodes : java.util.List[Node]): Unit = {
    println(nodes.size() + " Nodes!")
  }

  override def parseWays( ways : java.util.List[Way]): Unit = {
    println(ways.size() + " ways!")
  }

  override def parse(header : HeaderBlock ): Unit = {
    println("Got header block.");
  }

  def complete() {
    println("Complete!");
  }
  
  /* For understanding BinaryParser behavior */
  
  override def handleBlock( message : FileBlock) = {
    println("handleBlock!");
    super.handleBlock(message)
  }
  
  override def skipBlock( block : FileBlockPosition) = {
    println("skipBlock!");
    super.skipBlock(block)
  }
  
  override def parse( block : PrimitiveBlock) = {
    println("skipBlock!");
    super.parse(block)
  }
}
