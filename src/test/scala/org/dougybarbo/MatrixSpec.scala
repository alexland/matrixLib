

package org.dougybarbo.MatrixLib

import org.scalatest._
import org.scalactic._
import TripleEquals._
import Tolerance._

class MatrixSpec extends FlatSpec {

  implicit val ts = ThreadStrategy.SameThreadStrategy

  "a matrix" should "have a column rank and a row rank and a shape" in {
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

  "a matrix" should "be created from a matrix constructor" in {
    val a1 = MatrixCons.rnd(8, 5)
    val a2 = MatrixCons.rnd(5, 2)
    val res = MatrixMul.mmul(a1, a2)
    assert(a1.shape === (8, 5))
    assert(a2.shape === (5, 2))
    assert(res.shape === (8, 2))
  }

}
