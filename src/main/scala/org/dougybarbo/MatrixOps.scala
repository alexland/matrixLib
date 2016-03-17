
package org.dougybarbo.MatrixLib

import java.util.concurrent.{
  Callable,
  Executors
}

import collection.mutable.ArrayBuffer
import util.{
  Random ⇒ RND
}

/**
 * 	service to multiply two matrices together
 * while swapping out a threading strategy
 */
object MatrixMul extends App {
  /**
   * 	(linear algebra) multiplication of 2 matrices
   * 	takes an implicit parameter for changing threading strategy
   */
  def mmul(a: Matrix, b: Matrix)(implicit threading: ThreadStrategy = ThreadStrategy.SameThreadStrategy): Matrix = {
    // ensure the columns-rows line up for legal matrix multipication
    assert(a.colRank == b.rowRank)
    // create buffer to store results
    val buffer = new Array[Array[Double]](a.rowRank)
    for (i ← 0 until a.rowRank) {
      buffer(i) = new Array[Double](b.colRank)
    }
    /**
     * 	helper function to compute the value stored at
     * 	index (row,col) in the resulting matrix &
     * 	place that value in the buffer
     */
    def computeValue(row: Int, col: Int): Unit = {
      // constructs a List of element pairs from the two matricies
      val pairwiseElements =
        a.row(row).zip(b.col(col))
      // multiplies every row value by every column value
      // sum of products is the resulting value on the matrix
      val products =
        for ((x, y) ← pairwiseElements)
          yield x * y
      val result = products.sum
      buffer(row)(col) = result
    }
    // create a list of computations for every (row,col) result of the matrix
    val computations = for {
      i ← 0 until a.rowRank
      j ← 0 until b.colRank
    } yield threading.execute { () ⇒ computeValue(i, j) }
    // execute all computations *or* wait for threading to finish
    computations.foreach(_())
    new Matrix(buffer)
  }

}
