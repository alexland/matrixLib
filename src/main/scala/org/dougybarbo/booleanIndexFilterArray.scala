

package org.dougybarbo.MatrixLib


import scala.collection.mutable.ArrayBuffer


object MatrixFilter {

  /**
   * @ param m 2D Array, eg, created by Array.ofDim[Int](rows, cols)
   * @ param colIdx the integer index of a column in m
   * returns column in the 2D array, as specified by colIdx
   * as an ArrayBuffer
   */
  def colByIdx[A:Numeric](m:Array[Array[A]], colIdx:Int) = {
    var buf = new ArrayBuffer[A]()
    (0 to m.length-1).foreach(buf += m(_)(colIdx))
    buf
  }


  /**
   *
   *
   */
  def boolIdxFilter[A:Numeric](q:Seq[A], idx:Seq[Boolean]):Seq[A] = {
    for (t <- idx.zip(q).filter(t => t._1 == true)) yield t._2
  }


  /**
   *
   * @param q a sequence of (numeric) elements against which
   * the Boolean index is created
   * @param t threshold value which determines whether
   * @return Boolean index as Seq[Boolean] having same length as q
   *
   */
   def bidx[A:Numeric](q:Seq[A], f:A => Boolean):Seq[Boolean] = {
     q.map(f)
   }


  /**
    *
    *
    */
   def filterBidx[A:Numeric](q:Seq[A], idx:Seq[Boolean]):Seq[A] = {
     for (t <- idx.zip(q).filter(t => t._1 == true)) yield t._2
   }


  /**
   * equivalent to composing bidx & filterBidx, eg
   * filterBidx(bidx(q))
   * more efficient than above if the Boolean index does not
   * need to be re-used
   *
   */
  def filterBidx[A:Numeric](q:Seq[A], f:A => Boolean):Seq[A] = {
    q.filter(f)
  }

}
