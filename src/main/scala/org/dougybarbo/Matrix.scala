
package org.dougybarbo.MatrixLib

import java.util.concurrent.{
  Callable,
  Executors
}

import collection.mutable.ArrayBuffer

/**
 * stores a dense two-dimensional matrix of finite size
 */
class Matrix(private val repr: Array[Array[Double]]) {
  /**
   * access the row at idx (0-based)
   * returns a column-ordered Seq of vals in row
   */
  def row(idx: Int): Seq[Double] = {
    repr(idx)
  }
  /**
   * access the column at idx (0-based)
   * returns a row-ordered Seq of the values in the column
   */
  def col(idx: Int): Seq[Double] = {
    repr.foldLeft(ArrayBuffer[Double]()) {
      (buffer, currentRow) ⇒
        buffer.append(currentRow(idx))
        buffer
    } toArray
  }

  lazy val rowRank = repr.size
  lazy val colRank = if (rowRank > 0) repr(0).size else 0
  lazy val shape = (repr.size, colRank)
  /**
   * 	pretty-printer
   */
  override def toString = "Matrix" + repr.foldLeft("") {
    (data, row) ⇒ data + row.mkString("\n|", " ", "|")
  }
}
