

package org.dougybarbo.MatrixLib

import java.util.concurrent.{
	Callable,
	Executors
}

import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest._
import org.scalactic._
import Matchers._
import TripleEquals._
import Tolerance._
import prop.TableDrivenPropertyChecks._
import concurrent.{
	PatienceConfiguration,
	ScalaFutures
}

class MatrixSpec extends FlatSpec with Matchers {

	implicit val ts = ThreadScheme.LocalThread

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

	"a 2D matrix" should "result from multiplication of two 2D matrices" in {

		val matrixDims = Table(
			("r1", "c1", "r2", "c2"),
			(8, 5, 5, 2),
			(4, 3, 3, 2),
			(6, 4, 4, 4),
			(8, 3, 3, 2),
			(3, 5, 5, 3),
			(9, 4, 4, 8),
			(2, 2, 2, 2),
			(6, 8, 8, 3)
		)

		forAll(matrixDims) { (r1: Int, c1: Int, r2: Int, c2: Int) ⇒

			val a1 = MatrixCons.rnd(r1, c1)
			val a2 = MatrixCons.rnd(r2, c2)
			val res = MatrixMul.mmul(a1, a2)

			res.shape shouldEqual (r1, c2)

		}

	}

}
