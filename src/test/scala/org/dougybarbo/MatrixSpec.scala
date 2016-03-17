

package org.dougybarbo.MatrixLib

import org.scalatest._
import org.scalactic._
import TripleEquals._
import Tolerance._

class MatrixSpec extends FlatSpec {

  implicit val ts = ThreadStrategy.SameThreadStrategy

  "a Matrix" should "have a column rank and a row rank and a shape" in {
    val a = new Matrix(Array(Array(4, 3, 9), Array(5, 2, 7)))
    assert(a.colRank === 3)
    assert(a.rowRank === 2)
    assert(a.shape === (2, 3))
  }

  "two matrices of compatible shape" should "multiply & return a new matrix" in {
    val a1 = new Matrix(Array(Array(4, 3, 9), Array(5, 2, 7)))
    val a2 = new Matrix(Array(Array(4), Array(7), Array(3)))
    val res = MatrixMul.mmul(a1, a2)
    assert(res.shape === (2, 1))
  }

}
